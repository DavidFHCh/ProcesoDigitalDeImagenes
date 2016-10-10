package Procesador;

import java.util.*;
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
import javafx.scene.control.TextField;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.control.CheckBox;

/**
 *
 * @author davif
 */
public class Controlador implements Initializable {
    
    @FXML private ImageView imagenOriginal;

    @FXML private ImageView imagenProcesada;

    @FXML private Slider sliderRed;

    @FXML private Slider sliderGreen;

    @FXML private Slider sliderBlue;

    @FXML private Slider sliderBrillo;

    @FXML private Button buttonAccept;

    @FXML private Button buttonAccept1;

    @FXML private Button buttonAccept2;

    @FXML private TextField alto;

    @FXML private TextField ancho;

    @FXML private TextField numImagenesAncho;

    @FXML private TextField numImagenesAlto;

    @FXML private TextField numPixelesAncho;

    @FXML private TextField numPixelesAlto;

    @FXML private TextField nombreArchivoSopa;

    @FXML private CheckBox grisSopa;

    @FXML private TextField frase;


    private WritableImage imagenNueva;
    private PixelReader pr;
    private PixelWriter pw;
    private int width;
    private int height;
    private Image image = null;
    private static double rojo;
    private static double azul;
    private static double verde;
    private static double brillo;
    private static int alto1;
    private static int ancho1;
    private static int numImagenesAncho1;
    private static int numImagenesAlto1;
    private static int numPixelesAncho1;
    private static int numPixelesAlto1;    
    private static int escalaDeImagenes1; 
    private static String nombreArchivoSopaS;
    private static boolean grisSopaB;
    private static String fraseS;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        Stage secondStage = new Stage();     
        File file = fileChooser.showSaveDialog(secondStage);
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imagenProcesada.getImage(), null), "png", file);
                } catch (IOException ex) {
                    Logger.getLogger(
                    Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
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
            if(t)
                pw = this.crearPW();
            width = (int)image.getWidth();
            height = (int)image.getHeight();
        }
    }

    //En escencia todos los metodos que siguen son lo mismo, necesito ayuda para saber que es lo que tengo que sincrinizar, si es que se requiere.
    @FXML
    private void filtroPromedioByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,1);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRealByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,2);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoRByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,3);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoGByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,4);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRepetidoBByN(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,5);
           synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroMicaRoja(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,6);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroMicaVerde(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,7);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroMicaAzul(ActionEvent event){
        try{
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,8);
           synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroColoresCombinados(ActionEvent event){
        try{
            newWindow("micasCombinadas.fxml","Micas Combinadas");
            FiltrosColores fc = new FiltrosColores(crearPW(),pr,width,height,9,rojo,verde,azul);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
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

    @FXML
    private void filtroMosaico(ActionEvent event){
        try{
            newWindow("mosaicos.fxml","Mosaicos");
            Mosaico mos = new Mosaico(crearPW(),pr,width,height,alto1,ancho1);
           synchronized(mos){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    mos.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroMosaicoAux(ActionEvent event){
        alto1 = Integer.parseInt(alto.getCharacters().toString());
        ancho1 = Integer.parseInt(ancho.getCharacters().toString());
        Stage stage = (Stage)buttonAccept1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtroAltoContraste(ActionEvent event){
        try{
            AltoContraste fc = new AltoContraste(crearPW(),pr,width,height,1);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroAltoContrasteInverso(ActionEvent event){
        try{
            AltoContraste fc = new AltoContraste(crearPW(),pr,width,height,2);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroBrillo(ActionEvent event){
        try{
            newWindow("Brillo.fxml","Brillo");
            Brillo fc = new Brillo(crearPW(),pr,width,height,brillo/255);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }



    @FXML
    private void filtroBrilloAux(ActionEvent event){
        brillo = sliderBrillo.getValue();
        Stage stage = (Stage)buttonAccept2.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtroConvolucionOrignal(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,1);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionBlur(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,2);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionMoreBlur(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,3);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroConvolucionMotionBlur(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,4);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionHorizontalEdges(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,5);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroConvolucionVerticalEdges(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,6);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroConvolucionDegreeEdges(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,7);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionAllEdges(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,8);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionSharpen(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,9);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionSubtleSharpen(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,10);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroConvolucionExcessSharpen(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,11);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroConvolucionEmboss(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,12);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionExaggeratedEmboss(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,13);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroConvolucionMean(ActionEvent event){
        try{
            Convolucion fc = new Convolucion(crearPW(),pr,width,height,14);
            synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    fc.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
            
        }catch(Exception e){
             alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

     @FXML
    private void filtroReduccion(ActionEvent event){
        try{
            newWindow("Reducir.fxml","Reduccion");
            Reducir redu = new Reducir(crearPW(ancho1,alto1),pr,width,height,alto1,ancho1);
           synchronized(redu){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    redu.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroReduccionAux(ActionEvent event){
        alto1 = Integer.parseInt(alto.getCharacters().toString());
        ancho1 = Integer.parseInt(ancho.getCharacters().toString());
        Stage stage = (Stage)buttonAccept1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtroRecursivoByN(ActionEvent event){
        try{
            newWindow("Recursivo.fxml","Filtro Recursivo Blanco Y Negro");
            int x = width * (numPixelesAncho1/numImagenesAncho1);
            int y = height * (numPixelesAlto1/numImagenesAlto1);
            System.out.println(x+" "+y);
            FiltroRecursivoByN mos = new FiltroRecursivoByN(crearPW(x,y),pr,width,height,numImagenesAlto1,numImagenesAncho1,numPixelesAlto1,numPixelesAncho1);
           synchronized(mos){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    mos.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            e.printStackTrace();
            //alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroRecursivoAux(ActionEvent event){
        numImagenesAlto1 = Integer.parseInt(numImagenesAlto.getCharacters().toString());
        numImagenesAncho1 = Integer.parseInt(numImagenesAncho.getCharacters().toString());
        numPixelesAlto1 = Integer.parseInt(numPixelesAlto.getCharacters().toString());
        numPixelesAncho1 = Integer.parseInt(numPixelesAncho.getCharacters().toString());
        Stage stage = (Stage)buttonAccept1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtroRecursivoColor(ActionEvent event){
        try{
            newWindow("Recursivo.fxml","Filtro Recursivo Colores Reales");
            int x = width * (numPixelesAncho1/numImagenesAncho1);
            int y = height * (numPixelesAlto1/numImagenesAlto1);
            System.out.println(x+" "+y);
            FiltroRecursivoColor mos = new FiltroRecursivoColor(crearPW(x,y),pr,width,height,numImagenesAlto1,numImagenesAncho1,numPixelesAlto1,numPixelesAncho1);
           synchronized(mos){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    mos.run();
                    
                    synchronized(imagenProcesada){
                        javafx.application.Platform.runLater(() -> imagenProcesada.setImage(imagenNueva));
                    }
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            e.printStackTrace();
            //alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroSopaUnaLetra(ActionEvent event){
        try{
            newWindow("sopaDeLetrasUnaLetra.fxml","Sopa de Letras.");
            FiltroSopaDeLetras fc = new FiltroSopaDeLetras(pr,width,height,4,4);
           synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    String result = fc.run(grisSopaB);

                    PrintWriter writer = new PrintWriter(nombreArchivoSopaS +".html", "UTF-8");
                    writer.println(result);
                    writer.close();
                    
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            e.printStackTrace();
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroSopaUnaLetraAux(ActionEvent event){
        nombreArchivoSopaS = nombreArchivoSopa.getCharacters().toString();
        System.out.println(nombreArchivoSopaS);
        grisSopaB = grisSopa.isSelected();
        Stage stage = (Stage)buttonAccept1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtroSopaVariasLetra(ActionEvent event){
        try{
            newWindow("sopaDeLetrasVariasLetra.fxml","Sopa de Letras.");
            FiltroSopaDeLetrasVariasLetras fc = new FiltroSopaDeLetrasVariasLetras(pr,width,height,4,4);
           synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    String result = fc.run();

                    PrintWriter writer = new PrintWriter(nombreArchivoSopaS +".html", "UTF-8");
                    writer.println(result);
                    writer.close();
                    
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            e.printStackTrace();
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroSopaVariasLetraAux(ActionEvent event){
        nombreArchivoSopaS = nombreArchivoSopa.getCharacters().toString();
        System.out.println(nombreArchivoSopaS);
        Stage stage = (Stage)buttonAccept1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtroSopaFrase(ActionEvent event){
        try{
            newWindow("sopaDeLetrasFrase.fxml","Sopa de Letras.");
            FiltroSopaDeLetrasFrase fc = new FiltroSopaDeLetrasFrase(pr,width,height,4,4,fraseS);
           synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    String result = fc.run(grisSopaB);

                    PrintWriter writer = new PrintWriter(nombreArchivoSopaS +".html", "UTF-8");
                    writer.println(result);
                    writer.close();
                    
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            e.printStackTrace();
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroSopaFraseAux(ActionEvent event){
        nombreArchivoSopaS = nombreArchivoSopa.getCharacters().toString();
        fraseS = frase.getCharacters().toString();
        System.out.println(nombreArchivoSopaS);
        grisSopaB = grisSopa.isSelected();
        Stage stage = (Stage)buttonAccept1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void filtroSopaDominoBlancos(ActionEvent event){
        try{
            newWindow("sopaDeLetrasVariasLetra.fxml","Sopa de Letras Dominos Blancos.");
            FiltroSopaDeLetrasDominos fc = new FiltroSopaDeLetrasDominos(pr,width,height,4,4,true);
           synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    String result = fc.run();

                    PrintWriter writer = new PrintWriter(nombreArchivoSopaS +".html", "UTF-8");
                    writer.println(result);
                    writer.close();
                    
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            e.printStackTrace();
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }

    @FXML
    private void filtroSopaDominoNegros(ActionEvent event){
        try{
            newWindow("sopaDeLetrasVariasLetra.fxml","Sopa de Letras Dominos Negros.");
            FiltroSopaDeLetrasDominos fc = new FiltroSopaDeLetrasDominos(pr,width,height,4,4,false);
           synchronized(fc){
                Thread hilo = new Thread(new Task() {
                
                @Override
                protected Object call() throws Exception {
                    String result = fc.run();

                    PrintWriter writer = new PrintWriter(nombreArchivoSopaS +".html", "UTF-8");
                    writer.println(result);
                    writer.close();
                    
                    return null;
                }
                });
                hilo.start();
            }
        }catch(Exception e){
            e.printStackTrace();
            alerta("No hay Imagen.","Favor de abrir una imagen");
        }
    }


    private PixelWriter crearPW(){
        imagenNueva = new WritableImage((int)image.getWidth(),(int)image.getHeight());
        PixelWriter pw = imagenNueva.getPixelWriter();
        return pw;
    }

    private PixelWriter crearPW(int x, int y){
        imagenNueva = new WritableImage(x,y);
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

    public static void alertaInf(String msg,String msg1){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
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