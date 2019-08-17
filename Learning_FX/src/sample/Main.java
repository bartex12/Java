package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stage;
    private FlowPane pane;
    private Scene scene;
    private Label label;

    @Override
    public void start(Stage stage) throws Exception{
        this.stage = stage;
        pane = new FlowPane();
        label = new Label(" Привет, мир!");
        pane.getChildren().add(label);
        scene = new Scene(pane,640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
