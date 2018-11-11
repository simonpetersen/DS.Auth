package server;

import util.Strings;

import java.util.*;
import java.util.stream.Collectors;

public class PrintService {

    List<PrintJob> printJobs;
    private Map<String, String> config;
    private Timer timer;
    private int jobNumber;

    public PrintService() {
        printJobs = new ArrayList<>();
        config = new HashMap<>();
        timer = new Timer();
        jobNumber = 1;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                printJob();
            }
        };
        timer.schedule(timerTask, 0, 20*1000);
    }

    public void addPrintJob(String filename, String printer) {
        printJobs.add(new PrintJob(jobNumber++, filename, printer));
    }

    public List<String> getPrintQueue() {
        return printJobs.stream().map(p -> p.toString()).collect(Collectors.toList());
    }

    public String readConfig(String parameter) {
        if (config.containsKey(parameter)) {
            return config.get(parameter);
        }
        return String.format(Strings.ConfigNotFoundMsg, parameter);
    }

    public void setConfig(String parameter, String value) {
        config.put(parameter, value);
    }

    private void printJob() {
        if (!printJobs.isEmpty()) {
            PrintJob job = printJobs.get(0);
            Date date = new Date();
            System.out.println(String.format("%s: %s printed on %s", date, job.getFilename(), job.getPrinter()));
            printJobs.remove(0);
        }
    }
}
