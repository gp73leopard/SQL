package sql;

import lombok.Getter;

import java.sql.SQLException;
import java.util.logging.Logger;

import static connection.ConnectionDB.connection;

@Getter
public class QueryDML extends Query {

    private static final Logger logger = Logger.getLogger(QueryDML.class.getName());

    public void addValuesInTableFromJson(String table, String[] columns, String values) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("INSERT INTO %s(%s) VALUES %s", table, String.join(", ", columns), values));
        logger.info(String.format("В таблицу '%s' добавлены значения:\n%s", table, values));
    }

    public void addValuesInTableFromTable(String tableFirst, String tableSecond, String[] columns, String[] conditions) throws SQLException {
        statement = connection().createStatement();

        String stringBuilder = conditions != null ? String.format("INSERT INTO %s(%s) SELECT %s FROM %s WHERE %s", tableFirst,
                String.join(", ", columns), String.join(", ", columns), tableSecond, String.join(" AND ", conditions)) :
                String.format("INSERT INTO %s(%s) SELECT %s FROM %s", tableFirst,
                        String.join(", ", columns), String.join(", ", columns), tableSecond);

        statement.execute(stringBuilder);
        logger.info(String.format("В таблицу '%s' добавлены значения из таблицы '%s'", tableFirst, tableSecond));
    }

    public void deleteValuesFromTable(String table, String[] conditions) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("DELETE FROM %s WHERE %s", table, String.join(" AND ", conditions)));
        logger.info(String.format("Из таблицы '%s' удалены значения по условию:\n%s", table, String.join(" AND ", conditions)));
    }

    public void updateValuesInTable(String table, String changeColumns, String[] conditions) throws SQLException {
        statement = connection().createStatement();
        statement.execute(String.format("UPDATE %s SET %s WHERE %s", table, changeColumns, String.join(" AND ", conditions)));
        logger.info(String.format("Значения таблицы '%s' изменены по условию:\n%s", table, String.join(" AND ", changeColumns)));
    }
}
