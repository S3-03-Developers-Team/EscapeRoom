package org.s3team.ticket.menu;

import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Price;
import org.s3team.ticket.model.Ticket;
import org.s3team.ticket.service.TicketService;
import java.math.BigDecimal;
import java.util.List;

public class TicketMenu {

    private final TicketService ticketService;

    public TicketMenu(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public void start() {
        int option;

        do {
            showMenu();
            option = ConsoleInput.readInt("Select an option: ");

            switch (option) {
                case 1 -> createTicket();
                case 2 -> listTickets();
                case 3 -> findTicketById();
                case 4 -> updateTicket();
                case 5 -> deleteTicket();
                case 0 -> System.out.println("Exiting ticket panel...");
                default -> System.out.println("Invalid option");
            }
        } while (option != 0);
    }

    private void showMenu() {
        System.out.println("\n--- ADMIN TICKET PANEL ---");
        System.out.println("1. Create Ticket");
        System.out.println("2. List Tickets");
        System.out.println("3. Find Ticket by ID");
        System.out.println("4. Update Ticket");
        System.out.println("5. Delete Ticket");
        System.out.println("0. Exit");
    }

    private void createTicket() {
        try {
            BigDecimal total = ConsoleInput.readBigDecimal("Total price: ");
            int playerId = ConsoleInput.readPositiveInt("Player ID: ");
            int roomId = ConsoleInput.readPositiveInt("Room ID: ");

            Ticket ticket = ticketService.createTicket(
                    new Price(total),
                    new Id<>(playerId),
                    new Id<>(roomId)
            );

            System.out.println("Ticket created successfully:");
            System.out.println(ticket);

        } catch (Exception e) {
            System.out.println("Error creating ticket: " + e.getMessage());
        }
    }

    private void listTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();

        if (tickets.isEmpty()) {
            System.out.println("No tickets found.");
        } else {
            tickets.forEach(System.out::println);
        }
    }

    private void findTicketById() {
        int id = ConsoleInput.readPositiveInt("Ticket ID to find: ");

        ticketService.getTicketById(new Id<>(id))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Ticket not found")
                );
    }

    private void updateTicket() {
        try {
            int id = ConsoleInput.readPositiveInt("Ticket ID to update: ");
            BigDecimal total = ConsoleInput.readBigDecimal("New total price: ");
            int playerId = ConsoleInput.readPositiveInt("New Player ID: ");
            int roomId = ConsoleInput.readPositiveInt("New Room ID: ");

            Ticket ticket = Ticket.rehydrate(
                    new Id<>(id),
                    null,
                    new Price(total),
                    new Id<>(playerId),
                    new Id<>(roomId)
            );

            boolean updated = ticketService.updateTicket(ticket);

            if (updated) {
                System.out.println("Ticket updated successfully");
            } else {
                System.out.println("Ticket not found");
            }

        } catch (Exception e) {
            System.out.println("Error updating ticket: " + e.getMessage());
        }
    }

    private void deleteTicket() {
        int id = ConsoleInput.readPositiveInt("Ticket ID to delete: ");

        boolean deleted = ticketService.deleteTicket(new Id<>(id));

        if (deleted) {
            System.out.println("Ticket deleted successfully");
        } else {
            System.out.println("Ticket not found");
        }
    }
}
