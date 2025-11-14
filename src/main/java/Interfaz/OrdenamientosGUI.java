package Interfaz;

import javafx.application.Application;
import javafx.stage.Stage;

public class OrdenamientosGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        OrdenamientosController controller = new OrdenamientosController();
        controller.iniciar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}