import RDBMS.DataBaseType;
import controller.Controller;

public class Main {
    public static void main(String[] args) {
        Menu.welcome();

        if (isDevelopmentProfile(args)) {
            Menu.controller = new Controller(DataBaseType.MEMORY);
        } else {
            Menu.controller = new Controller(DataBaseType.MARIADB);
            Menu.setDatabaseCredentials();
        }

        Menu.showMainMenu();

        Menu.goodbye();
    }

    private static boolean isDevelopmentProfile(String[] args) {
        return args.length > 0 && args[0].equalsIgnoreCase("development");
    }
}