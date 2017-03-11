package com.example.nicholas.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.gson.Gson;

import java.util.Collections;



public  class RobotForm extends AppCompatActivity {

    int gFail;
    int gSuccess;
    int highGoalScoreIndex;
    int lowGoalScoreIndex;
    boolean baseline;
    boolean hopper;


    String[] highAccuracyArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_form);

        Match m = MainActivity.e.get(MainActivity.viewInteger);
        MainActivity.e.remove(MainActivity.viewInteger);
        MainActivity.e.add(m);
        MainActivity.viewInteger = MainActivity.e.size()-1;
        save();
        gFail = MainActivity.e.get(MainActivity.viewInteger).getGearFailNbr();
         gSuccess = MainActivity.e.get(MainActivity.viewInteger).getGearSuccessNbr();
         highGoalScoreIndex = MainActivity.e.get(MainActivity.viewInteger).getHighGoalScore();
         lowGoalScoreIndex = MainActivity.e.get(MainActivity.viewInteger).getLowGoalScore();
       baseline =  MainActivity.e.get(MainActivity.viewInteger).getAutonomousBaseline();
        hopper =MainActivity.e.get(MainActivity.viewInteger).getAutonomousHopper();
        //Checkboxes
      final CheckBox baselineBox = (CheckBox) findViewById(R.id.checkBox3);
        final CheckBox hopperBox = (CheckBox) findViewById(R.id.checkBox2);
        baselineBox.setChecked(baseline);



        baselineBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(baselineBox.isChecked()){
                    baseline = true;
                    MainActivity.e.get(MainActivity.viewInteger).setAutonomousBaseline(baseline);
                    save();
                }else{
                    baseline = false;
                    MainActivity.e.get(MainActivity.viewInteger).setAutonomousBaseline(baseline);
                    save();
                }
            }
        });
        hopperBox.setChecked(hopper);
        hopperBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(hopperBox.isChecked()){
                    hopper = true;
                    MainActivity.e.get(MainActivity.viewInteger).setAutonomousHopper(hopper);
                    save();
                }else{
                    hopper = false;
                    MainActivity.e.get(MainActivity.viewInteger).setAutonomousHopper(hopper);
                    save();
                }
            }
        });
        final Button gFailAdd = (Button) findViewById(R.id.gFailAdd);
        Button gFailSub = (Button) findViewById(R.id.gFailSub);







        /*GEAR FAILURE CODE*/
        final EditText gFailHolder = (EditText) findViewById(R.id.editText4);
        gFailHolder.setText(Integer.toString(gFail));
        gFailAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gFail++;
                    if(gFail>1){gFail=1;}
                    gFailHolder.setText(Integer.toString(gFail));
                    MainActivity.e.get(MainActivity.viewInteger).setGearFailNbr(gFail);
                    save();
                }
        });
        gFailSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gFail--;
                if(gFail<0){gFail=0;}
                gFailHolder.setText(Integer.toString(gFail));
                MainActivity.e.get(MainActivity.viewInteger).setGearFailNbr(gFail);
                save();
            }
        });
        /*GEAR SUCCESS CODE*/
        Button gSucessAdd = (Button) findViewById(R.id.gSuccessAdd);
        Button gSuccessSub = (Button) findViewById(R.id.gSuccessSub);
        final EditText gSuccessHolder = (EditText) findViewById(R.id.editText3);
        gSuccessHolder.setText(Integer.toString(gSuccess));
        gSucessAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gSuccess++;
                if(gSuccess>3){gSuccess=3;}
                gSuccessHolder.setText(Integer.toString(gSuccess));
                MainActivity.e.get(MainActivity.viewInteger).setGearSuccessNbr(gSuccess);
                save();
            }
        });
        gSuccessSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gSuccess--;
                if(gSuccess<0){gSuccess=0;}
                gSuccessHolder.setText(Integer.toString(gSuccess));
                MainActivity.e.get(MainActivity.viewInteger).setGearSuccessNbr(gSuccess);
                save();
            }
        });



        Context context=getApplicationContext();
        String[] array = context.getResources().getStringArray(R.array.arrays);

      Spinner highGoalScore = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,array
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
      highGoalScore.setAdapter(spinnerArrayAdapter);
        highGoalScore.setSelection(highGoalScoreIndex);
        highGoalScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.e.get(MainActivity.viewInteger).setHighGoalScore(i);
                save();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        Spinner lowGoalScore = (Spinner) findViewById(R.id.spinner);
        lowGoalScore.setAdapter(spinnerArrayAdapter);
        lowGoalScore.setSelection(lowGoalScoreIndex);
        lowGoalScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.e.get(MainActivity.viewInteger).setLowGoalScore(i);
                save();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSecondActivity();
            }
        });
    }
    public void goBackActivity(){  Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);}
    public void save() {
        String json = new Gson().toJson(MainActivity.e);
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2
        editor.putString(MainActivity.PREFS_KEY, json); //3
        editor.commit(); //4
    }

    public void goToSecondActivity()
    {
        Intent intent = new Intent(this, teleop.class);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_cart:

                AlertClass ac = new AlertClass();
                ac.setContext(getApplicationContext());
               ac.show( getFragmentManager(),"TAG");

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
