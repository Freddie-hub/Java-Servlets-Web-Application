import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // JDBC URL with embedded username and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/loggy_db?user=root"; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    public static void main(String[] args) {
        try {
            // Establish a connection
            Connection connection = getConnection();
            System.out.println("Database connected successfully!");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}
