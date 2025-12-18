package org.s3team.theme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.s3team.dataBaseConnection.DataBaseConnection;
import org.s3team.dataBaseConnection.TestConnection;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.theme.dao.ThemeDaoImpl;
import org.s3team.theme.model.Theme;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ThemeDaoImpTest {
    private ThemeDaoImpl themeDao;

    @BeforeEach
    public void setup() {
        DataBaseConnection db = TestConnection.getInstance();
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

    @Test
    void findByName_existingTheme_shouldReturnTheme() {
        Theme saved = themeDao.save(Theme.createNew(new Name("scary")));

        Optional<Theme> found = themeDao.findByName(new Name("scary"));

        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }

    @Test
    void findById_notFound_shouldReturnEmptyOptional() {
        Id<Theme> nonExistingId = new Id<>(9999);

        Optional<Theme> result = themeDao.findById(nonExistingId);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByName_notFound_shouldReturnEmptyOptional() {
        Optional<Theme> result = themeDao.findByName(new Name("not-exists"));

        assertTrue(result.isEmpty());
    }

    @Test
    void update_existingTheme_shouldReturnTrue_andPersistChanges() {
        Theme saved = themeDao.save(Theme.createNew(new Name("old")));

        boolean updated = themeDao.update(
                Theme.rehydrate(saved.getId(), new Name("new"))
        );

        assertTrue(updated);

        Optional<Theme> found = themeDao.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("new", found.get().getName().value());
    }

    @Test
    void delete_existingTheme_shouldRemoveTheme() {
        Theme saved = themeDao.save(Theme.createNew(new Name("temporary")));

        themeDao.delete(saved.getId());

        Optional<Theme> result = themeDao.findById(saved.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void delete_nonExistingTheme_shouldNotThrowException() {
        Id<Theme> nonExistingId = new Id<>(12345);

        assertDoesNotThrow(() -> themeDao.delete(nonExistingId));
    }

}
