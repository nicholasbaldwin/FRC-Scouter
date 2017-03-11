package com.example.nicholas.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class LengthSelect extends AppCompatActivity {
    public static final String PREFS_NAME = "PREFS";
    public static final String PREFS_KEY = "INTEGER";
    int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getValue()==null){save("5");}
       p = Integer.parseInt(getValue());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_select);

        final EditText et = (EditText) findViewById(R.id.editText8);
        et.setText(getValue());

        et.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(!et.getText().toString().equals("")){
                p = Integer.parseInt(et.getText().toString());}
                if(p>20){p=20; et.setText(Integer.toString(p)); }

                if(p<0){p=0; et.setText(Integer.toString(p));}
                save(Integer.toString(p));

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {



            }

            public void afterTextChanged(Editable s) {


            }

        });
    }
    public String getValue() {
        SharedPreferences settings;
        String text;
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
        text = settings.getString(PREFS_KEY, null); //2
        return text;
    }
   public void save(String string){
       SharedPreferences settings;
       SharedPreferences.Editor editor;
       settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
       editor = settings.edit(); //2
       editor.putString(PREFS_KEY, string); //3
       editor.commit();
   }
}
