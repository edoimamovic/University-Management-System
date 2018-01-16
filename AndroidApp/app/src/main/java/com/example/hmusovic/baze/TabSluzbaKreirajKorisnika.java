package com.example.hmusovic.baze;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class TabSluzbaKreirajKorisnika extends Fragment {
    EditText editIme, editPrezime, editJmbg, edituser, editpw;
    Spinner spinerUloga;
    Button dodajKorinika;

    SQLiteDatabase db;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_sluzba_kreiraj_korisnika, container, false);

        db = DatabaseClient.getInstance(getContext()).getDb();
        ArrayList<String> uloge = new ArrayList<>();

        spinerUloga = (Spinner) rootView.findViewById(R.id.planets_spinner_uloga);
        editIme = (EditText) rootView.findViewById(R.id.editIme);
        editPrezime = (EditText) rootView.findViewById(R.id.editPRezime);
        editJmbg = (EditText) rootView.findViewById(R.id.editJMBG);
        edituser = (EditText) rootView.findViewById(R.id.editUser);
        editpw = (EditText) rootView.findViewById(R.id.editPw);
        dodajKorinika = (Button) rootView.findViewById(R.id.buttonDodajKorisnika);

        // postavljanje uloga
        db = DatabaseClient.getInstance(getContext()).getDb();
        Cursor c = db.rawQuery("SELECT * FROM uloga", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("opis"));
            uloge.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, uloge);
        //set the spinners adapter to the previously created one.
        spinerUloga.setAdapter(adapter1);

        dodajKorinika.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM `korisnik`", null);
                Log.d("SQLITE", "Korisnici prije: " + String.valueOf(c.getCount()));
                c.close();

                String ime = editIme.getText().toString();
                String prezime = editPrezime.getText().toString();
                String jmbg = editJmbg.getText().toString();
                String user = edituser.getText().toString();
                String pass = editpw.getText().toString();
                int uloga = spinerUloga.getSelectedItemPosition() + 1;


                // ubacivanje u bazu
                db.execSQL("INSERT INTO `korisnik` (`ime`, `prezime`, `jmbg`, `username`, `password`, `ulogaId`) VALUES " +
                                "('" + ime + "', '" + prezime + "', '" +  jmbg + "', '" + user + "', '" + pass + "', " + uloga + ");");

                c = db.rawQuery("SELECT * FROM `korisnik`", null);
                Log.d("SQLITE", "INSERT Korisnik: " + String.valueOf(c.getCount()));
                c.close();

                editIme.setText("");
                editPrezime.setText("");
                editJmbg.setText("");
                edituser.setText("");
                editpw.setText("");
                spinerUloga.setSelection(0);
            }
        });


        return rootView;
    }
}
