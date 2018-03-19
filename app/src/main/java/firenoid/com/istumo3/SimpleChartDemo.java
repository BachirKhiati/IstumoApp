
package firenoid.com.istumo3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import firenoid.com.istumo3.fragment.HomeFragment;
import firenoid.com.istumo3.fragment.charts.BarChartFrag;
import firenoid.com.istumo3.fragment.charts.ComplexityFragment;
import firenoid.com.istumo3.fragment.charts.PieChartFrag;
import firenoid.com.istumo3.fragment.charts.ScatterChartFrag;
import firenoid.com.istumo3.fragment.charts.SineCosineFragment;
import firenoid.com.istumo3.notimportant.DemoBase;

/**
 * Demonstrates how to keep your charts straight forward, simple and beautiful with the MPAndroidChart library.
 * 
 * @author Philipp Jahoda
 */
public class SimpleChartDemo extends AppCompatActivity {

    private static boolean started=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.activity_awesomedesign);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.nav_home);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        
        PageAdapter a = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(a);




        if (!started ) {
            started=true;
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle(R.string.Features);
            b.setMessage(R.string.SwipeLeftRight);
            b.setPositiveButton("OK", new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            b.show();
        }




    }
       
    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm); 
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment f = null;
            
            switch(pos) {
            case 0:
                f = SineCosineFragment.newInstance();
                break;
            case 1:
                f = ComplexityFragment.newInstance();
                break;
         /*   case 2:
                f = BarChartFrag.newInstance();
                break;
            case 3:
                f = ScatterChartFrag.newInstance();
                break;
            case 4:
                f = HomeFragment.newInstance();
                break;*/
            }

            return f;
        }

        @Override
        public int getCount() {
            return 2;
        }



    }



    public void returnclick(View v){

        super.onBackPressed();


    }

}
