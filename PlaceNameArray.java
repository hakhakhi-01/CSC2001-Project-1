import java.io.*;
import java.util.*;

public class PlaceNameArray {

	// class fields
	private PlaceNameEntry[] array;
	private int size;
	private int comparisonCount;

	// class constructor
	public PlaceNameArray (int capacity) {
		array = new PlaceNameEntry[capacity];
		size = 0;
	}

	public void reset() { size = 0; }

	public int getComparisonCount() {
		return this.comparisonCount;
	}

	// loads data into an array up to a limit of N, skips duplicates
	public void load (String filename, int N) throws Exception {
		reset();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		HashSet<String> seen = new HashSet<>();
		String line;
		reader.readLine();

		while ((line = reader.readLine()) != null && size < N) {
			String[] parts = line.split(",");
			String name = parts[1];
			if (!seen.contains(name)) {
				array[size++] = new PlaceNameEntry(name, parts[2], parts[3], Integer.parseInt(parts[4]));
				seen.add(name);
			}
		}
		reader.close();
	}
	// search method
	public PlaceNameEntry search(String placeName) {
		comparisonCount = 0;
		for (int i = 0; i < size; i++) {
			comparisonCount++;
			if (array[i].getPlaceName().equalsIgnoreCase(placeName))
				return array[i];
		}
		return null;
	}
}
