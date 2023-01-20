/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * The main initial page of the application containing
 * an animation of fade in and rotate with an option
 * to skip the animation. If animation is not skipped
 * automatically advances to the main menu after 4 seconds
 */

package com.example.assignment3.GameUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.assignment3.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Welcome");

        ImageView welcome = findViewById(R.id.imageViewWelcomeCandy);
        setMenuButton();
        Animation alphaAnim = AnimationUtils.loadAnimation(this, R.anim.welcome_anim);
        Animation rotateAnim = AnimationUtils.loadAnimation(this, R.anim.welcome_rotation);

        final AnimationSet anims = new AnimationSet(false);
        anims.addAnimation(alphaAnim);
        anims.addAnimation(rotateAnim);
        welcome.startAnimation(anims);

        anims.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(intent);
                    }
                }, 4000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void setMenuButton() {
        Button menu = findViewById(R.id.mainMenu);
        menu.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainMenu.class);
            startActivity(intent);
        });
    }

}