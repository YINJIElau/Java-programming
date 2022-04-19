package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
/*
* controller of the login.fxml,let players to specify their name
* */
public class Login implements Initializable {

    @FXML
    private TextField input_name;
    @FXML
    private Button submit_button;
    private Stage stage;
    private String userName;
    /**
    * set the stage of this scene,so that we can close it in the controller.
    * */
    public void setStage(Stage stage){
        this.stage=stage;
    }
    /**
     *get the name player input.
     * @return the name of player
    * */
    public String getName(){
        return userName;
    }

    /**
     *implement button monitoring in the initialize method.
     * get the username, or set default username if player input nothing
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        submit_button.setOnAction(v->{
            userName=input_name.getText();
            if(userName==null){
                userName="anonymous player";
            }
            stage.close();
        });
    }
}
