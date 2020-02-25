package com.danieljames.cuber.letters;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LettersModel implements Serializable {

    Context context;
    int rowLimit = 26;
    int colLimit = 26;
    public String[][] letters = new String[rowLimit][colLimit];
    public int[][] points = new int[rowLimit][colLimit];
    public String[] keyList = {};
    public String[] valueList = {};
    public Integer[] pointList = {};
    String rootPath;
    String fileName = "cuber_letters.txt";
    public static LettersModel lettersModel;

    public LettersModel(Context context) {
        this.context = context;
        this.rootPath = context.getExternalFilesDir(null).getAbsolutePath() + "/Cuber/";
        readFile();
    }

    public void updatePoints(String key) {
        int rowIndex = (int) key.charAt(0) - (int) (new Character('A'));
        int colIndex = (int) key.charAt(1) - (int) (new Character('A'));
        if (rowIndex >= 0 && rowIndex < rowLimit && colIndex >= 0 && colIndex < colLimit) {
            points[rowIndex][colIndex] += 1;
            saveFile();
        }
    }

    public int getPoints(String key) {
        int rowIndex = (int) key.charAt(0) - (int) (new Character('A'));
        int colIndex = (int) key.charAt(1) - (int) (new Character('A'));
        return points[rowIndex][colIndex];
    }

    public void updatePair(String key, String value) {
        readLine(key + " " + value + " 0");
        saveFile();
    }

    private void readLine(String line) {
        int rowIndex = (int) line.charAt(0) - (int) (new Character('A'));
        int colIndex = (int) line.charAt(1) - (int) (new Character('A'));
        if (rowIndex >= 0 && rowIndex < rowLimit && colIndex >= 0 && colIndex < colLimit) {
            letters[rowIndex][colIndex] = line.substring(3, line.length() - 2).trim();
            points[rowIndex][colIndex] = line.charAt(line.length() - 1) - '0';
        }
    }

    private void readFile() {
        File file = new File(rootPath + fileName);
        if (file.exists()) {
            try {
                InputStream in = new FileInputStream(file);
                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                while (line != null) {
                    readLine(line);
                    line = reader.readLine();
                }
                in.close();
                setKeyValuePairs();
            } catch (IOException e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveFile() {
        setKeyValuePairs();
        try {
            File folder = new File(rootPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(rootPath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file);
            for (int i = 0; i < rowLimit; i++) {
                for (int j = 0; j < colLimit; j++) {
                    if (letters[i][j] != null) {
                        String line = String.valueOf((char) (new Character('A') + i)) +
                                String.valueOf((char) (new Character('A') + j)) +
                                " " +
                                letters[i][j] + " " + String.valueOf(points[i][j]) + "\n";
                        out.write(line.getBytes());
                    }
                }
            }
            out.close();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setKeyValuePairs() {
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<Integer> pointListTemp = new ArrayList<>();
        for (int i = 0; i < rowLimit; i++) {
            for (int j = 0; j < colLimit; j++) {
                if (letters[i][j] != null) {
                    keys.add(Character.toString((char) (new Character('A') + i)) +
                            ((char) (new Character('A') + j)));
                    values.add(letters[i][j]);
                    pointListTemp.add(points[i][j]);
                }
            }
        }
        this.keyList = keys.toArray(new String[0]);
        this.valueList = values.toArray(new String[0]);
        this.pointList = pointListTemp.toArray(new Integer[0]);
    }

    public void importFile(Uri uri) {
        this.letters = new String[rowLimit][colLimit];
        try {
            InputStream in = context.getContentResolver().openInputStream(uri);
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(in));
            String line = reader.readLine();
            while (line != null) {
                readLine(line);
                line = reader.readLine();
            }
            in.close();
            saveFile();
        } catch (IOException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void reset() {
        for (int i = 0; i < rowLimit; i++) {
            for (int j = 0; j < colLimit; j++) {
                this.points[i][j] = 0;
                saveFile();
            }
        }
    }
}
