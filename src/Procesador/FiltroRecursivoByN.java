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
	private ArrayList<WritableImage> images;



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

	private int determinaColor(int col){
		int i;
		int factor = 255/numImagenes;
		for(i = 0; i < numImagenes; i++){
			if(col <= factor){
				return i;
			}
			factor += (255/numImagenes);
		}
		return i;
	}

    public void aplicaFiltro(){
    	reducirImgyByN();
    	generaImagenes();
    	int x = 0, y = 0;
    	int img;
    	double promedio = 0; 
    	int factorAncho = width/anchoImagenes;
    	int factorAlto = height/altoImagenes;
    	int factorAnchoFinal = width/anchoImagenes;
    	int factorAltoFinal = height/altoImagenes;
    	int numPixeles = factorAnchoFinal*factorAltoFinal;
    	for(int i = 0; i < width;i++){
    		x = i;
    		for(int j = 0; j < height;j++){
    			y = j;
    			while(i < factorAncho){
    				while(j < factorAlto){
						Color color = pr.getColor(i,j);
						promedio += color.getRed();
						j++;
    				}
    				i++;
    			}
    			promedio = promedio/(double)numPixeles;
    			img = determinaColor((int)(promedio*255));
    			prAux = crearPR(images.get(img));
    			for(int k = 0; k < imagenPequeñaAncho; k++){
    				for(int l = 0; l < imagenPequeñaAlto; l++){
    					Color c = prAux.getColor(k,l);
    					pw.setColor(k+x,l+y,c);
    				}
    			}
    		}
    	}
    }
}