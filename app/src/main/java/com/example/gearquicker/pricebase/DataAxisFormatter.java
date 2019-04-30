package com.example.gearquicker.pricebase;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DataAxisFormatter implements IAxisValueFormatter {

    public DataAxisFormatter() {

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(new Date((long) value));
    }
}
