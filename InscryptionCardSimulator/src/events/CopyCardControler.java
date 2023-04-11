package events;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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

public class CopyCardControler implements ActionListener,MouseListener {
	private CopyCard copyCard;
	private CardPanel selectedCard = null;
	private Card copyOfCard;
	private boolean clickValidate = false;
	
	public CopyCardControler(CopyCard copyCard) {
		this.copyCard = copyCard;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<copyCard.getCardsPanelsMainDeck().size();i++) {
			copyCard.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		copyCard.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel) {
			selectedCard = (CardPanel) e.getSource();
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			int index = copyCard.getCardsPanelsMainDeck().indexOf(selectedCard);
			for (int i=0;i<copyCard.getCardsPanelsMainDeck().size();i++) {
				copyCard.getCardsPanelsMainDeck().get(i).getSelected().setVisible(i == index);
			}
			copyCard.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton) {
			/*if (clickValidate) {
				copyCard.dispose();
			} else {
				Random r = new Random();
				int chance = r.nextInt(Integer.parseInt(copyCard.getChanceDice().getText()));
				if (chance < Integer.parseInt(copyCard.getChanceSuccess().getText())) {
					System.out.println(chance);
					if (copyCard.isAttackBonus()) {
						selectedCard.getCard().setAttackBase(selectedCard.getCard().getAttackBase()+1);
						selectedCard.getCard().setAttack(selectedCard.getCard().getAttackBase());
					} else {
						selectedCard.getCard().setHpBase(selectedCard.getCard().getHpBase()+2);
						selectedCard.getCard().setHp(selectedCard.getCard().getHpBase());
					}
					try {
						copyCard.getResult().repaint(selectedCard.getCard());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					copyCard.getMenu().getMainDeck1().remove(selectedCard.getCard());
				}*/
			if (clickValidate) {
				copyCard.dispose();
			} else {
				
				try {
					copyOfCard = copySelectedCard(selectedCard.getCard());
					copyCard.getResult().repaint(copyOfCard);
					copyCard.getMenu().getMainDeck1().add(copyOfCard);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				copyCard.getMenu().saveDeck(copyCard.getMenu().getMainDeck1(), copyCard.getMenu().getSourceDeck1());
				clickValidate = true;
			}
			
			
			
		}
		
	}
	
	private Card copySelectedCard(Card cardToCopy) throws IOException {
		Card enhancedCard = null;
		if (cardToCopy instanceof BeastCard) {
			enhancedCard = ((BeastCard) cardToCopy).cloneCard((BeastCard) cardToCopy);
		} else if (cardToCopy instanceof RobotCard) {
			enhancedCard = ((RobotCard) cardToCopy).cloneCard((RobotCard) cardToCopy);
		} else if (cardToCopy instanceof UndeadCard) {
			enhancedCard = ((UndeadCard) cardToCopy).cloneCard((UndeadCard) cardToCopy);
		} else if (cardToCopy instanceof WizardCard) {
			enhancedCard = ((WizardCard) cardToCopy).cloneCard((WizardCard) cardToCopy);
		}
		Random r = new Random();
		for (int i=0;i<enhancedCard.getEffects().size();i++) {	
			if (r.nextInt(2)<1) {
				if (cardToCopy instanceof BeastCard) {
					String newEffect = Effect.namesBeastEffects.get(r.nextInt(Effect.namesBeastEffects.size()));
					if (Effect.namesLevelEffects.contains(newEffect)) {
						enhancedCard.getEffects().set(i, new Effect(newEffect, "beast", 1));
					} else {
						enhancedCard.getEffects().set(i, new Effect(newEffect, "beast"));
					}
				} else if (cardToCopy instanceof RobotCard) {
					String newEffect = Effect.namesRobotEffects.get(r.nextInt(Effect.namesRobotEffects.size()));
					if (Effect.namesLevelEffects.contains(newEffect)) {
						enhancedCard.getEffects().set(i, new Effect(newEffect, "robot", 1));
					} else {
						enhancedCard.getEffects().set(i, new Effect(newEffect, "robot"));
					}
				} else if (cardToCopy instanceof UndeadCard) {
					String newEffect = Effect.namesUndeadEffects.get(r.nextInt(Effect.namesUndeadEffects.size()));
					if (Effect.namesLevelEffects.contains(newEffect)) {
						enhancedCard.getEffects().set(i, new Effect(newEffect, "undead", 1));
					} else {
						enhancedCard.getEffects().set(i, new Effect(newEffect, "undead"));
					}
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
