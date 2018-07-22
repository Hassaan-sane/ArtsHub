package com.example.sane.onlinestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sane.onlinestore.Models.TblUser;
import com.example.sane.onlinestore.Models.TblUserDetail;

import java.io.Serializable;

public class UserAddActivity extends AppCompatActivity {

    String strname=null,strphone=null,straddress=null,Role =null;

    TblUser tblUser;
    TblUserDetail tblUserDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);

        final EditText name,phone,address;
        Button btnSignupFinal;
        final RadioGroup RadioGroup;

        name = findViewById(R.id.NameTextView);
        phone = findViewById(R.id.PhoneTextView);
        address = findViewById(R.id.AddressTextView);

        btnSignupFinal =findViewById(R.id.btnFinalSignUp);
        RadioGroup = findViewById(R.id.RGSignUp);

        final RadioButton RArtist = findViewById(R.id.RArtist);
        RadioButton RUser = findViewById(R.id.RUser);

        btnSignupFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strname = name.getText().toString();
                strphone = phone.getText().toString();
                straddress = address.getText().toString();
//

                RadioButton Radiobtnselected = null;
                if (RadioGroup.getCheckedRadioButtonId() == -1) {
                    RadioGroup.check(R.id.RUser);
                } else {
                    int selected = RadioGroup.getCheckedRadioButtonId();
                    Radiobtnselected = findViewById(selected);

                    if (Radiobtnselected==RArtist) {
                        Role = "R";
                    } else {
                        Role = "U";
                    }
                }

                Toast.makeText(UserAddActivity.this, "" + Radiobtnselected.getText().toString()+"   "+Role, Toast.LENGTH_SHORT).show();

                Intent interUser = new Intent(getApplicationContext(), SignUpActivity.class);
                interUser.putExtra("Name", strname);
                interUser.putExtra("Address", straddress);
                interUser.putExtra("Phone", strphone);
                interUser.putExtra("Role", Role);
                startActivity(interUser);

            }
        });


    }
}
