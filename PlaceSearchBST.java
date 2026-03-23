import java.util.Scanner;

public class PlaceSearchBST {
	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		PlaceNameBST BST = new PlaceNameBST();

		while (true) {
			System.out.println("Choose one option from below:");
			System.out.println("1. Load file");
			System.out.println("2. Search");
			System.out.println("3. Quit");
			int choice = scanner.nextInt();
			scanner.nextLine();

			if (choice == 1) {
				System.out.println("Enter filename:");
				String file = scanner.nextLine();
				System.out.println("Enter N:");
				int N = Integer.parseInt(scanner.nextLine());
				BST.loadFromFile(file, N);
				System.out.println("File has been loaded");

			} else if (choice == 2) {
				System.out.println("Enter the name of the place:");
				String name = scanner.nextLine();
				PlaceNameEntry result = BST.search(name);
				if (result == null)
					System.out.println("Place not found");
				else 
					System.out.println(result);
				System.out.println("ComparisonCount: " + BST.getComparisonCount());

			} else {
				break;
			}
			
		}
	}
}
