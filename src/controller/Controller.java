package controller;

import DAO.*;
import DTO.Customer;
import RDBMS.MariaDBConnection;
import RDBMS.MemoryDBConnection;
import System.DataBaseType;

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
        this.openConnection();

        try {
            int id = customerDAO.getNextValidId();

            System.out.println("Insira o nome do cliente: ");
            String name = scanner.nextLine().trim();

            System.out.println("Insira a cidade do cliente: ");
            String city = scanner.nextLine().trim();

            System.out.println("Insira o estado do cliente: ");
            String state = scanner.nextLine().trim();

            Customer customer = new Customer(id, name, city, state);

            this.customerDAO.addCustomer(customer);
            System.out.println("Cliente inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }

        this.closeConnection();
    }
}
