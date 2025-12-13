package org.s3team.certificate.dao;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.certificate.model.Certificate;
import org.s3team.certificate.model.CertificateType;
import org.s3team.certificate.model.Reward;

import org.s3team.common.valueobject.Id;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CertificateDaoImpl implements CertificateDao{

    private final Data_Base_Connection db;

    public CertificateDaoImpl(Data_Base_Connection db) { this.db = db; }

    @Override
    public Certificate save(Certificate certificate) {
        String sql = "INSERT INTO certificate (type, reward) VALUES (?, ?)";

        try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){


            stmt.setString(1, certificate.getCertificateType().name());
            stmt.setString(2, certificate.getReward().value());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Id<Certificate> generatedId = new Id<>(rs.getInt(1));
                return Certificate.rehydrate(
                        generatedId,
                        certificate.getCertificateType(),
                        certificate.getReward()
                );
            } else {
                throw new SQLException("No ID returned for certificate");
            }
        } catch (SQLException e){
            throw new RuntimeException("Error saving certificate", e);
        }
    }

    @Override
    public Optional<Certificate> findById(Id<Certificate> id) {
        String sql = "SELECT * FROM certificate WHERE id_certificate = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Error finding certificate", e);
        }
    }

    @Override
    public List<Certificate> findAll() {
        String sql = "SELECT * FROM certificate";
        List<Certificate> certificates = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                certificates.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all certificates", e);
        }
        return certificates;
    }

    @Override
    public boolean update(Certificate certificate) {
        String sql = "UPDATE certificate SET type = ?, reward = ? WHERE id_certificate = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, certificate.getCertificateType().toString());
            stmt.setString(2, certificate.getReward().value());
            stmt.setInt(3, certificate.getId().value());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating certificate", e);
        }
    }

    @Override
    public boolean delete(Id<Certificate> id) {
        String sql = "DELETE FROM certificate WHERE id_certificate = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting certificate", e);
        }
    }

    private Certificate mapRow(ResultSet rs) throws SQLException {
        return Certificate.rehydrate(
                new Id<>(rs.getInt("id_certificate")),
                CertificateType.valueOf(rs.getString("type")),
                new Reward(rs.getString("reward"))
        );
    }
}
