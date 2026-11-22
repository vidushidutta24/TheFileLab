import java.io.*;
import java.util.*;

public class DuplicateFinder implements Analyzer {

    public String getName() {
        return "Duplicate Finder";
    }

    public ArrayList<DuplicateGroup> findDuplicates(ScanResult result) {
        // step 1 group by size, only same size can be dup
        HashMap<Long, ArrayList<FileInfo>> bySize = new HashMap<Long, ArrayList<FileInfo>>();
        for (FileInfo f : result.getFiles()) {
            if (f.getSize() == 0) {
                continue;
            }
            ArrayList<FileInfo> list = bySize.get(f.getSize());
            if (list == null) {
                list = new ArrayList<FileInfo>();
                bySize.put(f.getSize(), list);
            }
            list.add(f);
        }

        // step 2 compare the actual bytes inside each group
        ArrayList<DuplicateGroup> groups = new ArrayList<DuplicateGroup>();
        for (Long size : bySize.keySet()) {
            ArrayList<FileInfo> same = bySize.get(size);
            if (same.size() < 2) {
                continue;
            }
            ArrayList<DuplicateGroup> local = new ArrayList<DuplicateGroup>();
            for (FileInfo f : same) {
                boolean placed = false;
                for (DuplicateGroup g : local) {
                    if (sameContent(g.getFiles().get(0).getPath(), f.getPath())) {
                        g.add(f);
                        placed = true;
                        break;
                    }
                }
                if (!placed) {
                    DuplicateGroup g = new DuplicateGroup(size);
                    g.add(f);
                    local.add(g);
                }
            }
            for (DuplicateGroup g : local) {
                if (g.getFiles().size() > 1) {
                    groups.add(g);
                }
            }
        }
        return groups;
    }

    private boolean sameContent(String p1, String p2) {
        try {
            BufferedInputStream a = new BufferedInputStream(new FileInputStream(p1));
            BufferedInputStream b = new BufferedInputStream(new FileInputStream(p2));
            boolean same = true;
            int x = a.read();
            while (x != -1) {
                if (x != b.read()) {
                    same = false;
                    break;
                }
                x = a.read();
            }
            a.close();
            b.close();
            return same;
        } catch (IOException e) {
            return false;
        }
    }

    public String analyze(ScanResult result) {
        ArrayList<DuplicateGroup> groups = findDuplicates(result);
        if (groups.size() == 0) {
            return "no duplicates found\n";
        }
        String s = "";
        long wasted = 0;
        int n = 1;
        for (DuplicateGroup g : groups) {
            s += "group " + n + " (" + SizeFormatter.format(g.getSize()) + " each)\n";
            for (FileInfo f : g.getFiles()) {
                s += "   " + f.getPath() + "\n";
            }
            wasted += g.getWastedSpace();
            n++;
        }
        s += "\nwasted space: " + SizeFormatter.format(wasted) + "\n";
        return s;
    }
}
