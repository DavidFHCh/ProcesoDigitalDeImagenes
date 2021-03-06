package Procesador;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class FiltroRecursivoByN{

	private PixelWriter pw;
	private PixelReader pr;
	private static PixelReader prAux;
	private static PixelWriter pwAux;
	private static WritableImage imagenAux;
	private static WritableImage imagenAux2;
	private int width;
	private int height;
	private int regionAlto;
	private int regionAncho;
	private int imagenPequeñaAlto;
	private int imagenPequeñaAncho;
	private double red; 
	private double green; 
	private double blue;
	protected double r;
	protected double g;
	protected double b;
	private ArrayList<Image> images;



	public FiltroRecursivoByN(PixelWriter pw1,PixelReader pr1,int width1, int height1, int regionAlto,int regionAncho, int imagenPequeñaAlto,int imagenPequeñaAncho){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		this.regionAlto = regionAlto;
		this.regionAncho = regionAncho;
		this.imagenPequeñaAlto = imagenPequeñaAlto;
		this.imagenPequeñaAncho = imagenPequeñaAncho;
		r = 0;
		g = 0;
		b = 0;
		images = new ArrayList<>();
		imagenAux = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
		imagenAux2 = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
	}

	private PixelReader reducirImgyByN(){
		pwAux = crearPW(imagenAux);
		Reducir redu = new Reducir(pwAux,pr,width,height,imagenPequeñaAlto,imagenPequeñaAncho);
		redu.run();
		
		FiltrosColores fc = new FiltrosColores(crearPW(imagenAux2),crearPR(imagenAux),imagenPequeñaAncho,imagenPequeñaAlto,2);
		fc.run();
		return crearPR(imagenAux2);

	}

	private Image generaImagenes(double factor,PixelReader pr1){
		pwAux = crearPW(imagenAux);
		WritableImage im = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
		Brillo brillo = new Brillo(crearPW(im),pr1,imagenPequeñaAncho,imagenPequeñaAlto,factor/255.0);
		brillo.run();
		return im;
	}

	private PixelReader crearPR(Image imagen){
		//System.out.println("QUe pasa?? ");
        PixelReader pr = imagen.getPixelReader();
        return pr;
    }
 	private PixelWriter crearPW(WritableImage imagen){
        PixelWriter pw = imagen.getPixelWriter();
        return pw;
    }

    private void generaImagenes(PixelReader pr1){
    	int factor = -255;
    	int crece = 510/30;
    	for(int i = 0; i < 30;i++){
    		images.add(generaImagenes(factor,pr1));
    		factor += crece;
    	}
    }

	private int determinaColor(int col){
		int i;
		int factor = 255/30;
		for(i = 0; i < 30; i++){
			if(col <= factor){
				return i;
			}
			factor += (255/30);
		}
		return i;
	}

    public void aplicaFiltro(){
    	try{
    		double propx = (double)imagenPequeñaAncho/(double)regionAncho;
    		double propy = (double)imagenPequeñaAlto/(double)regionAlto;
 		Color color = null;
    		double rprom = 0.0;
    		generaImagenes(reducirImgyByN());
		int num = regionAlto*regionAncho;
		for(int i = 0; i < width; i++){
			if((i+regionAncho) > width)
				break;
			for(int j = 0; j < height;j++){
				if((j+regionAlto) > height)
					break;
				for(int k = i; k < i+regionAncho; k++){
					for(int l = j;l < j+regionAlto;l++){
						color = pr.getColor(k,l);
						rprom += color.getRed();
					}
				}
				int prom = determinaColor((int)(rprom*255.0/num));
				if(prom == 30)
					prom--;
				rprom = 0;
				Image im = images.get(prom);
				PixelReader pr2  = crearPR(im);
				int x = 0,y = 0;
				for(int k = (int)(i*propx); k < (i*propx)+im.getWidth(); k++){
					if(x >= im.getWidth()){
						x = 0;
						break;
					}
					y = 0;
					for(int l = (int)(j*propy);l < (j*propy)+im.getHeight();l++){
						if(y >= im.getHeight()){
							y = 0;
							break;
						}
						Color c = pr2.getColor(x,y);
						pw.setColor(k,l,c);
						y++;
					}
					x++;
				}
				
				j += (regionAlto -1);
			}
			i += (regionAncho-1);
		}

    	}catch(Exception e){e.printStackTrace();}
    }

    public void run(){
    	aplicaFiltro();
    }
}