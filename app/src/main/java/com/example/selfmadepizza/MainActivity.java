package com.example.selfmadepizza;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
import android.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.camera2.params.OisSample;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText enterPizza;
    TextView result;
    FragmentManager fragmentManager = getFragmentManager();
    android.app.Fragment frame = fragmentManager.findFragmentById(R.id.frame1);
    Button okButton, cancel, db_button;
    CheckBox checkBoxSmall;
    CheckBox checkBoxMedium;
    CheckBox checkBoxBig;
    CheckBox checkBoxCash;
    CheckBox checkBoxCupon;
    CheckBox checkBoxCard;
    SeekBar quantityOfPizza;
    OutputFragment outputFragment = new OutputFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterPizza = (EditText) findViewById(R.id.enterPizza);
        okButton = (Button) findViewById(R.id.okButton);
        db_button = (Button) findViewById(R.id.button_db);
        cancel = (Button) findViewById(R.id.cancel);
        checkBoxSmall = (CheckBox)findViewById(R.id.checkBoxSmall);
        checkBoxMedium = (CheckBox)findViewById(R.id.checkBoxMedium);
        checkBoxBig = (CheckBox)findViewById(R.id.checkBoxBig);
        checkBoxCash = (CheckBox)findViewById(R.id.checkBoxCash);
        checkBoxCupon = (CheckBox)findViewById(R.id.checkBoxCupon);
        checkBoxCard = (CheckBox)findViewById(R.id.checkBoxCard);
        quantityOfPizza = (SeekBar)findViewById(R.id.seekBar2);

        Intent intent = getIntent();
        String res = intent.getStringExtra("result");
        if(res != null){
            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG ).show();
        }

        db_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the other activity
                Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        checkBoxCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxCard.isChecked()){
                    checkBoxCard.setChecked(true);
                    checkBoxCash.setChecked(false);
                    checkBoxCupon.setChecked(false);
                }
            }
        });

        checkBoxCash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxCash.isChecked()){
                    checkBoxCard.setChecked(false);
                    checkBoxCupon.setChecked(false);
                    checkBoxCash.setChecked(true);
                }
            }
        });

        checkBoxCupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxCupon.isChecked()){
                    checkBoxCard.setChecked(false);
                    checkBoxCash.setChecked(false);
                    checkBoxCupon.setChecked(true);
                }
            }
        });

        checkBoxSmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxSmall.isChecked()){
                    checkBoxSmall.setChecked(true);
                    checkBoxMedium.setChecked(false);
                    checkBoxBig.setChecked(false);
                }
            }
        });

        checkBoxMedium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxMedium.isChecked()){
                    checkBoxSmall.setChecked(false);
                    checkBoxMedium.setChecked(true);
                    checkBoxBig.setChecked(false);
                }
            }
        });
        checkBoxBig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBoxBig.isChecked()){
                    checkBoxSmall.setChecked(false);
                    checkBoxMedium.setChecked(false);
                    checkBoxBig.setChecked(true);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFragment(outputFragment);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String getPizza = enterPizza.getText().toString();
                    String pizzaSize = "";
                    String payment = "";

                    if (getPizza.length() == 0){
                        Toast.makeText(getApplicationContext(), "Уведіть назву піци", Toast.LENGTH_LONG ).show();
                    }
                    if(!checkBoxBig.isChecked() && !checkBoxSmall.isChecked() && !checkBoxMedium.isChecked()){
                        Toast.makeText(getApplicationContext(), "Оберіть розмір", Toast.LENGTH_LONG ).show();
                    }
                    if(!checkBoxCash.isChecked() && !checkBoxCupon.isChecked() && !checkBoxCard.isChecked()){
                        Toast.makeText(getApplicationContext(), "Оберіть оплату", Toast.LENGTH_LONG ).show();
                    }

                    else{
                        if(checkBoxBig.isChecked()){
                            pizzaSize = checkBoxBig.getText().toString();
                        }
                        if(checkBoxSmall.isChecked()){
                            pizzaSize = checkBoxSmall.getText().toString();
                        }
                        if(checkBoxMedium.isChecked()){
                            pizzaSize = checkBoxMedium.getText().toString();
                        }
                        if(checkBoxCard.isChecked()){
                            payment = checkBoxCard.getText().toString();
                        }
                        if(checkBoxCupon.isChecked()){
                            payment = checkBoxCupon.getText().toString();
                        }
                        if(checkBoxCash.isChecked()){
                            payment = checkBoxCash.getText().toString();
                        }
                        String res = String.format("Ви обрали піцу: %s\nРозмір: %s \nСпосіб оплати: %s\nКількість піц: %d шт.",  getPizza, pizzaSize, payment, quantityOfPizza.getProgress()+1);

                        outputFragment.order = res;
                        Log.e("res", "set");
                        setFragment(outputFragment);
                        Log.e("setFragment", "set");
                    }
                }
            }
        );

    }

    public void setFragment(Fragment fr){

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().
                setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).
                replace(R.id.frame1, fr, "SOMETAG").
                addToBackStack(null).
                show(fr).
                commit();
    }

    public void hideFragment(Fragment fr){
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().
                setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).
                replace(R.id.frame1, fr, "SOMETAG").
                addToBackStack(null).
                hide(fr).
                commit();
    }

}