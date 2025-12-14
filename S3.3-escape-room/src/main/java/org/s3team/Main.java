package org.s3team;

import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.Menu.MainMenuController;
import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Model.Email;
import org.s3team.Player.Model.Player;
import org.s3team.Player.Service.PlayerService;
import org.s3team.common.valueobject.Name;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- S3.3 Escape Room Application Starting ---");

        // 1. Intentar obtener la instancia Singleton y abrir la conexión
        try {
            // El método getInstance() intentará conectar a MySQL.
            // Si falla, lanzará una RuntimeException y el programa terminará.
            MySQL_Data_Base_Connection dbInstance = MySQL_Data_Base_Connection.getInstance();

            // 2. Si llegamos aquí, la conexión fue exitosa.
            Connection conn = dbInstance.getConnection();

            System.out.println("Database Connection Status: SUCCESS!");
            System.out.println("JDBC Connection Object: " + conn);

            // Aquí iría el resto de la lógica de tu aplicación


        } catch (RuntimeException e) {
            System.err.println("FATAL ERROR: Application failed to initialize due to connection failure.");
            System.err.println("Check DB container status, network, and credentials.");
            e.printStackTrace();
            // Salida con código de error para que Docker sepa que falló el inicio
            System.exit(1);
        }

//        MainMenuController startApp = new MainMenuController();
//        startApp.startApplication();
//        Player player1 = Player.create(new Name("Rafa"),new Email("rafa@gmail.com"), false );
//        Player player2 = Player.create(new Name("Juan"),new Email("juan@gmail.com"), true );
//        PlayerDAOImp playerdao = new PlayerDAOImp();
//        PlayerService playerService = new PlayerService(playerdao);
//        Player player1Saved = playerService.save(player1);
//        System.out.println("Player 1: "+player1Saved.toString());
//        Player player2Saved = playerService.save(player2);
//        System.out.println("Player 2: "+player2Saved.toString());
//        System.out.println("First final all:"+playerService.findAll());
//
//        System.out.println("Method findById result for player 1: "+playerService.findById(player1Saved.getId()));
//        Player player2SavedUpdate = Player.rehydrate(player2Saved.getId(), player2Saved.getName(), new Email("lolo@gmail.com"), player2Saved.isSubscribed() );
//        playerService.update(player2SavedUpdate);
//        System.out.println("Method update for player 2:"+player2SavedUpdate.toString());
//        playerService.delete(player1Saved.getId());
//        System.out.println("Second findAll after deleited player 1: "+playerService.findAll());
//        System.out.println("Find player 2 by Email: "+playerService.findByEmail("lolo@gmail.com"));
//        System.out.println("Find player 2 by name: "+playerService.findByName("Juan"));



    }
}