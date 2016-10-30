package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.lang.*;


public class Oleo{

	PixelWriter pwO;
	PixelReader prO;
	int width;
	int height;
	int operation;
	double red; 
	double green; 
	double blue;
	protected double r;
	protected double g;
	protected double b; 
	private static WritableImage bYN;

	public Oleo(PixelWriter pw1,PixelReader pr1,int width1, int height1, int operation1){
		pwO = pw1;
		prO = pr1;
		width = width1;
		height = height1;
		operation = operation1;
		bYN = new WritableImage(width,height);
	}

	private int repetido(int matrix[][]){
		int[] repetido = new int[256];
		for (int i = 0;i < 256 ;i++){
			repetido[i] = 0;
		}
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5;j++){
				repetido[matrix[i][j]]++;
			}
		}
		int rep = 0;
		int max = 0;
		for(int i = 0; i < 256;i++){
			if(repetido[i] > rep){
				rep = repetido[i];
				max = i;
			}
		}
		return max;
	}

	private int maximo(int matrix[][]){
		int max = 0;
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5;j++){
				if(matrix[i][j] > max)
					max = matrix[i][j];
			}
		}
		return max;
	}

	private int minimo(int matrix[][]){
		int max = matrix[0][0];
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5;j++){
				if(matrix[i][j] < max)
					max = matrix[i][j];
			}
		}
		return max;
	}

	private void oleoNormal(PixelReader pr){
		int[][] matrix = new int[5][5];
		for(int i = 0; i < width; i++){
			if((i+5) == width)
				break;
			for(int j = 0 ;j < height; j++){
				if((j+5) == height)
					break;
				for(int k = i; k < i+5; k++){
					for(int l = j; l < j+5; l++){
						matrix[k-i][l-j] = (int)(pr.getColor(k,l).getRed()*255);
					}
				}
				double repetido = (double)repetido(matrix)/255;
				Color c = Color.color(repetido,repetido,repetido);
				pwO.setColor(i+3,j+3,c);
			}
		}
	}

	private void maxim(PixelReader pr){
		int[][] matrix = new int[5][5];
		for(int i = 0; i < width; i++){
			if((i+5) == width)
				break;
			for(int j = 0 ;j < height; j++){
				if((j+5) == height)
					break;
				for(int k = i; k < i+5; k++){
					for(int l = j; l < j+5; l++){
						matrix[k-i][l-j] = (int)(pr.getColor(k,l).getRed()*255);
					}
				}
				double repetido = (double)maximo(matrix)/255;
				Color c = Color.color(repetido,repetido,repetido);
				pwO.setColor(i+3,j+3,c);
			}
		}
	}

	private void minim(PixelReader pr){
		int[][] matrix = new int[5][5];
		for(int i = 0; i < width; i++){
			if((i+5) == width)
				break;
			for(int j = 0 ;j < height; j++){
				if((j+5) == height)
					break;
				for(int k = i; k < i+5; k++){
					for(int l = j; l < j+5; l++){
						matrix[k-i][l-j] = (int)(pr.getColor(k,l).getRed()*255);
					}
				}
				double repetido = (double)minimo(matrix)/255;
				Color c = Color.color(repetido,repetido,repetido);
				pwO.setColor(i+3,j+3,c);
			}
		}
	}

	private PixelReader blancoYNegro(){
		FiltrosColores fc = new FiltrosColores(bYN.getPixelWriter(),prO,width,height,2);
		fc.run();
		return bYN.getPixelReader();
	}

	public void run(){
		try{
		PixelReader pr1 = blancoYNegro();
		switch(operation){
			case 1:
				oleoNormal(pr1);
				break;
			case 2:
				maxim(pr1);
				break;
			case 3:
				minim(pr1);
				break;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}