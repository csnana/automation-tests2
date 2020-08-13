package utilities.readers;

/**
 * @author sajeev rajagopalan
 * project autoFrame, this class will help us to perform database query operations and
 * return the results as result set
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseReader {

    public static Connection connection;
    public static Statement statement;

    public ResultSet getQueryResult(String connection, String username, String password, String query) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            DatabaseReader.connection = DriverManager.getConnection(
                    connection, username, password);

            System.out.println("connection established");
            statement = DatabaseReader.connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            DatabaseReader.connection.close();
            return rs;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
