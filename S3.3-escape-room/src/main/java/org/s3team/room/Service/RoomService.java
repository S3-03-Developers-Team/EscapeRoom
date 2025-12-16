package org.s3team.room.Service;

import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.Exceptions.ThemeNotFoundException;
import org.s3team.common.valueobject.*;
import org.s3team.notification.NotificableEvent;
import org.s3team.notification.SendNotificationService;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.model.Room;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.model.Theme;

import java.util.List;
import java.util.Optional;

public class RoomService implements NotificableEvent {
    private final RoomDAO roomDAO;
    private final ThemeDao themeDao;

    public RoomService(RoomDAO roomDAO, ThemeDao themeDao) {
        this.roomDAO = roomDAO;
        this.themeDao = themeDao;
    }

    public Room save(Room room) {
        Id<Theme> themeId = room.getThemeId();
        themeDao.findById(themeId).orElseThrow(() ->
                new ThemeNotFoundException(themeId)
        );
        generateNotification("A new room has been created: "+room.getName());
        return roomDAO.save(room);
    }

    public Room findById(Id<Room> id) {
        Optional<Room> roomOptional = roomDAO.findById(id);

        return roomOptional.orElseThrow(
                () -> new RoomNotFoundException(id)
        );
    }

    public List<Room> findAll() {
        return roomDAO.findAll();
    }

    public boolean update(Room room) {

        roomDAO.findById(room.getRoomId()).orElseThrow(
                () -> new RoomNotFoundException(room.getRoomId())
        );

        Id<Theme> themeId = room.getThemeId();
        themeDao.findById(themeId).orElseThrow(() ->
                new ThemeNotFoundException(themeId)
        );

        return roomDAO.update(room);
    }

    public boolean delete(Id<Room> id) {

        roomDAO.findById(id).orElseThrow(
                () -> new RoomNotFoundException(id)
        );

        return roomDAO.delete(id);
    }

    public int count() {
        return roomDAO.count();
    }

    public Price calculateTotalPrice() {
        return roomDAO.calculateTotalPrice();
    }

    @Override
    public void generateNotification(String message) {
        SendNotificationService newNotification = new SendNotificationService();
        newNotification.sendNotificationToSubscribers(message);
    }
}