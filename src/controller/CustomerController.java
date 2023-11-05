package controller;

import DAO.AbstractCustomerDAO;
import DAO.AbstractOrderDAO;
import DTO.Customer;
import exception.InvalidCustomerIdException;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerController {
    private static final Scanner scanner = new Scanner(System.in);

    public static void insert(AbstractCustomerDAO customerDAO) {
        try {
            int id = customerDAO.getNextValidId();

            String name;
            do {
                System.out.println("Insira o nome do cliente: ");
                name = scanner.nextLine().trim();
            } while (name.isEmpty());

            String city;
            do {
                System.out.println("Insira a cidade do cliente: ");
                city = scanner.nextLine().trim();
            } while (city.isEmpty());

            String state;
            do {
                System.out.println("Insira o estado do cliente: ");
                state = scanner.nextLine().trim();
            } while (state.isEmpty());

            Customer customer = new Customer(id, name, city, state);

            customerDAO.addCustomer(customer);
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public static void getById(AbstractCustomerDAO customerDAO) {
        try {
            System.out.println("Insira o ID do cliente: ");
            int id = getCustomerId();

            Customer customer = customerDAO.getCustomerById(id);

            if (customer != null) {
                System.out.println("----- ----- -----");
                System.out.println("Cliente com id " + id + " encontrado!");
                System.out.println("* Nome: " + customer.getName());
                System.out.println("* Cidade: " + customer.getCity());
                System.out.println("* Estado: " + customer.getState());
                System.out.println("----- ----- -----");

            } else {
                System.out.println("Cliente não encontrado!");
            }
        } catch (InvalidCustomerIdException icd) {
            System.out.println("ID do cliente inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao obter cliente: " + e.getMessage());
        }
    }

    public static void getByName(AbstractCustomerDAO customerDAO) {
        try {
            System.out.println("Insira o nome do cliente: ");
            String name = scanner.nextLine().trim();

            var customers = customerDAO.getCustomerByName(name);
            if (customers.isEmpty()) {
                System.out.println("Nenhum cliente encontrado!");
            } else {
                System.out.println("Clientes encontrados: ");
                System.out.println("----- ----- -----");
                for (Customer customer : customers) {
                    System.out.println("\n** Id " + customer.getId());
                    System.out.println("* Nome: " + customer.getName());
                    System.out.println("* Cidade: " + customer.getCity());
                    System.out.println("* Estado: " + customer.getState());
                }
                System.out.println("----- ----- -----");
            }


        } catch (SQLException e) {
            System.out.println("Erro ao obter clientes: " + e.getMessage());
        }
    }

    public static void delete(AbstractCustomerDAO customerDAO, AbstractOrderDAO ordersDAO) {
        try {
            int id = getCustomerId();

            Customer customer = customerDAO.getCustomerById(id);
            if (customer == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            ordersDAO.deleteOrdersByCustomerId(id);
            customerDAO.deleteCustomer(id);
            System.out.println("Cliente deletado com sucesso!");
        } catch (InvalidCustomerIdException icd) {
            System.out.println("ID do cliente inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    static int getCustomerId() throws InvalidCustomerIdException {
        try {
            System.out.println("Insira o ID do cliente: ");
            int customerId = scanner.nextInt();
            scanner.nextLine();

            return customerId;
        } catch (InputMismatchException iem) {
            throw new InvalidCustomerIdException("ID do cliente inválido!");
        }
    }
}
