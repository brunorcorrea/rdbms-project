package DAO;

import DTO.Orders;
import RDBMS.MemoryDBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class Order_Mem_DAO extends AbstractOrderDAO {
    private final MemoryDBConnection databaseRef;

    public Order_Mem_DAO(MemoryDBConnection databaseRef) {
        super();
        this.databaseRef = databaseRef;
    }

    @Override
    public List<Orders> getAllOrdersOrderedByNumber() throws SQLException {
        List<Orders> orders = new ArrayList<>(databaseRef.getOrderList());

        orders.sort(Comparator.comparingInt(Orders::getNumber));

        return orders;
    }

    @Override
    public List<Orders> getOrdersByCustomerId(int customerId) throws SQLException {
        List<Orders> orders = new ArrayList<>();
        Iterator<Orders> iterator = databaseRef.getOrderList().iterator();

        while (iterator.hasNext()) {
            Orders buffer = iterator.next();
            if (buffer.getCustomerId() == customerId) {
                orders.add(buffer);
            }
        }
        return orders;
    }

    @Override
    public Orders getOrderByNumber(int orderNumber) throws SQLException {
        Orders order = null;
        Iterator<Orders> iterator = databaseRef.getOrderList().iterator();

        while (iterator.hasNext()) {
            Orders buffer = iterator.next();
            if (buffer.getNumber() == orderNumber) {
                order = buffer;
            }
        }
        return order;
    }

    @Override
    public void addOrder(Orders order) throws SQLException {
        databaseRef.getOrderList().add(order);
    }

    @Override
    public void deleteOrder(int orderNumber) throws SQLException {
        ArrayList<Orders> orders = databaseRef.getOrderList();

        for (int index = 0; index < orders.size(); index++) {
            if (orders.get(index).getNumber() == orderNumber) {
                orders.remove(index);
                break;
            }
        }
    }

    @Override
    public void deleteOrdersByCustomerId(int customerId) throws SQLException {
        ArrayList<Orders> orders = databaseRef.getOrderList();

        for (int index = 0; index < orders.size(); index++) {
            if (orders.get(index).getCustomerId() == customerId) {
                orders.remove(index);
                break;
            }
        }
    }

    @Override
    public int getNextValidNumber() throws SQLException {
        return databaseRef.getOrderList().size() + 1;
    }
}
