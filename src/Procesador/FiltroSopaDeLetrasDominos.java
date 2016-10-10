package Procesador;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class FiltroSopaDeLetrasDominos{

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
	private boolean blancos;

	private final static String[] coloresAbren = {"9","8","7","6","5","4","3","2","1","0"};
	private final static String[] coloresCierran = {"(","*","&","^","%","$","#","@","!",")"};



	public FiltroSopaDeLetrasDominos(PixelReader pr1,int width1, int height1, int regionAlto,int regionAncho,boolean blancos){
		pr = pr1;
		width = width1;
		height = height1;
		this.regionAlto = regionAlto;
		this.regionAncho = regionAncho;
		this.blancos = blancos;
		if(blancos){
			result = "<!DOCTYPE html>\n<html>\n<style>@font-face {font-family: \"las vegas white dominoes\";src: url(lasvwd.TTF) format(\"truetype\");}</style>\n<body>";
		}else{	
			result = "<!DOCTYPE html>\n<html>\n<style>@font-face {font-family: \"las vegas black dominoes\";src: url(lasvbld.TTF) format(\"truetype\");}</style>\n<body>";
		}
	}


	private int determinaLetra(int prom){
		int rango = 25;
		int i = 0;
		int cont = 0;
		if(prom >= 255){
			return 9;
		}
		while(cont < prom){
			cont += rango;
			i++;
		}
		if(i >= 10){
			return 9;
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
    		
			boolean abre = true;
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
				String s = "";
				int letra = determinaLetra((int)rprom);
				if(blancos){
					if(abre){
						s = "<font style=\"font-family:las vegas white dominoes; color:rgb(0,0,0);\">" + coloresAbren[letra] + "</b>\n";
						abre = false;
					}else{
						s = "<font style=\"font-family:las vegas white dominoes; color:rgb(0,0,0);\">" + coloresCierran[letra] + "</b>\n";
						abre = true;
					}
				}else{
					if(abre){
						s = "<font style=\"font-family:las vegas black dominoes; color:rgb(0,0,0);\">" + coloresAbren[letra] + "</b>\n";
						abre = false;
					}else{
						s = "<font style=\"font-family:las vegas black dominoes; color:rgb(0,0,0);\">" + coloresCierran[letra] + "</b>\n";
						abre = true;
					}
				}


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