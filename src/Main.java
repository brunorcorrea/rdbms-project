import RDBMS.DataBaseType;
import controller.Controller;
import view.InfoMenu;
import view.Menu;

public class Main {
    public static void main(String[] args) {
        InfoMenu.welcome();

        if (isDevelopmentProfile(args)) {
            Menu.controller = new Controller(DataBaseType.MEMORY);
        } else {
            Menu.controller = new Controller(DataBaseType.MARIADB);
            Menu.setDatabaseCredentials();
        }

        Menu.showMainMenu();

        InfoMenu.goodbye();
    }

    private static boolean isDevelopmentProfile(String[] args) {
        return args.length > 0 && args[0].equalsIgnoreCase("development");
    }
}