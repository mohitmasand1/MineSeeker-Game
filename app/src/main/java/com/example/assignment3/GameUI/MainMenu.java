/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * Main menu consisting of an options button, a help button,
 * and a play game button. Any settings selected in options
 * are changed accordingly in the game.
 */

package com.example.assignment3.GameUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;

import com.example.assignment3.R;

public class MainMenu extends AppCompatActivity {
    private int gameNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3d156d")));
        bar.setTitle("Main Menu");
        gameNumber = 0;
        

        setHelpButton();
        setOptionsButton();
        setPlayGameButton();
    }


    public void setHelpButton() {
        Button menu = findViewById(R.id.getHelpBtn);
        menu.setOnClickListener(view -> {
            Intent help = Help.makeIntent(MainMenu.this);
            startActivity(help);
        });
    }

    public void setOptionsButton() {
        Button menu = findViewById(R.id.optionsBtn);
        menu.setOnClickListener(view -> {
            Intent option = Options.makeIntent(MainMenu.this);
            startActivity(option);
        });
    }

    public void setPlayGameButton() {
        Button menu = findViewById(R.id.playGameBtn);
        menu.setOnClickListener(view -> {
            gameNumber++;
            Intent play = PlayGame.makeIntent(MainMenu.this);
            startActivity(play);
        });
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}
