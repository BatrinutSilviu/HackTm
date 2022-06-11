package com.example.hacktmfrontend;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class JsonToArrayListParser {

    public static ArrayList<String> parse(String json) throws JSONException {
        JSONArray jArray = new JSONArray(json);
        ArrayList<String> listdata = new ArrayList<String>();
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                listdata.add(jArray.getString(i));
            }
        }

        return listdata;
    }
}
