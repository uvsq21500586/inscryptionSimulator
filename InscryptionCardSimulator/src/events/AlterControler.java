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
import cards.CardPanel;
import cards.RobotCard;
import cards.UndeadCard;
import cards.WizardCard;
import effects.Effect;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;

public class AlterControler implements ActionListener,MouseListener {
	private Alter alter;
	private CardPanel selectedCardToSacrifice = null;
	private CardPanel selectedCardToEnhance = null;
	
	public AlterControler(Alter alter) {
		this.alter = alter;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<alter.getCardsPanelsMainDeck().size();i++) {
			alter.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		alter.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel && selectedCardToEnhance == null) {
			CardPanel selectedCard = (CardPanel) e.getSource();
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			int index = alter.getCardsPanelsMainDeck().indexOf(selectedCard);
			selectedCard.setVisible(true);
			if (selectedCardToSacrifice == null) {
				selectedCardToSacrifice = selectedCard;
				try {
					alter.getCardToSacrify().repaint(selectedCard.getCard());
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				alter.getCardToSacrify().getBeingSacrified().setVisible(true);
			} else {
				selectedCardToEnhance = selectedCard;
				try {
					alter.getCardToEnhance().repaint(selectedCard.getCard());
					Card enhancedCard = enhanceCard(selectedCardToSacrifice.getCard(), selectedCard.getCard());
					alter.getResult().repaint(enhancedCard);
					alter.getResult().repaint(enhancedCard);
					alter.getButtonValidate().setEnabled(true);
					
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		
		if (e.getSource() instanceof JButton) {
			alter.getMenu().getMainDeck1().remove(selectedCardToSacrifice.getCard());
			alter.getMenu().getMainDeck1().remove(selectedCardToEnhance.getCard());
			alter.getMenu().getMainDeck1().add(alter.getResult().getCard());
			alter.getMenu().saveDeck(alter.getMenu().getMainDeck1(), alter.getMenu().getSourceDeck1());
			alter.dispose();
		}
		
	}
	
	private Card enhanceCard(Card cardToSacrify, Card cardToEnhance) throws IOException {
		Card enhancedCard = null;
		if (cardToEnhance instanceof BeastCard) {
			enhancedCard = ((BeastCard) cardToEnhance).cloneCard((BeastCard) cardToEnhance);
		} else if (cardToEnhance instanceof RobotCard) {
			enhancedCard = ((RobotCard) cardToEnhance).cloneCard((RobotCard) cardToEnhance);
		} else if (cardToEnhance instanceof UndeadCard) {
			enhancedCard = ((UndeadCard) cardToEnhance).cloneCard((UndeadCard) cardToEnhance);
		} else if (cardToEnhance instanceof WizardCard) {
			enhancedCard = ((WizardCard) cardToEnhance).cloneCard((WizardCard) cardToEnhance);
		}
		if (enhancedCard != null) {
			for (int i=0;i<cardToSacrify.getEffects().size();i++) {
				Effect effectToTransfert = cardToSacrify.getEffects().get(i);
				Optional<Effect> effectPresentInCardToEnhance = enhancedCard.getEffects().stream().filter(effect -> effect.getName().equals(effectToTransfert.getName())).findFirst();
				if (effectPresentInCardToEnhance.isPresent() && Effect.namesLevelEffects.contains(effectToTransfert.getName())) {
					effectPresentInCardToEnhance.get().setLevel(effectPresentInCardToEnhance.get().getLevel()+1);
				} else if (enhancedCard.getEffects().size()<4 && !effectPresentInCardToEnhance.isPresent()) {
					enhancedCard.getEffects().add(effectToTransfert);
				}
			}
		}
		
		
		return enhancedCard;
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
