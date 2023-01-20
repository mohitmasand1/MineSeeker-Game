/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * UI of the game itself. Each click on the button results
 * in a check of the status of the boardelement clicked which
 * then reveals either a mine or scan information accordingly.
 * If element is already scanned, nothing happens
 */

package com.example.assignment3.GameUI;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.Game.Board;
import com.example.assignment3.R;

import org.w3c.dom.Text;

public class PlayGame extends AppCompatActivity {

    private static int ROWS;
    private static int COLS;
    private static int MINES;
    Board board;

    Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3d156d")));
        bar.setTitle(R.string.game_title);

        board = Board.getInstance();
        initializeBoard();
        setCandyAndScansText();
        populateButtons();
    }


    public void initializeBoard() {
        ROWS = Options.getBoardWidthChosen(this);
        COLS = Options.getBoardLengthChosen(this);
        MINES = Options.getNumMinesChosen(this);
        board.setUpBoard(ROWS, COLS, MINES);
        buttons = new Button[ROWS][COLS];
    }

    @SuppressLint("SetTextI18n")
    public void setCandyAndScansText() {
        TextView candies = findViewById(R.id.textViewMinesFound);
        candies.setText(board.minesFound() + " of " + board.getNumMines() + getString(R.string.candies_found_counter));
        TextView scanned = findViewById(R.id.textViewScansUsed);
        scanned.setText(getString(R.string.scans_used_counter) + board.getUsedScans());
    }

    public static Intent makeIntent(Context context) { return new Intent(context, PlayGame.class); }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForbuttons);
        final long[] scanVibPat = {0,500,200,500,200,500};
        final long[] mineVibPat = {0,200,500,200,500,200};
        Vibrator mineVib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        Vibrator scanVib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final MediaPlayer Scanned = MediaPlayer.create(this,R.raw.click_sound);
        final MediaPlayer FoundMine = MediaPlayer.create(this,R.raw.mine_click_sound);

        for (int row = 0; row < ROWS; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for (int col = 0; col < COLS; col++) {
                final int FINAL_ROW = row;
                final int FINAL_COL = col;
                Button button = new Button(this);
                button.getBackground().setAlpha(90);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                // make text not clip on small buttons
                button.setPadding(0,0,0,0);

                button.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        lockButtonSizes();
                        switch (board.board[FINAL_ROW][FINAL_COL].getStatus()) {
                            case 0: case 2:
                                board.spotSearched(FINAL_ROW, FINAL_COL);
                                button.setText("" + board.board[FINAL_ROW][FINAL_COL].getValue());

                                Scanned.start();
                                scanVib.vibrate(VibrationEffect.createWaveform(scanVibPat,-1));
                                break;
                            case 1:

                                FoundMine.start();
                                mineVib.vibrate(VibrationEffect.createWaveform(mineVibPat,-1));

                                MineClicked(FINAL_ROW, FINAL_COL);
                                board.mineFound(FINAL_ROW, FINAL_COL);
                                break;
                        }
                        updateUIState();
                        // update ui
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void MineClicked(int row, int col) {
        Button button = buttons[row][col];
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();

        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.candy);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resources = getResources();
        button.setBackground(new BitmapDrawable(resources, scaledBitmap));
    }

    private void lockButtonSizes() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Button button = buttons[row][col];

                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
    // update scores
    @SuppressLint("SetTextI18n")
    private void updateUIState() {
        setCandyAndScansText();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board.board[i][j].getStatus() == -1) {
                    buttons[i][j].setText("" + board.board[i][j].getValue());
                }
            }
        }

        if (board.checkAllMinesFound()) {
            FragmentManager manager = getSupportFragmentManager();
            DialogFragment dialog = new DialogFragment();
            dialog.setCancelable(false);
            dialog.show(manager, getString(R.string.dialog_tag));
        }
    }

}