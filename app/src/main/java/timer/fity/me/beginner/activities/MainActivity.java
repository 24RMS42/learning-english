package timer.fity.me.beginner.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import timer.fity.me.beginner.R;
import timer.fity.me.beginner.controllers.ViewController;
import timer.fity.me.beginner.fragments.LearnFragment;
import timer.fity.me.beginner.fragments.LevelSelectionFragment;
import timer.fity.me.beginner.interfacies.OnPlayCompliteListener;
import timer.fity.me.beginner.utils.AdsHelper;
import timer.fity.me.beginner.utils.DatabaseHelper;
import timer.fity.me.beginner.utils.LogUtils;
import timer.fity.me.beginner.utils.SoundHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.iv_main_background)
    ImageView ivMainBackground;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    DatabaseHelper databaseHelper;

    public static MainActivity Instance;
    String TAG = "LearnEnglish";

    public static boolean wantToExit = false;
    public static AdsHelper adsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPrivacyPolice();

        if(SoundHelper.getInstance() != null){
            SoundHelper.getInstance().stopPlayer();
        }

        ButterKnife.bind(this);
        ViewController.getViewController().setContex(this);
        ViewController.getViewController().setFragmentManager(getSupportFragmentManager());
        initListeneres();
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        findViewById(R.id.iv_back_mainpage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.wantToExit) return;
                onBackPressed();
            }
        });

        adsHelper = new AdsHelper(this.getApplicationContext());
    }

    private void checkPrivacyPolice() {
        File f = new File(this.getFilesDir(), "privacy_police_agree.txt");
        checkAndCopyDataBase();
        if (!f.exists()) {
            Intent intent = new Intent(getApplicationContext(), PrivacyPolicyActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private void checkAndCopyDataBase() {

        databaseHelper = OpenHelperManager.getHelper(
                this.getApplicationContext(),
                DatabaseHelper.class);

        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DATABASE_NAME);
        if (!database.exists()) {
            databaseHelper.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = this.getAssets().open(DatabaseHelper.DATABASE_NAME);
        String outFileName = getApplicationContext().getDatabasePath(DatabaseHelper.DATABASE_NAME).getPath();

        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    private void initListeneres() {
        ivClose.setOnClickListener(this);
    }

    public void learnClick(View view) {
        if (MainActivity.wantToExit) return;
        if (ViewController.getViewController().getFragmentManager().getBackStackEntryCount() == 0) {
            ViewController.getViewController().addFragment(new LearnFragment());
        }
    }

    public void playClick(View view) {
        if (MainActivity.wantToExit) return;
        if (ViewController.getViewController().getFragmentManager().getBackStackEntryCount() == 0) {
            ViewController.getViewController().addFragment(new LevelSelectionFragment());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getActionBar().hide();
        }catch(Exception e) {}
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeApp();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (MainActivity.wantToExit) return;
        if (ViewController.getViewController().getFragmentManager().getBackStackEntryCount() > 0) {
            SoundHelper.getInstance().stopPlayer();
            ViewController.getViewController().getFragmentManager().popBackStack();
            MainActivity.adsHelper.interstitialAdShown = false;
        } else {
            moveTaskToBack(true);
            System.exit(0);
        }
        LogUtils.d("onBackPressed", "onBackPressed");
    }

    public void hideStatusBar(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void closeApp() {
        if (MainActivity.wantToExit) return;
        MainActivity.wantToExit = true;
        SoundHelper.getInstance().playTrack(R.raw.action_sound_quit, new OnPlayCompliteListener() {
            @Override
            public void onComplite() {
                finish();
                System.exit(0);
            }
        });
    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    @Override
    protected void onDestroy() {

        MainActivity.Instance = null;

        super.onDestroy();
    }
}
