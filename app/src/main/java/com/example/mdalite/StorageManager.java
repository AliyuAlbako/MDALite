package com.yourcompany.medicalassistant;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;

public class StorageManager {
    private Context context;
    private static final String FILE_NAME = "diagnosis_logs.json";

    public StorageManager(Context context) {
        this.context = context;
    }

    public void saveDiagnosisLog(String patient, String result, float confidence) {
        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            JSONArray logs;
            if (file.exists()) {
                FileReader reader = new FileReader(file);
                char[] buffer = new char[(int) file.length()];
                reader.read(buffer);
                reader.close();
                logs = new JSONArray(new String(buffer));
            } else {
                logs = new JSONArray();
            }

            JSONObject entry = new JSONObject();
            entry.put("patient", patient);
            entry.put("diagnosis", result);
            entry.put("confidence", confidence);
            entry.put("timestamp", System.currentTimeMillis());
            logs.put(entry);

            FileWriter writer = new FileWriter(file);
            writer.write(logs.toString());
            writer.close();

        } catch (Exception e) {
            Log.e("StorageManager", "Failed to save log: " + e.getMessage());
        }
    }
}
