package helloworld.step1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class MainPanelController {
    @FXML
    private Button helloBtn;

    @FXML
    protected void handleButtonAction(ActionEvent actionEvent) {
        helloBtn.setText("Hello World ! I am javaFx on Controller!");
    }
}
