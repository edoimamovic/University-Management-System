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
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TabStudentIspiti extends Fragment {

    TableLayout tl, tl2;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    Integer korisnikID;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_student_ispiti, container, false);

        db = DatabaseClient.getInstance(getContext()).getDb();
        tl = (TableLayout) rootView.findViewById(R.id.displayLinear1);
        tl2 = (TableLayout) rootView.findViewById(R.id.displayLinear2);

        sharedPreferences  = getActivity().getSharedPreferences("local1", Context.MODE_PRIVATE);
        korisnikID = sharedPreferences.getInt("loginID", -1);

        initTablePrijave();

        return rootView;
    }

    public void initTablePrijave() {
        ocistiTabele();

        Cursor kursCursor = db.rawQuery("SELECT p.naziv, i._id, i.rokzaprijave, i.vrijemeodrzavanja, i.Sala_id, i.tip FROM nastava n, prijavaispit pi, kurs k, predmet p, ispitnirok i WHERE n.Student_id = ? AND n._id=pi.Nastava_id AND pi.ispitnirok_id = i._id AND n.Kurs_id = k._id AND k.Predmet_id = p._id AND k.godina = 2017 AND date('now')<i.vrijemeodrzavanja", new String[]{ String.valueOf(korisnikID) });

        Map<Integer, Integer> mapaPrijavljeni = new HashMap<>();
        final Map<Integer, Integer> mapaOtvoreni = new HashMap<>();
        assert kursCursor != null;

        kursCursor.moveToFirst();

        if(kursCursor.getCount() > 0) {
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

                Cursor salaCursor = db.rawQuery("SELECT naziv FROM sala WHERE _id = ?", new String[]{String.valueOf(sala_id)});
                assert salaCursor != null;
                salaCursor.moveToFirst();

                if (salaCursor.getCount() > 0) {
                    Sala_naziv = salaCursor.getString(0);
                }
                salaCursor.close();

                Cursor tipCursor = db.rawQuery("SELECT opis FROM tipispita WHERE _id = ?", new String[]{String.valueOf(tip)});
                assert tipCursor != null;
                tipCursor.moveToFirst();

                if (tipCursor.getCount() > 0) {
                    tipIspita = tipCursor.getString(0);
                }
                tipCursor.close();

                String datumRok[] = rokPrijave.substring(0, 10).split("-");
                String vrijemeRok = rokPrijave.substring(11, 16);

                String datumOdrz[] = vrijemeodrzavanja.substring(0, 10).split("-");
                String vrijemeOdrz = vrijemeodrzavanja.substring(11, 16);


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

                row.addView(textPredmet);
                row.addView(textRokZaPrijavu);
                row.addView(textVrijemeOdrz);
                row.addView(textSala);
                row.addView(textTip);

                tl.addView(row, i + 1);

                mapaPrijavljeni.put(id, tip);

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
            textPredmet.setText("Nemate prijavljenih ispita");
            textPredmet.setTextSize(15f);
            textPredmet.setHeight(100);
            textPredmet.setWidth(160);
            textPredmet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(textPredmet);
            tl.addView(row, 1);

        }

        Cursor otvoreniCursor = db.rawQuery("SELECT p.naziv, n._id, i._id, i.rokzaprijave, i.vrijemeodrzavanja, i.Sala_id, i.tip FROM nastava n, kurs k, predmet p, ispitnirok i WHERE n.Student_id = ? AND n.Kurs_id=k._id AND k._id=i.Kurs_id AND k.Predmet_id = p._id AND k.godina = 2017 AND date('now')<i.vrijemeodrzavanja", new String[]{ String.valueOf(korisnikID) });

        assert otvoreniCursor != null;
        otvoreniCursor.moveToFirst();

        if(otvoreniCursor.getCount() > 0) {
            for (int i = 0; i < otvoreniCursor.getCount(); i++) {
                TableRow row = new TableRow(getContext());
                row.setMinimumHeight(50);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                lp.height = 100;
                row.setLayoutParams(lp);

                String Predmet_naziv = otvoreniCursor.getString(0);
                Integer nastavaID = otvoreniCursor.getInt(1);
                Integer id = otvoreniCursor.getInt(2);
                String rokPrijave = otvoreniCursor.getString(3);
                String vrijemeodrzavanja = otvoreniCursor.getString(4);
                Integer sala_id = otvoreniCursor.getInt(5);
                Integer tip = otvoreniCursor.getInt(6);

                if(!mapaPrijavljeni.containsKey(id)) {

                    String Sala_naziv = "";
                    String tipIspita = "";

                    Cursor salaCursor = db.rawQuery("SELECT naziv FROM sala WHERE _id = ?", new String[]{String.valueOf(sala_id)});
                    assert salaCursor != null;
                    salaCursor.moveToFirst();

                    if (salaCursor.getCount() > 0) {
                        Sala_naziv = salaCursor.getString(0);
                    }
                    salaCursor.close();

                    Cursor tipCursor = db.rawQuery("SELECT opis FROM tipispita WHERE _id = ?", new String[]{String.valueOf(tip)});
                    assert tipCursor != null;
                    tipCursor.moveToFirst();

                    if (tipCursor.getCount() > 0) {
                        tipIspita = tipCursor.getString(0);
                    }
                    tipCursor.close();

                    String datumRok[] = rokPrijave.substring(0, 10).split("-");
                    String vrijemeRok = rokPrijave.substring(11, 16);

                    String datumOdrz[] = vrijemeodrzavanja.substring(0, 10).split("-");
                    String vrijemeOdrz = vrijemeodrzavanja.substring(11, 16);


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

                    Button buttonPrijavi = new Button(getContext());
                    buttonPrijavi.setText("Prijavi");
                    buttonPrijavi.setId(id);

                    buttonPrijavi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int ispitID = v.getId();

                            int nastavaID = mapaOtvoreni.get(ispitID);

                            String myFormat = "yyyy-MM-dd"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            Calendar myCalendar = Calendar.getInstance();
                            String datumPrijave = sdf.format(myCalendar.getTime());


                            db.execSQL("INSERT INTO `prijavaispit` (`datumPrijave`, `Nastava_id`, `ispitnirok_id`) VALUES " +
                                    "('" + datumPrijave + "', " + nastavaID + ", " + ispitID + ");");


                            initTablePrijave();
                        }
                    });

                    row.addView(textPredmet);
                    row.addView(textRokZaPrijavu);
                    row.addView(textVrijemeOdrz);
                    row.addView(textSala);
                    row.addView(textTip);
                    row.addView(buttonPrijavi);

                    tl2.addView(row, tl2.getChildCount());

                    mapaOtvoreni.put(id, nastavaID);
                }

                otvoreniCursor.moveToNext();

            }
            otvoreniCursor.close();
        }
        else {
            TableRow row = new TableRow(getContext());
            row.setMinimumHeight(50);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            lp.height = 100;
            row.setLayoutParams(lp);

            TextView textPredmet = new TextView(getContext());
            textPredmet.setText("Nemate otvorenih ispita za prijavu");
            textPredmet.setTextSize(15f);
            textPredmet.setHeight(100);
            textPredmet.setWidth(160);
            textPredmet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(textPredmet);
            tl2.addView(row, 1);

        }

    }

    private void ocistiTabele() {
        int brojPrijava = tl.getChildCount();
        int brojOtvorenih = tl2.getChildCount();

        int i = 1;
        while (i < brojPrijava) {
            tl.removeViewAt(1);
            i++;
        }

        int j = 1;
        while (j < brojOtvorenih) {
            tl2.removeViewAt(1);
            j++;
        }
    }

}
