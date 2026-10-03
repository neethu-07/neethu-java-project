package FoodOrderManager;

import java.sql.Connection;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) throws Exception {
        Connection c = DBConnection.getConnection();

        // Table 1: Menu Items
        String createMenuItemsTable = """
            CREATE TABLE IF NOT EXISTS Menu (
                item_id INT PRIMARY KEY,
                item_name VARCHAR(100),
                price DOUBLE
            );
            """;

        // Table 2: Customers
        String createCustomerTable = """
            CREATE TABLE IF NOT EXISTS Customer (
                customer_id INT PRIMARY KEY,
                name VARCHAR(100),
                phone VARCHAR(15)
            );
            """;

        // Table 3: Orders
        String createOrdersTable = """
            CREATE TABLE IF NOT EXISTS Orders (
                order_id INT PRIMARY KEY,
                customer_id INT,
                item_id INT,
                quantity INT,
                total_price DOUBLE,
                FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
                FOREIGN KEY (item_id) REFERENCES Menu(item_id)
            );
            """;

        Statement s = c.createStatement();
        s.executeUpdate(createMenuItemsTable);
        s.executeUpdate(createCustomerTable);
        s.executeUpdate(createOrdersTable);

        System.out.println("All Tables Created Successfully");
    }
}
