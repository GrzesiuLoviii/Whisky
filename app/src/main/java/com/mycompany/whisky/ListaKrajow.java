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
 * Klasa tworzy nowe Activity (nowe okno), w którym wyświetlana jest lista krajów.
 * Do tego celu wykorzystywane jest listView oraz baza danych, z której pobierane są rekordy.
 */

public class ListaKrajow extends Activity {

    /**
     * @param mojeKraje Lista krajów
     * @param kraj Nazwa kraju
     */
    private List<Kraje> mojeKraje = new ArrayList<Kraje>();
    protected static String kraj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_krajow);

        //Połączenie z bazą danych i dodanie rekordów (krajów) do ArrayList.
        ZarzadcaBazy zb = new ZarzadcaBazy(this);
        Cursor k = zb.dajWszystkieKraje();

        while (k.moveToNext()) {
            String nazwa = k.getString(1);
            int ikona = k.getInt(2);
            mojeKraje.add(new Kraje(nazwa, ikona));
        }

        //Wyświetlenie listView w oknie
        listaWidoku();
        //Metoda wykrywa wybranie elementu z listy i otwiera nowe Activity
        wybranieElementuZListy();
    }

    /**
     * Metoda tworzy listę krajów w listView
     */
    private void listaWidoku() {
        ArrayAdapter<Kraje> adapter = new MyListAdapter();
        ListView lista = (ListView) findViewById(R.id.listaKrajow);
        lista.setAdapter(adapter);
    }

    /**
     * Metoda uzupełnia listView danymi odebranymi wcześniej z bazy danych
     */
    private class MyListAdapter extends ArrayAdapter<Kraje>  {
        public MyListAdapter(){
            super(ListaKrajow.this, R.layout.widok_listy_miejsca, mojeKraje);
        }

        public View getView(int position, View converView, ViewGroup parent) {
            View elementWidoku = converView;

            if(elementWidoku == null)
                elementWidoku = getLayoutInflater().inflate(R.layout.widok_listy_miejsca, parent, false);

            Kraje obecnyKraj = mojeKraje.get(position);
            //Ustawienie zdjęcia flagi kraju w elemencie listy
            ImageView widokZdjecia = (ImageView)elementWidoku.findViewById(R.id.zdjecieNaLiscie);
            widokZdjecia.setImageResource(obecnyKraj.getIkona());

            //Ustawienie nazwy kraju w elemencie listy
            TextView widokNazwy = (TextView) elementWidoku.findViewById(R.id.nazwaMiejsca);
            widokNazwy.setText(obecnyKraj.getNazwa());

            return elementWidoku;
        }
    }

    /**
     * Metoda jest wywoływana po wybraniu dowolnego elementu z listView
     */
    private void wybranieElementuZListy() {
        ListView lista = (ListView) findViewById(R.id.listaKrajow);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {
                Kraje kliknietyKraj = mojeKraje.get(position);
                kraj = kliknietyKraj.getNazwa();

                //Wyświetlenie nowego Activity dla wciśniętego kraju
                wyswietlButelkiDlaKraju();
            }
        });
    }

    /**
     * Wyświetlenie nowego Activity. W oknie tym zostaną wyświetlone wszystkie
     * butelki dla wybranego przez użytkownika kraju
     */
    private void wyswietlButelkiDlaKraju() {
        Intent i = new Intent(this,WyswietlButelkiDlaKraju.class);
        startActivity(i);
    }
}
