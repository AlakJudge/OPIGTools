package application;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.prefs.Preferences;

import javafx.animation.AnimationTimer;
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
import javafx.scene.control.DatePicker;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
	ScrollPane sssCharSelectionScrollPane, changeDPowerScrollPane;
	@FXML
	Label bestTeamPower, ruinsLabel, bonus;
	@FXML
	Button changeLevelButton, backToSelectionButton, submitCharsButton, changeDPowerButton, allSSSButton, allSSButton, allSButton, sButton,	
			ssButton, sssButton, exitButton, findNextBestTeam, changeAllLevelsButton, changeAlldPowersButton;
	@FXML
	ToggleButton orderAZButton;
	@FXML
	ProgressBar progressBar = new ProgressBar();
	@FXML
	TextField missionPowerTextField, changeAllTextField;
		
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
	boolean ruinsToggle = false;
	boolean savedLevelChanged = false;
	boolean savedDPowerChanged = false;
	boolean converted = false;
	static boolean loadLastFile = false;
	
	TextField[] powerTextField;
	Character character[];
	static int missionPower = 0;
	static boolean changeLevel = false;
	static boolean changeDPower = false;
	static double totalBonus;
	static String[] charLevel;
	
	int lastNumMembers;

	final int MAX_WIDTH = 120;
	final int MAX_HEIGHT = 150;

	// SSS CHACACTER ICONS
	String[] sssImageTooltips = { "Eustass Kid (New World)", "Chopper (Christmas)", "Zoro Asura", "General Franky", "Shirahoshi", "Prime Whitebeard", "Zephyr", "Robin - Christmas", "Yamato", "Mihawk (Summit War)", "God Usopp", "Sanji Germa",
			"Enma Zoro", "Oden", "Nami (Valentine's Day)", "Carrot", "Kaido", "Magellan", "Charlotte Linlin - Lily",
			"Swimsuit - Hancock", "Blackbeard", "Snakeman Luffy", "Golden Lion", "Fujitora", "Shanks", "Rayleigh",
			"Charlotte Linlin", "Kizaru", "Aokiji", "Sengoku", "Whitebeard", "Akainu", "Garp", "Mihawk"};

	String[] sssSelectedImagePaths = { "resources/Eustass Kid (New World)BW.png", "resources/Chopper (Christmas)BW.png", "resources/Zoro AsuraBW.png", "resources/General FrankyBW.png", "resources/ShirahoshiBW.png", "resources/Prime WhitebeardBW.png", "resources/ZephyrBW.png", "resources/Robin - ChristmasBW.png", "resources/YamatoBW.png",
			"resources/Mihawk (Summit War)BW.png", "resources/God UsoppBW.png", "resources/Sanji GermaBW.png",
			"resources/Enma ZoroBW.png", "resources/OdenBW.png", "resources/Nami (Valentine's Day)BW.png",
			"resources/CarrotBW.png", "resources/KaidoBW.png", "resources/MagellanBW.png",
			"resources/Charlotte Linlin - LilyBW.png", "resources/Swimsuit - HancockBW.png",
			"resources/BlackbeardBW.png", "resources/Snakeman LuffyBW.png", "resources/Golden LionBW.png",
			"resources/FujitoraBW.png", "resources/ShanksBW.png", "resources/RayleighBW.png",
			"resources/Charlotte LinlinBW.png", "resources/KizaruBW.png", "resources/AokijiBW.png",
			"resources/SengokuBW.png", "resources/WhitebeardBW.png", "resources/AkainuBW.png", "resources/GarpBW.png",
			"resources/MihawkBW.png"};

	String[] sssImagePaths = { "resources/Eustass Kid (New World).png", "resources/Chopper (Christmas).png", "resources/Zoro Asura.png", "resources/General Franky.png", "resources/Shirahoshi.png", "resources/Prime Whitebeard.png", "resources/Zephyr.png", "resources/Robin - Christmas.png", "resources/Yamato.png",
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
	CheckBoxImage[] checkboxImageSSS = new CheckBoxImage[sssImageTooltips.length];
	CheckBoxImage[] checkboxImageSS = new CheckBoxImage[ssImageTooltips.length];
	CheckBoxImage[] checkboxImageS = new CheckBoxImage[sImageTooltips.length];
	Tooltip tooltipSSS[] = new Tooltip[sssImageTooltips.length];
	Tooltip tooltipSS[] = new Tooltip[ssImageTooltips.length];
	Tooltip tooltipS[] = new Tooltip[sImageTooltips.length];
	Integer[] numMembersNumbers = { 2, 3, 4, 5, 6, 7, 8 };
	Integer[] threePacksBought = { 0, 1, 2, 3};
	Integer[] fivePacksBought = { 0, 1, 2, 3, 4, 5};
	Integer[] sixPacksBought = { 0, 1, 2, 3, 4, 5, 6};
	Integer[] tenPacksBought = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	
	private static final String TOOLTIP = "tooltip";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		prefs = Preferences.userRoot().node(getClass().getName());
		loadState();
		if (secs > 0 || mins > 0 || hrs > 0) {			
			if (!timerStopped) {		
				startTimer();			
			}
		}
        // Register a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveState();
        }));
		
		changeAllTextField.setVisible(false);
		changeAllLevelsButton.setVisible(false);
		changeAlldPowersButton.setVisible(false);
		missionPowerTextField.setVisible(false);
		numMembersChoiceBox.getItems().addAll(numMembersNumbers);
		numMembersChoiceBox.setOnAction(this::setNumMembers);
		numMembersChoiceBox.setValue(7);
		
		// setting up brooks calculator values
		oneThousandGemsPack.getItems().addAll(sixPacksBought);
		oneThousandGemsPack.setValue(0);
		fiveThousandGemsPack.getItems().addAll(fivePacksBought);
		fiveThousandGemsPack.setValue(0);
		money299pack.getItems().addAll(threePacksBought);
		money299pack.setValue(0);
		money999pack.getItems().addAll(fivePacksBought);
		money999pack.setValue(0);
		money2999pack.getItems().addAll(fivePacksBought);
		money2999pack.setValue(0);
		money9999pack.getItems().addAll(tenPacksBought);
		money9999pack.setValue(0);
		oneThousandGemsPack.setValue(0);
		fiveThousandGemsPack.setValue(0);
		money299pack.setValue(0);
		money2999pack.setValue(0);
		money999pack.setValue(0);
		money9999pack.setValue(0);
		
		changeDPowerScrollPane.setVisible(false);
		bestTeamAnchorPane.setVisible(false);
		backToSelectionButton.setVisible(false);
		orderAZButton.setVisible(false);
		comparisonPane.setVisible(false);
		calculatorPane.setVisible(false);
		splitPane.setDividerPosition(0, 0.5);
		SplitPane.setResizableWithParent(splitPane.getItems().get(0), false);
		
		unit = new Character[68];

		// S chars
		unit[0] = new Character("Cavendish", "S", 63, "swordsmaster", "piratesSH", 707375, 46247, 24983, 404, 1);
		unit[1] = new Character("Vista", "S", 63, "swordsmaster", "colonel", 540452, 52669, 11659, 401, 1);
		unit[2] = new Character("Buggy", "S", 63, "SHparamecia", "will", "thrillerBark", "paramecia", "captain", 469621, 33950,
				16239, 370, 1);
		unit[3] = new Character("Jozu", "S", 63, "will", "paramecia", "thrillerBark", "SHparamecia", 540452, 52669,
				11659, 390, 1);
		unit[4] = new Character("Smoker", "S", 63, "will", "paramecia", "thrillerBark", "colonel", 769621, 33950,
				16239, 400, 1);
		unit[5] = new Character("Lucci", "S", 63, "will", "vinsmoke", 540452, 52669, 11659, 400, 1);
		unit[6] = new Character("Crocodile", "S", 63, "will", "paramecia", "vinsmoke", "shichibukaiVinsmoke", 707375,
				46247, 24983, 398, 1);
		unit[7] = new Character("Franky (Supernova)", "S", 63, "will", "piratesRevolutionaries", "cyborg", 757904, 36500, 13324,
				390, 1);
		unit[8] = new Character("Vinsmoke Reiju", "S", 63, "will", "piratesPursuit", "vinsmoke", 540452, 52669, 11659,
				405, 1);
		unit[9] = new Character("Jimbei", "S", 63, "piratesPursuit", "SHsupernova", 540452, 52669, 11659, 380, 1);
		unit[10] = new Character("Moria", "S", 63, "paramecia", "thrillerBark", "captain", 540846, 33586, 35532, 382, 1);
		unit[11] = new Character("Hawkins", "S", 63, "paramecia", "thrillerBark", 540452, 52669, 11659, 408, 1);
		unit[12] = new Character("Kid", "S", 63, "paramecia", "thrillerBark", "breakthrough", 540452, 52669, 11659,
				408, 1);
		unit[13] = new Character("Nami (Supernova)", "S", 63, "SHparamecia", "breakthrough", 707375, 46247, 24983, 380, 1);
		unit[14] = new Character("Ivankov", "S", 63, "piratesRevolutionaries", 707375, 46247, 24983, 380, 1);
		unit[15] = new Character("Chopper (Supernova)", "S", 63, "SHsupernova", 707375, 46247, 24983, 380, 1);
		unit[16] = new Character("Robin (Supernova)", "S", 63, "SHsupernova", 707375, 46247, 24983, 406, 1);
		unit[17] = new Character("Luffy (Supernova)", "S", 63, "swordsmaster", "SHsupernova", "captain", 757904, 36500, 13324,
				400, 1);
		unit[18] = new Character("Usopp (Supernova)", "S", 63, "swordsmaster", 540452, 52669, 11659, 398, 1);	
		unit[19] = new Character("Bartolomeo", "S", 63, "swordsmaster", "piratesSH", 707375, 46247, 24983, 406, 1);
		
		// SS chars
		unit[20] = new Character("Ace", "SS", 105, "swordsmaster", 1091668, 112980, 11659, 407, 1);
		unit[21] = new Character("Doflamingo", "SS", 105, "swordsmaster", "will", "shichibukaiVinsmoke", "captain", 1091668,
				112980, 11659, 415, 1);
		unit[22] = new Character("Sabo", "SS", 105, "swordsmaster", "piratesSH", 1091668, 112980, 11659, 417, 1);
		unit[23] = new Character("Enel", "SS", 105, "swordsmaster", "skypiea", 1617146, 105743, 24983, 425, 1);
		unit[24] = new Character("Katakuri", "SS", 105, "SHparamecia", "vinsmoke", "will", 1617216, 105715, 25097, 409, 1);
		unit[25] = new Character("Kuma", "SS", 105, "will", "marineShichibukai", 1413372, 70888, 12491, 419, 1);
		unit[26] = new Character("Marco", "SS", 105, "will", "paramecia", "thrillerBark", "colonel", 1491760, 70888,
				17322, 410, 1);
		unit[27] = new Character("Boa Hancock", "SS", 105, "will", "shichibukaiVinsmoke", "shichibukai", "captain", 1130454, 68517,
				31090, 397, 1);
		unit[28] = new Character("Nightmare Luffy", "SS", 105, "will", "captain", 1617146, 105743, 24983, 366, 1);
		unit[29] = new Character("Law (Supernova)", "SS", 105, "captain", 1617216, 105715, 25097, 400, 1);
		unit[30] = new Character("Vinsmoke Ichiji", "SS", 105, "will", "vinsmoke", "shichibukaiVinsmoke", 862119,
				112957, 11712, 415, 1);
		unit[31] = new Character("Law", "SS", 105, "marines", "shichibukai", "captain", 1617146, 105743, 24983, 400, 1);
		unit[32] = new Character("Sanji (Supernova)", "SS", 105, "marines", "swordsmaster", "piratesSH", 1617146,
				105743, 24983, 441, 1);
		unit[33] = new Character("Zoro (Supernova)", "SS", 105, "swordsmaster", "SHsupernova", 1091668, 112980, 11659,
				415, 1);
		
		// SSS chars
		unit[34] = new Character("Aokiji", "SSS", 157, "marines", "swordsmaster", "piratesSH", 2567159, 167898, 24983,
				445, 1);
		unit[35] = new Character("Mihawk", "SSS", 157, "marines", "swordsmaster", "will", 1724031, 180936, 11659, 395, 1);
		unit[36] = new Character("Golden Lion", "SSS", 157, "wayOfFreedom", 1724108, 180902, 11712, 410, 1);
		unit[37] = new Character("Fujitora", "SSS", 157, "wayOfFreedom", 1724108, 180902, 11712, 425, 1);
		unit[38] = new Character("Kizaru", "SSS", 157, "wayOfFreedom", 1159997, 146539, 10553, 425, 1);
		unit[39] = new Character("Snakeman Luffy", "SSS", 157, "swordsmaster", "colonel", "captain", 2425897, 98896, 13385, 421, 1);
		unit[40] = new Character("Shanks", "SSS", 157, "swordsmaster", "will", "captain", 1159997, 146539, 10553, 414, 1);
		unit[41] = new Character("Akainu", "SSS", 157, "swordsmaster", 2425907, 98913, 13324, 409, 1);
		unit[42] = new Character("Sengoku", "SSS", 157, "will", "marineShichibukai", 2635858, 108099, 16239, 415, 1);
		unit[43] = new Character("Garp", "SSS", 157, "will", "marineShichibukai", 2248187, 112009, 12491, 419, 1);
		unit[44] = new Character("Whitebeard", "SSS", 157, "piratesPursuit", "captain", 1724031, 180936, 11659, 421, 1);
		unit[45] = new Character("Rayleigh", "SSS", 157, "yonko", 1359739, 180902, 11712, 415, 1);
		unit[46] = new Character("Kaido", "SSS", 157, "yonko", "captain", 2635841, 93116, 16313, 415, 1);
		unit[47] = new Character("Oden", "SSS", 157, "breakthrough", 1724108, 180902, 11712, 419, 1);
		unit[48] = new Character("Swimsuit - Hancock", "SSS", 157, "cyborg", 2567264, 167852, 25097, 416, 1);
		unit[49] = new Character("Robin - Christmas", "SSS", 157, "SHparamecia", 3908232, 134164, 25097, 420, 1);
		unit[50] = new Character("Blackbeard", "SSS", 157, "shichibukaiVinsmoke", "captain", 2567264, 167898, 25097, 425, 1);
		unit[51] = new Character("Nami (Valentine's Day)", "SSS", 157, "piratesSH", 2567264, 167852, 25097, 423, 1);
		unit[52] = new Character("Yamato", "SSS", 157, "will", 1882743, 180936, 12548, 419, 1);
		unit[53] = new Character("God Usopp", "SSS", 157, "breakthrough", 1563294, 103564, 25097, 408, 1);
		unit[54] = new Character("Sanji Germa", "SSS", 157, "breakthrough", 2390912, 127959, 11712, 409, 1);
		unit[55] = new Character("Enma Zoro", "SSS", 157, "breakthrough", 1359739, 180936, 11712, 418, 1);
		unit[56] = new Character("Carrot", "SSS", 157, "cyborg", 2425897, 98896, 13385, 417, 1);
		unit[57] = new Character("Charlotte Linlin - Lily", "SSS", 157, "marineShichibukai", 1359739, 180936, 11712, 418, 1);
		unit[58] = new Character("Charlotte Linlin", "SSS", 157, "yonko", "captain", 2567159, 167898, 24983, 410, 1);
		unit[59] = new Character("Mihawk (Summit War)", "SSS", 157, "shichibukai", 1922920, 127959, 11712, 412, 1);
		unit[60] = new Character("Magellan", "SSS", 157, "paramecia", 2635841, 108082, 16313, 420, 1);
		unit[61] = new Character("Prime Whitebeard", "SSS", 157, "paramecia", "captain", "yonko", 1901456, 158585, 12139, 415, 1);
		unit[62] = new Character("Zephyr", "SSS", 157, "marines", "marineShichibukai", 2345424, 125630, 12548, 425, 1);
		unit[63] = new Character("Shirahoshi", "SSS", 157, "will", 3225503, 152923, 25097, 423, 1);
		unit[64] = new Character("General Franky", "SSS", 157, "will", "piratesSH", "cyborg", 3226319, 98408, 30064, 424, 1);
		unit[65] = new Character("Zoro Asura", "SSS", 157, "swordsmaster", "breakthrough", 1131856, 123449, 25045, 415, 1);
		unit[66] = new Character("Chopper (Christmas)", "SSS", 157, "piratesSH", "breakthrough", 2857454, 158808, 38822, 415, 1);
		unit[67] = new Character("Eustass Kid (New World)", "SSS", 157, "paramecia", "breakthrough", 2669814, 143069, 33114, 415, 1);
				
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
		calculatorPane.setVisible(false);
	}

	public void setNumMembers(ActionEvent e) { // get the vale from the choice box and make sure it's set to the correct variable when needed
		Main.numMembers = numMembersChoiceBox.getValue();
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
		
		charsSelected.clear();
		removeAllChildren(changeDPowerPane);
		removeAllChildren(bestTeamPane);
		
		changeDPower=true;
		changeLevel=false;
		
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
			changeAllTextField.clear();
			changeAllTextField.setVisible(true);
			changeAlldPowersButton.setVisible(true);	
			characterList.clear();
			convertCharsSelected(charsSelected);
			loadSelectedChars(); 
			missionPowerTextField.setVisible(true);
		}
	}
	
	public void changeLevel() { // method to run after selecting the characters and choosing to change the level
		
		charsSelected.clear();
		removeAllChildren(changeDPowerPane);
		removeAllChildren(bestTeamPane);
	
		changeLevel=true;
		changeDPower=false;
		
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
			changeAllTextField.clear();
			changeAllTextField.setVisible(true);
			changeAllLevelsButton.setVisible(true);
			characterList.clear();
			convertCharsSelected(charsSelected);
			loadSelectedChars(); 
			missionPowerTextField.setVisible(true);
		}
	}

	public void loadSelectedChars() { // loading page with the selected characters and their powers that can be changed

		toggleRuinsPane("off");
		changeDPowerScrollPane.setVisible(true);
		missionPowerTextField.setVisible(true);
		sssPaneInvisible();
		ssPaneInvisible();
		sPaneInvisible();

		changeDPowerButton.setVisible(false);
		changeLevelButton.setVisible(false);
		backToSelectionButton.setVisible(true);

		int numElements = charsSelected.size();
		String name[] = new String[numElements];
		String defaultPower[] = new String[numElements];
		String currentLevel[] = new String[numElements];
		String currentPower[] = new String[numElements];
		Image icon[] = new Image[numElements];
		ImageView iconView[] = new ImageView[numElements];
		VBox vbox[] = new VBox[numElements];
			
		// loop creating each imagecheckbox and the textfield under it with the power
		for (int i = 0; i < numElements; i++) { 

			vbox[i] = new VBox();
			powerTextField[i] = new TextField();
			powerTextField[i].setPrefSize(MAX_WIDTH, 10);
			name[i] = charsSelected.get(i);
			character[i] = findUnit(name[i], unitList); // finding the character
			defaultPower[i] = Integer.toString(character[i].getDefaultPower()); // getting default power
			currentLevel[i] = Integer.toString(character[i].getLevel()); // getting default level
			currentPower[i] = Integer.toString(character[i].getdPower()); // getting default power
			
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
			
			// Set default level if not loading levels from file
			if (changeLevel) {
				if (savedLevelChanged) {
					powerTextField[i].setText(currentLevel[i]);
				}
				else {
					powerTextField[i].setText("1");
				}
			} 
			
			// Set default dpower if not loading dpower from file
			else if (changeDPower) {
				if (savedDPowerChanged) {
					powerTextField[i].setText(currentPower[i]);
				}
				else {
					powerTextField[i].setText(defaultPower[i]);
				}
			}
			
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
			String finalCharPower[] = new String[numMembers];
			Image icon[] = new Image[numMembers];
			ImageView iconView[] = new ImageView[numMembers];
			Label powerLabel[] = new Label[numMembers];
			VBox vbox[] = new VBox[numMembers];

			for (int i = 0; i < numMembers; i++) {

				// getting the name and power of the chars in the best team
				if (changeLevel || (savedLevelChanged && !changeDPower)) {
					finalCharPower[i] = Integer.toString(TeamCalculator.bestTeam[0][i].getLevel());
					
				}
				else {
					finalCharPower[i] = Integer.toString(TeamCalculator.bestTeam[0][i].dPower);
				}		
				
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
				powerLabel[i].setText(finalCharPower[i]);
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
			
			if (missionPower != 0 && TeamCalculator.highestPower < missionPower) {
				showWarningPopup("Warning", "Your best team does not have enough power for this mission :(");
			}
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
			sButton.setVisible(false);
			ssButton.setVisible(false);
			sssButton.setVisible(false);
			orderAZButton.setVisible(false);
			ruinsToggle = false;
		} else if (status.equals("on")) {
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
		missionPowerTextField.setVisible(false);
		backToSelectionButton.setVisible(false);
		changeDPowerButton.setVisible(true);
		changeLevelButton.setVisible(true);
		submitCharsButton.setVisible(true);
		orderAZButton.setVisible(false);
		converted = false;
		changeDPower = false;
		changeLevel = false;
		savedLevelChanged = false;
		savedDPowerChanged = false;
		powerTextField = new TextField[unitList.size()];
		character = new Character[unitList.size()];
		missionPowerTextField.clear();
		deselectAllCheckboxes();
		removeAllChildren(changeDPowerPane);
		removeAllChildren(bestTeamPane);
		toggleRuinsPane("on");
	}

	public void backToSelection() { // button to retun to selection after having pressed to change the dpower
		backToSelectionButton.setVisible(false);
		changeDPowerButton.setVisible(true);
		changeLevelButton.setVisible(true);
		orderAZButton.setVisible(true);
		changeDPowerScrollPane.setVisible(false);
		missionPowerTextField.setVisible(false);
		changeAllTextField.setVisible(false);
		changeAllLevelsButton.setVisible(false);
		changeAlldPowersButton.setVisible(false);
		toggleRuinsPane("on");
		
		// making sure to go back to the last selected pane
		if (currentPane != null) {		
			if (currentPane == sssCharSelectionPane) {
				sssCharSelectionPane.setVisible(true);
				sssCharSelectionScrollPane.setVisible(true);
			}
			else
				currentPane.setVisible(true);
		}
		// unless you loaded the chars from a file and there was no last pane, then load the SSS one
		else 
			sssPaneVisible();
		
				
		for (CheckBoxImage checkBox : checkboxImageSSS) {
			String charName = checkBox.getTooltipText();
			if (charsSelected.contains(charName)) {
				checkBox.setSelected(true);
			}
		}
		for (CheckBoxImage checkBox : checkboxImageSS) {
			String charName = checkBox.getTooltipText();
			if (charsSelected.contains(charName)) {
				checkBox.setSelected(true);
			}
		}
		for (CheckBoxImage checkBox : checkboxImageS) {
			String charName = checkBox.getTooltipText();
			if (charsSelected.contains(charName)) {
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

	public void progressBar() { // progress bar being loaded and best team calculations being executed at the same time
		
		lastNumMembers = Main.numMembers;
		charLevel = new String[charsSelected.size()];
		
		if (!missionPowerTextField.getText().isEmpty()) {
			try {
			    missionPower = Integer.parseInt(missionPowerTextField.getText());
			} catch (NumberFormatException e) {
				showErrorPopup("Error", "Minimum Mission DPower can only contain numbers!");
			}
		}
			
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

		if (characterList.isEmpty() || (!changeDPower && !changeLevel)) { //only run calculations if the user has selected chars and reached the changedpower page
			showErrorPopup("Error", "Select your characters and their level/dPower first!");
		} else {
			changeAllTextField.clear();
			changeAllTextField.setVisible(false);
			changeAllLevelsButton.setVisible(false);
			changeAlldPowersButton.setVisible(false);
			changeDPowerScrollPane.setVisible(false);
			bestTeamPane.setVisible(false);			
			bestTeamPower.setVisible(false);
			progressBar.setVisible(true);
			bestTeamAnchorPane.setVisible(true);

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
				
				if (changeLevel || (savedLevelChanged && !changeDPower)) {
					
					float tempPower =  Float.parseFloat(powerTextField[i].getText());
					charLevel[i] = powerTextField[i].getText();
					character[i].setLevel(Integer.parseInt(charLevel[i])); // set character levels
					
					if (tempPower == 1) {
						character[i].setdPower(character[i].getDefaultPower());		
					}
					else {
						if (character[i].getType().equals("SSS")) {
							
							tempPower = (float) Math.ceil((tempPower * 7.5 - 7.5) + 157);
							character[i].setdPower((int)tempPower);
							 
						}
						else if (character[i].getType().equals("SS")) {
							character[i].setdPower(((int)tempPower * 5 - 5) + 105);
						}
						else if (character[i].getType().equals("S")) {
							character[i].setdPower(((int)tempPower * 3 - 3) + 63);
						}
					}
				}
				else {
					character[i].setdPower(Integer.parseInt(powerTextField[i].getText()));
				}				
			}			
			TeamCalculator.compare(characterList);
			latch.countDown(); 
		}		
	}
	
	public void findNextBestTeam() {
		List<Character> charsToRemove = new ArrayList<>();
		
		//Main.numMembers = numMembersChoiceBox.getValue();
		for (int i = 0; i < characterList.size(); i++) {
			for (int j = 0; j < lastNumMembers; j++) {		
				if (characterList.get(i).getName().equals(TeamCalculator.bestTeam[0][j].getName())) {
					charsToRemove.add(characterList.get(i));
				}
			}	
		}	
		characterList.removeAll(charsToRemove);
		if (characterList.size() > Main.numMembers) {
			TeamCalculator.resetBest();
			removeAllChildren(bestTeamPane);
			progressBar();
		} 
		else {
			showErrorPopup("Error", "Not enough characters to compare!");
		}
	}

	public void loadLastFile() {
		 
		loadLastFile = true;
		loadChars(); 
	}
	
	public void loadChars() { // loading chars and their dpower from SER file user may have saved.
		
		CharSelection selection = new CharSelection();
		try {
			selection.loadSelection();
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
		
		if (!CharSelection.cancelled) {
			
			// If the char levels were saved, toggle that, or toggle the dpower instead
			// Also toggle the changelevel or dpower buttons if each perspective one was saved
			if (selection.levelSaved == 1) {
				savedLevelChanged = true;
				savedDPowerChanged = false;
				changeLevel = true;
				changeDPower = false;
			}
			else {
				savedLevelChanged = false;
				savedDPowerChanged = true;
				changeLevel = false;
				changeDPower = true;
			}

			charsSelected.clear();
			characterList.clear();
			TeamCalculator.resetBest();
			deselectAllCheckboxes();
			removeAllChildren(changeDPowerPane);

			for (Character character : unitList) {
				character.setdPower(character.defaultPower);
			}
			
			charsSelected.addAll(selection.charsSelected);			
			convertCharsSelected(charsSelected);
			converted = true;
									
			for (int i = 0; i < charsSelected.size(); i++) {
				if (savedLevelChanged) 
					characterList.get(i).setLevel(selection.savedInput[i]);
				else if (savedDPowerChanged) 
					characterList.get(i).setdPower(selection.savedInput[i]);
			}
			loadLastFile = false;
			loadSelectedChars();
		}
	}

	public void saveChars() { // saving chars and their dpower to SER file
		
		int levelSaved;
		int powerSaved;
		if (changeLevel)
			levelSaved = 1;
		else
			levelSaved = 0;
		
		if (changeDPower)
			powerSaved = 1;
		else
			powerSaved = 0;
		
		CharSelection selection = new CharSelection();
		int[] savedInput = new int[charsSelected.size()];

		if (!charsSelected.isEmpty()) {
			for (int i = 0; i < charsSelected.size(); i++) {
					savedInput[i] = Integer.parseInt(powerTextField[i].getText());
			}

			try {
				selection.saveSelection(charsSelected, savedInput, levelSaved, powerSaved);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void changeAllLevels() {
		
		String changeAllTarget = changeAllTextField.getText();
		
		for (int i = 0; i < charsSelected.size(); i++) {
			powerTextField[i].setText(changeAllTarget);
		}
	}
	
	public void changeAlldPowers() {
		
		String changeAllTarget = changeAllTextField.getText();
		
		for (int i = 0; i < charsSelected.size(); i++) {
			powerTextField[i].setText(changeAllTarget);
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
	@FXML
	GridPane skillsGridPaneA, skillsGridPaneB;
	@FXML
	StackPane differenceStackPane;
	
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
	
	final int SKILL_MAX_HEIGHT = 110;
	final int SKILL_MAX_WIDTH= 130;
	
	
	String[] sssSkillIcons = { "Prime Whitebeard skill 1", "Prime Whitebeard skill 2", "Prime Whitebeard skill 3", "Prime Whitebeard skill 4", "Zephyr skill 1", "Zephyr skill 2",
			"Zephyr skill 3", "Zephyr skill 4", "Robin - Christmas skill 1", "Robin - Christmas skill 2", "Robin - Christmas skill 3", "Robin - Christmas skill 4", 
			"Yamato", "Mihawk (Summit War)", "God Usopp", "Sanji Germa",
			"Enma Zoro", "Oden", "Nami (Valentine's Day)", "Carrot", "Kaido", "Magellan", "Charlotte Linlin - Lily",
			"Swimsuit - Hancock", "Blackbeard", "Snakeman Luffy", "Golden Lion", "Fujitora", "Shanks", "Rayleigh",
			"Charlotte Linlin", "Kizaru", "Aokiji", "Sengoku", "Whitebeard", "Akainu", "Garp", "Mihawk"};
	
	String[] sssSkillIconsPaths = { "resources/skills/Prime Whitebeard skill 1.png", "resources/skills/Prime Whitebeard skill 2", "resources/skills/Prime Whitebeard skill 3", 
			"resources/skills/Prime Whitebeard skill 4", "resources/skills/Zephyr skill 1", "resources/skills/Zephyr skill 2", "resources/skills/Zephyr skill 3", 
			"resources/skills/Zephyr skill 4", "resources/skills/Robin - Christmas skill 1", "resources/skills/Robin - Christmas skill 2", "resources/skills/Robin - Christmas skill 3", 
			"resources/skills/Robin - Christmas skill 4", "Yamato", "Mihawk (Summit War)", "God Usopp", "Sanji Germa",
			"Enma Zoro", "Oden", "Nami (Valentine's Day)", "Carrot", "Kaido", "Magellan", "Charlotte Linlin - Lily",
			"Swimsuit - Hancock", "Blackbeard", "Snakeman Luffy", "Golden Lion", "Fujitora", "Shanks", "Rayleigh",
			"Charlotte Linlin", "Kizaru", "Aokiji", "Sengoku", "Whitebeard", "Akainu", "Garp", "Mihawk"};
	
	public void setCharSkills() {
		// Prime Whitebeard
		Skills murakumogiri = new Skills();
		murakumogiri.setAtk(275);
		murakumogiri.setMiscEffect1("If \"Heal Reduction\" is applied, deal " + 315 +" dmg instead");
		Skills theStrongestMan = new Skills();
		theStrongestMan.setBreakRate(30);
		theStrongestMan.setMiscEffect1("Immune to debuffs while above 70% HP from the beginning of the turn until you attack");
		Skills earthManipulation = new Skills();
		earthManipulation.setCritRate(20);
		earthManipulation.setSpd(-15);
		earthManipulation.setDmgToTarget(50);
		Skills vibrationWave = new Skills();
		vibrationWave.setHealProhibit(true);	
		
		for (int i=0; i<unit.length;i++) {
			if (unit[i].getName().equals("Prime Whitebeard")) {
				unit[i].setSkill1("Total of " + NumberFormat.getNumberInstance().format(Math.abs(unit[i].getAtk()*(murakumogiri.getAtk()/100))) + " single target damage\n" + murakumogiri.getMiscEffect1());
				unit[i].setSkill2("+" + theStrongestMan.getBreakRate() + "% Break Rate\n" + theStrongestMan.getMiscEffect1());
				unit[i].setSkill3("+" + earthManipulation.getCritRate() + "% Crit Rate\n" + earthManipulation.getSpd() + "Speed\n" + "+" + earthManipulation.getDmgToTarget()
				 + "% Damage to the target");
				unit[i].setSkill4("Applies Heal Prohibit");
			}
			if (unit[i].getName().equals("Zephyr")) {
				unit[i].setSkill1("Total of " + unit[i].getAtk()*(murakumogiri.getAtk()/100) + " damage\n" + "");
				unit[i].setSkill2("");
				unit[i].setSkill3("");
				unit[i].setSkill4("");
			}
			if (unit[i].getName().equals("Robin - Christmas")) {
				unit[i].setSkill1("Total of " + unit[i].getAtk()*(murakumogiri.getAtk()/100) + " damage");
				unit[i].setSkill2("");
				unit[i].setSkill3("");
				unit[i].setSkill4("");
			}
		}
	}
	
	public void charComparisonVisible() {
		comparisonPane.setVisible(true);
		ruinsPane.setVisible(false);
		calculatorPane.setVisible(false);
		charAVBox.setBorder(border);
		charBVBox.setBorder(border);	
		resultVBox.setBorder(border);
	}
	
	public void getCharInfo() {
		
		differenceStackPane.setVisible(true);
		charAVBox.setVisible(true);
		charBVBox.setVisible(true);
		skillsGridPaneA.setVisible(false);
		skillsGridPaneB.setVisible(false);
		
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
	    	autocompleteBoxA.toFront();
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
	    	autocompleteBoxB.toFront();
	    }
	}		

	public void getSkilsInfo () {
		
		differenceStackPane.setVisible(false);
		charAVBox.setVisible(false);
		charBVBox.setVisible(false);
		skillsGridPaneA.setVisible(true);
		skillsGridPaneB.setVisible(true);

		setCharSkills();
		
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
		
		charChoiceA = findUnit(choiceA, unitList);
		charChoiceB = findUnit(choiceB, unitList);
		
		String imagePathA = "resources/" + choiceA + ".png";
		Image imageA = new Image(getClass().getResourceAsStream(imagePathA));
		charAIcon.setImage(imageA);
		
		String imagePathB = "resources/" + choiceB + ".png";
		Image imageB = new Image(getClass().getResourceAsStream(imagePathB));
		charBIcon.setImage(imageB);
		
		Image[] skillA = new Image[4];
		ImageView[] skillIconA = new ImageView[4];
		Image[] skillB = new Image[4];
		ImageView[] skillIconB = new ImageView[4];
		
		for (int i = 0; i < 4; i++) {
			final int index = i;
			
			skillA[i] = new Image(getClass().getResourceAsStream("resources/skills/" + choiceA + " skill " + (i + 1) + ".png"));
			skillIconA[i] = new ImageView(skillA[i]);
			skillIconA[i].setFitHeight(SKILL_MAX_HEIGHT);
			skillIconA[i].setFitWidth(SKILL_MAX_WIDTH);
			
			skillB[i] = new Image(getClass().getResourceAsStream("resources/skills/" + choiceB + " skill " + (i + 1) + ".png"));
			skillIconB[i] = new ImageView(skillB[i]);
			skillIconB[i].setFitHeight(SKILL_MAX_HEIGHT);
			skillIconB[i].setFitWidth(SKILL_MAX_WIDTH);			
			
			skillsGridPaneA.add(skillIconA[i], 2, i);			
			skillsGridPaneB.add(skillIconB[i], 0, i);
			
			zoomSkillBoxes(skillIconA[index], skillIconA[index], skillIconB[index], index);
			zoomSkillBoxes(skillIconB[index], skillIconA[index], skillIconB[index], index);
			
		}
		
		// 2nd column. Skill numbers
		Label skill1TextA = new Label(charChoiceA.getSkill1()); 
		Label skill2TextA = new Label(charChoiceA.getSkill2()); 
		Label skill3TextA = new Label(charChoiceA.getSkill3()); 
		Label skill4TextA = new Label(charChoiceA.getSkill4()); 
		skillsGridPaneA.add(skill1TextA, 1, 0);
		skillsGridPaneA.add(skill2TextA, 1, 1);
		skillsGridPaneA.add(skill3TextA, 1, 2);
		skillsGridPaneA.add(skill4TextA, 1, 3);
		
		Label skill1TextB = new Label(charChoiceB.getSkill1()); 
		Label skill2TextB = new Label(charChoiceB.getSkill2()); 
		Label skill3TextB = new Label(charChoiceB.getSkill3()); 
		Label skill4TextB = new Label(charChoiceB.getSkill4()); 
		skillsGridPaneB.add(skill1TextB, 1, 0);
		skillsGridPaneB.add(skill2TextB, 1, 1);
		skillsGridPaneB.add(skill3TextB, 1, 2);
		skillsGridPaneB.add(skill4TextB, 1, 3);
				
	}
	public void zoomSkillBoxes(Node hovered, Node boxA, Node boxB, int index) {
		hovered.setOnMouseEntered(event -> {
			boxA.setScaleX(2.0);   
			boxA.setScaleY(2.0);   
			boxA.toFront();
			boxB.setScaleX(2.0);   
			boxB.setScaleY(2.0);   
			boxB.toFront();
			if (index == 0) {
				boxA.setTranslateX(-60);
				boxA.setTranslateY(45);
				boxB.setTranslateX(50);
				boxB.setTranslateY(45);
			}
			else if (index == 3) {
				boxA.setTranslateX(-60);
				boxA.setTranslateY(-60);
				boxB.setTranslateX(50);
				boxB.setTranslateY(-50);
			}
			else
				boxA.setTranslateX(-60);
				boxB.setTranslateX(50);
		});			
		hovered.setOnMouseExited(event -> {
			boxA.setScaleX(1.0);   
			boxA.setScaleY(1.0);
			boxA.setTranslateX(0);
			boxA.setTranslateY(0);
			boxB.setScaleX(1.0);   
			boxB.setScaleY(1.0);
			boxB.setTranslateX(0);
			boxB.setTranslateY(0);
		});
	}

	// Event Calculators starts here
	@FXML 
	AnchorPane calculatorPane;
	@FXML
	Button eventCalcButton, brooksCalcButton, setSailCalcButton, resetBrooksButton, calculateBrooksButton, startTimer, stopTimer;
	@FXML
	DatePicker brooksEndDate;
	@FXML
	CheckBox dailiesDoneCheckbox;
	@FXML
	TextField currentGemsTextfield, currentWinesTextfield, extraInstancesTextfield, wineGoalTextfield,
	textfield1k, textfield5k, textfield299, textfield999, textfield2999, textfield9999;
	@FXML
	ChoiceBox<Integer> oneThousandGemsPack, fiveThousandGemsPack, money299pack, money2999pack, money999pack, money9999pack;
	@FXML
	Label idlingWinesLabel, dailiesWinesLabel, totalWinesLabel, timerDisplay, remainingTimerDisplay, availableWines;
	
	int totalWines, totalIdlingWines, totalDailiesWines, totalGemsWines, totalPacksWines, currentWines, currentGems, wineGoal;
	int extraInstanceChances = 0;
	int sssCost = 10000;
	int pack1k = 300;
	int pack5k = 1200;
	int pack299 = 200;
	int pack999 = 360;
	int pack2999 = 1080;
	int pack9999 = 4000;
	
	Boolean timerStopped = true;
	
	public void eventCalculatorsVisible() {
		calculatorPane.setVisible(true);
		comparisonPane.setVisible(false);
		ruinsPane.setVisible(false);
	}
	
	public void calculateBrooks() {
		
		int instanceChances = 12;
		int winesPerInstance = 3;
		
		LocalDate today = LocalDate.now();
		LocalDateTime endOfDay = LocalDateTime.of(today, LocalTime.MAX);
		LocalDateTime timeNow = LocalDateTime.now();
		
		// Specify the timezone ID for China
        String chinaTimeZoneId = "Asia/Bangkok";
        // Get the current time in the specified timezone
        LocalDateTime chinaTime = LocalDateTime.now(ZoneId.of(chinaTimeZoneId));
		// Going to add the minutes of timezone difference into the calculation. To get Server time.
        long timeZoneMinutesDifference = ChronoUnit.MINUTES.between(timeNow, chinaTime);

        // Calculate the difference in days
        long daysUntilTarget = ChronoUnit.DAYS.between(today, brooksEndDate.getValue());
        // Calculate the minutes until the end of today
        long minutesUntilMidnight = (ChronoUnit.SECONDS.between(LocalDateTime.now(), endOfDay)) / 60;       
        // calcaulate minutes left until the end of Brooks
        int minutesOfBrooksLeft = (int) (daysUntilTarget * 24 * 60) + (int) minutesUntilMidnight + (int) timeZoneMinutesDifference;        
        // total of wines possible to get until the end of the event + 10% adjustment as some may be missed
        totalIdlingWines = (int) ((minutesOfBrooksLeft / 6) * 0.9);
        
        // calculating the the total of wines you can get from daily instances
        
        if (dailiesDoneCheckbox.isSelected()) {
        	extraInstanceChances = Integer.parseInt(extraInstancesTextfield.getText());
        	// instance chances, plus any extra, times the winer per instance. Then times that by the numbers of day left for the event
        	totalDailiesWines = ((instanceChances + extraInstanceChances) * winesPerInstance) * ((int) daysUntilTarget);
        }
        else {
        	extraInstanceChances = Integer.parseInt(extraInstancesTextfield.getText());
        	totalDailiesWines = ((instanceChances + extraInstanceChances) * winesPerInstance) * ((int) daysUntilTarget + 1);;
        }        	
        
        // Get the current gems and wines 
        currentWines = Integer.parseInt(currentWinesTextfield.getText());
        currentGems = Integer.parseInt(currentGemsTextfield.getText());
        
        // Get the total amount of wines you get per gem and $$ pack you bought
        totalGemsWines = (oneThousandGemsPack.getValue() * pack1k) + (fiveThousandGemsPack.getValue() * pack5k);
        totalPacksWines = 
        		(money299pack.getValue() * pack299) + 
        		(money999pack.getValue() * pack999) +
        		(money2999pack.getValue() * pack2999) +
        		(money9999pack.getValue() * pack9999);
        
        
        totalWines = currentWines + totalIdlingWines + totalDailiesWines;
        calculateBrooksPurchases();
        
	}
	
	public void calculateBrooksPurchases() {
		
		int packsNeeded1k = 0;
		int packsNeeded5k = 0;
		int packsNeeded299 = 0;
		int packsNeeded999 = 0;
		int packsNeeded2999 = 0;
		int packsNeeded9999 = 0;	
		
		int winesNeeded = sssCost - totalWines;	
		int packsAvailable1k = 6 - oneThousandGemsPack.getValue();
		int packsAvailable5k = 5 - fiveThousandGemsPack.getValue();
		int packsAvailable299 = 3 - money299pack.getValue();
		int packsAvailable999 = 5 - money999pack.getValue();
		int packsAvailable2999 = 5 - money2999pack.getValue();
		int packsAvailable9999 = 10 - money9999pack.getValue();

		
		// If player wants to set their own goal, it's set here. Otherwise it will be 10k for an SSS
		wineGoal = Integer.parseInt(wineGoalTextfield.getText());
		if(wineGoal > 0) {
			winesNeeded = wineGoal - totalWines;
		}
		else
			winesNeeded = sssCost - totalWines;
		
		// Calculation
		while (winesNeeded >= 0 && currentGems >= 1000 && packsAvailable1k > 0) {						
			winesNeeded -= pack1k;	
			totalWines += pack1k;
			currentGems -= 1000;
			packsAvailable1k--;
			packsNeeded1k++;
			
		}
		while (winesNeeded >= 0 && currentGems >= 5000 && packsAvailable5k > 0) {						
			winesNeeded -= pack5k;	
			totalWines += pack5k;
			currentGems -= 5000;
			packsAvailable5k--;
			packsNeeded5k++;
		}
		
		while (winesNeeded > 0 && packsAvailable299 > 0) {						
			winesNeeded -= pack299;	
			totalWines += pack299;
			packsAvailable299--;
			packsNeeded299++;
		}
		
		while (winesNeeded > 0 && packsAvailable999 > 0) {						
			winesNeeded -= pack999;	
			totalWines += pack999;
			packsAvailable999--;
			packsNeeded999++;
		}
		
		while (winesNeeded > 0 && packsAvailable2999 > 0) {						
			winesNeeded -= pack2999;
			totalWines += pack2999;
			packsAvailable2999--;
			packsNeeded2999++;
		}
		
		while (winesNeeded > 0 && packsAvailable9999 > 0) {			
			winesNeeded -= pack9999;
			totalWines += pack9999;
			packsAvailable9999--;
			packsNeeded9999++;
		}
		
		
		textfield1k.setText(packsNeeded1k + "");
		textfield5k.setText(packsNeeded5k + "");
		textfield299.setText(packsNeeded299 + "");
		textfield999.setText(packsNeeded999 + "");
		textfield2999.setText(packsNeeded2999 + "");
		textfield9999.setText(packsNeeded9999 + "");
		idlingWinesLabel.setText(""+totalIdlingWines);
        dailiesWinesLabel.setText(""+totalDailiesWines);
		totalWinesLabel.setText("" + NumberFormat.getNumberInstance().format(Math.abs(totalWines)));	
	}
	
	public void resetBrooks() {
		totalWines = 0;
		wineGoal = 0;
		totalIdlingWines = 0;
		totalDailiesWines = 0;
		totalGemsWines = 0;
		totalPacksWines = 0;
		currentWines = 0;
		currentGems = 0;
		extraInstanceChances = 0;
		wineGoalTextfield.setText("0");
		currentGemsTextfield.setText("0");
		currentWinesTextfield.setText("0");
		extraInstancesTextfield.setText("0");
		dailiesDoneCheckbox.setSelected(false);
		oneThousandGemsPack.setValue(0);
		fiveThousandGemsPack.setValue(0);
		money299pack.setValue(0);
		money2999pack.setValue(0);
		money999pack.setValue(0);
		money9999pack.setValue(0);
		textfield1k.setText("0");
		textfield5k.setText("0");
		textfield299.setText("0");
		textfield999.setText("0");
		textfield2999.setText("0");
		textfield9999.setText("0");
	}

		long timestamp;
	    long secs = 0;
	    long hrs = 0;
	    long mins = 0;
	    long remainingSecs = 3;
	    long remainingMins = 0;
	    String seconds = "00";
	    String minutes = "00";
	    String hours = "00";
	    String remainingSeconds = "00";
	    String remainingMinutes = "00";
	    long fraction = 0;	     
	    Preferences prefs;	    
	    LocalDateTime savedTime;
	    long savedMillis;
	    long loadedTime;
	    int availableWinesToCollect = 0;


	    AnimationTimer timer = new AnimationTimer() {

		    @Override
		    public void start() {
		        // current time adjusted by remaining time from last run
		        timestamp = System.currentTimeMillis() - fraction;
		        super.start();
		    }

		    @Override
		    public void stop() {
		        super.stop();
		        // save leftover time not handled with the last update
		        fraction = System.currentTimeMillis() - timestamp;
		    }

		    @Override
		    public void handle(long now) {
		        long newTime = System.currentTimeMillis();
		        if (timestamp + 1000 <= newTime) {
		        	long deltaT = (newTime - timestamp) / 1000;
		            secs += deltaT;
		            remainingSecs -= deltaT;
		            timestamp += 1000 * deltaT;
		            Platform.runLater(() -> {
		                displayTimer();
		                displayNextWineTimer();
		            });
		        }
		    }
		};	
		
		public void startTimer() {
			timer.start();	
			timerStopped = false;
		}
		
		public void stopTimer() {
			timer.stop();	
			timerStopped = true;
		}
		
		public void resetTimer() {
			secs = 0;
			mins = 0;
			hrs = 0;
		    remainingSecs = 0;
			remainingMins = 6;
			fraction = 0;
			savedMillis = 0;
			availableWinesToCollect = 0;
			availableWines.setText("0");
	        displayTimer();
	        displayNextWineTimer();
		}
		
		private void loadState() {
			timerStopped = prefs.getBoolean("timerStopped", timerStopped);
			if (!timerStopped) {
		        secs = prefs.getLong("secs", 0);
		        mins = prefs.getLong("mins", 0);
		        hrs = prefs.getLong("hrs", 0);
		        remainingSecs = prefs.getLong("remainingSecs", 0);
				remainingMins = prefs.getLong("remainingMins", 0);
		        fraction = prefs.getLong("fraction", 0);	
		        availableWinesToCollect = prefs.getInt("availableWinesToCollect", 0);
		        
		    	savedMillis  = prefs.getLong("savedMillis", 0);	        
		    	Instant instant = Instant.ofEpochMilli(savedMillis);
		        savedTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());	    	
		    	long secondsEllapsed = (ChronoUnit.SECONDS.between(savedTime, LocalDateTime.now()));
		    	 
		    	hrs += secondsEllapsed / 3600;
		    	long savedRemainingSeconds = secondsEllapsed % 3600;
		    	mins += savedRemainingSeconds / 60;
		    	secs += savedRemainingSeconds % 60;
		    		        	    	
		    	long savedRemainingMinutes = secondsEllapsed / 60;
		    	availableWinesToCollect += savedRemainingMinutes / 6;
		    	remainingMins -= savedRemainingMinutes % 6;
		    	if (remainingMins < 0) {
		    		availableWinesToCollect++;
		    		remainingMins += 6;
		    	}
		    	remainingSecs -= secondsEllapsed % 60;
		    	if (remainingSecs < 0) {
		    		if (remainingMins == 0) {
			    		availableWinesToCollect++;
		    		}
		    		remainingMins += 6;
		    	}
		    	availableWines.setText("" + availableWinesToCollect);
		        displayTimer();
		        displayNextWineTimer();
			}
	    }

	   private void saveState() {
	        prefs.putLong("secs", secs);
	        prefs.putLong("mins", mins);
	        prefs.putLong("hrs", hrs);
	        prefs.putLong("remainingSecs", remainingSecs);
			prefs.putLong("remainingMins", remainingMins);
	        prefs.putLong("fraction", fraction);     
	        
	        savedMillis = System.currentTimeMillis();
	        prefs.putLong("savedMillis", savedMillis);

	        prefs.putInt("availableWinesToCollect", availableWinesToCollect);
	        prefs.putBoolean("timerStopped", timerStopped);
	    }		
	    
	   public void displayTimer() {
			 
		   if (secs >= 60) {
				mins++;
				secs = 0;
			}			
			
			if (mins >= 60) {
				hrs ++;
				mins = 0;
			}
			
			if (secs < 10)
				seconds = "0" + secs;
			else 
				seconds = "" + secs;
			
			if (mins < 10)
				minutes = "0" + mins;
			else 
				minutes = "" + mins;
			
			if (hrs < 10)
				hours = "0" + hrs;
			else 
				hours = "" + hrs;		
			
			timerDisplay.setText(hours + ":" + minutes + ":" + seconds); 
			
		}
	   
	   public void displayNextWineTimer() {
		   
			if (remainingSecs < 0) {
				remainingMins--;
				remainingSecs = 59;
			}	
			
			if (remainingSecs < 10)
				remainingSeconds = "0" + remainingSecs;
			else 
				remainingSeconds = "" + remainingSecs;
			
			remainingMinutes = "0" + remainingMins;
		
			if (remainingMins == 0 && remainingSecs == 0) {
				availableWinesToCollect++;
				remainingMins = 6;			
				availableWines.setText("" + availableWinesToCollect);
			}
		   
		   remainingTimerDisplay.setText(remainingMinutes + ":" + remainingSeconds);
	   }
}