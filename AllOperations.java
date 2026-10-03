package FoodOrderManager;

import java.sql.*;

public class AllOperations {
    public static final Connection c = DBConnection.getConnection();

    // 1. Populate the Food Menu
    public static void addMenuItems() {
        String query = "INSERT INTO Menu VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = c.prepareStatement(query);

            // Item 1
            ps.setInt(1, 1);
            ps.setString(2, "Veg Burger");
            ps.setDouble(3, 120.00);
            ps.executeUpdate();

            // Item 2
            ps.setInt(1, 2);
            ps.setString(2, "Chicken Pizza");
            ps.setDouble(3, 350.00);
            ps.executeUpdate();

            // Item 3
            ps.setInt(1, 3);
            ps.setString(2, "Paneer Butter Masala");
            ps.setDouble(3, 220.00);
            ps.executeUpdate();

            System.out.println("Menu items inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Add Customer Details
    public static void addCustomer(int id, String name, String phone) {
        String query = "INSERT INTO Customer VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, phone);
            ps.executeUpdate();
            System.out.println("Customer registered: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. View the Food Menu
    public static void viewMenu() {
        String query = "SELECT * FROM Menu";
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            System.out.println("\n--- FOOD MENU ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("item_id") +
                                   " | Item: " + rs.getString("item_name") +
                                   " | Price: ₹" + rs.getDouble("price"));
            }
            System.out.println("-----------------\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Place Order & Calculate Total
    public static void placeOrder(int orderId, int customerId, int itemId, int quantity) {
        String getPriceQuery = "SELECT price FROM Menu WHERE item_id = ?";
        String insertOrderQuery = "INSERT INTO Orders VALUES (?, ?, ?, ?, ?)";

        try {
            // Fetch item price
            PreparedStatement pricePs = c.prepareStatement(getPriceQuery);
            pricePs.setInt(1, itemId);
            ResultSet rs = pricePs.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                double totalPrice = price * quantity; // Calculate Total

                // Insert into Orders table
                PreparedStatement orderPs = c.prepareStatement(insertOrderQuery);
                orderPs.setInt(1, orderId);
                orderPs.setInt(2, customerId);
                orderPs.setInt(3, itemId);
                orderPs.setInt(4, quantity);
                orderPs.setDouble(5, totalPrice);

                orderPs.executeUpdate();
                System.out.println("Order placed successfully! Total Amount: ₹" + totalPrice);
            } else {
                System.out.println("Item ID not found in menu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method to test operations
    public static void main(String[] args) {
        // Step 1: Add initial menu items
        addMenuItems();

        // Step 2: Register a customer
        addCustomer(501, "Rahul Sharma", "9876543210");

        // Step 3: Display menu
        viewMenu();

        // Step 4: Place an order (OrderId: 1001, CustomerId: 501, ItemId: 2, Quantity: 2)
        placeOrder(1001, 501, 2, 2);
    }
}
