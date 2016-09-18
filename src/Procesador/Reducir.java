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
		int factorAlto = 0;
		int factorAncho = 0;
		int fAFinal = height/alto;
		int fAnFinal = width/ancho;
		int pixelNuevoX = 0;
		int pixelNuevoY = 0;
		try{
		for(int i = 0; i < width; i++){
			System.out.println("what");
			if(i >= factorAncho){
				factorAncho += fAnFinal;
				pixelNuevoX++;
			}
			for(int j = 0;j < height;j++){
				if(j >= factorAlto){
				factorAlto += fAFinal;
				pixelNuevoY++;
				}
				Color color = pr.getColor(i,j);
				pw.setColor(pixelNuevoX,pixelNuevoY,color);
			}
			pixelNuevoY=0;
			factorAlto = 0;
		}
		factorAncho = 0;
		System.out.println("what no");
	}
		catch(Exception e){
			e.printStackTrace();
		}	
	}

	public void run(){
		aplicarReduccion();
	}
}