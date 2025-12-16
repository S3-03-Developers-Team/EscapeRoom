package org.s3team.theme.themeMenu;
import org.s3team.common.util.ConsoleInput;
import org.s3team.common.valueobject.Id;
import org.s3team.common.valueobject.Name;
import org.s3team.theme.model.Theme;
import org.s3team.theme.service.ThemeService;

import java.util.List;

public class ThemeMenu {
    private final ThemeService themeService;

    public ThemeMenu(ThemeService themeService) {
        this.themeService = themeService;
    }
    public void themeMenu() {
        boolean themeMenuExit = false;

        while (!themeMenuExit) {
            try {
                System.out.println("\n--- Theme management ---");
                System.out.println("1. Add New Theme");
                System.out.println("2. Delete Theme By Id");
                System.out.println("3. Delete Theme By Name");
                System.out.println("4. Update Theme");
                System.out.println("5. Find Theme By Id");
                System.out.println("6. Find Theme By Name");
                System.out.println("7. Display All Themes");
                System.out.println("0. Go Back to Main Menu");
                System.out.println("----------------------------");

                int option = ConsoleInput.readInt("Choose an option (0-7): ");

                switch (option) {
                    case 1 -> {
                        System.out.println("\n--- ➕ Add New Theme ---");
                        String nameInput = ConsoleInput.readLine("Enter Theme Name: ");
                        Name name = new Name(nameInput);
                        Theme newTheme = themeService.createTheme(name);
                        System.out.println("✅ Theme successfully created: " + newTheme);
                    }

                    case 2 -> {
                        System.out.println("\n--- 🗑️ Delete Theme By Id ---");
                        int idValue = ConsoleInput.readInt("Enter Theme ID to delete: ");
                        Id<Theme> id = new Id<>(idValue);
                        themeService.deleteTheme(id);
                        System.out.println("✅ Theme with ID " + idValue + " successfully deleted.");
                    }

                    case 3 -> {
                        System.out.println("\n--- 🗑️ Delete Theme By Name ---");
                        String nameStr = ConsoleInput.readLine("Enter Theme Name to delete: ");
                        Name name = new Name(nameStr);
                        themeService.deleteThemeByName(name);
                        System.out.println("✅ Theme with Name '" + nameStr + "' successfully deleted.");
                    }

                    case 4 -> {
                        System.out.println("\n--- ✍️ Update Theme ---");
                        int idValue = ConsoleInput.readInt("Enter Theme ID to update: ");
                        Id<Theme> id = new Id<>(idValue);
                        Theme existingTheme = themeService.getThemeById(id);
                        if (existingTheme == null) {
                            System.out.println("❌ Error: Theme with ID " + idValue + " not found.");
                            return;
                        }
                        System.out.println("Current Name: " + existingTheme.getName().value());
                        String newNameStr = ConsoleInput.readLine("Enter New Theme Name: ");
                        Name newName = new Name(newNameStr);

                        Theme updatedTheme = Theme.rehydrate(id, newName);
                        themeService.updateTheme(updatedTheme);
                        System.out.println("✅ Theme successfully updated: " + updatedTheme);
                    }

                    case 5 -> {
                        System.out.println("\n--- 🔎 Find Theme By Id ---");
                        int idValue = ConsoleInput.readInt("Enter Theme ID: ");
                        Id<Theme> id = new Id<>(idValue);
                        Theme theme = themeService.getThemeById(id);
                        if (theme != null) {
                            System.out.println("Found Theme: " + theme);
                        } else {
                            System.out.println("Theme with ID " + idValue + " not found.");
                        }
                    }

                    case 6 -> {
                        System.out.println("\n--- 🔎 Find Theme By Name ---");
                        String nameStr = ConsoleInput.readLine("Enter Theme Name: ");
                        Name name = new Name(nameStr);
                        Theme theme = themeService.getThemeByName(name);
                        if (theme != null) {
                            System.out.println("Found Theme: " + theme);
                        } else {
                            System.out.println("Theme with Name '" + nameStr + "' not found.");
                        }
                    }

                    case 7 -> {
                        System.out.println("\n--- 📜 Display All Themes ---");
                        List<Theme> themes = themeService.getAllThemes();
                        if (themes.isEmpty()) {
                            System.out.println("No themes found.");
                        } else {
                            for (Theme theme : themes) {
                                System.out.println(theme);
                            }
                            System.out.println("Total themes: " + themes.size());
                        }
                    }

                    case 0 -> {
                        System.out.println("Returning to Main Menu...");
                        themeMenuExit = true;
                    }
                    default -> System.out.println("Invalid option. Please choose between 0 and 7.");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("❌ Input validation error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.err.println("❌ Operation failed: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("❌ An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
