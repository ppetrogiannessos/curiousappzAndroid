package com.curiousappz.www.carky_test;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Step_2 extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener,GetResponse
{

    Button btnyear;
    MaterialBetterSpinner car_spinner,model_spinner, transmission_spinner, fuel_spinner;
    DatePicker datePicker;
    EditText registration_no,km , car_address ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_step_2);

         car_spinner = (MaterialBetterSpinner) findViewById(R.id.car_spinner);
        model_spinner = (MaterialBetterSpinner) findViewById(R.id.model_spinner);
       transmission_spinner = (MaterialBetterSpinner) findViewById(R.id.transmission_spinner);
      /*  MaterialBetterSpinner year_spinner = (MaterialBetterSpinner) findViewById(R.id.year_spinner);*/
       fuel_spinner = (MaterialBetterSpinner) findViewById(R.id.fuel_spinner);

        registration_no=(EditText)findViewById(R.id.etreg);
        km=(EditText)findViewById(R.id.etkm);
        car_address=(EditText)findViewById(R.id.etadd);

        btnyear=(Button)findViewById(R.id.byear);
        ImageButton btnnext = (ImageButton) findViewById(R.id.bnext2);

        btnnext.setOnClickListener(this);
        btnyear.setOnClickListener(this);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        car_spinner.setAdapter(adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
       adapter = ArrayAdapter.createFromResource(this,
                R.array.model_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        model_spinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.transmission_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transmission_spinner.setAdapter(adapter);

        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
                R.array.fuel_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel_spinner.setAdapter(adapter);


        }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id)
    {
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case (R.id.byear):
                createDialogWithoutDateField().show();
                break;
            case (R.id.bnext2):
               /* Intent intent  =new Intent(this , Step_3.class);
                startActivity(intent);*/

                carDetails(car_spinner.getText().toString(),model_spinner.getText().toString(),transmission_spinner.getText().toString(),
                        datePicker.getYear(), fuel_spinner.getText().toString(), registration_no.getText().toString(),
                        km.getText().toString(), car_address.getText().toString());
        }

    }


    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(this, null, 2016, 12, 26);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {
                        Log.i("test", datePickerField.getName());
                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
        }
        return dpd;
    }


    void carDetails(String name, String model, String transmission, Integer year, String fueltype, String registration_no, String km, String car_address)
    {
        String data;
        try {
            data = URLEncoder.encode("Car_Name", "UTF-8") + "=" + name;
            data += "&" + URLEncoder.encode("Model", "UTF-8") + "=" + model;
            data += "&" + URLEncoder.encode("Transmission", "UTF-8") + "=" + transmission;
            data += "&" + URLEncoder.encode("Year", "UTF-8") + "=" + year;
            data += "&" + URLEncoder.encode("Fuel_Type", "UTF-8") + "=" + fueltype;
            data += "&" + URLEncoder.encode("Registration_No", "UTF-8") + "=" +registration_no ;
            data += "&" + URLEncoder.encode("KM", "UTF-8") + "=" + km;
            data += "&" + URLEncoder.encode("Car_Address", "UTF-8") + "=" + car_address;

            System.out.println("Parameter data " + data);
            Get_WebService webService = new Get_WebService(this, "http://carky-app.azurewebsites.net/api/Account/Register", data,"","POST");
            webService.execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getService(String result, int responsecode)
    {

        if (responsecode == 200) {
            Log.i("clicks", "You Clicked B1");
            Intent i = new Intent(this, Step_3.class);
            startActivity(i);
           // Toast.makeText(this,"Successfully Registerd" ,Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this," Registeration failed" ,Toast.LENGTH_LONG).show();
        }


    }
}
