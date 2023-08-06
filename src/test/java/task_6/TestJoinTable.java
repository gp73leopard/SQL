package task_6;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Product;
import sql.QuerySelect;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestJoinTable {
    private final QuerySelect querySelect = new QuerySelect();

    // Найти продукты в таблице ProductTwo, которых нет в таблице ProductOne (использовать подзапрос)
    @Test(priority = 40)
    public void test1() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        String[] conditions = {"model NOT IN (SELECT model FROM ProductOne)"};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("producttwo", columns, conditions);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_6/queryWithSubquery.json"));
        Gson gson = new Gson();
        Product products = gson.fromJson(reader, Product.class);

        ArrayList<ArrayList<String>> listProducts = new ArrayList<>();

        while (rs.next()) {
            int model = rs.getInt(1);
            int price = rs.getInt(2);
            String maker = rs.getString(3);
            ArrayList<String> rowTable = new ArrayList<>();
            rowTable.add(String.valueOf(model));
            rowTable.add(String.valueOf(price));
            rowTable.add(maker);
            listProducts.add(rowTable);
        }

        for (int i = 0; i < products.getModel().size(); i++) {
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(0)), products.getModel().get(i));
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(1)), products.getPrice().get(i));
            Assert.assertEquals(listProducts.get(i).get(2), products.getMaker().get(i));
        }
    }

    @DataProvider(name = "joinTable")
    public Object[][] dataProviderJoinTable(){
        return new Object[][]{
                {"INNER", "src/test/resources/json/task_6/innerJoin.json"},
                {"LEFT", "src/test/resources/json/task_6/leftJoin.json"},
                {"RIGHT", "src/test/resources/json/task_6/rightJoin.json"},
                {"FULL", "src/test/resources/json/task_6/fullJoin.json"}
        };
    }

    // Найти продукты из таблиц ProductOne и ProductTwo, номера моделей которых встречаются в обеих таблицах (также для внешнего соединения)
    @Test(dataProvider = "joinTable", priority = 41)
    public void test2(String typeJoin, String jsonPath) throws SQLException, FileNotFoundException {
        String[] columns = {"*"};
        ResultSet rs = querySelect.getRowsFromJoinTablesWithConditions("productone","producttwo", typeJoin, columns, "model", null);

        JsonReader reader = new JsonReader(new FileReader(jsonPath));
        Gson gson = new Gson();
        Product products = gson.fromJson(reader, Product.class);

        ArrayList<ArrayList<String>> listProducts = new ArrayList<>();

        while (rs.next()) {
            int code = rs.getInt(1);
            int model = rs.getInt(2);
            int price = rs.getInt(3);
            String maker = rs.getString(4);
            int codeJoinTable = rs.getInt(5);
            int modelJoinTable = rs.getInt(6);
            int priceJoinTable = rs.getInt(7);
            String makerJoinTable = rs.getString(8);
            ArrayList<String> rowTable = new ArrayList<>();
            rowTable.add(String.valueOf(code));
            rowTable.add(String.valueOf(model));
            rowTable.add(String.valueOf(price));
            rowTable.add(maker);
            rowTable.add(String.valueOf(codeJoinTable));
            rowTable.add(String.valueOf(modelJoinTable));
            rowTable.add(String.valueOf(priceJoinTable));
            rowTable.add(makerJoinTable);
            listProducts.add(rowTable);
        }

        for (int i = 0; i < products.getModel().size(); i++) {
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(0)), products.getCode().get(i));
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(1)), products.getModel().get(i));
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(2)), products.getPrice().get(i));
            Assert.assertEquals(listProducts.get(i).get(3), products.getMaker().get(i));
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(4)), products.getCodeJoinTable().get(i));
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(5)), products.getModelJoinTable().get(i));
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(6)), products.getPriceJoinTable().get(i));
            Assert.assertEquals(listProducts.get(i).get(7), products.getMakerJoinTable().get(i));
        }
    }

    @AfterMethod
    public void closeSession() throws SQLException {
        querySelect.closeSession();
    }
}
