package com.curiousappz.www.carky_test;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Verification extends Activity implements View.OnClickListener, AsyncResponse {

    EditText et1, et2, et3, et4, et5, et6;
    Button botp, btnresend;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String access_token;
    String resUrl;
    String o1 ,o2,o3,o4,o5,o6;
    String otpverify;
    String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);

        botp = (Button) findViewById(R.id.botp);
        botp.setOnClickListener(this);
        btnresend = (Button) findViewById(R.id.bresend);
        btnresend.setOnClickListener(this);


        et1.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (v.getId() == et1.getId()) {
                    if (et1.getText().toString().length() == 1)
                    {

                         o1 =et1.getText().toString();
                        System.out.println(o1);
                        et2.requestFocus();
                    }
                }
                return false;

            }
        });


        et2.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (v.getId() == et2.getId()) {
                    if (et2.getText().length() == 1) {
                        o2 =et2.getText().toString();
                        System.out.println(o2);
                        et3.requestFocus();

                    }

                }
                return false;

            }
        });
        et3.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (v.getId() == et3.getId()) {
                    if (et3.getText().toString().length() == 1) {
                        o3 =et3.getText().toString();
                        System.out.println(o3);
                        et4.requestFocus();
                    }

                }
                return false;

            }
        });
        et4.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (v.getId() == et4.getId()) {
                    if (et4.getText().toString().length() == 1) {
                        o4=et4.getText().toString();
                        System.out.println(o4);
                        et5.requestFocus();
                    }

                }
                return false;

            }
        });
        et5.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (v.getId() == et5.getId()) {
                    if (et5.getText().toString().length() == 1) {
                        o5 =et5.getText().toString();
                        System.out.println(o5);
                        et6.requestFocus();
                    }
                }
                return false;

            }
        });

    }



 // @Override
  //  public void serviceResponse(String result, int responsecode) {

/*

        System.out.println(resUrl);
        if (responsecode == 200 && resUrl.contains("token")) {
            sharedPreferences = getSharedPreferences("Verify", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            try {

                JSONObject  json_result = new JSONObject(result);
                access_token = (String) json_result.get("access_token");
                System.out.println("response access token " + access_token);
                editor.putString("access_token", access_token);
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(this, Step_2.class);
            startActivity(i);
            Toast.makeText(this, "Successfully Registerd", Toast.LENGTH_LONG).show();
        } else if (responsecode ==200 && resUrl.contains("SendPhoneNumberConfirmation")) {


            System.out.println(result);


        } else {
            Toast.makeText(this, " Registeration failed", Toast.LENGTH_LONG).show();
        }
*/

 //   }


    @Override
    public void serviceResponse(String result, int responsecode, String content)
    {
        otpverify=result;
        System.out.println("server otp : " + otpverify);

        System.out.println(resUrl);
        if (responsecode == 200 && resUrl.contains("token")) {
            sharedPreferences = getSharedPreferences("Register", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            try {



                JSONObject  json_result = new JSONObject(result);
                access_token = (String) json_result.get("access_token");
                System.out.println("response access token " + access_token);
                editor.putString("access_token", access_token);
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Intent i = new Intent(this, Step_2.class);
            startActivity(i);
            Toast.makeText(this, "Successfully Registerd", Toast.LENGTH_LONG).show();
        } else if (responsecode ==200 && resUrl.contains("SendPhoneNumberConfirmation")) {


            System.out.println(result);


        } else {
            Toast.makeText(this, " Registeration failed", Toast.LENGTH_LONG).show();
        }


    }
    @Override
    public void onClick(View v)

    {

        if (v.getId()==R.id.botp)
        {

            sharedPreferences = getSharedPreferences("Register", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

           otpverify =  sharedPreferences.getString("otp_res" , "null");
            o6=et6.getText().toString();
            System.out.println(o6);

            otp = (o1+o2+o3+o4+o5+o6);

            System.out.println("server otp : " + otpverify);
            System.out.println(" otp : " + otp);

            if (otpverify.contains(otp))
            {
                Intent i = new Intent(this, Step_2.class);
                startActivity(i);

                Toast.makeText(this,"correct otp",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Incorrect otp",Toast.LENGTH_SHORT).show();
            }
        }

    }
}
