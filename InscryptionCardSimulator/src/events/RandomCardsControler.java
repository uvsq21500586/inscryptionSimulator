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

public class RandomCardsControler implements ActionListener,MouseListener {
	private RandomCards randomCards;
	private CardPanel selectedCard = null;
	
	public RandomCardsControler(RandomCards randomCards) {
		this.randomCards = randomCards;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<3;i++) {
			randomCards.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		randomCards.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel) {
			selectedCard = (CardPanel) e.getSource();
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			int index = randomCards.getCardsPanelsMainDeck().indexOf(selectedCard);
			for (int i=0;i<3;i++) {
				randomCards.getCardsPanelsMainDeck().get(i).getSelected().setVisible(i == index);
			}
			randomCards.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton) {
			randomCards.getMenu().getMainDeck1().add(selectedCard.getCard());
			randomCards.getMenu().saveDeck(randomCards.getMenu().getMainDeck1(), randomCards.getMenu().getSourceDeck1());
			randomCards.dispose();
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
