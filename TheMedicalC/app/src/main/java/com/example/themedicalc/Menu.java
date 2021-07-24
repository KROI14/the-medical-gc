package com.example.themedicalc;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Menu extends Fragment implements View.OnClickListener{
    private View contentView;
    private Button btnAddPatient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView =  inflater.inflate(R.layout.fragment_menu, container, false);

        btnAddPatient = contentView.findViewById(R.id.addPatient);
        btnAddPatient.setOnClickListener(this);

        return contentView;
    }

    @Override
    public void onClick(View v) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new PatientInput()).commit();
    }
}