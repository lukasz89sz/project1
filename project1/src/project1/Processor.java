package project1;

import org.opencv.core.Mat;  
import org.opencv.core.MatOfRect;  
import org.opencv.core.Point;  
import org.opencv.core.Rect;  
import org.opencv.core.Scalar;  
import org.opencv.core.Size;  

import org.opencv.imgproc.Imgproc;  
import org.opencv.objdetect.CascadeClassifier;  
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_face.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Processor{
	 
	public String imie ="";
	 private CascadeClassifier face_cascade;  
	 public Mat mGrey=new Mat(); 
	 public Mat cropped ;
	 recognition rec= new recognition ();
	static String[] wierszTablicy;
	static String nazwa_pliku = "test.txt";
	static CzytajPlik plik = new CzytajPlik(nazwa_pliku);
	 // Create a constructor method  
     
	public Processor(){  
   	face_cascade = new CascadeClassifier("haarcascade_frontalface_alt.xml");  
          
   	if(face_cascade.empty())  
       {  
   		System.out.println("--(!)Error loading A\n");  
   		return;  
       }  
       else  
       	System.out.println("Face classifier za³adowany");  
     }
     
	public Mat detect(Mat inputframe){  
		
		
		Mat mRgba=new Mat();  
		mGrey=new Mat(); 
		
		MatOfRect faces = new MatOfRect();  
		inputframe.copyTo(mRgba);  
		inputframe.copyTo(mGrey);  
		Imgproc.cvtColor( mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);  
		Imgproc.equalizeHist( mGrey, mGrey );  
		face_cascade.detectMultiScale(mGrey, faces);  
		
		int a=0;
		
		System.out.println(String.format("znaleziono" + faces.toArray().length + " osobe"));  
		for(Rect rect:faces.toArray())  
		{   
			
			
			a++;
			
			Mat uncropped = mRgba;
			//Rect roi = new Rect(rect.y, rect.y + rect.height, rect.x, rect.x + rect.width);
			//cropped = new Mat(uncropped, roi);

		
			int x= rect.y + rect.height;
			int y= rect.x + rect.width;
			cropped  = uncropped.submat(rect.y, x, rect.x, y);
			
			
			String nazwa="zdjecie.png";
			File destFile = new File( nazwa);
    		
  		  	BufferedImage image = MatToBufferedImage(cropped);
  		  	BufferedImage tempPNG = resizeImage(image, 400, 300);
  		  	
  		  try {
  			ImageIO.write(tempPNG, "png", destFile);
  		} catch (IOException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		  	int b;
  		  	b=recognition.recognit();
       	 
  		  try {
				wierszTablicy = plik.OtworzPlik();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  		
			for(int i=0; i < wierszTablicy.length;i++){
	            String p=wierszTablicy[i].substring(0, 1); 
	            if( b== Integer.valueOf(p)){
	            	imie=wierszTablicy[i].substring(2,wierszTablicy[i].length()-2  );
	            	rec.imie=wierszTablicy[i].substring(2,wierszTablicy[i].length()-2  );
			}}
	
			
			Point center= new Point(rect.x + rect.width*0.5, rect.y + rect.height*0.5 );  
			if(main.bool==true){
            Imgproc.putText(mRgba, imie, new Point(rect.x,rect.y),
            		
                    FONT_HERSHEY_PLAIN, 1.0 ,new  Scalar(0,255,255));
            
			}
			Imgproc.ellipse( mRgba, center, new Size( rect.width*0.5, rect.height*0.5), 0, 0, 360, new Scalar( 255, 0, 255 ), 4, 8, 0 );  
		
		

			
			
     
       	  
		} 
		
		return mRgba;  
	}

	private void putText(Mat mRgba, String string, Point point, int fontHersheyPlain, double d, Scalar scalar) {
		// TODO Auto-generated method stub
		
	}

    public static BufferedImage resizeImage(final Image image, int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        //below three lines are for RenderingHints for better image quality at cost of higher processing time
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }
    public static BufferedImage MatToBufferedImage(Mat frame) {
        //Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }
}  
