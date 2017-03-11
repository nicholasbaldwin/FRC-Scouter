package com.example.nicholas.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.google.gson.Gson;

public class teleop extends AppCompatActivity {
     int highCycles =  MainActivity.e.get(MainActivity.viewInteger).getTHighCycles();
     int lowCycles =  MainActivity.e.get(MainActivity.viewInteger).getTLowCycles();
    int gFail =  MainActivity.e.get(MainActivity.viewInteger).getTGearFail();
    int gSuccess = MainActivity.e.get(MainActivity.viewInteger).getTGearSuccess();
    int highGoalScoreIndex = MainActivity.e.get(MainActivity.viewInteger).getTHighGoalScore();
    int lowGoalScoreIndex = MainActivity.e.get(MainActivity.viewInteger).getTLowGoalScore();
    boolean fuelCollectionGround =  MainActivity.e.get(MainActivity.viewInteger).getFuelCollectionGround();
    boolean fuelCollectionHopper =  MainActivity.e.get(MainActivity.viewInteger).getFuelCollectionHopper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop);
        Button gFailAdd = (Button) findViewById(R.id.gFailAdd);
        Button gFailSub = (Button) findViewById(R.id.gFailSub);
       final CheckBox fcg = (CheckBox) findViewById(R.id.checkBox3);
       final CheckBox fch = (CheckBox) findViewById(R.id.checkBox2);
        fcg.setChecked(fuelCollectionGround);
        fch.setChecked(fuelCollectionHopper);
        fcg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(fcg.isChecked()){
                    fuelCollectionGround = true;
                    MainActivity.e.get(MainActivity.viewInteger).setFuelCollectionGround(fuelCollectionGround);
                    save();
                }else{
                    fuelCollectionGround= false;
                    MainActivity.e.get(MainActivity.viewInteger).setFuelCollectionGround(fuelCollectionGround);
                    save();
                }
            }
        });

        fch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(fch.isChecked()){
                    fuelCollectionHopper = true;
                    MainActivity.e.get(MainActivity.viewInteger).setFuelCollectionHopper(fuelCollectionHopper);
                    save();
                }else{
                    fuelCollectionHopper = false;
                    MainActivity.e.get(MainActivity.viewInteger).setFuelCollectionHopper(fuelCollectionHopper);
                    save();
                }
            }
        });
        /*GEAR FAILURE CODE*/
        final EditText gFailHolder = (EditText) findViewById(R.id.editText4);
        gFailHolder.setText(Integer.toString(gFail));
        gFailAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gFail++;
                if(gFail>15){gFail=15;}
                gFailHolder.setText(Integer.toString(gFail));
                MainActivity.e.get(MainActivity.viewInteger).setTGearFail(gFail);
                save();
            }
        });
        gFailSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gFail--;
                if(gFail<0){gFail=0;}
                gFailHolder.setText(Integer.toString(gFail));
                MainActivity.e.get(MainActivity.viewInteger).setTGearFail(gFail);
                save();
            }
        });
        Button gSuccessAdd = (Button) findViewById(R.id.gSuccessAdd);
        Button gSuccessSub = (Button) findViewById(R.id.gSuccessSub);
        /*GEAR SUCCESS CODE*/
        final EditText gSuccessHolder = (EditText) findViewById(R.id.editText3);
        gSuccessHolder.setText(Integer.toString(gSuccess));
        gSuccessAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gSuccess++;
                if(gSuccess>15){gSuccess=15;}
                gSuccessHolder.setText(Integer.toString(gSuccess));
                MainActivity.e.get(MainActivity.viewInteger).setTGearSuccess(gSuccess);
                save();
            }
        });
        gSuccessSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gSuccess--;
                if(gSuccess<0){gSuccess=0;}
                gSuccessHolder.setText(Integer.toString(gSuccess));
                MainActivity.e.get(MainActivity.viewInteger).setTGearSuccess(gSuccess);
                save();
            }
        });


        //HIGH CYCLES
        final Button highCyclesAdd = (Button) findViewById(R.id.button4);
        Button highCyclesSub = (Button) findViewById(R.id.button5);

        final EditText highCyclesHolder = (EditText) findViewById(R.id.editText6);
        highCyclesHolder.setText(Integer.toString(highCycles));
        highCyclesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highCycles++;
                if(highCycles>15){highCycles=15;}
                highCyclesHolder.setText(Integer.toString(highCycles));
                MainActivity.e.get(MainActivity.viewInteger).setTHighCycles(highCycles);
                save();
            }
        });
        highCyclesSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highCycles--;
                if(highCycles<0){highCycles=0;}
                highCyclesHolder.setText(Integer.toString(highCycles));
                MainActivity.e.get(MainActivity.viewInteger).setTHighCycles(highCycles);
                save();
            }
        });
        //LOW CYCLES
        Button lowCyclesAdd = (Button) findViewById(R.id.button6);
        Button lowCyclesSub = (Button) findViewById(R.id.button8);

        final EditText lowCyclesHolder = (EditText) findViewById(R.id.editText2);
        lowCyclesHolder.setText(Integer.toString(lowCycles));
        lowCyclesAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowCycles++;
                if(lowCycles>15){lowCycles=15;}
                lowCyclesHolder.setText(Integer.toString(lowCycles));
                MainActivity.e.get(MainActivity.viewInteger).setTLowCycles(lowCycles);
                save();
            }
        });
        lowCyclesSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lowCycles--;
                if(lowCycles<0){lowCycles=0;}
                lowCyclesHolder.setText(Integer.toString(lowCycles));
                MainActivity.e.get(MainActivity.viewInteger).setTLowCycles(lowCycles);
                save();
            }
        });

        Context context=getApplicationContext();
        String[] array = context.getResources().getStringArray(R.array.arrays);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,array
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        Spinner lowGoalScore = (Spinner) findViewById(R.id.spinner);
        lowGoalScore.setAdapter(spinnerArrayAdapter);
        lowGoalScore.setSelection(lowGoalScoreIndex);
        lowGoalScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.e.get(MainActivity.viewInteger).setTLowGoalScore(i);
                save();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        Spinner highGoalScore = (Spinner) findViewById(R.id.spinner2);
        highGoalScore.setAdapter(spinnerArrayAdapter);
        highGoalScore.setSelection(highGoalScoreIndex);
        highGoalScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.e.get(MainActivity.viewInteger).setTHighGoalScore(i);
                save();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        final Button bt = (Button) findViewById(R.id.button2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             goToFinalActivity();
            }
        });
    }
    public void goToFinalActivity()
    {
        Intent intent = new Intent(this, EndGame.class);

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
