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
import cards.UndeadCard;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;
import frames.duelbuttons.CardPanel;

public class TrialControler implements ActionListener,MouseListener {
	private Trial trial;
	private TrialPanel selectedTrial = null;
	private CardPanel selectedCard = null;
	private int nbClicsOnValidate = 0;
	private boolean success = false;
	
	
	public TrialControler(Trial trial) {
		this.trial = trial;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<3;i++) {
			trial.getCardsPanelsNewCards().get(i).addMouseListener(this);
			trial.getTrialPanels()[i].addMouseListener(this);
		}
		trial.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel) {
			selectedCard = (CardPanel) e.getSource();
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			int index = trial.getCardsPanelsMainDeck().indexOf(selectedCard);
			for (int i=0;i<3;i++) {
				trial.getCardsPanelsMainDeck().get(i).getSelected().setVisible(i == index);
			}
			trial.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof TrialPanel && nbClicsOnValidate == 0) {
			selectedTrial = (TrialPanel) e.getSource();
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			int index = trial.getCardsPanelsMainDeck().indexOf(selectedCard);
			for (int i=0;i<trial.getCardsPanelsMainDeck().size();i++) {
				trial.getCardsPanelsMainDeck().get(i).getSelected().setVisible(i == index);
			}
			trial.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton && ((JButton)e.getSource()).isEnabled()) {
			if (nbClicsOnValidate == 0) {
				for (int i=0;i<trial.getCardsPanelsMainDeck().size();i++) {
					trial.getCardsPanelsMainDeck().get(i).setVisible(true);
					
				}
				nbClicsOnValidate++;
				//trial test
				
				int score = 0;
				if (selectedTrial.getType().equals("health")) {
					for (int i=0;i<trial.getCardsPanelsMainDeck().size();i++) {
						score += trial.getCardsPanelsMainDeck().get(i).getCard().getHpBase();
					}
				} else if (selectedTrial.getType().equals("power")) {
					for (int i=0;i<trial.getCardsPanelsMainDeck().size();i++) {
						score += trial.getCardsPanelsMainDeck().get(i).getCard().getAttackBase();
					}
				} else if (selectedTrial.getType().equals("wisdom")) {
					for (int i=0;i<trial.getCardsPanelsMainDeck().size();i++) {
						score += trial.getCardsPanelsMainDeck().get(i).getCard().getEffects().size();
					}
				} else if (selectedTrial.getType().equals("blood")) {
					for (int i=0;i<trial.getCardsPanelsMainDeck().size();i++) {
						Card card = trial.getCardsPanelsMainDeck().get(i).getCard();
						if (card instanceof BeastCard && ((BeastCard)card).getCostType().equals("blood")) {
							score += card.getLevel();
						}
						
					}
				} else if (selectedTrial.getType().equals("bone")) {
					for (int i=0;i<trial.getCardsPanelsMainDeck().size();i++) {
						Card card = trial.getCardsPanelsMainDeck().get(i).getCard();
						if (card instanceof UndeadCard ||(card instanceof BeastCard && ((BeastCard)card).getCostType().equals("bone"))) {
							score += card.getLevel();
						}
						
					}
				}
				if (score >= selectedTrial.getLevel()) {
					trial.getButtonValidate().setEnabled(false);
					for (int i=0;i<trial.getCardsPanelsNewCards().size();i++) {
						trial.getCardsPanelsNewCards().get(i).setVisible(true);
					}
				}
				
			} else {
				if (selectedCard != null) {
					trial.getMenu().getMainDeck1().add(selectedCard.getCard());
				}
				trial.dispose();
			}
			//trial.getMenu().getMainDeck1().add(selectedCard.getCard());
			//trial.getMenu().saveDeck(trial.getMenu().getMainDeck1(), trial.getMenu().getSourceDeck1());
			
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
