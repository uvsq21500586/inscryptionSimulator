package events;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class BuildDeathCard  extends JFrame {
	public final int nbSkinsDeaths = 1;
	List<CardPanel> cardsPanelsCardsTypeAndCost = new ArrayList<>();
	List<CardPanel> cardsPanelsCardsHpAttack = new ArrayList<>();
	List<CardPanel> cardsPanelsCardsEffects = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private CardPanel resultCard;
	
	
	public BuildDeathCard(Menu menu, Integer nbChoices) throws IOException, FontFormatException {
		super("Build death cards");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 950);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		
		
		resultCard = new CardPanel();
		panel.add(resultCard);
		resultCard.setBounds(300,930,200,300);
		
		List<Card> drawCardsMainDeck1 = new ArrayList<>();
		List<Card> drawCardsMainDeck2 = new ArrayList<>();
		List<Card> drawCardsMainDeck3 = new ArrayList<>();
		
		for(int i=0;i<nbChoices;i++) {
			Card card = menu.getMainDeck1().get(r.nextInt( menu.getMainDeck1().size()));
			if (!drawCardsMainDeck1.contains(card)) {
				drawCardsMainDeck1.add(card);
			}
			Card card2 = menu.getMainDeck1().get(r.nextInt( menu.getMainDeck1().size()));
			if (!drawCardsMainDeck2.contains(card2)) {
				drawCardsMainDeck2.add(card2);
			}
			Card card3 = menu.getMainDeck1().get(r.nextInt( menu.getMainDeck1().size()));
			if (!drawCardsMainDeck3.contains(card3)) {
				drawCardsMainDeck3.add(card3);
			}
		}
		
		
		for(int i=0;i<drawCardsMainDeck1.size();i++) {
			cardsPanelsCardsTypeAndCost.add(new CardPanel(drawCardsMainDeck1.get(i)));
			panel.add(cardsPanelsCardsTypeAndCost.get(i));
			cardsPanelsCardsTypeAndCost.get(i).setBounds(200*i, 0, 200, 300);
		}
		
		for(int i=0;i<drawCardsMainDeck2.size();i++) {
			cardsPanelsCardsHpAttack.add(new CardPanel(drawCardsMainDeck2.get(i)));
			panel.add(cardsPanelsCardsHpAttack.get(i));
			cardsPanelsCardsHpAttack.get(i).setBounds(200*i, 310, 200, 300);
			cardsPanelsCardsHpAttack.get(i).setVisible(false);
		}
		
		for(int i=0;i<drawCardsMainDeck3.size();i++) {
			cardsPanelsCardsEffects.add(new CardPanel(drawCardsMainDeck3.get(i)));
			panel.add(cardsPanelsCardsEffects.get(i));
			cardsPanelsCardsEffects.get(i).setBounds(200*i, 620, 200, 300);
			cardsPanelsCardsEffects.get(i).setVisible(false);
		}
		
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(150,930,100,50);
		buttonValidate.setEnabled(false);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsCardsTypeAndCost.size(), 1300);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsCardsTypeAndCost.size(), 1300));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new BuildDeathCardControler(this);
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

	public CardPanel getResultCard() {
		return resultCard;
	}

	public void setResultCard(CardPanel resultCard) {
		this.resultCard = resultCard;
	}

	public List<CardPanel> getCardsPanelsCardsTypeAndCost() {
		return cardsPanelsCardsTypeAndCost;
	}

	public void setCardsPanelsCardsTypeAndCost(List<CardPanel> cardsPanelsCardsTypeAndCost) {
		this.cardsPanelsCardsTypeAndCost = cardsPanelsCardsTypeAndCost;
	}

	public List<CardPanel> getCardsPanelsCardsHpAttack() {
		return cardsPanelsCardsHpAttack;
	}

	public void setCardsPanelsCardsHpAttack(List<CardPanel> cardsPanelsCardsHpAttack) {
		this.cardsPanelsCardsHpAttack = cardsPanelsCardsHpAttack;
	}

	public List<CardPanel> getCardsPanelsCardsEffects() {
		return cardsPanelsCardsEffects;
	}

	public void setCardsPanelsCardsEffects(List<CardPanel> cardsPanelsCardsEffects) {
		this.cardsPanelsCardsEffects = cardsPanelsCardsEffects;
	}
	
	
	

}
