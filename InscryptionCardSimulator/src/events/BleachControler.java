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
import effects.Effect;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;

public class BleachControler implements ActionListener,MouseListener {
	private Bleach bleach;
	private CardPanel selectedCard = null;
	private boolean clickValidate = false;
	
	public BleachControler(Bleach bleach) {
		this.bleach = bleach;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<bleach.getCardsPanelsMainDeck().size();i++) {
			bleach.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		bleach.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CardPanel) {
			selectedCard = (CardPanel) e.getSource();
			//((CardPanel) e.getSource()).getSelected().setVisible(true);
			int index = bleach.getCardsPanelsMainDeck().indexOf(selectedCard);
			for (int i=0;i<bleach.getCardsPanelsMainDeck().size();i++) {
				bleach.getCardsPanelsMainDeck().get(i).getSelected().setVisible(i == index);
			}
			bleach.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton) {
			if (clickValidate) {
				bleach.dispose();
			} else {
				Random r = new Random();
				Card card = selectedCard.getCard();
				if (card.getEffects().size() == 0) {
					try {
					String nameNewEffect = "";
					Effect effect = null;
					if (card instanceof BeastCard) {
						nameNewEffect = Effect.namesBeastEffects.get(r.nextInt(Math.min(bleach.getMenu().getModulo1(), Effect.namesBeastEffects.size())));
						if (Effect.namesLevelEffects.contains(nameNewEffect)) {
							effect = new Effect(nameNewEffect,"beast",1);
						} else {
							effect = new Effect(nameNewEffect,"beast");
						}
					} else if (card instanceof RobotCard) {
						nameNewEffect = Effect.namesRobotEffects.get(r.nextInt(Math.min(bleach.getMenu().getModulo1(), Effect.namesRobotEffects.size())));
						if (Effect.namesLevelEffects.contains(nameNewEffect)) {
							effect = new Effect(nameNewEffect,"robot",1);
						} else {
							effect = new Effect(nameNewEffect,"robot");
						}
					} else if (card instanceof UndeadCard) {
						nameNewEffect = Effect.namesUndeadEffects.get(r.nextInt(Math.min(bleach.getMenu().getModulo1(), Effect.namesUndeadEffects.size())));
						if (Effect.namesLevelEffects.contains(nameNewEffect)) {
							effect = new Effect(nameNewEffect,"undead",1);
						} else {
							effect = new Effect(nameNewEffect,"undead");
						}
					}
					card.getEffects().add(effect);
					bleach.getResult().repaint(selectedCard.getCard());
					} catch (IOException | FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						int idEffect = r.nextInt(card.getEffects().size());
						Effect effect = card.getEffects().get(idEffect);
						String nameNewEffect = "";
						if (card instanceof BeastCard) {
							nameNewEffect = Effect.namesBeastEffects.get(r.nextInt(Math.min(bleach.getMenu().getModulo1(), Effect.namesBeastEffects.size())));
							if (Effect.namesLevelEffects.contains(nameNewEffect)) {
								effect = new Effect(nameNewEffect,"beast",1);
							} else {
								effect = new Effect(nameNewEffect,"beast");
							}
						} else if (card instanceof RobotCard) {
							nameNewEffect = Effect.namesRobotEffects.get(r.nextInt(Math.min(bleach.getMenu().getModulo1(), Effect.namesRobotEffects.size())));
							if (Effect.namesLevelEffects.contains(nameNewEffect)) {
								effect = new Effect(nameNewEffect,"robot",1);
							} else {
								effect = new Effect(nameNewEffect,"robot");
							}
						} else if (card instanceof UndeadCard) {
							nameNewEffect = Effect.namesUndeadEffects.get(r.nextInt(Math.min(bleach.getMenu().getModulo1(), Effect.namesUndeadEffects.size())));
							if (Effect.namesLevelEffects.contains(nameNewEffect)) {
								effect = new Effect(nameNewEffect,"undead",1);
							} else {
								effect = new Effect(nameNewEffect,"undead");
							}
						}
						card.getEffects().set(idEffect, effect);
						bleach.getResult().repaint(selectedCard.getCard());
						
					} catch (IOException | FontFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				bleach.getMenu().saveDeck(bleach.getMenu().getMainDeck1(), bleach.getMenu().getSourceDeck1());
				clickValidate = true;
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
