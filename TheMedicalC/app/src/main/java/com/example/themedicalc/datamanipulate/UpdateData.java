package com.example.themedicalc.datamanipulate;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateData {
    private String ipAddress;
    private Context con;

    public UpdateData(Context con, String ipAddress){
        this.con = con;
        this.ipAddress = ipAddress;
    }

    public void updateMoney(String accNum, double amountPaid){
        new UpdateMoney().execute(accNum, String.valueOf(amountPaid));
    }

    class UpdateMoney extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(final String... strings) {
            StringRequest rqst = new StringRequest(Request.Method.POST, ipAddress + "projects/AndroidServers/TheMedicalGCs/UpdateMoney.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    HashMap<String, String> map = new HashMap<>();
                    map.put("accNum", strings[0]);
                    map.put("amount", strings[1]);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(con);
            requestQueue.add(rqst);

            return null;
        }
    }
}
