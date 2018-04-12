package timer.fity.me.beginner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

import timer.fity.me.beginner.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        findViewById(R.id.btn_agree_privacy_policy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f1 = new File(getApplicationContext().getFilesDir(), "/images/");
                if (!f1.exists()) {
                    f1.mkdirs();
                }
                File f2 = new File(getApplicationContext().getFilesDir(), "/voices/");
                if (!f2.exists()) {
                    f2.mkdirs();
                }
                File file = new File(getApplicationContext().getFilesDir(), "privacy_police_agree.txt");
                if(!file.exists()){
                    file.mkdir();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
