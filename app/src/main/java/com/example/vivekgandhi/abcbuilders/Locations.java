package com.example.vivekgandhi.abcbuilders;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Locations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String er="msg";
    Double[] lon;
    Double[] lat;
    int totalrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        new GetDataTask().execute("http://192.168.2.4:1002/api/status");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


       /* lat[0]=19.080922;
        lon[0] = 72.907986;
        lat[1]=19.044548;
        lon[1] = 72.820564;
        LatLng[] prop = new LatLng[n];
        prop[0] = new LatLng(lat[0],lon[0]);*/
        Log.i(er,"b4 loop ");
        for (int i=0;i<totalrow ;i++ ) {
            LatLng l = new LatLng(lat[i],lon[i]);
              mMap.addMarker(new MarkerOptions().position(l).title("property " +i));
                   // .snippet(getStateString(Cars.get(i).getState())).icon(bitmapMarker)))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
            Log.i(er,"adding map  = "+i);}
        // Add a marker in Sydney and move the camera
        /*LatLng property1 = new LatLng(19.080922, 72.907986);
        mMap.addMarker(new MarkerOptions().position(property1).//title("property1 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property1));

        LatLng property2 = new LatLng(19.044548, 72.820564);
        mMap.addMarker(new MarkerOptions().position(property2).title("property2 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property2));

        LatLng property3 = new LatLng(19.048979, 72.820915);
        mMap.addMarker(new MarkerOptions().position(property3).title("property3 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property3));

        LatLng property4 = new LatLng(19.115096, 72.867296);
        mMap.addMarker(new MarkerOptions().position(property4).title("property4 "));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(property4));
*/
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(property2,12.0f));
    }


    class GetDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            Log.i(er,"onPreExecute");
            super.onPreExecute();

            progressDialog = new ProgressDialog(Locations.this);
            progressDialog.setMessage("Loading data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i(er,"");
            try {
                return getData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i(er,"onPostExecute ");
         //   List<Books> data=new ArrayList<>();

            //   Spinner s = (Spinner) findViewById(R.id.books);
            //set data response to textView
//            mResult.setText(result);
            //n.setText("onPostExe");
            //JSONObject obj = null;
            JSONArray obj ;
            try {
                obj = new JSONArray(result);
                //obj = new JSONObject(result);
               // cN = new String[obj.length()];
                totalrow = obj.length();
                lon = new Double[obj.length()];
                lat = new Double[obj.length()];
                //Books[] bookData = new Books[obj.length()];
                for (int i = 0; i < obj.length(); i++) {
                    JSONObject jsonobj = obj.getJSONObject(i);
                    // Books bookData = new Books();
                    String N1;
                  /*  bookData[i] = new Books();
                    bookData[i].id = jsonobj.getString("_id");
                    bookData[i].bookName = jsonobj.getString("bookname");
                    bookData[i].authorName = jsonobj.getString("author");
                    bookData[i].price = Integer.parseInt(jsonobj.getString("price"));
                    bookData[i].stock = Integer.parseInt(jsonobj.getString("stock"));*/
                    lat[i] = Double.parseDouble(jsonobj.getString("lat"));
                    lon[i] = Double.parseDouble(jsonobj.getString("lon"));
                    Log.i(er,lat[i] + " lon = " + lon[i]);
                   /* data.add(bookData[i]);
                    if (i == obj.length() - 1) {
                        n.setText("total row = " + obj.length());
                    }*/
                }
                // Setup and Handover data to recyclerview
              /*  mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                mAdapter = new BooksAdapter(DisplayActivity.this, data);
                mRVFishPrice.setAdapter(mAdapter);
                mRVFishPrice.setLayoutManager(new LinearLayoutManager(DisplayActivity.this));
                */
                //  mAdapter.notifyDataSetChanged();
             /*   ArrayAdapter<String> adapter = new ArrayAdapter<String>(DisplayActivity.this,
                        android.R.layout.linearLayout5, cN);
                s.setAdapter(adapter);*/
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //cancel progress dialog
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Log.i(er,"after onPostExecute");
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(Locations.this);
        }

        private String getData(String urlPath) throws IOException {
            //  n.setText("GET Data");
            StringBuilder result = new StringBuilder();
            BufferedReader bufferedReader =null;

            try {
                //Initialize and config request, then connect to server
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    result.append(line).append("\n");

                }



            } /*catch (JSONException e) {
                e.printStackTrace();
            } */finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            }
            Log.i(er," done with getData");
            return result.toString();
        }


    }
}
