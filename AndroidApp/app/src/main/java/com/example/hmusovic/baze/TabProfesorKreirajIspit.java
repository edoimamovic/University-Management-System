package com.example.hmusovic.baze;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TabProfesorKreirajIspit extends Fragment {
    EditText editDatumRok, editVrijemeRok, editDatumIspit, editVrijemeIspit;
    Spinner spinnerKurs, spinnerRok, spinnerSala;
    Button dodajIspit;

    SQLiteDatabase db;
    Calendar myCalendar;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_profesor_kreiraj_ispit, container, false);

        db = DatabaseClient.getInstance(getContext()).getDb();
        ArrayList<String> kursevi = new ArrayList<>();
        ArrayList<String> sale = new ArrayList<>();
        ArrayList<String> rokovi = new ArrayList<>();

        spinnerKurs = (Spinner) rootView.findViewById(R.id.planets_spinner_kurs);
        spinnerRok = (Spinner) rootView.findViewById(R.id.planets_spinner_tipIspita);
        spinnerSala = (Spinner) rootView.findViewById(R.id.planets_spinner_sala);

        editDatumRok = (EditText) rootView.findViewById(R.id.datumRok);
        editVrijemeRok = (EditText) rootView.findViewById(R.id.vrijemeRok);
        editDatumIspit = (EditText) rootView.findViewById(R.id.datumIspit);
        editVrijemeIspit = (EditText) rootView.findViewById(R.id.vrijemeIspit);

        dodajIspit = (Button) rootView.findViewById(R.id.buttonDodajIspit);

        myCalendar = Calendar.getInstance();

        // postavljanje kurseva
        db = DatabaseClient.getInstance(getContext()).getDb();
        Cursor c = db.rawQuery("SELECT * FROM predmet", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("naziv"));
            kursevi.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, kursevi);
        //set the spinners adapter to the previously created one.
        spinnerKurs.setAdapter(adapter1);

        // postavljanje sala
        db = DatabaseClient.getInstance(getContext()).getDb();
        c = db.rawQuery("SELECT * FROM sala", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("naziv"));
            sale.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, sale);
        //set the spinners adapter to the previously created one.
        spinnerSala.setAdapter(adapter2);

        // postavljanje rokova
        db = DatabaseClient.getInstance(getContext()).getDb();
        c = db.rawQuery("SELECT * FROM tipispita", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("opis"));
            rokovi.add(naziv);
            c.moveToNext();
        }
        c.close();

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, rokovi);
        //set the spinners adapter to the previously created one.
        spinnerRok.setAdapter(adapter3);

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
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editDatumIspit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date1, myCalendar
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
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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

        dodajIspit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor c = db.rawQuery("SELECT * FROM `ispitnirok`", null);
                Log.d("SQLITE", "Ispiti prije: " + String.valueOf(c.getCount()));
                c.close();

                int kurs = spinnerKurs.getSelectedItemPosition() + 1;
                int sala = spinnerSala.getSelectedItemPosition() + 1;
                int rok = spinnerRok.getSelectedItemPosition() + 1;
                String datumRok = editDatumRok.getText().toString();
                String vrijemeRok = editVrijemeRok.getText().toString();
                String datumIspit = editDatumIspit.getText().toString();
                String vrijemeIspit = editDatumIspit.getText().toString();


                db.execSQL("INSERT INTO `ispitnirok` (`rokZaPrijave`, `vrijemeOdrzavanja`, `Sala_id`, `Kurs_id`, `tip`) VALUES " +
                                "('" + datumRok + " " + vrijemeRok + "', '" + datumIspit + " " + vrijemeIspit + "', " + sala + ", " + kurs + ", " + rok + ");");


                c = db.rawQuery("SELECT * FROM `ispitnirok`", null);
                Log.d("SQLITE", "INSERT ispit: " + String.valueOf(c.getCount()));
                c.close();

                editDatumRok.setText("");
                editVrijemeRok.setText("");
                editDatumIspit.setText("");
                editVrijemeIspit.setText("");
                spinnerRok.setSelection(0);
                spinnerSala.setSelection(0);
                spinnerKurs.setSelection(0);
            }
        });


        return rootView;
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
