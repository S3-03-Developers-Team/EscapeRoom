package org.s3team.inventoryService;

import org.s3team.Exceptions.ClueNotFoundException;
import org.s3team.clue.model.Clue;
import org.s3team.clue.service.ClueService;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.decoration.model.Decoration;
import org.s3team.decoration.service.DecorationService;
import org.s3team.room.Service.RoomService;
import org.s3team.room.model.Room;
import java.util.List;

public class InventoryQueryService {
    private ClueService clueService;
    private DecorationService decorationService;
    private RoomService roomService;

    public InventoryQueryService(ClueService clueService, DecorationService decorationService, RoomService roomService) {
        this.clueService = clueService;
        this.decorationService = decorationService;
        this.roomService = roomService;
    }


    public Room findRoomById(Id<Room> id) {
        return roomService.findById(id);
    }

    public List<Room> listRooms() {
        return roomService.findAll();
    }


    public int countRooms() {
        return roomService.count();
    }

    public Price calculateRoomTotalPrice() {
        return roomService.calculateTotalPrice();
    }


    public Clue getClueById(Id<Clue> id) {
        return clueService.getClueById(id).orElseThrow(() ->
                new ClueNotFoundException("Clue with ID " + id.value() + " not found.")
        );
    }

    public List<Clue> listClues() {
        return clueService.getAllClues();
    }


    public int countClues() {
        return clueService.count();
    }

    public Price calculateClueTotalPrice() {
        return clueService.calculateTotalPrice();
    }


    public Decoration findDecorationById(Id<Decoration> id) {
        return decorationService.findDecorationById();
    }

    public List<Decoration> listDecorations() {
        return decorationService.getAllDecorations();
    }


    public int countDecorations() {
        return decorationService.count();
    }

    public Price calculateDecorationTotalPrice() {
        return decorationService.calculateTotalPrice();
    }

}
