package org.s3team.playercertificate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.s3team.certificate.dao.CertificateDao;
import org.s3team.certificate.model.Certificate;
import org.s3team.certificate.model.CertificateType;
import org.s3team.certificate.model.Reward;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.player.dao.PlayerDAO;
import org.s3team.player.model.Email;
import org.s3team.player.model.Player;
import org.s3team.playercertificate.dao.PlayerCertificateDao;
import org.s3team.room.dao.RoomDAO;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlayerCertificateServiceTest {

    @Mock
    private PlayerCertificateDao pcDao;

    @Mock
    private CertificateDao certificateDao;

    @Mock
    private PlayerDAO playerDAO;

    @Mock
    private RoomDAO roomDAO;

    @InjectMocks
    private PlayerCertificateService pcService;

    private Id<Player> playerId;
    private Id<Certificate> certificateId;
    private Id<Room> roomId;

    private Player player;
    private Certificate certificate;
    private Room room;

    @BeforeEach
    void setup() {
        playerId = new Id<>(1);
        certificateId = new Id<>(2);
        roomId = new Id<>(3);

        player = Player.create(new Name("Player"),new Email("Carlos@gmail.com"),true);
        certificate = Certificate.createNew(CertificateType.NO_HINTS_CHALLENGE,Reward.NONE);
        room = Room.createNew(new Name("Room"), Difficulty.EASY,new Price(BigDecimal.valueOf(10)),new Id<>(5));
    }

    @Test
    void assignCertificate_alreadyExists_throwsException() throws SQLException {
        when(playerDAO.findById(playerId)).thenReturn(Optional.of(player));
        when(certificateDao.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(roomDAO.findById(roomId)).thenReturn(Optional.of(room));

        when(pcDao.exists(playerId, certificateId, roomId)).thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                pcService.assignCertificate(playerId, certificateId, roomId)
        );

        assertTrue(ex.getMessage().contains("already assigned"));

        verify(playerDAO).findById(playerId);
        verify(certificateDao).findById(certificateId);
        verify(roomDAO).findById(roomId);
        verify(pcDao).exists(playerId, certificateId, roomId);

        verify(pcDao, never()).add(any());
    }

    @Test
    void assignCertificate_notExists_addsCertificate() throws SQLException {
        when(playerDAO.findById(playerId)).thenReturn(Optional.of(player));
        when(certificateDao.findById(certificateId)).thenReturn(Optional.of(certificate));
        when(roomDAO.findById(roomId)).thenReturn(Optional.of(room));

        when(pcDao.exists(playerId, certificateId, roomId)).thenReturn(false);

        when(pcDao.add(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = pcService.assignCertificate(playerId, certificateId, roomId);

        assertNotNull(result);
        assertEquals(playerId, result.getPlayerId());
        assertEquals(certificateId, result.getCertificateId());
        assertEquals(roomId, result.getRoomId());

        verify(pcDao).exists(playerId, certificateId, roomId);
        verify(pcDao).add(any());
    }
}
