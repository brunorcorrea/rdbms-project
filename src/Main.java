public class Main {
    public static void main(String[] args) {
        Menu.welcome();

        if (isProductionProfile(args)) Menu.setDatabaseCredentials();
        Menu.showMainMenu();

        Menu.goodbye();
    }

    private static boolean isProductionProfile(String[] args) {
        return args.length == 0 || args[0].equalsIgnoreCase("production");
    }
}