package com.example.nicholas.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class TeamSelection extends AppCompatActivity {
    static int tNum = 0;
    static int mNum =0;
    static int idx=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);
        Button selectButton = (Button) findViewById(R.id.button3);
       final TextView tv = (TextView) findViewById(R.id.textView10);
        tv.setVisibility(View.INVISIBLE);
        final TextView tv2 = (TextView) findViewById(R.id.textView5);
        tv2.setVisibility(View.INVISIBLE);
     final   Button yes = (Button) findViewById(R.id.button12);
     final   Button no = (Button) findViewById(R.id.button11);
         yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);

        final EditText teamNumber = (EditText) findViewById(R.id.editText5);
       final EditText matchNumber = (EditText) findViewById(R.id.editText4);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            boolean visible = false;
            if((teamNumber.getText().toString()!="")&&(matchNumber.getText().toString()!="")){
            int t =  Integer.valueOf(teamNumber.getText().toString());
            int m =  Integer.valueOf(matchNumber.getText().toString());
            for(int i = 0; i<MainActivity.e.size();i++){
                if((MainActivity.e.get(i).getMatchNumber()==m)&&(MainActivity.e.get(i).getTeamNumber()==t))
                {
                idx =i;
                tNum = t;
                mNum =m;
                visible = true;
                break;
                }
                else{
                visible = false;
                }
            }
           if(visible==(true)){
            tv.setVisibility(View.VISIBLE);
               tv2.setVisibility(View.VISIBLE);
               yes.setVisibility(View.VISIBLE);
               no.setVisibility(View.VISIBLE);
           }
           else
           {
               Match match = new Match();
               tv.setVisibility(View.INVISIBLE);
               tv2.setVisibility(View.INVISIBLE);
               yes.setVisibility(View.INVISIBLE);
               no.setVisibility(View.INVISIBLE);
               match.setTeamNumber(t);
               match.setMatchNumber(m);
               MainActivity.e.add(match);
               MainActivity.viewInteger=MainActivity.e.size()-1;
               save();
               goToForm();
           }}
          else{

            }


            }
        });



       yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
           Match created = new Match();
           created.setTeamNumber(tNum);
           created.setMatchNumber(mNum);
           MainActivity.e.remove(idx);
           MainActivity.e.add(created);
           MainActivity.viewInteger = MainActivity.e.size()-1;
           save();
          goToForm();
        }});
       no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                tv.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                yes.setVisibility(View.INVISIBLE);
                no.setVisibility(View.INVISIBLE);
            }});

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
    public void goToForm(){ Intent intent = new Intent(this, RobotForm.class);

        startActivity(intent);}
}
