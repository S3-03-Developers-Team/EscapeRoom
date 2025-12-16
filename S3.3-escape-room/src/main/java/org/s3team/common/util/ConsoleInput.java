package org.s3team.common.util;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleInput {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static Boolean readBoolean(String prompt) {
        while (true) {

            System.out.print(prompt);
            String input = scanner.nextLine().toLowerCase().trim();
            String y = "y";
            String n = "n";
            if (input.equals(y)) {
                return true;
            } else if (input.equals(n)) {
                return false;
            } else {
                System.out.println("ERROR: Please enter 'y' for Yes or 'n' for No.");
            }

        }

    }


    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("ID must be a positive number greater than 0.");
        }
    }

    public static BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }
}
