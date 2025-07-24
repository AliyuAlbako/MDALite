package com.yourcompany.medicalassistant;

import android.content.Context;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class AIInferenceHelper {
    private Interpreter interpreter;

    public AIInferenceHelper(Context context, String modelPath) {
        try {
            interpreter = new Interpreter(loadModelFile(context, modelPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MappedByteBuffer loadModelFile(Context context, String path) throws IOException {
        FileInputStream fis = new FileInputStream(context.getAssets().openFd(path).getFileDescriptor());
        FileChannel fileChannel = fis.getChannel();
        long startOffset = context.getAssets().openFd(path).getStartOffset();
        long declaredLength = context.getAssets().openFd(path).getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public float[][] doInference(float[][] inputData) {
        float[][] output = new float[1][1];
        interpreter.run(inputData, output);
        return output;
    }
}
