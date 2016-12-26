package com.curiousappz.www.carky_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by HP on 12/25/2016.
 */

public class Post_WebService extends AsyncTask<Void,String,String>
{
    ProgressDialog pd;
    Context c;
    AsyncResponse response = null;
    private String urlString;
    private String data;
    private String Content;
    private String Error = null;
    private String accessToken;
    private int responseCode;
    private String requestMethod;

    Post_WebService(Context context,String urlStr,String parameters,String accesstoken ,String request_type)
    {
        this.c = context;
        response = (AsyncResponse)  context;
        urlString = urlStr;
        this.data = parameters;
        this.accessToken = accesstoken;
        this.requestMethod = request_type;
    }

    @Override

    protected String  doInBackground(Void... params) {

        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(urlString);

            // Send POST data request



            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
          //  conn.
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            if (accessToken.length()>0)
            {
                conn.setRequestProperty("Authorization", "Bearer "+accessToken);
            }
            conn.setDoOutput(true);
            conn.connect();

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            responseCode=conn.getResponseCode();

            // Get the server response
            System.out.println( "Server status code === "+responseCode);
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + " ");
            }

            // Append Server Response To Content String
            Content = sb.toString();
            System.out.println("Content buffer "+Content);

        }
        catch(Exception ex)
        {
            Error = ex.getMessage();
            System.out.println("Exception "+Error);
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }
return Content;
    }

    @Override
    protected void onPreExecute()
    {

        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        pd.show();
    }


    @Override
    protected void onPostExecute(String result) {
        pd.cancel();
        super.onPostExecute(result);
        //System.out.println("onPostExecute  "+result);


            response.serviceResponse(result,responseCode);

    }
}
interface AsyncResponse
{
    void serviceResponse(String result, int responsecode);
}
