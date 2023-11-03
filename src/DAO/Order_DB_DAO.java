package DAO;

import DTO.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Order_DB_DAO extends AbstractOrderDAO {
    private final Connection connection;

    public Order_DB_DAO(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public List<Orders> getOrdersByCustomerId(int customerId) throws SQLException {
        validateIfCustomerIdIsValid(customerId);

        List<Orders> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE customerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Orders order = new Orders();
                order.setNumber(resultSet.getInt("number"));
                order.setCustomerId(resultSet.getInt("customerId"));
                order.setDescription(resultSet.getString("description"));
                order.setPrice(resultSet.getBigDecimal("price"));
                orders.add(order);
            }
        }

        return orders;
    }

    @Override
    public Orders getOrderByNumber(int orderNumber) throws SQLException {
        validateIfOrderNumberIsValid(orderNumber);

        String query = "SELECT * FROM Orders WHERE number = ?";
        Orders order = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                order = new Orders();
                order.setNumber(resultSet.getInt("number"));
                order.setCustomerId(resultSet.getInt("customerId"));
                order.setDescription(resultSet.getString("description"));
                order.setPrice(resultSet.getBigDecimal("price"));
            }
        }

        return order;
    }

    @Override
    public void addOrder(Orders order) throws SQLException {
        validateIfCanInsertValue();

        String query = "INSERT INTO Orders (number, customerId, description, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getNumber());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setString(3, order.getDescription());
            preparedStatement.setBigDecimal(4, order.getPrice());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void updateOrder(Orders order) throws SQLException {
        validateIfOrderNumberIsValid(order.getNumber());

        String query = "UPDATE Orders SET customerId = ?, description = ?, price = ? WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, order.getCustomerId());
            preparedStatement.setString(2, order.getDescription());
            preparedStatement.setBigDecimal(3, order.getPrice());
            preparedStatement.setInt(4, order.getNumber());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteOrder(int orderNumber) throws SQLException {
        validateIfOrderNumberIsValid(orderNumber);

        String query = "DELETE FROM Orders WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderNumber);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteOrdersByCustomerId(int customerId) throws SQLException {
        validateIfCustomerIdIsValid(customerId);

        String query = "DELETE FROM Orders WHERE customerId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteAllOrders() throws SQLException {
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;

        String query = "DELETE FROM Orders WHERE id BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, minPrimaryKeyValue);
            preparedStatement.setInt(2, maxPrimaryKeyValue);
            preparedStatement.executeUpdate();
        }
    }

    public void validateIfOrderNumberIsValid(int number) throws SQLException {
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;

        if(number < minPrimaryKeyValue || number > maxPrimaryKeyValue)
            throw new SQLException("O número do pedido deve estar entre " + minPrimaryKeyValue + " e " + maxPrimaryKeyValue + "!");
    }

    public void validateIfCustomerIdIsValid(int id) throws SQLException {
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;

        if (id < minPrimaryKeyValue || id > maxPrimaryKeyValue)
            throw new SQLException("O ID do cliente deve estar entre " + minPrimaryKeyValue + " e " + maxPrimaryKeyValue + "!");
    }

    public void validateIfCanInsertValue() throws SQLException {
        int minPrimaryKeyValue = 10 * 10_000; //group number 10
        int maxPrimaryKeyValue = 10 * 10_000 + 9_999;

        String query = "SELECT MAX(id) as id FROM Orders WHERE id BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, minPrimaryKeyValue);
            preparedStatement.setInt(2, maxPrimaryKeyValue);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                resultSet.getInt("id");

                int nextId = resultSet.getInt("id") + 1;
                if(nextId > maxPrimaryKeyValue)
                    throw new SQLException("O banco de dados está cheio e não pode mais armazenar novos pedidos!");
            }
        }
    }
}
