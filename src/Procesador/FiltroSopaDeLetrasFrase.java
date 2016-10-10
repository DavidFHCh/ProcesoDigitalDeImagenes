package Procesador;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class FiltroSopaDeLetrasFrase{

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

	private final char[] fraseChars;


	public FiltroSopaDeLetrasFrase(PixelReader pr1,int width1, int height1, int regionAlto,int regionAncho,String frase){
		pr = pr1;
		width = width1;
		height = height1;
		this.regionAlto = regionAlto;
		this.regionAncho = regionAncho;
		result = "<!DOCTYPE html>\n<html>\n<body>";
		fraseChars = frase.trim().toCharArray();
	}



    public String aplicaFiltro(boolean gris){
    	try{
    		int len = fraseChars.length;
			int conta = 0;
    		if(gris){
    			imagenAux = new WritableImage(width,height);
    			PixelWriter pw = imagenAux.getPixelWriter();
    			FiltrosColores fc = new FiltrosColores(pw,pr,width,height,2);
				fc.run();
				pr = imagenAux.getPixelReader();
    		}

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
						gprom += color.getGreen();
						bprom += color.getBlue();
					}
				}
				rprom = (rprom/num)*255;
				gprom = (gprom/num)*255;
				bprom = (bprom/num)*255;

				
				if(conta == len)
					conta = 0;
				String s = "<b style='font-family:consolas; color:rgb("+ (int)rprom + "," + (int)gprom + "," + (int)bprom +");'>"+ fraseChars[conta] +"</b>\n";
				conta++;
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

    public String run(boolean gris){
    	return aplicaFiltro(gris);
    }
}