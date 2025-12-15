package org.s3team.decoration.service;

import org.s3team.Exceptions.DecorationNotFoundException;
import org.s3team.Exceptions.RoomNotFoundException;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.decoration.dao.DecorationDao;
import org.s3team.decoration.dao.DecorationDaoImpl;
import org.s3team.decoration.model.Decoration;
import org.s3team.decoration.model.Material;
import org.s3team.notification.NotificableEvent;
import org.s3team.notification.SendNotificationService;
import org.s3team.room.DAO.RoomDAO;

import java.util.List;
import java.util.Optional;

public class DecorationService implements NotificableEvent {

    private final DecorationDao decorationDao;
    private final RoomDAO roomDAO;


    public DecorationService(DecorationDao decorationDao, RoomDAO roomDAO) {
        this.decorationDao = decorationDao;
        this.roomDAO = roomDAO;
    }



        public Decoration create(Decoration decoration) {

            roomDAO.findById(decoration.getRoomId())
                    .orElseThrow(() -> new RoomNotFoundException(decoration.getRoomId()));

            Decoration savedDecoration = decorationDao.save(decoration);

            generateNotification("A new object has been created: " + decoration.getName());

            return savedDecoration;
        }

    public List<Decoration> getAllDecorations() {
        return decorationDao.findAll();
    }
    public Decoration findById(Id<Decoration> id) {
        if (id == null) {
            throw new IllegalArgumentException("Decoration ID cannot be null.");
        }

        Optional<Decoration> decorationOptional = decorationDao.findById(id);

        return decorationOptional.orElseThrow(() ->
                new DecorationNotFoundException("未找到 ID 为 " + id.value() + " 的装饰品。")
        );
    }

    public boolean update(Decoration decoration) {
        Id<Decoration> decorationId = decoration.getDecorationId();

        if (decorationId == null || decorationId.value() <= 0) {
            throw new IllegalArgumentException("A valid Decoration ID must be provided for the update operation.");
        }

        // Existence check: findById will throw DecorationNotFoundException if not found.
        findById(decorationId);

        boolean success = decorationDao.update(decoration);

        if (success) {
            generateNotification("Decoration ID " + decorationId.value() + " has been updated.");
        }

        return success;
    }

    public boolean delete(Id<Decoration> id) {

        if (id == null || id.value() <= 0) {
            throw new IllegalArgumentException("A valid Decoration ID must be provided for the delete operation.");
        }
        findById(id);
        boolean success = decorationDao.delete(id);
        if (success) {
            System.out.println("INFO: Decoration ID " + id.value() + " has been successfully deleted.");
        }
        return success;
    }

    public int count() {
        return decorationDao.count();
    }

    public Price calculateTotalPrice() {
        return decorationDao.calculateTotalPrice();
    }

    @Override
    public void generateNotification(String message) {
        SendNotificationService newNotification = new SendNotificationService();
        newNotification.sendNotificationToSubscribers(message);
    }
}
