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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class TabSluzbaKreirajKurs extends Fragment {
    Spinner spinnerPremet, spinnerOdsjek;
    EditText editPred, editTut, editLab, editEcts;
    Button dodajKurs;
    CheckBox izborni;
    SQLiteDatabase db;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_sluzba_kreiraj_kurs, container, false);

        spinnerPremet = (Spinner) rootView.findViewById(R.id.planets_spinner_predmeti);
        spinnerOdsjek = (Spinner) rootView.findViewById(R.id.planets_spinner_odsjek);
        editPred = (EditText) rootView.findViewById(R.id.editPred);
        editLab = (EditText) rootView.findViewById(R.id.editLab);
        editTut = (EditText) rootView.findViewById(R.id.editTut);
        editEcts = (EditText) rootView.findViewById(R.id.editECTS);
        dodajKurs = (Button) rootView.findViewById(R.id.buttonDodajKurs);
        izborni = (CheckBox) rootView.findViewById(R.id.checkBoxIzborni);


        ArrayList<String> predmeti = new ArrayList<>();
        ArrayList<String> odsjeci = new ArrayList<>();

        // postavljanje predmeta
        db = DatabaseClient.getInstance(getContext()).getDb();
        Cursor c = db.rawQuery("SELECT * FROM predmet", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("naziv"));
            predmeti.add(naziv);
            c.moveToNext();
        }
        c.close();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, predmeti);
        spinnerPremet.setAdapter(adapter);

        // postavljanje odsjeka
        db = DatabaseClient.getInstance(getContext()).getDb();
        c = db.rawQuery("SELECT * FROM odsjek", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("name"));
            odsjeci.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, odsjeci);
        //set the spinners adapter to the previously created one.
        spinnerOdsjek.setAdapter(adapter1);


        dodajKurs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM `kurs`", null);
                Log.d("SQLITE", "Kurs prije: " + String.valueOf(c.getCount()));
                c.close();

                int predmet = spinnerPremet.getSelectedItemPosition() + 1;
                int odsjek = spinnerOdsjek.getSelectedItemPosition() + 1;
                int pred = Integer.valueOf(editPred.getText().toString());
                int tut = Integer.valueOf(editTut.getText().toString());
                int lab = Integer.valueOf(editLab.getText().toString());
                float ects  = Float.valueOf(editEcts.getText().toString());
                int izb = 0;
                if (izborni.isChecked()) izb = 1;

                db.execSQL("INSERT INTO `kurs` (`Predmet_id`, `godina`, `Odsjek_id`, `izborni`, `brPredavanja`, `brTutorijala`, `brLabova`, `ects`) VALUES " +
                        "(" + predmet + ", 2017, " + odsjek + ", " + izb + ", " + pred + ", " + tut + ", " + lab + ", '" + ects  + "')");

                c = db.rawQuery("SELECT * FROM `kurs`", null);
                Log.d("SQLITE", "INSERT Kurs: " + String.valueOf(c.getCount()));
                c.close();

                editPred.setText("");
                editTut.setText("");
                editLab.setText("");
                editEcts.setText("");
                spinnerPremet.setSelection(0);
                spinnerOdsjek.setSelection(0);
                izborni.setChecked(false);
            }
        });

        return rootView;
    }


}
