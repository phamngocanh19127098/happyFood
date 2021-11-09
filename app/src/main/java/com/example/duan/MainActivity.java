package com.example.duan;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Connection().execute();
        listView= (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
    }

    class Connection extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String result="";
            String host= "http://10.0.2.2/createapost/getdata.php";
            try{
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response= client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuffer stringBuffer= new StringBuffer("");
                String line="";
                while((line=reader.readLine())!=null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result=stringBuffer.toString();

            }
            catch (Exception e){
                return new String("There exception: "+e.getMessage());
            }


            return result;
        }
        @Override
        protected void onPostExecute(String result){
            try {

                JSONObject jsonResult= new JSONObject(result);
                int success =jsonResult.getInt("success");
                if(success==1){
                    //Toast.makeText(getApplicationContext(),"Oke, there are result",Toast.LENGTH_SHORT).show();
                    JSONArray cars = jsonResult.getJSONArray("cars");
                    for(int i=0;i<cars.length();i++){
                        JSONObject car= cars.getJSONObject(i);
                        int id = car.getInt("id");
                        String food = car.getString("food");
                        String address = car.getString("address");
                        String description = car.getString("description");
                        String username= car.getString("username");
                        String line= Integer.toString(id) + " "+ food+ " "+address +" "+description+" "+ username;
                        adapter.add(line);

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"No cars yet",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }


}