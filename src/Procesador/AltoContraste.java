package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;

public class AltoContraste extends FiltrosColores{

	public AltoContraste(PixelWriter pw1,PixelReader pr1,int width1, int height1, int operation1){
		super(pw1,pr1,width1,height1,operation1);
	}

	public void contraste(){
		for(int i = 0; i < super.width; i++){
			for(int j = 0; j < super.height; j++){
				Color c = pr.getColor(i,j);
				Color c1 = null;
				super.r = c.getRed();
				super.g = c.getGreen();
				super.b = c.getBlue();
				if(r < .5){
					r = 0;
				}else{
					r = 1;
				}
				if(g < .5){
					g = 0;
				}else{
					g = 1;
				}
				if(b < .5){
					b = 0;
				}else{
					b = 1;
				}
				c1 = Color.color(r,g,b);
				pw.setColor(i,j,c1);
			}
		}
	}

	public void contrasteInverso(){
		for(int i = 0; i < super.width; i++){
			for(int j = 0; j < super.height; j++){
				Color c = pr.getColor(i,j);
				Color c1 = null;
				super.r = c.getRed();
				super.g = c.getGreen();
				super.b = c.getBlue();
				if(r > .5){
					r = 0;
				}else{
					r = 1;
				}
				if(g > .5){
					g = 0;
				}else{
					g = 1;
				}
				if(b > .5){
					b = 0;
				}else{
					b = 1;
				}
				c1 = Color.color(r,g,b);
				pw.setColor(i,j,c1);
			}
		}
	}

	@Override
	public void run(){
		switch(super.operation){
			case 1:
				contraste();
				break;
			case 2:
				contrasteInverso();
				break;
		}
	}

}