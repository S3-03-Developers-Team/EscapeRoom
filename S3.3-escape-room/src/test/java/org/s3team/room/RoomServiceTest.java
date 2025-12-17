package org.s3team.room;

import org.junit.jupiter.api.Test;
import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.Exceptions.ThemeNotFoundException;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.room.model.Room;
import org.s3team.theme.model.Theme;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.s3team.common.valueobject.*;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.Service.RoomService;
import org.s3team.room.model.Difficulty;
import org.s3team.theme.dao.ThemeDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomDAO mockRoomDAO;

    @Mock
    private ThemeDao mockThemeDao;

    @InjectMocks
    private RoomService roomServiceWithMocks;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findAll_shouldReturnListFromDAO() {
        List<Room> fakeList = new ArrayList<>();
        fakeList.add(Room.createNew(new Name("Mock Room 1"), Difficulty.EASY, new Price(new BigDecimal("10")), new Id<>(1)));
        fakeList.add(Room.createNew(new Name("Mock Room 2"), Difficulty.MEDIUM, new Price(new BigDecimal("20")), new Id<>(2)));

        when(mockRoomDAO.findAll()).thenReturn(fakeList);

        List<Room> result = roomServiceWithMocks.findAll();

        assertEquals(2, result.size());
        verify(mockRoomDAO).findAll();
    }

    // ==============================
    // Mockito 测试 save，验证主题存在
    // ==============================
/*    @Test
    void save_shouldCallDAOAndReturnRoom_whenThemeExists_mock() {
        Room room = Room.createNew(
                new Name("Mock Room"),
                Difficulty.HARD,
                new Price(new BigDecimal("50")),
                new Id<>(1)
        );

        when(mockThemeDao.findById(room.getThemeId())).thenReturn(Optional.of(Theme.rehydrate(new Id<>(1), new Name("Mock Theme"))));
        when(mockRoomDAO.save(room)).thenReturn(room);

        Room saved = roomServiceWithMocks.save(room);

        assertEquals(room, saved);
        verify(mockThemeDao).findById(room.getThemeId());
        verify(mockRoomDAO).save(room);
    }*/

@Test
    void save_shouldThrowException_whenThemeNotFound() {
        Id<Theme> themeId = new Id<>(99);
        Room room = Room.createNew(new Name("Room B"), Difficulty.MEDIUM, new Price(new BigDecimal("150")), themeId);

        when(mockThemeDao.findById(themeId)).thenReturn(Optional.empty());

        assertThrows(ThemeNotFoundException.class, () -> roomServiceWithMocks.save(room));
        verify(mockRoomDAO, never()).save(any());
    }

    @Test
    void findById_shouldReturnRoom_whenExists() {
        Id<Room> roomId = new Id<>(1);
        Room room = Room.createNew(new Name("Room C"), Difficulty.HARD, new Price(new BigDecimal("200")), new Id<>(1));

        when(mockRoomDAO.findById(roomId)).thenReturn(Optional.of(room));

        Room found = roomServiceWithMocks.findById(roomId);

        assertEquals(room, found);
    }

    @Test
    void findById_shouldThrowException_whenNotFound() {
        Id<Room> roomId = new Id<>(99);

        when(mockRoomDAO.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomServiceWithMocks.findById(roomId));
    }

    @Test
    void delete_shouldCallDAOAndReturnTrue_whenRoomExists() {
        Id<Room> roomId = new Id<>(1);
        Room mockRoom = mock(Room.class);

        // 模拟 DAO 行为
        when(mockRoomDAO.findById(roomId)).thenReturn(Optional.of(mockRoom));
        when(mockRoomDAO.delete(roomId)).thenReturn(true);

        // 调用 Service 方法
        boolean result = roomServiceWithMocks.delete(roomId);

        // 验证
        assertTrue(result);
        verify(mockRoomDAO).findById(roomId);
        verify(mockRoomDAO).delete(roomId);
    }

    @Test
    void delete_shouldThrowException_whenRoomNotFound() {
        Id<Room> roomId = new Id<>(99);

        when(mockRoomDAO.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomServiceWithMocks.delete(roomId));

        verify(mockRoomDAO, never()).delete(any());
    }

    @Test
    void update_shouldReturnTrue_whenRoomExistsAndThemeExists() {
        Id<Theme> themeId = new Id<>(1);

        Room room = Room.rehydrate(new Id<>(1), new Name("Updated Room"), Difficulty.HARD, new Price(new BigDecimal("200")), themeId);

        when(mockRoomDAO.findById(room.getRoomId())).thenReturn(Optional.of(room));
        when(mockThemeDao.findById(themeId)).thenReturn(Optional.of(mock(Theme.class)));
        when(mockRoomDAO.update(room)).thenReturn(true);

        boolean result = roomServiceWithMocks.update(room);

        assertTrue(result);
        verify(mockRoomDAO).update(room);
    }

    @Test
    void update_shouldThrowException_whenRoomNotFound() {
        Id<Room> roomId = new Id<>(99);
        Id<Theme> themeId = new Id<>(1);
        Room room = Room.createNew(new Name("Nonexistent Room"), Difficulty.EASY, new Price(new BigDecimal("50")), themeId);

        when(mockRoomDAO.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> roomServiceWithMocks.update(room));

        verify(mockRoomDAO, never()).update(any());
    }

    @Test
    void count_shouldReturnValueFromDAO() {
        when(mockRoomDAO.count()).thenReturn(5);

        int result = roomServiceWithMocks.count();

        assertEquals(5, result);
        verify(mockRoomDAO).count();
    }

    @Test
    void calculateTotalPrice_shouldReturnValueFromDAO() {
        Price expectedPrice = new Price(new BigDecimal("150.00"));
        when(mockRoomDAO.calculateTotalPrice()).thenReturn(expectedPrice);

        Price result = roomServiceWithMocks.calculateTotalPrice();

        assertEquals(expectedPrice, result);
        verify(mockRoomDAO).calculateTotalPrice();
    }
}
