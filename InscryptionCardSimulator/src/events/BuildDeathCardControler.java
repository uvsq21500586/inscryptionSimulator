package events;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import cards.BeastCard;
import cards.Card;
import cards.CardFactory;
import cards.RobotCard;
import cards.UndeadCard;
import effects.Effect;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;
import frames.duelbuttons.CardPanel;

public class BuildDeathCardControler implements ActionListener,MouseListener {
	private BuildDeathCard buildDeathCard;
	private CardPanel selectedCardTypeAndCost = null;
	private CardPanel selectedCardHpAndAttack = null;
	private CardPanel selectedCardEffects = null;
	private int nbClicsOnValidate = 0;
	
	
	public BuildDeathCardControler(BuildDeathCard buildDeathCard) {
		this.buildDeathCard = buildDeathCard;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<buildDeathCard.getCardsPanelsCardsTypeAndCost().size();i++) {
			buildDeathCard.getCardsPanelsCardsTypeAndCost().get(i).addMouseListener(this);
		}
		for (int i=0;i<buildDeathCard.getCardsPanelsCardsHpAttack().size();i++) {
			buildDeathCard.getCardsPanelsCardsHpAttack().get(i).addMouseListener(this);
		}
		for (int i=0;i<buildDeathCard.getCardsPanelsCardsEffects().size();i++) {
			buildDeathCard.getCardsPanelsCardsEffects().get(i).addMouseListener(this);
		}
		buildDeathCard.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel) {
			CardPanel selectedCard = (CardPanel) e.getSource();
			if (nbClicsOnValidate == 0 && buildDeathCard.getCardsPanelsCardsTypeAndCost().contains(selectedCard)) {
				selectedCardTypeAndCost = selectedCard;
				for (int i=0;i<buildDeathCard.getCardsPanelsCardsTypeAndCost().size();i++) {
					buildDeathCard.getCardsPanelsCardsTypeAndCost().get(i).getSelected().setVisible(buildDeathCard.getCardsPanelsCardsTypeAndCost().get(i).equals(selectedCardTypeAndCost));
				}
				buildDeathCard.getButtonValidate().setEnabled(true);
			} else if (nbClicsOnValidate == 1 && buildDeathCard.getCardsPanelsCardsHpAttack().contains(selectedCard)) {
				selectedCardHpAndAttack = selectedCard;
				for (int i=0;i<buildDeathCard.getCardsPanelsCardsHpAttack().size();i++) {
					buildDeathCard.getCardsPanelsCardsHpAttack().get(i).getSelected().setVisible(buildDeathCard.getCardsPanelsCardsHpAttack().get(i).equals(selectedCardHpAndAttack));
				}
				buildDeathCard.getButtonValidate().setEnabled(true);
			} else if (nbClicsOnValidate == 2 && buildDeathCard.getCardsPanelsCardsEffects().contains(selectedCard)) {
				selectedCardEffects = selectedCard;
				for (int i=0;i<buildDeathCard.getCardsPanelsCardsEffects().size();i++) {
					buildDeathCard.getCardsPanelsCardsEffects().get(i).getSelected().setVisible(buildDeathCard.getCardsPanelsCardsEffects().get(i).equals(selectedCardEffects));
				}
				buildDeathCard.getButtonValidate().setEnabled(true);
			}
			
			
		}
		
		if (e.getSource() instanceof JButton && ((JButton)e.getSource()).isEnabled()) {
			if (nbClicsOnValidate == 0) {
				buildDeathCard.getButtonValidate().setEnabled(false);
				nbClicsOnValidate++;
				for (int i=0;i<buildDeathCard.getCardsPanelsCardsHpAttack().size();i++) {
					buildDeathCard.getCardsPanelsCardsHpAttack().get(i).setVisible(true);
				}
			} else if (nbClicsOnValidate == 1) {
				buildDeathCard.getButtonValidate().setEnabled(false);
				nbClicsOnValidate++;
				for (int i=0;i<buildDeathCard.getCardsPanelsCardsEffects().size();i++) {
					buildDeathCard.getCardsPanelsCardsEffects().get(i).setVisible(true);
				}
			} else if (nbClicsOnValidate == 2) {
				nbClicsOnValidate++;
				//build deathcard
				Card deathcard = deathCard(selectedCardTypeAndCost.getCard(), selectedCardHpAndAttack.getCard(), selectedCardEffects.getCard());
				try {
					buildDeathCard.getResultCard().repaint(deathcard);
					buildDeathCard.getMenu().getDeadCardsList().add(deathcard);
					List<Card> availableCards = new ArrayList<>();
					for (int i=0;i<buildDeathCard.getMenu().getDeadCardsList().size();i++) {
						availableCards.add(buildDeathCard.getMenu().getDeadCardsList().get(i));
					}
					buildDeathCard.getMenu().setAvailableDeadCardsList(availableCards);
					buildDeathCard.getMenu().saveDeadCards();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (nbClicsOnValidate == 3) {
				buildDeathCard.dispose();
			}
			//trial.getMenu().getMainDeck1().add(selectedCard.getCard());
			//trial.getMenu().saveDeck(trial.getMenu().getMainDeck1(), trial.getMenu().getSourceDeck1());
			
		}
		
	}
	
	private Card deathCard(Card cardTypeAndCost, Card cardHpAndAttack, Card cardEffects) {
		int idDeath = 1 + buildDeathCard.getMenu().getDeadCardsList().size()%buildDeathCard.nbSkinsDeaths;
		if (cardTypeAndCost instanceof BeastCard) {
			BeastCard bestcardTypeAndCost = (BeastCard) cardTypeAndCost;
			String costType = bestcardTypeAndCost.getCostType();
			int level = bestcardTypeAndCost.getLevel();
			int hp = cardHpAndAttack.getHpBase();
			int attack = cardHpAndAttack.getAttackBase();
			List<Effect> effects = cardEffects.getEffects();
			
			return new BeastCard("death_" + idDeath, costType, level, hp, attack, effects, 0, true);
		} else if (cardTypeAndCost instanceof RobotCard) {
			RobotCard robotcardTypeAndCost = (RobotCard) cardTypeAndCost;
			int level = robotcardTypeAndCost.getLevel();
			int hp = cardHpAndAttack.getHpBase();
			int attack = cardHpAndAttack.getAttackBase();
			List<Effect> effects = cardEffects.getEffects();
			return new RobotCard("death_" + idDeath, level, hp, attack, effects, 0, true);
		}
		
		
		return null;
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
