package org.s3team.service;

import org.s3team.common.dao.DecorationDao;
import org.s3team.common.valueobject.Decoration;
import org.s3team.common.valueobject.Material;

import java.util.List;

public class DecorationService {

    private final DecorationDao decorationDao;

    public DecorationService() {
        this.decorationDao = new DecorationDao();
    }

    public void createDecoration(Decoration newDecoration) {

        int targetRoomId = newDecoration.getRoomId();
        List<Decoration> existingDecorations = decorationDao.findByRoomId(targetRoomId);

        if (existingDecorations.isEmpty()) {
            decorationDao.save(newDecoration);
            System.out.println("SUCCESS: First item added. Room " + targetRoomId + " is now a " + newDecoration.getMaterial() + " room.");

        } else {

            Material existingMaterial = existingDecorations.get(0).getMaterial();
            Material newMaterial = newDecoration.getMaterial();

            if (existingMaterial == newMaterial) {
                decorationDao.save(newDecoration);
                System.out.println("SUCCESS: Material matches (" + existingMaterial + "). Saved.");
            } else {
                String errorMessage = "RULE VIOLATION: Room " + targetRoomId +
                        " is a " + existingMaterial + " room. " +
                        "Cannot add " + newMaterial + " item.";

                throw new IllegalArgumentException(errorMessage);
            }
        }
    }

    public List<Decoration> getAllDecorations() {
        return decorationDao.findAll();
    }
}
