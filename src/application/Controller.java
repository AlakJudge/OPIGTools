package application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Controller implements Initializable {
	@FXML
	Pane ruinsPane, bestTeamAnchorPane, paypalButton;
	@FXML
	ChoiceBox<Integer> numMembersChoiceBox;
	@FXML
	FlowPane sssCharSelectionPane, ssCharSelectionPane, sCharSelectionPane, changeDPowerPane, bestTeamPane;
	@FXML
	ScrollPane changeDPowerScrollPane, sssCharSelectionScrollPane;
	@FXML
	Label bestTeamPower, ruinsLabel;
	@FXML
	Button backToSelectionButton, submitCharsButton, changeDPowerButton, allSSSButton, allSSButton, allSButton, sButton,
			ssButton, sssButton, exitButton;
	@FXML
	ToggleButton orderAZButton;
	@FXML
	ProgressBar progressBar = new ProgressBar();

	ArrayList<CheckBoxImage> originalOrderSSS;
	ArrayList<CheckBoxImage> originalOrderSS;
	ArrayList<CheckBoxImage> originalOrderS;
	boolean sssToggled = false;
	boolean ssToggled = false;
	boolean sToggled = false;
	boolean green = false;

	static Thread calculationThread;
	static Thread updateUIThread;

	ArrayList<Character> characterList = new ArrayList<>();
	ArrayList<String> charsSelected = new ArrayList<>();
	ArrayList<String> loadedCharsSelected = new ArrayList<>();
	ArrayList<Character> unitList = new ArrayList<>();
	ArrayList<String> charNames = new ArrayList<>();
	Character[] unit;
	Pane currentPane;
	Boolean ruinsToggle = false;

	TextField[] powerTextField;
	Character character[];
	String sPower[];

	final int MAX_WIDTH = 120;
	final int MAX_HEIGHT = 150;

	// SSS CHACACTER ICONS
	String[] sssImageTooltips = { "Prime Whitebeard", "Zephyr", "Robin - Christmas", "Yamato", "Mihawk (Summit War)", "God Usopp", "Sanji Germa",
			"Enma Zoro", "Oden", "Nami (Valentine's Day)", "Carrot", "Kaido", "Magellan", "Charlotte Linlin - Lily",
			"Swimsuit - Hancock", "Blackbeard", "Snakeman Luffy", "Golden Lion", "Fujitora", "Shanks", "Rayleigh",
			"Charlotte Linlin", "Kizaru", "Aokiji", "Sengoku", "Whitebeard", "Akainu", "Garp", "Mihawk"};

	String[] sssSelectedImagePaths = { "resources/Prime WhitebeardBW.png", "resources/ZephyrBW.png", "resources/Robin - ChristmasBW.png", "resources/YamatoBW.png",
			"resources/Mihawk (Summit War)BW.png", "resources/God UsoppBW.png", "resources/Sanji GermaBW.png",
			"resources/Enma ZoroBW.png", "resources/OdenBW.png", "resources/Nami (Valentine's Day)BW.png",
			"resources/CarrotBW.png", "resources/KaidoBW.png", "resources/MagellanBW.png",
			"resources/Charlotte Linlin - LilyBW.png", "resources/Swimsuit - HancockBW.png",
			"resources/BlackbeardBW.png", "resources/Snakeman LuffyBW.png", "resources/Golden LionBW.png",
			"resources/FujitoraBW.png", "resources/ShanksBW.png", "resources/RayleighBW.png",
			"resources/Charlotte LinlinBW.png", "resources/KizaruBW.png", "resources/AokijiBW.png",
			"resources/SengokuBW.png", "resources/WhitebeardBW.png", "resources/AkainuBW.png", "resources/GarpBW.png",
			"resources/MihawkBW.png"};

	String[] sssImagePaths = { "resources/Prime Whitebeard.png", "resources/Zephyr.png", "resources/Robin - Christmas.png", "resources/Yamato.png",
			"resources/Mihawk (Summit War).png", "resources/God Usopp.png", "resources/Sanji Germa.png",
			"resources/Enma Zoro.png", "resources/Oden.png", "resources/Nami (Valentine's Day).png",
			"resources/Carrot.png", "resources/Kaido.png", "resources/Magellan.png",
			"resources/Charlotte Linlin - Lily.png", "resources/Swimsuit - Hancock.png", "resources/Blackbeard.png",
			"resources/Snakeman Luffy.png", "resources/Golden Lion.png", "resources/Fujitora.png",
			"resources/Shanks.png", "resources/Rayleigh.png", "resources/Charlotte Linlin.png", "resources/Kizaru.png",
			"resources/Aokiji.png", "resources/Sengoku.png", "resources/Whitebeard.png", "resources/Akainu.png",
			"resources/Garp.png", "resources/Mihawk.png"};

	// SS CHACACTER ICONS
	String[] ssImageTooltips = { "Law (Supernova)", "Katakuri", "Vinsmoke Ichiji", "Zoro (Supernova)", "Ace",
			"Doflamingo", "Sabo", "Enel", "Sanji (Supernova)", "Kuma", "Marco", "Boa Hancock", "Law",
			"Nightmare Luffy" };

	String[] ssImagePaths = { "resources/Law (Supernova).png", "resources/Katakuri.png",
			"resources/Vinsmoke Ichiji.png", "resources/Zoro (Supernova).png", "resources/Ace.png",
			"resources/Doflamingo.png", "resources/Sabo.png", "resources/Enel.png", "resources/Sanji (Supernova).png",
			"resources/Kuma.png", "resources/Marco.png", "resources/Boa Hancock.png", "resources/Law.png",
			"resources/Nightmare Luffy.png" };

	String[] ssSelectedImagePaths = { "resources/Law (Supernova)BW.png", "resources/KatakuriBW.png",
			"resources/Vinsmoke IchijiBW.png", "resources/Zoro (Supernova)BW.png", "resources/AceBW.png",
			"resources/DoflamingoBW.png", "resources/SaboBW.png", "resources/EnelBW.png",
			"resources/Sanji (Supernova)BW.png", "resources/KumaBW.png", "resources/MarcoBW.png",
			"resources/Boa HancockBW.png", "resources/LawBW.png", "resources/Nightmare LuffyBW.png" };

	// S CHACACTER ICONS
	String[] sImageTooltips = { "Luffy (Supernova)", "Usopp (Supernova)", "Moria", "Hawkins", "Kid", "Buggy", "Jozu",
			"Smoker", "Chopper (Supernova)", "Bartolomeo", "Robin (Supernova)", "Cavendish", "Ivankov",
			"Nami (Supernova)", "Lucci", "Crocodile", "Franky (Supernova)", "Vista", "Jimbei", "Vinsmoke Reiju" };

	String[] sImagePaths = { "resources/Luffy (Supernova).png", "resources/Usopp (Supernova).png",
			"resources/Moria.png", "resources/Hawkins.png", "resources/Kid.png", "resources/Buggy.png",
			"resources/Jozu.png", "resources/Smoker.png", "resources/Chopper (Supernova).png",
			"resources/Bartolomeo.png", "resources/Robin (Supernova).png", "resources/Cavendish.png",
			"resources/Ivankov.png", "resources/Nami (Supernova).png", "resources/Lucci.png", "resources/Crocodile.png",
			"resources/Franky (Supernova).png", "resources/Vista.png", "resources/Jimbei.png",
			"resources/Vinsmoke Reiju.png" };

	String[] sSelectedImagePaths = { "resources/Luffy (Supernova)BW.png", "resources/Usopp (Supernova)BW.png",
			"resources/MoriaBW.png", "resources/HawkinsBW.png", "resources/KidBW.png", "resources/BuggyBW.png",
			"resources/JozuBW.png", "resources/SmokerBW.png", "resources/Chopper (Supernova)BW.png",
			"resources/BartolomeoBW.png", "resources/Robin (Supernova)BW.png", "resources/CavendishBW.png",
			"resources/IvankovBW.png", "resources/Nami (Supernova)BW.png", "resources/LucciBW.png",
			"resources/CrocodileBW.png", "resources/Franky (Supernova)BW.png", "resources/VistaBW.png",
			"resources/JimbeiBW.png", "resources/Vinsmoke ReijuBW.png" };

	boolean allSelectedSSS = false;
	boolean allSelectedSS = false;
	boolean allSelectedS = false;
	CheckBoxImage[] checkboxImageSSS = new CheckBoxImage[29];
	CheckBoxImage[] checkboxImageSS = new CheckBoxImage[14];
	CheckBoxImage[] checkboxImageS = new CheckBoxImage[20];
	Tooltip tooltipSSS[] = new Tooltip[29];
	Tooltip tooltipSS[] = new Tooltip[14];
	Tooltip tooltipS[] = new Tooltip[20];
	Integer[] numMembersNumbers = { 2, 3, 4, 5, 6, 7, 8 };

	private static final String TOOLTIP = "tooltip";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		numMembersChoiceBox.getItems().addAll(numMembersNumbers);
		numMembersChoiceBox.setOnAction(this::setNumMembers);
		numMembersChoiceBox.setValue(7);
		changeDPowerScrollPane.setVisible(false);
		bestTeamAnchorPane.setVisible(false);
		backToSelectionButton.setVisible(false);
		orderAZButton.setVisible(false);
		comparisonPane.setVisible(false);
		splitPane.setDividerPosition(0, 0.5);
		SplitPane.setResizableWithParent(splitPane.getItems().get(0), false);
		
		unit = new Character[63];

		// SSS chars
		unit[0] = new Character("Aokiji", "SSS", 157, "marines", "swordsmaster", "piratesSH", 2567159, 167898, 24983,
				445);
		unit[1] = new Character("Mihawk", "SSS", 157, "marines", "swordsmaster", "will", 1724031, 180936, 11659, 395);
		unit[2] = new Character("Golden Lion", "SSS", 157, "wayOfFreedom", 1724108, 180902, 11712, 410);
		unit[3] = new Character("Fujitora", "SSS", 157, "wayOfFreedom", 1724108, 180902, 11712, 425);
		unit[4] = new Character("Kizaru", "SSS", 157, "wayOfFreedom", 1159997, 146539, 10553, 425);
		unit[5] = new Character("Snakeman Luffy", "SSS", 157, "swordsmaster", "colonel", 2425897, 98896, 13385, 421);
		unit[6] = new Character("Shanks", "SSS", 157, "swordsmaster", "will", 1159997, 146539, 10553, 414);
		unit[7] = new Character("Akainu", "SSS", 157, "swordsmaster", 2425907, 98913, 13324, 409);
		unit[8] = new Character("Sengoku", "SSS", 157, "will", "marineShichibukai", 2635858, 108099, 16239, 415);
		unit[9] = new Character("Garp", "SSS", 157, "will", "marineShichibukai", 2248187, 112009, 12491, 419);
		unit[10] = new Character("Whitebeard", "SSS", 157, "piratesPursuit", 1724031, 180936, 11659, 421);
		unit[11] = new Character("Rayleigh", "SSS", 157, "yonko", 1359739, 180902, 11712, 415);
		unit[12] = new Character("Kaido", "SSS", 157, "yonko", 2635841, 93116, 16313, 415);
		unit[13] = new Character("Oden", "SSS", 157, "breakthrough", 1724108, 180902, 11712, 419);
		unit[14] = new Character("Swimsuit - Hancock", "SSS", 157, "cyborg", 2567264, 167852, 25097, 416);
		unit[15] = new Character("Robin - Christmas", "SSS", 157, "SHparamecia", 3908232, 134164, 25097, 420);
		unit[16] = new Character("Blackbeard", "SSS", 157, "shichibukaiVinsmoke", 2567264, 167898, 25097, 425);
		unit[17] = new Character("Nami (Valentine's Day)", "SSS", 157, "piratesSH", 2567264, 167852, 25097, 423);
		unit[18] = new Character("Yamato", "SSS", 157, "will", 1882743, 180936, 12548, 419);
		unit[19] = new Character("God Usopp", "SSS", 157, "breakthrough", 1563294, 103564, 25097, 408);
		unit[20] = new Character("Sanji Germa", "SSS", 157, "breakthrough", 2390912, 127959, 11712, 409);
		unit[21] = new Character("Enma Zoro", "SSS", 157, "breakthrough", 1359739, 180936, 11712, 418);
		unit[22] = new Character("Carrot", "SSS", 157, "cyborg", 2425897, 98896, 13385, 417);
		unit[23] = new Character("Charlotte Linlin - Lily", "SSS", 157, "marineShichibukai", 1359739, 180936, 11712, 418);
		unit[24] = new Character("Charlotte Linlin", "SSS", 157, "yonko", 2567159, 167898, 24983, 410);
		unit[25] = new Character("Mihawk (Summit War)", "SSS", 157, "shichibukai", 1922920, 127959, 11712, 412);
		unit[26] = new Character("Magellan", "SSS", 157, "paramecia", 2635841, 108082, 16313, 420);
		unit[27] = new Character("Prime Whitebeard", "SSS", 157, "paramecia", "captain", "yonko", 1901456, 158585, 12139, 415);
		unit[28] = new Character("Zephyr", "SSS", 157, "marines", "marineShichibukai", 2345424, 125630, 12548, 425);
		// SS chars
		unit[29] = new Character("Zoro (Supernova)", "SS", 105, "swordsmaster", "SHsupernova", 1091668, 112980, 11659,
				415);
		unit[30] = new Character("Ace", "SS", 105, "swordsmaster", 1091668, 112980, 11659, 407);
		unit[31] = new Character("Doflamingo", "SS", 105, "swordsmaster", "will", "shichibukaiVinsmoke", 1091668,
				112980, 11659, 415);
		unit[32] = new Character("Sabo", "SS", 105, "swordsmaster", "piratesSH", 1091668, 112980, 11659, 417);
		unit[33] = new Character("Enel", "SS", 105, "swordsmaster", "skypiea", 1617146, 105743, 24983, 425);
		unit[34] = new Character("Katakuri", "SS", 105, "SHparamecia", "vinsmoke", "will", 1617216, 105715, 25097, 409);
		unit[35] = new Character("Kuma", "SS", 105, "will", "marineShichibukai", 1413372, 70888, 12491, 419);
		unit[36] = new Character("Marco", "SS", 105, "will", "paramecia", "thrillerBark", "colonel", 1491760, 70888,
				17322, 410);
		unit[37] = new Character("Boa Hancock", "SS", 105, "will", "shichibukaiVinsmoke", "shichibukai", 1130454, 68517,
				31090, 397);
		unit[38] = new Character("Nightmare Luffy", "SS", 105, "will", 1617146, 105743, 24983, 366);
		unit[39] = new Character("Law (Supernova)", "SS", 105, "captain", 1617216, 105715, 25097, 400);
		unit[40] = new Character("Vinsmoke Ichiji", "SS", 105, "will", "vinsmoke", "shichibukaiVinsmoke", 862119,
				112957, 11712, 415);
		unit[41] = new Character("Law", "SS", 105, "marines", "shichibukai", 1617146, 105743, 24983, 400);
		unit[42] = new Character("Sanji (Supernova)", "SS", 105, "marines", "swordsmaster", "piratesSH", 1617146,
				105743, 24983, 441);
		// S chars
		unit[43] = new Character("Bartolomeo", "S", 63, "swordsmaster", "piratesSH", 707375, 46247, 24983, 406);
		unit[44] = new Character("Cavendish", "S", 63, "swordsmaster", "piratesSH", 707375, 46247, 24983, 404);
		unit[45] = new Character("Vista", "S", 63, "swordsmaster", "colonel", 540452, 52669, 11659, 401);
		unit[46] = new Character("Buggy", "S", 63, "SHparamecia", "will", "thrillerBark", "paramecia", 469621, 33950,
				16239, 370);
		unit[47] = new Character("Jozu", "S", 63, "will", "paramecia", "thrillerBark", "SHparamecia", 540452, 52669,
				11659, 390);
		unit[48] = new Character("Smoker", "S", 63, "will", "paramecia", "thrillerBark", "colonel", 769621, 33950,
				16239, 400);
		unit[49] = new Character("Lucci", "S", 63, "will", "vinsmoke", 540452, 52669, 11659, 400);
		unit[50] = new Character("Crocodile", "S", 63, "will", "paramecia", "vinsmoke", "shichibukaiVinsmoke", 707375,
				46247, 24983, 398);
		unit[51] = new Character("Franky (Supernova)", "S", 63, "will", "piratesRevolutionaries", "cyborg", 757904, 36500, 13324,
				390);
		unit[52] = new Character("Vinsmoke Reiju", "S", 63, "will", "piratesPursuit", "vinsmoke", 540452, 52669, 11659,
				405);
		unit[53] = new Character("Jimbei", "S", 63, "piratesPursuit", "SHsupernova", 540452, 52669, 11659, 380);
		unit[54] = new Character("Moria", "S", 63, "paramecia", "thrillerBark", 540846, 33586, 35532, 382);
		unit[55] = new Character("Hawkins", "S", 63, "paramecia", "thrillerBark", 540452, 52669, 11659, 408);
		unit[56] = new Character("Kid", "S", 63, "paramecia", "thrillerBark", "breakthrough", 540452, 52669, 11659,
				408);
		unit[57] = new Character("Nami (Supernova)", "S", 63, "SHparamecia", "breakthrough", 707375, 46247, 24983, 380);
		unit[58] = new Character("Ivankov", "S", 63, "piratesRevolutionaries", 707375, 46247, 24983, 380);
		unit[59] = new Character("Chopper (Supernova)", "S", 63, "SHsupernova", 707375, 46247, 24983, 380);
		unit[60] = new Character("Robin (Supernova)", "S", 63, "SHsupernova", 707375, 46247, 24983, 406);
		unit[61] = new Character("Luffy (Supernova)", "S", 63, "swordsmaster", "SHsupernova", 757904, 36500, 13324,
				400);
		unit[62] = new Character("Usopp (Supernova)", "S", 63, "swordsmaster", 540452, 52669, 11659, 398);			
		
		// adding all characters to a list
		for (Character character : unit) {
			unitList.add(character);
		}
		
		// adding all character names to a list of names
		for (int i = 0; i < unit.length; i++) {
			charNames.add(unit[i].getName());
		}
		
		// setting up the initial setup of the program
		powerTextField = new TextField[unitList.size()];
		character = new Character[unitList.size()];
		sPower = new String[unitList.size()];
		createSCheckboxes();
		createSSCheckboxes();
		createSSSCheckboxes();
		ruinsPane.setVisible(false);
		sssCharSelectionPane.setVisible(false);
		ssCharSelectionPane.setVisible(false);
		sCharSelectionPane.setVisible(false);	
		
		// Setting up paypal button
		Image paypal = new Image(getClass().getResourceAsStream("resources/paypaldonate.png"));
		ImageView paypalView = new ImageView(paypal);
		paypalView.setFitWidth(paypalButton.getPrefWidth());
		paypalView.setFitHeight(paypalButton.getPrefHeight());
		paypalView.setOnMouseEntered(e -> paypalView.setCursor(Cursor.OPEN_HAND));
		paypalView.setOnMouseExited(e -> paypalView.setCursor(Cursor.DEFAULT));
		paypalButton.getChildren().add(paypalView);
	}

	public static Character findUnit(String name, ArrayList<Character> unitList) { // method to use a string name to find the character unit
		for (Character character : unitList) {
			if (name.equals(character.name)) {
				return character;
			}
		}
		return null;
	}

	public void ruinsVisible() { // methods button use to the the main ruins feature pane visible
		ruinsPane.setVisible(true);
		comparisonPane.setVisible(false);
	}

	public void setNumMembers(ActionEvent e) { // get the vale from the choice box and make sure it's set to the correct variable when needed
		int selectedValue = numMembersChoiceBox.getValue();
		Main.numMembers = selectedValue;
	}

	public class CheckBoxImage extends Pane { // creating custom checkbox class that adds an image instead of a box

		private Image unselectedImage;
		private Image selectedImage;
		private boolean selected;
		private CheckBox checkBox;

		public CheckBoxImage(Image unselectedImage, Image selectedImage) { // constructor for creating the checkbox taking in both selected and unselected images

			this.unselectedImage = unselectedImage;
			this.selectedImage = selectedImage;
			this.selected = false;

			ImageView imageView = new ImageView(unselectedImage);
			imageView.setFitWidth(MAX_WIDTH);
			imageView.setFitHeight(MAX_HEIGHT);

			checkBox = new CheckBox();
			checkBox.setOpacity(0); // Set the checkbox to be transparent
			checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> { // observer for when the checkbox is selected. Run updateImage if it is.
				selected = newValue;
				updateImage();
			});

			this.getChildren().addAll(imageView, checkBox);
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
			checkBox.setSelected(selected);
			updateImage();
		}

		private void updateImage() { // update selected and unselected image depending on checkbox status
			ImageView imageView = (ImageView) this.getChildren().get(0);
			boolean isSelected = checkBox.isSelected();

			if (isSelected) {
				imageView.setImage(selectedImage);
			} else {
				imageView.setImage(unselectedImage);
			}
		}

		public void selectAll() {
			selected = true;
			checkBox.setSelected(true);
			updateImage();
		}

		public void deselectAll() {
			selected = false;
			checkBox.setSelected(false);
			updateImage();
		}

		public Image getImage() {
			return unselectedImage;
		}

		public String getTooltipText() {
			Tooltip tTooltip = (Tooltip) this.getProperties().get(TOOLTIP);
			String tooltip = tTooltip.getText();
			return tooltip;
		}

		public void setTooltipText(String text) {

			Tooltip tooltip = new Tooltip(text);
			this.getProperties().put(TOOLTIP, tooltip);
			Tooltip.install(this, tooltip);
		}
	}

	public void createSSSCheckboxes() { // loop creating the checkboxes with the SSS character's icons to select

		for (int i = 0; i < checkboxImageSSS.length; i++) {
			final int index = i;
			String imagePath = sssImagePaths[i];
			Image image = new Image(getClass().getResourceAsStream(imagePath));
			Image selectedImage = new Image(getClass().getResourceAsStream(sssSelectedImagePaths[i]));

			checkboxImageSSS[i] = new CheckBoxImage(image, selectedImage);
			checkboxImageSSS[i].setOnMouseClicked(event -> {
				checkboxImageSSS[index].setSelected(!checkboxImageSSS[index].isSelected());
			});

			checkboxImageSSS[i].setTooltipText(sssImageTooltips[i]);
			sssCharSelectionPane.getChildren().add(checkboxImageSSS[i]);
		}
		// making sure the select all button is at the end
		sssCharSelectionPane.getChildren().remove(allSSSButton);
		sssCharSelectionPane.getChildren().add(allSSSButton);

	}

	public void createSSCheckboxes() { // loop creating the checkboxes with the SS character's icons to select
		for (int i = 0; i < checkboxImageSS.length; i++) {
			final int index = i;
			String imagePath = ssImagePaths[i];
			Image image = new Image(getClass().getResourceAsStream(imagePath));
			Image selectedImage = new Image(getClass().getResourceAsStream(ssSelectedImagePaths[i]));

			checkboxImageSS[i] = new CheckBoxImage(image, selectedImage);
			checkboxImageSS[i].setOnMouseClicked(event -> {
				checkboxImageSS[index].setSelected(!checkboxImageSS[index].isSelected());
			});

			checkboxImageSS[i].setTooltipText(ssImageTooltips[i]);
			ssCharSelectionPane.getChildren().add(checkboxImageSS[i]);
		}
		// making sure the select all button is at the end
		ssCharSelectionPane.getChildren().remove(allSSButton);
		ssCharSelectionPane.getChildren().add(allSSButton);
	}

	public void createSCheckboxes() { // loop creating the checkboxes with the S character's icons to select
		for (int i = 0; i < checkboxImageS.length; i++) {
			final int index = i;
			String imagePath = sImagePaths[i];
			Image image = new Image(getClass().getResourceAsStream(imagePath));
			Image selectedImage = new Image(getClass().getResourceAsStream(sSelectedImagePaths[i]));

			checkboxImageS[i] = new CheckBoxImage(image, selectedImage);
			checkboxImageS[i].setOnMouseClicked(event -> {
				checkboxImageS[index].setSelected(!checkboxImageS[index].isSelected());
			});

			checkboxImageS[i].setTooltipText(sImageTooltips[i]);
			sCharSelectionPane.getChildren().add(checkboxImageS[i]);
		}
		// making sure the select all button is at the end
		sCharSelectionPane.getChildren().remove(allSButton);
		sCharSelectionPane.getChildren().add(allSButton);
	}

	public void selectAllSSSCheckboxes() {

		if (!allSelectedSSS) {
			for (Node node : sssCharSelectionPane.getChildren()) {
				if (node instanceof CheckBoxImage) {
					CheckBoxImage checkBoxImage = (CheckBoxImage) node;
					checkBoxImage.selectAll();
				}
			}
			allSelectedSSS = true;
		}

		else if (allSelectedSSS) {
			for (Node node : sssCharSelectionPane.getChildren()) {
				if (node instanceof CheckBoxImage) {
					CheckBoxImage checkBoxImage = (CheckBoxImage) node;
					checkBoxImage.deselectAll();
				}
			}
			allSelectedSSS = false;
		}
	}

	public void selectAllSSCheckboxes() {

		if (!allSelectedSS) {
			for (Node node : ssCharSelectionPane.getChildren()) {
				if (node instanceof CheckBoxImage) {
					CheckBoxImage checkBoxImage = (CheckBoxImage) node;
					checkBoxImage.selectAll();
				}
			}
			allSelectedSS = true;
		}

		else if (allSelectedSS) {
			for (Node node : ssCharSelectionPane.getChildren()) {
				if (node instanceof CheckBoxImage) {
					CheckBoxImage checkBoxImage = (CheckBoxImage) node;
					checkBoxImage.deselectAll();
				}
			}
			allSelectedSS = false;
		}
	}

	public void selectAllSCheckboxes() {

		if (!allSelectedS) {
			for (Node node : sCharSelectionPane.getChildren()) {
				if (node instanceof CheckBoxImage) {
					CheckBoxImage checkBoxImage = (CheckBoxImage) node;
					checkBoxImage.selectAll();
				}
			}
			allSelectedS = true;
		}

		else if (allSelectedS) {
			for (Node node : sCharSelectionPane.getChildren()) {
				if (node instanceof CheckBoxImage) {
					CheckBoxImage checkBoxImage = (CheckBoxImage) node;
					checkBoxImage.deselectAll();
				}
			}
			allSelectedS = false;
		}
	}

	public void deselectAllCheckboxes() {

		for (Node node : sssCharSelectionPane.getChildren()) {
			if (node instanceof CheckBoxImage) {
				CheckBoxImage checkBoxImage = (CheckBoxImage) node;
				checkBoxImage.deselectAll();
			}
		}
		for (Node node : ssCharSelectionPane.getChildren()) {
			if (node instanceof CheckBoxImage) {
				CheckBoxImage checkBoxImage = (CheckBoxImage) node;
				checkBoxImage.deselectAll();
			}
		}
		for (Node node : sCharSelectionPane.getChildren()) {
			if (node instanceof CheckBoxImage) {
				CheckBoxImage checkBoxImage = (CheckBoxImage) node;
				checkBoxImage.deselectAll();
			}
		}

		allSelectedS = false;
		allSelectedSS = false;
		allSelectedSSS = false;

	}

	public void sssPaneVisible() {
		sssCharSelectionPane.setVisible(true);
		sssCharSelectionScrollPane.setVisible(true);
		orderAZButton.setVisible(true);
		ssPaneInvisible();
		sPaneInvisible();
		currentPane = sssCharSelectionPane;
		updateToggleButtonStyle();
	}

	public void ssPaneVisible() {
		ssCharSelectionPane.setVisible(true);
		orderAZButton.setVisible(true);
		sssPaneInvisible();
		sPaneInvisible();
		currentPane = ssCharSelectionPane;
		updateToggleButtonStyle();
	}

	public void sPaneVisible() {
		sCharSelectionPane.setVisible(true);
		orderAZButton.setVisible(true);
		sssPaneInvisible();
		ssPaneInvisible();
		currentPane = sCharSelectionPane;
		updateToggleButtonStyle();
	}

	public void sssPaneInvisible() {
		sssCharSelectionPane.setVisible(false);
		sssCharSelectionScrollPane.setVisible(false);
	}

	public void ssPaneInvisible() {
		ssCharSelectionPane.setVisible(false);
	}

	public void sPaneInvisible() {
		sCharSelectionPane.setVisible(false);
	}

	public void convertCharsSelected(ArrayList<String> charList) { // converting the list of names from tooltips to a list of the actual Character objects
		for (int i = 0; i < charList.size(); i++) {
			String name = charList.get(i);
			characterList.add(findUnit(name, unitList));
			characterList.get(i).setdPower(characterList.get(i).getDefaultPower()); //settings their power to the default one
		}
	}

	public void changeDPower() { // method to run after selecting the characters

		// adding all selected chars to the CharsSelected array
		for (CheckBoxImage element : checkboxImageSSS) {
			if (element.isSelected()) {
				charsSelected.add(element.getTooltipText());
			}

		}
		for (CheckBoxImage element : checkboxImageSS) {
			if (element.isSelected()) {
				charsSelected.add(element.getTooltipText());
			}
		}
		for (CheckBoxImage element : checkboxImageS) {
			if (element.isSelected()) {
				charsSelected.add(element.getTooltipText());
			}
		}

		// only load the selected chars if the number selected is higher than the min number of members a team must have
		if (charsSelected.size() <= Main.numMembers) {
			showErrorPopup("Error", "Not enough characters selected!");
			charsSelected.clear();
		} else { 
			loadSelectedChars(); 
		}
	}

	public void loadSelectedChars() { // loading page with the selected characters and their powers that can be changed

		toggleRuinsPane("off");
		changeDPowerScrollPane.setVisible(true);
		sssPaneInvisible();
		ssPaneInvisible();
		sPaneInvisible();

		changeDPowerButton.setVisible(false);
		backToSelectionButton.setVisible(true);

		int numElements = charsSelected.size();
		String name[] = new String[numElements];
		Integer power[] = new Integer[numElements];
		Image icon[] = new Image[numElements];
		ImageView iconView[] = new ImageView[numElements];
		VBox vbox[] = new VBox[numElements];

		convertCharsSelected(charsSelected); // converst name list to character object list

		// loop creating each imagecheckbox and the textfield under it with the power
		for (int i = 0; i < numElements; i++) { 

			vbox[i] = new VBox();
			powerTextField[i] = new TextField();
			powerTextField[i].setPrefSize(MAX_WIDTH, 10);
			name[i] = charsSelected.get(i);
			character[i] = findUnit(name[i], unitList); // finding the character
			power[i] = character[i].getDefaultPower(); // getting default power
			if (sPower[i] == null) {
				sPower[i] = power[i].toString(); // converting to string in order to set as textbox default text
			}
			// loops getting the char icons
			for (CheckBoxImage element : checkboxImageSSS) {
				if (name[i].equals(element.getTooltipText())) {
					icon[i] = element.getImage();
					break;
				}
			}

			for (CheckBoxImage element : checkboxImageSS) {
				if (name[i].equals(element.getTooltipText())) {
					icon[i] = element.getImage();
					break;
				}
			}

			for (CheckBoxImage element : checkboxImageS) {
				if (name[i].equals(element.getTooltipText())) {
					icon[i] = element.getImage();
					break;
				}
			}

			iconView[i] = new ImageView(icon[i]);
			iconView[i].setPreserveRatio(true);
			iconView[i].setFitWidth(MAX_WIDTH);
			iconView[i].setFitHeight(MAX_HEIGHT);
			powerTextField[i].setText(sPower[i]);
			powerTextField[i].setAlignment(Pos.CENTER);

			vbox[i].getChildren().addAll(iconView[i], powerTextField[i]);
			changeDPowerPane.getChildren().add(vbox[i]);
		}
	}

	private void updateUI() { // update the UI after finishing calculating the best team  

		Platform.runLater(() -> {

			bestTeamPower.setVisible(true);
			progressBar.setVisible(false);
			bestTeamPane.setVisible(true);
			int numMembers = Main.numMembers;
			String name[] = new String[numMembers];
			String sPower[] = new String[numMembers];
			Image icon[] = new Image[numMembers];
			ImageView iconView[] = new ImageView[numMembers];
			Label powerLabel[] = new Label[numMembers];
			VBox vbox[] = new VBox[numMembers];

			for (int i = 0; i < numMembers; i++) {

				// getting the name and power of the chars in the best team
				sPower[i] = Integer.toString(TeamCalculator.bestTeam[0][i].dPower);
				name[i] = TeamCalculator.bestTeam[0][i].name;

				// loops getting the char icons
				for (CheckBoxImage element : checkboxImageSSS) {
					if (name[i].equals(element.getTooltipText())) {
						icon[i] = element.getImage();
						break;
					}
				}

				for (CheckBoxImage element : checkboxImageSS) {
					if (name[i].equals(element.getTooltipText())) {
						icon[i] = element.getImage();
						break;
					}
				}

				for (CheckBoxImage element : checkboxImageS) {
					if (name[i].equals(element.getTooltipText())) {
						icon[i] = element.getImage();
						break;
					}
				}

				iconView[i] = new ImageView(icon[i]);
				powerLabel[i] = new Label();
				vbox[i] = new VBox();
				iconView[i].setPreserveRatio(true);
				iconView[i].setFitWidth(MAX_WIDTH);
				iconView[i].setFitHeight(MAX_HEIGHT);
				powerLabel[i].setText(sPower[i]);
				powerLabel[i].setPrefSize(MAX_WIDTH, 10);
				powerLabel[i].setFont(Font.font("Palatino", 18));
				powerLabel[i].setStyle("-fx-font-weight: bold;");
				powerLabel[i].setTextFill(Color.WHITE);
				powerLabel[i].setAlignment(Pos.CENTER);
				powerLabel[i].getStyleClass().add("text-outline");
				vbox[i].getChildren().addAll(iconView[i], powerLabel[i]);
				bestTeamPane.getChildren().add(vbox[i]);
			}
			String highestPower = Integer.toString(TeamCalculator.highestPower);
			bestTeamPower.setText(highestPower);

		});
	}

	public static void showAlert(AlertType alertType, String title, String content) { // structure for different alerts
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void showInformationPopup(String title, String content) {
		showAlert(AlertType.INFORMATION, title, content);
	}

	public static void showWarningPopup(String title, String content) {
		showAlert(AlertType.WARNING, title, content);
	}

	public static void showErrorPopup(String title, String content) {
		showAlert(AlertType.ERROR, title, content);
	}

	public static void showConfirmationPopup(String title, String content) {
		showAlert(AlertType.CONFIRMATION, title, content);
	}

	public static void removeAllChildren(Pane pane) { // method to clear a pane completely
		pane.getChildren().clear();
	}

	public Pane currentPane() { // method to keep tracking of the current selected pane
		return currentPane;
	}

	public void toggleRuinsPane(String status) { //method to make easier the transition of the ruins feature

		if (status.equals("off")) {
			ruinsLabel.setVisible(false);
			numMembersChoiceBox.setVisible(false);
			sButton.setVisible(false);
			ssButton.setVisible(false);
			sssButton.setVisible(false);
			orderAZButton.setVisible(false);
			ruinsToggle = false;
		} else if (status.equals("on")) {
			ruinsLabel.setVisible(true);
			numMembersChoiceBox.setVisible(true);
			sButton.setVisible(true);
			ssButton.setVisible(true);
			sssButton.setVisible(true);
			ruinsToggle = true;
		}
	}

	public void reset() { // reset the whole program and allow a new team to be created without any side effects
		TeamCalculator.resetBest();
		for (Character character : unitList) {
			character.setdPower(character.defaultPower);
		}
		characterList.clear();
		charsSelected.clear();
		sssCharSelectionPane.setVisible(false);
		ssCharSelectionPane.setVisible(false);
		sCharSelectionPane.setVisible(false);
		bestTeamAnchorPane.setVisible(false);
		changeDPowerScrollPane.setVisible(false);
		backToSelectionButton.setVisible(false);
		changeDPowerButton.setVisible(true);
		submitCharsButton.setVisible(true);
		orderAZButton.setVisible(false);
		powerTextField = new TextField[unitList.size()];
		character = new Character[unitList.size()];
		sPower = new String[unitList.size()];
		deselectAllCheckboxes();
		removeAllChildren(changeDPowerPane);
		removeAllChildren(bestTeamPane);
		toggleRuinsPane("on");
	}

	public void backToSelection() { // button to retun to selection after having pressed to change the dpower
		backToSelectionButton.setVisible(false);
		changeDPowerButton.setVisible(true);
		orderAZButton.setVisible(true);
		changeDPowerScrollPane.setVisible(false);
		
		// making sure to go back to the last selected pane
		if (currentPane != null)
			currentPane.setVisible(true);
		// unless you loaded the chars from a file and there was no lsat pane, then load the SSS one
		else
			sssCharSelectionPane.setVisible(true);

		characterList.clear();
		charsSelected.clear();
		removeAllChildren(changeDPowerPane);
		removeAllChildren(bestTeamPane);
		toggleRuinsPane("on");

		for (CheckBoxImage checkBox : checkboxImageSSS) {
			String charName = checkBox.getTooltipText();
			if (loadedCharsSelected.contains(charName)) {
				checkBox.setSelected(true);
			}
		}
		for (CheckBoxImage checkBox : checkboxImageSS) {
			String charName = checkBox.getTooltipText();
			if (loadedCharsSelected.contains(charName)) {
				checkBox.setSelected(true);
			}
		}
		for (CheckBoxImage checkBox : checkboxImageS) {
			String charName = checkBox.getTooltipText();
			if (loadedCharsSelected.contains(charName)) {
				checkBox.setSelected(true);
			}
		}
	}

	public void paypalButton() { // linking to the paypal donation page

		String paypalLink = "https://www.paypal.com/donate/?hosted_button_id=GZKU898TS7VRS";
		Main mainApp = Main.getInstance();
		if (mainApp.getHostServices() != null) {
			mainApp.getHostServices().showDocument(paypalLink);
		}
	}

	public void orderAZ() { // button to order the checkboxes from A to Z.

		// sort SSS chars alphabetically
		if (currentPane == sssCharSelectionPane) {
			if (!sssToggled) { // only if the button is not toggled already
				ObservableList<Node> checkBoxes = currentPane.getChildren();
				ArrayList<CheckBoxImage> checkboxList = new ArrayList<>();
				originalOrderSSS = new ArrayList<>();

				// Getting a list of the checkboxes in the pane
				for (Node checkbox : checkBoxes) {
					if (checkbox instanceof CheckBoxImage) {
						checkboxList.add((CheckBoxImage) checkbox);
					}
				}

				// save original order.
				originalOrderSSS.addAll(checkboxList);

				// Sort checkboxes based on tooltip text
				checkboxList.sort(Comparator.comparing(CheckBoxImage::getTooltipText));
				// Remove checkboxes from GridLayout
				removeAllChildren(sssCharSelectionPane);

				// Re-add checkboxes in sorted order
				for (int i = 0; i < checkboxImageSSS.length; i++) {
					sssCharSelectionPane.getChildren().add(checkboxList.get(i));
				}
				sssCharSelectionPane.getChildren().add(allSSSButton);
				sssToggled = true;
				updateToggleButtonStyle(); // setting the button background green
			}

			else if (sssToggled) {

				// Remove checkboxes from GridLayout
				removeAllChildren(sssCharSelectionPane);

				// Re-add checkboxes in sorted order
				for (int i = 0; i < checkboxImageSSS.length; i++) {
					sssCharSelectionPane.getChildren().add(originalOrderSSS.get(i));
				}
				sssCharSelectionPane.getChildren().add(allSSSButton);
				sssToggled = false;
				updateToggleButtonStyle(); // setting the button back to default
			}
		}

		// sort chars alphabetically
		if (currentPane == ssCharSelectionPane) {
			if (!ssToggled) { // only if the button is not toggled already

				ObservableList<Node> checkBoxes = currentPane.getChildren();
				ArrayList<CheckBoxImage> checkboxList = new ArrayList<>();
				originalOrderSS = new ArrayList<>();
				
				// Getting a list of the checkboxes in the pane
				for (Node checkbox : checkBoxes) {
					if (checkbox instanceof CheckBoxImage) {
						checkboxList.add((CheckBoxImage) checkbox);
					}
				}

				// save original order.
				originalOrderSS.addAll(checkboxList);

				// Sort checkboxes based on tooltip text
				checkboxList.sort(Comparator.comparing(CheckBoxImage::getTooltipText));
				// Remove checkboxes from GridLayout
				removeAllChildren(ssCharSelectionPane);

				// Re-add checkboxes in sorted order
				for (int i = 0; i < checkboxImageSS.length; i++) {
					ssCharSelectionPane.getChildren().add(checkboxList.get(i));
				}
				ssCharSelectionPane.getChildren().add(allSSButton);
				ssToggled = true;
				updateToggleButtonStyle(); // setting the button background green
			}

			else if (ssToggled) {

				// Remove checkboxes from GridLayout
				removeAllChildren(ssCharSelectionPane);

				// Re-add checkboxes in sorted order
				for (int i = 0; i < checkboxImageSS.length; i++) {
					ssCharSelectionPane.getChildren().add(originalOrderSS.get(i));
				}
				ssCharSelectionPane.getChildren().add(allSSButton);
				ssToggled = false;
				updateToggleButtonStyle(); // setting the button back to default
			}
		}

		// sort chars alphabetically
		if (currentPane == sCharSelectionPane) {
			if (!sToggled) { // only if the button is not toggled already

				ObservableList<Node> checkBoxes = currentPane.getChildren();
				ArrayList<CheckBoxImage> checkboxList = new ArrayList<>();
				originalOrderS = new ArrayList<>();

				// Getting a list of the checkboxes in the pane
				for (Node checkbox : checkBoxes) {
					if (checkbox instanceof CheckBoxImage) {
						checkboxList.add((CheckBoxImage) checkbox);
					}
				}

				// save original order.
				originalOrderS.addAll(checkboxList);

				// Sort checkboxes based on tooltip text
				checkboxList.sort(Comparator.comparing(CheckBoxImage::getTooltipText));
				// Remove checkboxes from GridLayout
				removeAllChildren(sCharSelectionPane);

				// Re-add checkboxes in sorted order
				for (int i = 0; i < checkboxImageS.length; i++) {
					sCharSelectionPane.getChildren().add(checkboxList.get(i));
				}
				sCharSelectionPane.getChildren().add(allSButton);
				sToggled = true;
				updateToggleButtonStyle(); // setting the button background green
			}

			else if (sToggled) {

				// Remove checkboxes from GridLayout
				removeAllChildren(sCharSelectionPane);

				// Re-add checkboxes in sorted order
				for (int i = 0; i < checkboxImageS.length; i++) {
					sCharSelectionPane.getChildren().add(originalOrderS.get(i));
				}
				sCharSelectionPane.getChildren().add(allSButton);
				sToggled = false;
				updateToggleButtonStyle(); // setting the button back to default
			}
		}
	}

	private void updateToggleButtonStyle() { // making sure the button toggle status and color is set correctly while switching panes

		if (sssToggled && currentPane == sssCharSelectionPane && !green) {
			orderAZButton.setSelected(true);
			green = true;
		}

		else if (!sssToggled && currentPane == sssCharSelectionPane && green) {
			orderAZButton.setSelected(false);
			green = false;
		}

		if (ssToggled && currentPane == ssCharSelectionPane && !green) {
			orderAZButton.setSelected(true);
			green = true;
		} else if (!ssToggled && currentPane == ssCharSelectionPane && green) {
			orderAZButton.setSelected(false);
			green = false;
		}

		if (sToggled && currentPane == sCharSelectionPane && !green) {
			orderAZButton.setSelected(true);
			green = true;
		} else if (!sToggled && currentPane == sCharSelectionPane && green) {
			orderAZButton.setSelected(false);
			green = false;
		}
	}

	public void progressBar() { // intermittent progress bar being loaded and bes team calculations being executed at the same time
		CountDownLatch latch = new CountDownLatch(1);

		calculationThread = new Thread(new CalculationRunnable(latch)); // calculations for the best team running on a new thread

		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				calculationThread.start();
				latch.await(); // making sure the calculation finishes before updating the UI
				updateUI();

				return null;
			}
		};
		Thread taskThread = new Thread(task);

		if (characterList.isEmpty()) { //only run calculations if the user has selected chars and reached the changedpower page
			showErrorPopup("Error", "Select your characters and their dPower first!");
		} else {
			bestTeamAnchorPane.setVisible(true);
			changeDPowerScrollPane.setVisible(false);
			bestTeamPane.setVisible(false);
			progressBar.setVisible(true);
			bestTeamPower.setVisible(false);
			taskThread.start();
		}
	}

	private class CalculationRunnable implements Runnable {
		private CountDownLatch latch;

		// setting up countdownlatch to make sure the calculation finishes before updating UI
		public CalculationRunnable(CountDownLatch latch) { 
			this.latch = latch;
		}

		@Override
		public void run() {
			// replacing the default power with whatever the user enters in the textfield
			for (int i = 0; i < charsSelected.size(); i++) {
				character[i].setdPower(Integer.parseInt(powerTextField[i].getText()));
			}
			TeamCalculator.compare(characterList);
			latch.countDown(); 
		}
	}

	public void loadChars() { // loading chars and their dpower from SER file user may have saved.

		CharSelection selection = new CharSelection();
		try {
			selection.loadSelection();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}

		if (!CharSelection.cancelled) {

			charsSelected.clear();
			characterList.clear();
			TeamCalculator.resetBest();
			deselectAllCheckboxes();
			removeAllChildren(changeDPowerPane);

			for (Character character : unitList) {
				character.setdPower(character.defaultPower);
			}
			loadedCharsSelected.addAll(selection.charsSelected);
			charsSelected.addAll(selection.charsSelected);
			loadSelectedChars();
			for (int i = 0; i < charsSelected.size(); i++) {
				sPower[i] = Integer.toString(selection.dPower[i]);
				powerTextField[i].setText(sPower[i]);
				character[i].setdPower(selection.dPower[i]);
			}
		}
	}

	public void saveChars() { // saving chars and their dpower to SER file

		CharSelection selection = new CharSelection();
		int[] saveDPower = new int[charsSelected.size()];

		if (!charsSelected.isEmpty()) {
			for (int i = 0; i < charsSelected.size(); i++) {

				saveDPower[i] = Integer.parseInt(powerTextField[i].getText());
			}

			try {
				selection.saveSelection(charsSelected, saveDPower);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void exit() {
		System.exit(0);
	}
	
	
	//Char comparison feature stars here
	@FXML
	AnchorPane comparisonPane, splitPaneA, splitPaneB;
	@FXML
	SplitPane splitPane;
	@FXML
	Button compareStatsButton, compareSkillsButton;
	@FXML
	ImageView charAIcon = new ImageView();
	@FXML
	ImageView charBIcon = new ImageView();
	@FXML
	TextField charATextField, charBTextField;
	@FXML
	Label charALabelHPnumber, charALabelATKnumber, charALabelDEFnumber, charALabelSPDnumber, 
	charBLabelHPnumber, charBLabelATKnumber, charBLabelDEFnumber, charBLabelSPDnumber,
	charALabelHP, charALabelATK, charALabelDEF, charALabelSPD,
	charBLabelHP, charBLabelATK, charBLabelDEF, charBLabelSPD,
	differenceLabelHPnumber, differenceLabelATKnumber, differenceLabelDEFnumber, differenceLabelSPDnumber;
	@FXML
	VBox charAVBox, charBVBox, resultVBox;
	@FXML
	ListView<String> autocompleteBoxA, autocompleteBoxB;
	
	String choiceA, choiceB, charAName, charBName, searchInput;
	Character charChoiceA;
	Character charChoiceB;
	
	BorderStroke borderStroke = new BorderStroke(
		    Color.SIENNA,                       
		    BorderStrokeStyle.SOLID,           	
		    null,                               
		    new BorderWidths(3)                 
		);
	Border border = new Border(borderStroke);
	
	
	public void charComparisonVisible() {
		comparisonPane.setVisible(true);
		ruinsPane.setVisible(false);
		charAVBox.setBorder(border);
		charBVBox.setBorder(border);	
		resultVBox.setBorder(border);
	}
	
	public void getCharInfo() {
		
		charAName = charATextField.getText().toLowerCase();
		charBName = charBTextField.getText().toLowerCase();

			
		for (int i = 0; i < charNames.size(); i++) {
			if (charAName.equals(charNames.get(i).toLowerCase()))
				choiceA = charNames.get(i);
		}
		for (int i = 0; i < charNames.size(); i++) {
			if (charBName.equals(charNames.get(i).toLowerCase()))
				choiceB = charNames.get(i);
		}
		
		String imagePathA = "resources/" + choiceA + ".png";
		Image imageA = new Image(getClass().getResourceAsStream(imagePathA));
		charAIcon.setImage(imageA);
		
		String imagePathB = "resources/" + choiceB + ".png";
		Image imageB = new Image(getClass().getResourceAsStream(imagePathB));
		charBIcon.setImage(imageB);
		
		charChoiceA = findUnit(choiceA, unitList);
		charChoiceB = findUnit(choiceB, unitList);
		
		// char A stats
		charALabelHPnumber.setText(""+ NumberFormat.getInstance().format(charChoiceA.getHp()));
		charALabelATKnumber.setText(""+ NumberFormat.getInstance().format(charChoiceA.getAtk()));
		charALabelDEFnumber.setText(""+ NumberFormat.getInstance().format(charChoiceA.getDef()));
		charALabelSPDnumber.setText(""+ NumberFormat.getInstance().format(charChoiceA.getSpeed()));
		
		// char B stats
		charBLabelHPnumber.setText(""+ NumberFormat.getInstance().format(charChoiceB.getHp()));
		charBLabelATKnumber.setText(""+ NumberFormat.getInstance().format(charChoiceB.getAtk()));
		charBLabelDEFnumber.setText(""+ NumberFormat.getInstance().format(charChoiceB.getDef()));
		charBLabelSPDnumber.setText(""+ NumberFormat.getInstance().format(charChoiceB.getSpeed()));
		
		compareStats();
	}
	
	public void compareStats() {
		
		String differenceHP = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getHp() - charChoiceB.getHp()));
		String differenceATK = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getAtk() - charChoiceB.getAtk()));
		String differenceDEF = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getDef() - charChoiceB.getDef()));
		String differenceSPD = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getSpeed() - charChoiceB.getSpeed()));
		
		// HP comparison
		if (charChoiceA.getHp() > charChoiceB.getHp()) {
			charALabelHPnumber.getStyleClass().clear();
			charBLabelHPnumber.getStyleClass().clear();
			charALabelHPnumber.getStyleClass().add("green-background");
			charBLabelHPnumber.getStyleClass().add("red-background");
		}
		else if (charChoiceA.getHp() < charChoiceB.getHp()) {
			charALabelHPnumber.getStyleClass().clear();
			charBLabelHPnumber.getStyleClass().clear();
			charALabelHPnumber.getStyleClass().add("red-background");
			charBLabelHPnumber.getStyleClass().add("green-background");
		}
		else {
			charALabelHPnumber.getStyleClass().clear();
			charBLabelHPnumber.getStyleClass().clear();
			charALabelHPnumber.getStyleClass().add("white-background");
			charBLabelHPnumber.getStyleClass().add("white-background");
		}
		
		// ATK comparison
		if (charChoiceA.getAtk() > charChoiceB.getAtk()) {
			charALabelATKnumber.getStyleClass().clear();
			charBLabelATKnumber.getStyleClass().clear();
			charALabelATKnumber.getStyleClass().add("green-background");
			charBLabelATKnumber.getStyleClass().add("red-background");
		}
		else if (charChoiceA.getAtk() < charChoiceB.getAtk()) {
			charALabelATKnumber.getStyleClass().clear();
			charBLabelATKnumber.getStyleClass().clear();
			charALabelATKnumber.getStyleClass().add("red-background");
			charBLabelATKnumber.getStyleClass().add("green-background");
		}
		else {
			charALabelATKnumber.getStyleClass().clear();
			charBLabelATKnumber.getStyleClass().clear();
			charALabelATKnumber.getStyleClass().add("white-background");
			charBLabelATKnumber.getStyleClass().add("white-background");
		}
		
		// DEF comparison
		if (charChoiceA.getDef() > charChoiceB.getDef()) {
			charALabelDEFnumber.getStyleClass().clear();
			charBLabelDEFnumber.getStyleClass().clear();
			charALabelDEFnumber.getStyleClass().add("green-background");
			charBLabelDEFnumber.getStyleClass().add("red-background");
		}
		else if (charChoiceA.getDef() < charChoiceB.getDef()) {
			charALabelDEFnumber.getStyleClass().clear();
			charBLabelDEFnumber.getStyleClass().clear();
			charALabelDEFnumber.getStyleClass().add("red-background");
			charBLabelDEFnumber.getStyleClass().add("green-background");
		}
		else {
			charALabelDEFnumber.getStyleClass().clear();
			charBLabelDEFnumber.getStyleClass().clear();
			charALabelDEFnumber.getStyleClass().add("white-background");
			charBLabelDEFnumber.getStyleClass().add("white-background");
		}
		
		// SPEED comparison
		if (charChoiceA.getSpeed() > charChoiceB.getSpeed()) {
			charALabelSPDnumber.getStyleClass().clear();
			charBLabelSPDnumber.getStyleClass().clear();
			charALabelSPDnumber.getStyleClass().add("green-background");
			charBLabelSPDnumber.getStyleClass().add("red-background");
		}
		else if (charChoiceA.getSpeed() < charChoiceB.getSpeed()) {
			charALabelSPDnumber.getStyleClass().clear();
			charBLabelSPDnumber.getStyleClass().clear();
			charALabelSPDnumber.getStyleClass().add("red-background");
			charBLabelSPDnumber.getStyleClass().add("green-background");
		}
		else {
			charALabelSPDnumber.getStyleClass().clear();
			charBLabelSPDnumber.getStyleClass().clear();
			charALabelSPDnumber.getStyleClass().add("white-background");
			charBLabelSPDnumber.getStyleClass().add("white-background");
		}
		
		differenceLabelHPnumber.getStyleClass().add("yellow-background");
		differenceLabelATKnumber.getStyleClass().add("yellow-background");
		differenceLabelDEFnumber.getStyleClass().add("yellow-background");
		differenceLabelSPDnumber.getStyleClass().add("yellow-background");
		
		differenceLabelHPnumber.setText(differenceHP);
		differenceLabelATKnumber.setText(differenceATK);
		differenceLabelDEFnumber.setText(differenceDEF);
		differenceLabelSPDnumber.setText(differenceSPD);
		
	}
	
	public void charATextFieldAction () {
		
		searchInput = charATextField.getText();
		ObservableList<String> suggestionsList = FXCollections.observableArrayList();			
				
		for (String charName : charNames) {
			if (charName.toLowerCase().startsWith(searchInput)) {
				suggestionsList.add(charName);	
			}
		}		
		autocompleteBoxA.setItems(suggestionsList);
		
		// autocomplete box height
		autocompleteBoxA.setPrefHeight(25 * suggestionsList.size());
		
		charATextField.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.DOWN) {
		        autocompleteBoxA.requestFocus();
		        autocompleteBoxA.getSelectionModel().select(0);
		    }
		});
		
		autocompleteBoxA.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.ENTER) {
		        charATextField.setText(autocompleteBoxA.getSelectionModel().getSelectedItem());
		        autocompleteBoxA.setVisible(false);
		        charATextField.requestFocus();
		        charATextField.positionCaret(charATextField.getText().length());
		    }
		});
		autocompleteBoxA.setOnMousePressed(event -> {			
		        charATextField.setText(autocompleteBoxA.getSelectionModel().getSelectedItem());
		        autocompleteBoxA.setVisible(false);
		        charATextField.requestFocus();
		        charATextField.positionCaret(charATextField.getText().length());		    
		});
		
		if (suggestionsList.isEmpty()) {
	    	autocompleteBoxA.setVisible(false);
	    } else {
	    	autocompleteBoxA.setVisible(true);
	    }
	}
	
	public void charBTextFieldAction () {
		
		searchInput = charBTextField.getText();
		ObservableList<String> suggestionsList = FXCollections.observableArrayList();			
				
		for (String charName : charNames) {
			if (charName.toLowerCase().startsWith(searchInput)) {
				suggestionsList.add(charName);	
			}
		}		
		autocompleteBoxB.setItems(suggestionsList);
		
		// autocomplete box height
		autocompleteBoxB.setPrefHeight(25 * suggestionsList.size()); 
		
		autocompleteBoxB.setOnMousePressed(event -> {			
			charBTextField.setText(autocompleteBoxB.getSelectionModel().getSelectedItem());
	        autocompleteBoxB.setVisible(false);
	        charBTextField.requestFocus();
	        charBTextField.positionCaret(charBTextField.getText().length());		    
		});
		
		charBTextField.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.DOWN) {
		        autocompleteBoxB.requestFocus();
		        autocompleteBoxB.getSelectionModel().select(0);
		    }
		});
		
		autocompleteBoxB.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.ENTER) {
		        charBTextField.setText(autocompleteBoxB.getSelectionModel().getSelectedItem());
		        autocompleteBoxB.setVisible(false);
		        charBTextField.requestFocus();
		        charBTextField.positionCaret(charBTextField.getText().length());
		    }
		});

		if (suggestionsList.isEmpty()) {
	    	autocompleteBoxB.setVisible(false);
	    } else {
	    	autocompleteBoxB.setVisible(true);
	    }
	}		
}