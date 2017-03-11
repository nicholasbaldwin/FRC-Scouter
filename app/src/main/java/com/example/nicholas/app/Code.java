package com.example.nicholas.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public  class Code extends AppCompatActivity {
    static Match vals = new Match();
    static boolean ba = false;
    static int idx=0;
    static Match valTransfer = new Match();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);


       final EditText et = (EditText) findViewById(R.id.codeVal);
        final EditText teamNumber = (EditText) findViewById(R.id.editText4);
        final EditText matchNumber = (EditText) findViewById(R.id.editText5);
        final TextView t1 = (TextView) findViewById(R.id.textView3) ;
        final TextView t2 = (TextView) findViewById(R.id.textView4) ;
        final Button yes = (Button) findViewById(R.id.button12);
        final Button no = (Button) findViewById(R.id.button11);
        t1.setVisibility(View.INVISIBLE);
        t2.setVisibility(View.INVISIBLE);
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
        et.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            TransferCode t = new TransferCode();
            TransferCode output = t.Decode(et.getText().toString());
          vals = t.toMatch(output);

             matchNumber.setText(Integer.toString(vals.getMatchNumber()));
             teamNumber.setText(Integer.toString(vals.getTeamNumber()))
             ;
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }



        });
        Button submitButton = (Button)findViewById(R.id.button10);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             valTransfer = vals;
            if((!teamNumber.getText().toString().equals(""))&&(!matchNumber.getText().toString().equals("")))    {
           ba =true;
            for(int i =0; i<MainActivity.e.size();i++) {
                int a = MainActivity.e.get(i).getMatchNumber();
                int b = MainActivity.e.get(i).getTeamNumber();
                int c = vals.getMatchNumber();
                int d = vals.getTeamNumber();
                 idx = i;
                if ((a == c) && (b == d)) {
                    t1.setVisibility(View.VISIBLE);
                    t2.setVisibility(View.VISIBLE);
                    yes.setVisibility(View.VISIBLE);
                    no.setVisibility(View.VISIBLE);
                    ba=false;
                    break;
                } else {
                    t1.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    yes.setVisibility(View.INVISIBLE);
                    no.setVisibility(View.INVISIBLE);
                   ba = true;
                }
            }

             if(ba==true){
              MainActivity.e.add(vals);
                 MainActivity.viewInteger = MainActivity.e.size()-1;
                 goToThirdActivity();
                 save();
             }
            }

            }}
        );

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {  t1.setVisibility(View.INVISIBLE);
                t2.setVisibility(View.INVISIBLE);
                yes.setVisibility(View.INVISIBLE);
                no.setVisibility(View.INVISIBLE);}});
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { MainActivity.e.remove(idx);
            MainActivity.e.add(valTransfer)
            ;
            MainActivity.viewInteger = MainActivity.e.size()-1;
            goToThirdActivity();
            }

        });

    }
    private void goToThirdActivity() {
        Intent intent = new Intent(this, RobotForm.class);

        startActivity(intent);

    }
    public void save() {
        String json = new Gson().toJson(MainActivity.e);
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(MainActivity.PREFS_KEY, json); //3
        editor.commit(); //4
    }
}
