package view;

import RDBMS.MariaDBConnection;
import controller.Controller;

import java.util.List;
import java.util.Scanner;

import static view.CustomerMenu.customerMenu;
import static view.InfoMenu.infoMenu;
import static view.OrderMenu.orderMenu;
import static view.ReportMenu.reportMenu;

public class Menu {
    public static Controller controller;

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> mainMenuOptions = List.of(
            "Clientes", "Pedidos", "Relatórios", "Informações", "Sair"
    );

    public static void setDatabaseCredentials() {
        System.out.println("Por favor insira as credenciais do banco de dados:");
        System.out.println("Usuário: ");
        MariaDBConnection.USERNAME = scanner.nextLine();

        System.out.println("Senha: ");
        MariaDBConnection.PASSWORD = scanner.nextLine();
    }

    public static void showMainMenu() {
        int option;
        do {
            printMenuOptions("Menu Principal", mainMenuOptions);
            option = getOption();

            switch (option) {
                case 1 -> customerMenu(controller);
                case 2 -> orderMenu(controller);
                case 3 -> reportMenu(controller);
                case 4 -> infoMenu();
                case 5 -> closeProgram();
                default -> System.out.println("Opção Inválida.");
            }
        } while (option != 5);
    }

    private static void closeProgram() {
        System.out.println("Saindo...");
        controller.closeConnection();
    }

    static void printDivider() {
        System.out.println("--------------------------------------------------");
    }

    static int getOption() {
        int option;
        System.out.println("Por favor, escolha uma opção: ");
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            option = 0;
        }

        scanner.nextLine();
        return option;
    }

    static void printMenuOptions(String menuTitle, List<String> infoMenuOptions) {
        System.out.println("--- " + menuTitle + " ---");
        for (int i = 0; i < infoMenuOptions.size(); i++) {
            System.out.println((i + 1) + ". " + infoMenuOptions.get(i));
        }
    }
}