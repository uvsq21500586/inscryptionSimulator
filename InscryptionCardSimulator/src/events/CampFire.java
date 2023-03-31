package events;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextArea;

import cards.Card;
import cards.CardFactory;
import frames.Menu;
import frames.duelbuttons.CardPanel;

public class CampFire  extends JFrame {
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private boolean attackBonus;
	private JTextArea chanceSuccess = new JTextArea("1");
	private JTextArea chanceDice = new JTextArea("2");
	private JLabel resultText = new JLabel("Success! Your card gain power or health!");
	private CardPanel result;
	
	public CampFire(Menu menu) throws IOException, FontFormatException {
		super("Camp Fire");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 950);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		for(int i=0;i<menu.getMainDeck1().size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(menu.getMainDeck1().get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
		}
		attackBonus = r.nextInt(2) > 0;
		
		if (attackBonus) {
			JLabel description = new JLabel("Choose a card to add 1 attack.");
			description.setBounds(50, 310, 600, 50);
			description.setFont(font.deriveFont(Font.BOLD,18f));
			panel.add(description);
		} else {
			JLabel description = new JLabel("Choose a card to add 2 hp.");
			description.setBounds(50, 310, 600, 50);
			description.setFont(font.deriveFont(Font.BOLD,18f));
			panel.add(description);
		}
		JLabel chanceSuccessLabel = new JLabel("Chances success:");
		chanceSuccessLabel.setBounds(20, 410, 200, 50);
		chanceSuccessLabel.setFont(font.deriveFont(Font.BOLD,14f));
		panel.add(chanceSuccessLabel);
		chanceSuccess.setBounds(250, 410, 100, 50);
		chanceSuccess.setFont(font.deriveFont(Font.BOLD,18f));
		panel.add(chanceSuccess);
		JLabel chanceDiceLabel = new JLabel("Dice:");
		chanceDiceLabel.setBounds(100, 510, 120, 50);
		chanceDiceLabel.setFont(font.deriveFont(Font.BOLD,18f));
		panel.add(chanceDiceLabel);
		chanceDice.setBounds(250, 510, 100, 50);
		chanceDice.setFont(font.deriveFont(Font.BOLD,18f));
		panel.add(chanceDice);
		
		resultText.setBounds(600, 400, 450, 50);
		resultText.setFont(font.deriveFont(Font.BOLD,16f));
		panel.add(resultText);
		resultText.setForeground(new Color(0,128,0));
		resultText.setVisible(false);
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(500,400,100,50);
		buttonValidate.setEnabled(false);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		result = new CardPanel();
		panel.add(result);
		result.setBounds(550,450,200,300);
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new CampFireControler(this);
	}
	
	public CampFire(List<Card> boosterMain) throws IOException, FontFormatException {
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

	public boolean isAttackBonus() {
		return attackBonus;
	}

	public void setAttackBonus(boolean attackBonus) {
		this.attackBonus = attackBonus;
	}

	public JTextArea getChanceSuccess() {
		return chanceSuccess;
	}

	public void setChanceSuccess(JTextArea chanceSuccess) {
		this.chanceSuccess = chanceSuccess;
	}

	public JTextArea getChanceDice() {
		return chanceDice;
	}

	public void setChanceDice(JTextArea chanceDice) {
		this.chanceDice = chanceDice;
	}

	public CardPanel getResult() {
		return result;
	}

	public void setResult(CardPanel result) {
		this.result = result;
	}

	public JLabel getResultText() {
		return resultText;
	}

	public void setResultText(JLabel resultText) {
		this.resultText = resultText;
	}
	
	

}
