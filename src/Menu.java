import RDBMS.MariaDBConnection;
import controller.Controller;

import java.util.List;
import java.util.Scanner;
import System.DataBaseType;

public class Menu { //TODO treat exception when pass a string instead of an integer
    private static final Controller controller = new Controller(DataBaseType.MEMORY); //TODO change to a polymorphic approach

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

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            // TODO Handle any exceptions.
        }
    }

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
        clearConsole();
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

            clearConsole();
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
                    controller.insertCustomer();
                    break;
                case 2:
                    controller.getCustomerById();
                    break;
                case 3:
                    controller.getCustomersByName();
                    break;
                case 4:
                    // TODO
                    break;
                default:
                    System.out.println("Opção Inválida.");
            }

            clearConsole();
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

            clearConsole();
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

            clearConsole();
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
                case 1 -> showHelp();
                case 2 -> showAbout();
                default -> System.out.println("Opção Inválida.");
            }

            clearConsole();
        } while (option < 1 || option > 2);
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