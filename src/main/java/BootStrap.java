import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BootStrap extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/to_do_list_form.fxml"))));
        stage.setResizable(false);
        //stage.setTitle("Thoga-Kade| DASHBOARD FORM");
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
    }

}
