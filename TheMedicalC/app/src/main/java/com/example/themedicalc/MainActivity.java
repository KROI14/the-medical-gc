package com.example.themedicalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    public static final String IP_ADDRESS = "http://192.168.254.113/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Menu()).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if(fragment instanceof Menu){
            super.onBackPressed();
        }
        else{
            if(fragment instanceof Payment){
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setCancelable(false);
                alertDialog.setTitle(Html.fromHtml("<font color='#FFCC00'>Warning!</font>"));
                alertDialog.setMessage("You are in the middle of transaction, are you sure you want to exit?");
                alertDialog.setPositiveButton(Html.fromHtml("<font color='#00000'>Yes</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Menu()).commit();
                        alertDialog.create().dismiss();
                    }
                });
                alertDialog.setNegativeButton(Html.fromHtml("<font color='#00000'>No</font>"), null);
                alertDialog.show();
            }
            else if(fragment instanceof PatientInput){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Menu()).commit();
            }
        }
    }
}