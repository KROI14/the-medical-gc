package com.example.themedicalc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Receipt extends Fragment {
    private View view;
    private TextView txtName, txtAddress, txtContact;
    private TextView txtQtyXray, txtQtyCtScan, txtQtyCovidTest;
    private TextView txtPriceXray, txtPriceCtScan, txtPriceCovidTest, txtTotalLabFee;
    private TextView txtRoomPrice, txtDaysSpent, txtTax, txtTotalFee, txtInsureName, txtDiscounted, txtAmountPaid, txtBalance;

    private Button btnHome;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receipt, container, false);

        txtName = view.findViewById(R.id.txtName);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtContact = view.findViewById(R.id.txtContact);

        txtQtyXray = view.findViewById(R.id.txtQtyXRay);
        txtQtyCtScan = view.findViewById(R.id.txtQtyCtScan);
        txtQtyCovidTest = view.findViewById(R.id.txtQtyCovidTest);

        txtPriceXray = view.findViewById(R.id.txtPriceXray);
        txtPriceCtScan = view.findViewById(R.id.txtPriceCtScan);
        txtPriceCovidTest = view.findViewById(R.id.txtPriceCovidTest);

        txtTotalLabFee = view.findViewById(R.id.txtTotalLabFee);

        txtRoomPrice = view.findViewById(R.id.txtRoomPrice);
        txtDaysSpent = view.findViewById(R.id.txtDaysSpent);
        txtTax = view.findViewById(R.id.txtTax);
        txtTotalFee = view.findViewById(R.id.txtTotalFee);
        txtInsureName = view.findViewById(R.id.txtInsuranceName);
        txtDiscounted = view.findViewById(R.id.txtDiscounted);
        txtAmountPaid = view.findViewById(R.id.txtAmountPaid);
        txtBalance = view.findViewById(R.id.txtBalance);

        btnHome = view.findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Menu()).commit();
            }
        });

        getReceipt();

        return  view;
    }

    public void getReceipt(){
        final ProgressDialog prd = new ProgressDialog(getContext());
        prd.setCancelable(false);
        prd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prd.setMessage("Loading...");
        prd.setCancelable(false);
        prd.setCanceledOnTouchOutside(false);
        prd.show();

        StringRequest strRequest = new StringRequest(com.android.volley.Request.Method.POST, MainActivity.IP_ADDRESS+"projects/AndroidServers/TheMedicalGCs/FetchReceipt.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jo = jsonArray.getJSONObject(0);

                            String name = jo.getString("Firstname") + " " + jo.getString("Lastname");
                            txtName.setText(name);

                            String address = jo.getString("Address");
                            txtAddress.setText(address);

                            String contact = jo.getString("Contact");
                            txtContact.setText(contact);

                            int xRay = jo.getInt("Xray");
                            txtQtyXray.setText("" + xRay);

                            int ctScan = jo.getInt("CTScan");
                            txtQtyCtScan.setText("" + ctScan);

                            int covidTest = jo.getInt("CovidTest");
                            txtQtyCovidTest.setText("" + covidTest);
                            calculateLabFees(xRay, ctScan, covidTest);

                            String roomPrice = jo.getString("Room");
                            txtRoomPrice.setText(roomPrice);

                            String daysSpent = jo.getString("Days");
                            txtDaysSpent.setText(daysSpent);

                            String tax = jo.getString("Tax");
                            txtTax.setText(tax);

                            String totalFee = jo.getString("TotalPrice");
                            txtTotalFee.setText(totalFee);

                            String insurance = jo.getString("InsuranceName");
                            txtInsureName.setText(insurance);

                            String dc = jo.getString("Discount");
                            txtDiscounted.setText(dc);

                            String amountPaid = jo.getString("AmountPaid");
                            txtAmountPaid.setText(amountPaid);

                            String balance = jo.getString("Balance");
                            txtBalance.setText(balance);

                            prd.dismiss();
                        }
                        catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            prd.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap();
                map.put("paymentID", getArguments().getString("PAYMENT_ID"));
                return map;
            }
        };
        Volley.newRequestQueue(getContext()).add(strRequest);
    }

    public void calculateLabFees(int xRay, int ctScan, int covidTest){
        int xRayPrice = xRay * 500;
        txtPriceXray.setText("" +  xRayPrice);

        int ctScanPrice = ctScan * 2500;
        txtPriceCtScan.setText("" + ctScanPrice);

        int covidTestPrice = covidTest  * 500;
        txtPriceCovidTest.setText("" + covidTestPrice);

        int totalLabFee = xRayPrice + ctScanPrice + covidTestPrice;
        txtTotalLabFee.setText("" + totalLabFee);
    }
}
