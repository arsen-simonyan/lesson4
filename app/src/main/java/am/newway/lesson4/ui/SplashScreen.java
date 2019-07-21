package am.newway.lesson4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import am.newway.lesson4.R;
import am.newway.lesson4.ui.login.LoginActivity;

public class SplashScreen extends AppCompatActivity
{
    @Override protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splashscreen );

        Timer timer = new Timer();
        TimerTask task = new TimerTask(  ){
            @Override public void run()
            {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class)) ;
                finish();
            }
        };
        timer.schedule( task, 3000 );
    }
}
