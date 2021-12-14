package dict;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

import javax.swing.JFileChooser;

/**
 * 
 * This class has methods for reading from files, comparing the words with
 * the ones in the dict, and if there are misspelled words, it would give
 * suggestions for corrections back. The window will open to let you choose
 * the file, where you want to check its words.
 *
 * *******************************************
 *    Title: Lab 9: Sets in the Java Collection Framework.
 *    Author: University of the people.
 *    Date: 12/13/2021
 *    Availability: https://my.uopeople.edu/mod/page/view.php?id=268268&inpopup=1
 *    
 * *******************************************
 */
public class Dict { // Begin of Dict class.

	public static void main(String[] args) { // Begin of main.

		HashSet<String> wordsIn = new HashSet<>(); // for reading words.


		try {

			/**
			 * This is for the dict word file. You should put the path
			 * here.
			 */
			Scanner filein = new Scanner(new File("words.txt"));

			while (filein.hasNext()) {


				String token = filein.next();
				wordsIn.add(token.toLowerCase());

			}

			/**
			 * This is where you choose the file, you want its words to 
			 * be checked.
			 */
			Scanner otherFile = new Scanner(getInputFileNameFromUser());
			otherFile.useDelimiter("[^a-zA-Z]+");

			while (otherFile.hasNext()) {

				String token2 = otherFile.next();

				if (! wordsIn.contains(token2.toLowerCase())) {

					System.out.println(token2 + ": " + corrections(token2, wordsIn));

				}

			}

		} 

		catch (FileNotFoundException e) {

			e.printStackTrace();

		}

	} // End of main.

	/**
	 * Lets the user select an input file using a standard file
	 * selection dialog box.  If the user cancels the dialog
	 * without selecting a file, the return value is null.
	 * 
	 * *******************************************
	 *    Title: Lab 9: Sets in the Java Collection Framework.
	 *    Author: University of the people.
	 *    Date: 12/13/2021
	 *    Availability: https://my.uopeople.edu/mod/page/view.php?id=268268&inpopup=1
	 *    
	 * *******************************************
	 */
	static File getInputFileNameFromUser() { // Begin of method.

		JFileChooser fileDialog = new JFileChooser();
		fileDialog.setDialogTitle("Select File for Input");
		int option = fileDialog.showOpenDialog(null);

		if (option != JFileChooser.APPROVE_OPTION) {

			return null;

		}

		else {

			return fileDialog.getSelectedFile();

		}

	} // End of getInputFileNameFromUse method.

	/**
	 * This method returns suggestions back, if the word is misspelled.
	 * This method might return no suggestions back, if there are no 
	 * suggestions available.
	 * 
	 * @param String badWord.
	 * @param HashSet dictionary.
	 * @return TreeSet.
	 */
	static TreeSet corrections(String badWord, HashSet dictionary) { // Begin of method.

		TreeSet<String> misspelled = new TreeSet<>();

		/**
		 * This loop deletes any one of the letters and check for right
		 * possibilities in the dictionary.
		 */
		for (int i = 0; i < badWord.length(); i++) {

			String s = badWord.substring(0,i) + badWord.substring(i+1);

			if(dictionary.contains(s)) {

				misspelled.add(s);

			}

		}

		/**
		 * This loop change any letter in the word with another, and 
		 * compares it with the words in the dict.
		 */
		for (int i = 0; i < badWord.length(); i++) {

			for (char ch = 'a'; ch <= 'z'; ch++) {

				String s = badWord.substring(0,i) + ch + badWord.substring(i+1);

				if (dictionary.contains(s)) {

					misspelled.add(s);

				}

			}

		}

		/**
		 * This loop insert any letter at any point in the misspelled word,
		 * and compares it with the words in the dict.
		 */
		for (int i = 0; i < badWord.length(); i++) {

			for (char ch = 'a'; ch <= 'z'; ch++) {

				String s = badWord.substring(0, i) + ch + badWord.substring(i);

				if (dictionary.contains(s)) {

					misspelled.add(s);

				}

			}

		}

		/**
		 * This loop swap any two neighboring letters with each other,
		 * and then compare the word with the ones in the dict.
		 */
		for (int i = 0; i < badWord.length()-1; i++) {

			String s = badWord.substring(0,i) + badWord.charAt(i+1) +
					badWord.charAt(i) + badWord.substring(i+2);

			if (dictionary.contains(s)) {

				misspelled.add(s);

			}

		}

		/**
		 * This loop insert a space at any point in the misspelled word,
		 * compares each part of the word with the ones in the dict.
		 * If the two are in the dict, then it returns them.
		 */
		for (int i = 0; i < badWord.length(); i++) {

			String s1 = badWord.substring(i,i+1);
			String s2 = badWord.substring(i+1);

			if (dictionary.contains(s1) && dictionary.contains(s2)) {

				misspelled.add( s1 + " " + s2);

			}

		}

		if (misspelled.isEmpty()) {

			misspelled.add("(no suggestions)");

		}

		return misspelled;

	} // End of corrections method.

} // End of Dict class.
