package com.mycompany.whisky;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Karol on 2014-12-30.
 * Klasa tworzy nowe Activity (nowe okno), w którym wyświetlane są informacje
 * o wybranej wcześniej butelce whisky.
 */

public class WyswietlButelke extends ActionBarActivity {

    /**
     * @param nazwa Nazwa butelki
     * @param wiek Wiek butelki
     * @param strona Adres URL do strony producenta
     * @param ikona Adres do zdjęcia butelki
     */
    private String nazwa,
                   wiek,
                   strona;
    private int ikona;

    ZarzadcaBazy zb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyswietl_butelke);

        zb = new ZarzadcaBazy(this);

        //Odebranie danych o butelce
        odebranieInformacjiOButelce();
        //Ustawienie informacji o butelce
        ustawienieInformacjiOButelceWOknie();

        //Obsługa przycisku przejścia na stronę producenta whisky
        Button przyciskStrony = (Button) findViewById(R.id.przyciskDoStrony);
        OnClickListener sluchacz = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(strona);
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        };
        przyciskStrony.setOnClickListener(sluchacz);

        //Obsługa przycisku ocenienia danej butelki whisky
        Button przyciskOceny = (Button) findViewById(R.id.przyciskOcen);
        OnClickListener sluchacz2 = new OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                double ocena = ratingBar.getRating();
                //Dokonanie aktualizacji w bazie danych (ocenienie butelki przez użytkownika)
                zb.aktualizujButelke(WyswietlButelke.this.nazwa, wiek, ocena);

                Toast.makeText(getApplicationContext(), "Whisky zostało ocenione na " + ocena + "/5", Toast.LENGTH_SHORT).show();
            }
        };
        przyciskOceny.setOnClickListener(sluchacz2);

        //Wyzerowanie zmiennych
        ListaWszystkie.nazwa = null;
        WyswietlButelkiDlaKraju.nazwa = null;
        WyswietlButelkiDlaRegionu.nazwa = null;
    }

    /**
     * Sprawdzenie, z którego okna przeszliśmy do wyświetlenia informacji o butelce
     * w celu odebrania takich informacji jak nazwa, wiek, ikona i strona producenta whisky
     */
    private void odebranieInformacjiOButelce() {
        if(ListaWszystkie.nazwa != null) {
            nazwa = ListaWszystkie.nazwa;
            wiek = ListaWszystkie.wiek;
            ikona = ListaWszystkie.ikona;
            strona = ListaWszystkie.strona;
        } else if (WyswietlButelkiDlaKraju.nazwa != null) {
            nazwa = WyswietlButelkiDlaKraju.nazwa;
            wiek = WyswietlButelkiDlaKraju.wiek;
            ikona = WyswietlButelkiDlaKraju.ikona;
            strona = WyswietlButelkiDlaKraju.strona;
        } else if (WyswietlButelkiDlaRegionu.nazwa != null){
            nazwa = WyswietlButelkiDlaRegionu.nazwa;
            wiek = WyswietlButelkiDlaRegionu.wiek;
            ikona = WyswietlButelkiDlaRegionu.ikona;
            strona = WyswietlButelkiDlaRegionu.strona;
        } else if (OcenioneButelki.nazwa != null){
            nazwa = OcenioneButelki.nazwa;
            wiek = OcenioneButelki.wiek;
            ikona = OcenioneButelki.ikona;
            strona = OcenioneButelki.strona;
        } else {
            nazwa = SzukajButelki.nazwa;
            wiek = SzukajButelki.wiek;
            ikona = SzukajButelki.ikona;
            strona = SzukajButelki.strona;
        }
    }

    /**
     * Odczytanie informacji o butelce i ustawienie ich w oknie
     */
    private void ustawienieInformacjiOButelceWOknie() {
        TextView nazwaButelki = (TextView)findViewById(R.id.nazwa_butelki);
        nazwaButelki.setText(this.nazwa);

        TextView wiekButelki = (TextView)findViewById(R.id.podajWiek);
        wiekButelki.setText(wiek);

        ImageView adresZdjeciaButelki = (ImageView)findViewById(R.id.zdjecieNaLiscie);
        adresZdjeciaButelki.setImageResource(ikona);

        //Opis butelki
        String opis = "To flagowy produkt firmy Brown-Forman. Produkowana jest od 1875 roku.\n\n " +
                "Zawiera: \n 78% kukurydzy,\n 12% żyta\n 10% słodowanego jęczmienia.\n\n" +
                "Aromat: lekki i gładki z dużą ilością słodyczy i nutami orzechów, wanilii, " +
                "węgla drzewnego, banana, gruszki, kukurydzy, dębu, cynamonu i gałki muszkatołowej.\n\n" +
                "Smak: słodki i głęboki z akcentami kukurydzy, dębu, wanilii, karmelu, toffi, melasy," +
                " syropu klonowego, węgla drzewnego, skóry, tytoniu i cytrusów.\n\n" +
                "Finisz: przyjemny i słodki z kremowymi nutami i tonami wanilii, karmelu, orzechów i " +
                "zwęglonego dębu.";

        TextView opisButelki = (TextView)findViewById(R.id.opisButelki);
        opisButelki.setText(opis);
    }
}
