package view;

import controller.Controller;

import java.util.List;

import static view.Menu.getOption;
import static view.Menu.printMenuOptions;

public class OrderMenu {
    private static final List<String> orderMenuOptions = List.of(
            "Inserir pedido para um cliente", "Consultar pedido pelo número", "Apagar pedido pelo número "
    );

    static void orderMenu(Controller controller) {
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
}
