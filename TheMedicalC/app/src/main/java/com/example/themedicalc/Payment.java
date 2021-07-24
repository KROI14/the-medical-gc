package com.example.themedicalc;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themedicalc.dataholder.BankHolder;
import com.example.themedicalc.dataholder.InsuranceHolder;
import com.example.themedicalc.datamanipulate.FetchData;
import com.example.themedicalc.datamanipulate.InsertData;
import com.example.themedicalc.datamanipulate.UpdateData;

import java.util.ArrayList;

public class Payment extends Fragment implements AdapterView.OnItemSelectedListener {
    private View view;

    private Spinner bankSpinner, insurSpinner;

    private EditText etInsureNum, etAccNum;

    private Button btnPay, btnShowBank;

    private TextView txtBankOwner, txtBankMoney, txtTotalFee;

    private LinearLayout showDetails;

    private FetchData fetchData;
    private InsertData insertData;
    private UpdateData updateData;

    private double totalFee;
    private String billID, paymentID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        setItemsInSpinner();
        etInsureNum = view.findViewById(R.id.insuranceNum);
        etAccNum = view.findViewById(R.id.accNum);
        insurSpinner.setOnItemSelectedListener(this);

        showDetails = view.findViewById(R.id.bankDetailsContainer);

        fetchData = new FetchData(getContext(), MainActivity.IP_ADDRESS);
        fetchData.requestBankInsurance();

        insertData = new InsertData(getContext(), MainActivity.IP_ADDRESS);

        updateData = new UpdateData(getContext(), MainActivity.IP_ADDRESS);

        txtBankMoney = view.findViewById(R.id.txtBankMoney);
        txtBankOwner = view.findViewById(R.id.txtBankOwner);

        txtTotalFee = view.findViewById(R.id.txtTotalFee);
        totalFee = (getArguments() != null ? getArguments().getDouble("TOTAL_FEE") : 0.0);
        billID = (getArguments() != null ? getArguments().getString("BILL_ID") : "No ID");
        paymentID = (getArguments() != null ? getArguments().getString("PAYMENT_ID") : "No ID");
        txtTotalFee.setText("Total fee: ₱" + totalFee);

        buttonManipulation();

        return view;
    }

    public void buttonManipulation(){
        final ArrayList<BankHolder> bankList = fetchData.getBankList();
        final ArrayList<InsuranceHolder> insureList = fetchData.getInsuranceList();

        final String bankName[] = {""};
        final String accNum[] = {""};
        final int money[] = {0};

        final String insureName[] = {""};
        final double discounted[] = {0.0};
        final double balance[] = {0.0};
        final double newTotal[] = {0.0};

        btnShowBank = view.findViewById(R.id.btnShowBank);
        btnShowBank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String bankNameInput = bankSpinner.getSelectedItem().toString();
                String accNumInput = etAccNum.getText().toString();

                boolean isShowBankDetails = true;
                for(int i = 0; i < bankList.size(); i++){
                    if(bankList.get(i).getBankName().equals(bankNameInput) && bankList.get(i).getAccNum().equals(accNumInput)){
                        showDetails.setVisibility(View.VISIBLE);

                        txtBankOwner.setText(bankList.get(i).getBankOwner());
                        txtBankMoney.setText(bankList.get(i).getMoney() + "");

                        bankName[0] = bankList.get(i).getBankOwner();
                        accNum[0] = bankList.get(i).getAccNum();
                        money[0] = bankList.get(i).getMoney();

                        isShowBankDetails = true;
                        break;
                    }
                    else{
                        showDetails.setVisibility(View.GONE);
                        isShowBankDetails = false;
                    }
                }
                if(!isShowBankDetails){
                    Toast.makeText(getContext(), "Bank account not found", Toast.LENGTH_SHORT).show();
                }

                if(insurSpinner.getSelectedItem().toString().equals("none")){
                    newTotal[0] = totalFee;
                    insureName[0] = "N/A";
                    discounted[0] = 0.0;
                    txtTotalFee.setText("Total fee: ₱" + totalFee);
                }
                else{
                    String insureNameInput = insurSpinner.getSelectedItem().toString();
                    String insureNumInput = etInsureNum.getText().toString();
                    boolean isInsureAvail = true;
                    for(int i = 0; i < insureList.size(); i++){
                        if(insureList.get(i).getName().equals(insureNameInput) && insureList.get(i).getInsurNum().equals(insureNumInput)){
                            insureName[0] = insureList.get(i).getName();
                            discounted[0] = totalFee * insureList.get(i).getDiscount();
                            isInsureAvail = true;
                            break;
                        }
                        else{
                            isInsureAvail = false;
                        }
                    }
                    if(isInsureAvail){
                        newTotal[0] = totalFee - discounted[0];

                        txtTotalFee.setText("Total fee: ₱" + totalFee + "\nNew Total fee: ₱" + newTotal[0]);
                    }
                    else{
                        newTotal[0] = totalFee;
                        insureName[0] = "N/A";
                        discounted[0] = 0.0;
                        txtTotalFee.setText("Total fee: ₱" + totalFee);
                    }
                }
            }
        });

        btnPay = view.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(showDetails.getVisibility() == View.VISIBLE){
                    if(newTotal[0] > money[0]){
                        Toast.makeText(getContext(), "Insufficient funds", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        double newMoney = money[0] - newTotal[0];
                        balance[0] = newTotal[0] - money[0];
                        if(balance[0] <= 0){
                            balance[0] = 0;
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("PAYMENT_ID", paymentID);

                        Receipt res = new Receipt();
                        res.setArguments(bundle);

                        insertData.insertPaymentData(paymentID, accNum[0], billID, insureName[0], discounted[0], newTotal[0], balance[0]);
                        updateData.updateMoney(accNum[0], newTotal[0]);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, res).commit();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Insert your bank account first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String theChosen1 = insurSpinner.getSelectedItem().toString();
        if(theChosen1.equalsIgnoreCase("none")){
            etInsureNum.setVisibility(View.INVISIBLE);
        }
        else{
            etInsureNum.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void setItemsInSpinner(){
        bankSpinner = view.findViewById(R.id.bankList);

        ArrayList<String> bankList = new ArrayList<>();
        bankList.add("BDO Unibank INC");
        bankList.add("Bank of the Phil Islands");
        bankList.add("Land Bank of The Philippines");
        bankList.add("Phil National Bank");
        bankList.add("Metropolitan Bank and TCO");

        ArrayAdapter adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, bankList);
        bankSpinner.setAdapter(adapter);

        insurSpinner = view.findViewById(R.id.insuranceList);
        ArrayList<String> insuranceList = new ArrayList();
        insuranceList.add("none");
        insuranceList.add("Sun life");
        insuranceList.add("Maxicare");
        insuranceList.add("Medicard");
        adapter = new ArrayAdapter(getContext(), R.layout.spinner_item, insuranceList);
        insurSpinner.setAdapter(adapter);
    }
}