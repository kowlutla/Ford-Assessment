package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class is used to establish Database Connection
 */
public class DBConnection {

    /**
     * Method to Create Connection to database and
     *
     * @return Connection Object if it creates
     * Otherwise it will raise exception and return null
     */
    public static Connection getDBConnection() {
        String url = "jdbc:h2:~/assessment";
        String user = "MKOWLUTL";
        String password = "12345678";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
