package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;

public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btnSend;


    @FXML
    TextField rec1;

    @FXML
    TextField rec2;

    @FXML
    TextField rec3;



    public void sendMsg (){
        textArea.appendText(textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();
    }

}
