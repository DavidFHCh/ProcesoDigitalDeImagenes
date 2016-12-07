package Procesador;

import java.util.*;
import java.io.*;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import org.apache.commons.io.*;
import org.apache.commons.io.filefilter.*;
import javafx.scene.paint.Color;

public class FotoMosaico{

	private PixelWriter pw;
	private PixelReader pr;
	private static PixelReader prAux;
	private static PixelWriter pwAux;
	private int width;
	private int height;
	private int regionAlto;
	private int regionAncho;
	private int imagenPequeñaAlto;
	private int imagenPequeñaAncho;
	private File dir;

	public FotoMosaico(PixelWriter pw1,PixelReader pr1,int width1, int height1, int regionAlto,int regionAncho, int imagenPequeñaAlto,int imagenPequeñaAncho,File dir){
		pw = pw1;
		pr = pr1;
		width = width1;
		height = height1;
		this.regionAlto = regionAlto;
		this.regionAncho = regionAncho;
		this.imagenPequeñaAlto = imagenPequeñaAlto;
		this.imagenPequeñaAncho = imagenPequeñaAncho;
		this.dir = dir;
	}

	private class Imagen{

		private String nombre;
		private double red;
		private double green;
		private double blue;

		public Imagen(String nombre,double red,double green,double blue){
			this.nombre = nombre;
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		public String getNombre(){
			return nombre;
		}

		public double getR(){
			return red;
		}

		public double getG(){
			return green;
		}

		public double getB(){
			return blue;
		}
	}

	private ArrayList<Imagen> obtenImagenes(File dir){
		ArrayList<Imagen> l = new ArrayList<>();
		try{
			double pixeles = imagenPequeñaAncho*imagenPequeñaAlto;
			
			Iterator it = FileUtils.iterateFiles(dir,  new RegexFileFilter( "([^\\s]+(\\.(?i)(jpg|png|gif|bmp|JPG|PNG))$)"), DirectoryFileFilter.DIRECTORY);
        	while(it.hasNext()){
        	    File file = (File)it.next();
				
            Image img = new Image(new FileInputStream(file));
				
            WritableImage wimg = new WritableImage(imagenPequeñaAncho,imagenPequeñaAlto);
        	    prAux = img.getPixelReader();
        	    pwAux =  wimg.getPixelWriter();
            Reducir redu = new Reducir(pwAux,prAux,(int)img.getWidth(),(int)img.getHeight(),imagenPequeñaAlto,imagenPequeñaAncho);
				redu.run();

				prAux = wimg.getPixelReader();
	
				double red = 0;
				double green = 0;
				double blue = 0;
				Color color = null;

				try{
					for(int i = 0; i < imagenPequeñaAncho;i++){
						for(int j = 0; j < imagenPequeñaAlto;j++){
							color = prAux.getColor(i,j);
							red += color.getRed();
							green += color.getGreen();
							blue += color.getBlue();
						}
					}
				}catch(Exception e){continue;}

				red = red/pixeles;
				green = green/pixeles;
				blue = blue/pixeles;


        	    l.add(new Imagen(file.getAbsolutePath(),red,green,blue));
        	}
        	}catch(Exception fnfe){}	
        return l;
	}


	private Image getCercana(Color c, ArrayList<Imagen> l){
		double red1 = c.getRed();
		double green1 = c.getGreen();
		double blue1 = c.getBlue();
		Imagen minima = null;
		Imagen actual = l.get(0);
		minima = actual;
		double min = dist(actual.getR(),actual.getG(),actual.getB(),red1,green1,blue1);
		double act = 0;
		for(int i = 1; i < l.size();i++){
			actual = l.get(i);
			act = dist(actual.getR(),actual.getG(),actual.getB(),red1,green1,blue1);
			if(act < min)
				minima = actual;
		}
		Image im = null;
		try{im = new Image(new FileInputStream(new File(minima.getNombre())));}
		catch(Exception e){}
		return im;
	}

	private double dist(double x0,double y0, double z0,double x1,double y1, double z1){
		return Math.sqrt(((x0-x1)*(x0-x1))+((y0-y1)*(y0-y1))+((z0-z1)*(z0-z1)));
	}


	public void run(){
		try{
		Color c = null;
		Image img = null;
		ArrayList<Imagen> imgs = obtenImagenes(dir);
		int x = width/regionAncho;
		int y = height/regionAlto;
		WritableImage wimg = new WritableImage(x,y);
		PixelWriter pwredu = wimg.getPixelWriter();
		Icono redu = new Icono(pwredu,pr,width,height,x,y,true);
		redu.run();
		pr = wimg.getPixelReader();
		for(int i = 0;i < x; i++){
			for (int j = 0;j < y ;j++ ){
				c = pr.getColor(i,j);
				img = getCercana(c,imgs);
				if(img == null)
					continue;
				prAux = img.getPixelReader();

				int x1 = 0;
				int y1 = 0;
				for(int k = i*regionAncho; k < (i+1)*regionAncho;k++){
					for(int l = j*regionAlto; l < (j+1)*regionAlto;l++){
						pw.setColor(k,l,prAux.getColor(x1,y1));
						y1++;
					}
					x1++;
				}
			}
		}
		}catch(Exception e){e.printStackTrace();}
	}
}
