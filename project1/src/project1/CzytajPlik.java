package project1;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class CzytajPlik {
  
	
	 private String sciezka;
     
	    public CzytajPlik(String plik_sciezka){
	        sciezka = plik_sciezka;
	        
	    }
	    public String[] OtworzPlik() throws IOException {
	         
	        FileReader fr = new FileReader(sciezka);
	        BufferedReader czytajTekst = new BufferedReader(fr);
	         
	        int liczbaWierszy = CzytajLinie();
	        String[] tekstDane = new String[liczbaWierszy];
	         
	        int i;
	         
	        for(i=0; i < liczbaWierszy; i++){
	            tekstDane[i] = czytajTekst.readLine();
	        }
	         
	        czytajTekst.close();
	        return tekstDane;
	    }
	    
	    int CzytajLinie() throws IOException {
	         
	        FileReader plik_do_czytania = new FileReader(sciezka);
	        BufferedReader bf = new BufferedReader(plik_do_czytania);
	         
	        String linie;
	        int liczbaLinii = 0;
	        while((linie = bf.readLine()) != null){
	            liczbaLinii++;
	        }
	        bf.close();
	         
	        return liczbaLinii;
	    }
}