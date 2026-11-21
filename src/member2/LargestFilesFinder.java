import java.util.*;

public class LargestFilesFinder implements Analyzer {
    private int topN;

    public LargestFilesFinder(int topN) {
        this.topN = topN;
    }

    public String getName() {
        return "Largest Files";
    }

    public ArrayList<FileInfo> getLargest(ScanResult result) {
        // small one stays on top so we kick it out when queue is full
        PriorityQueue<FileInfo> pq = new PriorityQueue<FileInfo>(11, new FileSizeComparator());
        for (FileInfo f : result.getFiles()) {
            pq.add(f);
            if (pq.size() > topN) {
                pq.poll();
            }
        }
        ArrayList<FileInfo> list = new ArrayList<FileInfo>();
        while (!pq.isEmpty()) {
            list.add(pq.poll());
        }
        Collections.reverse(list); // biggest first
        return list;
    }

    public String analyze(ScanResult result) {
        String s = "top " + topN + " largest files\n";
        int rank = 1;
        for (FileInfo f : getLargest(result)) {
            s += rank + ". " + SizeFormatter.format(f.getSize()) + "  " + f.getPath() + "\n";
            rank++;
        }
        return s;
    }
}
