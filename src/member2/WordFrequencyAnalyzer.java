import java.io.*;
import java.util.*;

public class WordFrequencyAnalyzer implements Analyzer {
    private int topN;

    public WordFrequencyAnalyzer(int topN) {
        this.topN = topN;
    }

    public String getName() {
        return "Word Frequency";
    }

    public ArrayList<WordCount> getTopWords(ScanResult result) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();

        for (FileInfo f : result.getFiles()) {
            if (!f.isTextFile()) {
                continue;
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.toLowerCase().split("[^a-z0-9]+");
                    for (String w : parts) {
                        if (w.length() < 3 || StopWordFilter.isStopWord(w)) {
                            continue; // too small or boring words are useless
                        }
                        if (map.containsKey(w)) {
                            map.put(w, map.get(w) + 1);
                        } else {
                            map.put(w, 1);
                        }
                    }
                }
                br.close();
            } catch (IOException e) {
                // file missing or locked skip
            }
        }

        ArrayList<WordCount> all = new ArrayList<WordCount>();
        for (String w : map.keySet()) {
            all.add(new WordCount(w, map.get(w)));
        }
        Collections.sort(all);

        ArrayList<WordCount> top = new ArrayList<WordCount>();
        for (int i = 0; i < all.size() && i < topN; i++) {
            top.add(all.get(i));
        }
        return top;
    }

    public String analyze(ScanResult result) {
        ArrayList<WordCount> top = getTopWords(result);
        String s = "top " + topN + " words\n";
        int rank = 1;
        for (WordCount w : top) {
            s += rank + ". " + w.getWord() + " - " + w.getCount() + "\n";
            rank++;
        }
        return s;
    }
}
