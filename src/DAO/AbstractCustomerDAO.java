package DAO;

import DTO.Customer;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractCustomerDAO {
    public abstract List<Customer> getAllCustomersOrderedByPropertyAndDirection(String property, String direction) throws SQLException;

    abstract public Customer getCustomerById(int customerId) throws SQLException;

    public abstract List<Customer> getCustomerByName(String customerName) throws SQLException;

    abstract public void addCustomer(Customer customer) throws SQLException;

    abstract public void deleteCustomer(int customerId) throws SQLException;

    abstract public int getNextValidId() throws SQLException;
}
