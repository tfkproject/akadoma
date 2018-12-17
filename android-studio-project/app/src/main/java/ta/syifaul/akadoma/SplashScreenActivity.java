package ta.syifaul.akadoma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

import ta.syifaul.akadoma.util.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //cek login
        session = new SessionManager(SplashScreenActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                session.checkLogin();

                if(!session.isLoggedIn()){
                    finish();
                }

                //ambil data user
                HashMap<String, String> user = session.getUserDetails();
                String level_ = user.get(SessionManager.KEY_LEVEL);

                if(level_ == null){
                    level_ = "";
                }
                else{
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    intent.putExtra("level", level_); //akan di pake di fragment jadwal dan pengumuman
                    startActivity(intent);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }

}
