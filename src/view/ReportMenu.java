package view;

import controller.Controller;

import java.util.List;

import static view.Menu.getOption;
import static view.Menu.printMenuOptions;

public class ReportMenu {
    private static final List<String> reportMenuOptions = List.of(
            "Clientes ordenados por identificador", "Clientes ordenados por nome", "Pedidos ordenados por número", "Pedidos dos clientes ordenados por nome"
    );

    static void reportMenu(Controller controller) {
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
}
