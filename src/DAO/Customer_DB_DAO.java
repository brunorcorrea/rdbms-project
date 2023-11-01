package DAO;

import DTO.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer_DB_DAO extends AbstractCustomerDAO {
    private final Connection connection;

    public Customer_DB_DAO(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public List<Customer> getAllCustomersOrderedByName() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;
        String query = "SELECT * FROM Customer WHERE id BETWEEN ? AND ? ORDER BY name";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, minPrimaryKeyValue);
            preparedStatement.setInt(2, maxPrimaryKeyValue);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customers.add(customer);
            }

            return customers;
        }
    }

    @Override
    public Customer getCustomerById(int customerId) throws SQLException {
        validateIfIdIsValid(customerId);

        String query = "SELECT * FROM Customer WHERE id = ?";
        Customer customer = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
            }
        }

        return customer;
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        validateIfIdIsValid(customer.getId());

        String query = "INSERT INTO Customer (id, name, city, state) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getCity());
            preparedStatement.setString(4, customer.getState());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateCustomer(Customer customer) throws SQLException {
        validateIfIdIsValid(customer.getId());

        String query = "UPDATE Customer SET name = ?, city = ?, state = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getCity());
            preparedStatement.setString(3, customer.getState());
            preparedStatement.setInt(4, customer.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        validateIfIdIsValid(customerId);

        String query = "DELETE FROM Customer WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAllCustomers() throws SQLException {
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;
        String query = "DELETE FROM Customer WHERE id BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, minPrimaryKeyValue);
            preparedStatement.setInt(2, maxPrimaryKeyValue);
            preparedStatement.executeUpdate();
        }
    }

    public void validateIfIdIsValid(int id) throws SQLException {
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;

        if (id < minPrimaryKeyValue || id > maxPrimaryKeyValue)
            throw new SQLException("O ID do cliente deve estar entre " + minPrimaryKeyValue + " e " + maxPrimaryKeyValue + "!");
    }

    public int getNextValidId() throws SQLException {
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;

        String query = "SELECT MAX(id) as id FROM Customer WHERE id BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, minPrimaryKeyValue);
            preparedStatement.setInt(2, maxPrimaryKeyValue);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.wasNull()) {
                return minPrimaryKeyValue;
            } else {
                resultSet.next();
                resultSet.getInt("id");

                int nextId = resultSet.getInt("id") + 1;
                if (nextId > maxPrimaryKeyValue)
                    throw new SQLException("O banco de dados está cheio e não pode mais armazenar novos clientes!"); //TODO change exception type

                return nextId;
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao obter o próximo ID válido!", e);
        }
    }
}
