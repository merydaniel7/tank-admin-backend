package com.tank.tankadminbackend.services.marketingcost;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;

import com.google.api.services.analyticsreporting.v4.model.ColumnHeader;
import com.google.api.services.analyticsreporting.v4.model.DateRange;
import com.google.api.services.analyticsreporting.v4.model.DateRangeValues;
import com.google.api.services.analyticsreporting.v4.model.GetReportsRequest;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import com.google.api.services.analyticsreporting.v4.model.Metric;
import com.google.api.services.analyticsreporting.v4.model.MetricHeaderEntry;
import com.google.api.services.analyticsreporting.v4.model.Report;
import com.google.api.services.analyticsreporting.v4.model.ReportRequest;
import com.google.api.services.analyticsreporting.v4.model.ReportRow;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GoogleAnalyticsService {
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private final String VIEW_ID;
    private final String KEY_FILE_LOCATION;
    private final String APPLICATION_NAME;
    private float sumAdCoast;


    public GoogleAnalyticsService(String keyFileLocation, String viewId, String applicationName) {
        this.KEY_FILE_LOCATION = keyFileLocation;
        this.VIEW_ID = viewId;
        this.APPLICATION_NAME = applicationName;
        this.sumAdCoast = 0;
    }


    private AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(KEY_FILE_LOCATION))
                .createScoped(AnalyticsReportingScopes.all());

        // Construct the Analytics Reporting service object.
        return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }


    private GetReportsResponse getAdCostReport(AnalyticsReporting service, String date) throws IOException {
        // Create the DateRange object.
        DateRange dateRange = new DateRange();
        // Use yesterday
        dateRange.setStartDate(date);
        dateRange.setEndDate(date);

        // Create the Metrics object.
        Metric sessions = new Metric()
                .setExpression("ga:adCost")
                .setAlias("cost");

        //Dimension pageTitle = new Dimension().setName("ga:adGroup");

        // Create the ReportRequest object.
        ReportRequest request = new ReportRequest()
                .setViewId(VIEW_ID)
                .setDateRanges(List.of(dateRange))
                .setMetrics(List.of(sessions));
                //.setDimensions(List.of(pageTitle));

        ArrayList<ReportRequest> requests = new ArrayList<>();
        requests.add(request);

        // Create the GetReportsRequest object.
        GetReportsRequest getReport = new GetReportsRequest()
                .setReportRequests(requests);

        // Call the batchGet method.

        // Return the response.
        return service.reports().batchGet(getReport).execute();
    }


    public float getSumOfAdCost(String date) throws GeneralSecurityException, IOException {
        AnalyticsReporting service = initializeAnalyticsReporting();
        GetReportsResponse response = getAdCostReport(service, date);

        for (Report report: response.getReports()) {
            ColumnHeader header = report.getColumnHeader();
            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
            List<ReportRow> rows = report.getData().getRows();

            if (rows != null) {

                for (ReportRow row : rows) {
                    List<DateRangeValues> metrics = row.getMetrics();

                    for (DateRangeValues values : metrics) {
                        for (int i = 0; i < values.getValues().size() && i < metricHeaders.size(); i++) {
                            sumAdCoast += Float.parseFloat(values.getValues().get(i));
                        }
                    }
                }
            }
        }

        return sumAdCoast;
    }


    public void printResponse(GetReportsResponse response) {

        for (Report report: response.getReports()) {
            ColumnHeader header = report.getColumnHeader();
            List<String> dimensionHeaders = header.getDimensions();
            List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
            List<ReportRow> rows = report.getData().getRows();

            if (rows == null) {
                System.out.println("No data found for " + VIEW_ID);
                return;
            }

            for (ReportRow row: rows) {
                List<String> dimensions = row.getDimensions();
                List<DateRangeValues> metrics = row.getMetrics();

                for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
                    System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
                }

                for (int j = 0; j < metrics.size(); j++) {
                    System.out.print("Date Range (" + j + "): ");
                    DateRangeValues values = metrics.get(j);
                    for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
                        System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
                    }
                }
            }
        }
    }
}
