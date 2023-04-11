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

import cards.BeastCard;
import cards.Card;
import cards.CardFactory;
import cards.CardPanel;
import frames.Menu;

public class BoulderCards  extends JFrame {
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	List<CardPanel> cardsPanelsBoulders = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	
	public BoulderCards(Menu menu, Integer nbchoices) throws IOException, FontFormatException {
		super("Boulders");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		List<Card> boosterMain = new ArrayList<>();
		int idGoldenPelt = r.nextInt(3+nbchoices);
		for(int i=0;i<3+nbchoices;i++) {
			cardsPanelsBoulders.add(new CardPanel(new BeastCard("boulder", "blood", 0, 5, 0, new ArrayList<>(), 0, false)));
			if (i == idGoldenPelt) {
				boosterMain.add(new BeastCard("golden_pelt", "blood", 0, 3, 0, new ArrayList<>(), 0, false));
				boosterMain.get(i).setSacrificiable(false);
			} else {
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
			
		}
		
		
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(boosterMain.get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
			cardsPanelsMainDeck.get(i).setVisible(false);
			panel.add(cardsPanelsBoulders.get(i));
			cardsPanelsBoulders.get(i).setBounds(200*i, 0, 200, 300);
			
		}
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(500,400,100,50);
		buttonValidate.setEnabled(false);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new BoulderCardsControler(this);
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

	public List<CardPanel> getCardsPanelsBoulders() {
		return cardsPanelsBoulders;
	}

	public void setCardsPanelsBoulders(List<CardPanel> cardsPanelsBoulders) {
		this.cardsPanelsBoulders = cardsPanelsBoulders;
	}
	
	

}
