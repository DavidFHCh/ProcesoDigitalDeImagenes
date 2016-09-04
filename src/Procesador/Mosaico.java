package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class Mosaico{

	private int alto;
	private int ancho;
	PixelWriter pw;
	PixelReader pr;
	int width;
	int height;
	private Color color;
	private double r;
	private double g;
	private double b; 
	private double rprom;
	private double gprom;
	private double bprom; 
	private int contador = 0;


	public Mosaico(PixelWriter pw1,PixelReader pr1,int width1, int height1, int alto, int ancho){
		pw = pw1;
		pr = pr1;
		this.alto = alto;
		this.ancho = ancho;
		width = width1;
		height = height1;
		r = 0;
		g = 0;
		b = 0;
	}

	public void  mosaico(){
		int num = alto*ancho;
		for(int i = 0; i < width; i++){
			if((i+ancho) > width)
				break;
			for(int j = 0; j < height;j++){
				if((j+alto) > height)
					break;
				for(int k = i; k < i+ancho; k++){
					for(int l = j;l < j+alto;l++){
						color = pr.getColor(k,l);
						rprom += color.getRed();
						gprom += color.getGreen();
						bprom += color.getBlue();
					}
				}
				color = Color.color(rprom/num,gprom/num,bprom/num);
				rprom = 0;
				gprom = 0;
				bprom = 0;
				for(int k = i; k < i+ancho; k++){
					for(int l = j;l < j+alto;l++){
						pw.setColor(k,l,color);
					}
				}
				
				j += (alto -1);
			}
			i += (ancho-1);
		}
	}

	public void run(){
		mosaico();
	}

		

}