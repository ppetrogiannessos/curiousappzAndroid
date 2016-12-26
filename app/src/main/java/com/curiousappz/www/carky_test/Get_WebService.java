package com.curiousappz.www.carky_test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by HP on 12/25/2016.
 */

public class Get_WebService extends AsyncTask<Void,String,String>
{
    ProgressDialog pd;
    HttpURLConnection conn;
    Context c;
   GetResponse get_response = null;
    private String urlString;
    private String data;
    private String Content;
    private String Error = null;
    private String accessToken;
    private int responseCode;
    private String requestMethod;

    Get_WebService(Context context,String urlStr,String parameters,String accesstoken ,String request_type)
    {
        this.c = context;
        get_response = (GetResponse)  context;
        urlString = urlStr;
        this.data = parameters;
        this.accessToken = accesstoken;
        this.requestMethod = request_type;
    }

    @Override

    protected String  doInBackground(Void... params) {
        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(urlString);

            // Send GET data request

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            String s = "Bearer "+accessToken;
            // System.out.println("Authentic :"+s);
            conn.setRequestProperty("Authorization", "Bearer "+accessToken);
            conn.setRequestMethod("GET");

           responseCode = conn.getResponseCode();
            Log.v("Status code :", "httpStatus " + responseCode);

            if(responseCode == HttpURLConnection.HTTP_OK){
                Content = readStream(conn.getInputStream());
                Log.v("CatalogClient", Content);
            }
            else
            {
                System.out.println(conn.getResponseMessage());
                System.out.println(conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
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

                conn.disconnect();
            }

            catch(Exception ex) {}
        }
        return null;
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


        get_response.getService(result,responseCode);

    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}

interface GetResponse
{
    void getService(String result, int responsecode);
}
