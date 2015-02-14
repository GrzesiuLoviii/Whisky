package com.mycompany.whisky;

/**
 * Created by Karol on 2014-12-29.
 * Klasa zawiera nazwę i flagę krajów, w których produkowana jest whisky.
 * Informacje będą wyświetlane w listView.
 */

public class Kraje {

    /**
     * @param nazwa Nazwa kraju
     * @param ikona Adres do zdjęcia flagi kraju
     */
    private String nazwa;
    private int ikona;

    public Kraje(String nazwa, int ikona) {
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
