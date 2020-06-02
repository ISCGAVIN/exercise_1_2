package com.example.assignment2exercise12;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity
{
    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue myQueue = Volley.newRequestQueue(getApplicationContext());

        final TextView result = (TextView)findViewById(R.id.textView1);
        final Button request  = (Button)findViewById(R.id.btnGet);
        request.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String url ="https://api.geonet.org.nz/quake?MMI=1";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
                        {

                            @Override
                            public void onResponse(JSONObject response)
                            {

                                StringBuilder localities = new StringBuilder();

                                try {
                                    JSONArray data = response.getJSONArray("features");

                                    for(int index = 0; index < data.length(); index++)
                                    {
                                        JSONObject quake = data.getJSONObject(index);

                                        JSONObject properties = quake.getJSONObject("properties");
                                        localities.append("Locality: " + properties.getString("locality")+"\n");
                                        localities.append("Time: " + properties.getString("time")+"\n");
                                        localities.append("Depth: " + properties.getString("depth")+"\n");
                                        localities.append("Quality: " + properties.getString("quality")+"\n\n");


                                    }

                                    System.err.println(response);
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                                result.setText(localities.toString());

                            }
                        }, new Response.ErrorListener()
                        {

                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.e("error",error.getLocalizedMessage());

                            }
                        });
                myQueue.add(jsObjRequest);
            }
        });
    }
}
