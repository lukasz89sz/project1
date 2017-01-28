package project1;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.border.EtchedBorder;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
public class main{
	
	static int a=0;
	static int max=0;
	
	static String[] wierszTablicy;
	 static String nazwa_pliku = "test.txt";
	 static Boolean znjaduje=false;
	 static String nazwa=null;
	 static CzytajPlik plik = new CzytajPlik(nazwa_pliku);
	 static JTextField  text=new JTextField(20);
	 static int licznik=0;
	 static Boolean bool=false;
	 static Boolean sprawdz_test=false;
	 static Mat webcam_image; 
	
	 public static void main(String arg[]) throws IOException{  
		
		
	    recognition rec= new recognition ();
			
		// Load the native library.  
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);       
   		String window_name = "Capture - Face detection";  
   		
   		JFrame frame = new JFrame(window_name);  
		
   		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		frame.setSize(400,700);  
	      
		
		 
	     
	         
	        //Zapis do pliku
	        final ZapiszPlik dane = new ZapiszPlik(nazwa_pliku,true);
	       //dane.zapiszDoPliku("1-lukasz_1");
	        //dane.zapiszDoPliku("1-lukasz_2");
	      
	       	//dane.zapiszDoPliku("2-marek_1");
	    
	     
	       
	         
	  
		
		final Processor my_processor = new Processor();  
		My_Panel my_panel = new My_Panel();  
		
		 JButton dodaj = new JButton("Dodaj osobe");
		 final JButton sprawdz = new JButton("Sprawdz");
		
		  JPanel buttonPane = new JPanel();
          buttonPane.add(dodaj);
          final Label l=new Label("Hi There!");
		 
          frame.setLayout(new BorderLayout());
          dodaj.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae){
            	 
            	  String wypisz=null;
              	
             
             	  int pozycja=0;
             	  Integer x = null;
             	  String poz="rrr";
             	  licznik=0;
             	 znjaduje=false;
       	           
				try {
					wierszTablicy = plik.OtworzPlik();
				
       	            max=0;
       	            int i;
       	            String p	;
       	            for(i=0; i < plik.CzytajLinie();i++){
       	            
       	         
       	               
       	            p=wierszTablicy[i].substring(0, 1);
       	            if(max==0) max = Integer.valueOf( p);
       	            if(max<Integer.valueOf( p))max=Integer.valueOf( p);
       	            if(wierszTablicy[i].contains(text.getText())){licznik+=1;
       	             
       	             System.out.println(wierszTablicy[i]);
       	             pozycja=i; 
       	             wypisz=Integer.toString(licznik);
       	             poz=wierszTablicy[i].substring(0, 1);
       	             x=Integer.valueOf( poz);	
       	             
       	             znjaduje=true; }
       	            }
       	            
       	            licznik+=1;
       	          
       	            nazwa=poz+"-"+text.getText()+"_"+licznik;
       	            //if(!znjaduje){ max+=1; nazwa=max+"-"+text.getText()+"_1";}
       	            max+=1; nazwa=max+"-"+text.getText()+"_1";
       	          dane.zapiszDoPliku(nazwa);
       	            	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       	        
       	          
				String pom="C://facerecognizer//"+nazwa+".png";
            	
       		 	File destFile = new File(pom);
       		 	BufferedImage image = MatToBufferedImage(my_processor.cropped);
       		 	//image=grayscale(image);
            	BufferedImage tempPNG = resizeImage(image, 400, 300);
            	   try {
					ImageIO.write(tempPNG, "png", destFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 
             	  	l.setText("dodano");
             	  	
             	  
             	  
             	 
              }
          
          
           });
          
          
          sprawdz.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae){
            	  
            	if(sprawdz_test==false){
            	  sprawdz.setText("Wyczysc");
            	  l.setText("dodaj");
            	  	try {
						wierszTablicy = plik.OtworzPlik();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            		
					for(int i=0; i < wierszTablicy.length;i++){
       	            String p=wierszTablicy[i].substring(0, 1); 
       	            if( a== Integer.valueOf(p)){
       	            my_processor.imie=wierszTablicy[i].substring(2,wierszTablicy[i].length()-2  );
       	            text.setText(wierszTablicy[i].substring(2,wierszTablicy[i].length()-2  ));
       	            }}
					bool=true;
					l.setText(":Osoba " );
					sprawdz_test=true;
            	}else{
            		
            		bool=false;	
            		sprawdz_test=false;
            		text.setText("");
            		sprawdz.setText("Sprawdz");
            	}
            		
          
            	  
              }
           });
          my_panel.add(dodaj, BorderLayout.PAGE_END);
          my_panel.add( sprawdz, BorderLayout.PAGE_END);
         
          my_panel.add(text, BorderLayout.PAGE_END);
         
          my_panel.add(l);
	        
			frame.setContentPane(my_panel); 
		       
		frame.setVisible(true);        
		 
		
		//-- 2. Read the video stream  
		 
		VideoCapture capture = new VideoCapture(0);   
		webcam_image=new Mat(); 
		if( capture.isOpened()){  
			while( true ){  
				capture.read(webcam_image);  
	            if(!webcam_image.empty()){
	            	 
	            		 File destFile = new File("zdjecie.png");
	            		
	            		  //BufferedImage image = MatToBufferedImage(webcam_image);
	                 	  //BufferedImage tempPNG = resizeImage(image, 400, 300);
	                 	  //image=grayscale(image);
	                 	   //ImageIO.write(tempPNG, "png", destFile);
	                 	   	
	                 	  File destFile1 = new File("zdjecie.png");
	                 	  if(my_processor.cropped!=null){
	                 	  //BufferedImage image1 = MatToBufferedImage(my_processor.cropped);
	                 	  //image1=grayscale(image1);
	                 	  //BufferedImage tempPNG1 = resizeImage(image1, 400, 300);
	                 	   //ImageIO.write(tempPNG1, "png", destFile1);
	                 	   //a=recognition.recognit();
	                 	 
	                 	  }
	            	
	            
	            	 
	            	
						
	            	
	            	
					frame.setSize(webcam_image.width()+100, webcam_image.height()+150);  
					//-- 3. Apply the classifier to the captured image  
					webcam_image=my_processor.detect(webcam_image);  
					//-- 4. Display the image  
					my_panel.MatToBufferedImage(webcam_image); // We could look at the error...  
					my_panel.repaint();   
				}  
	            else{   
	            	System.out.println(" --(!) No captured frame -- Break!");   
	            	break;   
	               }  
			}  
		}  
	    
              return;  
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
    
    
    public static BufferedImage grayscale(BufferedImage img) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor =
                        new Color(
                        red + green + blue,
                        red + green + blue,
                        red + green + blue);

                img.setRGB(j, i, newColor.getRGB());
            }
        }

        return img;
    }
    
    
}