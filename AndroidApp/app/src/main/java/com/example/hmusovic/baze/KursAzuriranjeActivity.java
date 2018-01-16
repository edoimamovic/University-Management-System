package com.example.hmusovic.baze;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class KursAzuriranjeActivity extends AppCompatActivity {
    Spinner spinnerPremet, spinnerOdsjek;
    EditText editPred, editTut, editLab, editEcts;
    Button azurirajKurs, obrisiKurs;
    CheckBox izborni;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurs_azuriranje);

        Intent intent = getIntent();
        final Integer kursID = intent.getIntExtra("kursID", -1);
        
        db = DatabaseClient.getInstance(this).getDb();

        spinnerPremet = (Spinner) findViewById(R.id.u_planets_spinner_predmeti);
        spinnerOdsjek = (Spinner) findViewById(R.id.u_planets_spinner_odsjek);
        editPred = (EditText) findViewById(R.id.u_editPred);
        editLab = (EditText) findViewById(R.id.u_editLab);
        editTut = (EditText) findViewById(R.id.u_editTut);
        editEcts = (EditText) findViewById(R.id.u_editECTS);
        azurirajKurs = (Button) findViewById(R.id.u_buttonAzurirajKurs);
        obrisiKurs = (Button) findViewById(R.id.u_buttonObrisiKurs);
        izborni = (CheckBox) findViewById(R.id.u_checkBoxIzborni);

        Cursor kursCursor = db.rawQuery("SELECT * FROM `kurs` WHERE _id = ?", new String[] { String.valueOf(kursID) });
        assert kursCursor != null;
        kursCursor.moveToFirst();

        Integer id = kursCursor.getInt(kursCursor.getColumnIndex("_id"));
        Integer Predmet_id = kursCursor.getInt(kursCursor.getColumnIndex("Predmet_id"));
        Integer Odsjek_id = kursCursor.getInt(kursCursor.getColumnIndex("Odsjek_id"));
        Integer izborniPr = kursCursor.getInt(kursCursor.getColumnIndex("izborni"));
        Integer brPredavanja = kursCursor.getInt(kursCursor.getColumnIndex("brPredavanja"));
        Integer brTutorijala = kursCursor.getInt(kursCursor.getColumnIndex("brTutorijala"));
        Integer brLabova = kursCursor.getInt(kursCursor.getColumnIndex("brLabova"));
        Float ects = kursCursor.getFloat(kursCursor.getColumnIndex("ects"));
        kursCursor.close();

        ArrayList<String> predmeti = new ArrayList<>();
        ArrayList<String> odsjeci = new ArrayList<>();

        // postavljanje predmeta
        db = DatabaseClient.getInstance(this).getDb();
        Cursor c = db.rawQuery("SELECT * FROM predmet", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("naziv"));
            predmeti.add(naziv);
            c.moveToNext();
        }
        c.close();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, predmeti);
        spinnerPremet.setAdapter(adapter);

        // postavljanje odsjeka
        c = db.rawQuery("SELECT * FROM odsjek", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("name"));
            odsjeci.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, odsjeci);
        //set the spinners adapter to the previously created one.
        spinnerOdsjek.setAdapter(adapter1);


        spinnerPremet.setSelection(Predmet_id-1);
        spinnerOdsjek.setSelection(Odsjek_id-1);
        editPred.setText("" + brPredavanja);
        editTut.setText("" + brTutorijala);
        editLab.setText("" + brLabova);
        editEcts.setText(String.valueOf(ects));

        if(izborniPr.equals(0))
            izborni.setSelected(false);
        else
            izborni.setSelected(true);

        azurirajKurs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            int predmet = spinnerPremet.getSelectedItemPosition() + 1;
            int odsjek = spinnerOdsjek.getSelectedItemPosition() + 1;
            int pred = Integer.valueOf(editPred.getText().toString());
            int tut = Integer.valueOf(editTut.getText().toString());
            int lab = Integer.valueOf(editLab.getText().toString());
            float ects  = Float.valueOf(editEcts.getText().toString());
            int izb = 0;
            if (izborni.isChecked()) izb = 1;

            db.execSQL("UPDATE `kurs` SET " +
                    "`Predmet_id`=" + predmet +
                    ", `Odsjek_id`=" + odsjek +
                    ", `izborni`=" + izb +
                    ", `brPredavanja`=" + pred +
                    ", `brTutorijala`=" + tut +
                    ", `brLabova`=" + lab +
                    ", `ects`='" + ects
                    + "' WHERE _id=" + kursID + ";");


            editPred.setText("");
            editTut.setText("");
            editLab.setText("");
            editEcts.setText("");
            spinnerPremet.setSelection(0);
            spinnerOdsjek.setSelection(0);
            izborni.setChecked(false);

            Intent i = new Intent(v.getContext(), MainActivitySluzba.class);
            startActivity(i);
            }
        });

        obrisiKurs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.execSQL("DELETE FROM `kurs` WHERE _id=" + kursID + ";");

                Intent i = new Intent(v.getContext(), MainActivitySluzba.class);
                startActivity(i);
            }
        });



    }
}
