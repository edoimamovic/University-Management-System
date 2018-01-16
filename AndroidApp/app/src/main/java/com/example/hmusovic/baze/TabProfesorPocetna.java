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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabProfesorPocetna extends Fragment {

    SQLiteDatabase db;
    SharedPreferences sharedPreferences;

    PieChart pieChartPrvi;
    BarChart barChart;
    ArrayList<String> predmeti;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tab_profesor_pocetna, container, false);

        predmeti = new ArrayList<>();
        db = DatabaseClient.getInstance(rootView.getContext()).getDb();
        sharedPreferences = getActivity().getSharedPreferences("local1", Context.MODE_PRIVATE);

        pieChartPrvi = (PieChart) rootView.findViewById(R.id.pieChartStudenti);
        barChart = (BarChart) rootView.findViewById(R.id.barchartProsjek);

        pieChartPrvi.getDescription().setEnabled(false);
        pieChartPrvi.setRotationEnabled(true);
        pieChartPrvi.setHoleRadius(40f);
        pieChartPrvi.setHoleColor(Color.TRANSPARENT);
        pieChartPrvi.setTransparentCircleAlpha(0);
        pieChartPrvi.setCenterText("Broj studenata");
        pieChartPrvi.setCenterTextSize(14);
        pieChartPrvi.setCenterTextColor(Color.WHITE);
        AddDataToPieChartPrvi();

        barChart.getDescription().setEnabled(true);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(10);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setEnabled(false);

        IAxisValueFormatter custom = new DefaultAxisValueFormatter(3);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setDrawGridLines(true);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(5f); // this replaces setStartAtZero(true)
        leftAxis.setGranularity(1f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setAxisMaximum(10f);
        rightAxis.setSpaceTop(15f);
        rightAxis.setGranularity(1f);
        rightAxis.setAxisMinimum(5f); // this replaces setStartAtZero(true)

        Legend l = barChart.getLegend();
        l.setEnabled(false);

        AddDataBarChart();

        return rootView;
    }

    private void AddDataToPieChartPrvi() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        int profesorId = sharedPreferences.getInt("loginID", -1);

        Map<String, Integer> studenti = new HashMap<>();

        Cursor c = db.rawQuery("SELECT p.naziv FROM predmet p, kurs k, nastava n WHERE p.Uposlenik_id = ? AND p._id = k.Predmet_id AND k._id = n.Kurs_id", new String[]{String.valueOf(profesorId)});
        assert c != null;
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            String nazivPredmeta = c.getString(0);

            if (studenti.containsKey(nazivPredmeta)) {
                int brojac = studenti.get(nazivPredmeta);
                brojac++;
                studenti.put(nazivPredmeta, brojac);
            } else {
                studenti.put(nazivPredmeta, 1);
            }
            c.moveToNext();
        }
        c.close();

        for (Map.Entry<String, Integer> entry : studenti.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            entries.add(new PieEntry(value, key));
        }

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

    private void AddDataBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();

        int profesorId = sharedPreferences.getInt("loginID", -1);

        Map<String, Float> studenti = new HashMap<>();
        Map<String, Integer> brojaci = new HashMap<>();

        Cursor c = db.rawQuery("SELECT p.naziv, n.ocjena FROM predmet p, kurs k, nastava n WHERE p.Uposlenik_id = ? AND p._id = k.Predmet_id AND k._id = n.Kurs_id", new String[]{String.valueOf(profesorId)});
        assert c != null;
        c.moveToFirst();


        for (int i = 0; i < c.getCount(); i++) {
            String nazivPredmeta = c.getString(0);
            Integer ocjena = c.getInt(1);

            if (studenti.containsKey(nazivPredmeta)) {
                Float stariProsjek = studenti.get(nazivPredmeta);
                Integer brojac = brojaci.get(nazivPredmeta);

                stariProsjek = stariProsjek * brojac;
                brojac++;
                Float noviProsjek = (stariProsjek + ocjena) / brojac;

                studenti.put(nazivPredmeta, noviProsjek);
                brojaci.put(nazivPredmeta, brojac);
            } else {
                studenti.put(nazivPredmeta, (float) ocjena);
                brojaci.put(nazivPredmeta, 1);
            }
            c.moveToNext();
        }
        c.close();

        ArrayList<String> nazivi = new ArrayList<>();

        int i = 1;
        for (Map.Entry<String, Float> entry : studenti.entrySet()) {
            String key = entry.getKey();
            Float value = entry.getValue();

            entries.add(new BarEntry(i, value));

            nazivi.add(key);
            i++;
        }

        String[] s = new String[nazivi.size()];
        for(int j = 0; j < nazivi.size(); j++)
            s[j] = nazivi.get(j);

        BarDataSet set1;

        if (barChart.getData() != null &&  barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(entries, "Prosjek ocjena");
            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.PASTEL_COLORS);
            set1.setStackLabels(s);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.5f);

            barChart.setData(data);
        }
    }
}

