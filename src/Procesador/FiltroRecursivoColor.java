package Procesador;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class FiltroRecursivoColor{

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
	private HashMap<Integer,Image> images;



	public FiltroRecursivoColor(PixelWriter pw1,PixelReader pr1,int width1, int height1, int regionAlto,int regionAncho, int imagenPequeñaAlto,int imagenPequeñaAncho){
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
		images = new HashMap<>();
		imagenAux = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
		imagenAux2 = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
	}

	private PixelReader reducirImg(){
		pwAux = crearPW(imagenAux);
		Reducir redu = new Reducir(pwAux,pr,width,height,imagenPequeñaAlto,imagenPequeñaAncho);
		redu.run();

		return crearPR(imagenAux);
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

    private void aplicarMica(WritableImage	wi,PixelReader pr1, double rprom, double gprom, double bprom){
    	FiltrosColores fc = new FiltrosColores(crearPW(wi),pr1,(int)imagenAux.getWidth(),(int)imagenAux.getHeight(),9,rprom*255,gprom*255,bprom*255);
		fc.run();
    }

    public void aplicaFiltro(){
    	try{
    		double propx = (double)imagenPequeñaAncho/(double)regionAncho;
    		double propy = (double)imagenPequeñaAlto/(double)regionAlto;
 			Color color = null;
    		double rprom = 0.0;
    		double gprom = 0.0;
    		double bprom = 0.0;
    		PixelReader pr1 = reducirImg();
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
						gprom += color.getGreen();
						bprom += color.getBlue();
					}
				}
				
				rprom = rprom/num;
				gprom = gprom/num;
				bprom = bprom/num;
				Color colorHM = Color.color(rprom,gprom,bprom);
				Image im = null;
				//imagenAux2 = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
				//if(images.containsKey(colorHM.hashCode())){
					//im = images.get(colorHM.hashCode());
					//System.out.println("entre?");
				//}else{
					aplicarMica(imagenAux2,pr1,rprom,gprom,bprom);

					//System.out.println(rprom + " " + gprom + " " + bprom);
					
					//images.put(colorHM.hashCode(),imagenAux2);
					im = imagenAux2;
				//}

				rprom = 0;
				gprom = 0;
				bprom = 0;


				PixelReader pr2 = crearPR(im);
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
		System.out.println("QUe pasa?? 222222");
    	}catch(Exception e){e.printStackTrace();}
    }

    public void run(){
    	aplicaFiltro();
    }
}