package org.s3team.theme.dao;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.theme.model.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThemeDaoImpl implements ThemeDao{

    private final Data_Base_Connection db;

    public ThemeDaoImpl(Data_Base_Connection db){
        this.db = db;
    }

    @Override
    public Theme save(Theme theme) {
        String sql = "INSERT INTO theme (name) VALUES (?)";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, theme.getName().value());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                Id<Theme> generatedId = new Id<>(rs.getInt(1));
                return Theme.rehydrate(generatedId, theme.getName());
            } else {
                throw new RuntimeException("The ID generated cannot be obtained");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new RuntimeException("A theme with that name already exists");
            }
            throw new RuntimeException("Error saving Theme", e);
        }
    }

    @Override
    public Optional<Theme> findById(Id<Theme> id) {
        String sql = "SELECT * FROM theme WHERE id_theme = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Theme theme = mapRow(rs);
                return Optional.of(theme);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching Theme by ID", e);
        }
    }

    @Override
    public List<Theme> findAll() {
        String sql = "SELECT * FROM theme";
        List<Theme> themes = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                themes.add(mapRow(rs));
            }

            return themes;

        } catch (SQLException e) {
            throw new RuntimeException("Error listing Themes", e);
        }
    }

    @Override
    public boolean update(Theme theme) {
        String sql = "UPDATE theme SET name = ? WHERE id_theme = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, theme.getName().value());
            stmt.setInt(2, theme.getId().value());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new RuntimeException("A theme with that name already exists");
            }
            throw new RuntimeException("Error al actualizar Theme", e);
        }
    }


    @Override
    public boolean delete(Id<Theme> id) {
        String sql = "DELETE FROM theme WHERE id_theme = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id.value());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Theme" + id, e);
        }
    }

    @Override
    public Optional<Theme> findByName(Name name) {
        String sql = "SELECT * FROM theme WHERE name = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name.value());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Theme theme = mapRow(rs);
                return Optional.of(theme);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching Theme by name" + name, e);
        }
    }

    @Override
    public boolean deleteByName(Name name) {
        String sql = "DELETE FROM theme WHERE name = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name.value());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Theme by name", e);
        }
    }

    private Theme mapRow(ResultSet rs) throws SQLException {
        return Theme.rehydrate(
                new Id<>(rs.getInt("id_theme")),
                new Name(rs.getString("name"))
        );
    }
}
