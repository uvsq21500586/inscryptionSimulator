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

public class TrapperControler implements ActionListener,MouseListener {
	private Trapper trapperCards;
	
	public TrapperControler(Trapper trapper) {
		this.trapperCards = trapper;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<3;i++) {
			trapper.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		trapper.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel) {
			CardPanel selectedCard = (CardPanel) e.getSource();
			String name = selectedCard.getCard().getAppearance();
			if (name.contains("rabbit")) {
				trapperCards.setNbRabbitPeltBoughtCount(trapperCards.getNbRabbitPeltBoughtCount()+1);
				trapperCards.getNbRabbitPeltBought().setText("nb rabbit pelts bought: " + trapperCards.getNbRabbitPeltBoughtCount());
			}
			if (name.contains("wolf")) {
				trapperCards.setNbWolfPeltBoughtCount(trapperCards.getNbWolfPeltBoughtCount()+1);
				trapperCards.getNbWolfPeltBought().setText("nb wolf pelts bought: " + trapperCards.getNbWolfPeltBoughtCount());
			}
			if (name.contains("golden")) {
				trapperCards.setNbGoldenPeltBoughtCount(trapperCards.getNbGoldenPeltBoughtCount()+1);
				trapperCards.getNbGoldenPeltBought().setText("nb golden pelts bought: " + trapperCards.getNbGoldenPeltBoughtCount());
			}
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			trapperCards.getMenu().getMainDeck1().add(selectedCard.getCard());
			trapperCards.getMenu().saveDeck(trapperCards.getMenu().getMainDeck1(), trapperCards.getMenu().getSourceDeck1());
			trapperCards.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton) {
			trapperCards.dispose();
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
