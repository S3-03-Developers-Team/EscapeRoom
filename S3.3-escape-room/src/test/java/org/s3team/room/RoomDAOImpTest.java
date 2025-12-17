package org.s3team.room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.DataBaseConnection.TestConnection;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;
import org.s3team.theme.model.Theme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * ClassName: TestRoom
 * Package: org.s3team.room
 * Description:
 * Author: Rong Jiang
 * Create:16/12/2025 - 20:16
 * Version:v1.0
 *
 */
public class RoomDAOImpTest {

    private RoomDAOImp roomDAOImp;

    @BeforeEach
    public void setup() {
        Data_Base_Connection db = TestConnection.getInstance();
        roomDAOImp = new RoomDAOImp(db);
    }

    @Test
    public void saveShouldPersistRoom_findByIdShouldReturnRoom(){
        Room room = Room.createNew(
                new Name("Test Room"),
                Difficulty.EASY,
                new Price(new BigDecimal("50.00")),
                new Id<Theme>(1)

        );
        Room roomSaved = roomDAOImp.save(room);
        Optional<Room> roomFound = roomDAOImp.findById(roomSaved.getRoomId());
        assertTrue(roomFound.isPresent());
        assertEquals(roomSaved,roomFound.get());
    }

    @Test
    void findById_shouldReturnEmpty_whenNotFound() {
        Optional<Room> roomFound = roomDAOImp.findById(new Id<Room>(9999));
        assertFalse(roomFound.isPresent());
    }

    @Test
    void findAll_shouldReturnList() {
        Room room = Room.createNew(
                new Name("Another Room"),
                Difficulty.MEDIUM,
                new Price(new BigDecimal("80.00")),
                new Id<>(1)
        );
        roomDAOImp.save(room);

        assertFalse(roomDAOImp.findAll().isEmpty());
    }

    @Test
    void update_shouldModifyRoom() {
        Room room = Room.createNew(
                new Name("Update Room"),
                Difficulty.EASY,
                new Price(new BigDecimal("60.00")),
                new Id<>(1)
        );
        Room saved = roomDAOImp.save(room);

        // 修改属性
        Room updatedRoom = Room.rehydrate(saved.getRoomId(),
                new Name("Updated Name"),
                Difficulty.HARD,
                new Price(new BigDecimal("100.00")),
                new Id<>(1));

        boolean success = roomDAOImp.update(updatedRoom);
        assertTrue(success);


        Optional<Room> fromDb = roomDAOImp.findById(saved.getRoomId());
        assertTrue(fromDb.isPresent());

        Room roomFromDb = fromDb.get();
        assertEquals(updatedRoom,roomFromDb);
    }

    @Test
    void delete_shouldRemoveRoom() {
        Room room = Room.createNew(
                new Name("Delete Room"),
                Difficulty.EASY,
                new Price(new BigDecimal("40.00")),
                new Id<>(1)
        );
        Room saved = roomDAOImp.save(room);

        boolean deleted = roomDAOImp.delete(saved.getRoomId());
        assertTrue(deleted);

        Optional<Room> fromDb = roomDAOImp.findById(saved.getRoomId());
        assertFalse(fromDb.isPresent());
    }

    @Test
    void count_shouldReturnNumberOfRooms() {
        int initialCount = roomDAOImp.count();

        Room room = Room.createNew(
                new Name("Count Room"),
                Difficulty.EASY,
                new Price(new BigDecimal("70.00")),
                new Id<>(1)
        );
        roomDAOImp.save(room);

        assertEquals(initialCount + 1, roomDAOImp.count());
    }

    @Test
    void calculateTotalPrice_shouldSumRoomPrices() {
        Room room1 = Room.createNew(
                new Name("Price1"),
                Difficulty.EASY,
                new Price(new BigDecimal("10.00")),
                new Id<>(1)
        );
        Room room2 = Room.createNew(
                new Name("Price2"),
                Difficulty.MEDIUM,
                new Price(new BigDecimal("20.00")),
                new Id<>(1)
        );
        roomDAOImp.save(room1);
        roomDAOImp.save(room2);

        assertEquals(new BigDecimal("30.00"), roomDAOImp.calculateTotalPrice().value());
    }
}
