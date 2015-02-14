package com.mycompany.whisky;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Karol on 2014-12-27.
 * Główna klasa aplikacji. Klasa tworzy główne okno aplikacji, na którym wyświetlane jest menu.
 * Znajduje się tutaj obsługa wszystkich 4 opcji aplikacji ( metody: otwarcie....(View view) )
 * oraz dodanie rekordów do bazy danych ( metoda: polaczenieZBaza() ).
 */

public class OknoGlowne extends ActionBarActivity {

    /**
     * @param przyciskWszystkieMarki Przycisk do wyświetlenia okna z wszystkimi markami whisky.
     * @param przyciskSzukaj Przycisk do wyświetlenia okna z możliwością szukania whisky po nazwie, kraj, roczniku.
     * @param przyciskOcenione Przycisk do wyświetlenia okna z ocenionymi butelkami whisky przez użytkownika.
     * @param przyciskQuiz Przycisk do wyświetlenia okna z quizem związanym z whisky.
     */
    private Button przyciskWszystkie,
                   przyciskSzukaj,
                   przyciskOcenione,
                   przyciskQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okno_glowne);

        przyciskWszystkie = (Button)findViewById(R.id.przyciskWszystkieMarki);
        przyciskSzukaj = (Button)findViewById(R.id.przyciskWyszukajButelke);
        przyciskOcenione = (Button)findViewById(R.id.przyciskOcenione);
        przyciskQuiz = (Button)findViewById(R.id.przyciskQuiz);

        polaczenieZBaza();

    }

    /**
     * Wyświetlenie nowego Activity (nowego okna) aplikacji.
     * Okno wyświetla wszystkie marki whisky z możliwością podziału na kraj oraz region.
     * Metoda jest wywoływana po wciśnięciu przycisku "przyciskWszystkie".
     */
    public void otwarcieOknaWszystkichMarek(View view) {
        Intent i = new Intent(this,WszystkieMarki.class);
        startActivity(i);
    }

    /**
     * Wyświetlenie nowego Activity (nowego okna) aplikacji.
     * Okno umożliwia wyszukanie butelki według nazwa, kraju i/lub rocznika.
     * Metoda jest wywoływana po wciśnięciu przycisku "przyciskSzukaj".
     */
    public void otwarcieOknaWyszukiwaniaButelki(View view) {
        Intent i = new Intent(this,SzukajButelki.class);
        startActivity(i);
    }

    /**
     * Wyświetlenie nowego Activity (nowego okna) aplikacji.
     * Okno wyświetla wszystkie butelki ocenione przez użytkownika.
     * Metoda jest wywoływana po wciśnięciu przycisku "przyciskOcenione".
     */
    public void otwarcieOknaOcenionychButelek(View view) {
        Intent i = new Intent(this,OcenioneButelki.class);
        startActivity(i);
    }

    /**
     * Wyświetlenie nowego Activity (nowego okna) aplikacji.
     * Okno wyświetla quiz. Składa się z 20 pytań, opcje do wyboru: A,B,C,D (tylko jedna jest poprawna).
     * Metoda jest wywoływana po wciśnięciu przycisku "przyciskQuiz".
     */
    public void otwarcieOknaQuizu(View view) {
        Intent i = new Intent(this,Quiz.class);
        startActivity(i);
    }

    /**
     * Metoda dodaje wszystkie rekordy do bazy danych (tj. kraje, regiony, butelki whisky).
     */
    private void polaczenieZBaza(){
        ZarzadcaBazy zb = new ZarzadcaBazy(this);

        // Sprawdzenie czy w bazie danych są już jakieś rekordy, jeżeli nie ma, uzupełniamy bazę danych
        if(zb.dajWszystkieButelki().getCount() == 0) {
            //Dodanie krajów (nazwa kraju, adres do zdjęcia flagi kraju)
            zb.dodajKraj("Szkocja", 0x7f020044);
            zb.dodajKraj("Irlandia", 0x7f020040);
            zb.dodajKraj("Japonia", 0x7f020041);
            zb.dodajKraj("USA", 0x7f020045);
            zb.dodajKraj("Kanada", 0x7f020042);
            zb.dodajKraj("Europa", 0x7f02003e);
            zb.dodajKraj("RPA", 0x7f020043);
            zb.dodajKraj("Indie i daleki wschód", 0x7f02003f);
            zb.dodajKraj("Australia", 0x7f02003d);

            //Dodanie regionów (nazwa regionu, adres do zdjęcia flagi kraju, w którym znajduje się region)
            zb.dodajRegion("Lowlands", 0x7f020044);
            zb.dodajRegion("Highlands", 0x7f020044);
            zb.dodajRegion("Speyside", 0x7f020044);
            zb.dodajRegion("Wyspy", 0x7f020044);
            zb.dodajRegion("Cambeltown", 0x7f020044);
            zb.dodajRegion("Tennessee", 0x7f020045);
            zb.dodajRegion("Kentucky", 0x7f020045);
            zb.dodajRegion("Reszta USA", 0x7f020045);

            //Dodanie butelek (nazwa butelki, kraj, region, wiek, oceniona, ocena, opis, adres do zdjęcia butelki, adres do strony producenta)
            //Oceniona: 0 - nie oceniona, 1 - oceniona
            zb.dodajButelke("Jack Daniel's No. 7", "USA", "Tennessee", "4-letnia", 0, 0.0, "", 0x7f02003b, "http://www.jackdaniels.com");
            zb.dodajButelke("The Speyside", "Szkocja", "Speyside", "12-letnia", 0, 0.0, "", 0x7f020047, "http://www.speysidedistillery.co.uk");
            zb.dodajButelke("Glenmorangie: The Original", "Szkocja", "Highlands", "10-letnia", 0, 0.0, "", 0x7f020039, "http://www.glenmorangie.com");
            zb.dodajButelke("Bladnoch", "Szkocja", "Lowlands", "8-letnia", 0, 0.0, "", 0x7f020034, "http://www.bladnoch.co.uk");
            zb.dodajButelke("Laphroaig", "Szkocja", "Wyspy", "10-letnia", 0, 0.0, "", 0x7f020046, "http://www.laphroaig.com");
            zb.dodajButelke("Bushmills Original", "Irlandia", "", "3-letnia", 0, 0.0, "", 0x7f020035, "http://www.bushmills.com");
            zb.dodajButelke("Yamazaki", "Japonia", "", "10-letnia", 0, 0.0, "", 0x7f020049, "http://www.theyamazaki.jp/en/distillery/museum.html");
            zb.dodajButelke("Jim Beam White Label", "USA", "Kentucky", "3-letnia", 0, 0.0, "", 0x7f02003c, "http://www.jimbeam.com");
            zb.dodajButelke("Glen Breton", "Kanada", "", "10-letnia", 0, 0.0, "", 0x7f020038, "http://www.glenoradistillery.com");
            zb.dodajButelke("Embrujo", "Europa", "", "3-letnia", 0, 0.0, "", 0x7f020036, "http://www.destileriasliber.com");
            zb.dodajButelke("Bain's Cape Mountain", "RPA", "", "3-letnia", 0, 0.0, "", 0x7f020033, "http://www.distell.co.za");
            zb.dodajButelke("Amrut Fusion", "Indie i daleki wschód", "", "3-letnia", 0, 0.0, "", 0x7f020037, "http://www.amrutdistilleries.com");
        }
    }
}
