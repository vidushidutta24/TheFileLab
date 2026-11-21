import java.io.File;
import java.util.ArrayList;

public class ScanCoordinator {

    public ScanResult scan(String path, int threadCount, ScanProgressListener listener)
            throws InvalidFolderException, ScanFailedException {
        long start = System.currentTimeMillis();

        FileScanner scanner = new FileScanner();
        ArrayList<File> all = scanner.findFiles(path);

        ScanResult result = new ScanResult(path);
        result.getStats().setFolderCount(scanner.getFolderCount());

        if (threadCount < 1) {
            threadCount = 1;
        }

        // split files between threads one by one like dealing cards
        ArrayList<ArrayList<File>> parts = new ArrayList<ArrayList<File>>();
        for (int i = 0; i < threadCount; i++) {
            parts.add(new ArrayList<File>());
        }
        for (int i = 0; i < all.size(); i++) {
            parts.get(i % threadCount).add(all.get(i));
        }

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new ScanWorker(parts.get(i), result, listener, all.size()));
            threads[i].start();
        }

        try {
            for (int i = 0; i < threadCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new ScanFailedException("scan got interrupted");
        }

        result.setTimeMs(System.currentTimeMillis() - start);
        result.setScanDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        return result;
    }
}
