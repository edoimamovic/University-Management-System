package com.example.hmusovic.baze;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


public class TabStudentPotvrde extends Fragment {

    Spinner spinnerTipPotvrde;
    Button podnesi;

    TableLayout tl;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    Integer korisnikID;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_student_potvrde, container, false);

        spinnerTipPotvrde = (Spinner) rootView.findViewById(R.id.planets_spinner_tipPotvrde);
        podnesi = (Button) rootView.findViewById(R.id.buttonPodnesiZahtjev);

        db = DatabaseClient.getInstance(getContext()).getDb();
        tl = (TableLayout) rootView.findViewById(R.id.displayLinear3);
        sharedPreferences  = getActivity().getSharedPreferences("local1", Context.MODE_PRIVATE);
        korisnikID = sharedPreferences.getInt("loginID", -1);

        ArrayList<String> tipoviPotvde = new ArrayList<>();

        // postavljanje predmeta
        db = DatabaseClient.getInstance(getContext()).getDb();
        Cursor c = db.rawQuery("SELECT * FROM tippotvrde", null);
        assert c != null;
        c.moveToFirst();

        for(int i=0; i<c.getCount(); i++) {
            String naziv = c.getString(c.getColumnIndex("naziv"));
            tipoviPotvde.add(naziv);
            c.moveToNext();
        }
        c.close();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, tipoviPotvde);
        spinnerTipPotvrde.setAdapter(adapter);

        podnesi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int selektovaniID = spinnerTipPotvrde.getSelectedItemPosition() + 1;

                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                Calendar myCalendar = Calendar.getInstance();
                String datumPrijave = sdf.format(myCalendar.getTime());

                // 0 - odbijen
                // 1 na cekanju
                // 2 prihvacen

                Random r = new Random();
                int random = r.nextInt(3); // 0-2 a treba stavit na 1
                db.execSQL("INSERT INTO 'zahtjevzapotvrdu' ('datumZahtjeva', 'status', tipPotvrde_id, Student_id) VALUES " +
                        "('" + datumPrijave + "', " +  random + ", " + selektovaniID + ", " + korisnikID + ");");

                spinnerTipPotvrde.setSelection(0);

                // pozovi refresh tabela
                initTablePrijave();
            }
        });

        initTablePrijave();
        return rootView;

    }


    public void initTablePrijave() {
        ocistiTabele();

        Cursor kursCursor = db.rawQuery("SELECT zzp.datumzahtjeva, zzp.status, tp.naziv FROM zahtjevzapotvrdu zzp, tippotvrde tp WHERE zzp.Student_id = ? AND zzp.tippotvrde_id = tp._id ", new String[] { korisnikID.toString() });

        assert kursCursor != null;

        kursCursor.moveToFirst();

        if(kursCursor.getCount() > 0) {
            for (int i = 0; i < kursCursor.getCount(); i++) {
                TableRow row = new TableRow(getContext());
                row.setMinimumHeight(50);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                lp.height = 100;
                row.setLayoutParams(lp);

                String datumZahtjeva = kursCursor.getString(0);
                Integer status = kursCursor.getInt(1);
                String nazivPotvrde = kursCursor.getString(2);

                String statusString = "";

                if(status == 0) statusString = "Odbijen";
                else if (status == 1) statusString = "Na cekanju";
                else statusString = "Obradjen";

                String myFormat1 = "yyyy-MM-dd";
                String myFormat2 = "dd.MM.yyyy"; //In which you need put here
                SimpleDateFormat sdf1 = new SimpleDateFormat(myFormat1, Locale.US);
                SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2, Locale.US);
                String datumNakon = "";
                try {
                    datumNakon = sdf2.format(sdf1.parse(datumZahtjeva));
                } catch (ParseException e) {

                }

                TextView textNaziv = new TextView(getContext());
                textNaziv.setText(nazivPotvrde);
                textNaziv.setHeight(100);
                textNaziv.setWidth(165);
                textNaziv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                TextView textRokZaPrijavu = new TextView(getContext());
                textRokZaPrijavu.setText(datumNakon);
                textRokZaPrijavu.setHeight(100);
                textRokZaPrijavu.setWidth(100);
                textRokZaPrijavu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                TextView textVrijemeOdrz = new TextView(getContext());
                textVrijemeOdrz.setText(statusString);
                textVrijemeOdrz.setHeight(100);
                textVrijemeOdrz.setWidth(120);
                textVrijemeOdrz.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                if(status == 0) textVrijemeOdrz.setTextColor(Color.RED);
                if(status == 1) textVrijemeOdrz.setTextColor(Color.GRAY);
                if(status == 2) textVrijemeOdrz.setTextColor(Color.GREEN);

                row.addView(textNaziv);
                row.addView(textRokZaPrijavu);
                row.addView(textVrijemeOdrz);

                tl.addView(row, i + 1);

                kursCursor.moveToNext();

            }
            kursCursor.close();
        }
        else {
            TableRow row = new TableRow(getContext());
            row.setMinimumHeight(50);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            lp.height = 100;
            row.setLayoutParams(lp);

            TextView textPredmet = new TextView(getContext());
            textPredmet.setText("Nemate aktivnih zahtjeva za potvrde");
            textPredmet.setTextSize(15f);
            textPredmet.setHeight(100);
            textPredmet.setWidth(250);
            textPredmet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(textPredmet);
            tl.addView(row, 1);

        }
    }

    private void ocistiTabele() {
        int brojPrijava = tl.getChildCount();

        int i = 1;
        while (i < brojPrijava) {
            tl.removeViewAt(1);
            i++;
        }

    }


}
