package org.s3team.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.s3team.DataBaseConnection.TestConnection;
import org.s3team.Exceptions.ThemeNotFoundException;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.room.Service.RoomService;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.dao.ThemeDaoImpl;
import org.s3team.theme.model.Theme;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ClassName: RoomServiceSaveTest
 * Package: org.s3team.room
 * Description:
 * Author: Rong Jiang
 * Create:16/12/2025 - 23:43
 * Version:v1.0
 *
 */
public class RoomServiceSaveTest {

    // ==============================
    // 2️⃣ 真实数据库测试 save 方法
    // ==============================
    private RoomDAO realRoomDAO;
    private ThemeDao realThemeDao;
    private RoomService roomServiceReal;

    @BeforeEach
    void setup() {

        realRoomDAO = new RoomDAOImp(TestConnection.getInstance());
        realThemeDao = new ThemeDaoImpl(TestConnection.getInstance()) {
            @Override
            public Optional<Theme> findById(Id<Theme> id) {
                return Optional.of(Theme.rehydrate(id, new Name("TestTheme")));
            }
        };
        roomServiceReal = new RoomService(realRoomDAO, realThemeDao) {
            @Override
            public void generateNotification(String message) {
                // do nothing
            }
        };
    }

    @Test
    void save_shouldPersistRoom_whenThemeExists_real() {
        Room room = Room.createNew(
                new Name("Real Room"),
                Difficulty.EASY,
                new Price(new BigDecimal("100.00")),
                new Id<>(1)
        );

        Room saved = roomServiceReal.save(room);

        assertNotNull(saved.getRoomId());

        Room found = roomServiceReal.findById(saved.getRoomId());
        assertEquals(saved, found);
    }

    @Test
    void save_shouldThrowThemeNotFoundException_whenThemeDoesNotExist_real() {
        Room room = Room.createNew(
                new Name("Room Without Theme"),
                Difficulty.MEDIUM,
                new Price(new BigDecimal("150.00")),
                new Id<>(99)
        );

        RoomService roomServiceWithFakeTheme = new RoomService(realRoomDAO, new ThemeDaoImpl(TestConnection.getInstance()) {
            @Override
            public Optional<Theme> findById(Id<Theme> id) {
                return Optional.empty();
            }
        }) {
            @Override
            public void generateNotification(String message) {
            }
        };

        assertThrows(ThemeNotFoundException.class, () -> roomServiceWithFakeTheme.save(room));
    }
}
