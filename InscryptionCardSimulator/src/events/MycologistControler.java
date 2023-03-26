package events;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import javax.swing.JButton;

import cards.BeastCard;
import cards.Card;
import cards.CardFactory;
import cards.RobotCard;
import cards.UndeadCard;
import cards.WizardCard;
import effects.Effect;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;
import frames.duelbuttons.CardPanel;

public class MycologistControler implements ActionListener,MouseListener {
	private Mycologist mycologist;
	private CardPanel selectedCardToFusion1 = null;
	private CardPanel selectedCardToFusion2 = null;
	
	public MycologistControler(Mycologist mycologist) {
		this.mycologist = mycologist;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<mycologist.getCardsPanelsMainDeck().size();i++) {
			mycologist.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		mycologist.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel && selectedCardToFusion2 == null) {
			CardPanel selectedCard = (CardPanel) e.getSource();
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			int index = mycologist.getCardsPanelsMainDeck().indexOf(selectedCard);
			selectedCard.setVisible(true);
			if (selectedCardToFusion1 == null) {
				selectedCardToFusion1 = selectedCard;
				try {
					mycologist.getCardToFusion1().repaint(selectedCard.getCard());
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				mycologist.getCardToFusion1().getBeingSacrified().setVisible(true);
			} else if (!selectedCard.equals(selectedCardToFusion1)) {
				selectedCardToFusion2 = selectedCard;
				try {
					mycologist.getCardToFusion2().repaint(selectedCard.getCard());
					Card enhancedCard = fusionCard(selectedCardToFusion1.getCard(), selectedCard.getCard());
					mycologist.getResult().repaint(enhancedCard);
					mycologist.getButtonValidate().setEnabled(true);
					
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		
		if (e.getSource() instanceof JButton) {
			mycologist.getMenu().getMainDeck1().remove(selectedCardToFusion1.getCard());
			mycologist.getMenu().getMainDeck1().remove(selectedCardToFusion2.getCard());
			mycologist.getMenu().getMainDeck1().add(mycologist.getResult().getCard());
			mycologist.getMenu().saveDeck(mycologist.getMenu().getMainDeck1(), mycologist.getMenu().getSourceDeck1());
			mycologist.dispose();
		}
		
	}
	
	private Card fusionCard(Card cardToFusion1, Card cardToFusion2) throws IOException {
		Card fusionedCard = null;
		if (cardToFusion2 instanceof BeastCard) {
			fusionedCard = ((BeastCard) cardToFusion2).cloneCard((BeastCard) cardToFusion2);
		} else if (cardToFusion2 instanceof RobotCard) {
			fusionedCard = ((RobotCard) cardToFusion2).cloneCard((RobotCard) cardToFusion2);
		} else if (cardToFusion2 instanceof UndeadCard) {
			fusionedCard = ((UndeadCard) cardToFusion2).cloneCard((UndeadCard) cardToFusion2);
		} else if (cardToFusion2 instanceof WizardCard) {
			fusionedCard = ((WizardCard) cardToFusion2).cloneCard((WizardCard) cardToFusion2);
		}
		if (fusionedCard != null) {
			for (int i=0;i<cardToFusion1.getEffects().size();i++) {
				Effect effectToTransfert = cardToFusion1.getEffects().get(i);
				Optional<Effect> effectPresentInCardToFusion = fusionedCard.getEffects().stream().filter(effect -> effect.getName().equals(effectToTransfert.getName())).findFirst();
				if (effectPresentInCardToFusion.isPresent() && Effect.namesLevelEffects.contains(effectToTransfert.getName())) {
					effectPresentInCardToFusion.get().setLevel(Math.max(effectPresentInCardToFusion.get().getLevel(), effectToTransfert.getLevel()));
				} else if (fusionedCard.getEffects().size()<4) {
					fusionedCard.getEffects().add(effectToTransfert);
				}
			}
			fusionedCard.setAttackBase(cardToFusion1.getAttackBase() + cardToFusion2.getAttackBase());
			fusionedCard.setAttack(fusionedCard.getAttackBase());
			fusionedCard.setHpBase(cardToFusion1.getHpBase() + cardToFusion2.getHpBase());
			fusionedCard.setHp(fusionedCard.getHpBase());
		}
		
		
		return fusionedCard;
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
