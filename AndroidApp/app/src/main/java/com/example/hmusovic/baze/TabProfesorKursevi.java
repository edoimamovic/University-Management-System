package com.example.hmusovic.baze;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TabProfesorKursevi extends Fragment {

    TableLayout tl;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    Integer korisnikID;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_profesor_kursevi, container, false);

        db = DatabaseClient.getInstance(getContext()).getDb();
        tl = (TableLayout) rootView.findViewById(R.id.displayLinear);
        sharedPreferences  = getActivity().getSharedPreferences("local1", Context.MODE_PRIVATE);
        korisnikID = sharedPreferences.getInt("loginID", -1);

        initTable();

        return rootView;
    }

    public void initTable() {

        Cursor kursCursor = db.rawQuery("SELECT p.naziv, k._id, k.Odsjek_id, k.izborni, k.brPredavanja, k.brTutorijala, k.brLabova, k.ects FROM kurs k, predmet p WHERE p.Uposlenik_id = ? AND p._id=k.Predmet_id", new String[]{ String.valueOf(korisnikID) });
        assert kursCursor != null;
        kursCursor.moveToFirst();

        for (int i = 0; i < kursCursor.getCount(); i++) {
            TableRow row = new TableRow(getContext());
            row.setMinimumHeight(50);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            lp.height = 100;
            row.setLayoutParams(lp);

            Integer id = kursCursor.getInt(kursCursor.getColumnIndex("_id"));
            String Predmet_naziv = kursCursor.getString(kursCursor.getColumnIndex("naziv"));
            Integer Odsjek_id = kursCursor.getInt(kursCursor.getColumnIndex("Odsjek_id"));
            Integer izborni = kursCursor.getInt(kursCursor.getColumnIndex("izborni"));
            Integer brPredavanja = kursCursor.getInt(kursCursor.getColumnIndex("brPredavanja"));
            Integer brTutorijala = kursCursor.getInt(kursCursor.getColumnIndex("brTutorijala"));
            Integer brLabova = kursCursor.getInt(kursCursor.getColumnIndex("brLabova"));
            Float ects = kursCursor.getFloat(kursCursor.getColumnIndex("ects"));

            String Odsjek_naziv = "";
            Integer brojStudenata = 0;

            Cursor odsjekCursor = db.rawQuery("SELECT name FROM odsjek WHERE _id = ?", new String[] {String.valueOf(Odsjek_id)});
            assert odsjekCursor != null;
            odsjekCursor.moveToFirst();

            if(odsjekCursor.getCount() > 0) {
                Odsjek_naziv = odsjekCursor.getString(0);
            }
            odsjekCursor.close();

            Cursor studentiCursor = db.rawQuery("SELECT COUNT(*) FROM nastava WHERE Kurs_id = ?", new String[] {String.valueOf(id)});
            assert studentiCursor != null;
            studentiCursor.moveToFirst();

            if(studentiCursor.getCount() > 0) {
                brojStudenata = studentiCursor.getInt(0);
            }
            studentiCursor.close();

            TextView textPredmet = new TextView(getContext());
            textPredmet.setText(Predmet_naziv);
            textPredmet.setHeight(100);
            textPredmet.setWidth(140);
            textPredmet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textOdsjek = new TextView(getContext());
            textOdsjek.setText(Odsjek_naziv);
            textOdsjek.setHeight(100);
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

            TextView textBroj = new TextView(getContext());
            textBroj.setText("" + brojStudenata);
            textBroj.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(textPredmet);
            row.addView(textOdsjek);
            row.addView(checkIzborni);
            row.addView(textPred);
            row.addView(textTut);
            row.addView(textLab);
            row.addView(textEcts);
            row.addView(textBroj);


            tl.addView(row, i+1);

            kursCursor.moveToNext();
        }
        kursCursor.close();
    }
}
