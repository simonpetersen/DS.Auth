package server;

import util.PrinterStatus;
import util.Strings;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PrintService {

    private List<PrintJob> printJobs;
    private Map<String, String> config;
    private Timer printTimer;
    private PrinterStatus status;
    Logger logger;

    public PrintService(Logger logger) {
        printJobs = new ArrayList<>();
        config = new HashMap<>();
        this.logger = logger;
        startPrinter();
    }

    public String addPrintJob(String filename, String printer) {
        printJobs.add(new PrintJob(filename, printer));
        return Strings.JobAdded;
    }

    public List<String> getPrintQueue() {
        List<String> jobs = printJobs.stream()
                .map(p -> printJobs.indexOf(p)+1 + "\t\t" + p.getFilename())
                .collect(Collectors.toList());

        jobs.add(0, "Number\tFilename");

        return jobs;
    }

    public String readConfig(String parameter) {
        if (config.containsKey(parameter)) {
            return config.get(parameter);
        }
        return String.format(Strings.ConfigNotFoundMsg, parameter);
    }

    public String moveJobToTopOfQueue(int jobNumber) {
        if (printJobs.size() >= jobNumber) {
            PrintJob job = printJobs.get(jobNumber-1);
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
        printTimer = new Timer();
        TimerTask printerTask = new TimerTask() {
            @Override
            public void run() {
                printJob();
            }
        };
        printTimer.schedule(printerTask, 0, 20*1000);
        status = PrinterStatus.Running;
        return Strings.PrinterStarted;
    }

    public String stop() {
        printTimer.cancel();
        status = PrinterStatus.Stopped;
        return Strings.PrinterStopped;
    }

    public String restart() {
        String msg = stop();
        printJobs = new ArrayList<PrintJob>();
        msg += "\n" + startPrinter();
        return msg;
    }

    public String status() {
        return status.toString();
    }

    private void printJob() {
        if (!printJobs.isEmpty()) {
            PrintJob job = printJobs.get(0);
            Date date = new Date();
            logger.info(String.format("%s: %s printed on printer: %s", date, job.getFilename(), job.getPrinter()));
            printJobs.remove(0);
        }
    }
}
