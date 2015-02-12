package com.codi.mvptest.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Codi on 2015/2/10.
 */
public class FileManager {

    private FileManager() {}

    private static class LazyHolder {
        private static final FileManager INSTANCE = new FileManager();
    }

    public static FileManager getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void writeToFile(File file, String fileContent) {
        if(!file.exists()) {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write(fileContent);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String readFileContent(File file) {
        StringBuilder fileBuilder = new StringBuilder();
        if(file.exists()) {
            try {
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    fileBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileBuilder.toString();
    }

    public boolean exists(File file) {
        return file.exists();
    }

    public void clearDirectory(File directory) {
        if(directory.exists()) {
            for(File file : directory.listFiles()) {
                file.delete();
            }
        }
    }

    public void writeToPreferences(Context context, String preferenceFileName, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(preferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getFromPreferences(Context context, String preferenceFileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }
}
