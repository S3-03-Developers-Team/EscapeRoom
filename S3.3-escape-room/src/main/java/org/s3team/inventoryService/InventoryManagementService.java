package org.s3team.inventoryService;

import org.s3team.clue.model.Clue;
import org.s3team.clue.model.ClueDescription;
import org.s3team.clue.model.ClueType;
import org.s3team.clue.service.ClueService;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.decoration.model.Decoration;
import org.s3team.decoration.service.DecorationService;
import org.s3team.room.Service.RoomService;
import org.s3team.room.model.Room;
import org.s3team.theme.model.Theme;

/**
 * ClassName: InventoryManagementService
 * Package: org.s3team.inventoryService
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 21:23
 * Version:v1.0
 *
 */
public class




InventoryManagementService {
    private ClueService clueService;
    private DecorationService decorationService;
    private RoomService roomService;

    public InventoryManagementService(ClueService clueService, DecorationService decorationService, RoomService roomService) {
        this.clueService = clueService;
        this.decorationService = decorationService;
        this.roomService = roomService;
    }

    public Room addRoom(Room room) {
        return roomService.save(room);
    }

    public Boolean updateRoom(Room room) {
        return roomService.update(room);
    }

    public boolean deleteRoom(Id<Room> id) {
        return roomService.delete(id);
    }

    public Clue addClue(ClueType type, ClueDescription description, Price price,
                        Id<Theme> themeId, Id<Room> roomId) {
        return clueService.createClue(type, description, price, themeId, roomId);
    }

    public Boolean updateClue(Clue clue) {
        return clueService.updateClue(clue);
    }

    public boolean deleteClue(Id<Clue> id) {
        return clueService.deleteClue(id);
    }

    public void addDecoration(Decoration decoration) {
        decorationService.create(decoration);
    }

    public Boolean updateDecoration(Decoration decoration) {
        return decorationService.update(decoration);
    }

    public boolean deleteDecoration(Id<Decoration> id) {
        return decorationService.delete(id);
    }

}
