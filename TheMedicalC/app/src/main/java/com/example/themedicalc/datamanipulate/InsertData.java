package com.example.themedicalc.datamanipulate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.themedicalc.Payment;
import com.example.themedicalc.R;

import java.util.HashMap;
import java.util.Map;

public class InsertData{

    private Context con;

    private String ipAddress;

    public InsertData(Context con, String ipAddress){
        this.con = con;
        this.ipAddress = ipAddress;
    }

    public void insertPatientAndMedical(String... data){
        String patientID = data[15];
        String fName = data[0];
        String lName = data[1];
        String age = data[2];
        String gender = data[3];
        String address = data[4];
        String contact = data[5];

        String billID = data[16];
        String illness = data[6];
        String condition = data[7];
        String xRay = data[8];
        String ctScan = data[9];
        String covidTest = data[10];
        String room = data[11];
        String days = data[12];
        String tax = data[13];
        String totalPrice = data[14];

        new InsertPatientAndMedical().execute(patientID, fName, lName, age, gender, address, contact,
                billID, illness, condition, xRay, ctScan, covidTest, room, days, tax, totalPrice);
    }

    public void insertPaymentData(String paymentID, String bankAcc, String billID, String insureName, double discount, double amountPaid, double balance){
        String strDiscount = String.valueOf(discount);
        String strAmountPaid = String.valueOf(amountPaid);
        String strBalance = String.valueOf(balance);

        new InsertPaymentData().execute(paymentID, bankAcc, billID, insureName, strDiscount, strAmountPaid, strBalance);
    }

    class InsertPatientAndMedical extends AsyncTask<String, Void, Void>{
        private ProgressDialog prd;

        @Override
        protected void onPreExecute() {
            prd = new ProgressDialog(con);
            prd.setCancelable(false);
            prd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prd.setMessage("Loading...");
            prd.setCancelable(false);
            prd.setCanceledOnTouchOutside(false);
            prd.show();
        }

        @Override
        protected Void doInBackground(final String... strings) {
            StringRequest strRequest = new StringRequest(Request.Method.POST, ipAddress + "projects/AndroidServers/TheMedicalGCs/InsertPatientMedical.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap();
                    data.put("patientID", strings[0]);
                    data.put("firstname", strings[1]);
                    data.put("lastname", strings[2]);
                    data.put("age", strings[3]);
                    data.put("gender", strings[4]);
                    data.put("address", strings[5]);
                    data.put("contact", strings[6]);

                    data.put("billID", strings[7]);
                    data.put("illness", strings[8]);
                    data.put("condition", strings[9]);
                    data.put("xRay", strings[10]);
                    data.put("ctScan",strings[11]);
                    data.put("covidTest", strings[12]);
                    data.put("room", strings[13]);
                    data.put("days", strings[14]);
                    data.put("tax", strings[15]);
                    data.put("totalPrice", strings[16]);

                    return data;
                }
            };

            RequestQueue rQueue = Volley.newRequestQueue(con);
            rQueue.add(strRequest);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            prd.dismiss();
        }
    }

    class InsertPaymentData extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(final String... strings) {
            StringRequest strRequest = new StringRequest(Request.Method.POST, ipAddress + "projects/AndroidServers/TheMedicalGCs/InsertPaymentData.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap();
                    data.put("paymentID", strings[0]);
                    data.put("bankAcc", strings[1]);
                    data.put("billID", strings[2]);
                    data.put("insureName", strings[3]);
                    data.put("discount", strings[4]);
                    data.put("amountPaid", strings[5]);
                    data.put("balance", strings[6]);

                    return data;
                }
            };

            RequestQueue rQueue = Volley.newRequestQueue(con);
            rQueue.add(strRequest);
            return null;
        }
    }
}
