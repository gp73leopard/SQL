package task_9;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pojo.Product;
import sql.QuerySelect;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestAdditionalFunctions {
    private final QuerySelect querySelect = new QuerySelect();

    // Отобразить первые 3 записи таблицы ProductOne
    @Test(priority = 70)
    public void test1() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        ResultSet rs = querySelect.getLimitRowsTable("productone", columns, null, 3);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_9/limitRows.json"));
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

    // Найти продукты из таблицы ProductOne, стоимость которых превышает стоимость любого продукта из таблицы ProductTwo
    @Test(priority = 71)
    public void test2() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        String[] conditions = {"price > ALL (SELECT price FROM producttwo)"};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", columns, conditions);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_9/priceMoreAll.json"));
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

    // Найти продукты из таблицы ProductOne, стоимость которых превышает стоимость хотя бы одного продукта из таблицы ProductTwo
    @Test(priority = 72)
    public void test3() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        String[] conditions = {"price > ANY (SELECT price FROM producttwo)"};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", columns, conditions);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_9/priceMoreAny.json"));
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

    // Вывести продукты из таблицы ProductOne и добавить текст (в новый столбец 'pricetext') в зависимости от цены
    @Test(priority = 73)
    public void test4() throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        String[] caseChecks = {"price > 70000 THEN 'expensive'", "price = 70000 THEN 'reasonably'", "'cheap'"};
        ResultSet rs = querySelect.getRowsTableAfterCheckingConditionsCase("productone", columns, caseChecks, "pricetext");

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_9/addColumnsPriceText.json"));
        Gson gson = new Gson();
        Product products = gson.fromJson(reader, Product.class);
        ArrayList<ArrayList<String>> listProducts = new ArrayList<>();

        while (rs.next()) {
            int model = rs.getInt(1);
            int price = rs.getInt(2);
            String maker = rs.getString(3);
            String pricetext = rs.getString(4);
            ArrayList<String> rowTable = new ArrayList<>();
            rowTable.add(String.valueOf(model));
            rowTable.add(String.valueOf(price));
            rowTable.add(maker);
            rowTable.add(pricetext);
            listProducts.add(rowTable);
        }

        for (int i = 0; i < products.getModel().size(); i++) {
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(0)), products.getModel().get(i));
            Assert.assertEquals(Integer.parseInt(listProducts.get(i).get(1)), products.getPrice().get(i));
            Assert.assertEquals(listProducts.get(i).get(2), products.getMaker().get(i));
            Assert.assertEquals(listProducts.get(i).get(3), products.getPriceText().get(i));
        }
    }

    // Напишите хранимую процедуру на добавление записи в таблицу ProductOne. После вызовите ее и проверьте по таблице, что запись добавилась
    @Test(priority = 74)
    public void test5() throws SQLException, FileNotFoundException {
        String[] columnsProcedure = {"modelValue integer", "priceValue integer", "makerValue varchar(25)"};
        String[] values = {"111", "120000", "'razer'"};
        String[] columnsProduct = {"111", "120000", "'razer'"};
        String[] conditions = {"model=111", "price=120000", "maker='razer'"};

        querySelect.createProcedure("insert_procedure", columnsProcedure,
                "INSERT INTO ProductOne (model, price, maker) VALUES (modelValue, priceValue, makerValue)");
        querySelect.callProcedure("insert_procedure", values);

        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", columnsProduct, conditions);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_9/valueInsertProcedure.json"));
        Gson gson = new Gson();
        Product products = gson.fromJson(reader, Product.class);
        ArrayList<ArrayList<String>> listProducts = new ArrayList<>();

        int model = 0;
        int price = 0;
        String maker = null;

        while (rs.next()) {
            model = rs.getInt(1);
            price = rs.getInt(2);
            maker = rs.getString(3);
            ArrayList<String> rowTable = new ArrayList<>();
            rowTable.add(String.valueOf(model));
            rowTable.add(String.valueOf(price));
            rowTable.add(maker);
            listProducts.add(rowTable);
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
