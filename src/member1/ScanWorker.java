import java.io.*;
import java.util.ArrayList;

public class ScanWorker implements Runnable {
    private ArrayList<File> myFiles;
    private ScanResult result;
    private ScanProgressListener listener;
    private int total;

    public ScanWorker(ArrayList<File> myFiles, ScanResult result, ScanProgressListener listener, int total) {
        this.myFiles = myFiles;
        this.result = result;
        this.listener = listener;
        this.total = total;
    }

    public void run() {
        int mine = 0;
        for (File f : myFiles) {
            String name = f.getName();
            String ext = "";
            int dot = name.lastIndexOf('.');
            if (dot > 0 && dot < name.length() - 1) {
                ext = name.substring(dot + 1).toLowerCase();
            }
            FileInfo info = new FileInfo(f.getAbsolutePath(), name, ext, f.length(), 0, 0);
            if (info.isTextFile()) {
                countWordsAndLines(f, info);
            }
            result.addFile(info);
            mine++;
            if (listener != null && mine % 20 == 0) {
                listener.onProgress(result.getDoneCount(), total);
            }
        }
        if (listener != null) {
            listener.onProgress(result.getDoneCount(), total);
        }
    }

    // reads line by line so big files dont blow the ram
    private void countWordsAndLines(File f, FileInfo info) {
        long words = 0;
        long lines = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                lines++;
                String t = line.trim();
                if (t.length() > 0) {
                    words += t.split("\\s+").length;
                }
            }
            br.close();
        } catch (IOException e) {
            // cant read it, whatever skip
        }
        info.setWords(words);
        info.setLines(lines);
    }
}
