package com.example.mdalite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mdalite.MainActivity;

public class DiagnosisResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvDiagnosisResult = findViewById(R.id.tvDiagnosisResult);
        TextView tvConfidence = findViewById(R.id.tvConfidence);
        Button btnBack = findViewById(R.id.btnBack);

        String diagnosis = getIntent().getStringExtra("diagnosisResult");
        float confidence = getIntent().getFloatExtra("confidenceScore", 0.0f);

        tvDiagnosisResult.setText("Diagnosis: " + diagnosis);
        tvConfidence.setText("Confidence: " + String.format("%.2f", confidence));

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(DiagnosisResultActivity.this, MainActivity.class));
            finish();
        });
    }
}
