package server;

import util.Strings;

import java.util.*;
import java.util.stream.Collectors;

public class PrintService {

    List<PrintJob> printJobs;
    private Map<String, String> config;
    private Timer printTimer;
    private int jobNumber;

    public PrintService() {
        printJobs = new ArrayList<>();
        config = new HashMap<>();
        printTimer = new Timer();
        jobNumber = 1;
        startPrinter();
    }

    public String addPrintJob(String filename, String printer) {
        printJobs.add(new PrintJob(jobNumber++, filename, printer));
        return null;
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

    public String moveJobToTopOfQueue(int jobId) {
        Optional<PrintJob> jobOptional = printJobs.stream().filter(p -> p.getJobNumber() == jobId).findFirst();
        if (jobOptional.isPresent()) {
            PrintJob job = jobOptional.get();
            printJobs.remove(job);
            printJobs.add(0, job);
            return Strings.JobMovedToTopOk;
        }

        return Strings.JobNotFound;
    }

    public String setConfig(String parameter, String value) {
        config.put(parameter, value);
        return Strings.ConfigSetOk;
    }

    public String startPrinter() {
        TimerTask printerTask = new TimerTask() {
            @Override
            public void run() {
                printJob();
            }
        };
        printTimer.schedule(printerTask, 0, 20*1000);
        return Strings.PrinterStarted;
    }

    public String stop() {
        printTimer.cancel();
        return Strings.PrinterStopped;
    }

    public String restart() {
        String msg = stop();
        printJobs = new ArrayList<PrintJob>();
        msg += "\n" + startPrinter();
        return msg;
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
