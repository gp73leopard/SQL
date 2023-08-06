package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import static connection.ConnectionDB.connection;

public class Query {
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    private static final Logger logger = Logger.getLogger(Query.class.getName());

    public void createProcedure(String procedureName, String[] columns, String procedure) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("CREATE PROCEDURE %s (%s) LANGUAGE SQL AS $$ %s $$",
                        procedureName, String.join(", ", columns), procedure));
        logger.info(String.format("В базе данных создана процедура '%s'", procedureName));
    }

    public void dropProcedure(String procedureName) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("DROP PROCEDURE %s",procedureName));
        logger.info(String.format("Проедура '%s' удалена", procedureName));
    }

    public void callProcedure(String procedureName, String[] values) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("CALL %s (%s)",
                procedureName, String.join(", ", values)));
        logger.info(String.format("Вызвана процедура '%s'", procedureName));
    }

    public void closeSession() throws SQLException {
        if (statement != null) {
            statement.close();
            logger.info("Объект Statement закрыт");
            if (resultSet != null) {
                resultSet.close();
                logger.info("ResultSet закрыт");
            }
        }
    }
}
