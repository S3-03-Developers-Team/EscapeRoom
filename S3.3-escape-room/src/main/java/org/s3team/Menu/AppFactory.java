package org.s3team.Menu;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.Player.DAO.PlayerDAO;
import org.s3team.Player.DAO.PlayerDAOImp;
import org.s3team.Player.Service.PlayerService;
import org.s3team.certificate.dao.CertificateDao;
import org.s3team.certificate.dao.CertificateDaoImpl;
import org.s3team.certificate.menu.CertificateMenu;
import org.s3team.certificate.dao.CertificateDaoImpl;
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
import org.s3team.playercertificate.dao.PlayerCertificateDao;
import org.s3team.playercertificate.dao.PlayerCertificateDaoImpl;
import org.s3team.playercertificate.service.PlayerCertificateService;

import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.room.Service.RoomService;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.dao.ThemeDaoImpl;
import org.s3team.theme.service.ThemeService;
import org.s3team.themeMenu.ThemeMenu;

/**
 * ClassName: AppFactory
 * Package: org.s3team.Menu
 * Description:
 * Author: Rong Jiang
 * Create:12/12/2025 - 23:03
 * Version:v1.0
 *
 */
public class AppFactory {
    private final Data_Base_Connection db;

    public AppFactory(Data_Base_Connection db) {
        this.db = db;
    }

    public InventoryMenu inventoryMenuGenerate() {
        RoomDAO roomDAO = new RoomDAOImp(db);
        DecorationDao decorationDao = new DecorationDaoImpl();
        ClueDao clueDao = new ClueDaoImpl(db);
        ThemeDao themeDao = new ThemeDaoImpl(db);
        DecorationService decorationService = new DecorationService(decorationDao,roomDAO);
        ClueService clueService = new ClueService(clueDao,roomDAO,themeDao);
        RoomService roomService = new RoomService(roomDAO,themeDao);
        InventoryManagementService inventoryManagementService = new InventoryManagementService(clueService,decorationService,roomService);
        InventoryQueryService inventoryQueryService = new InventoryQueryService(clueService,decorationService,roomService);
        AddItemMenu addItemMenu = new AddItemMenu(inventoryManagementService);
        RemoveItemMenu removeItemMenu = new RemoveItemMenu(inventoryManagementService);
        UpdateItemMenu updateItemMenu = new UpdateItemMenu(inventoryManagementService);
        DisplayInventoryMenu displayInvetoryMenu = new DisplayInventoryMenu(inventoryQueryService);
        DisplayInventoryValueMenu displayInventoryValueMenu = new DisplayInventoryValueMenu(inventoryQueryService);
        DisplayInventoryQuantityMenu displayInventoryQuantityMenu = new DisplayInventoryQuantityMenu(inventoryQueryService);
        return new InventoryMenu(addItemMenu,updateItemMenu,removeItemMenu,displayInvetoryMenu,displayInventoryValueMenu,displayInventoryQuantityMenu);
    }

    public CertificateMenu certificateMenuGenerate(){
        CertificateDao certificateDao = new CertificateDaoImpl(db);
        RoomDAO roomDAO = new RoomDAOImp(db);
        PlayerDAO playerDAO = new PlayerDAOImp(db);
        ThemeDao themeDao = new ThemeDaoImpl(db);
        PlayerCertificateDao playerCertificateDao = new PlayerCertificateDaoImpl(db);
        CertificateService certificateService = new CertificateService(certificateDao);
        RoomService roomService = new RoomService(roomDAO,themeDao);
        PlayerService playerService = new PlayerService(playerDAO);
        PlayerCertificateService playerCertificateService = new PlayerCertificateService(playerCertificateDao, certificateDao, playerDAO, roomDAO);
        return new CertificateMenu(certificateService,playerCertificateService,playerService,roomService);
    }

    public InventoryMenu salesMenuGenerate() {
        return null;
    }

    public ThemeMenu themeMenuGenerate() {
        ThemeDao themeDao = new ThemeDaoImpl(db);
        ThemeService themeService = new ThemeService(themeDao);
        ThemeMenu themeMenu = new ThemeMenu(themeService);
        return  themeMenu;
    }

}
