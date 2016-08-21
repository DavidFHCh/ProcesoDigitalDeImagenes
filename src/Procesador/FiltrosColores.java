package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;


public class FiltrosColores extends Thread{

	private Thread t;

	PixelWriter pw;
	PixelReader pr;
	int width;
	int height;
	int operation;
	private double r;
	private double g;
	private double b; 

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

	//tarea filtro gris (R+G+B)/3
	//filtro R*0.3    g*0.59 b*0.11
	//filtro (R;R;R) (GGG)(BBB)
	//micas
	//mosaico
	public void promByN1(){
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

	public void realByN(){
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

	public void repetidoByN(int color){
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
				default:
					System.out.println("Error");
			}
		}catch(Exception e){
			Controlador.alerta("Error","Intenta de nuevo.");
		}
	}

	public void start(){
		t = new Thread(this);
		t.start();
	}	
}