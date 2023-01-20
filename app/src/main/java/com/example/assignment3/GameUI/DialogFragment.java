/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * Dialog Fragment to display congratulations message once
 * the user wins the game
 */

package com.example.assignment3.GameUI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.GameManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.assignment3.Game.Board;
import com.example.assignment3.R;

public class DialogFragment extends AppCompatDialogFragment {

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // create the view to show
        Board board = Board.getInstance();

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.message_layout, null);
        TextView view = v.findViewById(R.id.textviewDialogScanned);
        view.setText("Scans Used: " + board.getUsedScans());
        // create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getActivity().finish();
            }
        };
        // build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }
}