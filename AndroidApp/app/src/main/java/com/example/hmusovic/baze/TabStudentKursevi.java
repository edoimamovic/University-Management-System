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

public class TabStudentKursevi extends Fragment {


    TableLayout tl;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    Integer korisnikID;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_student_kursevi, container, false);

        db = DatabaseClient.getInstance(getContext()).getDb();
        tl = (TableLayout) rootView.findViewById(R.id.displayLinear);
        sharedPreferences  = getActivity().getSharedPreferences("local1", Context.MODE_PRIVATE);
        korisnikID = sharedPreferences.getInt("loginID", -1);

        initTable();

        return rootView;
    }

    public void initTable() {

        Cursor kursCursor = db.rawQuery("SELECT p.naziv, k._id, k.Odsjek_id, k.izborni, k.brPredavanja, k.brTutorijala, k.brLabova, k.ects FROM nastava n, kurs k, predmet p  WHERE n.Student_id = ? AND n.Kurs_id = k._id AND p._id=k.Predmet_id", new String[]{ String.valueOf(korisnikID) });
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
            Integer izborni = kursCursor.getInt(kursCursor.getColumnIndex("izborni"));
            Integer brPredavanja = kursCursor.getInt(kursCursor.getColumnIndex("brPredavanja"));
            Integer brTutorijala = kursCursor.getInt(kursCursor.getColumnIndex("brTutorijala"));
            Integer brLabova = kursCursor.getInt(kursCursor.getColumnIndex("brLabova"));
            Float ects = kursCursor.getFloat(kursCursor.getColumnIndex("ects"));


            TextView textPredmet = new TextView(getContext());
            textPredmet.setText(Predmet_naziv);
            textPredmet.setHeight(100);
            textPredmet.setWidth(140);
            textPredmet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


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

            row.addView(textPredmet);
            row.addView(checkIzborni);
            row.addView(textPred);
            row.addView(textTut);
            row.addView(textLab);
            row.addView(textEcts);


            tl.addView(row, i+1);

            kursCursor.moveToNext();
        }
        kursCursor.close();
    }
}
