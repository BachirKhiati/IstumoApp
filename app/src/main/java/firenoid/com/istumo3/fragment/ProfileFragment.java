package firenoid.com.istumo3.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import firenoid.com.istumo3.R;
import firenoid.com.istumo3.utils.DatabaseHelper;
import firenoid.com.istumo3.utils.User;

import static firenoid.com.istumo3.LoginActivity.currentEmail;
import static firenoid.com.istumo3.RegisterActivity.getFilePath;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView textViewName;

    private android.widget.FrameLayout FrameLayout;
    private LinearLayout layoutValues;

    private DatabaseHelper databaseHelper;
    Button fullNameBtn, emailBtn, ageBtn, genderBtn, heiBtn, weiBtn, typeBtn, picBtn;
    User user;
    private static final String DATABASE_NAME = "User.db";
    // User table name
    static final String TABLE_USER = "user";

    private DatabaseHelper.AppDatabaseHelper appDB;
    private SQLiteDatabase db;

    private static Bitmap Image = null;
    private static Bitmap rotateImage = null;

    private static final int GALLERY = 1;


    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_VALUE = "user_value";
    private static final String COLUMN_USER_DATE = "user_date";
    private static final String COLUMN_USER_TIME = "user_time";
    private static final String PERSON_COLUMN_HEI = "user_height";
    private static final String PERSON_COLUMN_WEI = "user_weight";

    EditText Data;
    public static final String PERSON_COLUMN_TYPE = "type";
    public static final String PERSON_COLUMN_GENDER = "gender";
    public static final String PERSON_COLUMN_AGE = "age";
    public static final String PERSON_COLUMN_Picture = "picture";



    public TextView textViewEmail;
    public TextView textViewPassword;

    public TextView age,userUpdate;
    public TextView gender;
    public TextView hei;
    public TextView wei;
    public TextView type;
    public TextView picture;
    private DrawerLayout drawer;
    Button mLogin,button9 ;
    Button mLogin2;
    EditText mEmail;
    TextInputEditText TextInputEditTextUpdate;
    Uri uri;
    private AppCompatRadioButton RadioButtonMale;
    private AppCompatRadioButton RadioButtonFemale;
    private String seekbarString;
    public TextView textInputEditTextType2;


    LinearLayout  LinearLayoutName, LinearLayoutAge, LinearLayoutEmail, LinearLayoutGender,  LinearLayoutPass, LinearLayoutHei, LinearLayoutWei, LinearLayoutType;
    SeekBar Seekbarprofle;
    FrameLayout linearprofileUpdate;
    AlertDialog.Builder mBuilder;
    AlertDialog dialog;
    CircleImageView imageView;
    Toolbar toolbar;
    TextInputLayout textInputLayoutNameProfile;
    private String genderstring;
    public static final String PERSON_TABLE_NAME = "user";
    private FloatingActionButton fab;
    private View view;
    InputMethodManager imm;
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

        LayoutInflater lf = getActivity().getLayoutInflater();
        view =  lf.inflate(R.layout.fragment_profile, container, false); //pass the correct layout name for the fragment






        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }

        button9=(Button)view.findViewById(R.id.button9);
        LinearLayoutName = (LinearLayout) view.findViewById(R.id.LinearLayoutName);
        LinearLayoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        LinearLayoutAge = (LinearLayout) view.findViewById(R.id.LinearLayoutAge);
        LinearLayoutAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        LinearLayoutEmail = (LinearLayout) view.findViewById(R.id.LinearLayoutEmail);
        LinearLayoutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        LinearLayoutGender = (LinearLayout) view.findViewById(R.id.LinearLayoutGender);
        LinearLayoutGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        LinearLayoutPass = (LinearLayout) view.findViewById(R.id.LinearLayoutPass);
        LinearLayoutPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        LinearLayoutHei = (LinearLayout) view.findViewById(R.id.LinearLayoutHei);
        LinearLayoutHei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        LinearLayoutWei = (LinearLayout) view.findViewById(R.id.LinearLayoutWei);
        LinearLayoutWei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        LinearLayoutType = (LinearLayout) view.findViewById(R.id.LinearLayoutType);
        LinearLayoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });





////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////



        user = new User();
        databaseHelper = new DatabaseHelper(getContext());





        imageView=(CircleImageView) view.findViewById(R.id.imageBtnfrag);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageonClick(view);
            }
        });

        MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkPermissions()) {
                ActivityCompat.requestPermissions(getActivity(),permissions, 100);
            } else {


            }
        }
        mLogin = (Button) view.findViewById(R.id.btnprofileYesfrag);


        mLogin2 = (Button) view.findViewById(R.id.btnprofileNofrag);
        mLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            closeDialogdbfrag();
            }
        });
        mEmail = (EditText) view.findViewById(R.id.textInputEditTextFieldfrag);
        /*mEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });*/
        RadioButtonMale= (AppCompatRadioButton)view.findViewById(R.id.radio_piratesprofilefrag);

        RadioButtonFemale= (AppCompatRadioButton)view.findViewById(R.id.radio_ninjasprofilefrag);

        RadioButtonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonMale();
            }
        });
        textInputEditTextType2=view.findViewById(R.id.textInputEditTextTypefrag);

        RadioButtonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonfemale();
            }
        });

        Seekbarprofle = (SeekBar)view.findViewById(R.id.seekBarprofilefrag);
        Seekbarprofle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

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

        linearprofileUpdate= (FrameLayout)view.findViewById(R.id.linearprofileUpdatefrag);
        linearprofileUpdate .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        textInputLayoutNameProfile = (TextInputLayout)view.findViewById(R.id.textInputLayoutNameProfilefrag);

        textViewName = (TextView) view.findViewById(R.id.textViewNamefrag);
        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        textViewEmail = (TextView) view.findViewById(R.id.textViewEmailfrag);
        textViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        textViewPassword = (TextView) view.findViewById(R.id.textViewPasswordfrag);
        textViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });

        age = (TextView) view.findViewById(R.id.Agefrag);
        age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        gender = (TextView) view.findViewById(R.id.Genderfrag);
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        hei = (TextView) view.findViewById(R.id.Heightfrag);
        hei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        wei = (TextView) view.findViewById(R.id.Weightfrag);
        wei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        type = (TextView) view.findViewById(R.id.Typefrag);
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedbfrag(view);
            }
        });
        //   picture = (TextView) view.findViewById(R.id.Picture);
        FrameLayout= (FrameLayout) view.findViewById(R.id.linearUpdatefrag);

        layoutValues = (LinearLayout) view.findViewById(R.id.layoutValuesfrag);
        layoutValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        userUpdate = (TextView) view.findViewById(R.id.userUpdatetextView1frag);




        user = new User();
        //initViews();
        // initObjects();
        //button = view.findViewById<ImageButton> (Resource.Id.myButton);
        databaseHelper = new DatabaseHelper(getActivity());
        //  Data = (EditText) view.findViewById(R.id.Data);
        //    TextInputEditTextUpdate = (TextInputEditText) view.findViewById(R.id.TextInputEditTextUpdate);

        // getBitmap(databaseHelper.getUserPicture(currentEmail));

        String path = databaseHelper.getUserPicture(currentEmail);


        try {
            File f=new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            imageView.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        loadDatabaseValues();
        //  picture.setText(databaseHelper.getColumValue(currentEmail,PERSON_COLUMN_Picture));




/////////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////
        //profile
/////////////////////////////////////////////////////////////////////////////////////////////////
       /* fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showSnackbar(view, "Contact us!", Snackbar.LENGTH_LONG);

            }
        });*/





        drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);












        return view;
       // return inflater.inflate(R.layout.fragment_profile, container, false);


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


/////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
private  boolean checkIfAlreadyhavePermission() {
    int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    return result == PackageManager.PERMISSION_GRANTED;
}




    private void loadDatabaseValues() {
        textViewName.setText(databaseHelper.getColumValue(currentEmail, COLUMN_USER_NAME));
        textViewEmail.setText(databaseHelper.getColumValue(currentEmail, COLUMN_USER_EMAIL));
        textViewPassword.setText(databaseHelper.getColumValue(currentEmail, COLUMN_USER_PASSWORD));

        age.setText(databaseHelper.getColumValue(currentEmail, PERSON_COLUMN_AGE));
        gender.setText(databaseHelper.getColumValue(currentEmail, PERSON_COLUMN_GENDER));
        hei.setText(databaseHelper.getColumValue(currentEmail, PERSON_COLUMN_HEI));
        wei.setText(databaseHelper.getColumValue(currentEmail, PERSON_COLUMN_WEI));
        type.setText(databaseHelper.getColumValue(currentEmail, PERSON_COLUMN_TYPE));
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((AppCompatRadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_piratesprofilefrag:
                if (checked)
                    // Pirates are the best
                    genderstring = getString(R.string.male);
                RadioButtonFemale.setChecked(false);
                break;
            case R.id.radio_ninjasprofilefrag:
                if (checked)
                    // Ninjas rule
                    genderstring = getString(R.string.female);
                RadioButtonMale.setChecked(false);
                break;
        }
    }



    String check;
    String gendertm;

    public void closeDialogdbfrag() {
        mLogin.setVisibility(View.GONE);
        mLogin2.setVisibility(View.GONE);
        imm.hideSoftInputFromWindow(mEmail.getWindowToken(), 0);

        mEmail.setText("");
        //   dialog.dismiss();
        FrameLayout.setVisibility(View.GONE);
        button9.setVisibility(View.VISIBLE);

    }

    public void updatedbfrag(View view) {


        FrameLayout.setVisibility(View.VISIBLE);
        databaseHelper = new DatabaseHelper(getActivity());
        // user = new User();
        mEmail.setText("");
        mBuilder = new AlertDialog.Builder(getActivity());
        mLogin.setVisibility(View.VISIBLE);
        mLogin2.setVisibility(View.VISIBLE);
        RadioButtonFemale.setVisibility(View.GONE);
        RadioButtonMale.setVisibility(View.GONE);
        textInputLayoutNameProfile.setVisibility(View.GONE);
        Seekbarprofle.setVisibility(View.GONE);
        button9.setVisibility(View.GONE);
        linearprofileUpdate.setVisibility(View.GONE);
        mEmail.setFocusableInTouchMode(true);
        mEmail.requestFocus();


//                mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
       check = view.getTag().toString();
       updateTextView(check);
        //  dialog = mBuilder.create();
        mEmail.getSelectionStart();
        mEmail.setSelection(0);
        switch (check) {
            case "COLUMN_USER_NAME":
                textInputLayoutNameProfile.setVisibility(View.VISIBLE);
                mEmail.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);
                break;

            case "COLUMN_USER_EMAIL":
                textInputLayoutNameProfile.setVisibility(View.VISIBLE);
                Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());
                mEmail.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);

                break;

            case "PERSON_COLUMN_AGE":
                textInputLayoutNameProfile.setVisibility(View.VISIBLE);
                Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());
                mEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);
                break;

            case "PERSON_COLUMN_GENDER":
                RadioButtonFemale.setVisibility(View.VISIBLE);
                RadioButtonMale.setVisibility(View.VISIBLE);
                if (genderstring == null) {
                    genderstring = gender.getText().toString();

                }
                mEmail.setText(genderstring);
                imm.hideSoftInputFromWindow(mEmail.getWindowToken(), 0);


                break;

            case "PERSON_COLUMN_HEI":
                textInputLayoutNameProfile.setVisibility(View.VISIBLE);
                mEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);


                break;

            case "PERSON_COLUMN_WEI":
                Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());
                textInputLayoutNameProfile.setVisibility(View.VISIBLE);
                mEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);

                break;


            case "PERSON_COLUMN_TYPE":
                linearprofileUpdate.setVisibility(View.VISIBLE);
                Seekbarprofle.setVisibility(View.VISIBLE);
                Seekbarprofle.setProgress(Integer.valueOf(type.getText().toString()));
                if (seekbarString == null) {
                    seekbarString = type.getText().toString();

                }
                mEmail.setText(seekbarString);
                imm.hideSoftInputFromWindow(mEmail.getWindowToken(), 0);

                Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());


                break;

            case "COLUMN_USER_PASSWORD":
                textInputLayoutNameProfile.setVisibility(View.VISIBLE);
                Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());
                mEmail.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                imm.showSoftInput(mEmail, InputMethodManager.SHOW_IMPLICIT);


                break;

            case   "Cancel":

                closeDialogdbfrag();

                break;

           /* case   "":
                break;

            case   "":
                break;

            case   "":
                break;


            case   "":
                break;



            case   "":
                break;



            case   "":
                break;
*/
        }


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


                if (!mEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            R.string.success_login_msg,
                            Toast.LENGTH_SHORT).show();

                    //   Log.d("email", "check: "+check +"  email value: "+mEmail.getText().toString());
                    switch (check) {
                        case "COLUMN_USER_NAME":
                            textInputLayoutNameProfile.setVisibility(View.VISIBLE);
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());
                            user.setName(mEmail.getText().toString());


                            databaseHelper.updateoneUser(user, check);
                            break;

                        case "COLUMN_USER_EMAIL":
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());

                            user.setEmail(mEmail.getText().toString());

                            databaseHelper.updateoneUser(user, check);
                            break;

                        case "PERSON_COLUMN_AGE":
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());
                            mEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
                            user.setage(mEmail.getText().toString());

                            databaseHelper.updateoneUser(user, check);
                            break;

                        case "PERSON_COLUMN_GENDER":
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());


                            user.setgender(genderstring);

                            databaseHelper.updateoneUser(user, check);

                            break;

                        case "PERSON_COLUMN_HEI":
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());

                            user.sethei(mEmail.getText().toString());

                            databaseHelper.updateoneUser(user, check);
                            break;

                        case "PERSON_COLUMN_WEI":
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());

                            user.setwei(mEmail.getText().toString());

                            databaseHelper.updateoneUser(user, check);
                            break;


                        case "PERSON_COLUMN_TYPE":
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());

                            if (seekbarString == null) {
                                seekbarString = type.getText().toString();

                            }


                            user.settype(seekbarString);
                            mEmail.setText("-");
                            databaseHelper.updateoneUser(user, check);
                            break;

                        case "COLUMN_USER_PASSWORD":
                            Log.d("email", "check: " + check + "  email value: " + mEmail.getText().toString());

                            user.setPassword(mEmail.getText().toString().trim());

                            databaseHelper.updateoneUser(user, check);
                            break;
                    }

                    // getDataFromSQLite();
                    mLogin.setVisibility(View.GONE);
                    mLogin2.setVisibility(View.GONE);
                    mEmail.setText("");
                    //            dialog.dismiss();
                    imm.hideSoftInputFromWindow(mEmail.getWindowToken(), 0);
                    loadDatabaseValues();

                    FrameLayout.setVisibility(View.GONE);
                    button9.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(getActivity(),
                            R.string.error_login_msg,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        //   Log.d("bananaaaaaaaaaa", "updatedb: " + view.getTag().toString());


        //user.setName("Bachir");
        //  user.setName("bachir@aa.com");


        //    view.getTag();

        //   databaseHelper.updateoneUser(user);


    }


    public void updateTextView(String one) {

        switch (one) {
            case "COLUMN_USER_NAME":
                userUpdate.setText(R.string.FullName);
                break;

            case "COLUMN_USER_EMAIL":
                userUpdate.setText(R.string.email);
                break;

            case "PERSON_COLUMN_AGE":
                userUpdate.setText(R.string.age);
                break;

            case "PERSON_COLUMN_GENDER":
                userUpdate.setText(R.string.gender);

                break;

            case "PERSON_COLUMN_HEI":
                userUpdate.setText(R.string.Height);

                break;

            case "PERSON_COLUMN_WEI":
                userUpdate.setText(R.string.Weight);

                break;


            case "PERSON_COLUMN_TYPE":
                userUpdate.setText(R.string.Activity);

                break;

            case "COLUMN_USER_PASSWORD":
                userUpdate.setText(R.string.Password);

                break;
        }


    }


    public void ImageonClick(View v) {
        //  imageView.setImageBitmap(null);
        //   if (Image != null)
        //         Image.recycle();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.SelectPicture)), GALLERY);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY && resultCode != 0) {
            InputStream is = null;
            Uri mImageUri = data.getData();

            try {
                Image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);


                imageView.setImageBitmap(Image);
                // imageView.setImageURI();
                //  Image.get
/*

                                 new ImageSaver(getApplicationContext()).
                                        setFileName("myImage.png").
                                        setDirectoryName("images").
                                        save(bitmap);
*/


                path = getFilePath(getActivity(), mImageUri);


                user.setpicture(path.toString());


                databaseHelper.updateoneUser(user, "PERSON_COLUMN_Picture");

                ///File file = new File(new URI(path));
                //    Log.d("Email", "onCreate: "+path);


                //   Bitmap bitmap = BitmapFactory.decodeStream(is);
                Log.e("imagePath", "onActivityResult: " + path.toString());


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



    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.RECORD_AUDIO,
    };

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getActivity(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
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
    String path="";

    public void onRadioButtonMale() {
        // Is the button now checked?


        // Pirates are the best
        genderstring=getString(R.string.Male);
        RadioButtonFemale.setChecked(false);


    }



    public void onRadioButtonfemale() {
        // Is the button now checked?

        // Ninjas rule
        genderstring=getString(R.string.Female);
        RadioButtonMale.setChecked(false);


        }



}
