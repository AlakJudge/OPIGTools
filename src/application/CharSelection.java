package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

// save/load characters feature
public class CharSelection implements Serializable {

	private static final long serialVersionUID = 7504656738084623494L;
	
    private static final String LAST_LOADED_FILE_KEY = "lastLoadedFile";
    private static final String LAST_LOADED_FILE_LOCATION_KEY = "lastLoadedFileLocation";
    
	
	String name;
	ArrayList<String> charsSelected;
	int[] savedInput;
	static boolean cancelled = false;
	int levelSaved;
	int dPowerSaved;

	// constructor of what will be saved
	public CharSelection() {
		charsSelected = new ArrayList<>();
		savedInput = new int[0];
		levelSaved = 0;
		dPowerSaved = 0;
	}

	// save feature
	public void saveSelection(ArrayList<String> charsSelected, int[] savedInput, int levelSaved, int dPowerSaved) throws IOException {

		// Create a FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Serialized Files", "*.ser"));

		// Show the save dialog
		Stage stage = new Stage();
		File selectedFile = fileChooser.showSaveDialog(stage);

		if (selectedFile != null) {
			// Create the file output stream using the selected file
			try (FileOutputStream fileOut = new FileOutputStream(selectedFile);
					ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

				// Write the object to the output stream
				CharSelection selection = new CharSelection();
				selection.charsSelected = charsSelected;
				selection.savedInput = savedInput;
				selection.levelSaved = levelSaved;
				selection.dPowerSaved = dPowerSaved;
				out.writeObject(selection);
			}
		}
	}

	public void loadSelection() throws IOException, ClassNotFoundException {

		cancelled = false;

		// Create a FileChooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Serialized Files", "*.ser"));

	    // Retrieve the last location and last file from preferences
	    Preferences prefs = Preferences.userNodeForPackage(CharSelection.class);
	  
	    String lastFile = prefs.get(LAST_LOADED_FILE_KEY, null);
	    String lastLocation = prefs.get(LAST_LOADED_FILE_LOCATION_KEY, null);
	    
	    // Load last file loaded previously
	    if (Controller.loadLastFile) {
	    	if (lastFile != null) {
								
				// Create the file input stream using the selected file
				try (FileInputStream fileIn = new FileInputStream(lastFile);
						ObjectInputStream in = new ObjectInputStream(fileIn)) {

					// Read the object from the input stream
					CharSelection selection = (CharSelection) in.readObject();

					// Assign the loaded values to the current instance
					this.charsSelected = selection.charsSelected;
					this.savedInput = selection.savedInput;
					this.dPowerSaved = selection.dPowerSaved;
					this.levelSaved = selection.levelSaved;
				}
			} 
	    	else {
				cancelled = true;
			}
	    }
	    else {
	    	// Setting the directory of the last loaded file as the default one
		    if (lastLocation != null) {
		    	fileChooser.setInitialDirectory(new File(lastLocation));
		    }
		    
		    // Show the open dialog
			Stage stage = new Stage();
			File selectedFile = fileChooser.showOpenDialog(stage);
			
			if (selectedFile != null) {	
				// Save path of file and its directory
				saveLastFileLocation(selectedFile.getAbsolutePath(), selectedFile.getParent());
				
				// Create the file input stream using the selected file
				try (FileInputStream fileIn = new FileInputStream(selectedFile);
						ObjectInputStream in = new ObjectInputStream(fileIn)) {

					// Read the object from the input stream
					CharSelection selection = (CharSelection) in.readObject();

					// Assign the loaded values to the current instance
					this.charsSelected = selection.charsSelected;
					this.savedInput = selection.savedInput;
					this.dPowerSaved = selection.dPowerSaved;
					this.levelSaved = selection.levelSaved;
				}
			} else {
				cancelled = true;
			}
	    }		
	}
	
    // Method to save the last loaded file and its location 
    public static void saveLastFileLocation(String lastFile, String lastLocation) {
        Preferences prefs = Preferences.userNodeForPackage(CharSelection.class);
        prefs.put(LAST_LOADED_FILE_KEY, lastFile);
        prefs.put(LAST_LOADED_FILE_LOCATION_KEY, lastLocation);
        
    }
}