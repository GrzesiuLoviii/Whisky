package com.mycompany.whisky;

/**
 * Created by Karol on 2014-12-30.
 * Klasa zawiera nazwę i flagę regionów, w których produkowana jest whisky.
 * Informacje będą wyświetlane w listView.
 */

public class Regiony {

    /**
     * @param nazwa Nazwa regionu
     * @param ikona Adres do zdjęcia flagi kraju, w którym znajduje się region
     */
    private String nazwa;
    private int ikona;

    public Regiony(String nazwa, int ikona) {
        this.nazwa = nazwa;
        this.ikona = ikona;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getIkona() {
        return ikona;
    }
}
