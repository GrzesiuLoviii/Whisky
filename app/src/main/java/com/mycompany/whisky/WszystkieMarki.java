package com.mycompany.whisky;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/**
 * Created by Karol on 2014-12-27.
 * Klasa tworzy nowe Activity (nowe okno) aplikacji oraz dodaje do niego
 * 3 zakładki (Kraj, Region, Wszystkie).
 */

public class WszystkieMarki extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wszystkie_marki);

        dodajZakladki();
    }

    /**
     * Metoda tworzy w Activity 3 zakładki (kraj, region, wszystkie).
     * Zakładki umożliwiają wyświetlenie butelek z podziałem odpowiednia na kraj, region,
     * lub wszystkie(bez podziału).
     */
    private void dodajZakladki() {
        TabHost tabHost = getTabHost();

        //Utworzenie zakładki "Kraj"
        TabSpec zakladkaKraj = tabHost.newTabSpec("Kraj");
        zakladkaKraj.setIndicator("Kraj");
        Intent krajIntent = new Intent(this, ListaKrajow.class);
        zakladkaKraj.setContent(krajIntent);

        //Utworzenie zakładki "Region"
        TabSpec zakladkaRegion = tabHost.newTabSpec("Region");
        zakladkaRegion.setIndicator("Region");
        Intent regionIntent = new Intent(this, ListaRegionow.class);
        zakladkaRegion.setContent(regionIntent);

        //Utworzenie zakładki "Wszystkie"
        TabSpec zakladkaWszystkie = tabHost.newTabSpec("Wszystkie");
        zakladkaWszystkie.setIndicator("Wszystkie");
        Intent wszystkieIntent = new Intent(this, ListaWszystkie.class);
        zakladkaWszystkie.setContent(wszystkieIntent);

        //Dodanie wszystkich zakładek
        tabHost.addTab(zakladkaKraj);
        tabHost.addTab(zakladkaRegion);
        tabHost.addTab(zakladkaWszystkie);
    }
}
