import java.io.*;
import java.util.ArrayList;

public class KeywordSearcher {

    public ArrayList<SearchHit> search(ScanResult result, String keyword, int maxHits) {
        ArrayList<SearchHit> hits = new ArrayList<SearchHit>();
        String key = keyword.toLowerCase();

        for (FileInfo f : result.getFiles()) {
            if (!f.isTextFile()) {
                continue;
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
                String line;
                int lineNo = 0;
                while ((line = br.readLine()) != null) {
                    lineNo++;
                    if (line.toLowerCase().contains(key)) {
                        hits.add(new SearchHit(f.getPath(), lineNo, line.trim()));
                        if (hits.size() >= maxHits) {
                            br.close();
                            return hits;
                        }
                    }
                }
                br.close();
            } catch (IOException e) {
                // skip
            }
        }
        return hits;
    }
}
