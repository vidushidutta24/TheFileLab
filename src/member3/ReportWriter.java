import java.io.*;

public class ReportWriter {

    public void write(ScanResult r, String outPath) throws ReportException {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(outPath));
            pw.println("THE FILE LAB - REPORT");
            pw.println("date: " + r.getScanDate());
            pw.println(r.getSummary());
            pw.println();

            // same interface so one loop does everything
            Analyzer[] list = {
                new ExtensionAnalyzer(),
                new LargestFilesFinder(10),
                new WordFrequencyAnalyzer(20),
                new DuplicateFinder()
            };
            for (int i = 0; i < list.length; i++) {
                pw.println("== " + list[i].getName() + " ==");
                pw.println(list[i].analyze(r));
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            throw new ReportException("couldnt write report: " + e.getMessage());
        }
    }
}
