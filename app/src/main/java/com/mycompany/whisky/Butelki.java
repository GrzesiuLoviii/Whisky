package com.mycompany.whisky;

/**
 * Created by Karol on 2014-12-30.
 * Klasa zawiera informacje o butelkach Whisky, które będą wyświetlane w listView.
 */

public class Butelki {

    /**
     * @param nazwa Nazwa butelki whisky
     * @param wiek Wiek whisky
     * @param region Kraj (i opcjonalnie region) destylarni, w której powstaje whisky
     * @param ikona Adres do zdjęcia butelki
     * @param strona Adres URL do strony producenta
     * @param ocena Ocena jaką użytkownik przyznał danej whisky
     */
    private String nazwa,
                   wiek,
                   kraj,
                   strona;
    private int ikona;
    private double ocena;

    public Butelki(String nazwa, String wiek, String kraj, int ikona, String strona, double ocena) {
        this.nazwa = nazwa;
        this.wiek = wiek;
        this.kraj = kraj;
        this.ikona = ikona;
        this.strona = strona;
        this.ocena = ocena;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getWiek() {
        return wiek;
    }

    public String getKraj() {
        return kraj;
    }

    public int getIkona() {
        return ikona;
    }

    public String getStrona() {
        return strona;
    }

    public double getOcena() {
        return ocena;
    }
}
