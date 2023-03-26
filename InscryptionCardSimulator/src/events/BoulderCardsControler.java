package events;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;

import cards.BeastCard;
import cards.Card;
import cards.CardFactory;
import cards.RobotCard;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;
import frames.duelbuttons.CardPanel;

public class BoulderCardsControler implements ActionListener,MouseListener {
	private BoulderCards boulderCards;
	private CardPanel selectedCard = null;
	
	public BoulderCardsControler(BoulderCards boulderCards) {
		this.boulderCards = boulderCards;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<3;i++) {
			boulderCards.getCardsPanelsBoulders().get(i).addMouseListener(this);
		}
		boulderCards.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel && selectedCard == null) {
			int idselectedCard = boulderCards.getCardsPanelsBoulders().indexOf((CardPanel) e.getSource());
			selectedCard = boulderCards.getCardsPanelsMainDeck().get(idselectedCard);
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			boulderCards.getCardsPanelsBoulders().get(idselectedCard).setVisible(false);
			selectedCard.setVisible(true);
			boulderCards.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton) {
			boulderCards.getMenu().getMainDeck1().add(selectedCard.getCard());
			boulderCards.getMenu().saveDeck(boulderCards.getMenu().getMainDeck1(), boulderCards.getMenu().getSourceDeck1());
			boulderCards.dispose();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
