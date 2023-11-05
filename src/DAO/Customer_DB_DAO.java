package DAO;

import DTO.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.Info.MAX_PRIMARY_KEY_VALUE;
import static model.Info.MIN_PRIMARY_KEY_VALUE;

public class Customer_DB_DAO extends AbstractCustomerDAO {
    private final Connection connection;

    public Customer_DB_DAO(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public List<Customer> getAllCustomersOrderedByPropertyAndDirection(String property, String direction) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = getQuery(property, direction);

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, MIN_PRIMARY_KEY_VALUE);
            preparedStatement.setInt(2, MAX_PRIMARY_KEY_VALUE);
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

    private static String getQuery(String property, String direction) throws SQLException {
        String baseQuery = "SELECT * FROM Customer WHERE id BETWEEN ? AND ?";

        if (property != null && direction != null) {
            switch (property) {
                case "id" -> baseQuery += " ORDER BY id";
                case "name" -> baseQuery += " ORDER BY name";
                case "city" -> baseQuery += " ORDER BY city";
                case "state" -> baseQuery += " ORDER BY state";
                default -> throw new SQLException("A propriedade " + property + " não existe!");
            }

            switch (direction) {
                case "asc" -> baseQuery += " ASC";
                case "desc" -> baseQuery += " DESC";
                default -> throw new SQLException("A direção " + direction + " não existe!");
            }
        }

        return baseQuery;
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
    public List<Customer> getCustomerByName(String customerName) throws SQLException {
        String query = "SELECT * FROM Customer WHERE id BETWEEN ? AND ? AND name = ?";

        var customersList = new ArrayList<Customer>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, MIN_PRIMARY_KEY_VALUE);
            preparedStatement.setInt(2, MAX_PRIMARY_KEY_VALUE);
            preparedStatement.setString(3, customerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customersList.add(customer);
            }
        }

        return customersList;
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
    public void deleteCustomer(int customerId) throws SQLException {
        validateIfIdIsValid(customerId);

        String query = "DELETE FROM Customer WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        }
    }

    public void validateIfIdIsValid(int id) throws SQLException {
        if (id < MIN_PRIMARY_KEY_VALUE || id > MAX_PRIMARY_KEY_VALUE)
            throw new SQLException("O ID do cliente deve estar entre " + MIN_PRIMARY_KEY_VALUE + " e " + MAX_PRIMARY_KEY_VALUE + "!");
    }

    public int getNextValidId() throws SQLException {
        String query = "SELECT MAX(id) as id FROM Customer WHERE id BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, MIN_PRIMARY_KEY_VALUE);
            preparedStatement.setInt(2, MAX_PRIMARY_KEY_VALUE);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.wasNull()) {
                return MIN_PRIMARY_KEY_VALUE;
            } else {
                resultSet.next();
                resultSet.getInt("id");

                int nextId = resultSet.getInt("id") + 1;
                if (nextId > MAX_PRIMARY_KEY_VALUE)
                    throw new SQLException("O banco de dados está cheio e não pode mais armazenar novos clientes!");

                return nextId;
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao obter o próximo ID válido!", e);
        }
    }
}
