package Procesador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.*;
import java.io.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.image.WritableImage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;

/**
 *
 * @author davif
 */
public class Controlador implements Initializable {
    
    @FXML
    private ImageView imagenOriginal;


    @FXML
    private ImageView imagenProcesada;

    private WritableImage imagenNueva;
    private PixelReader pr;
    private PixelWriter pw;
    private int width;
    private int height;
    private Image image = null;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //algo
    }

    @FXML
    private void abrirImagen(ActionEvent abrirImg) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        boolean t = (image == null);
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            image = new Image(new FileInputStream(selectedFile));
            Platform.runLater(() -> imagenProcesada.setImage(image));
            Platform.runLater(() -> imagenOriginal.setImage(image));
            pr = this.crearPR();
            if(t)
                pw = this.crearPW();
            width = (int)image.getWidth();
            height = (int)image.getHeight();
        }
    }

    @FXML
    private void filtroPromedioByN(ActionEvent event) throws Exception{
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,1);
            synchronized(this){
            fc.start();
            }
            Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRealByN(ActionEvent event) throws Exception{
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,2);
            synchronized(this){
            fc.start();
            }
            Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoRByN(ActionEvent event) throws Exception{
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,3);
            synchronized(this){
            fc.start();
            }
            Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoGByN(ActionEvent event) throws Exception{
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,4);
            synchronized(this){
            fc.start();
            }
            Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoBByN(ActionEvent event) throws Exception{
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,5);
            synchronized(this){
            fc.start();
            }
            Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    private PixelWriter crearPW(){
        imagenNueva = new WritableImage((int)image.getWidth(),(int)image.getHeight());
        PixelWriter pw = imagenNueva.getPixelWriter();
        return pw;
    }

    private PixelReader crearPR(){
        PixelReader pr = image.getPixelReader();
        return pr;
    }

    public static void alerta(String msg,String msg1){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(msg1);
        alert.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}