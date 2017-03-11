package com.example.nicholas.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class EndGame extends AppCompatActivity {
boolean climb = MainActivity.e.get(MainActivity.viewInteger).getDidClimb();
boolean successful =  MainActivity.e.get(MainActivity.viewInteger).getClimbSuccessful();
boolean didNotFunction = MainActivity.e.get(MainActivity.viewInteger).getDidFunction();
boolean stopFunction= MainActivity.e.get(MainActivity.viewInteger).getStopFunction();
boolean defensive =  MainActivity.e.get(MainActivity.viewInteger).getDefensive();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
       final CheckBox climbBox = (CheckBox) findViewById(R.id.checkBox2);
        climbBox.setChecked(climb);
        climbBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(climbBox.isChecked()){
                    climb = true;
                    MainActivity.e.get(MainActivity.viewInteger).setDidClimb(climb);
                    save();
                }else{
                    climb = false;
                    MainActivity.e.get(MainActivity.viewInteger).setDidClimb(climb);
                    save();
                }
            }
        });
        final CheckBox successBox = (CheckBox) findViewById(R.id.checkBox3);
        successBox.setChecked(successful);
        successBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(successBox.isChecked()){
                    successful = true;
                    MainActivity.e.get(MainActivity.viewInteger).setClimbSuccessful(successful);
                    save();
                }else{
                    successful = false;
                    MainActivity.e.get(MainActivity.viewInteger).setClimbSuccessful(successful);
                    save();
                }
            }
        });


        final CheckBox dfBox = (CheckBox) findViewById(R.id.checkBox5);
        dfBox.setChecked(didNotFunction);
        dfBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(dfBox.isChecked()){
                   didNotFunction = true;
                    MainActivity.e.get(MainActivity.viewInteger).setDidFunction(didNotFunction);
                    save();
                }else{
                    didNotFunction = false;
                    MainActivity.e.get(MainActivity.viewInteger).setDidFunction(didNotFunction);
                    save();
                }
            }
        });

        final CheckBox stBox = (CheckBox) findViewById(R.id.checkBox4);
        stBox.setChecked(stopFunction);
        stBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(stBox.isChecked()){
                    stopFunction = true;
                    MainActivity.e.get(MainActivity.viewInteger).setStopFunction(stopFunction);
                    save();
                }else{
                    stopFunction = false;
                    MainActivity.e.get(MainActivity.viewInteger).setStopFunction(stopFunction);
                    save();
                }
            }
        });

        final CheckBox defBox = (CheckBox) findViewById(R.id.checkBox6);
        defBox.setChecked(defensive);
        defBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(defBox.isChecked()){
                    defensive = true;
                    MainActivity.e.get(MainActivity.viewInteger).setDefensive(defensive);
                    save();
                }else{
                    defensive = false;
                    MainActivity.e.get(MainActivity.viewInteger).setDefensive(defensive);
                    save();
                }
            }
        });

        Button goToMain = (Button) findViewById(R.id.button7);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGame.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
                startActivity(intent);
            }
        });

        Button spreadSheetGen = (Button) findViewById(R.id.button10);
        spreadSheetGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"data.csv");

                StringBuilder text = new StringBuilder();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                }
                catch (IOException e) {
                    //You'll need to add proper error handling here
                }

                if(text.toString().equals("")){
                    String init = "TEAM #,MATCH #,BASELINE,HOPPER,A GEAR S,A GEAR F,A HGS,A LGS,T GEAR S,T GEAR F,T HGS,T LGS,H CYCLES,L CYCLES,TRY CLIMB,CLIMB\n";
                   try{
                    FileOutputStream stream = new FileOutputStream(file);
                    try {
                        stream.write((init+matchToCSVLine(MainActivity.e.get(MainActivity.viewInteger))).getBytes());
                        MediaScannerConnection.scanFile(EndGame.this, new String[] {file.toString()}, null, null);
                    } finally {
                        stream.close();
                    }}
                   catch(FileNotFoundException e){}
                    catch(IOException e){}

                }
                else{
                    try{
                        FileOutputStream stream = new FileOutputStream(file);
                        try {
                            stream.write((text.toString()+matchToCSVLine(MainActivity.e.get(MainActivity.viewInteger))).getBytes());
                            MediaScannerConnection.scanFile(EndGame.this, new String[] {file.toString()}, null, null);
                        } finally {
                            stream.close();
                        }}
                    catch(FileNotFoundException e){}
                    catch(IOException e){}

                }
                Context context = getApplicationContext();
                CharSequence txt = "Exported.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, txt, duration);
                toast.show();

            }
        });


Button codeGen = (Button) findViewById(R.id.button9);
        codeGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = MainActivity.e.get(MainActivity.viewInteger).toString();
                EditText et = (EditText)findViewById(R.id.editText);
                et.setText(code);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", code);
                clipboard.setPrimaryClip(clip);
                Context context = getApplicationContext();
                CharSequence text = "Copied to clipboard.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

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
    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);

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
    public String matchToCSVLine(Match m){
     StringBuilder sb = new StringBuilder();
     String c = ",";
     sb.append(m.getTeamNumber() +c);
     sb.append(m.getMatchNumber()+c);
     sb.append(m.getAutonomousBaseline()+c);
     sb.append(m.getAutonomousHopper() + c);
     sb.append(m.getGearSuccessNbr()+c);
     sb.append(m.getGearFailNbr()+c);
     sb.append(m.getHighGoalScore()+c);
     sb.append(m.getLowGoalScore()+c);
     sb.append(m.getTGearSuccess()+c);
     sb.append(m.getTGearFail()+c);
     sb.append(m.getTHighGoalScore()+c);
     sb.append(m.getTLowGoalScore()+c);
     sb.append(m.getTHighCycles()+c);
     sb.append(m.getTLowCycles()+c);
     sb.append(m.getDidClimb()+c);
     sb.append(m.getClimbSuccessful());
        return sb.toString();
    }

}
