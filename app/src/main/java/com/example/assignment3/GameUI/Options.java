/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * Options menu containing two radio button lists of
 * board sizes and number of mines hidden in the board
 * passes these values to PlayGame accordingly using
 * SharePreferences
 */

package com.example.assignment3.GameUI;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.assignment3.R;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.options_title);

        createBoardSizeButtons();
        createNumberMinesButtons();
    }

    public static Intent makeIntent(Context context) { return new Intent(context, Options.class); }

    @SuppressLint("SetTextI18n")
    private void createBoardSizeButtons() {
        RadioGroup buttons = findViewById(R.id.group_board_size_options);

        int[] board_size = getResources().getIntArray(R.array.size_of_board);

        for (int i = 0; i < board_size.length; i+=2) {
            final int width = board_size[i];
            final int length = board_size[i+1];

            RadioButton size = new RadioButton(this);
            size.setText(width + " rows by " + length + " columns");

            size.setOnClickListener(view -> {
                saveBoardSizeChosen(width, length);
            });

            // Add to radio group
            buttons.addView(size);

            // Select default button
            if (width == getBoardWidthChosen(this)
                    && length == getBoardLengthChosen(this)) {
                size.setChecked(true);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void createNumberMinesButtons() {
        RadioGroup buttons = findViewById(R.id.group_number_mine_options);

        int[] num_mines = getResources().getIntArray(R.array.number_of_mines);

        for (int i = 0; i < num_mines.length; i++) {
            final int mines = num_mines[i];

            RadioButton mine = new RadioButton(this);
            mine.setText(mines + " " + getString(R.string.mines_counter));

            mine.setOnClickListener(view -> {
                saveNumMinesChosen(mines);
            });

            // Add to radio group
            buttons.addView(mine);

            if (mines == getNumMinesChosen(this)) {
                mine.setChecked(true);
            }
        }
    }

    private void saveBoardSizeChosen(int width, int length) {
        SharedPreferences prefs = this.getSharedPreferences("SizePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Rows of Board", width);
        editor.putInt("Cols of Board", length);
        editor.apply();
    }
    static public int getBoardWidthChosen(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("SizePrefs", MODE_PRIVATE);
        return prefs.getInt("Rows of Board", 0);
    }

    static public int getBoardLengthChosen(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("SizePrefs", MODE_PRIVATE);
        return prefs.getInt("Cols of Board", 0);
    }

    private void saveNumMinesChosen(int mines) {
        SharedPreferences prefs = this.getSharedPreferences("NumMinePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("Mines on Board", mines);
        editor.apply();
    }

    static public int getNumMinesChosen(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("NumMinePrefs", MODE_PRIVATE);
        return prefs.getInt("Mines on Board", 0);
    }

}