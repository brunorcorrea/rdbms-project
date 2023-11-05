package DAO;

import DTO.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static model.Info.MAX_PRIMARY_KEY_VALUE;
import static model.Info.MIN_PRIMARY_KEY_VALUE;


public class Order_DB_DAO extends AbstractOrderDAO {
    private final Connection connection;

    public Order_DB_DAO(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public List<Orders> getAllOrdersOrderedByNumber() throws SQLException {
        List<Orders> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE number BETWEEN ? AND ? ORDER BY number";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, MIN_PRIMARY_KEY_VALUE);
            preparedStatement.setInt(2, MAX_PRIMARY_KEY_VALUE);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Orders order = new Orders();
                order.setNumber(resultSet.getInt("number"));
                order.setCustomerId(resultSet.getInt("customerId"));
                order.setDescription(resultSet.getString("description"));
                order.setPrice(resultSet.getBigDecimal("price"));
                orders.add(order);
            }

            return orders;
        }
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
        validateIfOrderNumberIsValid(order.getNumber());

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

    public void validateIfOrderNumberIsValid(int number) throws SQLException {
        if (number < MIN_PRIMARY_KEY_VALUE || number > MAX_PRIMARY_KEY_VALUE)
            throw new SQLException("O número do pedido deve estar entre " + MIN_PRIMARY_KEY_VALUE + " e " + MAX_PRIMARY_KEY_VALUE + "!");
    }

    public void validateIfCustomerIdIsValid(int id) throws SQLException {
        if (id < MIN_PRIMARY_KEY_VALUE || id > MAX_PRIMARY_KEY_VALUE)
            throw new SQLException("O ID do cliente deve estar entre " + MIN_PRIMARY_KEY_VALUE + " e " + MAX_PRIMARY_KEY_VALUE + "!");
    }

    @Override
    public int getNextValidNumber() throws SQLException {
        String query = "SELECT MAX(id) as id FROM Orders WHERE number BETWEEN ? AND ?";

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
