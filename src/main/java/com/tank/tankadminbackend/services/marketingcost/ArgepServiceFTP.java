package com.tank.tankadminbackend.services.marketingcost;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ArgepServiceFTP {
    private float sumAdCoast;
    private final String shopCPC;
    private final String shopGS;
    private final String ftpUrl;
    private final String userName;
    private final String password;

    public ArgepServiceFTP(String shopCPC, String shopGS, String ftpUrl, String userName, String password) {
        this.shopCPC = shopCPC;
        this.shopGS = shopGS;
        this.ftpUrl = ftpUrl;
        this.userName = userName;
        this.password = password;
        this.sumAdCoast = 0;
    }

    private List<String[]> getArgepReports(String date) throws IOException {
        String cpcFileName = shopCPC + "_" + date + ".csv";
        String gsFileName = shopGS + "_" + date + ".csv";
        List<String[]> reports = new ArrayList<>();
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpUrl);
        ftpClient.login(userName, password);
        FTPFile[] files = ftpClient.listFiles();
        for (FTPFile file : files) {
            String fileName = file.getName();
            if (fileName.equals(cpcFileName)) {
                String[] cpcReport = getReportList(ftpClient, cpcFileName);
                reports.add(cpcReport);
            }

            if (fileName.equals(gsFileName)) {
                String[] gsReport = getReportList(ftpClient, gsFileName);
                reports.add(gsReport);
            }
        }
        return reports;
    }


    private String[] getReportList(FTPClient ftpClient, String fileName) throws IOException {
        InputStream inputStream = ftpClient.retrieveFileStream(fileName);
        int i;
        StringBuilder response = new StringBuilder();
        byte[] res = new byte[2048];
        while ((i = inputStream.read(res)) != -1) {
            response.append(new String(res, 0, i));
        }
        inputStream.close();
        ftpClient.completePendingCommand();
        return response.toString().split(System.lineSeparator());
    }


    public float getSumOfAdCost(String date) throws IOException {
        List<String[]> reports = getArgepReports(date);
        for (String[] report : reports) {
            for (int i = 1; i < report.length; i++) {
                String[] attributes = report[i].split(";");
                sumAdCoast += Integer.parseInt(attributes[3].replaceAll("\"", ""));
            }
        }
        return sumAdCoast;
    }
}
