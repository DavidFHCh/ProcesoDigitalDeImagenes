package Procesador;

import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.*;

public class QuitaMarcaDeAgua{

	PixelWriter pw;
	PixelReader pr;
	int width;
	int height;
	private double r;
	private double g;
	private double b; 

	public QuitaMarcaDeAgua(PixelWriter pw1,PixelReader pr1,int width1, int height1){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
	}

	private class OtroColor{

		private int x;
		private int y;

		public OtroColor(int x, int y){
			this.x = x;
			this.y = y;
		}

		public int getX(){
			return x;
		}

		public int getY(){
			return y;
		}
	}

	private void quita(){
		Color c = null;
		LinkedList otrosColores = new LinkedList<OtroColor>();
		for(int i = 0; i < width;i++){
			for(int j = 0; j < height; j++){
				c = pr.getColor(i,j);
				r = c.getRed();
				g = c.getGreen();
				b = c.getBlue();
				if(r != g || r != b || g != b){
					otrosColores.add(new OtroColor(i,j));
					pw.setColor(i,j,Color.color(0,0,0));
				}
			}
		}
		int inij = 0;
		int inii = 0;
		for(int z = 0; z < otrosColores.size(); z++){
			OtroColor oc = (OtroColor)otrosColores.pop();
			r = b = g = 0;
			inii = oc.getX()-4;
			inij = oc.getY()-4;
			if(inii < 0)
				inii = 0;
			if(inij < 0)
				inij = 0;
			for(int i = inii;i < (oc.getX()+4);i++){
				for(int j = inij;j < (oc.getY()+4);j++){
					if(i == j)
						continue;
					c = pr.getColor(i,j);
					r += c.getRed();
					g += c.getGreen();
					b += c.getBlue();
				}
			}
			c = Color.color(r/81,g/81,b/81);
			pw.setColor(oc.getX(),oc.getY(),c);
		}
	}

	public void run(){
		quita();
	}
}