package application;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CharComparison implements ActionListener {
	static boolean showAutocomplete = false; // Flag variable to track autocomplete visibility

	static Button statsButton, skillsButton;
	static TextField searchA, searchB;
	static Pane char1Panel, char2Panel;
	static LayeredPane compPanel;
	static Label skillsLabelA, skillsLabelB, statsLabelA, statsLabelB, resultsLabel, imageLabelA, imageLabelB;
	static DefaultListModel<String> autocompleteModelA = new DefaultListModel<>();
	static DefaultListModel<String> autocompleteModelB = new DefaultListModel<>();
	static List<String> autocompleteListA = new List<>(autocompleteModelA);
	static List<String> autocompleteListB = new List<String>(autocompleteModelB);
	static ScrollPane autocompleteScrollPaneA = new ScrollPane(autocompleteListA);
	static ScrollPane autocompleteScrollPaneB = new ScrollPane(autocompleteListB);
	static String choiceA, choiceB;
	static ImageIcon charIconA, charIconB;

	public static void Comparison(JFrame myFrame) {
		// Container parentA = autocompleteScrollPaneA.getParent();
		// Container parentB = autocompleteScrollPaneA.getParent();

		Border border;
		border = BorderFactory.createLineBorder(new Color(173, 101, 0), 3);

		// comparison panel details
		compPanel = new JLayeredPane();
		compPanel.setLayout(null);
		compPanel.setBounds(0, 115, 840, 750);
		compPanel.setFont(new Font("Carlito", Font.BOLD, 25));
		compPanel.setBackground(new Color(210, 208, 143));
		compPanel.setOpaque(true);
		compPanel.setVisible(false);
		myFrame.add(compPanel);

		char1Panel = new JPanel();
		char1Panel.setLayout(null);
		char1Panel.setBounds(0, 0, 420, 750);
		char1Panel.setBorder(border);
		char1Panel.setFont(new Font("Carlito", Font.BOLD, 25));
		char1Panel.setBackground(new Color(210, 208, 143));
		char1Panel.setOpaque(true);
		char1Panel.setVisible(true);
		compPanel.add(char1Panel, Integer.valueOf(0));

		char2Panel = new JPanel();
		char2Panel.setLayout(null);
		char2Panel.setBounds(420, 0, 420, 750);
		char2Panel.setBorder(border);
		char2Panel.setFont(new Font("Carlito", Font.BOLD, 25));
		char2Panel.setBackground(new Color(210, 208, 143));
		char2Panel.setOpaque(true);
		char2Panel.setVisible(true);
		compPanel.add(char2Panel, Integer.valueOf(0));

		searchA = new JTextField();
		searchA.setBounds(135, 30, 150, 30);
		searchA.setLayout(new BorderLayout());
		searchA.setFont(new Font("Carlito", Font.BOLD, 13));
		char1Panel.add(searchA);

		searchB = new JTextField();
		searchB.setBounds(135, 30, 150, 30);
		searchB.setLayout(new BorderLayout());
		searchB.setFont(new Font("Carlito", Font.BOLD, 15));
		char2Panel.add(searchB);

		imageLabelA = new JLabel();
		imageLabelA.setBorder(border);
		imageLabelA.setBounds(130, 80, 160, 200);
		imageLabelA.setPreferredSize(new Dimension(160, 200));
		char1Panel.add(imageLabelA);

		imageLabelB = new JLabel();
		imageLabelB.setBorder(border);
		imageLabelB.setBounds(130, 80, 160, 200);
		imageLabelB.setPreferredSize(new Dimension(160, 200));
		char2Panel.add(imageLabelB);

		statsLabelA = new JLabel();
		statsLabelA.setBorder(border);
		statsLabelA.setLayout(new GridLayout(8, 1, 0, 0));
		statsLabelA.setBounds(110, 300, 200, 400);
		char1Panel.add(statsLabelA);

		statsLabelB = new JLabel();
		statsLabelB.setBorder(border);
		statsLabelB.setLayout(new GridLayout(8, 1, 0, 0));
		statsLabelB.setBounds(110, 300, 200, 400);
		char2Panel.add(statsLabelB);

		resultsLabel = new JLabel();
		resultsLabel.setLayout(new GridLayout(8,1));
		resultsLabel.setBorder(border);
		resultsLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		resultsLabel.setBackground(new Color(210, 208, 143));
		resultsLabel.setOpaque(true);
		resultsLabel.setVisible(true);
		resultsLabel.setBounds(345, 280, 150, 400);
		compPanel.add(resultsLabel, Integer.valueOf(1));

		statsButton = new JButton("<html><center>" + "Compare Stats" + "</center></html>");
		statsButton.setFont(new Font("Carlito", Font.BOLD, 13));
		statsButton.setFocusable(false);
		statsButton.setVisible(true);
		statsButton.setBounds(310, 150, 100, 50);
		statsButton.addActionListener(e -> {
			if (choiceA != null && choiceB != null)
				compareStats();
		});
		compPanel.add(statsButton, Integer.valueOf(1));

		SearchBoxDemo();
		char1Panel.setComponentZOrder(autocompleteScrollPaneA, 0);
		char2Panel.setComponentZOrder(autocompleteScrollPaneB, 0);

		// remove focus from the search bar if click outside of it
		compPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Check if the mouse click is outside of the search field
				if (e.getSource() != searchA) {
					searchA.setFocusable(false); // Remove the focus from the text field
					searchB.setFocusable(false);
					showAutocomplete = false;
				}
			}
		});

		// Enable focus of search field A when clicked
		searchA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchA.setFocusable(true); // Enable focus on the text field
				searchA.requestFocusInWindow(); // Set focus to the text field
				autocompleteScrollPaneA.setVisible(true);
			}
		});

		// Enable focus of search field B when clicked
		searchB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				searchB.setFocusable(true); // Enable focus on the text field
				searchB.requestFocusInWindow(); // Set focus to the text field
				autocompleteScrollPaneB.setVisible(true);
			}
		});

		// Box A arrow key and enter key handling
		searchA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				ClassLoader classLoader = getClass().getClassLoader();

				// Handle enter key press
				if (keyCode == KeyEvent.VK_ENTER) {
					// hide autocomplete list if you type the exact name of the char
					if (Main.charNames.contains(searchA.getText()) && autocompleteModelA.size() == 1) {
						showAutocomplete = false;
						autocompleteScrollPaneA.setVisible(false);
					}

					if (showAutocomplete) {
						String selectedValue = autocompleteListA.getSelectedValue();
						if (selectedValue != null) {
							searchA.setText(selectedValue);
							showAutocomplete = false; // Hide the autocomplete list
							autocompleteScrollPaneA.setVisible(false);

						}
					} else {
						choiceA = searchA.getText();

						for (String charName : Main.charNames) {
							if (choiceA.equals(charName)) {

								String iconPath = choiceA + ".png";
								try {
									InputStream imageInput = classLoader.getResourceAsStream(iconPath);									BufferedImage image = ImageIO.read(imageInput);
									int width = imageLabelA.getPreferredSize().width;
									int height = imageLabelA.getPreferredSize().height;
									Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
									charIconA = new ImageIcon(resizedImage);
									imageLabelA.setIcon(charIconA);
								}

								catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
						showAutocomplete = false;
						autocompleteScrollPaneA.setVisible(false);
					}
				} else if (keyCode == KeyEvent.VK_DOWN) {
					// Handle down arrow key press
					if (showAutocomplete) {
						int selectedIndex = autocompleteListA.getSelectedIndex();
						int maxIndex = autocompleteListA.getModel().getSize() - 1;
						if (selectedIndex < maxIndex) {
							autocompleteListA.setSelectedIndex(selectedIndex + 1);
						} else if (selectedIndex == maxIndex) {
							autocompleteListA.setSelectedIndex(0);
						}

					}
				} else if (keyCode == KeyEvent.VK_UP) {
					// Handle up arrow key press
					if (showAutocomplete) {
						int selectedIndex = autocompleteListA.getSelectedIndex();
						if (selectedIndex > 0) {
							autocompleteListA.setSelectedIndex(selectedIndex - 1);
						} else if (selectedIndex < 1) {
							autocompleteListA.setSelectedIndex(autocompleteListA.getModel().getSize() - 1);
						}
					}
				} else {
					// Other key presses
					String searchTerm = searchA.getText().toLowerCase();
					updateAutoCompleteListA(searchTerm);
					showAutocomplete = true; // Show the autocomplete list
				}
			}
		});

		// Box B arrow key and enter key handling
		searchB.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				ClassLoader classLoader = getClass().getClassLoader();

				if (keyCode == KeyEvent.VK_ENTER) {
					// Handle enter key press

					// hide autocomplete list if you type the exact name of the char
					if (Main.charNames.contains(searchB.getText()) && autocompleteModelB.size() == 1) {
						showAutocomplete = false;
						autocompleteScrollPaneB.setVisible(false);
					}
					if (showAutocomplete) {
						String selectedValue = autocompleteListB.getSelectedValue();
						if (selectedValue != null) {
							searchB.setText(selectedValue);
							showAutocomplete = false; // Hide the autocomplete list
							autocompleteScrollPaneB.setVisible(false);
						}
					} else {
						choiceB = searchB.getText();

						for (String charName : Main.charNames) {
							if (choiceB.equals(charName)) {

								String iconPath = choiceB + ".png";
								try {
									InputStream imageInput = classLoader.getResourceAsStream(iconPath);									BufferedImage image = ImageIO.read(imageInput);
									int width = imageLabelB.getPreferredSize().width;
									int height = imageLabelB.getPreferredSize().height;
									Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
									charIconB = new ImageIcon(resizedImage);
									imageLabelB.setIcon(charIconB);
								}

								catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
						showAutocomplete = false;
						autocompleteScrollPaneB.setVisible(false);
					}
				} else if (keyCode == KeyEvent.VK_DOWN) {
					// Handle down arrow key press
					if (showAutocomplete) {
						int selectedIndex = autocompleteListB.getSelectedIndex();
						int maxIndex = autocompleteListB.getModel().getSize() - 1;
						if (selectedIndex < maxIndex) {
							autocompleteListB.setSelectedIndex(selectedIndex + 1);
						} else if (selectedIndex == maxIndex) {
							autocompleteListB.setSelectedIndex(0);
						}

					}
				} else if (keyCode == KeyEvent.VK_UP) {
					// Handle up arrow key press
					if (showAutocomplete) {
						int selectedIndex = autocompleteListB.getSelectedIndex();
						if (selectedIndex > 0) {
							autocompleteListB.setSelectedIndex(selectedIndex - 1);
						} else if (selectedIndex < 1) {
							autocompleteListB.setSelectedIndex(autocompleteListB.getModel().getSize() - 1);
						}
					}
				} else {
					// Other key presses
					String searchTerm = searchB.getText().toLowerCase();
					updateAutoCompleteListB(searchTerm);
					showAutocomplete = true; // Show the autocomplete list
				}
			}
		});

	}

	public static void compareStats() {
		
		JLabel hpLabel, hpTopLabel, atkLabel, atkTopLabel, defLabel, defTopLabel, speedLabel, speedTopLabel;
		Character charChoiceA = Character.findUnit(choiceA, Main.unitList);
		Character charChoiceB = Character.findUnit(choiceB, Main.unitList);
		
		
		boolean hpWinnerA, hpWinnerB, atkWinnerA, atkWinnerB, defWinnerA, defWinnerB, speedWinnerA, speedWinnerB, hpDraw, atkDraw, defDraw, speedDraw;
		
		hpWinnerA = false;
		hpWinnerB = false;
		atkWinnerA = false;
		atkWinnerB = false;
		defWinnerA = false;
		defWinnerB = false;
		speedWinnerA = false;
		speedWinnerB = false;
		hpDraw = false;
		atkDraw = false;
		defDraw = false;
		speedDraw = false;
		
		String hpDifference = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getHp() - charChoiceB.getHp()));
		if (charChoiceA.getHp() > charChoiceB.getHp())
			hpWinnerA = true;
		else if (charChoiceA.getHp() < charChoiceB.getHp())
			hpWinnerB = true;
		else
			hpDraw = true;
		
		String atkDifference = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getAtk() - charChoiceB.getAtk()));	
		if (charChoiceA.getAtk() > charChoiceB.getAtk())
			atkWinnerA = true;
		else if (charChoiceA.getAtk() < charChoiceB.getAtk())
			atkWinnerB = true;
		else
			atkDraw = true;
		
		String defDifference = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getDef() - charChoiceB.getDef()));
		if (charChoiceA.getDef() > charChoiceB.getDef())
			defWinnerA = true;
		else if (charChoiceA.getDef() < charChoiceB.getDef())
			defWinnerB = true;
		else
			defDraw = true;
		
		String speedDifference = NumberFormat.getNumberInstance().format(Math.abs(charChoiceA.getSpeed() - charChoiceB.getSpeed()));
		if (charChoiceA.getSpeed() > charChoiceB.getSpeed())
			speedWinnerA = true;
		else if (charChoiceA.getSpeed() < charChoiceB.getSpeed())
			speedWinnerB = true;
		else
			speedDraw = true;
			
		
		generateStats(charChoiceA, searchA, statsLabelA, hpWinnerA, atkWinnerA, defWinnerA, speedWinnerA, hpDraw, atkDraw, defDraw, speedDraw);
		generateStats(charChoiceB, searchB, statsLabelB, hpWinnerB, atkWinnerB, defWinnerB, speedWinnerB, hpDraw, atkDraw, defDraw, speedDraw);
		resultsLabel.removeAll();
		
		hpTopLabel = new JLabel("HP");
		hpTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		hpTopLabel.setForeground(new Color(0,186, 53));
		hpTopLabel.setPreferredSize(new Dimension(200, 100));
		hpTopLabel.setHorizontalAlignment(JLabel.CENTER);
		hpTopLabel.setVisible(true);
		resultsLabel.add(hpTopLabel);

		hpLabel = new JLabel(hpDifference);
		hpLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		hpLabel.setPreferredSize(new Dimension(200, 100));
		hpLabel.setHorizontalAlignment(JLabel.CENTER);
		hpLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // Adds a 5-pixel spacing at the bottom
		hpLabel.setVisible(true);
		resultsLabel.add(hpLabel);

		atkTopLabel = new JLabel("ATK");
		atkTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		atkTopLabel.setForeground(Color.red);
		atkTopLabel.setPreferredSize(new Dimension(200, 100));
		atkTopLabel.setHorizontalAlignment(JLabel.CENTER);
		atkTopLabel.setVisible(true);
		resultsLabel.add(atkTopLabel);

		atkLabel = new JLabel(atkDifference);
		atkLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		atkLabel.setPreferredSize(new Dimension(200, 100));
		atkLabel.setHorizontalAlignment(JLabel.CENTER);
		atkLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // Adds a 5-pixel spacing at the bottom

		atkLabel.setVisible(true);
		resultsLabel.add(atkLabel);

		defTopLabel = new JLabel("DEF");
		defTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		defTopLabel.setForeground(Color.blue);
		defTopLabel.setPreferredSize(new Dimension(200, 100));
		defTopLabel.setHorizontalAlignment(JLabel.CENTER);
		defTopLabel.setVisible(true);
		resultsLabel.add(defTopLabel);

		defLabel = new JLabel(defDifference);
		defLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		defLabel.setPreferredSize(new Dimension(200, 100));
		defLabel.setHorizontalAlignment(JLabel.CENTER);
		defLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // Adds a 5-pixel spacing at the bottom
		defLabel.setVisible(true);
		resultsLabel.add(defLabel);

		speedTopLabel = new JLabel("SPD");
		speedTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		speedTopLabel.setForeground(new Color(200, 0, 245));
		speedTopLabel.setPreferredSize(new Dimension(200, 100));
		speedTopLabel.setHorizontalAlignment(JLabel.CENTER);
		speedTopLabel.setVisible(true);
		resultsLabel.add(speedTopLabel);

		speedLabel = new JLabel(speedDifference);
		speedLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		speedLabel.setPreferredSize(new Dimension(200, 100));
		speedLabel.setHorizontalAlignment(JLabel.CENTER);
		speedLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // Adds a 5-pixel spacing at the bottom
		speedLabel.setVisible(true);
		resultsLabel.add(speedLabel);

		resultsLabel.repaint();
		resultsLabel.revalidate();

	}
	
	public static void generateStats (Character charChoice, JTextField searchBox, JLabel statsLabel, Boolean hpWinner, Boolean atkWinner, Boolean defWinner, Boolean speedWinner, 
									Boolean hpDraw, Boolean atkDraw, Boolean defDraw, Boolean speedDraw) {

		JLabel hpLabel, hpTopLabel, atkLabel, atkTopLabel, defLabel, defTopLabel, speedLabel, speedTopLabel;
		
		statsLabel.removeAll();
		
		hpTopLabel = new JLabel("HP");
		hpTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		hpTopLabel.setForeground(new Color(0,186, 53));
		hpTopLabel.setPreferredSize(new Dimension(200, 100));
		hpTopLabel.setHorizontalAlignment(JLabel.CENTER);
		hpTopLabel.setVisible(true);
		statsLabel.add(hpTopLabel);

		hpLabel = new JLabel(String.valueOf(NumberFormat.getNumberInstance().format(charChoice.getHp())));
		hpLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		hpLabel.setPreferredSize(new Dimension(200, 100));
		hpLabel.setVerticalAlignment(JLabel.CENTER);
		hpLabel.setHorizontalAlignment(JLabel.CENTER);		
		if (hpDraw)
			hpLabel.setBackground(Color.white);
		else if (hpWinner)
			hpLabel.setBackground(Color.green);
		else if (hpWinner==false)
			hpLabel.setBackground(Color.red);	
		hpLabel.setOpaque(true);
		hpLabel.setVisible(true);
		statsLabel.add(hpLabel);

		atkTopLabel = new JLabel("ATK");
		atkTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		atkTopLabel.setForeground(Color.red);
		atkTopLabel.setPreferredSize(new Dimension(200, 100));
		atkTopLabel.setHorizontalAlignment(JLabel.CENTER);
		atkTopLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		atkTopLabel.setVisible(true);
		statsLabel.add(atkTopLabel);

		atkLabel = new JLabel(String.valueOf(NumberFormat.getNumberInstance().format(charChoice.getAtk())));
		atkLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		atkLabel.setPreferredSize(new Dimension(200, 100));
		atkLabel.setHorizontalAlignment(JLabel.CENTER);
		if(atkDraw)
			atkLabel.setBackground(Color.white);
		else if (atkWinner)
			atkLabel.setBackground(Color.green);
		else if (atkWinner==false)
			atkLabel.setBackground(Color.red);	
		atkLabel.setOpaque(true);
		atkLabel.setVisible(true);
		statsLabel.add(atkLabel);

		defTopLabel = new JLabel("DEF");
		defTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		defTopLabel.setForeground(Color.blue);
		defTopLabel.setPreferredSize(new Dimension(200, 100));
		defTopLabel.setHorizontalAlignment(JLabel.CENTER);
		defTopLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		defTopLabel.setVisible(true);
		statsLabel.add(defTopLabel);

		defLabel = new JLabel(String.valueOf(NumberFormat.getNumberInstance().format(charChoice.getDef())));
		defLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		defLabel.setPreferredSize(new Dimension(200, 100));
		defLabel.setHorizontalAlignment(JLabel.CENTER);
		
		if(defDraw)
			defLabel.setBackground(Color.white);
		else if (defWinner)
			defLabel.setBackground(Color.green);
		else if (defWinner==false)
			defLabel.setBackground(Color.red);	
		defLabel.setOpaque(true);
		defLabel.setVisible(true);
		statsLabel.add(defLabel);

		speedTopLabel = new JLabel("SPD");
		speedTopLabel.setFont(new Font("Carlito", Font.BOLD, 25));
		speedTopLabel.setForeground(new Color(200, 0, 245));
		speedTopLabel.setPreferredSize(new Dimension(200, 100));
		speedTopLabel.setHorizontalAlignment(JLabel.CENTER);
		speedTopLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		speedTopLabel.setVisible(true);
		statsLabel.add(speedTopLabel);

		speedLabel = new JLabel(String.valueOf(NumberFormat.getNumberInstance().format(charChoice.getSpeed())));
		speedLabel.setFont(new Font("Carlito", Font.BOLD, 20));
		speedLabel.setPreferredSize(new Dimension(200, 100));
		speedLabel.setHorizontalAlignment(JLabel.CENTER);
		if(speedDraw)
			speedLabel.setBackground(Color.white);
		else if (speedWinner)
			speedLabel.setBackground(Color.green);
		else if (speedWinner==false)
			speedLabel.setBackground(Color.red);	
		speedLabel.setOpaque(true);
		speedLabel.setVisible(true);
		statsLabel.add(speedLabel);
		
		statsLabel.repaint();
		statsLabel.revalidate();
	}

	public void compareSkills() {

	}

	public static void SearchBoxDemo() {
		autocompleteScrollPaneA.setVisible(false); // Hide initially
		autocompleteScrollPaneB.setVisible(false);
		char1Panel.add(autocompleteScrollPaneA);
		char2Panel.add(autocompleteScrollPaneB);

		// Add a key listener to search field A for auto-completion behavior
		searchA.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_DOWN && keyCode != KeyEvent.VK_UP) {
					String searchTerm = searchA.getText().toLowerCase();
					updateAutoCompleteListA(searchTerm);
					updateAutocompleteScrollPaneHeightA();
					autocompleteScrollPaneA.setVisible(showAutocomplete && !searchTerm.isEmpty());
				}
			}
		});

		// Add a focus listener to hide the autocomplete panel when focus is lost
		searchA.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				autocompleteScrollPaneA.setVisible(false);
			}
		});

		// Add a mouse listener to handle selection from the auto-complete list
		autocompleteListA.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String selectedValue = autocompleteListA.getSelectedValue();
				if (selectedValue != null) {
					searchA.setText(selectedValue);
					choiceA = searchA.getText();
					ClassLoader classLoader = getClass().getClassLoader();
					
					for (String charName : Main.charNames) {
						if (choiceA.equals(charName)) {
							String iconPath = choiceA + ".png";
							try {
								InputStream imageInput = classLoader.getResourceAsStream(iconPath);
								BufferedImage image = ImageIO.read(imageInput);
								int width = imageLabelA.getPreferredSize().width;
								int height = imageLabelA.getPreferredSize().height;
								Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
								charIconA = new ImageIcon(resizedImage);
								imageLabelA.setIcon(charIconA);
							}

							catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
					showAutocomplete = false;
					autocompleteScrollPaneA.setVisible(false);
				}
			}
		});

		// Add a key listener to search field B for auto-completion behavior
		searchB.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode != KeyEvent.VK_ENTER && keyCode != KeyEvent.VK_DOWN && keyCode != KeyEvent.VK_UP) {
					String searchTerm = searchB.getText().toLowerCase();
					updateAutoCompleteListB(searchTerm);
					updateAutocompleteScrollPaneHeightB();
					autocompleteScrollPaneB.setVisible(showAutocomplete && !searchTerm.isEmpty());
				}
			}
		});

		// Add a focus listener to hide the autocomplete panel when focus is lost
		searchB.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				autocompleteScrollPaneB.setVisible(false);
			}
		});

		// Add a mouse listener to handle selection from the auto-complete list
		autocompleteListB.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String selectedValue = autocompleteListB.getSelectedValue();
				if (selectedValue != null) {
					searchB.setText(selectedValue);
					choiceB = searchB.getText();
					ClassLoader classLoader = getClass().getClassLoader();

					for (String charName : Main.charNames) {
						if (choiceB.equals(charName)) {

							String iconPath = choiceB + ".png";
							try {
								InputStream imageInput = classLoader.getResourceAsStream(iconPath);								
								BufferedImage image = ImageIO.read(imageInput);
								int width = imageLabelB.getPreferredSize().width;
								int height = imageLabelB.getPreferredSize().height;
								Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
								charIconB = new ImageIcon(resizedImage);
								imageLabelB.setIcon(charIconB);
							}

							catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
					showAutocomplete = false; // Hide the autocomplete list
					autocompleteScrollPaneB.setVisible(false);
				}
			}
		});
	}

	public static void updateAutocompleteScrollPaneHeightA() {

		int itemCount = autocompleteModelA.size();
		int maxHeight = 150; // Maximum height of the scroll pane
		int itemHeight = 20; // Height of each item in the list
		int scrollPaneHeight = Math.min(itemCount * itemHeight, maxHeight);
		autocompleteScrollPaneA.setBounds(135, 60, 150, scrollPaneHeight);
	}

	private static void updateAutocompleteScrollPaneHeightB() {
		int itemCount = autocompleteModelB.size();
		int maxHeight = 150; // Maximum height of the scroll pane
		int itemHeight = 20; // Height of each item in the list
		int scrollPaneHeight = Math.min(itemCount * itemHeight, maxHeight);
		autocompleteScrollPaneB.setBounds(135, 60, 150, scrollPaneHeight);
	}

	private static void updateAutoCompleteListA(String searchTerm) {

		autocompleteModelA.clear();

		for (String charName : Main.charNames) {
			if (charName.toLowerCase().startsWith(searchTerm)) {
				autocompleteModelA.addElement(charName);
			}
		}
	}

	private static void updateAutoCompleteListB(String searchTerm) {

		autocompleteModelB.clear();

		for (String charName : Main.charNames) {
			if (charName.toLowerCase().startsWith(searchTerm)) {
				autocompleteModelB.addElement(charName);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

}



