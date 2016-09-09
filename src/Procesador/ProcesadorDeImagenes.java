package Procesador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase Principal.
 * @author davif
 */
public class ProcesadorDeImagenes extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("procesoDigital.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("The Eye");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args de la la linea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}