package server;

public class PrintJob {

    private int jobNumber;
    private String filename;
    private String printer;

    public PrintJob(int jobNumber, String filename, String printer) {
        this.jobNumber = jobNumber;
        this.filename = filename;
        this.printer = printer;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public String toString() { return getJobNumber() + " " + getFilename(); }
}
