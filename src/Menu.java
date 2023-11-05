import RDBMS.MariaDBConnection;
import controller.Controller;

import java.util.List;
import java.util.Scanner;

public class Menu {
    public static Controller controller;

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

    private static void printDivider() {
        System.out.println("--------------------------------------------------");
    }

    public static void welcome() {
        printDivider();

        System.out.println("Bem-vindo ao " + Info.PROJECT_NAME + " v" + Info.PROJECT_VERSION + "!");
        System.out.println("Última atualização: " + Info.PROJECT_LAST_UPDATE);
        System.out.println(Info.PROJECT_DESCRIPTION);
        System.out.println("Autores: " + String.join(", ", Info.PROJECT_AUTHORS));

        printDivider();
    }

    public static void goodbye() {
        printDivider();

        System.out.println("Obrigado por usar o " + Info.PROJECT_NAME + " v" + Info.PROJECT_VERSION + "!");
        System.out.println("Última atualização: " + Info.PROJECT_LAST_UPDATE);
        System.out.println("Autores: " + String.join(", ", Info.PROJECT_AUTHORS));
        System.out.println("Programa encerrado com sucesso!");

        printDivider();
    }

    public static void setDatabaseCredentials() {
        System.out.println("Por favor insira as credenciais do banco de dados:");
        System.out.println("Usuário: ");
        MariaDBConnection.USERNAME = scanner.nextLine();

        System.out.println("Senha: ");
        MariaDBConnection.PASSWORD = scanner.nextLine();

        System.out.println("Credenciais salvas com sucesso!");
    }

    public static void showMainMenu() {
        int option;
        do {
            printMenuOptions("Menu Principal", mainMenuOptions);
            option = getOption();

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
            printMenuOptions("Menu de Clientes", customerMenuOptions);
            option = getOption();

            switch (option) {
                case 1 -> controller.insertCustomer();
                case 2 -> controller.getCustomerById();
                case 3 -> controller.getCustomersByName();
                case 4 -> controller.deleteCustomer();
                default -> System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 4);
    }

    private static void orderMenu() {
        int option;
        do {
            printMenuOptions("Menu de Pedidos", orderMenuOptions);
            option = getOption();

            switch (option) {
                case 1 -> controller.insertOrder();
                case 2 -> controller.getOrderByNumber();
                case 3 -> controller.deleteOrder();
                default -> System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 3);
    }

    private static void reportMenu() {
        int option;
        do {
            printMenuOptions("Menu de Relatórios", reportMenuOptions);
            option = getOption();

            switch (option) {
                case 1 -> controller.getCustomersOrderedById();
                case 2 -> controller.getCustomersOrderedByName();
                case 3 -> controller.getOrdersOrderedByNumber();
                case 4 -> controller.getOrdersOfCustomersOrderedByCustomerName();
                default -> System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 4);
    }

    private static void infoMenu() {
        int option;
        do {
            printMenuOptions("Menu de Informações", infoMenuOptions);
            option = getOption();

            switch (option) {
                case 1 -> showHelp();
                case 2 -> showAbout();
                default -> System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 2);
    }

    private static int getOption() {
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

    private static void printMenuOptions(String menuTitle, List<String> infoMenuOptions) {
        System.out.println("--- " + menuTitle + " ---");
        for (int i = 0; i < infoMenuOptions.size(); i++) {
            System.out.println((i + 1) + ". " + infoMenuOptions.get(i));
        }
    }

    private static void showHelp() {
        printDivider();

        var help = """
                AJUDA
                                
                Bem-vindo ao Sistema de Gerenciamento SI400 desenvolvido pelo grupo 10!
                                
                Este programa foi projetado para facilitar a interação com o banco de dados SI400 e oferece as seguintes funcionalidades:
                                
                1. **Gerenciamento de Clientes:**
                   - Adicione novos clientes e verifique se o ID do cliente está dentro da faixa permitida para o seu grupo.
                   - Consulte clientes pelo ID para visualizar todas as informações de um cliente.
                   - Consulte clientes pelo nome para encontrar clientes com nomes semelhantes.
                   - Exclua clientes pelo ID, incluindo a remoção de todos os pedidos associados.
                                
                2. **Gerenciamento de Pedidos:**
                   - Adicione novos pedidos, garantindo que o cliente exista no banco de dados.
                   - Consulte pedidos pelo número para visualizar os detalhes do pedido.
                   - Exclua pedidos pelo número.
                                
                3. **Relatórios:**
                   - Gere relatórios de clientes e pedidos ordenados por identificador ou nome.
                   - Crie um relatório especial que exibe pedidos de clientes com quebra de seções, mostrando informações detalhadas de cada cliente e seus pedidos.
                                
                4. **Informações:**
                   - Acesse esta seção para obter informações adicionais sobre como usar o programa.
                   - Consulte os créditos e a versão do programa.
                                
                Para começar, escolha uma opção no menu principal. Lembre-se de que, para cada ação que envolve o banco de dados, é importante respeitar a faixa de valores permitida para o grupo 10:
                   - ID do cliente: 100.000-109.999
                   - Número do pedido: 100.000-109.999
                                
                Obrigado por usar o Sistema de Gerenciamento SI400! Estamos aqui para tornar suas tarefas de gerenciamento de dados mais fáceis.
                """;
        System.out.println(help);

        printDivider();
    }

    private static void showAbout() {
        printDivider();

        System.out.println("Sobre o " + Info.PROJECT_NAME + " v" + Info.PROJECT_VERSION + ":");
        System.out.println("\tÚltima atualização: " + Info.PROJECT_LAST_UPDATE);
        System.out.println("\t" + Info.PROJECT_DESCRIPTION);
        System.out.println("\tAutores: " + String.join(", ", Info.PROJECT_AUTHORS));

        printDivider();
    }
}