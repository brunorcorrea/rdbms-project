package controller;

import DAO.*;
import DTO.Customer;
import DTO.Orders;
import RDBMS.MariaDBConnection;
import RDBMS.MemoryDBConnection;
import System.DataBaseType;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    private static final Scanner scanner = new Scanner(System.in);

    private AbstractCustomerDAO customerDAO = null;
    private AbstractOrderDAO ordersDAO = null;
    private MariaDBConnection myDBConnection = null;
    private MemoryDBConnection memoryDBConnection = null;
    private DataBaseType selectedDataBase = DataBaseType.INVALID;

    public Controller(DataBaseType selectedDataBase) {
        super();
        this.selectedDataBase = selectedDataBase;
        this.openConnection();
    }

    public void openConnection() {
        switch (selectedDataBase) {
            case MEMORY: {
                memoryDBConnection = new MemoryDBConnection();
                this.customerDAO = new Customer_Mem_DAO(memoryDBConnection);
                this.ordersDAO = new Order_Mem_DAO(memoryDBConnection);
            }
            break;
            case MARIADB: {
                myDBConnection = new MariaDBConnection();
                this.customerDAO = new Customer_DB_DAO(myDBConnection.getConnection());
                this.ordersDAO = new Order_DB_DAO(myDBConnection.getConnection());
            }
            break;
            default: {
                System.out.println("O banco de dados selecionado não é suportado.");
                throw new InvalidParameterException("Seletor não especificado: " + selectedDataBase);
            }
        }
    }

    public void closeConnection() {
        if (myDBConnection != null) {
            myDBConnection.close();
        }
        if (memoryDBConnection != null) {
            memoryDBConnection.close();
        }
    }

    public void insertCustomer() {
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

            this.customerDAO.addCustomer(customer);
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public void getCustomerById() {
        try {
            System.out.println("Insira o ID do cliente: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Customer customer = this.customerDAO.getCustomerById(id);

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
        } catch (SQLException e) {
            System.out.println("Erro ao obter cliente: " + e.getMessage());
        }
    }

    public void getCustomersByName() {
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

    public void deleteCustomer() {
        try {
            System.out.println("Insira o ID do cliente: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            this.ordersDAO.deleteOrdersByCustomerId(id);
            this.customerDAO.deleteCustomer(id);
            System.out.println("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    public void insertOrder() {
        try {
            int number = ordersDAO.getNextValidNumber();

            System.out.println("Insira o ID do cliente: ");
            int customerId = scanner.nextInt();

            Customer customer = this.customerDAO.getCustomerById(customerId);

            if (customer == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            System.out.println("Insira a descrição do pedido: ");
            String description = scanner.nextLine().trim();

            System.out.println("Insira o preço do pedido: ");
            double price = scanner.nextDouble();

            Orders order = new Orders(number, customerId, description, BigDecimal.valueOf(price));

            this.ordersDAO.addOrder(order);
            System.out.println("Pedido inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }
}
