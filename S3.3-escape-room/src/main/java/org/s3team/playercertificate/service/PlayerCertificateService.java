package org.s3team.playercertificate.service;

import org.s3team.Exceptions.CertificateNotFoundException;
import org.s3team.Exceptions.PlayerNotFoundException;
import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.Player.DAO.PlayerDAO;
import org.s3team.Player.Model.Player;
import org.s3team.certificate.dao.CertificateDao;
import org.s3team.certificate.model.Certificate;
import org.s3team.common.valueobject.Id;
import org.s3team.playercertificate.dao.PlayerCertificateDao;
import org.s3team.playercertificate.dto.PlayerCertificateInfo;
import org.s3team.playercertificate.model.PlayerCertificate;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.model.Room;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class PlayerCertificateService {

    private final PlayerCertificateDao pcDao;
    private final CertificateDao certificateDao;
    private final PlayerDAO playerDAO;
    private final RoomDAO roomDAO;


    public PlayerCertificateService(PlayerCertificateDao pcDao, CertificateDao certificateDao, PlayerDAO playerDAO, RoomDAO roomDAO) {
        this.pcDao = pcDao;
        this.certificateDao = certificateDao;
        this.playerDAO = playerDAO;
        this.roomDAO = roomDAO;
    }

    public PlayerCertificate assignCertificate(Id<Player> playerId, Id<Certificate> certificateId, Id<Room> roomId) {
        Objects.requireNonNull(playerId, "Player ID cannot be null");
        Objects.requireNonNull(certificateId, "Certificate ID cannot be null");
        Objects.requireNonNull(roomId, "Room ID cannot be null");

        try {
            playerDAO.findById(playerId)
                    .orElseThrow(() -> new PlayerNotFoundException(playerId));

            certificateDao.findById(certificateId)
                    .orElseThrow(() -> new CertificateNotFoundException(certificateId));

            roomDAO.findById(roomId)
                    .orElseThrow(() -> new RoomNotFoundException(roomId));

            if (pcDao.exists(playerId, certificateId, roomId)) {
                throw new IllegalStateException("Certificate " + certificateId + " already assigned to player " + playerId + " in room " + roomId);
            }


            PlayerCertificate pc = PlayerCertificate.createNew(playerId, certificateId, roomId);
            return pcDao.add(pc);

        } catch (SQLException e) {
            throw new RuntimeException("Error assigning certificate", e);
        }
    }

    public List<PlayerCertificate> getCertificatesByPlayer(Id<Player> playerId) {
        Objects.requireNonNull(playerId, "Player ID cannot be null");

        try {
            return pcDao.findByPlayer(playerId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching certificates by player", e);
        }
    }

    public List<PlayerCertificate> getCertificatesByRoom(Id<Room> roomId) {
        Objects.requireNonNull(roomId, "Room ID cannot be null");

        try {
            return pcDao.findByRoom(roomId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching certificates by room", e);
        }
    }

    public List<PlayerCertificate> getAllCertificates() {
        try {
            return pcDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all certificates", e);
        }
    }

    public List<PlayerCertificateInfo> getCertificatesForPlayerWithInfo(Id<Player> playerId) {
        Objects.requireNonNull(playerId, "Player ID cannot be null");

        try {
            return pcDao.findCertificatesByPlayerWithInfo(playerId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching certificates for player", e);
        }
    }

    public List<PlayerCertificateInfo> getCertificatesForRoomWithInfo(Id<Room> roomId) {
        Objects.requireNonNull(roomId, "Room ID cannot be null");

        try {
            return pcDao.findCertificatesByRoomWithInfo(roomId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching certificates for room", e);
        }
    }

    public List<PlayerCertificateInfo> getAllCertificatesWithInfo() {
        try {
            return pcDao.findAllWithInfo();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all certificates with info", e);
        }
    }
}
