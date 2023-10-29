import RDBMS.MariaDBConnection;

import java.util.List;
import java.util.Scanner;

public class Menu { //TODO treat exception when pass a string instead of an integer
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> mainMenuOptions = List.of(
            "Clientes", "Pedidos", "Relatórios", "Informações", "Sair"
    );
    private static final List<String> customerMenuOptions = List.of(
            "Inserir clientes", "Consultar cliente pelo identificador", "Consultar cliente pelo nome", "Apagar cliente pelo identificador"
    );
    private static final List<String> orderMenuOptions = List.of(
            "Inserir pedido para um cliente", "Consultar pedido pelo número", "Apagar pedido pelo número "
    );
    private static final List<String> reportMenuOptions = List.of(
            "Clientes ordenados por identificador", "Clientes ordenados por nome", "Pedidos ordenados por número", "Pedidos dos clientes ordenados por nome"
    );
    private static final List<String> infoMenuOptions = List.of(
            "Ajuda", "Sobre"
    );
    /*TODO optional:
    submenu de operações especiais: a) criação automática de um lote de clientes e respectivos pedidos para popular o BD
    durante testes; b) limpeza dos dados, para excluir todos os registros de clientes e pedidos na faixa válida do grupo
    */

    public static void setDatabaseCredentials() {
        System.out.println("Please enter the database credentials.");
        System.out.println("User: ");
        MariaDBConnection.USERNAME = scanner.nextLine();

        System.out.println("Password: ");
        MariaDBConnection.PASSWORD = scanner.nextLine();
    }

    public static void showMainMenu() {
        int option;
        do {
            System.out.println("Menu Principal"); //TODO maybe remove this?
            for (int i = 0; i < mainMenuOptions.size(); i++) {
                System.out.println((i + 1) + ". " + mainMenuOptions.get(i));
            }
            System.out.println("Por favor, escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> customerMenu();
                case 2 -> orderMenu();
                case 3 -> reportMenu();
                case 4 -> infoMenu();
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opção Inválida.");
            }
        } while (option != 5);
    }

    private static void customerMenu() {
        int option;
        do {
            System.out.println("Menu de Clientes");
            for (int i = 0; i < customerMenuOptions.size(); i++) {
                System.out.println((i + 1) + ". " + customerMenuOptions.get(i));
            }
            System.out.println("Por favor, escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // TODO
                    break;
                case 2:
                    // TODO
                    break;
                case 3:
                    // TODO
                    break;
                case 4:
                    // TODO
                    break;
                default:
                    System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 4);
    }

    private static void orderMenu() {
        int option;
        do {
            System.out.println("Menu de Pedidos");
            for (int i = 0; i < orderMenuOptions.size(); i++) {
                System.out.println((i + 1) + ". " + orderMenuOptions.get(i));
            }
            System.out.println("Por favor, escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // TODO
                    break;
                case 2:
                    // TODO
                    break;
                case 3:
                    // TODO
                    break;
                default:
                    System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 3);
    }

    private static void reportMenu() {
        int option;
        do {
            System.out.println("Menu de Relatórios");
            for (int i = 0; i < reportMenuOptions.size(); i++) {
                System.out.println((i + 1) + ". " + reportMenuOptions.get(i));
            }
            System.out.println("Por favor, escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // TODO
                    break;
                case 2:
                    // TODO
                    break;
                case 3:
                    // TODO
                    break;
                case 4:
                    // TODO
                    break;
                default:
                    System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 4);
    }

    private static void infoMenu() {
        int option;
        do {
            System.out.println("Menu de Informações");
            for (int i = 0; i < infoMenuOptions.size(); i++) {
                System.out.println((i + 1) + ". " + infoMenuOptions.get(i));
            }
            System.out.println("Por favor, escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    // TODO
                    break;
                case 2:
                    // TODO
                    break;
                default:
                    System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 2);
    }

}