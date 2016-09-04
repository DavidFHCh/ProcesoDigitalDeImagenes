package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class Brillo extends FiltrosColores{

	double brillo;

	public Brillo(PixelWriter pw1,PixelReader pr1,int width1, int height1, double brillo){
		super(pw1,pr1,width1,height1,0);
		this.brillo = brillo;
	}

	public void filtroBrillo(){
		for(int i = 0; i < super.width; i++){
			for(int j = 0; j < super.height; j++){
				Color c = pr.getColor(i,j);
				Color c1 = null;
				super.r = c.getRed() + brillo;
				super.g = c.getGreen() + brillo;
				super.b = c.getBlue() + brillo;
				if(r < 0) r = 0;
				if(g < 0) g = 0;
				if(b < 0) b = 0;
				if(r > 1) r = 1;
				if(g > 1) g = 1;
				if(b > 1) b = 1;
				c1 = Color.color(r,g,b);
				pw.setColor(i,j,c1);
			}
		}
	}

	@Override
	public void run(){
		filtroBrillo();
	}
}