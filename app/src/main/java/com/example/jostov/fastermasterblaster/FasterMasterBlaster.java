package com.example.jostov.fastermasterblaster;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;



public class FasterMasterBlaster extends Activity{

    int mods[] = {-1, -1, -1, -1, -1};
    boolean[][] combo = new boolean[40][3];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faster_master_blaster);
        EditText ed1 = (EditText) findViewById(R.id.stop1);
        ed1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                calcOptions();
            }
        });
        EditText ed2 = (EditText) findViewById(R.id.stop2);
        ed2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                calcOptions();
            }
        });
        RadioGroup rg = (RadioGroup) findViewById(R.id.pickWidth);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.num3one:
                        setChoice(mods[3] / 100);
                    case R.id.num3two:
                        setChoice(mods[3] % 100);

                }
            }
        });
    }
    private void calcOptions()
    {
        mods[0] = -1;
        mods[1] = -1;
        mods[2] = -1;
        EditText tv1 = (EditText) findViewById(R.id.resistanceNumber);
        EditText tv2 = (EditText) findViewById(R.id.stop1);
        EditText tv3 = (EditText) findViewById(R.id.stop2);
        mods[0] =(!tv1.getText().toString().equals("")) ? Math.round(5.78f + Float.parseFloat(tv1.getText().toString())) : -1;
        mods[1] =(!tv2.getText().toString().equals("")) ? Integer.parseInt(tv2.getText().toString()) : -1;
        mods[2] =(!tv3.getText().toString().equals("")) ? Integer.parseInt(tv3.getText().toString()) : -1;
        if ((mods[0] >= 0)&&(mods[1] >= 0)&&(mods[2] >= 0)){
            calculateCombos();
            giveChoice();
    }
    }
    private void calculateCombos(){
        combo = new boolean[40][3];
        Log.d("dbug", "mod1 : "+mods[1]+", mod2 : "+mods[2]+", mod3 : "+mods[3]);
        for (int i = 0; i < 40; i++){
            if (((i + 1)%4 == mods[0] %4 )&&(((i+1) % 10 == mods[1]) || ((i+1) % 10 ==mods[2])))
            {
                combo[i][2] = true;
                Log.d("dbug", "combo["+i+"][2] set to true");
            }
            if ((i+1)%4 == (mods[0]+2)%4){
                combo[i][1] = true;
                Log.d("dbug", "combo["+i+"][1] set to true");
            }
        }
    }
    private void giveChoice(){
        mods[3] = 0;
        for (int i = 0; i < 40; i++)
        {
            if (combo[i][2])
            {
                mods[3] = ((mods[3] * 100) + (i+1));
                Log.d("dbug", "mods[3] says : " + mods[3]);
            }
        }
        RadioButton b1 = (RadioButton)findViewById(R.id.num3one);
        RadioButton b2 = (RadioButton)findViewById(R.id.num3two);

        b1.setText(String.valueOf(mods[3] / 100));
        b2.setText(String.valueOf(mods[3] % 100));


    }

    public void setChoice(int Choice){
        for (int i = ((Choice -5) % 40); i < (Choice+3) % 40 ; i++){
            combo[i][1] = false;
        }
        setCombo2();
        setCombo1();
        setCombo3(Choice);


    }
    private void setCombo2(){
        int k = 1;
        for(int i =0; i < 40; i++){
            if (combo[i][1]&& k<9){
                TextView tv = (TextView)findViewById(getResources().getIdentifier("num"+(k), "id",getPackageName()));
                tv.setText(String.valueOf(i+1));
                k++;
            }

        }
    }
    private void setCombo1(){
        TextView tv = (TextView)findViewById(R.id.firstNum);
        tv.setText(String.valueOf(mods[0]));
    }
    private void setCombo3(int n){
        TextView tv = (TextView)findViewById(R.id.lastNum);
        tv.setText(String.valueOf(n));

    }

}
