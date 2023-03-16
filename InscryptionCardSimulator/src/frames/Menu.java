package frames;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;

import cards.Card;
import frames.menubuttons.ButtonToBoosterCard;
import frames.menubuttons.ButtonToBuildDeck;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSeeDeck;
import frames.menubuttons.ButtonToSimulatorCard;

public class Menu extends JFrame {
	//types: beast robot undead
	//codes interdits: 1-1 (sauf pour P2 pour farmer),4-2,5-2,7-2,9-2,9-3,10-3,11-2,11-3,12-2,12-3
	
	//j1: 13-3(5), booster value = 2*4+1=9, vies: 1/1
	//crédits: 4
	private int modulo1 = 9;
	private int multiplier1 = 2;
	private int globalStrenght1 = 2;
	private int rarityStrenght1 = 1;
	private String typecards1 = "undead";
	private int PV1 = 5;
	private int nbCards1 = 7;
	private List<Card> mainDeck1;
	private List<Card> sourceDeck1;
	
	//j2: 3-1
	private int modulo2 = 7;
	private int multiplier2 = 3;
	private int globalStrenght2 = 1;
	private int rarityStrenght2 = 1;
	private String typecards2 = "beast";
	private int PV2 = 6;
	private int nbCards2 = 5;
	private List<Card> mainDeck2;
	private List<Card> sourceDeck2;
	
	private ButtonToDuel buttonTestDuel;
	private ButtonToDuel buttonTrueDuel;
	private ButtonToSimulatorCard buttonSimulatorCard;
	private ButtonToBoosterCard buttonBoosterCard;
	private ButtonToBuildDeck buttonToBuildDeck;
	private ButtonToSeeDeck buttonToSeeDeck;

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
		
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		JLabel labelParamP1 = new JLabel("Parameter P1");
		this.getContentPane().add(labelParamP1);
		labelParamP1.setBounds(100, 190, 300, 50);
		labelParamP1.setFont(font.deriveFont(Font.BOLD,24f));
		labelParamP1.setForeground(new Color(255, 255, 255));
		
		JLabel labelParamP2 = new JLabel("Parameter P2");
		this.getContentPane().add(labelParamP2);
		labelParamP2.setBounds(1000, 190, 300, 50);
		labelParamP2.setFont(font.deriveFont(Font.BOLD,24f));
		labelParamP2.setForeground(new Color(255, 255, 255));
		this.getContentPane().add(panelBackground);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new MenuControler(this);
	}
	
	private void setButtons() {
		buttonTrueDuel = new ButtonToDuel("True Duel");
		buttonTrueDuel.setBounds(600, 190, 100, 50);
		buttonTrueDuel.setForeground(new Color(255, 255, 255));
		buttonTestDuel = new ButtonToDuel("Test Duel");
		buttonTestDuel.setBounds(600, 300, 100, 50);
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
		this.repaint();
		this.revalidate();
	}



	public ButtonToSimulatorCard getButtonSimulatorCard() {
		return buttonSimulatorCard;
	}



	public void setButtonSimulatorCard(ButtonToSimulatorCard buttonSimulatorCard) {
		this.buttonSimulatorCard = buttonSimulatorCard;
	}



	public int getModulo1() {
		return modulo1;
	}



	public void setModulo1(int modulo1) {
		this.modulo1 = modulo1;
	}



	public int getMultiplier1() {
		return multiplier1;
	}



	public void setMultiplier1(int multiplier1) {
		this.multiplier1 = multiplier1;
	}



	public int getGlobalStrenght1() {
		return globalStrenght1;
	}



	public void setGlobalStrenght1(int globalStrenght1) {
		this.globalStrenght1 = globalStrenght1;
	}



	public int getRarityStrenght1() {
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



	public int getPV1() {
		return PV1;
	}



	public void setPV1(int pV1) {
		PV1 = pV1;
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



	public int getModulo2() {
		return modulo2;
	}



	public void setModulo2(int modulo2) {
		this.modulo2 = modulo2;
	}



	public int getMultiplier2() {
		return multiplier2;
	}



	public void setMultiplier2(int multiplier2) {
		this.multiplier2 = multiplier2;
	}



	public int getGlobalStrenght2() {
		return globalStrenght2;
	}



	public void setGlobalStrenght2(int globalStrenght2) {
		this.globalStrenght2 = globalStrenght2;
	}



	public int getRarityStrenght2() {
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



	public int getPV2() {
		return PV2;
	}



	public void setPV2(int pV2) {
		PV2 = pV2;
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



	public int getNbCards1() {
		return nbCards1;
	}



	public void setNbCards1(int nbCards1) {
		this.nbCards1 = nbCards1;
	}



	public int getNbCards2() {
		return nbCards2;
	}



	public void setNbCards2(int nbCards2) {
		this.nbCards2 = nbCards2;
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
	
	

}
