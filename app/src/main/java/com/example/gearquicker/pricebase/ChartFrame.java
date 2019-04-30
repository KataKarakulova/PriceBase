package com.example.gearquicker.pricebase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ChartFrame extends Fragment {

    private Spinner spinner;
    private LineChart chart;

    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.chart_fragment, container, false);
        setHasOptionsMenu(true);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {
        initSpinner(view);
        initChart(view);
    }

    private void initSpinner(View view) {
        spinner = view.findViewById(R.id.spinner);
        List<OrmItem> items = realm.copyFromRealm(realm.where(OrmItem.class).findAll());
        ArrayAdapter<OrmItem> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initChart(View view) {
        chart = view.findViewById(R.id.chart);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setEnabled(false);
        chart.setDescription(null);
        if (chart.getLegend() != null) {
            chart.getLegend().setEnabled(false);
        }
        chart.setScaleXEnabled(false);
        chart.getXAxis().setValueFormatter(new DataAxisFormatter());
        refreshChart();
    }

    private void refreshChart() {
        List<OrmPrice> prices = realm.copyFromRealm(realm.where(OrmPrice.class).equalTo("item.id", spinner.getSelectedItem().toString()).findAll());
        chart.clear();
        chart.invalidate();

        if (prices.size() > 0) {
            long maxData = prices.get(0).getDate();
            long minData = prices.get(0).getDate();
            double maxPrice = prices.get(0).getPrice();
            double minPrice = prices.get(0).getPrice();
            for (OrmPrice price : prices) {
                maxData = Math.max(maxData, price.getDate());
                minData = Math.min(minData, price.getDate());
                maxPrice = Math.max(maxPrice, price.getPrice());
                minPrice = Math.min(minPrice, price.getPrice());
            }

            List<Entry> entries = new ArrayList<>();
            for (OrmPrice price : prices) {
                entries.add(new Entry((float) price.getDate(), (float) price.getPrice()));
            }
            LineDataSet dataSet = new LineDataSet(entries, "");
            LineData lineData = new LineData(dataSet);
            dataSet.setDrawHighlightIndicators(false);
            dataSet.setDrawCircles(true);
            dataSet.setDrawValues(false);
            dataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
            dataSet.setColors(new int[]{R.color.colorPrimary}, getContext());
            dataSet.setCircleColor(R.color.colorPrimary);
            dataSet.setLineWidth(5);
            float delta = (float) ((maxPrice - minPrice) / 10);
            chart.getAxisRight().setAxisMinimum((float) minPrice - delta);
            chart.getAxisRight().setAxisMaximum((float) maxPrice + delta);
            chart.getXAxis().setLabelRotationAngle(45);
            chart.setData(lineData);
            chart.invalidate();
        }
    }

}
