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
	}

	private void reducirImgyByN(){
		Reducir redu = new Reducir(crearPW(imagenAux),pr,width,height,imagenPequeñaAlto,imagenPequeñaAncho);
		redu.run();
		prAux = crearPR(imagenAux);
		FiltrosColores fc = new FiltrosColores(crearPW(imagenAux2),prAux,width,height,2);
		prAux = crearPR(imagenAux2);
		imagenAux = null;
	}


	private PixelReader crearPR(WritableImage imagen){
        PixelReader pr = imagen.getPixelReader();
        return pr;
    }
 	private PixelWriter crearPW(WritableImage imagen){
        imagen = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
        PixelWriter pw = imagenAux.getPixelWriter();
        return pw;
    }
}