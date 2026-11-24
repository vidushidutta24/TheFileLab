import java.util.*;

public class CliMenu {
    private Scanner sc = new Scanner(System.in);
    private ScanResult current = null;

    public void start() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                scanFolder();
            } else if (choice.equals("2")) {
                if (haveScan()) System.out.println(current.getSummary());
            } else if (choice.equals("3")) {
                if (haveScan()) {
                    int n = askNumber("how many words", 10);
                    System.out.println(new WordFrequencyAnalyzer(n).analyze(current));
                }
            } else if (choice.equals("4")) {
                if (haveScan()) System.out.println(new ExtensionAnalyzer().analyze(current));
            } else if (choice.equals("5")) {
                if (haveScan()) {
                    int n = askNumber("how many files", 10);
                    System.out.println(new LargestFilesFinder(n).analyze(current));
                }
            } else if (choice.equals("6")) {
                if (haveScan()) System.out.println(new DuplicateFinder().analyze(current));
            } else if (choice.equals("7")) {
                if (haveScan()) searchKeyword();
            } else if (choice.equals("8")) {
                if (haveScan()) saveReport();
            } else if (choice.equals("9")) {
                if (haveScan()) saveCsv();
            } else if (choice.equals("0")) {
                running = false;
            } else {
                System.out.println("wrong option");
            }
        }
        System.out.println("bye");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== THE FILE LAB =====");
        System.out.println("1  scan a folder");
        System.out.println("2  summary");
        System.out.println("3  top words");
        System.out.println("4  extension stats");
        System.out.println("5  largest files");
        System.out.println("6  find duplicates");
        System.out.println("7  search keyword");
        System.out.println("8  save report (txt)");
        System.out.println("9  save csv");
        System.out.println("0  exit");
        System.out.print("choice: ");
    }

    private boolean haveScan() {
        if (current == null) {
            System.out.println("scan a folder first (option 1)");
            return false;
        }
        return true;
    }

    private int askNumber(String label, int def) {
        System.out.print(label + " [" + def + "]: ");
        return InputValidator.toInt(sc.nextLine(), def);
    }

    private void scanFolder() {
        System.out.print("folder path: ");
        String path = sc.nextLine().trim();
        if (!InputValidator.isValidFolder(path)) {
            System.out.println("that folder doesnt exist");
            return;
        }
        int threads = askNumber("threads", 4);
        try {
            ScanCoordinator coordinator = new ScanCoordinator();
            current = coordinator.scan(path, threads, new ProgressPrinter());
            System.out.println();
            System.out.println(current.getSummary());
        } catch (InvalidFolderException e) {
            System.out.println("error: " + e.getMessage());
        } catch (ScanFailedException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void searchKeyword() {
        System.out.print("keyword: ");
        String key = sc.nextLine().trim();
        if (key.length() == 0) {
            System.out.println("empty keyword");
            return;
        }
        ArrayList<SearchHit> hits = new KeywordSearcher().search(current, key, 100);
        for (SearchHit h : hits) {
            System.out.println(h.getPath() + " : " + h.getLineNo() + " : " + h.getLine());
        }
        System.out.println(hits.size() + " hits");
    }

    private void saveReport() {
        System.out.print("file name [report.txt]: ");
        String name = sc.nextLine().trim();
        if (name.length() == 0) name = "report.txt";
        try {
            new ReportWriter().write(current, name);
            System.out.println("saved " + name);
        } catch (ReportException e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    private void saveCsv() {
        System.out.print("file name [files.csv]: ");
        String name = sc.nextLine().trim();
        if (name.length() == 0) name = "files.csv";
        try {
            new CsvExporter().export(current, name);
            System.out.println("saved " + name);
        } catch (ReportException e) {
            System.out.println("error: " + e.getMessage());
        }
    }
}
