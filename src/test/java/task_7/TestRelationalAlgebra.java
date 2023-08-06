package task_7;

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

public class TestRelationalAlgebra {
    private final QuerySelect querySelect = new QuerySelect();

    private static final String[] CONDITIONS_FIRST_TABLE_FOR_UNION = {"maker='asus'"};
    private static final String[] CONDITIONS_SECOND_TABLE_FOR_UNION = {"maker='samsung'"};

    @DataProvider(name = "relationalAlgebra")
    public Object[][] dataProviderRelationalAlgebra(){
        return new Object[][]{
                // Отобразить продукты производителей 'asus' из таблицы ProductOne  и 'samsung' из таблицы ProductTwo
                {"UNION", CONDITIONS_FIRST_TABLE_FOR_UNION, CONDITIONS_SECOND_TABLE_FOR_UNION, "src/test/resources/json/task_7/unionProduct.json"},
                // Отобразить продукты из таблиц ProductOne и ProductTwo, номера моделей которых сопадают
                {"INTERSECT", null, null, "src/test/resources/json/task_7/intersectProduct.json"},
                // Исключить продукты из таблицы ProductOne, которые есть в таблице ProductTwo и отобразить их
                {"EXCEPT", null, null, "src/test/resources/json/task_7/exceptProduct.json"}
        };
    }

    @Test(dataProvider = "relationalAlgebra", priority = 50)
    public void test1(String typeOperation, String[] conditionsFirstTable, String[] conditionsSecondTable, String jsonPath) throws SQLException, FileNotFoundException {
        String[] columns = {"model", "price", "maker"};
        ResultSet rs = querySelect.getRowsFromRelationalAlgebraTablesWithConditions("productone", conditionsFirstTable, "producttwo", conditionsSecondTable, typeOperation, columns);

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
