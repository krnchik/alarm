package com.krnchik.model.history;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HistoryModel implements History {

    String path = "history.txt";

    public HistoryModel() {
        new File(path);
    }

    @Override
    public void write(Date date, TimeZone timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy z HH:mm");
        sdf.setTimeZone(timeZone);
        try {
            String data = sdf.format(date) + "\n";
            Files.write(Paths.get(path),
                    data.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> giveHistory() {
        List<String> history = new ArrayList<>();
        try {
            history.addAll(Files
                    .readAllLines(Paths
                            .get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }
}
