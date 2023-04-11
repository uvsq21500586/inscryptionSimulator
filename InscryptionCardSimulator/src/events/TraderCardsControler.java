package events;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Optional;

import javax.swing.JButton;

import cards.BeastCard;
import cards.Card;
import cards.CardFactory;
import cards.CardPanel;
import cards.RobotCard;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;

public class TraderCardsControler implements ActionListener,MouseListener {
	private TraderCards traderCards;
	private CardPanel selectedCard = null;
	
	public TraderCardsControler(TraderCards traderCards) {
		this.traderCards = traderCards;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<8;i++) {
			traderCards.getCardsPanelsMainDeck().get(i).addMouseListener(this);
			traderCards.getCardsPanelsMainDeckUpgraded().get(i).addMouseListener(this);
		}
		for (int i=0;i<4;i++) {
			traderCards.getCardsPanelsMainDeckUpgraded2().get(i).addMouseListener(this);
		}
		traderCards.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel && traderCards.getNbPelts()>0) {
			selectedCard = (CardPanel) e.getSource();
			traderCards.getMenu().getMainDeck1().add(selectedCard.getCard());
			traderCards.setNbPelts(traderCards.getNbPelts()-1);
			
			if (traderCards.getNbPeltsLabel().getText().contains("rabbit")) {
				Optional<Card> pelt = traderCards.getMenu().getMainDeck1().stream()
						.filter(card -> card.getAppearance().equals("rabbit_pelt")).findAny();
				if (pelt.isPresent()) {
					traderCards.getMenu().getMainDeck1().remove(pelt.get());
				}
				traderCards.getNbPeltsLabel().setText("Nb rabbit pelts: " + traderCards.getNbPelts());
			} else if (traderCards.getNbPeltsLabel().getText().contains("wolf")) {
				Optional<Card> pelt = traderCards.getMenu().getMainDeck1().stream()
						.filter(card -> card.getAppearance().equals("wolf_pelt")).findAny();
				if (pelt.isPresent()) {
					traderCards.getMenu().getMainDeck1().remove(pelt.get());
				}
				traderCards.getNbPeltsLabel().setText("Nb wolf pelts: " + traderCards.getNbPelts());
			} else {
				Optional<Card> pelt = traderCards.getMenu().getMainDeck1().stream()
						.filter(card -> card.getAppearance().equals("golden_pelt")).findAny();
				if (pelt.isPresent()) {
					traderCards.getMenu().getMainDeck1().remove(pelt.get());
				}
				traderCards.getNbPeltsLabel().setText("Nb golden pelts: " + traderCards.getNbPelts());
			}
			
			traderCards.getMenu().saveDeck(traderCards.getMenu().getMainDeck1(), traderCards.getMenu().getSourceDeck1());
			selectedCard.setVisible(false);
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			/*int index = traderCards.getCardsPanelsMainDeck().indexOf(selectedCard);
			for (int i=0;i<3;i++) {
				traderCards.getCardsPanelsMainDeck().get(i).getSelected().setVisible(i == index);
			}
			traderCards.getButtonValidate().setEnabled(true);*/
		}
		
		if (e.getSource() instanceof JButton) {
			if (traderCards.getNbPeltsLabel().getText().contains("rabbit")) {
				for (int i=0;i<8;i++) {
					traderCards.getCardsPanelsMainDeck().get(i).setVisible(false);
					traderCards.getCardsPanelsMainDeckUpgraded().get(i).setVisible(true);
				}
				int nbPelts = 0;
				for (int i=0;i<traderCards.getMenu().getMainDeck1().size();i++) {
					if (traderCards.getMenu().getMainDeck1().get(i).getAppearance().equals("wolf_pelt")) {
						nbPelts++;
					}
				}
				traderCards.setNbPelts(nbPelts);
				traderCards.getNbPeltsLabel().setText("Nb wolf pelts: " + traderCards.getNbPelts());
			} else if (traderCards.getNbPeltsLabel().getText().contains("wolf")) {
				for (int i=0;i<8;i++) {
					traderCards.getCardsPanelsMainDeckUpgraded().get(i).setVisible(false);
				}
				for (int i=0;i<4;i++) {
					traderCards.getCardsPanelsMainDeckUpgraded2().get(i).setVisible(true);
				}
				int nbPelts = 0;
				for (int i=0;i<traderCards.getMenu().getMainDeck1().size();i++) {
					if (traderCards.getMenu().getMainDeck1().get(i).getAppearance().equals("golden_pelt")) {
						nbPelts++;
					}
				}
				traderCards.setNbPelts(nbPelts);
				traderCards.getNbPeltsLabel().setText("Nb golden pelts: " + traderCards.getNbPelts());
			} else {
				traderCards.dispose();
			}
			
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
