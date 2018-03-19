package firenoid.com.istumo3;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.BarChart;
import com.skyfishjy.library.RippleBackground;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import firenoid.com.istumo3.fragment.HistoryFragment;
import firenoid.com.istumo3.fragment.HomeFragment;
import firenoid.com.istumo3.fragment.NotificationsFragment;
import firenoid.com.istumo3.fragment.ProfileFragment;
import firenoid.com.istumo3.fragment.SettingsFragment;
import firenoid.com.istumo3.fragment.SimpleFragment;
import firenoid.com.istumo3.fragment.TodayFragment;
import firenoid.com.istumo3.utils.DatabaseHelper;
import firenoid.com.istumo3.utils.User;

import static firenoid.com.istumo3.LoginActivity.currentEmail;
import static firenoid.com.istumo3.LoginActivity.currentUserId;
import static firenoid.com.istumo3.fragment.HistoryFragment.showSnackbar;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseHelper databaseHelper;
    private NavigationView navigationView;

    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private FloatingActionButton fab;
    String userName = "";
    String idFromIntent = "";
    SQLiteDatabase db;


    //  CardView  CardView;
    Button menuBtn;

    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    ImageView imageView, imageView2;
    TextView textView;
    User user;

    Toolbar toolbar;


    // index to identify current nav menu item
    public static int navItemIndex = 0;
    Button button3;
    // tags used to attach the fragments
    private static final String TAG_HOME = "Home";
    private static final String TAG_Profile = "Profile";
    private static final String TAG_HISTORY = "Calendar";
    private static final String TAG_NOTIFICATIONS = "Today";
    private static final String TAG_TODAY = "Today";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG;
    DrawerLayout drawer;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;
    private String name;
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    public static boolean started=false;
    int snak = 0;
    public static boolean loggedIn=false;






























    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

      drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle(R.string.nav_home);

        final int MyVersion = Build.VERSION.SDK_INT;

        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkPermissions()) {
                ActivityCompat.requestPermissions(MainActivity.this, permissions, 100);
            }
        }

        if (currentUserId == null) {
            Intent idIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(idIntent);
        }



        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);



        databaseHelper = new DatabaseHelper(getApplicationContext());
       user=new User();

        if (currentUserId != null & !loggedIn) {
            loggedIn=true;

                userName = databaseHelper.getUserName(currentEmail);

            fab.requestFocus();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.Welcome)+" " + userName, Snackbar.LENGTH_LONG);
            snackbar.show();
        }



        mHandler = new Handler();



        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showSnackbar(view, getString(R.string.Contact), Snackbar.LENGTH_LONG);

            }
        });

        // load nav menu header data

        try {
//
        } catch (Exception e) {
            e.printStackTrace();
        }


        // initializing navigation menu

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();


        }
        fab.setVisibility(View.GONE);
        loadNavHeader();
    }

    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        }

        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        }


        else  if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        else  if (loggedIn) {

                return;
            }




        finish();



    }
    public  void pressback(View view) {

        navItemIndex = 0;
        CURRENT_TAG = TAG_HOME;
        loadHomeFragment();

    }

           // super.onBackPressed();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
    private AnimationDrawableWrapper drawableWrapper;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        // define the animation for rotation


        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        if (navItemIndex == 3) {

            getMenuInflater().inflate(R.menu.main, menu);
        }
        if (navItemIndex ==2) {

            getMenuInflater().inflate(R.menu.main, menu);
        }

        if (navItemIndex == 1) {

            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            loggedIn=false;
            started=false;
            Intent idIntent = new Intent(MainActivity.this, LoginActivity.class);

                      startActivity(idIntent);

        }

        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



       /* if (id == R.id.nav_camera) {

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        switch (item.getItemId())
        {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_home:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;



                break;
            case R.id.nav_profile:
                  navItemIndex = 1;
                   CURRENT_TAG = TAG_Profile;


                //Intent idIntent = new Intent(MainActivity.this, Profile.class);
               // startActivity(idIntent);
                break;

            case R.id.nav_history:
                navItemIndex = 2;
                CURRENT_TAG = TAG_HISTORY;
                break;
         case R.id.nav_today:
                navItemIndex = 3;
                CURRENT_TAG = TAG_TODAY;
                break;
            case R.id.nav_settings:
                navItemIndex = 4;
                CURRENT_TAG = TAG_SETTINGS;
                break;
            case R.id.nav_about_us:
                // launch new intent instead of loading fragment
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_privacy_policy:
                // launch new intent instead of loading fragment
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                return true;
            default:
                navItemIndex = 0;

        }

        loadHomeFragment();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // set toolbar title

        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
      //  toggleFab();

        //Closing drawer on item click


        // refresh toolbar menu
        invalidateOptionsMenu();
    }



    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // Profile


                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;

               /* ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;*/
            case 2:
                // Calendar fragment
                HistoryFragment HistoryFragment = new HistoryFragment();
                return HistoryFragment;
            case 3:
                // notifications fragment
                TodayFragment todayFragment = new TodayFragment();
                return todayFragment;

            case 4:
                // settings fragment
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;


            default:
                return new HomeFragment();
        }
    }








    //////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public void menubutton(View view) {


        //  CardView.setVisibility(View.VISIBLE);
        //CardView.setVisibility(View.VISIBLE);
        // menuBtn.setVisibility(View.GONE);

        //  imageView.setVisibility(View.GONE);
        //textView.setVisibility(View.GONE);


        //CardView.setLayoutParams(new CardView.LayoutParams(100, 400));

       /*
        CardView.LayoutParams params = (CardView.LayoutParams) ConstraintLayout.getLayoutParams();
        int leftMargin = (int) getResources().getDimension(R.dimen.exclusion_card_margin_left_right);
        int rightMargin = (int) getResources().getDimension(R.dimen.exclusion_card_margin_left_right);

        params.setMargins(leftMargin, 0, rightMargin, 0);
        parentLayout.setLayoutParams(params);
*/


    }

    public void bluetoothOn(View view) {

        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content1);


        if (rippleBackground.isRippleAnimationRunning()) {

            rippleBackground.stopRippleAnimation();
        } else {

            rippleBackground.startRippleAnimation();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            //    Glide.with(this)
            //        .load(R.drawable.gifgreen)
            //         .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            //     .into((ImageView)findViewById(R.id.gif));

        }


        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        /*if (!mBluetoothAdapter.isEnabled()) {

            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            Glide.with(this)
                      .load(R.drawable.gifgreen)
                      .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                     .into((ImageView)findViewById(R.id.gif));
        }*/
    }


    public void Hidemenu(View view) {

        //CardView.setVisibility(View.GONE);
        //   CardView.setVisibility(View.GONE);
        //           //  menuBtn.setVisibility(View.VISIBLE);;

        //  imageView.setVisibility(View.VISIBLE);
        //  textView.setVisibility(View.VISIBLE);
    }


    public void todayActivity(View view) {
        navItemIndex = 3;
        CURRENT_TAG = TAG_TODAY;
        loadHomeFragment();


    }


    public void CalanderMenu(View view) {
        navItemIndex = 2;
        CURRENT_TAG = TAG_HISTORY;
        loadHomeFragment();

    }



    public void returnclick(View v){

        super.onBackPressed();


    }















    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(MainActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // do something
            }
            return;
        }
    }

   /* private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }*/

   public void loadNavHeader() {


        //  imageView = (CircleImageView) findViewById(R.id.imageBtnMain);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        //  textView = findViewById(R.id.userNameText);
        user= new User();

        if (currentUserId != null) {
            String name = databaseHelper.getUserName(currentEmail);
            txtName.setText(name);
            user = new User();
            String path = databaseHelper.getUserPicture(currentEmail);

            try {

                Glide.with(this)
                        .load(R.drawable.headerimage3)

                        .into(imgNavHeaderBg);

              //  GlideApp.with(this).load("http://goo.gl/gEgYUd").into(imageView);

               /* Glide.with(this).load(R.drawable.headerimage2)
                        .override(100, 200)
                        .into(imgNavHeaderBg);*/
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                File f = new File(path);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

              /*  Glide.with(this).load(b)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgProfile);*/


                imgProfile.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        }


    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.RECORD_AUDIO,
    };


}
