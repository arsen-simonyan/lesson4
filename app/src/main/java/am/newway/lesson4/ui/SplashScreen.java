package am.newway.lesson4.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import am.newway.lesson4.R;
import am.newway.lesson4.ui.login.LoginActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class SplashScreen extends AppCompatActivity
{
    @Override protected void onCreate( @Nullable Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splashscreen );

        final View img = findViewById(R.id.imageView);
        SpringAnimation springAnimation
                = new SpringAnimation(img, DynamicAnimation.X);

        SpringForce springForce = new SpringForce();
        springForce.setFinalPosition(img.getX());
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);

        springAnimation.setSpring(springForce);

        springAnimation.setStartVelocity(30000f);
        springAnimation.start();

        springAnimation.addEndListener( new DynamicAnimation.OnAnimationEndListener() {
            @Override public void onAnimationEnd( DynamicAnimation animation , boolean canceled , float value , float velocity )
            {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class)) ;
                finish();
            }
        } );
    }
}
