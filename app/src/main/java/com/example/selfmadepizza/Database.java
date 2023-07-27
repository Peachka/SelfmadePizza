package com.example.selfmadepizza;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class Database {
    private static final String FILE_NAME = "orders.database";

    public static void save(Context context, String order) {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);

            writer.write(order + "\n");
            writer.flush();
            writer.close();

            Toast.makeText(context, "Збережено замовлення", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(context, "Помилка збереження", Toast.LENGTH_SHORT).show();
        }
    }

    public static String[] load(Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));

            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            reader.close();

            String content = contentBuilder.toString().trim();

            if (content.isEmpty()) {
                return new String[0];
            }

            Toast.makeText(context, "Завантажено замовлення", Toast.LENGTH_SHORT).show();
            return content.split("\n");
        } catch (IOException ex) {
            return new String[0];
        }
    }

    public static void clear(Context context) {
        context.deleteFile(FILE_NAME);
        Toast.makeText(context, "Усі замовлення видалено", Toast.LENGTH_SHORT).show();
    }
}
