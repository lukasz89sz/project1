package project1;


import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
 
public class ZapiszPlik {
     
    private String sciezka;
    private boolean dodaj_do_pliku = false;
     
    public ZapiszPlik(String plik_sciezka){
        sciezka = plik_sciezka;
    }
     
    public ZapiszPlik(String plik_sciezka, boolean dodaj_wartosc){
        sciezka = plik_sciezka;
        dodaj_do_pliku = dodaj_wartosc;
    }
     
    public void zapiszDoPliku(String tekstLinia) throws IOException {
        FileWriter napisz = new FileWriter(sciezka, dodaj_do_pliku);
        PrintWriter drukuj_linie = new PrintWriter(napisz);
         
        drukuj_linie.printf("%s" + "%n", tekstLinia);
        drukuj_linie.close();
    }
     
}