package controller;

import DAO.*;
import DTO.Customer;
import DTO.Orders;
import RDBMS.DataBaseType;
import RDBMS.MariaDBConnection;
import RDBMS.MemoryDBConnection;
import exception.InvalidCustomerIdException;
import exception.InvalidOrderNumberException;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.InputMismatchException;
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
            case MEMORY -> {
                memoryDBConnection = new MemoryDBConnection();
                this.customerDAO = new Customer_Mem_DAO(memoryDBConnection);
                this.ordersDAO = new Order_Mem_DAO(memoryDBConnection);
            }
            case MARIADB -> {
                myDBConnection = new MariaDBConnection();
                this.customerDAO = new Customer_DB_DAO(myDBConnection.getConnection());
                this.ordersDAO = new Order_DB_DAO(myDBConnection.getConnection());
            }
            default -> {
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
            int id = getCustomerId();

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
        } catch (InvalidCustomerIdException icd) {
            System.out.println("ID do cliente inválido!");
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
            int id = getCustomerId();

            this.ordersDAO.deleteOrdersByCustomerId(id);
            this.customerDAO.deleteCustomer(id);
            System.out.println("Cliente deletado com sucesso!");
        } catch (InvalidCustomerIdException icd) {
            System.out.println("ID do cliente inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    public void insertOrder() {
        try {
            int number = ordersDAO.getNextValidNumber();

            int customerId = getCustomerId();

            Customer customer = this.customerDAO.getCustomerById(customerId);

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
            this.ordersDAO.addOrder(order);

            System.out.println("Pedido inserido com sucesso!");
        } catch (InvalidCustomerIdException icd) {
            System.out.println("ID do cliente inválido!");
        } catch (InputMismatchException iem) {
            System.out.println("Preço do pedido inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    public void getOrderByNumber() {
        try {
            int number = getOrderNumber();

            Orders order = this.ordersDAO.getOrderByNumber(number);

            if (order != null) {
                Customer customer = this.customerDAO.getCustomerById(order.getCustomerId());

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

    public void deleteOrder() {
        try {
            int number = getOrderNumber();

            this.ordersDAO.deleteOrder(number);
            System.out.println("Pedido deletado com sucesso!");
        } catch (InvalidOrderNumberException ion) {
            System.out.println("Número do pedido inválido!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar pedido: " + e.getMessage());
        }
    }

    public void getCustomersOrderedById() {
        try {
            var customers = customerDAO.getAllCustomersOrderedByPropertyAndDirection("id", "ASC");
            if (customers.isEmpty()) {
                System.out.println("Nenhum cliente encontrado!");
            } else {
                System.out.println("Clientes encontrados: ");
                System.out.println("----- ----- -----");
                for (Customer customer : customers) {
                    System.out.println("-----");
                    System.out.println("* Id " + customer.getId());
                    System.out.println("* Nome: " + customer.getName());
                    System.out.println("* Cidade: " + customer.getCity());
                    System.out.println("* Estado: " + customer.getState());
                    System.out.println("-----");
                }
                System.out.println("----- ----- -----");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter relatório de clientes ordenados pelo ID: " + e.getMessage());
        }
    }

    public void getCustomersOrderedByName() {
        try {
            var customers = customerDAO.getAllCustomersOrderedByPropertyAndDirection("name", "ASC");
            if (customers.isEmpty()) {
                System.out.println("Nenhum cliente encontrado!");
            } else {
                System.out.println("Clientes encontrados: ");
                System.out.println("----- ----- -----");
                for (Customer customer : customers) {
                    System.out.println("-----");
                    System.out.println("* Id " + customer.getId());
                    System.out.println("* Nome: " + customer.getName());
                    System.out.println("* Cidade: " + customer.getCity());
                    System.out.println("* Estado: " + customer.getState());
                    System.out.println("-----");
                }
                System.out.println("----- ----- -----");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter relatório de clientes ordenados pelo nome: " + e.getMessage());
        }
    }

    public void getOrdersOrderedByNumber() {
        try {
            var orders = ordersDAO.getAllOrdersOrderedByNumber();
            if (orders.isEmpty()) {
                System.out.println("Nenhum pedido encontrado!");
            } else {
                System.out.println("Pedidos encontrados: ");
                System.out.println("----- ----- -----");
                for (Orders order : orders) {
                    System.out.println("-----");
                    System.out.println("* Número " + order.getNumber());
                    System.out.println("* ID do Cliente: " + order.getCustomerId());
                    System.out.println("* Descrição do Pedido: " + order.getDescription());
                    System.out.println("* Preço do Pedido: " + order.getPrice());
                    System.out.println("-----");
                }
                System.out.println("----- ----- -----");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter relatório de pedidos ordenados pelo número: " + e.getMessage());
        }
    }

    public void getOrdersOfCustomersOrderedByCustomerName() {
        try {
            var customers = customerDAO.getAllCustomersOrderedByPropertyAndDirection("name", "ASC");
            if (customers.isEmpty()) {
                System.out.println("Nenhum cliente encontrado!");
            } else {
                System.out.println("Clientes encontrados: ");
                System.out.println("----- ----- -----");
                for (Customer customer : customers) {
                    System.out.println("-----");
                    System.out.println("* Id " + customer.getId());
                    System.out.println("* Nome: " + customer.getName());
                    System.out.println("* Cidade: " + customer.getCity());
                    System.out.println("* Estado: " + customer.getState());
                    System.out.println("-----");

                    var orders = ordersDAO.getOrdersByCustomerId(customer.getId());
                    if (orders.isEmpty()) {
                        System.out.println("Nenhum pedido encontrado!");
                    } else {
                        System.out.println("Pedidos encontrados: ");
                        System.out.println("----- ----- -----");
                        for (Orders order : orders) {
                            System.out.println("-----");
                            System.out.println("** Número " + order.getNumber());
                            System.out.println("* ID do Cliente: " + order.getCustomerId());
                            System.out.println("* Descrição do Pedido: " + order.getDescription());
                            System.out.println("* Preço do Pedido: " + order.getPrice());
                            System.out.println("-----");
                        }
                        System.out.println("----- ----- -----");
                    }
                }
                System.out.println("----- ----- -----");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter relatório de pedidos ordenados pelo nome do cliente: " + e.getMessage());
        }
    }

    private static int getOrderNumber() throws InvalidOrderNumberException {
        try {
            System.out.println("Insira o número do pedido: ");
            int number = scanner.nextInt();
            scanner.nextLine();

            return number;
        } catch (InputMismatchException iem) {
            throw new InvalidOrderNumberException("Número do pedido inválido!");
        }
    }

    private static int getCustomerId() throws InvalidCustomerIdException {
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