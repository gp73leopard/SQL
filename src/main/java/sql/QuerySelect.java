package sql;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static connection.ConnectionDB.connection;

@Getter
public class QuerySelect extends Query {

    private static final Logger logger = Logger.getLogger(QuerySelect.class.getName());

    public Boolean checkExistColumnOrTable(String schema, String table, String column) throws SQLException {
        Boolean exists = null;
        statement = connection().createStatement();
        StringBuilder queryCheckExists = new StringBuilder();
        queryCheckExists.append("SELECT EXISTS (SELECT FROM information_schema.");
        if (column.equals("")) queryCheckExists.append("tables ");
        else queryCheckExists.append("columns ");
        queryCheckExists.append(String.format("WHERE table_schema = '%s' AND table_name = '%s')", schema, table));

        if (!column.equals("")) {
            queryCheckExists.deleteCharAt(queryCheckExists.length() - 1);
            queryCheckExists.append(String.format(" AND column_name = '%s')", column));
        }

        resultSet = statement.executeQuery(queryCheckExists.toString());

        while (resultSet.next()) {
            exists = resultSet.getBoolean(1);
        }
        return exists;
    }

    public ResultSet getAllRowsTable(String table, String[] columns) throws SQLException {
        statement = connection().createStatement();
        resultSet = statement.executeQuery(String.format("SELECT %s FROM %s", String.join(", ", columns), table));
        return resultSet;
    }

    public ResultSet getLimitRowsTable(String table, String[] columns, String[] conditions, int limit) throws SQLException {
        statement = connection().createStatement();
        resultSet = conditions != null ?
                statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s LIMIT %s",
                        String.join(", ", columns), table, String.join(", ", conditions), limit)) :
                statement.executeQuery(String.format("SELECT %s FROM %s LIMIT %s", String.join(", ", columns), table, limit));
        return resultSet;
    }

    public ResultSet getRowsTableAfterCheckingConditionsCase (String table, String[] columns, String[] caseCheck, String nameColumn) throws SQLException {
        statement = connection().createStatement();
        List<String> caseCheckList = Arrays.stream(caseCheck).limit(columns.length-1).collect(Collectors.toList());
        String[] caseCheckStrings = new String[caseCheckList.size()];

        for (int i = 0; i < caseCheckList.size(); i++) {
            caseCheckStrings[i] = caseCheckList.get(i);
        }

        resultSet =
                statement.executeQuery(String.format("SELECT %s, CASE WHEN %s ELSE %s END AS %s FROM %s", String.join(", ", columns),
                String.join(" WHEN ", caseCheckStrings), caseCheck[caseCheck.length-1], nameColumn, table));
        return resultSet;
    }
//
//    SELECT model, price, maker,
//            CASE
//    WHEN price > 70000 THEN 'expensive'
//    WHEN price = 70000 THEN 'reasonably'
//    ELSE 'cheap'
//    END AS pricetext
//    FROM productone;


    public ResultSet getRowsFromTableWithConditions(String table, String[] columns, String[] conditions) throws SQLException {
        StringBuilder columnsString = appendStringColumns(columns);
        StringBuilder conditionsString = appendStringConditions(conditions);
        resultSet = conditions != null ?
                statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s",
                        columnsString, table, conditionsString)) :
                statement.executeQuery(String.format("SELECT %s FROM %s", columnsString, table));
        return resultSet;
    }

    public ResultSet getRowsFromJoinTablesWithConditions(String tableFirst, String tableSecond, String typeJoin,
            String[] columns, String joinColumn, String[] conditions) throws SQLException {
        StringBuilder columnsString = appendStringColumns(columns);
        StringBuilder conditionsString = appendStringConditions(conditions);
        resultSet = conditions != null ?
                statement.executeQuery(String.format("SELECT %s FROM %s j1 %s JOIN %s j2 on j1.%s=j2.%s WHERE %s",
                columnsString, tableFirst, typeJoin, tableSecond, joinColumn, joinColumn, conditionsString)) :
                statement.executeQuery(String.format("SELECT %s FROM %s j1 %s JOIN %s j2 on j1.%s=j2.%s",
                        columnsString, tableFirst, typeJoin, tableSecond, joinColumn, joinColumn));
        return resultSet;
    }

    public ResultSet getRowsFromRelationalAlgebraTablesWithConditions(String tableFirst, String[] conditionsFirstTable,
              String tableSecond, String[] conditionsSecondTable, String typeOperation, String[] columns) throws SQLException {
        StringBuilder columnsString = appendStringColumns(columns);
        StringBuilder conditionsStringFirstTable = appendStringConditions(conditionsFirstTable);
        StringBuilder conditionsStringSecondTable = appendStringConditions(conditionsSecondTable);
        resultSet = conditionsFirstTable != null ? conditionsSecondTable != null ?
                statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s %s SELECT %s FROM %s WHERE %s",
                        columnsString, tableFirst, conditionsStringFirstTable, typeOperation, columnsString, tableSecond, conditionsStringSecondTable)) :
                statement.executeQuery(String.format("SELECT %s FROM %s WHERE %s %s SELECT %s FROM %s ",
                        columnsString, tableFirst, conditionsStringFirstTable, typeOperation, columnsString, tableSecond)) :
                conditionsSecondTable != null ?
                        statement.executeQuery(String.format("SELECT %s FROM %s %s SELECT %s FROM %s WHERE %s",
                                columnsString, tableFirst, typeOperation, columnsString, tableSecond, conditionsStringSecondTable)) :
                        statement.executeQuery(String.format("SELECT %s FROM %s %s SELECT %s FROM %s",
                                columnsString, tableFirst, typeOperation, columnsString, tableSecond));
        return resultSet;
    }

    private StringBuilder appendStringColumns(String[] columns) throws SQLException {
        statement = connection().createStatement();
        StringBuilder columnsString = new StringBuilder();
        columnsString.append(String.join(", ", columns));
        return columnsString;
    }

     private StringBuilder appendStringConditions(String[] conditions) throws SQLException {
        statement = connection().createStatement();
        StringBuilder conditionsString = new StringBuilder();
        if (conditions != null) {
            conditionsString.append(String.join(" AND ", conditions));
        }
        return conditionsString;
    }
}
