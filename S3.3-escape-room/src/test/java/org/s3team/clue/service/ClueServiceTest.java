package org.s3team.clue.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.s3team.clue.dao.ClueDao;
import org.s3team.clue.model.*;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.room.dao.RoomDAO;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.model.Theme;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClueServiceTest {

    private ClueDao clueDao;
    private RoomDAO roomDao;
    private ThemeDao themeDao;

    private ClueService clueService;
    private Clue clue;

    @BeforeEach
    void setUp() {
        clueDao = mock(ClueDao.class);
        roomDao = mock(RoomDAO.class);
        themeDao = mock(ThemeDao.class);
        clueService = new ClueService(clueDao, roomDao, themeDao);
        clue = Clue.createNew(ClueType.OBJECT,new ClueDescription("description"), new Price(BigDecimal.valueOf(5)),new Id<>(4),new Id<>(5));
    }

    @Test
    void updateClue_ok() {
        Id<Room> roomId = clue.getRoomId();
        Id<Theme> themeId = clue.getThemeId();

        Room room = Room.rehydrate(roomId,new Name("test"),Difficulty.EASY, new Price(BigDecimal.valueOf(1)),themeId);
        Theme theme = Theme.rehydrate(themeId, new Name("Theme"));

        when(roomDao.findById(roomId)).thenReturn(Optional.of(room));
        when(themeDao.getById(themeId)).thenReturn(theme);

        when(clueDao.update(clue)).thenReturn(true);

        boolean result = clueService.updateClue(clue);

        assertTrue(result);
        verify(roomDao).findById(roomId);
        verify(themeDao).getById(themeId);
        verify(clueDao).update(clue);
    }

    @Test
    void getClueById_found() {
        Id<Clue> clueId = new Id<>(1);

        when(clueDao.findById(clueId)).thenReturn(Optional.of(clue));

        Optional<Clue> result = clueService.getClueById(clueId);

        assertTrue(result.isPresent());
        assertEquals(clue, result.get());
        verify(clueDao).findById(clueId);
    }

    @Test
    void getAllClues_ok() {
        Clue clue1 = clue;
        Clue clue2 = clue;
        Clue clue3 = clue;

        when(clueDao.findAll()).thenReturn(Arrays.asList(clue1, clue2, clue3));

        List<Clue> clues = clueService.getAllClues();

        assertEquals(3, clues.size());
        verify(clueDao).findAll();
    }

    @Test
    void deleteClue_ok() {
        Id<Clue> clueId = new Id<>(1);

        when(clueDao.delete(clueId)).thenReturn(true);

        boolean result = clueService.deleteClue(clueId);

        assertTrue(result);
        verify(clueDao).delete(clueId);
    }

    @Test
    void calculateTotalPrice_ok() {
        Price total = new Price(BigDecimal.valueOf(130));

        when(clueDao.calculateTotalPrice()).thenReturn(total);

        Price result = clueService.calculateTotalPrice();

        assertEquals(total, result);
        verify(clueDao).calculateTotalPrice();
    }
}
