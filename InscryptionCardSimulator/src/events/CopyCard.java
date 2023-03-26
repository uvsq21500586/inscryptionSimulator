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
import javax.swing.JTextArea;

import cards.Card;
import cards.CardFactory;
import frames.Menu;
import frames.duelbuttons.CardPanel;

public class CopyCard  extends JFrame {
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private boolean attackBonus;
	private CardPanel result;
	
	public CopyCard(Menu menu) throws IOException, FontFormatException {
		super("Copy");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 950);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		for(int i=0;i<menu.getMainDeck1().size();i++) {
			if (!menu.getMainDeck1().get(i).getAppearance().contains("pelt")) {
				cardsPanelsMainDeck.add(new CardPanel(menu.getMainDeck1().get(i)));
				panel.add(cardsPanelsMainDeck.get(i));
				cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
			}
		}
		JLabel description = new JLabel("Choose a card to copy.");
		description.setBounds(50, 310, 600, 50);
		description.setFont(font.deriveFont(Font.BOLD,18f));
		panel.add(description);
		
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
		new CopyCardControler(this);
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

	public CardPanel getResult() {
		return result;
	}

	public void setResult(CardPanel result) {
		this.result = result;
	}
	
	

}
