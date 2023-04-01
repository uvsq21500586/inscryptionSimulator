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

import cards.BeastCard;
import cards.Card;
import cards.CardFactory;
import frames.Menu;
import frames.duelbuttons.CardPanel;

public class Trapper extends JFrame {
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private JLabel nbRabbitPeltBought = new JLabel("nb rabbit pelts bought: 0");
	private JLabel nbWolfPeltBought = new JLabel("nb wolf pelts bought: 0");
	private JLabel nbGoldenPeltBought = new JLabel("nb golden pelts bought: 0");
	private int nbRabbitPeltBoughtCount = 0;
	private int nbWolfPeltBoughtCount = 0;
	private int nbGoldenPeltBoughtCount = 0;
	
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
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		nbRabbitPeltBought.setFont(font.deriveFont(Font.BOLD,16f));
		nbWolfPeltBought.setFont(font.deriveFont(Font.BOLD,16f));
		nbGoldenPeltBought.setFont(font.deriveFont(Font.BOLD,16f));
		nbRabbitPeltBought.setBounds(50,400,300,50);
		nbWolfPeltBought.setBounds(50,460,300,50);
		nbGoldenPeltBought.setBounds(50,520,300,50);
		panel.add(nbRabbitPeltBought);
		panel.add(nbWolfPeltBought);
		panel.add(nbGoldenPeltBought);
		
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

	public JLabel getNbRabbitPeltBought() {
		return nbRabbitPeltBought;
	}

	public void setNbRabbitPeltBought(JLabel nbRabbitPeltBought) {
		this.nbRabbitPeltBought = nbRabbitPeltBought;
	}

	public JLabel getNbWolfPeltBought() {
		return nbWolfPeltBought;
	}

	public void setNbWolfPeltBought(JLabel nbWolfPeltBought) {
		this.nbWolfPeltBought = nbWolfPeltBought;
	}

	public JLabel getNbGoldenPeltBought() {
		return nbGoldenPeltBought;
	}

	public void setNbGoldenPeltBought(JLabel nbGoldenPeltBought) {
		this.nbGoldenPeltBought = nbGoldenPeltBought;
	}

	public int getNbRabbitPeltBoughtCount() {
		return nbRabbitPeltBoughtCount;
	}

	public void setNbRabbitPeltBoughtCount(int nbRabbitPeltBoughtCount) {
		this.nbRabbitPeltBoughtCount = nbRabbitPeltBoughtCount;
	}

	public int getNbWolfPeltBoughtCount() {
		return nbWolfPeltBoughtCount;
	}

	public void setNbWolfPeltBoughtCount(int nbWolfPeltBoughtCount) {
		this.nbWolfPeltBoughtCount = nbWolfPeltBoughtCount;
	}

	public int getNbGoldenPeltBoughtCount() {
		return nbGoldenPeltBoughtCount;
	}

	public void setNbGoldenPeltBoughtCount(int nbGoldenPeltBoughtCount) {
		this.nbGoldenPeltBoughtCount = nbGoldenPeltBoughtCount;
	}
	
	

}
