package sql;

import lombok.Getter;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

import static connection.ConnectionDB.connection;

@Getter
public class QueryDDL extends Query {

    private static final Logger logger = Logger.getLogger(QueryDDL.class.getName());

    public void createTable(String table, String[] strings) throws SQLException {
        statement = connection().createStatement();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE TABLE ");
        stringBuilder.append(table);
        stringBuilder.append("(");
        for (int i = 0; i < strings.length; i++) {
            stringBuilder.append(Arrays.asList(strings).get(i) + ",");
            if (i == strings.length - 1)
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append(")");
        statement.execute(stringBuilder.toString());
        logger.info(String.format("Таблица '%s' создана!", table));
    }

    public void deleteTable(String table) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("DROP TABLE %s", table));
        logger.info(String.format("Таблица '%s' удалена!", table));
    }

    public void addColumnInTable(String table, String columnName, String columnType) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("ALTER TABLE %s ADD %s %s", table, columnName, columnType));
        logger.info(String.format("В таблицу '%s' добавлен столбец '%s'!", table, columnName));
    }

    public void deleteColumnInTable(String table, String columnName) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("ALTER TABLE %s DROP COLUMN %s", table, columnName));
        logger.info(String.format("Из таблицы '%s' удален столбец '%s'!", table, columnName));
    }

    public void deleteData() {
        try {
            deleteTable("productone");
            deleteTable("producttwo");
            dropProcedure("insert_procedure");
        } catch (Exception ignored) {}
    }
}
