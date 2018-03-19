package firenoid.com.istumo3.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaActionSound;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import firenoid.com.istumo3.R;

import static android.os.Build.VERSION.SDK_INT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    Button button3, button4;
    Boolean soundmuted = false;
    Vibrator v;
    ToneGenerator tg;
    MediaActionSound sound;

    public static Boolean lastSoundselection=false;
    public static Boolean  lastNotificationSettingSelected=false;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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


        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        soundOn = view.findViewById(R.id.toggleButtonnsoundOn);


        soundOFF = view.findViewById(R.id.toggleButtonnsoundOff);


        v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);


        notifOff = view.findViewById(R.id.toggleButtonnotificationOff);


        notiOn = view.findViewById(R.id.toggleButtonnotificationOn);




        button3 = view.findViewById(R.id.buttonnotification);


        final Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);


/*
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shownotification();
            }
        });*/


        if(lastSoundselection){
            soundOn.setBackgroundResource(R.mipmap.iconcheck35);
            soundOFF.setBackgroundResource(R.mipmap.iconclose25);
        }

        else if(!lastSoundselection){
            soundOn.setBackgroundResource(R.mipmap.iconcheck3);
            soundOFF.setBackgroundResource(R.mipmap.iconclose3);
        }


         if(lastNotificationSettingSelected){
            notiOn.setBackgroundResource(R.mipmap.iconcheck35);
            notifOff.setBackgroundResource(R.mipmap.iconclose25);
        }

        else if(!lastNotificationSettingSelected){
            notiOn.setBackgroundResource(R.mipmap.iconcheck3);
            notifOff.setBackgroundResource(R.mipmap.iconclose3);
        }




        //////////////////////////
        ///////////////////////////////////
        soundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundOn.setBackgroundResource(R.mipmap.iconcheck3);
                soundOFF.setBackgroundResource(R.mipmap.iconclose3);
                UnMuteAudio();
                lastSoundselection = false;
            }
        });

        soundOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundOn.setBackgroundResource(R.mipmap.iconcheck35);
                soundOFF.setBackgroundResource(R.mipmap.iconclose25);
                MuteAudio();
                lastSoundselection = true;
            }
        });


        /////////////////////
        //////////////////////////////////
        notiOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notiOn.setBackgroundResource(R.mipmap.iconcheck3);
                notifOff.setBackgroundResource(R.mipmap.iconclose3);
                lastNotificationSettingSelected = false;

            }
        });

        notifOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notiOn.setBackgroundResource(R.mipmap.iconcheck35);
                notifOff.setBackgroundResource(R.mipmap.iconclose25);
                lastNotificationSettingSelected = true;
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


    public void MuteAudio() {
        soundmuted = true;
        v.vibrate(500);

        soundOn.setTextColor(getResources().getColor(R.color.red));
        AudioManager mAlramMAnager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        if (SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
        } else {

            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }
    }

    public void UnMuteAudio() {
        soundOn.setTextColor(getResources().getColor(R.color.greenreal));
        soundmuted = false;
        // sound.play(MediaActionSound.);
        AudioManager mAlramMAnager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        if (SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);


        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
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

    Button soundOn;
    Button soundOFF;

    Button notifOff;
    Button notiOn;


    public void shownotification() {


        if (soundOn.getVisibility() != View.VISIBLE) {

            soundOn.setVisibility(View.VISIBLE);
            soundOFF.setVisibility(View.VISIBLE);
        } else {

            soundOn.setVisibility(View.GONE);
            soundOFF.setVisibility(View.GONE);

        }
    }


}
