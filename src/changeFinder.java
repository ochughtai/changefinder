import java.io.File; 
import java.util.*;



public class changeFinder{
	
	//Allocate root nodes
	static Node<File> root;
	static Node<File> root2;
	//Allocate Lists of Differences
	static ArrayList<String> added = new ArrayList<String>();
	static ArrayList<String> deleted = new ArrayList<String>();
	
	public static String getInput(int prompt) {
		//Create scanner for System.in
		Scanner scanner = new Scanner(System.in);
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
		
		//Close Scanner
		scanner.close();
		
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
	
	public static void getChildren(Node<File> parent) {
		
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
				getChildren(dir);
			}
		}
		
		
	}
	
	/*
	 * Recursively compares the files from both trees and returns lists of differences 
	 */
	public static void compareFiles(Node<File> parent, Node<File> parent2){
		
		//Get children from both lists
		List<Node<File>> children1 = parent.getChildren();
		List<Node<File>> children2 = parent2.getChildren();
		
		//Allocate list of children in both trees
		List<Node<File>> inBoth = null;
		
		//Find files added
		for (Node<File> child2 : children2){
			boolean found = false;
			for (Node<File> child : children1){
				if (child.getData().getAbsolutePath() == child2.getData().getAbsolutePath()) {
					found = true;
				}
			}
			if (!found){
				added.add(child2.getData().getAbsolutePath());
				//Recursively add it's children to the list
				//TODO
			}
			else{
				inBoth.add(child2);
			}
		}
		
		//Find files deleted
		for (Node<File> child : children1){
			boolean found = false;
			for (Node<File> child2 : children2){
				if (child.getData().getAbsolutePath() == child2.getData().getAbsolutePath()) {
					found = true;
				}
			}
			if (!found){
				deleted.add(child.getData().getAbsolutePath());
				//Recursively add its children to the list
				//TODO
			}
			else{
				inBoth.add(child);
			}
		}
		//TODO
		//Restructure this to check to see if the nodes are in both, otherwise do something
		
	}
	
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
	

	public static void main(String[] args) {
		//Get directory from user
		String dirPath = getInput(1);
		//Open the directory
		File dir = openDir(dirPath);
		//Create root node
		root = new Node<File>(dir);
		//Get the list of all the files in the directory
		getChildren(root);
		//Print all the files to console
//		printFiles(root);
		//Create root node for 2nd Tree
		root2 = new Node<File>(dir);
		//Get the list of all files in the directory
		getChildren(root2);
		

	}

}
