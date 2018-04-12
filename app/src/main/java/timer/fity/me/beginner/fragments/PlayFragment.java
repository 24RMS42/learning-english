package timer.fity.me.beginner.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timer.fity.me.beginner.R;
import timer.fity.me.beginner.controllers.ViewController;
import timer.fity.me.beginner.entities.Game;
import timer.fity.me.beginner.interfacies.OnPlayCompliteListener;
import timer.fity.me.beginner.utils.SoundHelper;

public class PlayFragment extends Fragment implements RewardedVideoAdListener{

    @BindView(R.id.iv_background_play)
    ImageView ivBackgroundPlay;
    Unbinder unbinder;
    @BindView(R.id.game)
    Game game;

    public static RewardedVideoAd mRewardedVideoAd;

    public PlayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this.getActivity());
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

        View view = inflater.inflate(R.layout.fragment_play, container, false);
        view.findViewById(R.id.iv_back_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundHelper.getInstance().playTrack(R.raw.action_sound_quit, new OnPlayCompliteListener() {
                    @Override
                    public void onComplite() {
                        getActivity().finish();
                        System.exit(0);
                    }
                });
            }
        });
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewController.getViewController().setPlayFragment(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd("ca-app-pub-1368614258949399/9613824278",
                    new AdRequest.Builder().build());
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
     //   Toast.makeText(this.getActivity(), "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
      //  Toast.makeText(this.getActivity(), "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
     //   Toast.makeText(this.getActivity(), "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //Game.Instance.resetGame();
     //   Toast.makeText(this.getActivity(), "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
        if (Game.Instance.gameFinished) {
            Game.Instance.resetGame();
        } else {
            Game.Instance.logicOfGame();
        }
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
     //   Toast.makeText(this.getActivity(), "onRewarded! currency: " + rewardItem.getType() + "  amount: " +
     //           rewardItem.getAmount(), Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
        if (Game.Instance.gameFinished) {
            Game.Instance.resetGame();
        }
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
     //   Toast.makeText(this.getActivity(), "onRewardedVideoAdLeftApplication",
     //           Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
     //   Toast.makeText(this.getActivity(), "onRewardedVideoAdFailedToLoad" + i, Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }
}
