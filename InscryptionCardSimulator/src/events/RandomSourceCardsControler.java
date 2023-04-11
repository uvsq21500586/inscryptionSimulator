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
import cards.CardPanel;
import cards.RobotCard;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;

public class RandomSourceCardsControler implements ActionListener,MouseListener {
	private RandomSourceCards randomCards;
	private CardPanel selectedNewSourceCard = null;
	private CardPanel selectedDeckSourceCard = null;
	
	public RandomSourceCardsControler(RandomSourceCards randomCards) {
		this.randomCards = randomCards;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<randomCards.getCardsPanelsNewSourceDeck().size();i++) {
			randomCards.getCardsPanelsNewSourceDeck().get(i).addMouseListener(this);
		}
		for (int i=0;i<randomCards.getCardsPanelsSourceDeck().size();i++) {
			randomCards.getCardsPanelsSourceDeck().get(i).addMouseListener(this);
		}
		randomCards.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel) {
			CardPanel selectedCard = (CardPanel) e.getSource();
			if (randomCards.getCardsPanelsNewSourceDeck().contains(selectedCard)) {
				selectedNewSourceCard = selectedCard;
				for (int i=0;i<randomCards.getCardsPanelsNewSourceDeck().size();i++) {
					randomCards.getCardsPanelsNewSourceDeck().get(i).getSelected().setVisible(false);
				}
				selectedNewSourceCard.getSelected().setVisible(true);
				randomCards.getButtonValidate().setEnabled(true);
			} else {
				selectedDeckSourceCard = selectedCard;
				for (int i=0;i<randomCards.getCardsPanelsSourceDeck().size();i++) {
					randomCards.getCardsPanelsSourceDeck().get(i).getSelected().setVisible(false);
				}
				selectedDeckSourceCard.getSelected().setVisible(true);
				System.out.println(randomCards.getMenu().getSourceDeck1().indexOf(selectedDeckSourceCard.getCard()));
				System.out.println(selectedDeckSourceCard.getCard().equals(randomCards.getMenu().getSourceDeck1().get(1)));
			}
			
		}
		
		if (e.getSource() instanceof JButton) {
			randomCards.getMenu().getSourceDeck1().add(selectedNewSourceCard.getCard());
			if (selectedDeckSourceCard != null) {
				System.out.println(randomCards.getMenu().getSourceDeck1().indexOf(selectedDeckSourceCard.getCard()));
				randomCards.getMenu().getSourceDeck1().remove(selectedDeckSourceCard.getCard());
			}
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
