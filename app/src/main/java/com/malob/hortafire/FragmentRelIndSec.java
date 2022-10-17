package com.malob.hortafire;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.malob.hortafire.hortalica.Dados;

import java.util.ArrayList;


public class FragmentRelIndSec extends Fragment {
    ArrayList barArraylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rel_ind_sec, container, false);

        BarChart barChart = view.findViewById(R.id.barchart);
        getData();
        BarDataSet barDataSet = new BarDataSet(barArraylist, "Sem.  Ger.  Ber.  Eng.");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        //color bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //text color
        barDataSet.setValueTextColor(Color.BLACK);

        //settting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        // setting descrition label
        Description description = barChart.getDescription();
        description.setText("Tito");
        description.setEnabled(false);

        // eixo Y
        YAxis rightAxis = barChart.getAxisRight();
        YAxis leftAxis = barChart.getAxisLeft();
        rightAxis.setEnabled(false);
        leftAxis.setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setEnabled(false);
        return view;
    }

    private void getData() {
        barArraylist = new ArrayList();
        barArraylist.add(new BarEntry(2, Dados.dataS));
        barArraylist.add(new BarEntry(3, Dados.dataG));
        barArraylist.add(new BarEntry(4, Dados.dataB));
        barArraylist.add(new BarEntry(5, Dados.dataE));
    }
}