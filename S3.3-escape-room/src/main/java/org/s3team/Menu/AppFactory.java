package org.s3team.Menu;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.Player.DAO.PlayerDAO;
import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Service.PlayerService;
import org.s3team.Player.menu.PlayerManagementMenu;
import org.s3team.certificate.dao.CertificateDao;
import org.s3team.certificate.dao.CertificateDaoImpl;
import org.s3team.certificate.menu.CertificateMenu;
import org.s3team.certificate.service.CertificateService;
import org.s3team.clue.dao.ClueDao;
import org.s3team.clue.dao.ClueDaoImpl;
import org.s3team.clue.service.ClueService;
import org.s3team.decoration.dao.DecorationDao;
import org.s3team.decoration.dao.DecorationDaoImpl;
import org.s3team.decoration.service.DecorationService;
import org.s3team.inventoryMenu.*;
import org.s3team.inventoryService.InventoryManagementService;
import org.s3team.inventoryService.InventoryQueryService;
import org.s3team.notification.NotificationManagementMenu;
import org.s3team.notification.SendNotificationService;
import org.s3team.playercertificate.dao.PlayerCertificateDao;
import org.s3team.playercertificate.dao.PlayerCertificateDaoImpl;
import org.s3team.playercertificate.service.PlayerCertificateService;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.room.Service.RoomService;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.dao.ThemeDaoImpl;
import org.s3team.theme.service.ThemeService;
import org.s3team.ticket.dao.TicketDao;
import org.s3team.ticket.dao.TicketDaoImpl;
import org.s3team.ticket.menu.TicketMenu;
import org.s3team.ticket.service.TicketService;
import org.s3team.theme.themeMenu.ThemeMenu;

/**
 * ClassName: AppFactory
 * Package: org.s3team.Menu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 23:03
 * Version:v1.0
 */
public class AppFactory {
    private final Data_Base_Connection db;

    private final RoomDAO roomDAO;
    private final ThemeDao themeDao;
    private final ClueDao clueDao;
    private final PlayerDAO playerDAO;
    private final TicketDao ticketDao;
    private final CertificateDao certificateDao;
    private final PlayerCertificateDao playerCertificateDao;
    private final DecorationDao decorationDao;
    private final ThemeService themeService;
    private final DecorationService decorationService;
    private final ClueService clueService;
    private final RoomService roomService;
    private final InventoryManagementService inventoryManagementService;
    private final InventoryQueryService inventoryQueryService;
    private final CertificateService certificateService;
    private final PlayerService playerService;
    private final PlayerCertificateService playerCertificateService;
    private final TicketService ticketService;

    public AppFactory(Data_Base_Connection db) {

        this.db = db;

        this.roomDAO = new RoomDAOImp(db);
        this.themeDao = new ThemeDaoImpl(db);
        this.playerDAO = new PlayerDAOImp(db);
        this.decorationDao = new DecorationDaoImpl(db);
        this.clueDao = new ClueDaoImpl(db);
        this.ticketDao = new TicketDaoImpl(db);
        this.certificateDao = new CertificateDaoImpl(db);
        this.playerCertificateDao = new PlayerCertificateDaoImpl(db);
        this.themeService = new ThemeService(themeDao);
        this.decorationService = new DecorationService(decorationDao,roomDAO);
        this.clueService = new ClueService(clueDao, roomDAO, themeDao);
        this.roomService = new RoomService(roomDAO, themeDao);
        this.inventoryManagementService = new InventoryManagementService(clueService, decorationService, roomService);
        this.inventoryQueryService = new InventoryQueryService(clueService, decorationService, roomService);
        this.certificateService = new CertificateService(certificateDao);
        this.playerService = new PlayerService(new PlayerDAOImp(db));
        this.playerCertificateService = new PlayerCertificateService(playerCertificateDao, certificateDao, playerDAO, roomDAO);
        this.ticketService = new TicketService(ticketDao, roomDAO, playerDAO);
    }

    public InventoryMenu inventoryMenuGenerate() {
        AddItemMenu addItemMenu = new AddItemMenu(inventoryManagementService);
        RemoveItemMenu removeItemMenu = new RemoveItemMenu(inventoryManagementService);
        UpdateItemMenu updateItemMenu = new UpdateItemMenu(inventoryManagementService);
        DisplayInventoryMenu displayInventoryMenu = new DisplayInventoryMenu(inventoryQueryService);
        DisplayInventoryValueMenu displayInventoryValueMenu = new DisplayInventoryValueMenu(inventoryQueryService);
        DisplayInventoryQuantityMenu displayInventoryQuantityMenu = new DisplayInventoryQuantityMenu(inventoryQueryService);
        return new InventoryMenu(addItemMenu, updateItemMenu, removeItemMenu, displayInventoryMenu, displayInventoryValueMenu, displayInventoryQuantityMenu);
    }

    public CertificateMenu certificateMenuGenerate() {
        return new CertificateMenu(certificateService, playerCertificateService, playerService, roomService);
    }

    public TicketMenu ticketMenuGenerate() {
        return new TicketMenu(ticketService);
    }


    public PlayerManagementMenu playerMenuGenerate() {
        return new PlayerManagementMenu(playerService);
    }

    public NotificationManagementMenu notificationMenuGenerate() {
        SendNotificationService sendNotificationService = new SendNotificationService();
        return new NotificationManagementMenu(sendNotificationService, playerService);
    }

    public ThemeMenu themeMenuGenerate() {
        return  new ThemeMenu(themeService);
    }

}