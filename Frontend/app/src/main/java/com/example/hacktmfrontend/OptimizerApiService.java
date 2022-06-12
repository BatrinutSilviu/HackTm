package com.example.hacktmfrontend;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OptimizerApiService {
    private final String getDietUrl = "http://10.10.9.209:5005/diet";

    public OptimizerApiService(Context context){
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.allowThreadDiskReads();
        StrictMode.allowThreadDiskWrites();
    }

    public String getDiet(int proteins, int carbs, int fats, int days, int mealsPerDay, int calories) throws IOException
    {
        int proteinGrams = MacroToGramsTransformer.transformToGrams(Macros.Protein, proteins, calories);
        int carbGrams = MacroToGramsTransformer.transformToGrams(Macros.Carb, carbs, calories);
        int fatGrams = MacroToGramsTransformer.transformToGrams(Macros.Fat,fats, calories);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"proteins\":" + proteinGrams +
                ",\n    \"carbs\":" + carbGrams + ",\n    \"fat\":" + fatGrams + ",\n    \"days\":" + days +
                ",\n    \"meals_per_day\":" + mealsPerDay + "\n}");

        Request request = new Request.Builder()
                .url(getDietUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Response code", String.valueOf(response.code()));
                Log.d("Response body", response.body().toString());
            }
        });

        return "";
    }
}
