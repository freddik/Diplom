package automatization.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.sql.*;

public class SearchClientController {

    public  static Connection c = null;
    public   static Statement stmt = null;

    @FXML
    private Button button1;

    @FXML
    private TextField idColumn;

    @FXML
    private TextField numberColumn;


    private int id ;
    private int id1;
    private String text1;
    private String text;
    // инициализируем форму данными
    @FXML
    public void initialize() throws SQLException, ClassNotFoundException,ConnectException {
        initData();
    }

    // подготавливаем данные для таблицы
      private void initData() throws SQLException, ClassNotFoundException {

          Class.forName("org.postgresql.Driver");
          c = DriverManager.getConnection("jdbc:postgresql://localhost:5433/postgres", "postgres", "2034489f");
          c.setAutoCommit(false);
          System.out.println("Opened database successfully");
          stmt = c.createStatement();

          button1.setOnAction(e->{
              text1 = idColumn.getText();
              id = Integer.parseInt(text1);
              System.out.println(id);
              String sql = "INSERT INTO dipl (new_column)"  +  "VALUES ("+id+")";

              try {
                  stmt.executeUpdate(sql);

                  stmt.close();
                  c.commit();
              }
              catch (SQLException t) {
                  System.err.println(t.getClass().getName()+": "+t.getMessage());

                   System.exit(0);
              }
               openNewWindow1("/automatization/forms/ViewClientForm.fxml");
              Stage stage = (Stage) button1.getScene().getWindow();
              stage.close();
          });
    }

     public void openNewWindow1(String FXMLFile) {
        //ChildNode child;
        try {
            URL url = getClass().getResource(FXMLFile);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(url);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Pane root =   fxmlLoader.load(url.openStream());
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/automatization/img/icon.png"));
            Scene scene = new Scene(root, 900, 550);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}