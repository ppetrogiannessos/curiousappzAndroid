package com.curiousappz.www.carky_test;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import static java.net.Proxy.Type.HTTP;

public class Step_2 extends Activity implements AdapterView.OnItemSelectedListener,View.OnClickListener,GetResponse,AsyncResponse
{
    ArrayList<String> makelist=new ArrayList<>();
    ArrayList<String> modellist = new ArrayList<>();
    ArrayList<Integer>transmission_list = new ArrayList<>();
    ArrayList<Integer>fuel_list = new ArrayList<>();
    ArrayList<JSONObject> category_list= new ArrayList<>();
    MaterialBetterSpinner car_spinner,model_spinner, transmission_spinner, fuel_spinner;
    EditText registration_no,km , car_address ;
    ImageButton btnnext;
    Spinner year_spinner;
    ArrayList<Integer> years;
    Integer year;
    String access_token;
    JSONArray list = null;
    int i;
    JSONObject carTypeDTO = null;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_step_2);
       // carDetails();
        carDetails();

         car_spinner = (MaterialBetterSpinner) findViewById(R.id.car_spinner);
        model_spinner = (MaterialBetterSpinner) findViewById(R.id.model_spinner);
       transmission_spinner = (MaterialBetterSpinner) findViewById(R.id.transmission_spinner);
       year_spinner = (Spinner) findViewById(R.id.year_spinner);
       fuel_spinner = (MaterialBetterSpinner) findViewById(R.id.fuel_spinner);

        year_spinner.setOnItemSelectedListener(this);


        registration_no=(EditText)findViewById(R.id.etreg);
        km=(EditText)findViewById(R.id.etkm);
        car_address=(EditText)findViewById(R.id.etadd);
         btnnext = (ImageButton) findViewById(R.id.bnext2);

        btnnext.setOnClickListener(this);




        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,makelist);
// Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        car_spinner.setAdapter(adapter1);

        adapter1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,modellist);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        model_spinner.setAdapter(adapter1);
       // System.out.println("modellist : " + modellist);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,transmission_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        transmission_spinner.setAdapter(adapter);

        adapter= new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,fuel_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel_spinner.setAdapter(adapter);

      years = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = 1900; i <= thisYear; i++)
        {
            years.add(i);
        }

        adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(adapter);


    }



    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id)
    {
        year = Integer.parseInt(year_spinner.getSelectedItem().toString());

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    void carDetails()
    {
        SharedPreferences sharedPreferences =getSharedPreferences("Verify", Context.MODE_PRIVATE);

         access_token = sharedPreferences.getString("access_token","");
        System.out.println("Access token "+access_token);
        String data="";

            Get_WebService webService = new Get_WebService(this,"http://carky-app.azurewebsites.net/api/Helper/GetAllCarTypes",data,access_token, "GET");
            webService.execute();
        }

    @Override
    public void getService(String result, int responsecode)
    {


        if (responsecode == 200) {
            System.out.println("Result :::: "+result);

            try {
                list = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("Array : "+list);

            for ( i=0; i<list.length(); i++)
            {
                try {
                    carTypeDTO = list.getJSONObject(i);


                        String make = carTypeDTO.getString("Make");
                        String model = carTypeDTO.getString("Model");
                      //  Integer transmission = jsonObject.getInt("Transmission");
                        //Integer fuel =jsonObject.getInt("Fuel");
                        JSONObject category = (JSONObject) carTypeDTO.get("Category");
                        makelist.add(make);
                        modellist.add(model);
                   // System.out.println("CarTypeDTO : " +list);

                    System.out.println("category : " +category);
                      // String description = (String) category.get("Description");
                       /* System.out.println("Make : " + make +i);
                        System.out.println("Description : " + description);*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            transmission_list.add(1);
            transmission_list.add(2);
            fuel_list.add(1);
            fuel_list.add(2);
            fuel_list.add(3);

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,makelist);
// Specify the layout to use when the list of choices appears
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            car_spinner.setAdapter(adapter1);
            System.out.println("Make list  : " + makelist);

            adapter1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,modellist);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            model_spinner.setAdapter(adapter1);
             System.out.println("modellist : " + modellist);

            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,transmission_list);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            transmission_spinner.setAdapter(adapter);
              System.out.println("Transmission list  : " + transmission_list);

            adapter= new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,fuel_list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fuel_spinner.setAdapter(adapter);
              System.out.println("FuelList : " + fuel_list);
/*
            Intent i = new Intent(this, Step_3.class);
           startActivity(i);*/
         //   Toast.makeText(this,"Successfully Registerd" ,Toast.LENGTH_LONG).show();
        }
        else
        {
           // Toast.makeText(this," Registeration failed" ,Toast.LENGTH_LONG).show();
        }



    }
    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.bnext2)
        {
            AddCar(carTypeDTO, year, 1, car_address.getText().toString(), registration_no.getText().toString());
        }

    }

    void AddCar(JSONObject finaljson, Integer year, Integer odometer, String address, String registration)
    {
        String data;
        //String car = cartype.toString().replace("{", "");

      //  System.out.println("car : " +car);
        try {

            JSONObject category = new JSONObject();
            category.put("Description"," this is car");
            category.put("Id",2);
            category.put("Price",0.0);
            String categoryStr = category.toString();

            JSONObject carjson = new JSONObject();
            carjson.put("Id",2);
            carjson.put("Make" , "Mini");
            carjson.put("Model", "500");
            carjson.put("Transmission",2);
            carjson.put("Fuel" ,1);
            carjson.put("Category",category);

            String carStr = carjson.toString();
            JSONObject  jsonObject = new JSONObject();
            jsonObject.put("Address","Indore");
            jsonObject.put("CarType",carjson);
            jsonObject.put("Odometer",123456);
            jsonObject.put("Year",2015);
            jsonObject.put("Registration","1hdyedke23665");
    //JSONObject object = new JSONObject();
           // object.put("Culture","en_US");
           // data = jsonObject.toString();
           // data= data.toString();
          //  data = URLEncoder.encode("Culture", "UTF-8") + "=" + "en_US";
            data = URLEncoder.encode("CarType", "UTF-8") + "=" + carStr;
            data += "&" +URLEncoder.encode("Year", "UTF-8") + "=" + "2016";
            data += "&" + URLEncoder.encode("Odometer", "UTF-8") + "=" + "123456";
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + "indore";
            data += "&" + URLEncoder.encode("Registration", "UTF-8") + "=" + "12f4vf5v6rfvcr4";


          //  data = jsonObject.toString();

            System.out.println("Parameter data " + data);
            System.out.println("Accesstoken " + access_token);
            Post_WebService webService = new Post_WebService(this,"http://carky-app.azurewebsites.net/api/CarOwner/AddCar", data,access_token,"POST");
            webService.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


//        JSONObject category= new JSONObject();
//        try {
//            category.put("Description","Hatchback");
//            category.put("Id" , "2");
//            category.put("Price" , "0");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String categ = category.toString();
//        JSONObject carjson = new JSONObject();
//        try {
//            carjson.put("Id","2");
//            carjson.put("Make" , "Mini");
//            carjson.put("Model", "500");
//            carjson.put("Transmission",2);
//            carjson.put("Fuel" ,1);
//            carjson.put("Category",category);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String car = carjson.toString();
//
//
//         finaljson =new JSONObject();
//        try {
//            finaljson.put("Address",address);
//            finaljson.put("CarType",carjson);
//            finaljson.put("Odometer",odometer);
//            finaljson.put("year",year);
//            finaljson.put("Registration",registration);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Parameter data " + finaljson.toString());
//        System.out.println("Parameter data " + finaljson);
//        String input = finaljson.toString();
//        System.out.println("Parameter data " + input);
//        // System.out.println("Parameter data " + data);
//        Post_WebService webService = new Post_WebService(this, "http://carky-app.azurewebsites.net/api/CarOwner/AddCar", input,access_token,"POST");
//        webService.execute();
    }





    @Override
    public void serviceResponse(String result, int responsecode, String content)  {
        if (responsecode == 200)
        {
            Log.i("clicks", "You Clicked step2 button");
            Intent i = new Intent(this, Step_3.class);
            startActivity(i);
            Toast.makeText(this,"Successfully Registerd" ,Toast.LENGTH_LONG).show();
        }

        else if (responsecode==500)
        {
            Intent i = new Intent(this, Step_3.class);
            startActivity(i);
            Toast.makeText(this,"Car Added" ,Toast.LENGTH_LONG).show();
        }

    }


}
