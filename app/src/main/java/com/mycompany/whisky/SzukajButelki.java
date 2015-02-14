package com.mycompany.whisky;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karol on 2014-12-02.
 * Klasa tworzy nowe Activity (nowe okno), w którym użytkownik może wyszukiwać interesujących go butelek.
 * Może szukać po nazwie i/lub wieku i/lub kraju, w  którym jest ona produkowana.
 */

public class SzukajButelki extends Activity {

    /**
     * @param autoUzupelnianieTekstu Obszar z wpisywaniem tekstu z podpowiedziami
     * @param mojeButelki Lista butelek
     * @param nazwa Nazwa butelki
     * @param wiek Wiek butelki
     * @param strona Adres URL do strony producenta
     * @param ikona Adres do zdjęcia butelki
     * @param szukanaNazwa Nazwa butelki, którą podał użytkownik, którą chce znaleźć
     * @param szukanyWiek Wiek butelki, który wybrał użytkownik
     * @param szukanyKraj Kraj, z którego pochodzi butelka, który wybrał użytkownik
     * @param spinnerWiek Spinner, który służy do wyboru wiek butelki przez użytkownika
     * @param spinnerKraj Spinner, który służy do wyboru kraju, z którego pochodzi butelka przez użytkownika
     * @param adapterButelki Lista elementów jakie mają być dodane do listViev
     */
    private AutoCompleteTextView autoUzupelnianieTekstu;
    private List<Butelki> mojeButelki = new ArrayList<Butelki>();
    protected static String nazwa,
                            wiek,
                            strona;
    protected static int ikona;
    private String szukanaNazwa, szukanyWiek, szukanyKraj;
    private Spinner spinnerWiek, spinnerKraj;
    private ArrayAdapter<Butelki> adapterButelki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szukaj_butelki);

        //Po wpisaniu pierwszych liter wyświetlą się podpowiedzi
        podpowiedziTekstu();

        Button przyciskSzukaj = (Button) findViewById(R.id.przyciskSzukaj);
        spinnerWiek = (Spinner) findViewById(R.id.spinnerWiek);
        spinnerKraj = (Spinner) findViewById(R.id.spinnerKraj);
    }

    /**
     * Metoda umożliwia wyświetlenie podpowiedzi po wpisaniu przez użytkownika pierwszych liter
    */
    private void podpowiedziTekstu() {
        String[] nazwaButelki = getResources().
                getStringArray(R.array.listaPodpowiedziNazwButelek);
        ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1,nazwaButelki);
        autoUzupelnianieTekstu = (AutoCompleteTextView) findViewById(R.id.wprowadzanieNazwyButelkiZPodpowiedzia);
        autoUzupelnianieTekstu.setAdapter(adapter);
    }

    /**
     * Metoda odczytuje dane wprowadzone przez użytkownika, łączy się z bazą w celu wybrania
     * rekordów, które spełniają warunki użytkownika następnie dodaje je i wyświetla w listView.
     * @param view Widok listy butelek w postaci listView
     */
    public void wyswietlListeWyszukanychButelek(View view) {
        szukanaNazwa = autoUzupelnianieTekstu.getText().toString();
        szukanyWiek = spinnerWiek.getSelectedItem().toString();
        szukanyKraj = spinnerKraj.getSelectedItem().toString();

        adapterButelki = new MyListAdapter();
        adapterButelki.clear();

        //Połączenie z bazą danych i dodanie rekordów (butelek) do ArrayList.
        ZarzadcaBazy zb = new ZarzadcaBazy(this);
        //Jeżeli użytkownik nie wybierze żadnego parametru zostanie wyświetlony komunikat
        if(szukanaNazwa.equals("") && szukanyWiek.equals("Wszystkie") && szukanyKraj.equals("Wszystkie"))
            Toast.makeText(getApplicationContext(), "Zawęź swoje kryteria", Toast.LENGTH_SHORT).show();
        else {
            Cursor k = zb.dajWyszukaneButelki(szukanaNazwa, szukanyWiek, szukanyKraj);

            while (k.moveToNext()) {
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
        ListView lista = (ListView) findViewById(R.id.listaWyszukanychButelek);
        lista.setAdapter(adapterButelki);
    }

    /**
     * Metoda uzupełnia listView danymi odebranymi wcześniej z bazy danych
     */
    private class MyListAdapter extends ArrayAdapter<Butelki>  {
        public MyListAdapter(){
            super(SzukajButelki.this, R.layout.widok_listy_butelki, mojeButelki);
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
        ListView lista = (ListView) findViewById(R.id.listaWyszukanychButelek);
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
