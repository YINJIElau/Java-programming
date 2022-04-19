package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * display the main view of the game
 */
public class APP extends Application {
    protected Controller controller;
    protected Login login;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root=fxmlLoader.load();
        login=fxmlLoader.getController();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        login.setStage(stage);
        stage.showAndWait();

        FXMLLoader fxmlLoader2=new FXMLLoader();
        fxmlLoader2.setLocation(getClass().getResource("layout.fxml"));
        fxmlLoader2.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root2= fxmlLoader2.load();
        Scene scene2=new Scene(root2);
        primaryStage.setScene(scene2);
        primaryStage.setTitle("hello:"+login.getName());
        primaryStage.show();

        controller=fxmlLoader2.getController();
        controller.setName(login.getName());
        controller.initGraphicalMap();
    }


    public static void main(String[] args) {
        Application.launch(APP.class);
    }
}
