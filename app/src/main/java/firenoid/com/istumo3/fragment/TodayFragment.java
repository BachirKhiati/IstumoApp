package firenoid.com.istumo3.fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import firenoid.com.istumo3.R;
import firenoid.com.istumo3.custom.MyMarkerView;
import firenoid.com.istumo3.custom.RadarMarkerView;

import static android.graphics.Color.WHITE;
import static android.graphics.Color.rgb;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment implements OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public  static PieChart mChart1;
    private  static PieChart mChart2;
    private  static PieChart mChart3;
    private  static SeekBar mSeekBarX, mSeekBarY;
    private  static  TextView tvX, tvY,totalHourSettingTExt;
    public static String dateSelected;
    private  static CalendarView CalanderHistory;
    FloatingActionButton fab;
    //  private ProgressBar mProgressBar;
    // private Handler handler = new Handler();
    Toolbar toolbar;
    String Yesterday, today;
    Button buttonCalendar;
    private RadarChart mRadarChart;
    private LineChart mLineChart;
    ImageView linecharBtn,piechartBtn,barchartBtn;

    private Typeface tf;


    private OnFragmentInteractionListener mListener;

    public TodayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
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

        View view= inflater.inflate(R.layout.fragment_today, container, false);

        piechart(view);
        barchart(view);
        linechart(view);


        linecharBtn=(ImageView)view.findViewById(R.id.imageViewline);
        linecharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChart1.setVisibility(View.GONE);
                mChart2.setVisibility(View.GONE);
                mChart3.setVisibility(View.GONE);
                 mLineChart.setVisibility(View.VISIBLE);
                  mRadarChart.setVisibility(View.GONE);


            }
        });

        piechartBtn=(ImageView)view.findViewById(R.id.imageViewpie);
        piechartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChart1.setVisibility(View.VISIBLE);
                mChart2.setVisibility(View.VISIBLE);
                mChart3.setVisibility(View.VISIBLE);
                mLineChart.setVisibility(View.GONE);
                mRadarChart.setVisibility(View.GONE);



            }
        });
        barchartBtn=(ImageView)view.findViewById(R.id.imageViewbar);
        barchartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChart1.setVisibility(View.GONE);
                mChart2.setVisibility(View.GONE);
                mChart3.setVisibility(View.GONE);
                mLineChart.setVisibility(View.GONE);
                mRadarChart.setVisibility(View.VISIBLE);


            }
        });

        mChart1 .setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Intent idIntent = new Intent(getActivity(), firenoid.com.istumo3.RadarChartActivitry.class);
                startActivity(idIntent);
            }

            @Override
            public void onNothingSelected() {

            }
        });




        return view;




    }


    private void linechart(View view){

        mLineChart = (LineChart) view.findViewById(R.id.chart1Line);

        mLineChart.setOnChartValueSelectedListener(this);
        mLineChart.setDrawGridBackground(false);

        // no description text
        mLineChart.getDescription().setEnabled(false);

        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        // mLineChart.setScaleXEnabled(true);
        // mLineChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);

        // set an alternative background color
        // mLineChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mv.setChartView(mLineChart); // For bounds control
        mLineChart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(15f);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        LimitLine ll1 = new LimitLine(12f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(15f);
        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(0f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(15f);
        ll2.setTypeface(tf);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(24);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mLineChart.getAxisRight().setEnabled(false);

        //mLineChart.getViewPortHandler().setMaximumScaleY(2f);
        //mLineChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data


//        mLineChart.setVisibleXRange(20);
//        mLineChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mLineChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mLineChart.animateX(2500);
        //mLineChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);



        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < 24; i++) {

            float val = (float) (Math.random() * 10) + 3;
            values.add(new Entry(i, val, getResources().getDrawable(R.drawable.star)));
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

            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.WHITE);
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(15f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setValueTextColor(Color.WHITE);

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.WHITE);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mLineChart.setData(data);








    }
    }

    private void barchart(View view){



        Typeface mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");





        mRadarChart = (RadarChart) view.findViewById(R.id.chart1radar);




        mRadarChart.getDescription().setEnabled(false);

        mRadarChart.setWebLineWidth(1f);
        mRadarChart.setWebColor(Color.WHITE);
        mRadarChart.setWebLineWidthInner(1f);
        mRadarChart.setWebColorInner(Color.WHITE);
        mRadarChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MarkerView mv = new RadarMarkerView(getActivity(), R.layout.radar_markerview);
        mv.setChartView(mRadarChart); // For bounds control
        mRadarChart.setMarker(mv); // Set the marker to the chart



        mRadarChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mRadarChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(18f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{getString(R.string.Morning), getString(R.string.Afternoon), getString(R.string.Night)};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);

        YAxis yAxis = mRadarChart.getYAxis();
        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(4, false);
        yAxis.setTextSize(18f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = mRadarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setTypeface(mTfLight);
        l.setTextSize(18f);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(3f);
        l.setTextColor(Color.WHITE);


        float mult = 80;
        float min = 20;
        int cnt = 3;

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mult) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mult) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, getString(R.string.today));
        set1.setColor(Color.rgb(103, 110, 129));
        set1.setFillColor(Color.rgb(103, 110, 129));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(10f);
        set1.setValueTextSize(18f);
        set1.setDrawHighlightCircleEnabled(true);
        set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, getString(R.string.Yesterday));
        set2.setColor(Color.rgb(121, 162, 175));
        set2.setFillColor(Color.rgb(121, 162, 175));
        set2.setDrawFilled(true);
        set2.setFillAlpha(180);
        set2.setLineWidth(10f);
        set1.setValueTextSize(18f);

        set2.setDrawHighlightCircleEnabled(false);
        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(mTfLight);
        data.setValueTextSize(18f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);
        mRadarChart.setTouchEnabled(false);
        mRadarChart.setData(data);

        mRadarChart.invalidate();


    }

    private void piechart(View view) {


        //   mSeekBarX = (SeekBar) view.findViewById(R.id.seekBar1);
        //    mSeekBarY = (SeekBar) view.findViewById(R.id.seekBar2);

        //    mSeekBarY.setProgress(10);

        //    mSeekBarX.setOnSeekBarChangeListener(this);
        //   mSeekBarY.setOnSeekBarChangeListener(this);
        buttonCalendar= (Button) view.findViewById(R.id.buttonCalendar);
        mChart1 = (PieChart) view.findViewById(R.id.chart1History);
        mChart2 = (PieChart) view.findViewById(R.id.chart2History);
        mChart3 = (PieChart) view.findViewById(R.id.chart3History);
        totalHourSettingTExt= (TextView) view.findViewById(R.id.totalHourSettingHistory);
        CalanderHistory = (CalendarView) view.findViewById(R.id.calendarViewHistory);

        dateSelected = getString(R.string.today);
        setDraw(mChart3);
        // setDraw(mChart2);
        //  setDraw(mChart);



        // dateSelected="Yesterday";
        Yesterday = getYesterdayDateString();
        today = geTodayDateString();


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




    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

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

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////

    private Calendar cal;




    //============================== clander

    public void ShopCalendar() {

        if (CalanderHistory.getVisibility() == View.GONE) {
            CalanderHistory.setVisibility(View.VISIBLE);
            mChart1.setVisibility(View.GONE);
            mChart2.setVisibility(View.GONE);
            mChart3.setVisibility(View.GONE);
            mLineChart.setVisibility(View.GONE);
            mRadarChart.setVisibility(View.GONE);
            totalHourSettingTExt.setVisibility(View.GONE);


        } else {

            CalanderHistory.setVisibility(View.GONE);
            mChart1.setVisibility(View.VISIBLE);
            mChart2.setVisibility(View.VISIBLE);
            mChart3.setVisibility(View.VISIBLE);
          //  mLineChart.setVisibility(View.VISIBLE);
          //  mRadarChart.setVisibility(View.VISIBLE);
            totalHourSettingTExt.setVisibility(View.VISIBLE);
            setDraw(mChart3);
            //  setDraw(mChart2);
            //  setDraw(mChart1);


        }

    }


    public void Showdraw() {
        mChart1.setVisibility(View.VISIBLE);
        mChart2.setVisibility(View.VISIBLE);
        mChart3.setVisibility(View.VISIBLE);
       // mLineChart.setVisibility(View.VISIBLE);
      //  mRadarChart.setVisibility(View.VISIBLE);
        totalHourSettingTExt.setVisibility(View.VISIBLE);
        CalanderHistory.setVisibility(View.GONE);
        linecharBtn.setVisibility(View.VISIBLE);
        piechartBtn.setVisibility(View.VISIBLE);
        barchartBtn.setVisibility(View.VISIBLE);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart1.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart1.invalidate();
                break;
            }


            case R.id.actionToggleCalander: {
                ShopCalendar();
                break;
            }



        }
        return true;
    }




    String[] mParties;
    public static float totalHourSetting;

    public static float[] value31, value32, value21, value22, value11, value12;


    private void setData(int count, int range, PieChart mChart) {

        float mult = range;
        Random rn = new Random();

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        switch (mChart.getTag().toString()) {
            case "2":
                mParties = new String[]{
                        getString(R.string.Slouch), getString(R.string.Other)
                };

               /* for (int i = 0; i < count; i++) {

                    value21 = new float[2];
                    value21[i] = (int) (Math.random() * mult) + mult / 5;

                    entries.add(new PieEntry(value21[i], mParties[i % mParties.length]));

                int answer = rn.nextInt(10) + 1;
                }*/
                value21 = new float[2];
                value21[0]= (rn.nextInt(10 - 7 + 1) + 7+rn.nextFloat()) -value31[0];
                value21[1]=24-value21[0];
                entries.add(new PieEntry(value21[0], mParties[0 % mParties.length]));
                entries.add(new PieEntry(value21[1], mParties[1 % mParties.length]));
                setDraw(mChart1);
                break;
            case "3":
                mParties = new String[]{
                        getString(R.string.Good), getString(R.string.Other)
                };

                value31 = new float[2];
                value31[0]= rn.nextInt(6 - 1 + 1) + 1+rn.nextFloat();
                value31[1]= (24-value31[0]);
                entries.add(new PieEntry(value31[0], mParties[0 % mParties.length]));
                entries.add(new PieEntry(value31[1], mParties[1 % mParties.length]));




                setDraw(mChart2);
               /* for (int i = 0; i < 1; i++) {


                   // value31[i] = (int) (Math.random() * mult) + mult / 5;
                    value31[i]= (float) (0 + (Math.random() * (0 - 8)));
                    entries.add(new PieEntry(value31[i], mParties[i % mParties.length]));

                }*/


                break;
            case "1":
                mParties = new String[]{
                        getString(R.string.Slouch), getString(R.string.Good), getString(R.string.restoftheday)
                };
                count = 3;
                value11 = new float[3];
                value11[0]=24-value21[0]-value31[0];
               /* for (int i = 0; i < count; i++) {


                    value11[i] = (int) (Math.random() * mult) + mult / 5;
                    entries.add(new PieEntry(value11[i], mParties[i % mParties.length]));

                }*/
                totalHourSetting= value21[0]+value31[0];
                totalHourSettingTExt.setText(getActivity().getString(R.string.TotalsittingHours)+"\n"+String.format("%.1f", totalHourSetting)+getActivity().getString(R.string.hhour));
                entries.add(new PieEntry(value21[0], mParties[0 % mParties.length]));
                entries.add(new PieEntry(value31[0], mParties[1 % mParties.length]));
                entries.add(new PieEntry(value11[0], mParties[2 % mParties.length]));



                break;

        }


        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.


        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.Hours));

        //dataSet.setDrawValues(false);


        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        switch (mChart.getTag().toString()) {
            case "3":


                dataSet.setSliceSpace(10f);
                dataSet.setSelectionShift(15f);
                dataSet.getSelectionShift();
                colors.add(rgb(70, 206, 249));
                colors.add(rgb(89, 238, 71));
                break;

            case "2":
                dataSet.setSliceSpace(10f);
                dataSet.setSelectionShift(15f);
                colors.add(rgb(255, 99, 71));
                colors.add(rgb(89, 238, 71));
                break;


            case "1":
                dataSet.setSliceSpace(10f);
                dataSet.setSelectionShift(15f);
                colors.add(rgb(255, 99, 71));
                colors.add(rgb(70, 206, 249));
                colors.add(rgb(89, 238, 71));

                break;

        }

        // colors.add(ColorTemplate.getHoloBlue());
        colors.add(rgb(255, 99, 71));
        colors.add(rgb(70, 206, 249));
        colors.add(rgb(89, 238, 71));





        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.3f);
        dataSet.setValueLineColor(getResources().getColor(R.color.white));
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);



        PieData data = new PieData(dataSet);


        // data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        mChart.setEntryLabelColor(getResources().getColor(R.color.white));
        mChart.setDrawEntryLabels(false);
        mChart.setData(data);


        // undo all highlights
        //  mChart.highlightValues(null);

        mChart.invalidate();


    }



    private void setDraw(PieChart mChart) {
        //  mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        // mChart.setExtraOffsets(50, 50, 50, 50);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));


        mChart.setExtraOffsets(50.f, 20.f, 40.f, 10.f);

        switch (mChart.getTag().toString()) {
            case "3":
                mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
                break;

            case "2":
                mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
                break;


            case "1":

                mChart.setExtraOffsets(20.f, 20, 0, 5.f);

                break;

        }



        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.TRANSPARENT);

        mChart.setTransparentCircleColor(Color.TRANSPARENT);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(90f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);
       // int colorBlack = Color.parseColor("#000000");
        mChart.setEntryLabelColor(getResources().getColor(R.color.white));
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        //  mChart.setUnit(" t");
        //   mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        Legend l;
        switch (mChart.getTag().toString()) {
            case "3":
                mChart.setCenterText(generateCenterSpannableText3());

                setData(2, 24, mChart);

                l = mChart.getLegend();

                l.setEnabled(false);

                break;

            case "2":
                mChart.setCenterText(generateCenterSpannableText2());
                setData(2, 24, mChart);

                l = mChart.getLegend();
                l.setEnabled(false);

                break;


            case "1":
                mChart.setCenterText(generateCenterSpannableText(dateSelected));
                setData(2, 24, mChart);


                l = mChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                l.setTextSize(15);
                l.setTextColor(WHITE);
                l.setDrawInside(false);
                l.setEnabled(true);

                break;

        }


        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


    }
    //========================================================================

    private SpannableString generateCenterSpannableText(String dateselect) {

        SpannableString s = new SpannableString(dateselect);
        s.setSpan(new RelativeSizeSpan(2f), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);

        s.setSpan(new StyleSpan(Typeface.ITALIC), 0, s.length(), 0);

        return s;
    }

    private SpannableString generateCenterSpannableText2() {

        SpannableString s = new SpannableString(getString(R.string.Slouch));
        s.setSpan(new RelativeSizeSpan(2f), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(rgb(255, 99, 71)), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 0, s.length(), 0);

        return s;
    }


    private SpannableString generateCenterSpannableText3() {

        SpannableString s = new SpannableString(getString(R.string.Good));
        s.setSpan(new RelativeSizeSpan(2), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(rgb(70, 206, 249)), 0, s.length(), 0);

        s.setSpan(new StyleSpan(Typeface.ITALIC), 0, s.length(), 0);

        return s;
    }


    //========================================================================








    //=======================================================
    //=======================================================



    public static boolean isYesterday(Date d) {
        return DateUtils.isToday(d.getTime() + DateUtils.DAY_IN_MILLIS);
    }



    private Date yesterday() {
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private Date today() {
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }

    private Date dateSelect(int back) {
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, back);
        return cal.getTime();
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(yesterday());
    }

    private String geTodayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(today());
    }

    private String getDateString(int back) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(dateSelect(back));
    }






    public static void showSnackbar(View view, String message, int duration) {
        // Create snackbar
        final Snackbar snackbar = Snackbar.make(view, message, duration);

        // Set an action on it, and a handler
        snackbar.setAction(R.string.DISMISS, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }






}









 /*   mProgressBar = (ProgressBar) view.findViewById(R.id.circle_progress_bar);
        Button btn = (Button) view.findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       int i = 0;
                                       int progressStatus = 0;

                                       progressStatus=50;

                                       mProgressBar
                                               .setProgress(progressStatus);

                                   }
                               });




            @Override
            public void onClick(View v) {
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
                }).start();
            }
        });*/




        /*fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                showSnackbar(view, "Contact us!", Snackbar.LENGTH_LONG);


            }
        });*/
// fab.setVisibility(View.GONE);







// Inflate the layout for this fragment



