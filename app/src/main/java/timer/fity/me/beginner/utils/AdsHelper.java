package timer.fity.me.beginner.utils;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import timer.fity.me.beginner.entities.Game;

/**
 * Created by Dmitry on 3/10/2018.
 */

public class AdsHelper {
    Context mContext;

    public InterstitialAd mInterstitialAd;
    public boolean interstitialAdShown = false;
    public boolean inGame = false;

    AdListener adListener = new AdListener() {
        @Override
        public void onAdLoaded() {
            // Code to be executed when an ad finishes loading.
            //  Toast.makeText(PlayFragment.this.getActivity(), "onInterstitialAdLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            // Code to be executed when an ad request fails.
            //   Toast.makeText(PlayFragment.this.getActivity(), "onInterstitialAdFailedToLoaded", Toast.LENGTH_SHORT).show();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }

        @Override
        public void onAdOpened() {
            // Code to be executed when the ad is displayed.
            //   Toast.makeText(PlayFragment.this.getActivity(), "onInterstitialAdOpened", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdLeftApplication() {
            // Code to be executed when the user has left the app.
            //  Toast.makeText(PlayFragment.this.getActivity(), "onInterstitialAdLeft", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdClosed() {
            // Code to be executed when when the interstitial ad is closed.
            //   Toast.makeText(PlayFragment.this.getActivity(), "onInterstitialAdClosed", Toast.LENGTH_SHORT).show();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            if (inGame) {
                Game.Instance.logicOfGame();
            }
        }
    };

    public AdsHelper(Context context) {
        MobileAds.initialize(context, "ca-app-pub-1368614258949399~6154147336");
        mContext = context;
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-1368614258949399/6309483012");
        mInterstitialAd.setAdListener(adListener);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
