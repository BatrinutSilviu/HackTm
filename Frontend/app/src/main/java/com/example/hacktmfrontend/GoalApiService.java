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

    public void sendPost(int calories, int proteins, int carbs, int fats)
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
                Map<String, String>  params = new HashMap<>();
                params.put("target_calories", String.valueOf(calories));
                params.put("proteins", String.valueOf(proteins));
                params.put("carbs", String.valueOf(carbs));
                params.put("fats", String.valueOf(fats));

                return params;
            }
        };

        this.queue.add(postRequest);
    }

    public boolean validatePercentages(int proteins, int carbs, int fats)
    {
        int sum = proteins + carbs + fats;

        return sum == 100;
    }
}
