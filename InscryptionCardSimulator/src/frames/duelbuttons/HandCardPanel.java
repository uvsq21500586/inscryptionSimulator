package frames.duelbuttons;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import cards.BeastCard;
import cards.Card;
import cards.CardPanel;

public class HandCardPanel extends JPanel {
	public List<CardPanel> cardsPanels = new ArrayList<>();
	
	/*public HandCardPanel(List<Card> cards) throws IOException, FontFormatException {
		super();
		setLayout(null);
		this.cards = cards;
	    this.setPreferredSize(new Dimension(200,1000));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
		
	}*/
	
	public HandCardPanel(List<CardPanel> cardsPanels) throws IOException, FontFormatException {
		super();
		setLayout(null);
		this.cardsPanels = cardsPanels;
	    this.setPreferredSize(new Dimension(200,1000));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
		
	}
	
	
}
