package gb_in.mobile_app_admin;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import gb_in.mobile_app_admin.presenter.Presenter;

/**
 * Created by user on 13.01.2018.
 */

public class StatisticFragment extends Fragment {

    private Presenter presenter;
    private ListView personList;
    PieChart chart;

    public StatisticFragment() {
    }

    public  void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        personList = view.findViewById(R.id.statisticsList);
        chart = view.findViewById(R.id.chart);
        chart.setEntryLabelTextSize(16);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        presenter.loadPersonStatistics();
        return view;
    }

    public void updateChart(String[] persons){
        List<PieEntry> entries = new ArrayList<PieEntry>();

        // turn your data into Entry objects
        Random r = new Random();
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i =0; i< persons.length; i++) {
            int next = r.nextInt(100);
            entries.add(new PieEntry(next, persons[i]));

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("Name", persons[i]);
            map.put("Count", "Количество упоминаний: " + next);
            arrayList.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter (getContext(), arrayList, android.R.layout.simple_list_item_2,
                new String[]{"Name", "Count"},
                new int[]{android.R.id.text1, android.R.id.text2});
        personList.setAdapter(simpleAdapter);

        PieDataSet dataSet = new PieDataSet(entries,"");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(14);

        PieData ndata = new PieData(dataSet);

        chart.setEntryLabelColor(Color.BLACK);

        chart.setData(ndata);
        chart.invalidate();

    }
}

