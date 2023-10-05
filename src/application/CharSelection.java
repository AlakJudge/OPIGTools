package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

// save/load characters feature
public class CharSelection implements Serializable {

	private static final long serialVersionUID = 7504656738084623494L;

	String name;
	ArrayList<String> charsSelected;
	int[] dPower;
	static boolean cancelled = false;

	// constructor of what will be saved
	public CharSelection() {
		charsSelected = new ArrayList<>();
		dPower = new int[0];
	}

	// save feature
	public void saveSelection(ArrayList<String> charsSelected, int[] dPower) throws IOException {

		// Create a FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Serialized Files", "*.ser"));

		// Show the save dialog
		Stage stage = new Stage();
		java.io.File selectedFile = fileChooser.showSaveDialog(stage);

		if (selectedFile != null) {
			// Create the file output stream using the selected file
			try (FileOutputStream fileOut = new FileOutputStream(selectedFile);
					ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

				// Write the object to the output stream
				CharSelection selection = new CharSelection();
				selection.charsSelected = charsSelected;
				selection.dPower = dPower;
				out.writeObject(selection);
			}
		}
	}

	public void loadSelection() throws IOException, ClassNotFoundException {

		cancelled = false;

		// Create a FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Serialized Files", "*.ser"));

		// Show the open dialog
		Stage stage = new Stage();
		java.io.File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			// Create the file input stream using the selected file
			try (FileInputStream fileIn = new FileInputStream(selectedFile);
					ObjectInputStream in = new ObjectInputStream(fileIn)) {

				// Read the object from the input stream
				CharSelection selection = (CharSelection) in.readObject();

				// Assign the loaded values to the current instance
				this.charsSelected = selection.charsSelected;
				this.dPower = selection.dPower;
			}
		} else {
			cancelled = true;
		}
	}
}