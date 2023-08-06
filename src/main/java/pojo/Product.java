package pojo;

import lombok.Data;
import java.util.List;

@Data
public class Product {
    private List<Integer> code;
    private List<Integer> model;
    private List<Integer> price;
    private List<String> maker;
    private List<Integer> codeJoinTable;
    private List<Integer> modelJoinTable;
    private List<Integer> priceJoinTable;
    private List<String> makerJoinTable;
    private List<String> priceText;
    private int qty;
    private int min_price;
    private int max_price;
    private double avg_price;
    private int count_model;
    private int sum_price;
}
