package com.example.hmusovic.baze;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TabSluzbaKursevi extends Fragment {

    TableLayout tl;
    SQLiteDatabase db;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_sluzba_kursevi, container, false);

        db = DatabaseClient.getInstance(getContext()).getDb();
        tl = (TableLayout) rootView.findViewById(R.id.displayLinear);

        initTable();

        return rootView;
    }

    public void initTable() {

        Cursor kursCursor = db.rawQuery("SELECT * FROM `kurs`", null);
        assert kursCursor != null;
        kursCursor.moveToFirst();

        for (int i = 0; i < kursCursor.getCount(); i++) {
            TableRow row = new TableRow(getContext());
            row.setMinimumHeight(50);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            lp.height = 100;
            row.setLayoutParams(lp);

            Integer id = kursCursor.getInt(kursCursor.getColumnIndex("_id"));
            Integer Predmet_id = kursCursor.getInt(kursCursor.getColumnIndex("Predmet_id"));
            Integer Odsjek_id = kursCursor.getInt(kursCursor.getColumnIndex("Odsjek_id"));
            Integer izborni = kursCursor.getInt(kursCursor.getColumnIndex("izborni"));
            Integer brPredavanja = kursCursor.getInt(kursCursor.getColumnIndex("brPredavanja"));
            Integer brTutorijala = kursCursor.getInt(kursCursor.getColumnIndex("brTutorijala"));
            Integer brLabova = kursCursor.getInt(kursCursor.getColumnIndex("brLabova"));
            Float ects = kursCursor.getFloat(kursCursor.getColumnIndex("ects"));

            String Predmet_naziv = "";
            String Odsjek_naziv = "";

            Cursor predmetCursor = db.rawQuery("SELECT naziv FROM predmet WHERE _id = ?", new String[] {String.valueOf(Predmet_id)});
            assert predmetCursor != null;
            predmetCursor.moveToFirst();

            if(predmetCursor.getCount() > 0) {
                Predmet_naziv = predmetCursor.getString(0);
            }
            predmetCursor.close();

            Cursor odsjekCursor = db.rawQuery("SELECT name FROM odsjek WHERE _id = ?", new String[] {String.valueOf(Odsjek_id)});
            assert odsjekCursor != null;
            odsjekCursor.moveToFirst();

            if(odsjekCursor.getCount() > 0) {
                Odsjek_naziv = odsjekCursor.getString(0);
            }
            odsjekCursor.close();

            TextView textPredmet = new TextView(getContext());
            textPredmet.setText(Predmet_naziv);
            textPredmet.setHeight(90);
            textPredmet.setWidth(100);
            textPredmet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textOdsjek = new TextView(getContext());
            textOdsjek.setText(Odsjek_naziv);
            textOdsjek.setHeight(90);
            textOdsjek.setWidth(45);
            textOdsjek.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            CheckBox checkIzborni = new CheckBox(getContext());
            checkIzborni.setEnabled(false);
            if(izborni.equals(0))
                checkIzborni.setChecked(false);
            else
                checkIzborni.setChecked(true);

            TextView textPred = new TextView(getContext());
            textPred.setText(String.valueOf(brPredavanja));
            textPred.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textTut = new TextView(getContext());
            textTut.setText(String.valueOf(brTutorijala));
            textTut.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textLab = new TextView(getContext());
            textLab.setText(String.valueOf(brLabova));
            textLab.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textEcts = new TextView(getContext());
            textEcts.setText(String.valueOf(ects));
            textEcts.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            Button buttonIzmjeni = new Button(getContext());
            buttonIzmjeni.setText("Azuriraj");
            buttonIzmjeni.setId(i);

            buttonIzmjeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer id = v.getId() + 1;

                    Intent i = new Intent(getContext(), KursAzuriranjeActivity.class);
                    i.putExtra("kursID", id);

                    startActivity(i);
                }
            });

            row.addView(textPredmet);
            row.addView(textOdsjek);
            row.addView(checkIzborni);
            row.addView(textPred);
            row.addView(textTut);
            row.addView(textLab);
            row.addView(textEcts);
            row.addView(buttonIzmjeni);

            tl.addView(row, i+1);

            kursCursor.moveToNext();
        }
        kursCursor.close();
    }
}

