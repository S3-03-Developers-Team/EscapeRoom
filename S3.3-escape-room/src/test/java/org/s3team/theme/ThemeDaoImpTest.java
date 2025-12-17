package org.s3team.theme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.s3team.DataBaseConnection.Data_Base_Connection;
import org.s3team.DataBaseConnection.TestConnection;
import org.s3team.common.valueobject.Name;
import org.s3team.room.DAO.RoomDAOImp;
import org.s3team.theme.dao.ThemeDaoImpl;
import org.s3team.theme.model.Theme;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThemeDaoImpTest {
    private ThemeDaoImpl themeDao;

    @BeforeEach
    public void setup() {
        Data_Base_Connection db = TestConnection.getInstance();
        themeDao = new ThemeDaoImpl(db);
    }

    @Test
    public void saveShouldPersistTheme_findByIdShouldReturnTheme() {
        Theme theme = Theme.createNew(new Name("happy"));

        Theme themeSaved = themeDao.save(theme);

        Optional<Theme> themeFound = themeDao.findById(themeSaved.getId());

        assertTrue(themeFound.isPresent());

        assertEquals(themeFound.get(),themeSaved);

    }
}
