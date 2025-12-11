package org.s3team.room.Service;

import org.s3team.common.valueobject.*;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.room.model.Room;

import java.util.List;
import java.util.Optional;

public class RoomService {
    private RoomDAOImp roomDAOImp;

    public RoomService(RoomDAOImp roomDAOImp) {
        this.roomDAOImp = roomDAOImp;
    }

    public Room save(Room room) {
        return roomDAOImp.save(room);

    }

    public Room findById(Id id) {
        Optional<Room> roomOptional = roomDAOImp.findById(id);

        return roomOptional.orElseThrow(
                () -> new RuntimeException("Room with ID " + id.value() + " not found.")
        );
    }

    public List findAll() {
        return roomDAOImp.findAll();
    }

    public boolean update(Room room) {
        if (roomDAOImp.findById(room.getRoomId()).isEmpty()) {
            return false;
        }
        return roomDAOImp.update(room);
    }

    public boolean delete(Id id) {
        return roomDAOImp.delete(id);
    }

    public int count() {
        return roomDAOImp.count();

    }

    public Price calculateTotalPrice() {
        return roomDAOImp.calculateTotalPrice();
    }

}
