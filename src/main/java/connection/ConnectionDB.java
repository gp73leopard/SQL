package connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionDB {
    private static final String DB_URL = Config.getDbConfigurations().dbUrl();
    private static final String DB_USER = Config.getDbConfigurations().dbUser();
    private static final String DB_PASS = Config.getDbConfigurations().dbPass();

    private static final Logger logger = Logger.getLogger(ConnectionDB.class.getName());

    public static java.sql.Connection connection() {

        logger.info("Testing connection to PostgreSQL JDBC");
        java.sql.Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.info("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }

        logger.info("PostgreSQL JDBC Driver successfully connected");


        try {
            connection = DriverManager
                    .getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (SQLException e) {
            logger.info("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            logger.info("You successfully connected to database now");
        } else {
            logger.info("Failed to make connection to database");
        }

        return connection;
    }

}
