package com.mycompany.whisky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Karol on 2014-12-30.
 * Klasa odpowiedzialna za utworzenie i operacje na bazie danych. Tworzone są trzy tabele
 * (whisky, kraj, region).
 */

public class ZarzadcaBazy extends SQLiteOpenHelper {
    public ZarzadcaBazy(Context context){
        super(context, "butelki38.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Utworzenie tabeli, w której przechowywane są informacje o butelkach whisky
        db.execSQL("create table whisky( " +
                "nr integer primary key autoincrement, " +
                "nazwa text, " +
                "kraj text, " +
                "region text, " +
                "wiek text," +
                "oceniony int," +
                "ocena double," +
                "info text," +
                "ikona int," +
                "strona text );");

        //Utworzenie tabeli, w której przechowywane są wszystkie kraje
        db.execSQL("create table kraje( " +
                "nr integer primary key autoincrement, " +
                "nazwa text," +
                "ikona int );");

        //Utworzenie tabeli, w której przechowywane są wszystkie regiony
        db.execSQL("create table regiony( " +
                "nr integer primary key autoincrement, " +
                "nazwa text," +
                "ikona int );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Metoda dodaje rekordy do bazy danych (do tabeli whisky).
     * @param nazwa Nazwa butelki
     * @param kraj Kraj, z którego pochodzi butelka
     * @param region Region, z którego pochodzi butelka
     * @param wiek Wiek butelki
     * @param oceniony Czy butelka jest oceniona (0-nie oceniona, 1-oceniona)
     * @param ocena Ocena użytkownika
     * @param info Informacje o butelce
     * @param ikona Adres do zdjęcia butelki
     * @param strona Adres URL do strony producenta
     */
    public void dodajButelke(String nazwa, String kraj, String region, String wiek, int oceniony, double ocena, String info, int ikona, String strona){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Nazwa", nazwa);
        wartosci.put("Kraj", kraj);
        wartosci.put("Region", region);
        wartosci.put("Wiek", wiek);
        wartosci.put("Oceniony", oceniony);
        wartosci.put("Ocena", ocena);
        wartosci.put("Info", info);
        wartosci.put("Ikona", ikona);
        wartosci.put("Strona", strona);
        db.insertOrThrow("whisky", null, wartosci);
    }

    /**
     * Metoda dodaje rekordy do bazy danych (do tabeli kraje).
     * @param nazwa Nazwa kraju
     * @param ikona Adres do zdjęcia kraju
     */
    public void dodajKraj(String nazwa, int ikona){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Nazwa", nazwa);
        wartosci.put("Ikona", ikona);
        db.insertOrThrow("kraje", null, wartosci);
    }

    /**
     * Metoda dodaje rekordy do bazy danych (do tabeli regiony).
     * @param nazwa Nazwa regionu
     * @param ikona Adres do zdjęcia kraju, w którym znajduje się region
     */
    public void dodajRegion(String nazwa, int ikona){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("Nazwa", nazwa);
        wartosci.put("Ikona", ikona);
        db.insertOrThrow("regiony", null, wartosci);
    }

    /**
     * Odebranie wszystkich rekordów (butelek) z bazy danych z tabeli whisky
     * @return Odebranie butelek z bazy danych.
     */
    public Cursor dajWszystkieButelki(){
        String[] kolumny={"Nr", "Nazwa", "Kraj", "Region", "Wiek", "Oceniony", "Ocena", "Info", "Ikona", "Strona"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("whisky", kolumny, null, null, null, null, null);
        return kursor;
    }

    /**
     * Odebranie wszystkich rekordów (krajów) z bazy danych z tabeli kraje
     * @return Odebranie krajów z bazy danych.
     */
    public Cursor dajWszystkieKraje(){
        String[] kolumny={"Nr", "Nazwa", "Ikona"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("kraje", kolumny, null, null, null, null, null);
        return kursor;
    }

    /**
     * Odebranie wszystkich rekordów (regionów) z bazy danych z tabeli regiony
     * @return Odebranie regionów z bazy danych.
     */
    public Cursor dajWszystkieRegiony(){
        String[] kolumny={"Nr", "Nazwa", "Ikona"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("regiony", kolumny, null, null, null, null, null);
        return kursor;
    }

    /**
     * Odebranie wszystkich rekordów (butelek) z bazy danych z tabeli whisky, które
     * spełniają warunek (odpowiedni kraj).
     * @param kraj Warunek, który muszą spełnić rekordy
     * @return Odebranie butelek z bazy danych
     */
    public Cursor dajButelkiZKraju(String kraj){
        String[] kolumny={"Nr", "Nazwa", "Kraj", "Region", "Wiek", "Oceniony", "Ocena", "Info", "Ikona", "Strona"};
        SQLiteDatabase db = getReadableDatabase();
        String[] arg = {kraj};
        Cursor kursor = db.query("whisky", kolumny, "Kraj=?", arg, null, null, null);
        return kursor;
    }

    /**
     * Odebranie wszystkich rekordów (butelek) z bazy danych z tabeli whisky, które
     * spełniają warunek (odpowiedni region).
     * @param region Warunek, który muszą spełnić rekordy
     * @return Odebranie butelek z bazy danych
     */
    public Cursor dajButelkiZRegionu(String region){
        String[] kolumny={"Nr", "Nazwa", "Kraj", "Region", "Wiek", "Oceniony", "Ocena", "Info", "Ikona", "Strona"};
        SQLiteDatabase db = getReadableDatabase();
        String[] arg = {region};
        Cursor kursor = db.query("whisky", kolumny, "Region=?", arg, null, null, null);
        return kursor;
    }

    /**
     * Usuwanie rekordu (butelki) z bazy danych
     */
    public void kasujButelki(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("whisky", null, null);
    }

    /**
     * Aktualizacja rekordów (butelek) w bazie danych w tabeli whisky, które spełniają warunki (nazwa, kraj, wiek)
     * @param nazwa Nazwa butelki
     * @param wiek Wiek butelki
     * @param ocena Ocena użytkownika
     */
    public void aktualizujButelke(String nazwa, String wiek, double ocena){
        SQLiteDatabase db = getWritableDatabase();
        String strSQL = "UPDATE whisky SET Oceniony = 1, Ocena = " + ocena + " WHERE Nazwa = '" + nazwa + "' AND Wiek = '" + wiek + "'";
        db.execSQL(strSQL);
    }

    /**
     * Odebranie wszystkich rekordów (butelek) z bazy danych z tabeli whisky, które
     * spełniają warunek (kolumna oceniony musi być równa 1).
     * @return Odebranie butelek z bazy danych
     */
    public Cursor dajWszystkieOcenione(){
        String[] kolumny={"Nr", "Nazwa", "Kraj", "Region", "Wiek", "Oceniony", "Ocena", "Info", "Ikona", "Strona"};
        SQLiteDatabase db = getReadableDatabase();
        String[] arg = {1+""};
        Cursor kursor = db.query("whisky", kolumny, "Oceniony=?", arg, null, null, null);
        return kursor;
    }

    /**
     * Odebranie wszystkich rekordów (butelek) z bazy danych z tabeli whisky, które
     * spełniają warunek (nazwa i/lub wiek i/lub kraj).
     * @param nazwa Nazwa butelki
     * @param wiek Wiek butelki
     * @param kraj Kraj, z którego pochodzi butelka
     * @return Odebranie butelek z bazy danych
     */
    public Cursor dajWyszukaneButelki(String nazwa, String wiek, String kraj) {
        String[] kolumny = {"Nr", "Nazwa", "Kraj", "Region", "Wiek", "Oceniony", "Ocena", "Info", "Ikona", "Strona"};
        SQLiteDatabase db = getReadableDatabase();

        //Sprawdzenie, które warunki muszą być spełnione (według jakich kryteriów użytkownik poszukuje butelki)
        if(!nazwa.equals("") && wiek.equals("Wszystkie") && kraj.equals("Wszystkie")) {
            String[] argumenty = {nazwa};
            Cursor kursor = db.query("whisky", kolumny, "nazwa=?", argumenty, null, null, null);
            return kursor;
        } else if(!nazwa.equals("") && !wiek.equals("Wszystkie") && kraj.equals("Wszystkie")) {
            String[] argumenty = {nazwa, wiek};
            Cursor kursor = db.query("whisky", kolumny, "nazwa=? AND wiek=?", argumenty, null, null, null);
            return kursor;
        } else if(!nazwa.equals("") && !wiek.equals("Wszystkie") && !kraj.equals("Wszystkie")) {
            String[] argumenty = {nazwa, wiek, kraj};
            Cursor kursor = db.query("whisky", kolumny, "nazwa=? AND wiek=? AND kraj=?", argumenty, null, null, null);
            return kursor;
        } else if(!nazwa.equals("") && wiek.equals("Wszystkie") && !kraj.equals("Wszystkie")) {
            String[] argumenty = {nazwa, kraj};
            Cursor kursor = db.query("whisky", kolumny, "nazwa=? AND kraj=?", argumenty, null, null, null);
            return kursor;
        } else if(nazwa.equals("") && !wiek.equals("Wszystkie") && !kraj.equals("Wszystkie")) {
            String[] argumenty = {wiek, kraj};
            Cursor kursor = db.query("whisky", kolumny, "rocznik=? AND kraj=?", argumenty, null, null, null);
            return kursor;
        } else if(nazwa.equals("") && !wiek.equals("Wszystkie") && kraj.equals("Wszystkie")) {
            String[] argumenty = {wiek};
            Cursor kursor = db.query("whisky", kolumny, "wiek=?", argumenty, null, null, null);
            return kursor;
        } else {
            String[] argumenty = {kraj};
            Cursor kursor = db.query("whisky", kolumny, "kraj=?", argumenty, null, null, null);
            return kursor;
        }
    }
}
