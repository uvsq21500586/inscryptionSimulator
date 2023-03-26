package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cards.Card;
import frames.duelbuttons.CardPanel;

public class Player1Deck  extends JFrame {
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	List<CardPanel> cardsPanelsSourceDeck = new ArrayList<>();
	
	public Player1Deck(Menu menu) throws IOException, FontFormatException {
		super("Deck view");
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		
		for(int i=0;i<menu.getMainDeck1().size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(menu.getMainDeck1().get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
		}
		for(int i=0;i<menu.getSourceDeck1().size();i++) {
			cardsPanelsSourceDeck.add(new CardPanel(menu.getSourceDeck1().get(i)));
			panel.add(cardsPanelsSourceDeck.get(i));
			cardsPanelsSourceDeck.get(i).setBounds(200*i, 320, 200, 300);
		}
		panel.setBounds(0, 0, 200 *  Math.max(cardsPanelsMainDeck.size(), cardsPanelsSourceDeck.size()), 650);
		panel.setPreferredSize(new Dimension(200 * Math.max(cardsPanelsMainDeck.size(), cardsPanelsSourceDeck.size()), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	public Player1Deck(List<Card> boosterMain, List<Card> boosterSource) throws IOException, FontFormatException {
		super("Booster view");
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(boosterMain.get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
		}
		for(int i=0;i<boosterSource.size();i++) {
			cardsPanelsSourceDeck.add(new CardPanel(boosterSource.get(i)));
			panel.add(cardsPanelsSourceDeck.get(i));
			cardsPanelsSourceDeck.get(i).setBounds(200*i, 320, 200, 300);
		}
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

}
