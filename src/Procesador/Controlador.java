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
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //algo
    }

    @FXML
    private void abrirImagen(ActionEvent abrirImg) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(new FileInputStream(selectedFile));
            imagenOriginal.setImage(image);
            imagenProcesada.setImage(image);
        }
    }

    @FXML
    private void filtroPromedioByN(ActionEvent event){
        Image img = imagenOriginal.getImage();
        int width = (int)img.getWidth();
        int height = (int)img.getHeight();
        PixelWriter pw = this.crearPW();
        PixelReader pr = this.crearPR();
        FiltrosColores fc = new FiltrosColores(pw,pr,width,height,1);
        fc.start();
        imagenProcesada.setImage(imagenNueva);
        pw = null;
        pr = null;
        imagenNueva = null;
        fc = null;
    }

    @FXML
    private void filtroRealByN(ActionEvent event){
        Image img = imagenOriginal.getImage();
        int width = (int)img.getWidth();
        int height = (int)img.getHeight();
        PixelWriter pw = this.crearPW();
        PixelReader pr = this.crearPR();
        FiltrosColores fc = new FiltrosColores(pw,pr,width,height,2);
        fc.start();
        imagenProcesada.setImage(imagenNueva);
        pw = null;
        pr = null;
        imagenNueva = null;
        fc = null;
    }

    private PixelWriter crearPW(){
        Image img = imagenProcesada.getImage();
        imagenNueva = new WritableImage((int)img.getWidth(),(int)img.getHeight());
        PixelWriter pw = imagenNueva.getPixelWriter();
        return pw;
    }

    private PixelReader crearPR(){
        Image img = imagenOriginal.getImage();
        PixelReader pr = img.getPixelReader();
        return pr;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}