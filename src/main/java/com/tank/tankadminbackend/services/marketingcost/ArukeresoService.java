package com.tank.tankadminbackend.services.marketingcost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tank.tankadminbackend.models.arukereso.ArukeresoHashCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ArukeresoService {
    private final String arukeresoApiKey;
    private float sumAdCoast;

    public ArukeresoService(String arukeresoApiKey) {
        this.arukeresoApiKey = arukeresoApiKey;
        this.sumAdCoast = 0;
    }


    private String sendGetHashCodeRequest(String date) throws IOException {
        String link = "https://ppapi.arukereso.com/v1.0/Stat/GenerateExport?DateFrom=" + date + "&DateTill=" + date;
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Api-Key", arukeresoApiKey);
        connection.setDoOutput(true);
        connection.setUseCaches(true);

        InputStream inputStream = connection.getInputStream();
        byte[] res = new byte[2048];
        int i;
        StringBuilder response = new StringBuilder();
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();

        return response.toString();
    }


    String getArukeresoHashCode(String date) throws IOException, InterruptedException {
        String response = sendGetHashCodeRequest(date);
        ObjectMapper mapper = new ObjectMapper();
        ArukeresoHashCode arukeresoHashCode = mapper.readValue(response, ArukeresoHashCode.class);
        Thread.sleep(3000);
        return arukeresoHashCode.getArukeresoHashCode();
    }


    List<String> getArukeresoReport(String arukeresoHashCode) throws IOException {

        URL url = new URL("https://ppapi.arukereso.com/v1.0/Stat/Download?Hash=" + arukeresoHashCode);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setConnectTimeout(20000);
        connection.setReadTimeout(20000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Api-Key", arukeresoApiKey);
        connection.setRequestProperty("Content-Type", "text/csv;charset=UTF-8");
        connection.setDoOutput(true);
        connection.setUseCaches(true);

        BufferedReader bufReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

        String line;
        List<String> lines = new ArrayList<>();
        while ((line = bufReader.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }


    private float getSumOfAdCost(List<String> report) {
        for (int i = 1; i < report.size() - 6; i++) {
            String[] attributes = report.get(i).split(";");
            sumAdCoast += Float.parseFloat(attributes[3]) / 1.27;
        }
        return sumAdCoast;
    }
}
