package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class Reducir{

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

	public Reducir(PixelWriter pw1,PixelReader pr1,int width1, int height1, int alto, int ancho){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		this.alto = alto;
		this.ancho = ancho;
	}

	public void aplicarReduccion(){
		int al = height/alto;
		int an = width/ancho;
		Color c = null;
		for(int i = 0; i < ancho;i++){
			for(int j = 0; j < alto; j++){
				c = pr.getColor((((i+1)*an)-1),(((j+1)*al)-1));
				pw.setColor(i,j,c);
			}
		} 
	}

	public void run(){
		aplicarReduccion();
	}
}