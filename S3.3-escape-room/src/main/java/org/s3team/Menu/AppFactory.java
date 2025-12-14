package org.s3team.Menu;

import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.clue.dao.ClueDao;
import org.s3team.clue.dao.ClueDaoImpl;
import org.s3team.clue.service.ClueService;
import org.s3team.decoration.dao.DecorationDao;
import org.s3team.decoration.dao.DecorationDaoImpl;
import org.s3team.decoration.service.DecorationService;
import org.s3team.inventoryMenu.*;
import org.s3team.inventoryService.InventoryManagementService;
import org.s3team.inventoryService.InventoryQueryService;
import org.s3team.room.DAO.RoomDAO;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.room.Service.RoomService;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.dao.ThemeDaoImpl;

import java.util.Scanner;

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
        DecorationService decorationService = new DecorationService();
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
    public InventoryMenu salesMenuGenerate() {
        return null;
    }

}
