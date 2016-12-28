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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends Activity implements View.OnClickListener, AsyncResponse {

    ImageButton btnnext;
    EditText name, surname, address, email, password, phone, cpassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String namestr, surnamestr, passwordstr, cpasswordstr, emailstr, phonestr, addstr;
    String access_token;
    String resUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.etName);
        surname = (EditText) findViewById(R.id.etSurname);
        address = (EditText) findViewById(R.id.etAddress);
        email = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.etPass);
        cpassword = (EditText) findViewById(R.id.etCPass);
        phone = (EditText) findViewById(R.id.etPhone);

        btnnext = (ImageButton) findViewById(R.id.bnext);
        btnnext.setOnClickListener(this);

    }

    public void onClick(View v) {

        // nextSCreen();

        if (name.getText().length() > 0) {
            namestr = name.getText().toString();
        } else {
            Toast.makeText(this, "Name Required ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (surname.getText().length() > 0) {
            surnamestr = surname.getText().toString();
        } else {
            Toast.makeText(this, "Name Required ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.getText().length() > 0) {
            emailstr = email.getText().toString();
        } else {
            Toast.makeText(this, "Name Required ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.getText().length() > 0) {
            passwordstr = password.getText().toString();
        } else {
            Toast.makeText(this, "Name Required ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cpassword.getText().length() > 0) {
            cpasswordstr = cpassword.getText().toString();
        } else {
            Toast.makeText(this, "Confirm password Required ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (address.getText().length() > 0) {
            addstr = address.getText().toString();
        } else {
            Toast.makeText(this, "Address Required ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.getText().length() > 0) {
            phonestr = phone.getText().toString();
        } else {
            Toast.makeText(this, "Phone no Required ", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            registerUser(namestr, surnamestr, emailstr, passwordstr, cpasswordstr, addstr, phonestr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    void registerUser(String username, String surname, String email, String password, String confirmPassword, String address, String contact) throws JSONException {
        String data;

        try {
            data = URLEncoder.encode("FirstName", "UTF-8") + "=" + username;
            data += "&" + URLEncoder.encode("Surname", "UTF-8") + "=" + surname;
            data += "&" + URLEncoder.encode("Password", "UTF-8") + "=" + password;
            data += "&" + URLEncoder.encode("ConfirmPassword", "UTF-8") + "=" + confirmPassword;
            data += "&" + URLEncoder.encode("PhoneNumber", "UTF-8") + "=" + contact;
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + address;
            data += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + email;
            resUrl = "Register";
            System.out.println("Parameter data " + data);
            Post_WebService webService = new Post_WebService(this, "http://carky-app.azurewebsites.net/api/Account/Register", data, "", "POST");
            webService.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void loginUser(String username, String password, String grant_type) {
        String data;
        System.out.println("username" + username + "passsword" + password);
        try {
            data = URLEncoder.encode("username", "UTF-8") + "=" + username;
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + password;
            data += "&" + URLEncoder.encode("grant_type", "UTF-8") + "=" + grant_type;
            System.out.println("Togin user method");

            System.out.println("Parameter data " + data);
            resUrl = "token";
            Post_WebService webService = new Post_WebService(this, "http://carky-app.azurewebsites.net/token", data, "", "POST");
            webService.execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void sendPhoneNnumberConfirmation() {
        String data;
        JSONObject object = new JSONObject();
        data = object.toString();
        try {
            resUrl = "SendPhoneNumberConfirmation";
            Post_WebService webService2 = new Post_WebService(this, "http://carky-app.azurewebsites.net/api/Account/SendPhoneNumberConfirmation", data, access_token, "POST");
            webService2.execute();
            System.out.println("OTP");
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }


    @Override
    public void serviceResponse(String result, int responsecode, String content) {


        System.out.println(resUrl);
        if (responsecode == 200 && resUrl.contains("Register")) {

            sharedPreferences = getSharedPreferences("Register", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();


            editor.putString("Name", namestr);
            editor.putString("SurNname", surnamestr);
            editor.putString("Address", addstr);
            editor.putString("Email", emailstr);
            editor.putString("Password", passwordstr);
            editor.putString("ConfirmPassword", cpasswordstr);
            editor.putString("PhoneNumber", phonestr);

            editor.commit();

            Log.i("clicks", "You Clicked B1");
            loginUser(emailstr, passwordstr, "password");
            Toast.makeText(this, "Successfully Registerd", Toast.LENGTH_LONG).show();


        } else if (responsecode == 200 && resUrl.contains("token")) {
            sharedPreferences = getSharedPreferences("Register", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            try {

                JSONObject json_result = new JSONObject(result);
                access_token = (String) json_result.get("access_token");
                System.out.println("response access token " + access_token);
                editor.putString("access_token", access_token);
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            sendPhoneNnumberConfirmation();
            // Intent i = new Intent(this, AddCar.class);
            //startActivity(i);
            //   Toast.makeText(this, "Successfully Registerd", Toast.LENGTH_LONG).show();
        } else if (responsecode == 200 && resUrl.contains("SendPhoneNumberConfirmation")) {


            sharedPreferences = getSharedPreferences("Register", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();

            String otp_res = result.toString();
            System.out.println("response otp_res " + otp_res);
            editor.putString("otp_res", otp_res);
            editor.commit();
            System.out.println(result);
            Intent i = new Intent(this, Verification.class);
            startActivity(i);
        } else {
            Toast.makeText(this, " Registeration failed", Toast.LENGTH_LONG).show();
        }

    }
}
