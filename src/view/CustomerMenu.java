package view;

import controller.Controller;

import java.util.List;

import static view.Menu.getOption;
import static view.Menu.printMenuOptions;

public class CustomerMenu {
    private static final List<String> customerMenuOptions = List.of(
            "Inserir clientes", "Consultar cliente pelo identificador", "Consultar cliente pelo nome", "Apagar cliente pelo identificador"
    );

    static void customerMenu(Controller controller) {
        int option;
        do {
            printMenuOptions("Menu de Clientes", customerMenuOptions);
            option = getOption();

            switch (option) {
                case 1 -> controller.insertCustomer();
                case 2 -> controller.getCustomerById();
                case 3 -> controller.getCustomersByName();
                case 4 -> controller.deleteCustomerAndHisOrders();
                default -> System.out.println("Opção Inválida.");
            }
        } while (option < 1 || option > 4);
    }
}
