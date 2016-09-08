package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;


public class Convolucion extends FiltrosColores{


	private final static double original[][] = {{0,0,0},
						   {0,1,0},
						   {0,0,0}};

	private final static double blur[][] = {{0,0.2,0},
					   {0.2,0.2,0.2},
					   {0,0.2,0}};

	private final static double moreBlur[][] = {{0,0,1,0,0},
						   {0,1,1,1,0},
						   {1,1,1,1,1},
						   {0,1,1,1,0},
						   {0,0,1,0,0}};

	private final static double motionBlur[][] = {{1,0,0,0,0,0,0,0,0},
							{0,1,0,0,0,0,0,0,0},
							{0,0,1,0,0,0,0,0,0},
							{0,0,0,1,0,0,0,0,0},
							{0,0,0,0,1,0,0,0,0},
							{0,0,0,0,0,1,0,0,0},
							{0,0,0,0,0,0,1,0,0},
							{0,0,0,0,0,0,0,1,0},
							{0,0,0,0,0,0,0,0,1}};

	private final static double horizontalEdges[][] = {{0,0,-1,0,0},
								  {0,0,-1,0,0},
								  {0,0,2,0,0},
								  {0,0,0,0,0},
								  {0,0,0,0,0}};

	private final static double verticalEdges[][] = {{0,0,-1,0,0},
								{0,0,-1,0,0},
								{0,0,4,0,0},
								{0,0,-1,0,0},
								{0,0,-1,0,0}};

	private final static double degree45Edges[][] = {{1,0,0,0,0},
								{0,-2,0,0,0},
								{0,0,6,0,0},
								{0,0,0,-2,0},
								{0,0,0,0,-1}};

	private final static double allEdges[][] = {{-1,-1,-1},
						   {-1,8,-1},
						   {-1,-1,-1}};

	private final static double sharpen[][] = {{-1,-1,-1},
						   {-1,9,-1},
						   {-1,-1,-1}};

	private final static double subtleSharpen[][] = {{-1,-1,-1,-1,-1},
								{-1,2,2,2,-1},
								{-1,2,8,2,-1},
								{-1,2,2,2,-1},
								{-1,-1,-1,-1,-1}};

	private final static double excessSharpen[][] = {{1,1,1},
						   		{1,-8,1},
						   		{1,1,1}};

	private final static double emboss[][] = {{-1,-1,0},
						 {-1,0,1},
						 {0,1,1}};
								
	private final static double exaggeratedEmboss[][] = {{-1,-1,-1,-1,0},
									{-1,-1,-1,0,1},
									{-1,-1,0,1,1},
									{-1,0,1,1,1},
									{0,1,1,1,1}};

	private final static double mean[][] = {{1,1,1},
				   {1,1,1},
				   {1,1,1}};								


	public Convolucion(PixelWriter pw1,PixelReader pr1,int width1, int height1, int operation1){
		super(pw1,pr1,width1,height1,operation1);
	}

	public void aplicarFiltro(double factor, double bias, double matrix[][],int matrixHeight, int matrixWidth){
		double bias1 = bias/255;
		Color colorMatrix = null;
		Color colorFinal = null;
		for(int i = 0; i < super.height;i++){
			for(int j = 0; j < super.width; j++){
				for(int k = 0; k < matrixHeight;k++){
					for(int l = 0; l < matrixWidth;l++){
						colorMatrix = super.pr.getColor(k+i,l+j);
						super.r += (colorMatrix.getRed()*matrix[k][l]);
						super.g += (colorMatrix.getGreen()*matrix[k][l]);
						super.b += (colorMatrix.getBlue()*matrix[k][l]);
					}
				}
				super.r = (super.r*factor) + bias1;
				super.g = (super.g*factor) + bias1;
				super.b = (super.b*factor) + bias1;

				//truncando los valores si son mayores a 1 o menores que 0.
				if(super.r < 0)
					super.r = 0;
				if(super.g < 0)
					super.g = 0;
				if(super.b < 0)
					super.b = 0;
				if(super.r > 1)
					super.r = 1;
				if(super.g > 1)
					super.g = 1;
				if(super.b > 1)
					super.b = 1;
				colorFinal = Color.color(super.r,super.g,super.b);
				pw.setColor(i+(matrixHeight/2),j+(matrixWidth/2),colorFinal);
				super.r = 0;
				super.g = 0;
				super.b = 0;
			}
		}
	}


	public void run(){
		switch(super.operation){
			case 1:
				aplicarFiltro(1,0,original,3,3);
				break;
			case 2:
				aplicarFiltro(1,0,blur,3,3);
				break;
			case 3:
				aplicarFiltro(1/13,0,moreBlur,5,5);
				break;
			case 4:
				aplicarFiltro(1/9,0,motionBlur,9,9);
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
				aplicarFiltro(1/8,0,subtleSharpen,5,5);
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
				aplicarFiltro(1,0,mean,3,3);
				break;
		}
	}
}