package task_8;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pojo.Product;
import sql.QueryDML;
import sql.QuerySelect;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestDML {
    private final QuerySelect querySelect = new QuerySelect();
    private final QueryDML queryDML = new QueryDML();

    // Добавить записи в таблицу ProductOne из таблицы ProductTwo с номерами моделей, которых не было в ProductOne
    @Test(priority = 60)
    public void test1() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        String[] conditions = {"model NOT IN (SELECT model FROM ProductOne)"};
        queryDML.addValuesInTableFromTable("productone", "producttwo", columns, conditions);
        ResultSet rs = querySelect.getAllRowsTable("productone", columns);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_8/newProductOneAllValues.json"));
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

    // Необходимо удалить все строки из таблицы ProductOne, кроме строки с наименьшим кодом (столбец code) для каждой группы ProductOne с одинаковым номером модели
    @Test(priority = 61)
    public void test2() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        String[] conditions = {"code NOT IN (SELECT MIN(code) FROM ProductOne GROUP BY model)"};
        queryDML.deleteValuesFromTable("productone", conditions);
        ResultSet rs = querySelect.getAllRowsTable("productone", columns);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_8/ValuesAfterDeleteProductOneAllValues.json"));
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

    // Измените, данные в таблице ProductOne так, чтобы номера моделей были увеличены на 10, для производителя apple
    @Test(priority = 62)
    public void test3() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        String[] conditions = {"maker='apple'"};
        queryDML.updateValuesInTable("productone", "model=model+10", conditions);
        ResultSet rs = querySelect.getAllRowsTable("productone", columns);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_8/updateValueProductOne.json"));
        Gson gson = new Gson();
        Product products = gson.fromJson(reader, Product.class);

        int model = 0;
        int price = 0;
        String maker = "";

        while (rs.next()) {
            model = rs.getInt(1);
            price = rs.getInt(2);
            maker = rs.getString(3);
        }

        Assert.assertEquals(model, products.getModel().get(0));
        Assert.assertEquals(price, products.getPrice().get(0));
        Assert.assertEquals(maker, products.getMaker().get(0));
    }

    @AfterMethod
    public void closeSession() throws SQLException {
        querySelect.closeSession();
    }
}
