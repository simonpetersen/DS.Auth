package server;

import util.Strings;

import java.util.*;
import java.util.stream.Collectors;

public class PrintService {

    List<PrintJob> printJobs;
    private Map<String, String> config;
    private Timer timer;
    private int jobNumber;
    private boolean authenticated;

    public PrintService() {
        printJobs = new ArrayList<>();
        config = new HashMap<>();
        timer = new Timer();
        jobNumber = 1;
        authenticated = false;
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                printJob();
            }
        };
        timer.schedule(timerTask, 0, 20*1000);
    }

    public void addPrintJob(String filename, String printer) {
        if (!authenticated) {
            return;
        }

        printJobs.add(new PrintJob(jobNumber++, filename, printer));
        authenticated = false;
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

    public void moveJobToTopOfQueue(int jobId) {
        Optional<PrintJob> jobOptional = printJobs.stream().filter(p -> p.getJobNumber() == jobId).findFirst();
        if (jobOptional.isPresent()) {
            PrintJob job = jobOptional.get();
            printJobs.remove(job);
            printJobs.add(0, job);
        }
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

    public String authenticate(String username, String password) {
        authenticated = true;
        return "Authentication successfull.";
    }
}
