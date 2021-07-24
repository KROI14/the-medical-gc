package com.example.themedicalc.datamanipulate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.themedicalc.dataholder.BankHolder;
import com.example.themedicalc.dataholder.InsuranceHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FetchData{
    private Context con;

    private String ipAddress;

    private String patientID;
    private String billID;
    private String paymentID;

    private ArrayList<BankHolder> arrBank;
    private ArrayList<InsuranceHolder> arrInsure;

    public FetchData(Context con, String ipAddress){
        this.con = con;
        this.ipAddress = ipAddress;
    }

    public void getJSONID(){
        new RequestIDTask().execute();
    }

    public String[] getId(){
        String arr[] = {patientID, billID, paymentID};
        return arr;
    }

    public void requestBankInsurance(){
        new RequestBankInsuranceTask().execute();
    }

    public ArrayList<BankHolder> getBankList(){
        return arrBank;
    }

    public ArrayList<InsuranceHolder> getInsuranceList(){
        return arrInsure;
    }

    private class RequestIDTask extends AsyncTask<Void, String, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            StringRequest strRequest = new StringRequest(com.android.volley.Request.Method.POST, ipAddress+"projects/AndroidServers/TheMedicalGCs/FetchID.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject obj = new JSONObject(response);
                                onProgressUpdate(obj.getString("PatientID"), obj.getString("BillID"), obj.getString("PaymentID"));
                            }
                            catch (Exception e){
                                Toast.makeText(con, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(con, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            Volley.newRequestQueue(con).add(strRequest);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            patientID = values[0];
            billID = values[1];
            paymentID = values[2];
        }
    }

    private class RequestBankInsuranceTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            arrBank = new ArrayList();
            arrInsure = new ArrayList();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            StringRequest strRequest = new StringRequest(com.android.volley.Request.Method.POST, ipAddress+"projects/AndroidServers/TheMedicalGCs/FetchLookup.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONArray data = new JSONArray(response);
                                JSONArray bankArray = data.getJSONArray(0);
                                JSONArray insureArray = data.getJSONArray(1);

                                for(int i = 0; i < bankArray.length(); i++){
                                    JSONObject bankObj = bankArray.getJSONObject(i);

                                    String accNum = bankObj.getString("AccNum");
                                    String bankName = bankObj.getString("BankName");
                                    String money = bankObj.getString("Money");
                                    String fName = bankObj.getString("Firstname");
                                    String lName = bankObj.getString("Lastname");
                                    arrBank.add(new BankHolder(accNum, bankName, fName, lName, money));
                                }

                                for(int i = 0; i < insureArray.length(); i++){
                                    JSONObject insurObj = insureArray.getJSONObject(i);

                                    String insureNum = insurObj.getString("InsuranceNum");
                                    String isnureName = insurObj.getString("Name");
                                    String discount = insurObj.getString("Discount");
                                    arrInsure.add(new InsuranceHolder(insureNum, isnureName, discount));
                                }
                            }
                            catch (Exception e){
                                Toast.makeText(con, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(con, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            Volley.newRequestQueue(con).add(strRequest);

            return null;
        }
    }
}