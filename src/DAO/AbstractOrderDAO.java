package DAO;

import DTO.Orders;

import java.sql.SQLException;
import java.util.List;

public abstract class AbstractOrderDAO {
    abstract public List<Orders> getOrdersByCustomerId(int customerId) throws SQLException;

    abstract public Orders getOrderByNumber(int orderNumber) throws SQLException;

    abstract public void addOrder(Orders order) throws SQLException;

    abstract public void deleteOrder(int orderNumber) throws SQLException;

    public abstract void deleteOrdersByCustomerId(int customerId) throws SQLException;

    public abstract int getNextValidNumber() throws SQLException;

    public abstract List<Orders> getAllOrdersOrderedByNumber() throws SQLException;
}
