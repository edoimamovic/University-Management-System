package com.example.hmusovic.baze;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class TabSluzbaPocetna extends Fragment {
    private Integer[] vrijednosti;
    private String[] labele = {"1. godina", "2. godina", "3. godina"};
    PieChart pieChartPrvi, pieChartDrugi;
    SQLiteDatabase db;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_sluzba_pocetna, container, false);

        db = DatabaseClient.getInstance(rootView.getContext()).getDb();

        pieChartPrvi = (PieChart) rootView.findViewById(R.id.pieChartPrvi);
        pieChartDrugi = (PieChart) rootView.findViewById(R.id.pieChartDrugi);

        pieChartPrvi.getDescription().setEnabled(false);
        pieChartPrvi.setRotationEnabled(true);
        pieChartPrvi.setHoleRadius(40f);
        pieChartPrvi.setHoleColor(Color.TRANSPARENT);
        pieChartPrvi.setTransparentCircleAlpha(0);
        pieChartPrvi.setCenterText("1. ciklus");
        pieChartPrvi.setCenterTextSize(14);
        pieChartPrvi.setCenterTextColor(Color.WHITE);

        pieChartDrugi.getDescription().setEnabled(false);
        pieChartDrugi.setRotationEnabled(true);
        pieChartDrugi.setHoleRadius(40f);
        pieChartDrugi.setHoleColor(Color.TRANSPARENT);
        pieChartDrugi.setTransparentCircleAlpha(0);
        pieChartDrugi.setCenterText("2. ciklus");
        pieChartDrugi.setCenterTextSize(14);
        pieChartDrugi.setCenterTextColor(Color.WHITE);

        AddDataToPieChartPrvi();
        AddDataToPieChartDrugi();

        return rootView;
    }

    private void AddDataToPieChartPrvi() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM student WHERE ciklus = 1 AND godinaStudija = 1", null);
        assert c != null;
        c.moveToFirst();
        int prvaGod = c.getInt(0);
        c.close();

        c = db.rawQuery("SELECT COUNT(*) FROM student WHERE ciklus = 1 AND godinaStudija = 2", null);
        assert c != null;
        c.moveToFirst();
        int drugaGod = c.getInt(0);
        c.close();

        c = db.rawQuery("SELECT COUNT(*) FROM student WHERE ciklus = 1 AND godinaStudija = 3", null);
        assert c != null;
        c.moveToFirst();
        int trecaGod = c.getInt(0);
        c.close();

        entries.add(new PieEntry(prvaGod, labele[0]));
        entries.add(new PieEntry(drugaGod, labele[1]));
        entries.add(new PieEntry(trecaGod, labele[2]));

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(5);
        pieDataSet.setValueTextSize(14);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        Legend legend = pieChartPrvi.getLegend();
        legend.setEnabled(true);
        legend.setFormSize(15f);
        legend.setTextSize(15f);
        legend.setTextColor(Color.WHITE);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData pieData = new PieData(pieDataSet);
        pieChartPrvi.setData(pieData);
        pieChartPrvi.animateY(1000);
        pieChartPrvi.invalidate();

    }

    private void AddDataToPieChartDrugi() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT COUNT(*) FROM student WHERE ciklus = 2 AND godinaStudija = 1", null);
        assert c != null;
        c.moveToFirst();
        int prvaGod = c.getInt(0);
        c.close();

        c = db.rawQuery("SELECT COUNT(*) FROM student WHERE ciklus = 2 AND godinaStudija = 2", null);
        assert c != null;
        c.moveToFirst();
        int drugaGod = c.getInt(0);
        c.close();
        
        entries.add(new PieEntry(prvaGod, labele[0]));
        entries.add(new PieEntry(drugaGod, labele[1]));

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setSliceSpace(5);
        pieDataSet.setValueTextSize(14);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        Legend legend = pieChartDrugi.getLegend();
        legend.setEnabled(true);
        legend.setFormSize(15f);
        legend.setTextSize(15f);
        legend.setTextColor(Color.WHITE);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData pieData = new PieData(pieDataSet);
        pieChartDrugi.setData(pieData);
        pieChartDrugi.animateX(1000);
        pieChartDrugi.invalidate();

    }
}
