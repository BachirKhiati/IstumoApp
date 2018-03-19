package firenoid.com.istumo3;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import firenoid.com.istumo3.utils.DatabaseHelper;
import firenoid.com.istumo3.utils.InputValidation;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;
    private LinearLayout nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private Button appCompatButtonLogin;

    private TextView textViewLinkRegister;
    public static String currentLanguage;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    public static String currentUserId;
    public static String currentEmail;


    @BindView(R.id.buttonfi)
    Button mToFIButton;
    @BindView(R.id.buttonen)
    Button mToENButton;


    String e = "";
    String ps = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.loginac);


        databaseHelper = new DatabaseHelper(getApplicationContext());
        ButterKnife.bind(this);

        Locale.getDefault().getDisplayLanguage();
        Log.d("language", "onCreate: " + Locale.getDefault().getDisplayLanguage());

        String lan = Locale.getDefault().getDisplayLanguage();


        if (lan.equals("fi") || lan.equals("Finnish")) {

            updateViews("fi");
        }


        currentLanguage = LocaleHelper.getLanguage(this);

        final int MyVersion = Build.VERSION.SDK_INT;

        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkPermissions()) {
                ActivityCompat.requestPermissions(LoginActivity.this, permissions, 100);
            }
        }


        if (currentLanguage.equals("fi")) {
            mToENButton.setVisibility(View.VISIBLE);
            mToFIButton.setVisibility(View.GONE);
        } else {

            mToFIButton.setVisibility(View.VISIBLE);
            mToENButton.setVisibility(View.GONE);
        }

        Log.d("onCreate", "onCreate: " + currentLanguage);
        Log.e("onCreate", "onCreate: " + currentLanguage);

//getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();


        Intent idIntent = getIntent();


        Bundle bundle = idIntent.getExtras();

        if (bundle != null) {
            e = getIntent().getStringExtra("E");
            ps = getIntent().getStringExtra("PS");
            textInputEditTextEmail.setText(e);
            textInputEditTextPassword.setText(ps);
        }


    }

    /**
     * This method is to initialize views
     */


    private void initViews() {

        nestedScrollView = (LinearLayout) findViewById(R.id.login);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);


    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    @OnClick(R.id.buttonen)
    public void onChangeToTRClicked() {
        mToFIButton.setVisibility(View.VISIBLE);
        mToENButton.setVisibility(View.GONE);
        updateViews("en");
    }

    @OnClick(R.id.buttonfi)
    public void onChangeToENClicked() {
        mToENButton.setVisibility(View.VISIBLE);
        mToFIButton.setVisibility(View.GONE);
        updateViews("fi");

    }

    private void updateViews(String languageCode) {


        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        this.recreate();


    }


    private void changeLang(View view){



}

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        textInputEditTextEmail.setText(e);
        textInputEditTextPassword.setText(ps);

        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {

        inputValidation = new InputValidation(activity);

    }





    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
// Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            Intent idIntent = new Intent(activity, MainActivity.class);

            idIntent.putExtra("NAME", databaseHelper.getUserName(textInputEditTextEmail.getText().toString().trim()));
            String ID = String.valueOf(databaseHelper.getUserId(textInputEditTextEmail.getText().toString().trim()));
            idIntent.putExtra("ID", ID);
            currentUserId = ID;
            currentEmail = textInputEditTextEmail.getText().toString().trim();
            emptyInputEditText();

            startActivity(idIntent);
        } else {

            textInputEditTextEmail.setError(getString(R.string.error_email_notfound)+"! "+getString(R.string.text_not_member) );
            textInputEditTextPassword.setError(getString(R.string.error_incorrect_password));
            hideKeyboardFrom(textInputEditTextEmail);

            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit textn
     */

    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(LoginActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(LoginActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
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


    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.RECORD_AUDIO,
    };
}