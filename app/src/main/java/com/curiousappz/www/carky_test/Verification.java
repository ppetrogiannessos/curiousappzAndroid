package com.curiousappz.www.carky_test;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Verification extends Activity implements View.OnClickListener , AsyncResponse{

    EditText et1,et2,et3,et4,et5,et6;
    Button botp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
        et4=(EditText)findViewById(R.id.et4);
        et5=(EditText)findViewById(R.id.et5);
        et6=(EditText)findViewById(R.id.et6);

        botp=(Button)findViewById(R.id.botp);
        botp.setOnClickListener(this);

        et1.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {

                if (v.getId() == et1.getId())
                {
                    if (et1.getText().toString().length() == 1) {
                        et2.requestFocus();
                    }
                }
return false;

            }
        });


        et2.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {

                if (v.getId() == et2.getId())
                {
                    if (et2.getText().toString().length() == 1)
                    {
                        et3.requestFocus();
                    }

                }
                return false;

            }
        });
        et3.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {

                if (v.getId() == et3.getId())
                {
                    if (et3.getText().toString().length() == 1) {
                        et4.requestFocus();
                    }

                }
                return false;

            }
        });
        et4.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {

                if (v.getId() == et4.getId())
                {
                    if (et4.getText().toString().length() == 1) {
                        et5.requestFocus();
                    }

                }
                return false;

            }
        });
        et5.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {

                if (v.getId() == et5.getId())
                {
                    if (et5.getText().toString().length() == 1) {
                        et6.requestFocus();
                    }
                }
                return false;

            }
        });
    }
    @Override
    public void onClick(View v) {
        loginUser("test@test.com" ,"123456abc","password");
//        Log.i("clicks","You Clicked B1");
//        Intent i=new Intent(this, Step_2.class);
//        startActivity(i);
    }



    void loginUser(String username , String password, String grant_type )
    {
        String data;
        try {
            data = URLEncoder.encode("username", "UTF-8") + "=" + username;
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + password;
            data += "&" + URLEncoder.encode("grant_type", "UTF-8") + "=" + grant_type;
            System.out.println("Togin user method");

            System.out.println("Parameter data " + data);
            Post_WebService webService = new Post_WebService(this, "http://carky-app.azurewebsites.net/token", data,"","POST");
            webService.execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serviceResponse(String result,int responsecode) {
        if (responsecode == 200) {
            Log.i("clicks", "You Clicked B1");
         Intent i = new Intent(this, Step_2.class);
           startActivity(i);
            Toast.makeText(this,"Successfully Registerd" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this," Registeration failed" ,Toast.LENGTH_LONG).show();
        }

    }
}
