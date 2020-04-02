package com.samir.luniva.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.samir.luniva.R;
import com.samir.luniva.databinding.ActivityClinicBinding;
import com.samir.luniva.helpers.ShowToast;

public class ClinicActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityClinicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityClinicBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.textview.setText("Hello");
        binding.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button:
                ShowToast.withLongMessage(binding.edittext.getText().toString());
        }
    }
}
