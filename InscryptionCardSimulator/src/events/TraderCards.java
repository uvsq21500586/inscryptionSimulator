package events;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cards.Card;
import cards.CardFactory;
import cards.CardPanel;
import frames.Menu;

public class TraderCards  extends JFrame {
	Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	List<CardPanel> cardsPanelsMainDeckUpgraded = new ArrayList<>();
	List<CardPanel> cardsPanelsMainDeckUpgraded2 = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private JLabel nbPeltsLabel;
	private int nbPelts;
	
	public TraderCards(Menu menu, int statsBonus) throws IOException, FontFormatException {
		super("Trader");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		List<Card> boosterMain = new ArrayList<>();
		for(int i=0;i<8;i++) {
			Card card1 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1());
			Card card2 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1());
			if (card2.getRarity() < card1.getRarity()) {
				boosterMain.add(card2);
			} else {
				boosterMain.add(card1);
			}
		}
		List<Card> boosterMain2 = new ArrayList<>();
		for(int i=0;i<8;i++) {
			Card card1 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1(),statsBonus,false);
			Card card2 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1(),statsBonus,false);
			if (card2.getRarity() < card1.getRarity()) {
				boosterMain2.add(card2);
			} else {
				boosterMain2.add(card1);
			}
		}
		List<Card> boosterMain3 = new ArrayList<>();
		for(int i=0;i<4;i++) {
			Card card1 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1(),2*statsBonus,false);
			Card card2 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1(),2*statsBonus,false);
			if (card1.getRarity() == 0 || (card2.getRarity()>0 && card2.getRarity() < card1.getRarity())) {
				boosterMain3.add(card2);
			} else {
				boosterMain3.add(card1);
			}
		}
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(boosterMain.get(i)));
			cardsPanelsMainDeckUpgraded.add(new CardPanel(boosterMain2.get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
			panel.add(cardsPanelsMainDeckUpgraded.get(i));
			cardsPanelsMainDeckUpgraded.get(i).setVisible(false);
			cardsPanelsMainDeckUpgraded.get(i).setBounds(200*i, 0, 200, 300);
			
		}
		for(int i=0;i<boosterMain3.size();i++) {
			cardsPanelsMainDeckUpgraded2.add(new CardPanel(boosterMain3.get(i)));
			panel.add(cardsPanelsMainDeckUpgraded2.get(i));
			cardsPanelsMainDeckUpgraded2.get(i).setBounds(200*i, 0, 200, 300);
			cardsPanelsMainDeckUpgraded2.get(i).setVisible(false);
		}
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(500,500,100,50);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		//nb pelts
		nbPelts = 0;
		for (int i=0;i<menu.getMainDeck1().size();i++) {
			if (menu.getMainDeck1().get(i).getAppearance().equals("rabbit_pelt")) {
				nbPelts++;
			}
		}
		
		nbPeltsLabel = new JLabel("Nb rabbit pelts: " + nbPelts);
		panel.add(nbPeltsLabel);
		nbPeltsLabel.setBounds(300,400,400,50);
		nbPeltsLabel.setFont(font.deriveFont(Font.BOLD,18f));
		
		if (nbPelts == 0) {
			for (int i=0;i<8;i++) {
				cardsPanelsMainDeck.get(i).setEnabled(false);
			}
		}
		
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new TraderCardsControler(this);
	}
	
	public TraderCards(List<Card> boosterMain) throws IOException, FontFormatException {
		super("Random cards");
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(boosterMain.get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
		}
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	public List<CardPanel> getCardsPanelsMainDeck() {
		return cardsPanelsMainDeck;
	}

	public void setCardsPanelsMainDeck(List<CardPanel> cardsPanelsMainDeck) {
		this.cardsPanelsMainDeck = cardsPanelsMainDeck;
	}

	public JButton getButtonValidate() {
		return buttonValidate;
	}

	public void setButtonValidate(JButton buttonValidate) {
		this.buttonValidate = buttonValidate;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<CardPanel> getCardsPanelsMainDeckUpgraded() {
		return cardsPanelsMainDeckUpgraded;
	}

	public void setCardsPanelsMainDeckUpgraded(List<CardPanel> cardsPanelsMainDeckUpgraded) {
		this.cardsPanelsMainDeckUpgraded = cardsPanelsMainDeckUpgraded;
	}

	public List<CardPanel> getCardsPanelsMainDeckUpgraded2() {
		return cardsPanelsMainDeckUpgraded2;
	}

	public void setCardsPanelsMainDeckUpgraded2(List<CardPanel> cardsPanelsMainDeckUpgraded2) {
		this.cardsPanelsMainDeckUpgraded2 = cardsPanelsMainDeckUpgraded2;
	}

	public JLabel getNbPeltsLabel() {
		return nbPeltsLabel;
	}

	public void setNbPeltsLabel(JLabel nbPeltsLabel) {
		this.nbPeltsLabel = nbPeltsLabel;
	}

	public int getNbPelts() {
		return nbPelts;
	}

	public void setNbPelts(int nbPelts) {
		this.nbPelts = nbPelts;
	}
	
	

}
