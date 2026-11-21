import java.util.*;

public class ExtensionAnalyzer implements Analyzer {

    public String getName() {
        return "Extension Stats";
    }

    public ArrayList<ExtensionStat> getStats(ScanResult result) {
        HashMap<String, ExtensionStat> map = new HashMap<String, ExtensionStat>();
        for (FileInfo f : result.getFiles()) {
            String ext = f.getExtension();
            if (ext.equals("")) {
                ext = "(none)";
            }
            ExtensionStat s = map.get(ext);
            if (s == null) {
                s = new ExtensionStat(ext);
                map.put(ext, s);
            }
            s.addFile(f.getSize());
        }
        ArrayList<ExtensionStat> list = new ArrayList<ExtensionStat>(map.values());
        Collections.sort(list);
        return list;
    }

    public String analyze(ScanResult result) {
        String s = "extension | files | size\n";
        for (ExtensionStat e : getStats(result)) {
            s += e.getExt() + " | " + e.getCount() + " | " + SizeFormatter.format(e.getTotalSize()) + "\n";
        }
        return s;
    }
}
