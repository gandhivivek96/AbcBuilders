package com.example.vivekgandhi.abcbuilders;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertActivity extends AppCompatActivity {
EditText[] et;
    Button submit;
    String[] input;
    private static final String TAG = "msg";
    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        et = new EditText[]{
                (EditText) findViewById(R.id.bhk),
                (EditText) findViewById(R.id.no),
                (EditText) findViewById(R.id.name),
                (EditText) findViewById(R.id.landmark),
                (EditText) findViewById(R.id.state),
                (EditText) findViewById(R.id.city),
                (EditText) findViewById(R.id.subub),
                (EditText) findViewById(R.id.pincode),
                (EditText) findViewById(R.id.price),
                (EditText) findViewById(R.id.contact),
                (EditText) findViewById(R.id.lat),
                (EditText) findViewById(R.id.lon)};
        submit = (Button) findViewById(R.id.submit);
        mResult = (TextView) findViewById(R.id.show);
    }

    public void insert(View view)
    {
        input = new String[]
                {
                    //for(int i=0;i<12;i++)
                    et[0].getText().toString().trim(),
        et[1].getText().toString().trim(),
        et[2].getText().toString().trim(),
        et[3].getText().toString().trim(),
        et[4].getText().toString().trim(),
        et[5].getText().toString().trim(),
        et[6].getText().toString().trim(),
        et[7].getText().toString().trim(),
        et[8].getText().toString().trim(),
        et[9].getText().toString().trim(),
        et[10].getText().toString().trim(),
        et[11].getText().toString().trim()};

//make POST request
        new PostDataTask().execute("http://192.168.2.4:1002/api/status");
    }

    class PostDataTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            Log.i(TAG,"inside preexec");
            super.onPreExecute();

            progressDialog = new ProgressDialog(InsertActivity.this);
            progressDialog.setMessage("Inserting data...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i(TAG,"inside doInBackgroundc");
            try {
                return postData(params[0]);
            } catch (IOException ex) {
                return "Network error !";
            } catch (JSONException ex) {
                return "Data Invalid !";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i(TAG,"inside nPostExecute");
            super.onPostExecute(result);

            mResult.setText(result);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

        private String postData(String urlPath) throws IOException, JSONException {
            Log.i(TAG,"inside postData");
            StringBuilder result = new StringBuilder();
            BufferedWriter bufferedWriter = null;
            BufferedReader bufferedReader = null;

            try {
                //Create data to send to server
                JSONObject dataToSend = new JSONObject();
               /* bhk: Number,
                        no : Number,
                        name: String,
                        landmark: String,
                        state: String,
                        city: String,
                        subub: String,
                        pincode: Number,
                        price: Number,
                        contact: Number,
                        lat: String,
                        lon: String*/
                dataToSend.put("bhk", input[0]);
                dataToSend.put("no", input[1]);
                dataToSend.put("name", input[2]);
                dataToSend.put("landmark", input[3]);
                dataToSend.put("state", input[4]);
                dataToSend.put("city", input[5]);
                dataToSend.put("subub", input[6]);
                dataToSend.put("pincode", input[7]);
                dataToSend.put("price", input[8]);
                dataToSend.put("contact", input[9]);
                dataToSend.put("lat", input[10]);
                dataToSend.put("lon", input[11]);


                //Initialize and config request, then connect to server.
                URL url = new URL(urlPath);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(10000 /* milliseconds */);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);  //enable output (body data)
                urlConnection.setRequestProperty("Content-Type", "application/json");// set header
                urlConnection.connect();

                //Write data into server
                OutputStream outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(dataToSend.toString());
                bufferedWriter.flush();

                //Read data response from server
                InputStream inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } finally {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }

            return result.toString();
        }
    }

}
