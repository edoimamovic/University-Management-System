package com.example.hmusovic.baze;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TabProfesorIspiti extends Fragment {

    TableLayout tl;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    Integer korisnikID;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_profesor_ispiti, container, false);
        db = DatabaseClient.getInstance(getContext()).getDb();
        tl = (TableLayout) rootView.findViewById(R.id.displayLinear1);
        sharedPreferences  = getActivity().getSharedPreferences("local1", Context.MODE_PRIVATE);
        korisnikID = sharedPreferences.getInt("loginID", -1);

        initTable();

        return rootView;
    }

    public void initTable() {

        Cursor kursCursor = db.rawQuery("SELECT p.naziv, i._id, i.rokzaprijave, i.vrijemeodrzavanja, i.Sala_id, i.tip FROM kurs k, predmet p, ispitnirok i WHERE p.Uposlenik_id = ? AND p._id=k.Predmet_id AND k._id =i.Kurs_id AND k.godina = 2017 AND date('now')<i.vrijemeodrzavanja", new String[]{ String.valueOf(korisnikID) });
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
            String rokPrijave = kursCursor.getString(kursCursor.getColumnIndex("rokzaprijave"));
            String vrijemeodrzavanja = kursCursor.getString(kursCursor.getColumnIndex("vrijemeodrzavanja"));
            Integer sala_id = kursCursor.getInt(kursCursor.getColumnIndex("Sala_id"));
            Integer tip = kursCursor.getInt(kursCursor.getColumnIndex("tip"));

            String Sala_naziv = "";
            String tipIspita = "";

            Cursor salaCursor = db.rawQuery("SELECT naziv FROM sala WHERE _id = ?", new String[] {String.valueOf(sala_id)});
            assert salaCursor != null;
            salaCursor.moveToFirst();

            if(salaCursor.getCount() > 0) {
                Sala_naziv = salaCursor.getString(0);
            }
            salaCursor.close();

            Cursor tipCursor = db.rawQuery("SELECT opis FROM tipispita WHERE _id = ?", new String[] {String.valueOf(tip)});
            assert tipCursor != null;
            tipCursor.moveToFirst();

            if(tipCursor.getCount() > 0) {
                tipIspita = tipCursor.getString(0);
            }
            tipCursor.close();

            String datumRok[] = rokPrijave.substring(0,10).split("-");
            String vrijemeRok = rokPrijave.substring(11,16);

            String datumOdrz[] = vrijemeodrzavanja.substring(0,10).split("-");
            String vrijemeOdrz = vrijemeodrzavanja.substring(11,16);



            TextView textPredmet = new TextView(getContext());
            textPredmet.setText(Predmet_naziv);
            textPredmet.setHeight(100);
            textPredmet.setWidth(140);
            textPredmet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textRokZaPrijavu = new TextView(getContext());
            textRokZaPrijavu.setText(datumRok[2] + "." + datumRok[1] + "." + datumRok[0] + " " + vrijemeRok);
            textRokZaPrijavu.setHeight(100);
            textRokZaPrijavu.setWidth(45);
            textRokZaPrijavu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textVrijemeOdrz = new TextView(getContext());
            textVrijemeOdrz.setText(datumOdrz[2] + "." + datumOdrz[1] + "." + datumOdrz[0] + " " + vrijemeOdrz);
            textVrijemeOdrz.setHeight(100);
            textVrijemeOdrz.setWidth(45);
            textVrijemeOdrz.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);


            TextView textSala = new TextView(getContext());
            textSala.setText(Sala_naziv);
            textSala.setWidth(35);
            textSala.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView textTip = new TextView(getContext());
            textTip.setText(tipIspita);
            textTip.setHeight(100);
            textTip.setWidth(55);
            textTip.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            Button buttonIzmjeni = new Button(getContext());
            buttonIzmjeni.setText("Azuriraj");
            buttonIzmjeni.setId(id);

            buttonIzmjeni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer id = v.getId();

                    Intent i = new Intent(getContext(), IspitAzuriranjeActivity.class);
                    i.putExtra("ispitID", id);

                    startActivity(i);
                }
            });


            row.addView(textPredmet);
            row.addView(textRokZaPrijavu);
            row.addView(textVrijemeOdrz);
            row.addView(textSala);
            row.addView(textTip);
            row.addView(buttonIzmjeni);

            tl.addView(row, i+1);

            kursCursor.moveToNext();

        }
        kursCursor.close();
    }
}
