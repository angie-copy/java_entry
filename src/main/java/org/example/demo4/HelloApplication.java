package org.example.demo4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


public class HelloApplication extends Application {

    private static Connection connection;


    // input fields
    private static TextField titleField;
    private static TextField amountField;
    private static TextField priceField;
    private static ComboBox<String> colorInput;
    private static CheckBox activeForSaleInput;
    public void start(Stage primaryStage) throws Exception{
        // connection
        connectDB();
        // root container
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // input fields
        titleField = new TextField();
        titleField.setPromptText("Title");

        amountField = new TextField();
        amountField.setPromptText("Amount");

        priceField = new TextField();
        priceField.setPromptText("Price");

        String[] colors_arr = {"Red", "Green", "Blue", "Purple", "White"};
        colorInput = new ComboBox(FXCollections.observableArrayList(colors_arr));
        colorInput.setPromptText("Color");

        activeForSaleInput = new CheckBox("Active For Sale");
        activeForSaleInput.setSelected(false);

        // buttons

        // submit button
        Button submitButton = new Button("submit");
        submitButton.setOnAction(e->{insertProduct();});

        // piechart button
        Button showPieChartButton = new Button("Show Pie Chart");
        showPieChartButton.setOnAction(e->{showPieChart();});
        // create main scene
        root.getChildren().addAll(titleField, amountField, priceField, colorInput, activeForSaleInput, submitButton, showPieChartButton);

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();





    }

    private void connectDB(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test3", "root", "");
            System.out.println(connection);
        }
        catch(SQLException e){
            System.out.println("could not connect to db " +e.getMessage());
        }

    }

    private void insertProduct(){
        if(checkFields()){
            showWarning("all fields must be filled");
        }
        else {
            String query = "insert into products (title, amount, price, color, active_for_sale) values (?, ?, ?, ?, ?)";
            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1,titleField.getText());
                ps.setInt(2, Integer.parseInt(amountField.getText()));
                ps.setDouble(3,Double.parseDouble(priceField.getText()));
                ps.setString(4, colorInput.getValue());
                ps.setBoolean(5, activeForSaleInput.isSelected());
                ps.executeUpdate();
                showInformation("new product added");
                clearFields();
            }
            catch(SQLException e){
                System.out.println("could not insert product");
            }
        }

    }

    private void showPieChart(){
        ObservableList<PieChart.Data> pieData =FXCollections.observableArrayList();

        String query = "select * from products";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            // create generic list of Product type
            List<Product> products = new ArrayList<>();
            if(rs!=null){
               // populate list from result set cause result set doesnt not suppor stream api
                while(rs.next()){
                    products.add(new Product(rs.getInt("id"), rs.getString("title"), rs.getInt("amount"), rs.getDouble("price"), rs.getString("color"), rs.getBoolean("active_for_sale")));
                }
                Map<String, Integer> groupedData =  products.stream()
                        .collect(Collectors.groupingBy(Product::getName, Collectors.summingInt(Product::getAmount)));
                groupedData.forEach( (title, amount)->{
                    PieChart.Data data = new PieChart.Data(title, amount);
                    data.setName(title +" "+ amount);
                    pieData.add(data);
                });

                PieChart pieChart = new PieChart(pieData);
                Scene scene = new Scene(pieChart);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();


            }
            else {
                showWarning("no entries found");
            }

        }
        catch(SQLException e) {
            System.out.println("could not show pie chart");
        }

    }


    // inputs validation
    private boolean checkFields(){
        return titleField.getText().isEmpty() ||
                amountField.getText().isEmpty() ||
                priceField.getText().isEmpty() ||
                colorInput.getValue()==null;
    }

    // alert
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("warning");
        alert.setContentText(message);
        alert.show();
    }
    private void showInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.show();
    }

    //clear fields
    private void clearFields(){
        titleField.clear();
        amountField.clear();
        priceField.clear();
        colorInput.setValue(null);
        colorInput.setPromptText("Color");
        activeForSaleInput.setSelected(false);
    }

    public static void main(String[] args){launch(args);};
}

class Product{
    private int id;
    private String name;
    private int amount;
    private double price;
    private String color;
    private boolean active_for_sale;


    public Product(int id, String name, int amount, double price, String color, boolean active_for_sale) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.color = color;
        this.active_for_sale = active_for_sale;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public boolean isActive_for_sale() {
        return active_for_sale;
    }
}
