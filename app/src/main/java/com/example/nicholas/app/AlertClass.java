package com.example.nicholas.app;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;


public class AlertClass extends DialogFragment {

 Context c;

    public void setContext(Context c){
    this.c = c;
    }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialogTheme);
            builder.setTitle("Delete Match?")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                         MainActivity.e.remove(MainActivity.viewInteger);
                         MainActivity.viewInteger = MainActivity.e.size()-1;
                            save();
                            Intent intent = new Intent(c,
                                    MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                         dialog.cancel();
                        }
                    });

            AlertDialog g = builder.create();
            g.show();
            g.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(c,R.color.colorAccent));
           g.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(c,R.color.colorAccent));
            return g;
        }

    public void save() {
        String json = new Gson().toJson(MainActivity.e);
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = c.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(MainActivity.PREFS_KEY, json); //3
        editor.commit(); //4
    }
    }



