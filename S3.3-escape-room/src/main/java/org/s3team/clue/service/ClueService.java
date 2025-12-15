package org.s3team.clue.service;

import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.clue.dao.ClueDao;
import org.s3team.clue.dao.ClueDaoImpl;
import org.s3team.clue.model.Clue;
import org.s3team.clue.model.ClueDescription;
import org.s3team.clue.model.ClueType;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.notification.NotificableEvent;
import org.s3team.notification.SendNotificationService;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.model.Room;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.model.Theme;

import java.util.List;
import java.util.Optional;

public class ClueService implements NotificableEvent {

    private final ClueDao clueDao;
    private final RoomDAO roomDao;
    private final ThemeDao themeDao;

    public ClueService(ClueDao clueDao, RoomDAO roomDao, ThemeDao themeDao){
        this.clueDao = clueDao;
        this.roomDao = roomDao;
        this.themeDao = themeDao;
    }

    public Clue createClue(ClueType type, ClueDescription description, Price price,
                           Id<Theme> themeId, Id<Room> roomId) {

        roomDao.findById(roomId).orElseThrow(() ->
                new RoomNotFoundException(roomId)
        );
        themeDao.getById(themeId);

        Clue clue = Clue.createNew(type, description, price, themeId, roomId);
        generateNotification("A new clue has been created: "+clue.getDescription());

        return clueDao.save(clue);
    }

    public Optional<Clue> getClueById(Id<Clue> id) {
        return clueDao.findById(id);
    }

    public List<Clue> getAllClues() {
        return clueDao.findAll();
    }

    public boolean updateClue(Clue clue) {
        roomDao.findById(clue.getRoomId()).orElseThrow(() ->
                new RoomNotFoundException(clue.getRoomId())
        );
        themeDao.getById(clue.getThemeId());

        return clueDao.update(clue);
    }

    public boolean deleteClue(Id<Clue> id) {
        return clueDao.delete(id);
    }

    public int count() {
        return clueDao.count();
    }

    public Price calculateTotalPrice() {
        return clueDao.calculateTotalPrice();
    }

    @Override
    public void generateNotification(String message) {
        SendNotificationService newNotification = new SendNotificationService();
        newNotification.sendNotificationToSubscribers(message);
    }
}