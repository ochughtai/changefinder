import java.io.File;
import java.util.*;



public class changeFinder{
	
	public static String getInput() {
		//Create scanner for System.in
		Scanner scanner = new Scanner(System.in);
		//Print Prompt
		System.out.print("Directory for before and after comparison: ");
		//Get input Address
		String input = scanner.nextLine();
		//Close Scanner
		scanner.close();
		//Return
		return input;
		
	}
	
	public static File openDir(String dir) {
		
		//Open File instance for specified dir
		File file = new File(dir);
		//If the file does not exist and is not a directory
		if (!(file.exists() && file.isDirectory())) {
			System.out.println("The specified directory could not be found");
			System.exit(0);
		}
		
		return file;
		
	}
	
	public static void getChildren(Node<String> parent, File start) {
		//Get subdirectories/files
		String[] children = start.list();
		
		for (String name : children) {
			//TO-DO
			File child = new File(name);
			
		}
		
		
	}
	

	public static void main(String[] args) {
		//Get directory from user
		String dir = getInput();
		//Open the directory
		File start = openDir(dir);
		//Create parent node for tree
		Node<String> parent = new Node(start.getAbsolutePath());

	}

}
