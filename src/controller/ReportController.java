package controller;

import DAO.AbstractCustomerDAO;
import DAO.AbstractOrderDAO;
import DTO.Customer;
import DTO.Orders;

import java.sql.SQLException;

public class ReportController {
    public static void getCustomersOrderedById(AbstractCustomerDAO customerDAO) {
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

    public static void getCustomersOrderedByName(AbstractCustomerDAO customerDAO) {
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

    public static void getOrdersOrderedByNumber(AbstractOrderDAO ordersDAO) {
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

    public static void getOrdersOfCustomersOrderedByCustomerName(AbstractOrderDAO ordersDAO, AbstractCustomerDAO customerDAO) {
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
            }
        } catch (SQLException e) {
            System.out.println("Erro ao obter relatório de pedidos ordenados pelo nome do cliente: " + e.getMessage());
        }
    }
}
