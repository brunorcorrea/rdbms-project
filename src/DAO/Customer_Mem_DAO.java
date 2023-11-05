package DAO;

import DTO.Customer;
import RDBMS.MemoryDBConnection;

import java.util.ArrayList;
import java.util.List;


public class Customer_Mem_DAO extends AbstractCustomerDAO {
    private final MemoryDBConnection databaseRef;

    public Customer_Mem_DAO(MemoryDBConnection databaseRef) {
        super();
        this.databaseRef = databaseRef;
    }

    @Override
    public List<Customer> getAllCustomersOrderedByPropertyAndDirection(String property, String direction) {
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
    public Customer getCustomerById(int customerId) {
        Customer customer = null;

        for (Customer buffer : databaseRef.getCustomerList()) {
            if (buffer.getId() == customerId) {
                customer = buffer;
            }
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomerByName(String customerName) {
        List<Customer> customers = new ArrayList<>();

        for (Customer customer : databaseRef.getCustomerList()) {
            if (customer.getName().equals(customerName)) {
                customers.add(customer);
            }
        }
        return customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        databaseRef.getCustomerList().add(customer);
    }

    @Override
    public void deleteCustomer(int customerId) {
        ArrayList<Customer> customers = databaseRef.getCustomerList();

        for (int index = 0; index < customers.size(); index++) {
            if (customers.get(index).getId() == customerId) {
                customers.remove(index);
                break;
            }
        }
    }

    @Override
    public int getNextValidId() {
        return databaseRef.getCustomerList().size() + 1;
    }
}
