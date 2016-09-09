package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;


public class Convolucion{


	private  double original[][] = {{0,0,0},
						   {0,1,0},
						   {0,0,0}};

	private  double blur[][] = {{0,0.2,0},
					   {0.2,0.2,0.2},
					   {0,0.2,0}};

	private  double moreBlur[][] = {{0,0,1,0,0},
						   {0,1,1,1,0},
						   {1,1,1,1,1},
						   {0,1,1,1,0},
						   {0,0,1,0,0}};

	private  double motionBlur[][] = {{1,0,0,0,0,0,0,0,0},
							{0,1,0,0,0,0,0,0,0},
							{0,0,1,0,0,0,0,0,0},
							{0,0,0,1,0,0,0,0,0},
							{0,0,0,0,1,0,0,0,0},
							{0,0,0,0,0,1,0,0,0},
							{0,0,0,0,0,0,1,0,0},
							{0,0,0,0,0,0,0,1,0},
							{0,0,0,0,0,0,0,0,1}};

	private  double horizontalEdges[][] = {{0,0,-1,0,0},
								  {0,0,-1,0,0},
								  {0,0,2,0,0},
								  {0,0,0,0,0},
								  {0,0,0,0,0}};

	private  double verticalEdges[][] = {{0,0,-1,0,0},
								{0,0,-1,0,0},
								{0,0,4,0,0},
								{0,0,-1,0,0},
								{0,0,-1,0,0}};

	private  double degree45Edges[][] = {{-1,0,0,0,0},
								{0,-2,0,0,0},
								{0,0,6,0,0},
								{0,0,0,-2,0},
								{0,0,0,0,-1}};

	private  double allEdges[][] = {{-1,-1,-1},
						   {-1,8,-1},
						   {-1,-1,-1}};

	private  double sharpen[][] = {{-1,-1,-1},
						   {-1,9,-1},
						   {-1,-1,-1}};

	private  double subtleSharpen[][] = {{-1,-1,-1,-1,-1},
								{-1,2,2,2,-1},
								{-1,2,8,2,-1},
								{-1,2,2,2,-1},
								{-1,-1,-1,-1,-1}};

	private  double excessSharpen[][] = {{1,1,1},
						   		{1,-7,1},
						   		{1,1,1}};

	private  double emboss[][] = {{-1,-1,0},
						 {-1,0,1},
						 {0,1,1}};
								
	private  double exaggeratedEmboss[][] = {{-1,-1,-1,-1,0},
									{-1,-1,-1,0,1},
									{-1,-1,0,1,1},
									{-1,0,1,1,1},
									{0,1,1,1,1}};

	private  double mean[][] = {{1,1,1},
				   {1,1,1},
				   {1,1,1}};								

	PixelWriter pw;
	PixelReader pr;
	int width;
	int height;
	int operation;
	double red; 
	double green; 
	double blue;
	protected double r;
	protected double g;
	protected double b; 

	public Convolucion(PixelWriter pw1,PixelReader pr1,int width1, int height1, int operation1){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		operation = operation1;
		r = 0;
		g = 0;
		b = 0;
	}

	public void aplicarFiltro(double factor, double bias, double matrix[][],int matrixHeight, int matrixWidth){

		double bias1 = bias/255;
		Color colorMatrix = null;
		Color colorFinal = null;
		try{
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height;j++){
					pw.setColor(i,j,Color.color(1,1,1));
				}
			}
		for(int i = 0;i < width;i++){
			if(i+matrixWidth > width)
							break;
			for(int j = 0; j < height; j++){
				if(j+matrixHeight > height)
						break;
				for(int k = 0; k < matrixWidth;k++){
					for(int l = 0; l < matrixHeight;l++){

						colorMatrix = pr.getColor(i+k,j+l);
						r += (colorMatrix.getRed()*matrix[k][l]);
						g += (colorMatrix.getGreen()*matrix[k][l]);
						b += (colorMatrix.getBlue()*matrix[k][l]);					
					}
					
				}
				r = (r*factor) + bias1;
				g = (g*factor) + bias1;
				b = (b*factor) + bias1;

				//truncando los valores si son mayores a 1 o menores que 0.
				if(r < 0)
					r = 0;
				if(g < 0)
					g = 0;
				if(b < 0)
					b = 0;
				if(r > 1)
					r = 1;
				if(g > 1)
					g = 1;
				if(b > 1)
					b = 1;
				colorFinal = Color.color(r,g,b);
				pw.setColor(i+(matrixWidth/2),j+(matrixHeight/2),colorFinal);
				r = 0;
				g = 0;
				b = 0;
			}
		}
	}catch(Exception e){e.printStackTrace();
	}
	}

	
	public void run(){
		switch(operation){
			case 1:
				aplicarFiltro(1,0,original,3,3);
				break;
			case 2:
				aplicarFiltro(1,0,blur,3,3);
				break;
			case 3:
				aplicarFiltro(0.07692,0,moreBlur,5,5);
				break;
			case 4:
				aplicarFiltro(0.1111,0,motionBlur,9,9);
				break;
			case 5:
				aplicarFiltro(1,0,horizontalEdges,5,5);
				break;
			case 6:
				aplicarFiltro(1,0,verticalEdges,5,5);
				break;
			case 7:
				aplicarFiltro(1,0,degree45Edges,5,5);
				break;
			case 8:
				aplicarFiltro(1,0,allEdges,3,3);
				break;
			case 9:
				aplicarFiltro(1,0,sharpen,3,3);
				break;
			case 10:
				aplicarFiltro(0.125,0,subtleSharpen,5,5);
				break;
			case 11:
				aplicarFiltro(1,0,excessSharpen,3,3);
				break;
			case 12:
				aplicarFiltro(1,128,emboss,3,3);
				break;
			case 13:
				aplicarFiltro(1,128,exaggeratedEmboss,5,5);
				break;
			case 14:
				aplicarFiltro(0.1111,0,mean,3,3);
				break;
		}
	}
}