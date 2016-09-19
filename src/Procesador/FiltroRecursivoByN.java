package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class FiltroRecursivoByN{

	private PixelWriter pw;
	private PixelReader pr;
	private PixelReader prAux;
	private PixelWriter pwAux;
	private WritableImage imagenAux;
	private WritableImage imagenAux2;
	private int width;
	private int height;
	private int altoImagenes;
	private int anchoImagenes;
	private int imagenPequeñaAlto;
	private int imagenPequeñaAncho;
	private double red; 
	private double green; 
	private double blue;
	private int numImagenes;
	protected double r;
	protected double g;
	protected double b;
	private ArrayList<Image> images;



	public FiltroRecursivoByN(PixelWriter pw1,PixelReader pr1,int width1, int height1, int altoImagenes,int anchoImagenes, int imagenPequeñaAlto,int imagenPequeñaAncho,int numImagenes){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		this.altoImagenes = altoImagenes;
		this.anchoImagenes = anchoImagenes;
		this.imagenPequeñaAlto = imagenPequeñaAlto;
		this.imagenPequeñaAncho = imagenPequeñaAncho;
		this.numImagenes = numImagenes;
		r = 0;
		g = 0;
		b = 0;
		images = new ArrayList<>();
	}

	private void reducirImgyByN(){
		Reducir redu = new Reducir(crearPW(imagenAux),pr,width,height,imagenPequeñaAlto,imagenPequeñaAncho);
		redu.run();
		FiltrosColores fc = new FiltrosColores(crearPW(imagenAux2),crearPR(imagenAux),width,height,2);
		fc.run();
		prAux = crearPR(imagenAux2);
		imagenAux = null;
	}

	private void generaImagenes(int factor){
		pwAux = crearPW(imagenAux);
		Brillo brillo = new Brillo(pwAux,prAux,imagenPequeñaAncho,imagenPequeñaAlto,factor);
	}

	private PixelReader crearPR(WritableImage imagen){
        PixelReader pr = imagen.getPixelReader();
        return pr;
    }
 	private PixelWriter crearPW(WritableImage imagen){
        imagen = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
        PixelWriter pw = imagen.getPixelWriter();
        return pw;
    }

    private void generaImagenes(){
    	int factor = -255;
    	int crece = 510/numImagenes;
    	for(int i = 0; i < numImagenes;i++){
    		generaImagenes(factor);
    		factor += crece;
    		images.add(imagenAux);
    	}
    }

    public void aplicaFiltro(){
    	reducirImgyByN();
    	generaImagenes();
    	for(int i = 0; i < width;i++){
    		for(int j = 0; j < height;j++){
    			pr.getColor(i,j);
    			
    		}
    	}
    }
}