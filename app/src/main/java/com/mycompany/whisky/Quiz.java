package com.mycompany.whisky;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Karol on 2015-02-14.
 * Klasa tworzy nowe Activity (nowe okno), w którym wyświetlany jest quiz związany z whisky.
 * Jeden quiz składa się z 15 pytań. Możliwe opcje do wyboru: A,B,C,D. Na każde pytanie jest 60 sekund.
 * Na koniec prezentowany jest uzyskany wynik.
 */

public class Quiz extends Activity {

    TextView numerPytania, czas, trescPytania;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        numerPytania = (TextView) findViewById(R.id.numerPytania);
        czas = (TextView) findViewById(R.id.czas);
        trescPytania = (TextView) findViewById(R.id.trescPytania);
        trescPytania.setEnabled(false);

        odliczanieCzasu();
    }

    private void odliczanieCzasu(){
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished/1000 < 10)
                    czas.setTextColor(Color.RED);
                else
                    czas.setTextColor(Color.BLACK);
                czas.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Czas się skończył. Za to pytanie nie dostaniesz punktu.", Toast.LENGTH_LONG).show();
            }
        }.start();
    }
}
