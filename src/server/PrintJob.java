package server;

/**
 * Basic model class representing a print job
 */
public class PrintJob {

    private String filename;
    private String printer;

    public PrintJob(String filename, String printer) {
        this.filename = filename;
        this.printer = printer;
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
}
