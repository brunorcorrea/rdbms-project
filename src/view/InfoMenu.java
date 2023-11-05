package view;

import model.Info;

import java.util.List;

import static view.Menu.*;

public class InfoMenu {
    private static final List<String> infoMenuOptions = List.of(
            "Ajuda", "Sobre"
    );

    static void infoMenu() {
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
