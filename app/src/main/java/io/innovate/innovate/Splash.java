package io.innovate.innovate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends Activity {


        public static final String SPLASH_SCREEN_OPTION = "io.nottynerd.innovate.Splash";
        public static final String SPLASH_SCREEN_OPTION_1 = "Option 1";
        public static final String SPLASH_SCREEN_OPTION_2 = "Option 2";
        public static final String SPLASH_SCREEN_OPTION_3 = "Option 3";


        private ImageView mLogo;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().requestFeature(Window.FEATURE_NO_TITLE); //Removing ActionBar
            setContentView(R.layout.activity_splash);

            mLogo = (ImageView) findViewById(R.id.icon);

            String category = SPLASH_SCREEN_OPTION_1;
            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(SPLASH_SCREEN_OPTION)) {
                category = extras.getString(SPLASH_SCREEN_OPTION, SPLASH_SCREEN_OPTION_1);
            }
            setAnimation(category);
            startEvent();

        }

        /** Animation depends on category.
         * */
        private void setAnimation(String category) {
            if (category.equals(SPLASH_SCREEN_OPTION_1)) {
                animation1();
            } else if (category.equals(SPLASH_SCREEN_OPTION_2)) {
                animation2();
            } else if (category.equals(SPLASH_SCREEN_OPTION_3)) {
                animation2();
            }
        }

        private void animation1() {
            ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
            scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleXAnimation.setDuration(1200);
            ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
            scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleYAnimation.setDuration(1200);
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
            alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            alphaAnimation.setDuration(1200);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
            animatorSet.setStartDelay(500);
            animatorSet.start();
        }

        private void animation2() {
            mLogo.setAlpha(1.0F);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
            mLogo.startAnimation(anim);
        }


    private void startEvent()
    {

        Thread timer=new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(5000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent openStartingPoint;
                    String regDet = "";

                    openStartingPoint=new Intent(getApplicationContext(),MainActivity.class);

                    startActivity(openStartingPoint);
                    overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                }
            }

        };
        timer.start();


    }

}
