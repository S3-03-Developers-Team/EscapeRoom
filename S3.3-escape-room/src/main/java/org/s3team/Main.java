package org.s3team;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.DataBaseConnection.MySQL_Data_Base_Connection;
import org.s3team.Menu.MainMenuController;
import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Model.Email;
import org.s3team.Player.Model.Player;
import org.s3team.Player.Service.PlayerService;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.common.valueobject.Price;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.room.Service.RoomService;
import org.s3team.room.model.Difficulty;
import org.s3team.room.model.Room;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.dao.ThemeDaoImpl;
import org.s3team.theme.model.Theme;
import org.s3team.theme.service.ThemeService;

import java.math.BigDecimal;
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
        Data_Base_Connection newConnection  = MySQL_Data_Base_Connection.getInstance();
        Player player1 = Player.create(new Name("Susanitaa"),new Email("susaanitaa@gmail.com"), true );
        PlayerDAOImp playerdao = new PlayerDAOImp(newConnection);
        PlayerService playerService = new PlayerService(playerdao);
        Player player1Saved = playerService.save(player1);
        System.out.println("Player 1: "+player1Saved.toString());


        RoomDAOImp roomDAOImp  = new RoomDAOImp(newConnection);
        ThemeDao themeDao = new ThemeDaoImpl(newConnection);
        ThemeService themeService = new ThemeService(themeDao);
        Theme fancy = themeService.createTheme(new Name("jungla"));
        RoomService roomService= new RoomService(roomDAOImp, themeDao);
        Room roomFancy = Room.createNew(new Name("jungla"), Difficulty.EASY, new Price(BigDecimal.valueOf(87.9)), fancy.getId());
        Room fancyRoom = roomService.save(roomFancy);



    }
}