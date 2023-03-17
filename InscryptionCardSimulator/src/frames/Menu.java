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
import frames.menubuttons.ButtonToOptions;
import frames.menubuttons.ButtonToSeeDeck;
import frames.menubuttons.ButtonToSimulatorCard;

public class Menu extends JFrame {
	//types: beast robot undead
	//codes interdits: 1-1 (sauf pour P2 pour farmer),4-2,5-2,7-2,9-2,9-3,10-3,11-2,11-3,12-2,12-3,
	//14-1,14-3,15-1
	
	//j1: 16-2(13)?, booster value = 2*4+1=9, vies: 1/1
	//crédits: 3
	//j2: 10-1
	
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
	
	private ButtonToDuel buttonTestDuel;
	private ButtonToDuel buttonTrueDuel;
	private ButtonToSimulatorCard buttonSimulatorCard;
	private ButtonToBoosterCard buttonBoosterCard;
	private ButtonToBuildDeck buttonToBuildDeck;
	private ButtonToSeeDeck buttonToSeeDeck;
	private ButtonToOptions buttonOptions;

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
		buttonOptions = new ButtonToOptions();
		buttonOptions.setBounds(100, 520, 150, 50);
		buttonOptions.setForeground(new Color(255, 255, 255));
		buttonOptions.add(new JLabel("Options"));
		this.repaint();
		this.revalidate();
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
	
	

}
