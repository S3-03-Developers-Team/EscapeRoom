package org.s3team.certificate.menu;

import org.s3team.Player.Model.Player;
import org.s3team.Player.Service.PlayerService;
import org.s3team.certificate.model.Certificate;
import org.s3team.certificate.model.CertificateType;
import org.s3team.certificate.model.Reward;
import org.s3team.certificate.service.CertificateService;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;
import org.s3team.playercertificate.dto.PlayerCertificateInfo;
import org.s3team.playercertificate.service.PlayerCertificateService;
import org.s3team.room.Service.RoomService;
import org.s3team.room.model.Room;

import java.util.List;

public class CertificateMenu {

    private final CertificateService certificateService;
    private final PlayerCertificateService playerCertificateService;
    private final PlayerService playerService;
    private final RoomService roomService;

    public CertificateMenu(CertificateService certificateService, PlayerCertificateService playerCertificateService, PlayerService playerService, RoomService roomService){
        this.certificateService = certificateService;
        this.playerCertificateService = playerCertificateService;
        this.playerService = playerService;
        this.roomService = roomService;
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n--- CERTIFICATE ADMIN MENU ---");
            System.out.println("1. Create new certificate");
            System.out.println("2. Assign certificate to player (by Room)");
            System.out.println("3. List certificates by player");
            System.out.println("4. List certificates by room");
            System.out.println("5. List all issued certificates");
            System.out.println("0. Exit");

            option = ConsoleInput.readInt("Select an option: ");

            switch (option) {
                case 1 -> createCertificate();
                case 2 -> assignCertificate();
                case 3 -> listByPlayer();
                case 4 -> listByRoom();
                case 5 -> listAllIssued();
                case 0 -> System.out.println("Exiting Certificate Menu");
                default -> System.out.println("Invalid option, please select a valid number");
            }
        } while (option != 0);
    }


    private void createCertificate() {
        CertificateType type = selectCertificateType();
        String rewardInput = ConsoleInput.readLine("Reward (optional, please Enter to skip): ");

        Reward reward = rewardInput.isBlank() ? Reward.NONE : new Reward(rewardInput);
        certificateService.createCertificate(
                type,
                reward
        );

        System.out.println("Certificate created successfully");
    }

    private void assignCertificate() {
        try {
            Id<Player> playerId = selectPlayer();
            Id<Certificate> certificateId = selectCertificate();
            Id<Room> roomId = selectRoom();

            if (roomId == null || playerId == null || certificateId == null) { System.out.println("Operation cancelled."); return; }

            playerCertificateService.assignCertificate(playerId,certificateId,roomId);

            System.out.println("Certificate assigned successfully.");

        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listByPlayer() {
        Id<Player> playerId = selectPlayer();

        if (playerId == null){
            System.out.println("No players available.");
            return;
        }

        List<PlayerCertificateInfo> list = playerCertificateService.getCertificatesForPlayerWithInfo(playerId);

        if (list.isEmpty()) {
            System.out.println("No certificates found for this player");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void listByRoom() {
        Id<Room> roomId = selectRoom();

        if (roomId == null){
            System.out.println("No rooms available.");
            return;
        }

        List<PlayerCertificateInfo> list = playerCertificateService.getCertificatesForRoomWithInfo(roomId);

        if (list.isEmpty()) {
            System.out.println("No certificates found for this room");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void listAllIssued() {
        List<PlayerCertificateInfo> list = playerCertificateService.getAllCertificatesWithInfo();

        if (list.isEmpty()) {
            System.out.println("No certificates issued yet.");
        } else {
            list.forEach(System.out::println);
        }
    }


    //-------------HELPERS

    private Id<Player> selectPlayer() {
        List<Player> players = playerService.findAll();

        if (players.isEmpty()) {
            System.out.println("No players available.");
            return null;
        }

        players.forEach(System.out::println);

        while(true) {
            int input = ConsoleInput.readInt("Select Player ID: ");
            if (players.stream().anyMatch(player -> player.getId().value() == input)) {
                return new Id<>(input);
            }
            System.out.println("Invalid ID, please select again.");
        }
    }

    private Id<Certificate> selectCertificate() {
        List<Certificate> certificates = certificateService.getAllCertificates();

        if (certificates.isEmpty()) {
            System.out.println("No certificates available.");
            return null;
        }

        certificates.forEach(System.out::println);

        while(true) {
            int input = ConsoleInput.readInt("Select Certificate ID: ");
            if (certificates.stream().anyMatch(certificate -> certificate.getId().value() == input)) {
                return new Id<>(input);
            }
            System.out.println("Invalid ID, please select again.");
        }
    }

    private Id<Room> selectRoom() {
        List<Room> rooms = roomService.findAll();

        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
            return null;
        }

        rooms.forEach(System.out::println);

        while(true) {
            int input = ConsoleInput.readInt("Select Room ID: ");
            if (rooms.stream().anyMatch(room -> room.getRoomId().value() == input)) {
                return new Id<>(input);
            }
            System.out.println("Invalid ID, please select again.");
        }
    }

    private CertificateType selectCertificateType() {
        CertificateType[] types = CertificateType.values();
        int index = 1;
        for (CertificateType type: CertificateType.values()) {
            System.out.println(index + ". " + type);
            index++;
        }

        while(true) {
            int choice = ConsoleInput.readInt("Select certificate type: ");
            if (choice >= 1 && choice <= types.length) {
                return types[choice - 1];
            }
            System.out.println("Invalid selection, please try again.");
        }
    }
}
