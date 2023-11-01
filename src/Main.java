public class Main {
    public static void main(String[] args) {
        // TODO detect if profile is development or production

        Menu.welcome();

//        Menu.setDatabaseCredentials(); // TODO only show this if profile is production
        Menu.showMainMenu();

        Menu.goodbye();
    }
}