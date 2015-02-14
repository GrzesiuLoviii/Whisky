package com.mycompany.whisky;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2014-12-28.
 * Klasa tworzy nowe Activity (nowe okno), w którym wyświetlane są wszystkie butelki whisky.
 * Do tego celu wykorzystywane jest listView oraz baza danych, z której pobierane są rekordy.
 */

public class ListaWszystkie extends Activity {

    /**
     * @param mojeButelki Lista butelek
     * @param nazwa Nazwa butelki
     * @param wiek Wiek butelki
     * @param strona Adres URL do strony producenta
     * @param ikona Adres do zdjęcia butelki
     * @param ocena Ocena jaką użytkownik przyznał danej whisky
     */
    private List<Butelki> mojeButelki = new ArrayList<Butelki>();
    protected static String nazwa,
                            wiek,
                            strona;
    protected static int ikona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_wszystkie);

        //Połączenie z bazą danych i dodanie rekordów (butelek) do ArrayList.
        ZarzadcaBazy zb = new ZarzadcaBazy(this);
        Cursor k = zb.dajWszystkieButelki();

        while (k.moveToNext()){
            String nazwa = k.getString(1);
            String kraj = k.getString(2);
            String region = k.getString(3);
            String wiek = k.getString(4);
            int ocena = k.getInt(6);
            String miejsce = kraj + " " + region;
            int ikona = k.getInt(8);
            String strona = k.getString(9);
            mojeButelki.add(new Butelki(nazwa, wiek, miejsce, ikona, strona, ocena));
        }

        //Wyświetlenie listView w oknie
        listaWidoku();
        //Metoda wykrywa wybranie elementu z listy i otwiera nowe Activity
        wybranieElementuZListy();
    }

    /**
     * Metoda tworzy listę butelek w listView
     */
    private void listaWidoku() {
        ArrayAdapter<Butelki> adapter = new MyListAdapter();
        ListView lista = (ListView) findViewById(R.id.listaWszystkie);
        lista.setAdapter(adapter);
    }

    /**
     * Metoda uzupełnia listView danymi odebranymi wcześniej z bazy danych
     */
    private class MyListAdapter extends ArrayAdapter<Butelki>  {
        public MyListAdapter(){
            super(ListaWszystkie.this, R.layout.widok_listy_butelki, mojeButelki);
        }

        public View getView(int position, View converView, ViewGroup parent) {
            View elementWidoku = converView;

            if(elementWidoku == null)
                elementWidoku = getLayoutInflater().inflate(R.layout.widok_listy_butelki, parent, false);

            Butelki obecnaButelka = mojeButelki.get(position);
            //Ustawienie zdjęcia butelki w elemencie listy
            ImageView widokZdjecia = (ImageView)elementWidoku.findViewById(R.id.zdjecieNaLiscie);
            widokZdjecia.setImageResource(obecnaButelka.getIkona());
            //Ustawienie nazwy butelki w elemencie listy
            TextView widokNazwy = (TextView) elementWidoku.findViewById(R.id.nazwaButelki);
            widokNazwy.setText(obecnaButelka.getNazwa());
            //Ustawienie wieku butelki w elemencie listy
            TextView widokWieku = (TextView) elementWidoku.findViewById(R.id.wiekButelki);
            widokWieku.setText(obecnaButelka.getWiek());
            //Ustawienie miejsca produkcji butelki w elemencie listy
            TextView widokMiejsca = (TextView) elementWidoku.findViewById(R.id.miejsceButelki);
            widokMiejsca.setText(obecnaButelka.getKraj());

            return elementWidoku;
        }
    }

    /**
     * Metoda jest wywoływana po wybraniu dowolnego elementu z listView
     */
    private void wybranieElementuZListy() {
        ListView lista = (ListView) findViewById(R.id.listaWszystkie);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {
                Butelki kliknietaButelka = mojeButelki.get(position);
                nazwa = kliknietaButelka.getNazwa();
                wiek = kliknietaButelka.getWiek();
                ikona = kliknietaButelka.getIkona();
                strona = kliknietaButelka.getStrona();

                //Wyświetlenie nowego Activity dla wciśniętej butelki
                wyswietlButelke();
            }
        });
    }

    /**
     * Wyświetlenie nowego Activity. W oknie tym zostanie wyświetlona wybrana przez
     * użytkownika butelka. Będzie tam jej dokładny opis oraz możliwość dokonania oceny.
     */
    private void wyswietlButelke() {
        Intent i = new Intent(this,WyswietlButelke.class);
        startActivity(i);
    }
}
