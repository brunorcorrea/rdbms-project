package DAO;

import DTO.Customer;
import RDBMS.MemoryDBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Customer_Mem_DAO extends AbstractCustomerDAO {
    private final MemoryDBConnection databaseRef;

    public Customer_Mem_DAO(MemoryDBConnection databaseRef) {
        super();
        this.databaseRef = databaseRef;
    }

    @Override
    public List<Customer> getAllCustomersOrderedByPropertyAndDirection(String property, String direction) throws SQLException {
        List<Customer> customers = new ArrayList<>(databaseRef.getCustomerList());

        if (property != null && direction != null) {
            switch (property) {
                case "id" -> customers.sort((customer1, customer2) -> {
                    if (direction.equalsIgnoreCase("asc")) {
                        return customer1.getId() - customer2.getId();
                    } else {
                        return customer2.getId() - customer1.getId();
                    }
                });
                case "name" -> customers.sort((customer1, customer2) -> {
                    if (direction.equalsIgnoreCase("asc")) {
                        return customer1.getName().compareTo(customer2.getName());
                    } else {
                        return customer2.getName().compareTo(customer1.getName());
                    }
                });
                case "city" -> customers.sort((customer1, customer2) -> {
                    if (direction.equalsIgnoreCase("asc")) {
                        return customer1.getCity().compareTo(customer2.getCity());
                    } else {
                        return customer2.getCity().compareTo(customer1.getCity());
                    }
                });
                case "state" -> customers.sort((customer1, customer2) -> {
                    if (direction.equalsIgnoreCase("asc")) {
                        return customer1.getState().compareTo(customer2.getState());
                    } else {
                        return customer2.getState().compareTo(customer1.getState());
                    }
                });
            }
        }

        return customers;
    }

    @Override
    public List<Customer> getAllCustomersOrderedByName() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        customers.addAll(databaseRef.getCustomerList());
        return customers;
    }

    @Override
    public Customer getCustomerById(int customerId) throws SQLException {
        Customer customer = null;
        Iterator<Customer> iterator = databaseRef.getCustomerList().iterator();

        while (iterator.hasNext()) {
            Customer buffer = iterator.next();
            if (buffer.getId() == customerId) {
                customer = buffer;
            }
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomerByName(String customerName) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        Iterator<Customer> iterator = databaseRef.getCustomerList().iterator();

        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            if (customer.getName().equals(customerName)) {
                customers.add(customer);
            }
        }
        return customers;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        databaseRef.getCustomerList().add(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        ArrayList<Customer> customers = databaseRef.getCustomerList();

        for (int index = 0; index < customers.size(); index++) {
            if (customers.get(index).getId() == customer.getId()) {
                customers.set(index, customer);
                break;
            }
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        ArrayList<Customer> customers = databaseRef.getCustomerList();

        for (int index = 0; index < customers.size(); index++) {
            if (customers.get(index).getId() == customerId) {
                customers.remove(index);
                break;
            }
        }
    }

    @Override
    public void deleteAllCustomers() throws SQLException {
        databaseRef.getCustomerList().clear();
    }

    @Override
    public int getNextValidId() throws SQLException {
        return databaseRef.getCustomerList().size() + 1;
    }
}
