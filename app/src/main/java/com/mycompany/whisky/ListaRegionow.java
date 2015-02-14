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
 * Klasa tworzy nowe Activity (nowe okno), w którym wyświetlana jest lista regionów.
 * Do tego celu wykorzystywane jest listView oraz baza danych, z której pobierane są rekordy.
 */

public class ListaRegionow extends Activity {

    /**
     * @param mojeRegiony Lista regionów
     * @param region Nazwa regionu
     */
    private List<Regiony> mojeRegiony = new ArrayList<Regiony>();
    protected static String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_regionow);

        //Połączenie z bazą danych i dodanie rekordów (regionów) do ArrayList.
        ZarzadcaBazy zb = new ZarzadcaBazy(this);
        Cursor k = zb.dajWszystkieRegiony();

        while (k.moveToNext()) {
            String nazwa = k.getString(1);
            int ikona = k.getInt(2);
            mojeRegiony.add(new Regiony(nazwa, ikona));
        }

        //Wyświetlenie listView w oknie
        listaWidoku();
        //Metoda wykrywa wybranie elementu z listy i otwiera nowe Activity
        wybranieElementuZListy();
    }

    /**
     * Metoda tworzy listę regionów w listView
     */
    private void listaWidoku() {
        ArrayAdapter<Regiony> adapter = new MyListAdapter();
        ListView lista = (ListView) findViewById(R.id.listaRegionow);
        lista.setAdapter(adapter);
    }

    /**
     * Metoda uzupełnia listView danymi odebranymi wcześniej z bazy danych
     */
    private class MyListAdapter extends ArrayAdapter<Regiony>  {
        public MyListAdapter(){
            super(ListaRegionow.this, R.layout.widok_listy_miejsca, mojeRegiony);
        }

        public View getView(int position, View converView, ViewGroup parent) {
            View elementWidoku = converView;

            if(elementWidoku == null)
                elementWidoku = getLayoutInflater().inflate(R.layout.widok_listy_miejsca, parent, false);

            Regiony obecnyRegion = mojeRegiony.get(position);
            //Ustawienie zdjęcia flagi kraju do którego należy region w elemencie listy
            ImageView widokZdjecia = (ImageView)elementWidoku.findViewById(R.id.zdjecieNaLiscie);
            widokZdjecia.setImageResource(obecnyRegion.getIkona());

            //Ustawienie nazwy regionu w elemencie listy
            TextView widokNazwy = (TextView) elementWidoku.findViewById(R.id.nazwaMiejsca);
            widokNazwy.setText(obecnyRegion.getNazwa());

            return elementWidoku;
        }
    }

    /**
     * Metoda jest wywoływana po wybraniu dowolnego elementu z listView
     */
    private void wybranieElementuZListy() {
        ListView lista = (ListView) findViewById(R.id.listaRegionow);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {
                Regiony kliknietyRegion = mojeRegiony.get(position);
                region = kliknietyRegion.getNazwa();

                //Wyświetlenie nowego Activity dla wciśniętego regionu
                wyswietlButelkiDlaRegionu();
            }
        });
    }

    /**
     * Wyświetlenie nowego Activity. W oknie tym zostaną wyświetlone wszystkie
     * butelki dla wybranego przez użytkownika regionu
     */
    private void wyswietlButelkiDlaRegionu() {
        Intent i = new Intent(this,WyswietlButelkiDlaRegionu.class);
        startActivity(i);
    }
}
