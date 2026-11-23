import java.io.*;

public class CsvExporter {

    public void export(ScanResult r, String outPath) throws ReportException {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(outPath));
            pw.println("path,name,extension,size,words,lines");
            for (FileInfo f : r.getFiles()) {
                String safePath = f.getPath().replace("\"", "\"\"");
                pw.println("\"" + safePath + "\"," + f.getName().replace(",", ";") + ","
                        + f.getExtension() + "," + f.getSize() + "," + f.getWords() + "," + f.getLines());
            }
            pw.close();
        } catch (IOException e) {
            throw new ReportException("csv failed: " + e.getMessage());
        }
    }
}
