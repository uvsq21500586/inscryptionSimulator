package events;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontFormatException;
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
import frames.Menu;
import frames.duelbuttons.CardPanel;

public class RandomSourceCards  extends JFrame {
	List<CardPanel> cardsPanelsNewSourceDeck = new ArrayList<>();
	List<CardPanel> cardsPanelsSourceDeck = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	
	public RandomSourceCards(Menu menu, Integer nbchoices) throws IOException, FontFormatException {
		super("Random cards");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 950);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		List<Card> boosterMain = new ArrayList<>();
		for(int i=0;i<nbchoices;i++) {
			Card card1 = CardFactory.sourceCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1());
			Card card2 = CardFactory.sourceCard(
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
		
		
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsNewSourceDeck.add(new CardPanel(boosterMain.get(i)));
			panel.add(cardsPanelsNewSourceDeck.get(i));
			cardsPanelsNewSourceDeck.get(i).setBounds(200*i, 0, 200, 300);
		}
		
		for(int i=0;i<menu.getSourceDeck1().size();i++) {
			cardsPanelsSourceDeck.add(new CardPanel(menu.getSourceDeck1().get(i)));
			panel.add(cardsPanelsSourceDeck.get(i));
			cardsPanelsSourceDeck.get(i).setBounds(200*i, 400, 200, 300);
		}
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(500,310,100,50);
		buttonValidate.setEnabled(false);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsSourceDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsSourceDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new RandomSourceCardsControler(this);
	}
	
	public RandomSourceCards(List<Card> boosterMain) throws IOException, FontFormatException {
		super("Random cards");
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsSourceDeck.add(new CardPanel(boosterMain.get(i)));
			panel.add(cardsPanelsSourceDeck.get(i));
			cardsPanelsSourceDeck.get(i).setBounds(200*i, 0, 200, 300);
		}
		panel.setBounds(0, 0, 200 * cardsPanelsSourceDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsSourceDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
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

	public List<CardPanel> getCardsPanelsNewSourceDeck() {
		return cardsPanelsNewSourceDeck;
	}

	public void setCardsPanelsNewSourceDeck(List<CardPanel> cardsPanelsNewSourceDeck) {
		this.cardsPanelsNewSourceDeck = cardsPanelsNewSourceDeck;
	}

	public List<CardPanel> getCardsPanelsSourceDeck() {
		return cardsPanelsSourceDeck;
	}

	public void setCardsPanelsSourceDeck(List<CardPanel> cardsPanelsSourceDeck) {
		this.cardsPanelsSourceDeck = cardsPanelsSourceDeck;
	}
	
	

}
