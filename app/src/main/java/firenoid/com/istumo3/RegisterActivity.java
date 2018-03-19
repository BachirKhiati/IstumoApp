package firenoid.com.istumo3;

/**
 * Created by OpiFrame on 07/12/2017.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import de.hdodenhof.circleimageview.CircleImageView;
import firenoid.com.istumo3.utils.DatabaseHelper;
import firenoid.com.istumo3.utils.InputValidation;
import firenoid.com.istumo3.utils.User;

import static firenoid.com.istumo3.utils.FileUtils.isDownloadsDocument;
import static firenoid.com.istumo3.utils.FileUtils.isExternalStorageDocument;
import static firenoid.com.istumo3.utils.FileUtils.isMediaDocument;


public class RegisterActivity extends AppCompatActivity {

        private final AppCompatActivity activity = RegisterActivity.this;

        private FrameLayout FrameLayout;
        TextInputEditText TextInputEditTextAge;
        TextInputEditText TextInputEditTextHei;
        TextInputEditText TextInputEditTextWei;
        SeekBar TextInputEditTexttype;



        private TextInputLayout textInputLayoutName;
        private TextInputLayout textInputLayoutEmail;
        private TextInputLayout textInputLayoutPassword;
        private TextInputLayout textInputLayoutConfirmPassword;

        private TextInputEditText textInputEditTextName;
        private TextInputEditText textInputEditTextEmail;
        private TextInputEditText textInputEditTextPassword;
        private TextInputEditText textInputEditTextConfirmPassword;

        private Button ButtonNext,registerNext3,appCompatButtonRegister3,ButtonNext2;
        private AppCompatRadioButton RadioButtonMale;
        private AppCompatRadioButton RadioButtonFemale;
        private String gender;
        public TextView textInputEditTextType2;

        private String age,hei,wei,type1;



        private static Bitmap Image = null;
        private static Bitmap rotateImage = null;

        private static final int GALLERY = 1;

        private String seekbarString="1";




        CircleImageView imageView;
        private AppCompatButton appCompatButtonRegister;
        private AppCompatTextView appCompatTextViewLoginLink;
        private RelativeLayout relativeLayout1,relativeLayout2,Layout1;
        private InputValidation inputValidation;
        private DatabaseHelper databaseHelper;
        private User user;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {




                super.onCreate(savedInstanceState);

                setContentView(R.layout.register);





                final int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                        if (!checkIfAlreadyhavePermission()) {
                                ActivityCompat.requestPermissions(RegisterActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        } else {



                        }
                }

                imageView = (CircleImageView)findViewById(R.id.imageButton);
                RadioButtonMale= (AppCompatRadioButton)findViewById(R.id.radio_pirates2);

                RadioButtonFemale= (AppCompatRadioButton)findViewById(R.id.radio_ninjas2);
                textInputEditTextType2=findViewById(R.id.textInputEditTextType2);
                RadioButtonMale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                onRadioButtonMale();
                        }
                });

                RadioButtonFemale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                onRadioButtonfemale();
                        }
                });
                TextInputEditTextAge = (TextInputEditText)findViewById(R.id.TextInputEditTextAge2);

                TextInputEditTextHei = (TextInputEditText)findViewById(R.id.TextInputEditTextHei2);

                ButtonNext = (Button) findViewById(R.id.registerNext1);

                ButtonNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                onClick2();

                        }
                });
                ButtonNext2 = (Button) findViewById(R.id.registerNext2);
                ButtonNext2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                onClick3();

                        }
                });



                registerNext3 = (Button) findViewById(R.id.registerNext3);
                registerNext3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                onClick4();

                        }
                });

                TextInputEditTextWei = (TextInputEditText)findViewById(R.id.TextInputEditTextWei2);

                relativeLayout1 = (RelativeLayout) findViewById(R.id.Layout1);
                relativeLayout2 = (RelativeLayout) findViewById(R.id.Layout2);
                Layout1 = (RelativeLayout) findViewById(R.id.Layout3);

                TextInputEditTexttype = (SeekBar)findViewById(R.id.seekBar3);




                initViews();
                initListeners();
                initObjects();



               /* TextInputEditTextAge.setOnValueChangedListener(new TextInputEditText.OnValueChangeListener() {
                        @Override
                        public void onValueChange(TextInputEditText picker, int oldVal, int newVal){
                                //Display the newly selected number from picker
                                age= String.valueOf(newVal);
                        }
                });

                TextInputEditTextHei.setOnValueChangedListener(new TextInputEditText.OnValueChangeListener() {
                        @Override
                        public void onValueChange(TextInputEditText picker, int oldVal, int newVal){
                                //Display the newly selected number from picker
                                hei= String.valueOf(newVal);
                        }
                });


                TextInputEditTextWei.setOnValueChangedListener(new TextInputEditText.OnValueChangeListener() {
                        @Override
                        public void onValueChange(TextInputEditText picker, int oldVal, int newVal){
                                //Display the newly selected number from picker
                               wei= String.valueOf(newVal);

                        }
                });


                TextInputEditTexttype.setOnValueChangedListener(new TextInputEditText.OnValueChangeListener() {
                        @Override
                        public void onValueChange(TextInputEditText picker, int oldVal, int newVal){
                                //Display the newly selected number from picker
                               type1=String.valueOf(newVal);
                        }
                });*/


                TextInputEditTexttype.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress,
                                                      boolean fromUser) {
                                // TODO Auto-generated method stub
                                seekbarString=String.valueOf(progress);
                                textInputEditTextType2.setText(seekbarString);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                                // TODO Auto-generated method stub
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                                // TODO Auto-generated method stub
                        }
                });



                final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake2);

                imageView.startAnimation(animShake);


        }


        @Override
        protected void attachBaseContext(Context base) {
                super.attachBaseContext(LocaleHelper.onAttach(base));
        }


        /**
         * This method is to initialize views
         *
         *
         *
         */
        private void initViews() {
                FrameLayout = (FrameLayout) findViewById(R.id.register);


                textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
                textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
                textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
                textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);




                textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
                textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
                textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
                textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);









                appCompatButtonRegister = (AppCompatButton) findViewById(R.id.registerNext3);

                appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink2);


                user = new User();




        }


        public void onRadioButtonMale() {
                // Is the button now checked?


                                        // Pirates are the best
                                        gender=getString(R.string.Male);
                                RadioButtonFemale.setChecked(false);


        }



        public void onRadioButtonfemale() {
                // Is the button now checked?

                                        // Ninjas rule
                                        gender=getString(R.string.Female);
                                RadioButtonMale.setChecked(false);


        }


        /**
         * This method is to initialize listeners
         */
        private void initListeners() {
           //     appCompatButtonRegister.setOnClickListener(this);
                appCompatTextViewLoginLink.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                finish();
                        }
                });

        }



        /**
         * This method is to initialize objects to be used
         */
        private void initObjects() {
                inputValidation = new InputValidation(activity);
                databaseHelper = new DatabaseHelper(activity);
                user = new User();

        }


        /**
         * This implemented method is to listen the click on view
         *
         * @param
         */

        public void onClick4() {

                                postDataToSQLite();



        }



        public void onClick2() {

                postDataToSQLiteVerification();



        }

        public void onClick3() {


                ButtonNext2.setVisibility(View.GONE);
                registerNext3.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.GONE);
                Layout1.setVisibility(View.VISIBLE);


        }





        public void onClick2Error(View v) {
                Layout1.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.GONE);
                ButtonNext.setVisibility(View.GONE);
                registerNext3.setVisibility(View.VISIBLE);

        }



        /**
         * This method is to validate the input text fields and post data to SQLite
         */
        private void postDataToSQLiteVerification() {
                if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
                        return;
                }
                if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                        return;
                }
                if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                        return;
                }
                if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
                        return;
                }
                if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                        textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
                        return;
                }


                if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                        , textInputEditTextPassword.getText().toString().trim())) {



                        textInputEditTextEmail.setError(getString(R.string.error_email_exists));
                        hideKeyboardFrom(textInputEditTextEmail);



                }



               if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

                        relativeLayout1.setVisibility(View.GONE);
                        relativeLayout2.setVisibility(View.VISIBLE);
                        ButtonNext.setVisibility(View.GONE);
                        ButtonNext2.setVisibility(View.VISIBLE);
                }else {
// Snack Bar to show error message that record already exists
                                Snackbar.make(FrameLayout, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
                        }


        }



        private void postDataToSQLite() {
                if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
                        return;
                }
                if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                        return;
                }
                if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
                        return;
                }
                if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
                        return;
                }
                if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                        textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
                        return;
                }



                if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

                        user.setName(textInputEditTextName.getText().toString().trim());
                        user.setEmail(textInputEditTextEmail.getText().toString().trim());
                        user.setPassword(textInputEditTextPassword.getText().toString().trim());
                        user.setgender(gender);
                        user.setage(TextInputEditTextAge.getText().toString().trim());
                        user.setwei(TextInputEditTextWei.getText().toString().trim());
                        user.sethei(TextInputEditTextHei.getText().toString().trim());
                        user.settype(seekbarString.trim());
                        user.setpicture(path.trim());




                        databaseHelper.addUser(user);

// Snack Bar to show success message that record saved successfully
                        Snackbar.make(FrameLayout
, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();




                        String e     = String.valueOf(textInputEditTextEmail.getText().toString().trim());
                        String ps = String.valueOf(textInputEditTextPassword.getText().toString().trim())  ;


                        Intent idIntent = new Intent(activity, LoginActivity.class);
                        idIntent.putExtra("E", e);
                        idIntent.putExtra("PS", ps);
                        startActivity(idIntent);

                        emptyInputEditText();
                } else {
// Snack Bar to show error message that record already exists
                        Snackbar.make(FrameLayout, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
                }


        }

        private void hideKeyboardFrom(View view) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        /**
         * This method is to empty all input edit text
         */
        private void emptyInputEditText() {
                textInputEditTextName.setText(null);
                textInputEditTextEmail.setText(null);
                textInputEditTextPassword.setText(null);
                textInputEditTextConfirmPassword.setText(null);
        }



        private boolean checkIfAlreadyhavePermission() {
                int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                return result == PackageManager.PERMISSION_GRANTED;
        }


        @Override
        public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                switch (requestCode) {
                        case 1: {
                                if (grantResults.length > 0
                                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {




                                } else {
                                        Toast.makeText(getApplicationContext(), R.string.permission, Toast.LENGTH_LONG).show();
                                }
                                break;
                        }
                }
        }




        public void imageselectionReg(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.SelectPicture)), GALLERY);
        }


        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == GALLERY && resultCode != 0) {
                        InputStream is = null;
                        Uri mImageUri = data.getData();
                        path="";
                        try {
                                Image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageUri);






                                imageView.setImageBitmap(Image);


                                path=getFilePath(getApplicationContext(),mImageUri);



                              //  user.setpicture(path.toString());


                              //  databaseHelper.updateoneUser(user, "PERSON_COLUMN_Picture");

                                ///File file = new File(new URI(path));
                                //    Log.d("Email", "onCreate: "+path);







                                //   Bitmap bitmap = BitmapFactory.decodeStream(is);
                                Log.e("imagePath", "onActivityResult: " +path.toString());















                                // Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());



                                //   databaseHelper.updateoneUser(user, "PERSON_COLUMN_Picture");

                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } catch (URISyntaxException e) {
                                e.printStackTrace();
                        }
                }
        }


        String path=".";
        @SuppressLint("NewApi")
        public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
                String selection = null;
                String[] selectionArgs = null;
                // Uri is different in versions after KITKAT (Android 4.4), we need to
                if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
                        if (isExternalStorageDocument(uri)) {
                                final String docId = DocumentsContract.getDocumentId(uri);
                                final String[] split = docId.split(":");
                                return Environment.getExternalStorageDirectory() + "/" + split[1];
                        } else if (isDownloadsDocument(uri)) {
                                final String id = DocumentsContract.getDocumentId(uri);
                                uri = ContentUris.withAppendedId(
                                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        } else if (isMediaDocument(uri)) {
                                final String docId = DocumentsContract.getDocumentId(uri);
                                final String[] split = docId.split(":");
                                final String type = split[0];
                                if ("image".equals(type)) {
                                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                                } else if ("video".equals(type)) {
                                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                                } else if ("audio".equals(type)) {
                                        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                                }
                                selection = "_id=?";
                                selectionArgs = new String[]{
                                        split[1]
                                };
                        }
                }
                if ("content".equalsIgnoreCase(uri.getScheme())) {
                        String[] projection = {
                                MediaStore.Images.Media.DATA
                        };
                        Cursor cursor = null;
                        try {
                                cursor = context.getContentResolver()
                                        .query(uri, projection, selection, selectionArgs, null);
                                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                if (cursor.moveToFirst()) {
                                        return cursor.getString(column_index);
                                }
                        } catch (Exception e) {
                        }
                } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                        return uri.getPath();
                }
                return null;
        }



}
