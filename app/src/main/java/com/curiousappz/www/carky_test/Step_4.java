package com.curiousappz.www.carky_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Step_4 extends Activity implements View.OnClickListener ,AsyncResponse{
    boolean checked;
    String access_token;
    String data ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_4);
        ImageButton btnnxt = (ImageButton) findViewById(R.id.bnext4);
        btnnxt.setOnClickListener(this);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
         checked = ((CheckBox) view).isChecked();



        // Check which checkbox was clicked
        }


    @Override
    public void onClick(View v)
    {
        if ((v.getId()==R.id.bnext4))
        {
 checkUser();
        }

    }


    void checkUser()
    {


        SharedPreferences sharedPreferences =getSharedPreferences("Verify", Context.MODE_PRIVATE);
        access_token = sharedPreferences.getString("access_token","");
        System.out.println("Access Token " + access_token);


        try {
            JSONObject object = new JSONObject();

                object.put("Culture" , "en_US");
             String data1  =  object.toString();


            data = URLEncoder.encode("Culture", "UTF-8") + "=" + data1;
            System.out.println("Parameter data " + data1);
            Post_WebService webService = new Post_WebService(this, "http://carky-app.azurewebsites.net/api/Account/FetchTerms",data1,access_token,"POST");
            webService.execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void serviceResponse(String result, int responsecode, String content)
    {
        if (responsecode == 200)
        {
            if (checked== true)
            {
                Toast.makeText(this,"Functionality not assinged" , Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,"Please Accept Tearms & Condition" , Toast.LENGTH_LONG).show();

            }

        }
        else
        {
            Toast.makeText(this," Error" ,Toast.LENGTH_LONG).show();
        }

    }
}
