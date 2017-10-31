package helloworld.candy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CandyApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CandyPanel.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);

        CandyController controller = fxmlLoader.getController();//获取Controller的实例对象
        controller.setStage(primaryStage);
        controller.setScene(scene);


        primaryStage.setScene(scene);
        primaryStage.setTitle("小工具");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/helloworld/candy/images/seahorse.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
