import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;

public class PlaceNameBST {

	//class fields
	private BSTNode root;
	private int comparisonCount;

	// BSTNode class defintion
	private class BSTNode {
		private PlaceNameEntry data;
		BSTNode right;
		BSTNode left;

		public BSTNode(PlaceNameEntry data, BSTNode right, BSTNode left) {
			this.data = data;
			this.right = right;
			this.left = left;
		}
	}
	// insert method
	public void insert(PlaceNameEntry entry) {
		if (root == null)
			root = new BSTNode(entry, null, null);
		else
			insert(entry, root);
	}
	// insert recursive method
	public void insert(PlaceNameEntry entry, BSTNode node) {
		int compare = entry.getPlaceName().compareTo(node.data.getPlaceName());
		if (compare <= 0) {
			if (node.left == null)
				node.left = new BSTNode(entry, null, null);
			else
				insert(entry, node.left);
		} else {
			if (node.right == null)
				node.right = new BSTNode(entry, null, null);
			else
				insert (entry, node.right);
		}
	}
	// searching method
	public PlaceNameEntry search(String placeName) {
		comparisonCount =  0;
		if (root == null)
			return null;
		BSTNode found = find(placeName, root);
			return (found == null) ? null : found.data;
	}

	// searching recursive method
	private BSTNode find(String placeName, BSTNode node) {
		if (node == null)
			return null;
		comparisonCount++;
		int compare = placeName.compareToIgnoreCase(node.data.getPlaceName());
		if (compare == 0)
			return node;
		else if (compare < 0)
			return find(placeName, node.left);
		else
			return find(placeName, node.right);
	}
	public int getComparisonCount() {
		return comparisonCount;
	}

	public void reset() {
		root = null;
		//size = 0;
		//comparisonCount = 0;
	}

	public void loadFromFile(String filename, int N) throws Exception {
		reset();
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		HashSet<String> seen = new HashSet<>();
		String line;
		reader.readLine();

		int count = 0;

		while ((line =  reader.readLine()) != null && count < N) {
			line = line.trim();
			if (line.isEmpty())
				continue;
			String[] parts = line.split(",");
			if (parts.length < 5)
				continue;
			String name = parts[1].trim();
			if (!seen.contains(name)) {
				PlaceNameEntry entry = new PlaceNameEntry(name, parts[2], parts[3], Integer.parseInt(parts[4]));
				insert(entry);
				seen.add(name);
				count++;
			}
		}
		reader.close();
	}
}

