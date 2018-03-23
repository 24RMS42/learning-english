package timer.fityfor.me.beginner.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import timer.fityfor.me.beginner.R;
import timer.fityfor.me.beginner.activities.MainActivity;
import timer.fityfor.me.beginner.controllers.DataController;
import timer.fityfor.me.beginner.controllers.ViewController;
import timer.fityfor.me.beginner.entities.Game;
import timer.fityfor.me.beginner.interfacies.OnPlayCompliteListener;
import timer.fityfor.me.beginner.utils.SoundHelper;

import static timer.fityfor.me.beginner.interfacies.Constants.LEVELS.EASY;
import static timer.fityfor.me.beginner.interfacies.Constants.LEVELS.HARD;
import static timer.fityfor.me.beginner.interfacies.Constants.LEVELS.MIDDLE;

public class LevelSelectionFragment extends Fragment {

    Unbinder unbinder;
    public LevelSelectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level_selection, container, false);
        SoundHelper.getInstance().stopPlayer();
        unbinder = ButterKnife.bind(this, view);
        view.findViewById(R.id.iv_back_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.wantToExit) return;
                getActivity().onBackPressed();
                MainActivity.adsHelper.interstitialAdShown = false;
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.wantToExit) return;
                MainActivity.wantToExit = true;
                SoundHelper.getInstance().playTrack(R.raw.action_sound_quit, new OnPlayCompliteListener() {
                    @Override
                    public void onComplite() {
                        getActivity().finish();
                        System.exit(0);
                    }
                });
            }
        });

        if (MainActivity.adsHelper.mInterstitialAd.isLoaded()) {
            MainActivity.adsHelper.mInterstitialAd.show();
            MainActivity.adsHelper.interstitialAdShown = true;
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewController.getViewController().setLevelSelectionFragment(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_hard_level, R.id.iv_middle_level, R.id.iv_easy_level})
    public void onViewClicked(View view) {
        if (ViewController.getViewController().getFragmentManager().getBackStackEntryCount() == 1) {
            Game.winPreviousGame = false;
            switch (view.getId()) {
                case R.id.iv_hard_level:
                    if (isOnline()) {
                        DataController.getInstance().setLevel(HARD);
                        ViewController.getViewController().addFragment(new PlayFragment());
                    } else {
                        showInternetAlert();
                    }
                    break;
                case R.id.iv_middle_level:
                    if (isOnline()) {
                        DataController.getInstance().setLevel(MIDDLE);
                        ViewController.getViewController().addFragment(new PlayFragment());
                    } else {
                        showInternetAlert();
                    }
                    break;
                case R.id.iv_easy_level:
                    if (isOnline()) {
                        DataController.getInstance().setLevel(EASY);
                        ViewController.getViewController().addFragment(new PlayFragment());
                    } else {
                        showInternetAlert();
                    }
                    break;
            }
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showInternetAlert() {
//        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
//        alertDialog.setTitle("");
//        alertDialog.setMessage("Check you internet connection to continue");
//        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//        alertDialog.show();
        ((MainActivity)getActivity()).hideStatusBar();
        Context context = ViewController.getViewController().getContex();
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_internet_dialog);

        Button exit = (Button) dialog.findViewById(R.id.okInternetDialog);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
