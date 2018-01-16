package com.example.hmusovic.baze;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class IspitAzuriranjeActivity extends AppCompatActivity {
    EditText editDatumRok, editVrijemeRok, editDatumIspit, editVrijemeIspit;
    Spinner spinnerKurs, spinnerRok, spinnerSala;
    Button azurirajIspit, obrisiIspit;

    SQLiteDatabase db;
    Calendar myCalendar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ispit_azuriranje);

        Intent intent = getIntent();
        final Integer ispitID = intent.getIntExtra("ispitID", -1);

        db = DatabaseClient.getInstance(this).getDb();
        ArrayList<String> kursevi = new ArrayList<>();
        ArrayList<String> sale = new ArrayList<>();
        ArrayList<String> rokovi = new ArrayList<>();

        spinnerKurs = (Spinner) findViewById(R.id.i_planets_spinner_kurs);
        spinnerRok = (Spinner) findViewById(R.id.i_planets_spinner_tipIspita);
        spinnerSala = (Spinner) findViewById(R.id.i_planets_spinner_sala);

        editDatumRok = (EditText) findViewById(R.id.i_datumRok);
        editVrijemeRok = (EditText) findViewById(R.id.i_vrijemeRok);
        editDatumIspit = (EditText) findViewById(R.id.i_datumIspit);
        editVrijemeIspit = (EditText) findViewById(R.id.i_vrijemeIspit);

        azurirajIspit = (Button) findViewById(R.id.i_buttonAzurirajIspit);
        obrisiIspit = (Button) findViewById(R.id.i_buttonObrisiIspit);

        myCalendar = Calendar.getInstance();

        Cursor ispitCursor = db.rawQuery("SELECT * FROM `ispitnirok` WHERE _id = ?", new String[] { String.valueOf(ispitID) });
        assert ispitCursor != null;
        ispitCursor.moveToFirst();

        String rokPrijave = ispitCursor.getString(ispitCursor.getColumnIndex("rokzaprijave"));
        String vrijemeodrzavanja = ispitCursor.getString(ispitCursor.getColumnIndex("vrijemeodrzavanja"));
        Integer sala_id = ispitCursor.getInt(ispitCursor.getColumnIndex("Sala_id"));
        Integer kurs_id = ispitCursor.getInt(ispitCursor.getColumnIndex("Kurs_id"));
        Integer tip = ispitCursor.getInt(ispitCursor.getColumnIndex("tip"));

        ispitCursor.close();

        // postavljanje kurseva
        Cursor c = db.rawQuery("SELECT * FROM predmet", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("naziv"));
            kursevi.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kursevi);
        //set the spinners adapter to the previously created one.
        spinnerKurs.setAdapter(adapter1);

        // postavljanje sala

        c = db.rawQuery("SELECT * FROM sala", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("naziv"));
            sale.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sale);
        //set the spinners adapter to the previously created one.
        spinnerSala.setAdapter(adapter2);

        // postavljanje rokova
        c = db.rawQuery("SELECT * FROM tipispita", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("opis"));
            rokovi.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rokovi);
        //set the spinners adapter to the previously created one.
        spinnerRok.setAdapter(adapter3);


        spinnerKurs.setSelection(kurs_id-1);
        spinnerRok.setSelection(tip-1);
        spinnerSala.setSelection(sala_id-1);

        editDatumRok.setText(rokPrijave.substring(0,10));
        editVrijemeRok.setText(rokPrijave.substring(11,16));

        editDatumIspit.setText(vrijemeodrzavanja.substring(0,10));
        editVrijemeIspit.setText(vrijemeodrzavanja.substring(11, 16));

        azurirajIspit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int kurs = spinnerKurs.getSelectedItemPosition() + 1;
                int sala = spinnerSala.getSelectedItemPosition() + 1;
                int rok = spinnerRok.getSelectedItemPosition() + 1;
                String datumRok = editDatumRok.getText().toString();
                String vrijemeRok = editVrijemeRok.getText().toString();
                String datumIspit = editDatumIspit.getText().toString();
                String vrijemeIspit = editVrijemeIspit.getText().toString();


                db.execSQL("UPDATE `ispitnirok` SET " +
                        "`rokZaPrijave`='" + datumRok + " " + vrijemeRok +
                        "', `vrijemeOdrzavanja`='" + datumIspit + " " + vrijemeIspit +
                        "', `Sala_id`=" + sala +
                        ", `Kurs_id`=" + kurs +
                        ", `tip`=" + rok + "" +
                        " WHERE _id=" + ispitID + ";");

                editDatumRok.setText("");
                editVrijemeRok.setText("");
                editDatumIspit.setText("");
                editVrijemeIspit.setText("");
                spinnerRok.setSelection(0);
                spinnerSala.setSelection(0);
                spinnerKurs.setSelection(0);

                Intent i = new Intent(v.getContext(), MainActivityProfesor.class);
                startActivity(i);
            }
        });

        obrisiIspit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.execSQL("DELETE FROM `ispitnirok` WHERE _id=" + ispitID + ";");

                Intent i = new Intent(v.getContext(), MainActivityProfesor.class);
                startActivity(i);
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelRok();
            }

        };

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelIspit();
            }
        };

        editDatumRok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editDatumIspit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(v.getContext(), date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        editVrijemeRok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour = "0";
                        String min = "0";
                        if(selectedHour < 10) hour = "0" + selectedHour;
                        else hour = "" + selectedHour;

                        if(selectedMinute < 10) min = "0" + selectedMinute;
                        else min = "" + selectedMinute;

                        editVrijemeRok.setText( hour + ":" + min + ":00");
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        editVrijemeIspit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour, min;

                        if(selectedHour < 10) hour = "0" + selectedHour;
                        else hour = "" + selectedHour;

                        if(selectedMinute < 10) min = "0" + selectedMinute;
                        else min = "" + selectedMinute;

                        editVrijemeIspit.setText( hour + ":" + min + ":00");
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }

    private void updateLabelRok() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDatumRok.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelIspit() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDatumIspit.setText(sdf.format(myCalendar.getTime()));
    }
}
