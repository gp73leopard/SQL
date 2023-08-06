package task_5;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pojo.Product;
import sql.QuerySelect;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestAggregateFunctions {
    private final QuerySelect querySelect = new QuerySelect();

    // Найти количество продуктов из таблицы ProductOne, выпущенных производителем Lenovo и отобразить их в столбце qty
    @Test(priority = 30)
    public void test1() throws SQLException, IOException {
        String[] aggFun = {"COUNT(*) AS qty"};
        String[] aggAddCond = {"maker='lenovo'"};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", aggFun, aggAddCond);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_5/qtyLenovo.json"));
        Gson gson = new Gson();
        Product product = gson.fromJson(reader, Product.class);

        int qty = 0;
        while (rs.next()) {
            qty = rs.getInt(1);
        }
        Assert.assertEquals(rs.getMetaData().getColumnName(1), "qty");
        Assert.assertEquals(qty, product.getQty());
    }

    // Найти минимальную, максимальную и среднюю (округлить до 2 знаков после запятой) цену на продукты из таблицы ProductOne
    @Test(priority = 31)
    public void test2() throws SQLException, IOException {
        String[] aggFun = {"MIN(price) AS min_price", "MAX(price) AS max_price", "ROUND(AVG(price), 2) AS avg_price"};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", aggFun, null);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_5/min_max_avg_price.json"));
        Gson gson = new Gson();
        Product product = gson.fromJson(reader, Product.class);

        int min_price = 0;
        int max_price = 0;
        double avg_price = 0;
        while (rs.next()) {
            min_price = rs.getInt(1);
            max_price = rs.getInt(2);
            avg_price = rs.getDouble(3);
        }
        Assert.assertEquals(min_price, product.getMin_price());
        Assert.assertEquals(max_price, product.getMax_price());
        Assert.assertEquals(avg_price, product.getAvg_price());
    }

    // Найти количество различных моделей из таблицы ProductOne
    @Test(priority = 32)
    public void test3() throws SQLException, IOException {
        String[] aggFun = {"COUNT(DISTINCT model) AS count_model"};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", aggFun, null);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_5/countDifferentModel.json"));
        Gson gson = new Gson();
        Product product = gson.fromJson(reader, Product.class);

        int countDifferentModel = 0;
        while (rs.next()) {
            countDifferentModel = rs.getInt(1);
        }
        Assert.assertEquals(countDifferentModel, product.getCount_model());
    }

    // Найти сумму цен продуктов из таблицы ProductOne, число моделей которых не превышает двух
    @Test(priority = 33)
    public void test4() throws SQLException, IOException {
        String[] aggFun = {"SUM (price) AS sum_price"};
        String[] aggAddCond = {"model IN (SELECT model FROM ProductOne GROUP BY model HAVING COUNT (model) <=2)"};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", aggFun, aggAddCond);

        JsonReader reader = new JsonReader(new FileReader("src/test/resources/json/task_5/sumPrice.json"));
        Gson gson = new Gson();
        Product product = gson.fromJson(reader, Product.class);

        int sumPrice = 0;
        while (rs.next()) {
            sumPrice = rs.getInt(1);
        }
        Assert.assertEquals(sumPrice, product.getSum_price());
    }

    @AfterMethod
    public void closeSession() throws SQLException {
        querySelect.closeSession();
    }
}
