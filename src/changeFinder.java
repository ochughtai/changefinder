import java.io.File; 
import java.util.*;



public class changeFinder{
	
	//Allocate root nodes
	static Node<File> root;
	static Node<File> root2;
	//Allocate Lists of Differences
	static List<Node<File>> added = new ArrayList<Node<File>>();
	static List<Node<File>> deleted = new ArrayList<Node<File>>();
	
	/*
	 * Compares 2 Node<Files> and determines if they are the same file
	 */
	public static boolean isEqualPath(Node<File> first, Node<File> second){
		
		if (first.getData().getAbsolutePath().equals(second.getData().getAbsolutePath())){
			return true;
		}
		
		return false;
		
	}
	
	
	public static String getInput(int prompt, Scanner scanner) {
		//Create string for user input
		String input = null;
		
		if (prompt == 1){
			//Print Prompt
			System.out.print("Directory for before and after comparison: ");
			//Get input 
			input = scanner.nextLine();
		}
		else if (prompt == 2){
			//Print Prompt
			System.out.print("Press Enter Once You Have Made Your Changes To Your Directory");
			//Wait for user to hit enter
			scanner.nextLine();
		}
		return input;
	}
	
	public static File openDir(String dir) {
		
		//Open File instance for specified dir
		File file = new File(dir);
		//If the file does not exist and is not a directory
		if (!(file.exists() && file.isDirectory())) {
			System.out.println("\nThe specified directory could not be found");
			System.exit(0);
		}
		
		return file;
		
	}
	/*
	 * Recursively get all children
	 */
	public static List<Node<File>> getAllChildren(Node<File> parent){
		
		List<Node<File>> children = parent.getChildren();
		
		for (Node<File> child : children){
			if (child.getData().isDirectory()){
				children.addAll(getAllChildren(child));
			}
		}
		
		return children;
	}
		
	
	public static void getChildrenFromFile(Node<File> parent) {
		
		//Get sub-directories/files
		File[] files = parent.getData().listFiles();
		
		//Convert all sub-directories/files to nodes
		List<Node<File>> children = new ArrayList<Node<File>>(); 
		for (File file : files){
			children.add(new Node<File>(file));
		}
		
		//Add list of children to the parent node
		parent.addChildren(children);
		
		//Recursively find and add all children
		for (Node<File> dir : parent.getChildren()){
			if (dir.getData().isDirectory()){
				getChildrenFromFile(dir);
			}
		}
		
		
	}
	
	

	/*
	 * Recursively compares the files from both trees and returns lists of differences 
	 */
	public static void compareFiles(){

		//Get trees in list form
		List<Node<File>> list1 = tree2List(root);
		List<Node<File>> list2 = tree2List(root2);
		
		//Find files added
		for (Node<File> file : list2) {
			boolean found = false;
			for (Node<File> file2 : list1) {
				if (isEqualPath(file, file2)){
					found = true;
				}
			}
			if (!found){
				added.add(file);
			}
		}

		//Find files added
		for (Node<File> file : list1) {
			boolean found = false;
			for (Node<File> file2 : list2) {
				if (isEqualPath(file, file2)){
					found = true;
				}
			}
			if (!found){
				deleted.add(file);
			}
		}

	}
	
	/*
	 * Helper function to recursively add all nodes to list 
	 */
	public static List<Node<File>> tree2List (Node<File> root){
		List<Node<File>> list = new ArrayList<Node<File>>();
		List<Node<File>> children = root.getChildren();
		//Print every file in the list to the console
		for (Node<File> file : children){
			list.add(file);
			//Recursively print children
			if (file.getData().isDirectory()){
				list.addAll(tree2List(file));
			}
		}	
		
		return list;
	}
	
	/*
	 * Helper function to recursively print all nodes for troubleshooting 
	 */
	public static void printNodes(Node<File> root){
		//Print every file in the list to the console
		for (Node<File> file : root.getChildren()){
			System.out.println(file.getData().getAbsolutePath());
			//Recursively print children
			if (file.getData().isDirectory()){
				printNodes(file);
			}
		}	
	}
	
	/*
	 * Prints absolute file path of all nodes in a list
	 */
	public static void printList (List<Node<File>> list){
		
		for (Node<File> file : list){
			System.out.println(file.getData().getAbsolutePath());
		}
	}
	

	public static void main(String[] args) {
		//Create scanner for System.in
		Scanner scanner = new Scanner(System.in);
		//Get directory from user
		String dirPath = getInput(1, scanner);
		//Open the directory
		File dir = openDir(dirPath);
		//Create root node
		root = new Node<File>(dir);
		//Get the list of all the files in the directory
		getChildrenFromFile(root);
		//Wait for the user to continue execution
		String input = getInput(2, scanner);
		//Create root node for 2nd Tree
		File dir2 = openDir(dirPath);
		root2 = new Node<File>(dir2);
		//Get the list of all files in the directory
		getChildrenFromFile(root2);
		//Compare the two tree structures
		compareFiles();
		//Print out the results of the comparison
		System.out.println("Files added to directory structure:");
		printList(added);
		System.out.println("Files deleted from directory structure:");
		printList(deleted);
		//Close scanner
		scanner.close();
	}

}
