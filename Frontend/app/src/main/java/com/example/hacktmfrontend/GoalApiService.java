package com.example.hacktmfrontend;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class GoalApiService {
    private final String addGoalURL = "http://10.10.9.209:3000/addGoal";
    private RequestQueue queue;

    public GoalApiService(Context context){
        this.queue = Volley.newRequestQueue(context);
    }

    public void sendPost(String calories, String proteins, String carbs, String fats)
    {
        if (!this.validatePercentages(proteins, carbs, fats))
        {
            return;
        }

        StringRequest postRequest = new StringRequest(Request.Method.POST, addGoalURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("target_calories", calories);
                params.put("proteins", proteins);
                params.put("carbs", carbs);
                params.put("fats", fats);

                return params;
            }
        };

        this.queue.add(postRequest);
    }

    public boolean validatePercentages(String proteins, String carbs, String fats)
    {
        int sum = Integer.parseInt(proteins) + Integer.parseInt(carbs) + Integer.parseInt(fats);

        return sum == 100;
    }
}
