package firenoid.com.istumo3.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.Random;

import firenoid.com.istumo3.CircularProgressBar;
import firenoid.com.istumo3.MainActivity;
import firenoid.com.istumo3.R;
import firenoid.com.istumo3.SimpleChartDemo;
import firenoid.com.istumo3.custom.MyMarkerView;

import static firenoid.com.istumo3.MainActivity.started;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CircularProgressBar mProgressBar;
    private Handler handler = new Handler();
    private OnFragmentInteractionListener mListener;
    private LineChart mLineChart;
    TextView textTimer,textTimer1,textTimer2;
    public Random rn;
    public int hourTime,minute;
    RippleBackground content1;
    public String hourformat;

   public static int progressStatus=0;
   private Button todayBtn;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        textTimer=view.findViewById(R.id.textTimer);
        textTimer1=view.findViewById(R.id.textTimer2);
        textTimer2=view.findViewById(R.id.textTimer3);
        todayBtn=view.findViewById(R.id.todayBtn);
        content1=view.findViewById(R.id.content1);

        mProgressBar = (CircularProgressBar) view.findViewById(R.id.circularProgress);
        mProgressBar.setProgress(60);
        mProgressBar.setProgressColor(Color.WHITE);


        rn = new Random();

        mLineChart = (LineChart) view.findViewById(R.id.chart1Linehome);

        linechart(view);

        if (!started) {
            started=true;
            content1.setVisibility(View.VISIBLE);
            content1.startRippleAnimation();
        }

        hourTime= rn.nextInt(6 - 1 + 1) + 1;
        minute=rn.nextInt(60 - 1 + 1) +1;

        final int goodhour,goodminute,badhour,badminute;
        goodhour=rn.nextInt(hourTime - 1 + 1) + 1;
        goodminute=rn.nextInt(minute - 1 + 1) + 1;

        badhour=hourTime-goodhour;
                badminute  =minute-goodminute;

        hourformat=getActivity().getString(R.string.hhour);
        new Thread(new Runnable() {
            int i = 0;


            public void run() {
                while (progressStatus < 60) {
                    progressStatus += 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        public void run() {

                            textTimer.setText( String.valueOf(hourTime)+hourformat+(minute < 10 ? "0" : "") + minute+"m");
                            textTimer1.setText(String.valueOf(goodhour)+hourformat+ (goodminute < 10 ? "0" : "") + goodminute+"m");
                            textTimer2.setText(String.valueOf(badhour)+hourformat+ (badminute < 10 ? "0" : "") + badminute+"m");
                            mProgressBar.setProgress(progressStatus);

                            addEntry();
                            i++;

                            if(content1.getVisibility()==View.VISIBLE || progressStatus>=3){

                                content1.stopRippleAnimation();
                                content1.setVisibility(View.GONE);
                                mProgressBar.setVisibility(View.VISIBLE);
                                textTimer.setVisibility(View.VISIBLE);
                                mLineChart.setVisibility(View.VISIBLE);
                                textTimer1.setVisibility(View.VISIBLE);
                                textTimer2.setVisibility(View.VISIBLE);
                                todayBtn.setVisibility(View.VISIBLE);
                            }
                            if(progressStatus==60){
                                minute++;
                                progressStatus=0;
                            }
                        }
                    });
                }
            }
        }).start();



                if(content1.getVisibility()==View.GONE ){
                    content1.stopRippleAnimation();
                    content1.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.VISIBLE);
                    textTimer.setVisibility(View.VISIBLE);
                    mLineChart.setVisibility(View.VISIBLE);
                    textTimer1.setVisibility(View.VISIBLE);
                    textTimer2.setVisibility(View.VISIBLE);
                    todayBtn.setVisibility(View.VISIBLE);

                }

       /* mLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        return view;
    }





    private void linechart(View view){







        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < 24; i++) {

            float val = (float) rn.nextInt(13 - 6 + 1) + 6;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;


        if (mLineChart.getData() != null &&
                mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mLineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");
           // set1.setColor(R.color.white);
            set1.setDrawValues(false);
            set1.setLineWidth(4f);

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setDrawCircles(false);
           /* if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
              Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.blac);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }*/

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mLineChart.setData(data);


            mLineChart.setBackgroundColor(getResources().getColor(R.color.transparent)); // use your bg color
            mLineChart.setDescription(null);
            mLineChart.setDrawGridBackground(false);
            mLineChart.setDrawBorders(false);
            mLineChart.setAutoScaleMinMaxEnabled(true);
            mLineChart.getData().setHighlightEnabled(false);
            // remove axis
            YAxis leftAxis = mLineChart.getAxisLeft();
            leftAxis.setEnabled(false);
            YAxis rightAxis = mLineChart.getAxisRight();
            rightAxis.setEnabled(false);
            leftAxis.setAxisMaximum(14);
            leftAxis.setAxisMinimum(5f);
            XAxis xAxis = mLineChart.getXAxis();
            xAxis.setEnabled(false);

            // hide legend
            Legend legend = mLineChart.getLegend();
            legend.setEnabled(false);

            mLineChart.invalidate();


      /*




*/




        }
    }

    private void addEntry() {

        LineData data = mLineChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well



            data.addEntry(new Entry(set.getEntryCount(), (float) rn.nextInt(13 - 6 + 1) + 6), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mLineChart.notifyDataSetChanged();

            // limit the number of visible entries
            mLineChart.setVisibleXRangeMaximum(24);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mLineChart.moveViewToX(data.getEntryCount());

            // this automatically refreshes the chart (calls invalidate())
            // mChart.moveViewTo(data.getXValCount()-7, 55f,
            // AxisDependency.LEFT);
        }
    }







    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
/*
 new Thread(new Runnable() {
         int i = 0;
         int progressStatus = 0;

public void run() {
        while (progressStatus < 100) {
        progressStatus += 5;
        try {
        Thread.sleep(200);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }

        // Update the progress bar
        handler.post(new Runnable() {
public void run() {
        mProgressBar
        .setProgress(progressStatus);
        i++;
        }
        });
        }
        }
        }).start();*/
