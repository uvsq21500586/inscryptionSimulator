package frames;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;

import cards.Card;
import frames.menubuttons.*;

public class Menu extends JFrame {
	//types: beast robot undead wizard
	//codes interdits: 1-1,4-2,5-2,7-2,9-2,9-3,10-3,11-2,11-3,12-2,12-3,14-3
	//  15-1,16-{3+4},17-{2+3+4},18-{2-4},19-{3-4}, 20-3,22-3
	
	//j1:(23) 23-1*1(23,1(23)), booster value = 2*4+2=10, vies: 1/1
	//crédits: 0
	//j2: s4;21-1(21)
	
	//base pelts prizes: 1-2-4, base start main deck size: 4, base start source deck size: 5
	//parameters: positive(bonus chance(more bonus events + campfire), nb choices cards, nbWavesPerBoss), negativeOrNeutral(higher pelt price and more boulders, harder trials, harder bosses)
	//totem -> more lifePoints, bonus stats for trade and trial, nb cards for trial success(min 3) and more chance camp fire success
	//nb deaths = 1 + (numero map(y1 + z1 + z2 - 2 in "j1: x1-y1(z1)" and "j2: x2-y2(z2)"))%(nb source cards supp + nb lives per game)
	
	//player game parameters -> choices costs, choices trader, totem
	//player global parameters -> player game parameters,(nb life points/ nb source cards supp)
	//rival global parameters -> main deck size/source deck size,(nb life points/ nb cards supp to optimize)
	//harder trial: for 4 levels, hp and attack limit +4, costs limit +2, effects limit +1
	
	//(fait: 5-1 vs 11-1)
	//serie: 14[4(2,2,1)-3(1(+0),2(+1),1(+0))],11[1(1,1,1)-4(2(+1),2(+1),1(+0))]
	// 11[1(1,1,1)-4(2(+1),2(+1),1(+0))]
	//j1: 9-1(9),1[1:2*1blood, 1:12345678, 1:1-0-0](incremented by (nb lifes -1))
	//bonus: 2-0-0
	//crédits: 0,d0
	//lifes: 1/1
	//nb deaths: 2/2
	//malus: dice, lifePoints adv, strenght, cards, cardsup, price pelt:+0
	//price pelts: 1,2,4
	//j2: 6-2(3)
	
	//default parameters
	private Integer modulo1 = 11;
	private Integer multiplier1 = 4;
	private Integer globalStrenght1 = 1;
	private Integer rarityStrenght1 = 1;
	private String typecards1 = "robot";
	private Integer lifePointsP1 = 6;
	private Integer nbMainCards1 = 6;
	private Integer nbSourceCards1 = 5;
	private List<Card> mainDeck1;
	private List<Card> sourceDeck1;
	
	private Integer modulo2 = 7;
	private Integer multiplier2 = 2;
	private Integer globalStrenght2 = 1;
	private Integer rarityStrenght2 = 1;
	private String typecards2 = "beast";
	private Integer lifePointsP2 = 5;
	private Integer nbMainCards2 = 5;
	private Integer nbSourceCards2 = 5;
	private Integer nbSupCards2 = 0;
	
	private List<Card> mainDeck2;
	private List<Card> sourceDeck2;
	
	private List<Card> deadCardsList;
	private List<Card> availableDeadCardsList;
	
	private ButtonToDuel buttonTestDuel;
	private ButtonToDuel buttonTrueDuel;
	private ButtonToSimulatorCard buttonSimulatorCard;
	private ButtonToBoosterCard buttonBoosterCard;
	private ButtonToBuildDeck buttonToBuildDeck;
	private ButtonToSeeDeck buttonToSeeDeck;
	private ButtonToOptions buttonOptions;
	private ButtonToHelp buttonHelp;
	private ButtonToSpecial buttonSpecial;
	private ButtonToResetDeathCards buttonToResetDeathCards;
	
	private JCheckBox checkGreenMageP1 = new JCheckBox("greenP1");
	private JCheckBox checkOrangeMageP1 = new JCheckBox("orangeP1");
	private JCheckBox checkBlueMageP1 = new JCheckBox("blueP1");
	
	private JCheckBox checkGreenMageP2 = new JCheckBox("greenP2");
	private JCheckBox checkOrangeMageP2 = new JCheckBox("orangeP2");
	private JCheckBox checkBlueMageP2 = new JCheckBox("blueP2");
	
	private JTextArea difficultyP2 = new JTextArea("0");
	
	public Menu() {
		super("Menu");
	}
	
	
	
	public ButtonToDuel getButtonduel() {
		return buttonTestDuel;
	}



	public void setButtonduel(ButtonToDuel buttonduel) {
		this.buttonTestDuel = buttonduel;
	}



	public void open() throws IOException, FontFormatException {
		Image img = ImageIO.read(new File("img/wood-background.jpeg"));
		JPanel panelBackground = new JPanel();
		panelBackground.setBounds(0, 0, 1500, 800);
		panelBackground.setLayout(null);
		//frame.addKeyListener((KeyListener) panelTerrain);
		JLabel labelBackground = new JLabel("");
		labelBackground.setBounds(0, 0, panelBackground.getWidth(), panelBackground.getHeight());
		panelBackground.add(labelBackground);
		labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(panelBackground.getWidth(), panelBackground.getHeight(), 
							Image.SCALE_DEFAULT)));
		this.setSize(1500, 800);
		setButtons();
		this.getContentPane().add(buttonTrueDuel);
		this.getContentPane().add(buttonTestDuel);
		this.getContentPane().add(buttonSimulatorCard);
		this.getContentPane().add(buttonToBuildDeck);
		this.getContentPane().add(buttonToSeeDeck);
		this.getContentPane().add(buttonBoosterCard);
		this.getContentPane().add(buttonOptions);
		this.getContentPane().add(buttonHelp);
		this.getContentPane().add(buttonSpecial);
		this.getContentPane().add(buttonToResetDeathCards);
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		JLabel wizardTypesP1 = new JLabel("Preference wizard types P1:");
		wizardTypesP1.setBounds(1000,150,400,50);
		wizardTypesP1.setFont(font.deriveFont(Font.BOLD,18f));
		wizardTypesP1.setForeground(new Color(255,255,255));
		this.getContentPane().add(wizardTypesP1);
		checkGreenMageP1.setBounds(1000,200,400,50);
		checkGreenMageP1.setFont(font.deriveFont(Font.BOLD,18f));
		checkGreenMageP1.setForeground(new Color(255,255,255));
		checkGreenMageP1.setOpaque(false);
		checkGreenMageP1.setSelected(true);
		this.getContentPane().add(checkGreenMageP1);
		checkOrangeMageP1.setBounds(1000,250,400,50);
		checkOrangeMageP1.setFont(font.deriveFont(Font.BOLD,18f));
		checkOrangeMageP1.setForeground(new Color(255,255,255));
		checkOrangeMageP1.setOpaque(false);
		checkOrangeMageP1.setSelected(true);
		this.getContentPane().add(checkOrangeMageP1);
		checkBlueMageP1.setBounds(1000,300,400,50);
		checkBlueMageP1.setFont(font.deriveFont(Font.BOLD,18f));
		checkBlueMageP1.setForeground(new Color(255,255,255));
		checkBlueMageP1.setOpaque(false);
		checkBlueMageP1.setSelected(true);
		this.getContentPane().add(checkBlueMageP1);
		
		JLabel wizardTypesP2 = new JLabel("Preference wizard types P2:");
		wizardTypesP2.setBounds(1000,400,400,50);
		wizardTypesP2.setFont(font.deriveFont(Font.BOLD,18f));
		wizardTypesP2.setForeground(new Color(255,255,255));
		this.getContentPane().add(wizardTypesP2);
		checkGreenMageP2.setBounds(1000,450,400,50);
		checkGreenMageP2.setFont(font.deriveFont(Font.BOLD,18f));
		checkGreenMageP2.setForeground(new Color(255,255,255));
		checkGreenMageP2.setOpaque(false);
		checkGreenMageP2.setSelected(true);
		this.getContentPane().add(checkGreenMageP2);
		checkOrangeMageP2.setBounds(1000,500,400,50);
		checkOrangeMageP2.setFont(font.deriveFont(Font.BOLD,18f));
		checkOrangeMageP2.setForeground(new Color(255,255,255));
		checkOrangeMageP2.setOpaque(false);
		checkOrangeMageP2.setSelected(true);
		this.getContentPane().add(checkOrangeMageP2);
		checkBlueMageP2.setBounds(1000,550,400,50);
		checkBlueMageP2.setFont(font.deriveFont(Font.BOLD,18f));
		checkBlueMageP2.setForeground(new Color(255,255,255));
		checkBlueMageP2.setOpaque(false);
		checkBlueMageP2.setSelected(true);
		this.getContentPane().add(checkBlueMageP2);
		
		
		JLabel difficultyP2Label = new JLabel("difficulty:");
		difficultyP2Label.setBounds(50,350,120,50);
		difficultyP2Label.setFont(font.deriveFont(Font.BOLD,18f));
		difficultyP2Label.setForeground(new Color(255,255,255));
		this.getContentPane().add(difficultyP2Label);
		
		difficultyP2.setBounds(200,350,100,50);
		this.getContentPane().add(difficultyP2);
		difficultyP2.setFont(font.deriveFont(Font.BOLD,18f));
		
		this.getContentPane().add(panelBackground);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new MenuControler(this);
	}
	
	private void setButtons() {
		buttonTrueDuel = new ButtonToDuel("True Duel");
		buttonTrueDuel.setBounds(600, 190, 150, 50);
		buttonTrueDuel.setForeground(new Color(255, 255, 255));
		buttonTestDuel = new ButtonToDuel("Test Duel");
		buttonTestDuel.setBounds(600, 300, 150, 50);
		buttonTestDuel.setForeground(new Color(255, 255, 255));
		buttonSimulatorCard = new ButtonToSimulatorCard();
		buttonSimulatorCard.setBounds(600, 410, 150, 50);
		buttonSimulatorCard.setForeground(new Color(255, 255, 255));
		buttonSimulatorCard.add(new JLabel("Card simulator"));
		buttonToBuildDeck = new ButtonToBuildDeck();
		buttonToBuildDeck.setBounds(600, 520, 150, 50);
		buttonToBuildDeck.setForeground(new Color(255, 255, 255));
		buttonToBuildDeck.add(new JLabel("Build deck"));
		buttonBoosterCard = new ButtonToBoosterCard();
		buttonBoosterCard.setBounds(600, 630, 150, 50);
		buttonBoosterCard.setForeground(new Color(255, 255, 255));
		buttonBoosterCard.add(new JLabel("Get booster"));
		buttonToSeeDeck = new ButtonToSeeDeck();
		buttonToSeeDeck.setBounds(800, 520, 150, 50);
		buttonToSeeDeck.setForeground(new Color(255, 255, 255));
		buttonToSeeDeck.add(new JLabel("See deck"));
		buttonOptions = new ButtonToOptions();
		buttonOptions.setBounds(100, 520, 150, 50);
		buttonOptions.setForeground(new Color(255, 255, 255));
		buttonOptions.add(new JLabel("Options"));
		buttonHelp = new ButtonToHelp();
		buttonHelp.setBounds(100, 620, 150, 50);
		buttonHelp.setForeground(new Color(255, 255, 255));
		buttonHelp.add(new JLabel("Help"));
		buttonSpecial = new ButtonToSpecial();
		buttonSpecial.setBounds(800, 630, 150, 50);
		buttonSpecial.setForeground(new Color(255, 255, 255));
		buttonSpecial.add(new JLabel("buttonSpecial"));
		buttonToResetDeathCards = new ButtonToResetDeathCards("Reset deaths");
		buttonToResetDeathCards.setBounds(800, 190, 150, 50);
		buttonToResetDeathCards.setForeground(new Color(255, 255, 255));
		
		this.repaint();
		this.revalidate();
	}
	
	public void saveDeck(List<Card> mainDeck1, List<Card> sourceDeck1) {
		File file = new File("save/deck.txt");

		// créer le fichier s'il n'existe pas
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String content = "main deck:\n";
		for (int i=0;i<mainDeck1.size();i++) {
			Card card = mainDeck1.get(i);
			content = content + card.toString() + "\n";
		}
		content = content + "source deck:\n";
		for (int i=0;i<sourceDeck1.size();i++) {
			Card card = sourceDeck1.get(i);
			content = content + card.toString() + "\n";
		}
		content = content + "fin\n";
		
		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	public void saveDeadCards() {
		File file = new File("save/deadcards.txt");

		// créer le fichier s'il n'existe pas
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		String content = "list dead cards:\n";
		for (int i=0;i<deadCardsList.size();i++) {
			Card card = deadCardsList.get(i);
			content = content + card.toString() + "\n";
		}
		content = content + "available dead cards:\n";
		for (int i=0;i<availableDeadCardsList.size();i++) {
			Card card = availableDeadCardsList.get(i);
			content = content + card.toString() + "\n";
		}
		content = content + "fin\n";
		
		FileWriter fw;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}



	public ButtonToSimulatorCard getButtonSimulatorCard() {
		return buttonSimulatorCard;
	}



	public void setButtonSimulatorCard(ButtonToSimulatorCard buttonSimulatorCard) {
		this.buttonSimulatorCard = buttonSimulatorCard;
	}



	public Integer getModulo1() {
		return modulo1;
	}



	public void setModulo1(int modulo1) {
		this.modulo1 = modulo1;
	}



	public Integer getMultiplier1() {
		return multiplier1;
	}



	public void setMultiplier1(int multiplier1) {
		this.multiplier1 = multiplier1;
	}



	public Integer getGlobalStrenght1() {
		return globalStrenght1;
	}



	public void setGlobalStrenght1(int globalStrenght1) {
		this.globalStrenght1 = globalStrenght1;
	}



	public Integer getRarityStrenght1() {
		return rarityStrenght1;
	}



	public void setRarityStrenght1(int rarityStrenght1) {
		this.rarityStrenght1 = rarityStrenght1;
	}



	public String getTypecards1() {
		return typecards1;
	}



	public void setTypecards1(String typecards1) {
		this.typecards1 = typecards1;
	}



	public Integer getLifePointsP1() {
		return lifePointsP1;
	}



	public void setLifePointsP1(int lp1) {
		lifePointsP1 = lp1;
	}



	public List<Card> getMainDeck1() {
		return mainDeck1;
	}



	public void setMainDeck1(List<Card> mainDeck1) {
		this.mainDeck1 = mainDeck1;
	}



	public List<Card> getSourceDeck1() {
		return sourceDeck1;
	}



	public void setSourceDeck1(List<Card> sourceDeck1) {
		this.sourceDeck1 = sourceDeck1;
	}



	public Integer getModulo2() {
		return modulo2;
	}



	public void setModulo2(int modulo2) {
		this.modulo2 = modulo2;
	}



	public Integer getMultiplier2() {
		return multiplier2;
	}



	public void setMultiplier2(int multiplier2) {
		this.multiplier2 = multiplier2;
	}



	public Integer getGlobalStrenght2() {
		return globalStrenght2;
	}



	public void setGlobalStrenght2(int globalStrenght2) {
		this.globalStrenght2 = globalStrenght2;
	}



	public Integer getRarityStrenght2() {
		return rarityStrenght2;
	}



	public void setRarityStrenght2(int rarityStrenght2) {
		this.rarityStrenght2 = rarityStrenght2;
	}



	public String getTypecards2() {
		return typecards2;
	}



	public void setTypecards2(String typecards2) {
		this.typecards2 = typecards2;
	}



	public Integer getLifePointsP2() {
		return lifePointsP2;
	}



	public void setLifePointsP2(int lp2) {
		lifePointsP2 = lp2;
	}



	public List<Card> getMainDeck2() {
		return mainDeck2;
	}



	public void setMainDeck2(List<Card> mainDeck2) {
		this.mainDeck2 = mainDeck2;
	}



	public List<Card> getSourceDeck2() {
		return sourceDeck2;
	}



	public void setSourceDeck2(List<Card> sourceDeck2) {
		this.sourceDeck2 = sourceDeck2;
	}



	public ButtonToDuel getButtonTestDuel() {
		return buttonTestDuel;
	}



	public void setButtonTestDuel(ButtonToDuel buttonTestDuel) {
		this.buttonTestDuel = buttonTestDuel;
	}



	public ButtonToDuel getButtonTrueDuel() {
		return buttonTrueDuel;
	}



	public void setButtonTrueDuel(ButtonToDuel buttonTrueDuel) {
		this.buttonTrueDuel = buttonTrueDuel;
	}



	public ButtonToBuildDeck getButtonToBuildDeck() {
		return buttonToBuildDeck;
	}



	public void setButtonToBuildDeck(ButtonToBuildDeck buttonToBuildDeck) {
		this.buttonToBuildDeck = buttonToBuildDeck;
	}



	public Integer getNbMainCards1() {
		return nbMainCards1;
	}



	public void setNbMainCards1(int nbCards1) {
		this.nbMainCards1 = nbCards1;
	}



	public Integer getNbMainCards2() {
		return nbMainCards2;
	}



	public void setNbMainCards2(int nbCards2) {
		this.nbMainCards2 = nbCards2;
	}



	public ButtonToBoosterCard getButtonBoosterCard() {
		return buttonBoosterCard;
	}



	public void setButtonBoosterCard(ButtonToBoosterCard buttonBoosterCard) {
		this.buttonBoosterCard = buttonBoosterCard;
	}



	public ButtonToSeeDeck getButtonToSeeDeck() {
		return buttonToSeeDeck;
	}



	public void setButtonToSeeDeck(ButtonToSeeDeck buttonToSeeDeck) {
		this.buttonToSeeDeck = buttonToSeeDeck;
	}



	public ButtonToOptions getButtonOptions() {
		return buttonOptions;
	}



	public void setButtonOptions(ButtonToOptions buttonOptions) {
		this.buttonOptions = buttonOptions;
	}



	public Integer getNbSourceCards1() {
		return nbSourceCards1;
	}



	public void setNbSourceCards1(int nbSourceCards1) {
		this.nbSourceCards1 = nbSourceCards1;
	}



	public Integer getNbSourceCards2() {
		return nbSourceCards2;
	}



	public void setNbSourceCards2(int nbSourceCards2) {
		this.nbSourceCards2 = nbSourceCards2;
	}



	public Integer getNbSupCards2() {
		return nbSupCards2;
	}



	public void setNbSupCards2(Integer nbSupCards2) {
		this.nbSupCards2 = nbSupCards2;
	}



	public JCheckBox getCheckGreenMageP1() {
		return checkGreenMageP1;
	}



	public void setCheckGreenMageP1(JCheckBox checkGreenMageP1) {
		this.checkGreenMageP1 = checkGreenMageP1;
	}



	public JCheckBox getCheckOrangeMageP1() {
		return checkOrangeMageP1;
	}



	public void setCheckOrangeMageP1(JCheckBox checkOrangeMageP1) {
		this.checkOrangeMageP1 = checkOrangeMageP1;
	}



	public JCheckBox getCheckBlueMageP1() {
		return checkBlueMageP1;
	}



	public void setCheckBlueMageP1(JCheckBox checkBlueMageP1) {
		this.checkBlueMageP1 = checkBlueMageP1;
	}



	public JCheckBox getCheckGreenMageP2() {
		return checkGreenMageP2;
	}



	public void setCheckGreenMageP2(JCheckBox checkGreenMageP2) {
		this.checkGreenMageP2 = checkGreenMageP2;
	}



	public JCheckBox getCheckOrangeMageP2() {
		return checkOrangeMageP2;
	}



	public void setCheckOrangeMageP2(JCheckBox checkOrangeMageP2) {
		this.checkOrangeMageP2 = checkOrangeMageP2;
	}



	public JCheckBox getCheckBlueMageP2() {
		return checkBlueMageP2;
	}



	public void setCheckBlueMageP2(JCheckBox checkBlueMageP2) {
		this.checkBlueMageP2 = checkBlueMageP2;
	}



	public ButtonToSpecial getButtonSpecial() {
		return buttonSpecial;
	}



	public void setButtonSpecial(ButtonToSpecial buttonSpecial) {
		this.buttonSpecial = buttonSpecial;
	}



	public JTextArea getDifficultyP2() {
		return difficultyP2;
	}



	public void setDifficultyP2(JTextArea difficultyP2) {
		this.difficultyP2 = difficultyP2;
	}



	public List<Card> getDeadCardsList() {
		return deadCardsList;
	}



	public void setDeadCardsList(List<Card> deadCardsList) {
		this.deadCardsList = deadCardsList;
	}



	public List<Card> getAvailableDeadCardsList() {
		return availableDeadCardsList;
	}



	public void setAvailableDeadCardsList(List<Card> availableDeadCardsList) {
		this.availableDeadCardsList = availableDeadCardsList;
	}



	public ButtonToResetDeathCards getButtonToResetDeathCards() {
		return buttonToResetDeathCards;
	}



	public void setButtonToResetDeathCards(ButtonToResetDeathCards buttonToResetDeathCards) {
		this.buttonToResetDeathCards = buttonToResetDeathCards;
	}



	public ButtonToHelp getButtonHelp() {
		return buttonHelp;
	}



	public void setButtonHelp(ButtonToHelp buttonHelp) {
		this.buttonHelp = buttonHelp;
	}
	
	

}
