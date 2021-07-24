package com.example.themedicalc;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.themedicalc.datamanipulate.FetchData;
import com.example.themedicalc.datamanipulate.InsertData;

public class PatientInput extends Fragment implements View.OnClickListener{
    private View contentView;

    //Patients information
    private EditText etFName, etLName, etAge, etAddress, etContact;
    private RadioGroup rbGroupGender;
    //Patients information end

    //Medical Information
    private EditText etIll;
    private RadioGroup rbGroupCondition;
    private CheckBox chkXRay, chkCtScan, chkCovid;//Chk Lab test
    private EditText etXRay, etCtScan, etCovid;//Edit text lab test
    private RadioGroup rbGroupRoom;
    private EditText etDaysSpent;
    //Medical information end

    //Button Submit
    private Button btnSubmit;

    //Patient Results
    private String firstname, lastname, age, gender, address, contact;

    //Medical Info Result
    private String illness, condition, xRay, ctScan, covidTest, room, days;

    double tax, totalFee;

    private InsertData insertData;
    private FetchData fetchData;
    private String id[] = new String[3];
    private Payment fragPayment;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_patient_input, container, false);

        instantiatePatient();
        instantiateMedicalInfo();
        checkBoxFunction(chkXRay, chkCtScan, chkCovid, etXRay, etCtScan, etCovid);

        btnSubmit = contentView.findViewById(R.id.submit);
        btnSubmit.setOnClickListener(this);

        fragPayment = new Payment();

        insertData = new InsertData(getContext(), MainActivity.IP_ADDRESS);
        fetchData = new FetchData(getContext(), MainActivity.IP_ADDRESS);
        fetchData.getJSONID();

        return contentView;
    }

    @Override
    public void onClick(View v) {
        if(v == btnSubmit){
            id = fetchData.getId();
            getDataFromText();
            popUpWindow();
        }
    }

    public void popUpWindow(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_window);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        if(days.isEmpty()){
            days = String.valueOf(0);
        }
        if(xRay.isEmpty()){
            xRay = String.valueOf(0);
        }
        if(ctScan.isEmpty()){
            ctScan = String.valueOf(0);
        }
        if(covidTest.isEmpty()){
            covidTest = String.valueOf(0);
        }

        createReceipt(dialog);

        TextView btnClose = dialog.findViewById(R.id.btnClose);
        Button btnPay = dialog.findViewById(R.id.btnPay);

        btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                insertData.insertPatientAndMedical(firstname, lastname, age, gender, address, contact,
                        illness, condition, xRay, ctScan, covidTest, room, days, String.valueOf(tax), String.valueOf(totalFee)
                        ,id[0], id[1]);
                dialog.dismiss();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragPayment).commit();
            }
        });
    }

    public void createReceipt(Dialog dialog){
        TextView txtBillID = dialog.findViewById(R.id.txtBillID);
        TextView txtFullname = dialog.findViewById(R.id.txtFullname);
        TextView txtAge = dialog.findViewById(R.id.txtAge);
        TextView txtGender = dialog.findViewById(R.id.txtGender);
        TextView txtAddress = dialog.findViewById(R.id.txtAddress);
        TextView txtContact = dialog.findViewById(R.id.txtContact);
        TextView txtIllness = dialog.findViewById(R.id.txtIllness);
        TextView qtyXRay = dialog.findViewById(R.id.qtyXRay);
        TextView qtyCTScan = dialog.findViewById(R.id.qtyCTScan);
        TextView qtyCovidTest = dialog.findViewById(R.id.qtyCovidTest);
        TextView priceXRay = dialog.findViewById(R.id.priceXRay);
        TextView priceCTScan = dialog.findViewById(R.id.priceCTScan);
        TextView priceCovidTest = dialog.findViewById(R.id.priceCovidTest);
        TextView txtTotalLabFee = dialog.findViewById(R.id.txtTotalLabFee);
        TextView txtRoomFee = dialog.findViewById(R.id.txtRoomPrice);
        TextView txtDaysSpent = dialog.findViewById(R.id.daysSpent);
        TextView txtTax = dialog.findViewById(R.id.txtTax);
        TextView txtTotalFee = dialog.findViewById(R.id.txtTotalFee);

        txtBillID.setText(id[1]);
        txtFullname.setText(firstname + " " + lastname);
        txtAge.setText(age);
        txtGender.setText(gender);
        txtAddress.setText(address);
        txtContact.setText(contact);
        txtIllness.setText(illness + "(" + condition + ")");
        qtyXRay.setText(xRay);
        qtyCTScan.setText(ctScan);
        qtyCovidTest.setText(covidTest);

        int xRayTotalPrice = (Integer.parseInt(xRay) *  500);
        priceXRay.setText("₱ " + xRayTotalPrice);

        int ctScanTotalPrice = (Integer.parseInt(ctScan) * 2500);
        priceCTScan.setText("₱ " + ctScanTotalPrice);

        int covidTotalPrice = (Integer.parseInt(covidTest) * 500);
        priceCovidTest.setText("₱ " + covidTotalPrice);

        int totalLabfee = xRayTotalPrice + ctScanTotalPrice + covidTotalPrice;
        txtTotalLabFee.setText("₱ " + totalLabfee);

        txtRoomFee.setText(room);
        int roomFee = 0;
        if(room.equalsIgnoreCase("ICU-₱1500/day")){
            roomFee = 1500;
        }
        else{
            roomFee = 500;
        }

        txtDaysSpent.setText("" + days);

        totalFee = (roomFee * Integer.parseInt(days)) + totalLabfee;

        tax = totalFee * .12;
        txtTax.setText("₱ " + tax);

        totalFee = totalFee + tax;
        txtTotalFee.setText("₱ " + totalFee);

        Bundle data = new Bundle();
        data.putDouble("TOTAL_FEE", totalFee);
        data.putString("BILL_ID", id[1]);
        data.putString("PAYMENT_ID", id[2]);
        fragPayment.setArguments(data);
    }

    public void getDataFromText(){
        firstname = etFName.getText().toString();
        lastname = etLName.getText().toString();
        age = etAge.getText().toString();
        gender = getRadioValue(rbGroupGender);
        address = etAddress.getText().toString();
        contact = etContact.getText().toString();

        illness = etIll.getText().toString();
        condition = getRadioValue(rbGroupCondition);
        xRay = etXRay.getText().toString();
        ctScan = etCtScan.getText().toString();
        covidTest = etCovid.getText().toString();
        room = getRadioValue(rbGroupRoom);
        days = etDaysSpent.getText().toString();
    }

    public void instantiatePatient(){
        etFName = contentView.findViewById(R.id.firstname);
        etLName = contentView.findViewById(R.id.lastname);
        etAge = contentView.findViewById(R.id.age);
        rbGroupGender = contentView.findViewById(R.id.groupGender);
        etAddress = contentView.findViewById(R.id.address);
        etContact = contentView.findViewById(R.id.contact);
        contact = etContact.getText().toString();
        rbGroupRoom = contentView.findViewById(R.id.groupRoomType);
        etDaysSpent = contentView.findViewById(R.id.daysSpent);
    }

    public void instantiateMedicalInfo(){
        etIll = contentView.findViewById(R.id.illness);
        rbGroupCondition = contentView.findViewById(R.id.groupCondition);
        chkXRay = contentView.findViewById(R.id.xRay);
        chkCtScan = contentView.findViewById(R.id.ctScan);
        chkCovid = contentView.findViewById(R.id.covidTest);
        etXRay = contentView.findViewById(R.id.numXRay);
        etCtScan = contentView.findViewById(R.id.numCTScan);
        etCovid = contentView.findViewById(R.id.numCovidTest);
    }

    //Radio function for Gender
    public String getRadioValue(RadioGroup rbGroup){
        int id = rbGroup.getCheckedRadioButtonId();
        RadioButton radioButton = contentView.findViewById(id);
        return radioButton.getText().toString();
    }

    //Checkbox function for Laboratory
    public void checkBoxFunction(CheckBox chk1, CheckBox chk2, CheckBox chk3, final EditText ...et){
        chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et[0].setVisibility(View.VISIBLE);
                }
                else{
                    et[0].setVisibility(View.INVISIBLE);
                }
            }
        });

        chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et[1].setVisibility(View.VISIBLE);
                }
                else{
                    et[1].setVisibility(View.INVISIBLE);
                }
            }
        });

        chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et[2].setVisibility(View.VISIBLE);
                }
                else{
                    et[2].setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}