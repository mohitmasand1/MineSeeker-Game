/**
 * @author Mohit Masand
 * @since October 18, 2022
 *
 * States the way to play the game while expressing
 * the theme as much as possible.
 * Contains link to course website and
 * to all images used in the project
 */

package com.example.assignment3.GameUI;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.example.assignment3.R;

import org.w3c.dom.Text;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.help_title);

        TextView course = findViewById(R.id.textViewCourseLink);
        course.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Intent makeIntent(Context context) { return new Intent(context, Help.class); }
}