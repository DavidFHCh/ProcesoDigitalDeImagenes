package Procesador;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class FiltroSopaDeLetrasVariasLetras{

	private PixelReader pr;
	private int width;
	private int height;
	private int regionAlto;
	private int regionAncho;
	private int imagenPequeñaAlto;
	private int imagenPequeñaAncho;
	private String result;
	private static WritableImage imagenAux;
	private final String end = "</body>\n</html>";

	private final static String[] colores = {"M","N","H","#","Q","U","A","D","O","Y","2","$","%","+","_","&nbsp"};




	public FiltroSopaDeLetrasVariasLetras(PixelReader pr1,int width1, int height1, int regionAlto,int regionAncho){
		pr = pr1;
		width = width1;
		height = height1;
		this.regionAlto = regionAlto;
		this.regionAncho = regionAncho;
		result = "<!DOCTYPE html>\n<html>\n<body>";
	}


	private int determinaLetra(int prom){
		int rango = 16;
		int i = 0;
		int cont = 0;
		while(cont < prom){
			cont += rango;
			i++;
		}
		return i;
	}

    public String aplicaFiltro(){
    	try{
    		
    		imagenAux = new WritableImage(width,height);
    		PixelWriter pw = imagenAux.getPixelWriter();
   			FiltrosColores fc = new FiltrosColores(pw,pr,width,height,2);
			fc.run();
			pr = imagenAux.getPixelReader();
    		

 			Color color = null;
    		double rprom = 0.0;
    		double gprom = 0.0;
    		double bprom = 0.0;
			int num = regionAlto*regionAncho;
		for(int i = 0; i < height; i++){
			if((i+regionAncho) > height)
				break;
			for(int j = 0; j < width;j++){
				if((j+regionAlto) > width)
					break;
				for(int k = i; k < i+regionAncho; k++){
					for(int l = j;l < j+regionAlto;l++){
						color = pr.getColor(l,k);
						rprom += color.getRed();
					}
				}
				rprom = (rprom/num)*255;

				//llamada a funcion que determina Caracter a usar
				int letra = determinaLetra((int)rprom);

				String s = "<b style='font-family:Courier New; color:rgb(0,0,0);'>" + colores[letra] + "</b>\n";

				result += s;

				rprom = 0;
				gprom = 0;
				bprom = 0;
				
				j += (regionAlto -1);
			}
			i += (regionAncho-1);
			result += "<br>\n";
		}
		result += end;
		//System.out.println(result);
    	return result;
    	}catch(Exception e){e.printStackTrace();}
    	System.out.println("Done");
    	return result;
    }

    public String run(){
    	return aplicaFiltro();
    }
}