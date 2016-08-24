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
import javafx.scene.canvas.Canvas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.scene.control.Button;

/**
 *
 * @author davif
 */
public class Controlador implements Initializable {
    
    @FXML
    private ImageView imagenOriginal;


    @FXML
    private ImageView imagenProcesada;

    @FXML
    private Slider sliderRed;

    @FXML
    private Slider sliderGreen;

    @FXML
    private Slider sliderBlue;

    @FXML
    private Button buttonAccept;

    private WritableImage imagenNueva;
    private PixelReader pr;
    private PixelWriter pw;
    private int width;
    private int height;
    private Image image = null;
    private static double rojo;
    private static double azul;
    private static double verde;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        //algo
    }

    @FXML
    private void abrirImagen(ActionEvent abrirImg) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        boolean t = (image == null);// solo creo un pixelwriter, si no, se genera un error de escritura.
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            image = new Image(new FileInputStream(selectedFile));
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(image));
            javafx.application.Platform.runLater(() -> imagenOriginal.setImage(image));
            pr = this.crearPR();
            if(t)//esta es la t que checo en la linea 49. para solo tener un pixelwriter.
                pw = this.crearPW();
            width = (int)image.getWidth();
            height = (int)image.getHeight();
        }
    }

    //En escencia todos los metodos que siguen son lo mismo, necesito ayuda para saber que es lo que tengo que sincrinizar, si es que se requiere.
    @FXML
    private void filtroPromedioByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,1);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));//Esta parecia una solucion al bug que sucede, solo disminuyo la cantidad de ocirrencias de este.
            }
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRealByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,2);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
            }
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoRByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,3);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
            }

        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoGByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,4);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoBByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,5);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroMicaRoja(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,6);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroMicaVerde(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,7);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroMicaAzul(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,8);
            synchronized(fc){
            fc.start();
            }
            synchronized(this){
            javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroColoresCombinados(ActionEvent event){
        try{
            newWindow("micasCombinadas.fxml","Micas Combinadas");
            System.out.println(rojo);
            FiltrosColores fc = new FiltrosColores(pw,pr,width,height,9,rojo,verde,azul);
            synchronized(fc){
                fc.start();
            }
            synchronized(this){
                javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva)); 
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroColoresCombinadosAux(ActionEvent event){
         rojo = sliderRed.getValue();
         verde = sliderGreen.getValue();
         azul = sliderBlue.getValue();
          Stage stage = (Stage)buttonAccept.getScene().getWindow();
    stage.close();
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

    /**
    *@param msg mensaje del header de la alerta.
    *@param msg1 cuerpo del mensaje de alerta.
    */
    public static void alerta(String msg,String msg1){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(msg1);
        alert.showAndWait();
    }

    private void newWindow(String nombreArchivo, String titulo) throws Exception{
         Parent root = FXMLLoader.load(getClass().getResource(nombreArchivo));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.setResizable(false);

            stage.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}