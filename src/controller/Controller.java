package controller;

import DAO.*;
import RDBMS.DataBaseType;
import RDBMS.MariaDBConnection;
import RDBMS.MemoryDBConnection;

import java.security.InvalidParameterException;

public class Controller {
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
        CustomerController.insert(this.customerDAO);
    }

    public void getCustomerById() {
        CustomerController.getById(this.customerDAO);
    }

    public void getCustomersByName() {
        CustomerController.getByName(this.customerDAO);
    }

    public void deleteCustomerAndHisOrders() {
        CustomerController.delete(this.customerDAO, this.ordersDAO);
    }

    public void insertOrder() {
        OrderController.insert(this.ordersDAO, this.customerDAO);
    }

    public void getOrderByNumber() {
        OrderController.getByNumber(this.ordersDAO, this.customerDAO);
    }

    public void deleteOrder() {
        OrderController.delete(this.ordersDAO);
    }

    public void getCustomersOrderedById() {
        ReportController.getCustomersOrderedById(this.customerDAO);
    }

    public void getCustomersOrderedByName() {
        ReportController.getCustomersOrderedByName(this.customerDAO);
    }

    public void getOrdersOrderedByNumber() {
        ReportController.getOrdersOrderedByNumber(this.ordersDAO);
    }

    public void getOrdersOfCustomersOrderedByCustomerName() {
        ReportController.getOrdersOfCustomersOrderedByCustomerName(this.ordersDAO, this.customerDAO);
    }
}