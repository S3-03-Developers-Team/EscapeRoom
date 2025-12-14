package org.s3team.theme.service;

import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.theme.dao.ThemeDao;
import org.s3team.theme.model.Theme;

import java.util.List;

public class ThemeService {

    private final ThemeDao themeDao;

    public ThemeService(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    public Theme createTheme(Name name) {

        if (themeDao.findByName(name).isPresent()) {
             throw new IllegalStateException("Theme with name " + name + " already exists.");
        }

        Theme theme = Theme.createNew(name);
        return themeDao.save(theme);
    }

    public Theme getThemeById(Id<Theme> id) {
        return themeDao.getById(id);
    }

    public Theme getThemeByName(Name name) {
        return themeDao.getByName(name);
    }

    public List<Theme> getAllThemes() {
        return themeDao.findAll();
    }

    public Theme updateTheme(Theme theme) {
        themeDao.getById(theme.getId());
        themeDao.update(theme);
        return theme;
    }

    public void deleteTheme(Id<Theme> id) {
        themeDao.getById(id);
        themeDao.delete(id);
    }

    public void deleteThemeByName(Name name) {
        themeDao.getByName(name);
        themeDao.deleteByName(name);
    }
}

