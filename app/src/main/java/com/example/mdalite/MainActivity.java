package com.example.mdalite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ImageView imageView;
    private Button btnCapture, btnDiagnose;
    private Bitmap capturedBitmap;
    private AIInferenceHelper inferenceHelper;
    private StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnCapture = findViewById(R.id.btnCapture);
        btnDiagnose = findViewById(R.id.btnDiagnose);

        inferenceHelper = new AIInferenceHelper(this, "models/malaria_model.tflite");
        storageManager = new StorageManager(this);

        btnCapture.setOnClickListener(v -> {
            // Implement camera capture logic here
            Toast.makeText(this, "Camera capture not yet wired.", Toast.LENGTH_SHORT).show();
        });

        btnDiagnose.setOnClickListener(v -> {
            if (capturedBitmap == null) {
                Toast.makeText(this, "Capture an image first", Toast.LENGTH_SHORT).show();
                return;
            }

            float[][] inputData = ImagePreprocessor.preprocessImage(capturedBitmap);
            float[][] result = inferenceHelper.doInference(inputData);
            float confidenceScore = result[0][0];
            String diagnosis = confidenceScore > 0.7 ? "Malaria Positive" : "Negative";

            storageManager.saveDiagnosisLog("Test Patient", diagnosis, confidenceScore);

            Intent intent = new Intent(MainActivity.this, DiagnosisResultActivity.class);
            intent.putExtra("diagnosisResult", diagnosis);
            intent.putExtra("confidenceScore", confidenceScore);
            startActivity(intent);
        });
    }
}
