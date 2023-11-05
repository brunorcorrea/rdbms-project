package controller;

import DAO.AbstractCustomerDAO;
import DAO.AbstractOrderDAO;
import DTO.Customer;
import DTO.Orders;
import exception.InvalidCustomerIdException;
import exception.InvalidOrderNumberException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static controller.CustomerController.getCustomerId;

public class OrderController {
    private static final Scanner scanner = new Scanner(System.in);

    public static void insert(AbstractOrderDAO ordersDAO, AbstractCustomerDAO customerDAO) {
        try {
            int number = ordersDAO.getNextValidNumber();

            int customerId = getCustomerId();

            Customer customer = customerDAO.getCustomerById(customerId);

            if (customer == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            String description;
            do {
                System.out.println("Insira a descrição do pedido: ");
                description = scanner.nextLine().trim();
            } while (description.isEmpty());


            System.out.println("Insira o preço do pedido: ");
            BigDecimal price = scanner.nextBigDecimal();
            Orders order = new Orders(number, customerId, description, price);
            ordersDAO.addOrder(order);

            System.out.println("Pedido inserido com sucesso!");
        } catch (InvalidCustomerIdException icd) {
            System.out.println("ID do cliente inválido!");
        } catch (InputMismatchException iem) {
            System.out.println("Preço do pedido inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    public static void getByNumber(AbstractOrderDAO ordersDAO, AbstractCustomerDAO customerDAO) {
        try {
            int number = getOrderNumber();

            Orders order = ordersDAO.getOrderByNumber(number);

            if (order != null) {
                Customer customer = customerDAO.getCustomerById(order.getCustomerId());

                System.out.println("----- ----- -----");
                System.out.println("Pedido com número " + number + " encontrado!");
                System.out.println("* ID do Cliente: " + customer.getId());
                System.out.println("* Nome do Cliente: " + customer.getName());
                System.out.println("* Descrição do Pedido: " + order.getDescription());
                System.out.println("* Preço do Pedido: " + order.getPrice());
                System.out.println("----- ----- -----");

            } else {
                System.out.println("Pedido não encontrado!");
            }
        } catch (InvalidOrderNumberException ion) {
            System.out.println("Número do pedido inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao obter pedido: " + e.getMessage());
        }
    }

    static int getOrderNumber() throws InvalidOrderNumberException {
        try {
            System.out.println("Insira o número do pedido: ");
            int number = scanner.nextInt();
            scanner.nextLine();

            return number;
        } catch (InputMismatchException iem) {
            throw new InvalidOrderNumberException("Número do pedido inválido!");
        }
    }

    public static void delete(AbstractOrderDAO ordersDAO) {
        try {
            int number = getOrderNumber();

            Orders order = ordersDAO.getOrderByNumber(number);
            if (order == null) {
                System.out.println("Pedido não encontrado!");
                return;
            }

            ordersDAO.deleteOrder(number);
            System.out.println("Pedido deletado com sucesso!");
        } catch (InvalidOrderNumberException ion) {
            System.out.println("Número do pedido inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar pedido: " + e.getMessage());
        }
    }
}
