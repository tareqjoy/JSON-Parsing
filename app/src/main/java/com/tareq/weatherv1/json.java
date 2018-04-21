package com.tareq.weatherv1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class json extends AsyncTask<String, String, Map<String, String>> {
    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Map<String, String> doInBackground(String... strings) {

        String urlStr=strings[0];  //the sent url is loaded to urlStr from strings array

        Map<String, String> data=new LinkedHashMap<String, String>();  //used Map to store Name and their mobile no

        try {

            URL url=new URL(urlStr);  //string converted to URL type
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();  //as HTTP
            connection.connect();   //connecting to the server

            InputStream stream=connection.getInputStream();  //downloading the file

            BufferedReader reader=new BufferedReader(new InputStreamReader(stream));  //store the read data from server line by line to memory

            StringBuffer StrBfr=new StringBuffer();  //store the value as String
            String line="";

            while ((line = reader.readLine()) != null)  //reading the file line by line
            {
                StrBfr.append(line+"\n");   //adding the line to Stringbuffer
            }

            String jsonStr = StrBfr.toString();   //convert Stringbuffer to pure String

            JSONObject JObj=new JSONObject(jsonStr);  // {} indicates a jason object

            JSONArray JContcts= JObj.getJSONArray("contacts");  // [] indicates a json object array, with "contacts" attribute

            for(int i=0; i<JContcts.length(); i++)
            {
                JSONObject IndividualContact=JContcts.getJSONObject(i); // loading single json object from json object array
                String name = IndividualContact.getString("name");  //loading the value of "name" attribute
                JSONObject phn=IndividualContact.getJSONObject("phone");
                String mobile=phn.getString("mobile");
                data.put(name, mobile);  //adding name and mobile not map
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return data;  //send the map to onPostExecute()
    }


}



