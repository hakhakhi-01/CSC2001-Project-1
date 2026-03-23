import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PlaceExperiment {

    private static final int[] SIZES = {1000,2000,3000,4000,5000,6000,7000,8000,9000,10000};
    private static final int ARRAY_CAPACITY = 15000;

    public static void main(String[] args) throws Exception {

        Path csvPath = resolveInputPath("../a1/SAPlaceNames.csv");
        Path optimalPath = resolveInputPath("../a1/SAPlaceNamesOptimal.txt");
        Path queryPath = resolveInputPath("../a1/SearchQueries.txt");

        List<String> queries = loadQueries(queryPath);
        if (queries.size() != 50) {
            throw new IllegalStateException("SearchQueries.txt must contain exactly 50 non-empty queries.");
        }

        // These ones are for every N as required
        List<PlaceNameEntry> dedupAsIs = loadUniqueEntriesFromCsv(csvPath);
        List<PlaceNameEntry> dedupSorted = new ArrayList<>(dedupAsIs);
        dedupSorted.sort(Comparator.comparing(PlaceNameEntry::getPlaceName, String.CASE_INSENSITIVE_ORDER));

        List<PlaceNameEntry> optimalOrdered = loadOptimalOrderedEntries(optimalPath, dedupAsIs);

        ensureEnoughRecords(dedupAsIs, "as-is CSV order");
        ensureEnoughRecords(dedupSorted, "sorted CSV order");
        ensureEnoughRecords(optimalOrdered, "optimal ordering file");

        System.out.println("N Array BST(as-is) BST(sorted) BST(optimal)");

        for (int n : SIZES) {
            double arrayAvg = runArrayCase(csvPath, n, queries);
            double bstAsIsAvg = runBstAsIsCase(csvPath, n, queries);
            double bstSortedAvg = runBstFromList(dedupSorted, n, queries);
            double bstOptimalAvg = runBstFromList(optimalOrdered, n, queries);

            System.out.printf(Locale.US, "%d %.1f %.1f %.1f %.1f%n",
                    n, arrayAvg, bstAsIsAvg, bstSortedAvg, bstOptimalAvg);
        }
    }

    private static Path resolveInputPath(String filename) throws IOException {
        Path[] candidates = new Path[] {
                Paths.get(filename),
                Paths.get("data", filename),
                Paths.get("..", "data", filename)
        };

        for (Path candidate : candidates) {
            if (Files.exists(candidate)) {
                return candidate;
            }
        }

        throw new IOException("Could not find input file: " + filename);
    }

    private static List<String> loadQueries(Path queryPath) throws IOException {
        List<String> queries = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(queryPath)) {
            String line;
            while ((line = br.readLine()) != null) {
                String q = line.trim();
                if (!q.isEmpty()) {
                    queries.add(q);
                }
            }
        }

        return queries;
    }

    private static List<PlaceNameEntry> loadUniqueEntriesFromCsv(Path csvPath) throws IOException {
        List<PlaceNameEntry> entries = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length < 5) {
                    continue;
                }

                String name = parts[1];
                if (seen.contains(name)) {
                    continue;
                }

                entries.add(new PlaceNameEntry(name, parts[2], parts[3], Integer.parseInt(parts[4])));
                seen.add(name);
            }
        }

        return entries;
    }

    private static List<PlaceNameEntry> loadOptimalOrderedEntries(Path optimalPath,
                                                                   List<PlaceNameEntry> dedupAsIs) throws IOException {
        Map<String, PlaceNameEntry> byName = new HashMap<>();
        for (PlaceNameEntry entry : dedupAsIs) {
            byName.putIfAbsent(entry.getPlaceName(), entry);
        }

        List<PlaceNameEntry> ordered = new ArrayList<>();
        Set<String> used = new HashSet<>();

        try (BufferedReader reader = Files.newBufferedReader(optimalPath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String name = line.trim();
                if (name.isEmpty() || used.contains(name)) {
                    continue;
                }

                PlaceNameEntry entry = byName.get(name);
                if (entry != null) {
                    ordered.add(entry);
                    used.add(name);
                }
            }
        }

        return ordered;
    }

    private static void ensureEnoughRecords(List<PlaceNameEntry> entries, String sourceLabel) {
        int needed = SIZES[SIZES.length - 1];
        if (entries.size() < needed) {
            throw new IllegalStateException("Not enough records in " + sourceLabel
                    + ": have " + entries.size() + ", need at least " + needed + ".");
        }
    }

    private static double runArrayCase(Path csvPath, int n, List<String> queries) throws Exception {
        PlaceNameArray array = new PlaceNameArray(ARRAY_CAPACITY);
        array.load(csvPath.toString(), n);

        long totalComparisonCount = 0;
        for (String q : queries) {
            array.search(q);
            totalComparisonCount += array.getComparisonCount();
        }

        return (double) totalComparisonCount / queries.size();
    }

    private static double runBstAsIsCase(Path csvPath, int n, List<String> queries) throws Exception {
        PlaceNameBST BST = new PlaceNameBST();
        BST.loadFromFile(csvPath.toString(), n);

        long totalComparisonCount = 0;
        for (String q : queries) {
            BST.search(q);
            totalComparisonCount += BST.getComparisonCount();
        }

        return (double) totalComparisonCount / queries.size();
    }

    private static double runBstFromList(List<PlaceNameEntry> entries, int n, List<String> queries) {
        PlaceNameBST BST = new PlaceNameBST();

        for (int i = 0; i < n; i++) {
            BST.insert(entries.get(i));
        }

        long totalComparisonCount = 0;
        for (String q : queries) {
            BST.search(q);
            totalComparisonCount += BST.getComparisonCount();
        }

        return (double) totalComparisonCount / queries.size();
    }
}
