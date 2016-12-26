package com.curiousappz.www.carky_test;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends Activity implements View.OnClickListener,AsyncResponse {

    ImageButton btnnext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnnext = (ImageButton) findViewById(R.id.bnext);
btnnext.setOnClickListener(this);
    }
    public void onClick(View v) {

        //registerUser("etN");
        Intent i = new Intent(MainActivity.this, Verification.class);
        startActivity(i);

    }



    void registerUser(String a, String surname, String email, String password, String confirmPassword, String address, String contact)
    {
        String data;
//        String email = "test13@test.com";
//        String password = "123456abc";
//        String confirmPassword = "123456abc";
//        String firstname = "test13";
//        String surname = "m";
//        String contact = "1234567891";
//        String address = "Indore";
        try {
            data = URLEncoder.encode("FirstName", "UTF-8") + "=" + a;
            data += "&" + URLEncoder.encode("Surname", "UTF-8") + "=" + surname;
            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + password;
            data += "&" + URLEncoder.encode("ConfirmPassword", "UTF-8") + "=" + confirmPassword;
            data += "&" + URLEncoder.encode("PhoneNumber", "UTF-8") + "=" + contact;
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + address;
            data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + email;

            System.out.println("Parameter data " + data);
            Post_WebService webService = new Post_WebService(this, "http://carky-app.azurewebsites.net/api/Account/Register", data,"","POST");
            webService.execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serviceResponse(String result,int responsecode) {
if (responsecode == 200) {
    Log.i("clicks", "You Clicked B1");
    Intent i = new Intent(MainActivity.this, Verification.class);
    startActivity(i);
    Toast.makeText(this,"Successfully Registerd" ,Toast.LENGTH_LONG).show();
}
        else
{
    Toast.makeText(this," Registeration failed" ,Toast.LENGTH_LONG).show();
}

    }
}
