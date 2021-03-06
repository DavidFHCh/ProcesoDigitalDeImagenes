package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;


public class FiltrosColores{


	protected PixelWriter pw;
	protected PixelReader pr;
	protected int width;
	protected int height;
	protected int width2;
	protected int height2;
	protected int operation;
	protected double red; 
	protected double green; 
	protected double blue;
	protected double intensidadLuzNegra;
	protected double r;
	protected double g;
	protected double b;
	protected PixelReader pr2;
	protected double alpha; 


	public FiltrosColores(PixelWriter pw1,PixelReader pr1,int width1, int height1, int operation1){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		operation = operation1;
		r = 0;
		g = 0;
		b = 0;
	}

	public FiltrosColores(PixelWriter pw1,PixelReader pr1,int width1, int height1,double intensidadLuzNegra, int operation1){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		operation = operation1;
		r = 0;
		g = 0;
		b = 0;
		this.intensidadLuzNegra = intensidadLuzNegra;// este va entre 1 y 7!
	}

	public FiltrosColores(PixelWriter pw1,PixelReader pr1,int width1, int height1, int operation1,double red, double green, double blue){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		operation = operation1;
		r = 0;
		g = 0;
		b = 0;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public FiltrosColores(PixelWriter pw1,PixelReader pr1,PixelReader pr2,int width1, int height1,int width2, int height2, double alpha,int operation1){
	pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		this.width2 = width2;
		this.height2 = height2;
		operation = operation1;
		r = 0;
		g = 0;
		b = 0;
		this.pr2 = pr2;
		this.alpha = alpha;
	}


	/**
	* Filtro de blanco y negro, calcula el gris (R+G+B)/3.
	*/
	private void promByN1(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				double prom = 0;
				Color c = pr.getColor(i,j);
				Color c1 = null;
				this.r = c.getRed();
				this.g = c.getGreen();
				this.b = c.getBlue();
				prom = (r+g+b)/3;
				c1 = Color.color(prom,prom,prom);
				pw.setColor(i,j,c1);
			}

		}			
	}

	/**
	* Filtro de blanco y negro, calcula el gris multiplicando por numeros magicos,obtenidos
	* por gente que hizo calculos especiales.
	*/
	private void realByN(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				double sum = 0;
				Color c = pr.getColor(i,j);
				Color c1 = null;
				this.r = c.getRed()*0.3;
				this.g = c.getGreen()*0.59;
				this.b = c.getBlue()*0.11;
				sum = r+g+b;
				c1 = Color.color(sum,sum,sum);
				pw.setColor(i,j,c1);
			}

		}			
	}

	/**
	* Filtro de blanco y negro, calcula el gris multiplicando por numeros magicos,obtenidos
	* por gente que hizo calculos especiales.
	*/
	private void sepia(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				double sum = 0;
				Color c = pr.getColor(i,j);
				Color c1 = null;
				this.r = c.getRed()*0.3;
				this.g = c.getGreen()*0.59;
				this.b = c.getBlue()*0.11;
				double red,green,blue;
				red = (0.393*r)+(0.769*g)+(0.189*b);
				green = (0.349*r)+(0.686*g)+(0.168*b);
				blue = (0.272*r)+(0.534*g)+(0.131*b);
				if(red > 1)
					red = 1;
				if(green > 1)
					green = 1;
				if(blue > 1)
					blue = 1;
				c1 = Color.color(red,green,blue);
				pw.setColor(i,j,c1);
			}

		}			
	}

	/**
	* Estos son tres filtros si el 'color' es 1 el gris es (R,R,R), si es 2 es (G,G,G), si es 3 entonces (B,B,B).
	* @param color un entero entre 1 y 3.
	*/
	private void repetidoByN(int color){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				double sum = 0;
				Color c = pr.getColor(i,j);
				Color c1 = null;
				switch (color){
					case 1:
						this.r = c.getRed();
						c1 = Color.color(r,r,r);
						break;
					case 2:
						this.g = c.getGreen();
						c1 = Color.color(g,g,g);
						break;
					case 3:
						this.b = c.getBlue();
						c1 = Color.color(b,b,b);
						break;
				}
				pw.setColor(i,j,c1);
			}

		}			
	}

	private void micasColor(int color){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				double sum = 0;
				Color c = pr.getColor(i,j);
				Color c1 = null;
				switch (color){
					case 1:
						this.r = c.getRed();
						c1 = Color.color(r,0,0);
						break;
					case 2:
						this.g = c.getGreen();
						c1 = Color.color(0,g,0);
						break;
					case 3:
						this.b = c.getBlue();
						c1 = Color.color(0,0,b);
						break;
				}
				pw.setColor(i,j,c1);
			}

		}	
	}


	private void micasColoresVariable(double red, double green, double blue){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				Color c = pr.getColor(i,j);
				Color c1 = null;
				int red1 = 0, green1 = 0, blue1 = 0,red2 = 0, green2 = 0, blue2 = 0, red3 = 0, green3 = 0, blue3 = 0;
				this.r = c.getRed();
				this.g = c.getGreen();
				this.b = c.getBlue();

				red1 = (int) (r*255);
				green1 = (int) (g*255);
				blue1 = (int) (b*255);

				red2 = (int) (red);
				green2 = (int) (green);
				blue2 = (int) (blue);

				red3 = red1 & red2; // aqui es donde combina los colores.
				green3 = green1 & green2;
				blue3 = blue1 & blue2;

				r = ((double) red3)/255;
				g = ((double) green3)/255;
				b = ((double) blue3)/255;

				c1 = Color.color(r,g,b);
				pw.setColor(i,j,c1);
			}

		}	
	}

	private void luzNegra(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				double sum = 0;
				Color c = pr.getColor(i,j);
				Color c1 = null;
				this.r = c.getRed();
				this.g = c.getGreen();
				this.b = c.getBlue();
				sum = ((222 * r) + (707 * g) + (71 * b)) / 1000;
				r = Math.abs(r-sum)*intensidadLuzNegra;
				g = Math.abs(g-sum)*intensidadLuzNegra;
				b = Math.abs(b-sum)*intensidadLuzNegra; 
				if(r > 1) 
					r = 1;
				if(g > 1) 
					g = 1;
				if(b > 1) 
					b = 1;
				c1 = Color.color(r,g,b);
				pw.setColor(i,j,c1);
			}

		}	
	}

	private void experimental(){
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.height; j++){
				double sum = 0;
				Color c = pr.getColor(i,j);
				Color c1 = null;
				this.r = c.getRed();
				this.g = c.getGreen();
				this.b = c.getBlue();
				sum = Double.min(r,Double.min(g,b));
				if(sum == r){
					c1 = Color.color(r,0,0);
				}
				if(sum == g){
					c1 = Color.color(0,g,0);
				}
				if(sum == b){
					c1 = Color.color(0,0,b);
				}
				pw.setColor(i,j,c1);
			}

		}			
	}

	private void blending(){
		int ancho = Math.min(width,width2);
		int alto = Math.min(height,height2);
		for(int i = 0; i < ancho; i++){
			for(int j = 0; j < alto; j++){
				Color c1 = pr.getColor(i,j);
				Color c2 = pr2.getColor(i,j);
				r = (c1.getRed() * alpha) + (c2.getRed() * (1.0 - alpha));
				g = (c1.getGreen() * alpha) + (c2.getGreen() * (1.0 - alpha));
				b = (c1.getBlue() * alpha) + (c2.getBlue() * (1.0 - alpha));

				pw.setColor(i,j,Color.color(r,g,b));
			}
		}
	}


	
	public void run(){
		try{
			switch(operation){
				case 1:
					this.promByN1();
					break;
				case 2:
					this.realByN();
					break;
				case 3:
					this.repetidoByN(1);
					break;
				case 4:
					this.repetidoByN(2);
					break;
				case 5:
					this.repetidoByN(3);
					break;
				case 6:
					this.micasColor(1);
					break;
				case 7:
					this.micasColor(2);
					break;
				case 8:
					this.micasColor(3);
					break;
				case 9: 
					this.micasColoresVariable(red,green,blue);
					break;
				case 10:
					this.sepia();
					break;
				case 11:
					this.experimental();
					break;
				case 12:
					this.luzNegra();
					break;
				case 13:
					this.blending();
					break;
				default:
					System.out.println("Error");
			}
		}catch(Exception e){
			Controlador.alerta("Error","Intenta de nuevo.");
		}
	}

}