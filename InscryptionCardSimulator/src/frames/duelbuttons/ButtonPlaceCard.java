package frames.duelbuttons;

import java.awt.Dimension;

import javax.swing.JButton;

import cards.CardPanel;

public class ButtonPlaceCard extends JButton {
	CardPanel cardPanel;
	
	public ButtonPlaceCard() {
		super();
		cardPanel = null;
	    this.setPreferredSize(new Dimension(200,300));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}
	
	public ButtonPlaceCard(ButtonPlaceCard buttonPlaceCard) {
		super();
		this.cardPanel = buttonPlaceCard.cardPanel;
	    this.setPreferredSize(new Dimension(200,300));
		this.setOpaque(false);
	    this.repaint();
		this.revalidate();
	}

	public CardPanel getCardPanel() {
		return cardPanel;
	}

	public void setCardPanel(CardPanel cardPanel) {
		this.cardPanel = cardPanel;
	}
	
	
}
