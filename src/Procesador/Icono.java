package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class Icono{

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
	private boolean mosaico;

	public Icono(PixelWriter pw1,PixelReader pr1,int width1, int height1, int ancho, int alto,boolean mosaico){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		this.alto = alto;
		this.ancho = ancho;
		this.mosaico = mosaico;
	}

	public void aplicarReduccion(){
		int al = height/alto;
		int an = width/ancho;
		Color c = null;
		for(int i = 0; i < ancho;i++){
			for(int j = 0; j < alto; j++){
				for(int k = (i*an); k < ((i+1)*an);k++){
					for(int l = (j*al); l < ((j+1)*al); l++){
						if(k == (((i+1)*an)-1) && l == (((j+1)*al)-1))
							c = pr.getColor(k,l);
					}
				}
				pw.setColor(i,j,c);
			}
		} 
	}

	public void aplicarReduccionMos(){
		int al = height/alto;
		int an = width/ancho;
		int pix = al*an;
		Color c = null;
		r = g = b = 0;
		for(int i = 0; i < ancho;i++){
			for(int j = 0; j < alto; j++){
				for(int k = (i*an); k < ((i+1)*an);k++){
					for(int l = (j*al); l < ((j+1)*al); l++){
							c = pr.getColor(k,l);
							r += c.getRed();
							g += c.getGreen();
							b += c.getBlue();
					}
				}
				pw.setColor(i,j,Color.color(r/pix,g/pix,b/pix));
				r = g = b = 0;
			}
		} 
	}

	public void run(){
		try{
			if(mosaico){
				aplicarReduccionMos();
			}else{
				aplicarReduccion();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}