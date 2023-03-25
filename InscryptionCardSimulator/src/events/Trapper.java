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
import frames.Menu;
import frames.duelbuttons.CardPanel;

public class Trapper extends JFrame {
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	
	public Trapper(Menu menu) throws IOException, FontFormatException {
		super("Trapper");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		List<Card> boosterMain = new ArrayList<>();
		boosterMain.add(new BeastCard("rabbit_pelt", "blood", 0, 1, 0, new ArrayList<>(), 0, true));
		boosterMain.add(new BeastCard("wolf_pelt", "blood", 0, 2, 0, new ArrayList<>(), 0, true));
		boosterMain.add(new BeastCard("golden_pelt", "blood", 0, 3, 0, new ArrayList<>(), 0, true));
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(boosterMain.get(i)));
			boosterMain.get(i).setSacrificiable(false);
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
		}
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(500,400,100,50);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new TrapperControler(this);
	}
	
	public Trapper(List<Card> boosterMain) throws IOException, FontFormatException {
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
	
	

}
