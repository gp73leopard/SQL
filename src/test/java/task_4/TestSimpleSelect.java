package task_4;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.Product;
import sql.QuerySelect;

import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestSimpleSelect {

    private final QuerySelect querySelect = new QuerySelect();

    // Найти все модели, кроме 102, 103, 104 из таблицы ProductOne. Ответ отсортируйте по убыванию номера модели
    private final String CONDITION_NOT_IN = "model NOT IN ('102', '103', '104') ORDER BY model DESC";
    // Отобразить строки из таблицы ProductOne у которых цена (price) больше 55000, а производитель (maker) с названием, начинающимся на 'a' и на 'l'
    private final String CONDITION_LIKE_AND_MORE = "price > 55000 AND (maker LIKE 'a%' OR maker LIKE 'l%')";
    // Отобразить строки из таблицы ProductOne, которые находятся в диапазоне цены (price) от  55000 до 60000
    private final String CONDITION_BETWEEN = "price BETWEEN 55000 AND 60000";

    @DataProvider(name = "selectSimple")
    public Object[][] dataProviderSelectSimple() {
        return new Object[][]{
                {CONDITION_NOT_IN, "src/test/resources/json/task_4/notIn102_103_104_OrderByDesk.json"},
                {CONDITION_LIKE_AND_MORE, "src/test/resources/json/task_4/checkLikeAndMore55k.json"},
                {CONDITION_BETWEEN, "src/test/resources/json/task_4/checkBetween55k_60k.json"}
        };
    }

    @Test(dataProvider = "selectSimple", priority = 20)
    public void test1(String condition, String jsonPath) throws SQLException, IOException {
        String[] columns = {"model", "price", "maker"};
        String[] conditions = {condition};
        ResultSet rs = querySelect.getRowsFromTableWithConditions("productone", columns, conditions);

        JsonReader reader = new JsonReader(new FileReader(jsonPath));
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

    @AfterMethod
    public void closeSession() throws SQLException {
        querySelect.closeSession();
    }
}
