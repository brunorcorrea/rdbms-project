package DAO;

import DTO.Customer;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractCustomerDAO {
    abstract public List<Customer> getAllCustomersOrderedByName() throws SQLException;

    abstract public Customer getCustomerById(int customerId) throws SQLException;

    abstract public void addCustomer(Customer customer) throws SQLException;

    abstract public void updateCustomer(Customer customer) throws SQLException;

    abstract public void deleteCustomer(int customerId) throws SQLException;

    abstract public void deleteAllCustomers() throws SQLException;

    abstract public int getNextValidId() throws SQLException;
}
