package com.example.nicholas.app;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {

    static ArrayList<Match> e = new ArrayList<>();
    private static  final String TAG = "MainActivity";
   static String jsonVal;
    static Gson gson;
 static int viewInteger;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";
    public static final String PREFS_NAME2 = "PREFS";
    public static final String PREFS_KEY2 = "INTEGER";

    @Override
    public void onResume() {
// After a pause OR at startup
        super.onResume();
        final EditText et = (EditText) findViewById(R.id.editText);
        final ListView lv = (ListView) findViewById(R.id.listView);
        if (!et.getText().toString().equals("")) {
          ArrayList c = new ArrayList();
        final  ArrayList<Match>  d = new ArrayList<Match>();
            for(int i = 0; i<e.size();i++){

                if((Integer.toString(e.get(i).getTeamNumber())).equals((et.getText().toString())))
                {
                    c.add("\nTeam: "+Integer.toString(e.get(i).getTeamNumber())+"\n Match: "+Integer.toString(e.get(i).getMatchNumber())+"\n");
                    d.add(e.get(i));
                }
            }
            Log.v(TAG,"SIZE:"+Integer.toString(d.size()));
            for(int i = 0; i<d.size();i++){Log.v("ITERATION",Integer.toString(d.get(i).getMatchNumber()));}
            ArrayAdapter ar =  new ArrayAdapter(getApplicationContext(), R.layout.customlayout1, c);

            lv.setAdapter(ar);
            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                    // Getting the Container Layout of the ListView
                    Log.v(TAG,"POSITION"+Integer.toString(position));
                    for(int i = 0; i<e.size();i++){

                        if(e.get(i).getMatchNumber()==d.get(position).getMatchNumber()&&e.get(i).getTeamNumber()==d.get(position).getTeamNumber()){

                            viewInteger = i;

                            break;
                        }}
                    Log.v(TAG,"MESSAGE "+viewInteger);

                    goToThirdActivity();

                }
            };
            lv.setOnItemClickListener(itemClickListener);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button enterCode = (Button) findViewById(R.id.button5);

        jsonVal = getValue();
        gson = new Gson();
        Type type = new TypeToken<ArrayList<Match>>(){}.getType();
        e = gson.fromJson(jsonVal, type);
        if(e == (null)){e = new ArrayList<Match>();}

        Button button = (Button) findViewById(R.id.button);
        final EditText et = (EditText) findViewById(R.id.editText);
        final ListView lv = (ListView) findViewById(R.id.listView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSecondActivity();
            }
        });
        enterCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 goToEnterActivity();
                }
            });
        Button viewRecents = (Button) findViewById(R.id.Recent);
       viewRecents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int RECENT_VALUES;
            try {
                RECENT_VALUES = Integer.parseInt(getValue2());
            }
            catch(NumberFormatException e){
                 RECENT_VALUES=5;
                }
                final ArrayList<Match> matchArray = new ArrayList<Match>();
                ArrayList a= new ArrayList<>();

                if(e.size()<RECENT_VALUES)
                {
                RECENT_VALUES = e.size();
                }
                for(int k = e.size()-1; k>=e.size()-RECENT_VALUES;k--){
                matchArray.add(e.get(k));
                }
                for(int idx = 0; idx<matchArray.size();idx++)
                {
                    a.add("\nTeam: "+Integer.toString(matchArray.get(idx).getTeamNumber())+"\n Match: "+Integer.toString(matchArray.get(idx).getMatchNumber())+"\n");
                }
                ArrayAdapter ar =  new ArrayAdapter(getApplicationContext(), R.layout.customlayout1, a);

                lv.setAdapter(ar);
                AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                        // Getting the Container Layout of the ListView
                        Log.v(TAG,"POSITION"+Integer.toString(position));
                        for(int i = 0; i<e.size();i++){

                            if(e.get(i).getMatchNumber()==matchArray.get(position).getMatchNumber()&&e.get(i).getTeamNumber()==matchArray.get(position).getTeamNumber()){

                                viewInteger = i;

                                break;
                            }}
                        Log.v(TAG,"MESSAGE "+viewInteger);

                        goToThirdActivity();

                    }
                };
                lv.setOnItemClickListener(itemClickListener);

            }
        });


        et.addTextChangedListener(new TextWatcher() {
        ArrayList a;
            ArrayList<Match>b;
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!s.equals("") )
                {
                    a = new ArrayList();
                    b = new ArrayList<Match>();
                    for(int i = 0; i<e.size();i++){

                    if((Integer.toString(e.get(i).getTeamNumber())).equals((et.getText().toString())))
                    {
                     a.add("\nTeam: "+Integer.toString(e.get(i).getTeamNumber())+"\n Match: "+Integer.toString(e.get(i).getMatchNumber())+"\n");
                    b.add(e.get(i));
                    }
                   }
                    Log.v(TAG,"SIZE:"+Integer.toString(b.size()));
                    for(int i = 0; i<b.size();i++){Log.v("ITERATION",Integer.toString(b.get(i).getMatchNumber()));}
                  ArrayAdapter ar =  new ArrayAdapter(getApplicationContext(), R.layout.customlayout1, a);

                 lv.setAdapter(ar);
                    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                            // Getting the Container Layout of the ListView
                            Log.v(TAG,"POSITION"+Integer.toString(position));
                            for(int i = 0; i<e.size();i++){

                                if(e.get(i).getMatchNumber()==b.get(position).getMatchNumber()&&e.get(i).getTeamNumber()==b.get(position).getTeamNumber()){

                             viewInteger = i;

                             break;
                            }}
                            Log.v(TAG,"MESSAGE "+viewInteger);

                            goToThirdActivity();

                        }
                    };
                    lv.setOnItemClickListener(itemClickListener);

                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

               Log.v("MAIN","BEFORE");

            }

            public void afterTextChanged(Editable s) {

                Log.v("MAIN","AFTER");
            }

        });



    }
    private void goToSecondActivity() {

        Intent intent = new Intent(this, TeamSelection.class);

        startActivity(intent);

    }


    private void goToThirdActivity() {
        Intent intent = new Intent(this, RobotForm.class);

        startActivity(intent);

    }
    private void goToEnterActivity() {
        Intent intent = new Intent(this, Code.class);

        startActivity(intent);

    }
    public String getValue2() {
        SharedPreferences settings;
        String text;
        settings = getSharedPreferences(PREFS_NAME2, Context.MODE_PRIVATE); //1
        text = settings.getString(PREFS_KEY2, null); //2
        return text;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_1, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_cart:
                changeVal();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void changeVal(){
        Intent intent = new Intent(this, LengthSelect.class);

        startActivity(intent);
    }

    public String getValue() {
        SharedPreferences settings;
        String text;
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        text = settings.getString(PREFS_KEY, null); //2
        return text;
    }

}



