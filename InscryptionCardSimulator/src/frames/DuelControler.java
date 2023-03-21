package frames;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JPanel;

import cards.BeastCard;
import cards.Card;
import cards.RobotCard;
import cards.UndeadCard;
import cards.WizardCard;
import effects.Effect;
import frames.duelbuttons.ButtonMainDeck;
import frames.duelbuttons.ButtonPlaceCard;
import frames.duelbuttons.ButtonSourceDeck;
import frames.duelbuttons.CardPanel;
import frames.duelbuttons.HandCardPanel;
import frames.duelbuttons.LeftButton;
import frames.duelbuttons.NextTurnButton;
import frames.duelbuttons.RightButton;
import frames.menubuttons.ButtonToDuel;

public class DuelControler implements ActionListener,MouseListener {
	private Duel duel;
	private int sacrificeNeeded = 0;
	private int sacrifices = 0;
	private boolean sacrifying = false;
	private boolean puttingBloodCard = false;
	private boolean mustDrawCard = false;
	private CardPanel cardSelected;
	private List<CardPanel> cardBeingSacrified;
	
	
	public DuelControler(Duel duel) {
		super();
		this.duel = duel;
		this.duel.getButtonMainDeck().addMouseListener(this);
		this.duel.getButtonSourceDeck().addMouseListener(this);
		this.duel.getButtonLeft().addMouseListener(this);
		this.duel.getButtonRight().addMouseListener(this);
		this.duel.getNextTurnButton().addMouseListener(this);
		for (int i=0;i<this.duel.getHandPanel().cardsPanels.size(); i++) {
			this.duel.getHandPanel().cardsPanels.get(i).addMouseListener(this);
		}
		for (int i=0;i<8;i++) {
			this.duel.getButtonPlaceCard()[i].addMouseListener(this);
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (mustDrawCard) {
			if (e.getSource() instanceof ButtonMainDeck) {
				try {
					duel.drawMainDeckCard(this);
					mustDrawCard = false;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (e.getSource() instanceof ButtonSourceDeck) {
				try {
					duel.drawSourceDeckCard(this);
					mustDrawCard = false;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (e.getSource() instanceof LeftButton && duel.getIdFirstCard()>0) {
				duel.setIdFirstCard(duel.getIdFirstCard()-4);
				try {
					duel.redrawHandCard(this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
			if (e.getSource() instanceof RightButton) {
				int idFirstCard = duel.getIdFirstCard();
				boolean turnJ2 = duel.isTurnJ2();
				if ((turnJ2 && idFirstCard < duel.getHandCard2().size()) ||(!turnJ2 && idFirstCard < duel.getHandCard1().size()))
				duel.setIdFirstCard(duel.getIdFirstCard()+4);
				try {
					duel.redrawHandCard(this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}
			return;
		}
		if (e.getSource() instanceof CardPanel && !puttingBloodCard) {
			System.out.println("select card");
			playCard(e);
			return;
			
		}
		if (e.getSource() instanceof ButtonPlaceCard && !sacrifying && cardSelected != null && ((ButtonPlaceCard) e.getSource()).getCardPanel() == null) {
			try {
				putCard(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		if (e.getSource() instanceof LeftButton && duel.getIdFirstCard()>0) {
			duel.setIdFirstCard(duel.getIdFirstCard()-4);
			try {
				duel.redrawHandCard(this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		if (e.getSource() instanceof RightButton) {
			int idFirstCard = duel.getIdFirstCard();
			boolean turnJ2 = duel.isTurnJ2();
			if ((turnJ2 && idFirstCard < duel.getHandCard2().size()) ||(!turnJ2 && idFirstCard < duel.getHandCard1().size()))
			duel.setIdFirstCard(duel.getIdFirstCard()+4);
			try {
				duel.redrawHandCard(this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		
		if (e.getSource() instanceof NextTurnButton) {
			try {
				duel.nextTurn(this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		
	}

	private void putCard(MouseEvent e) throws IOException, FontFormatException {
		System.out.println("puttingCard");
		ButtonPlaceCard fieldCard = (ButtonPlaceCard) e.getSource();
		if (duel.getButtonPlaceCard()[0].equals(fieldCard)) {
			System.out.println("puttingCard0");
			duel.getButtonPlaceCard()[0].setCardPanel(cardSelected);
			if (duel.isTurnJ2()) {
				duel.getHandCard2().remove(cardSelected);
			} else {
				duel.getHandCard1().remove(cardSelected);
			}
			
			duel.getPanel().add(cardSelected,0);
			cardSelected.setPosition("onField");
			cardSelected.setFieldPosition(0);
			cardSelected.setBounds(100,310,200,300);
			duel.getButtonPlaceCard()[0].setCardPanel(cardSelected);
		}
		if (duel.getButtonPlaceCard()[1].equals(fieldCard)) {
			System.out.println("puttingCard1");
			duel.getButtonPlaceCard()[1].setCardPanel(cardSelected);
			if (duel.isTurnJ2()) {
				duel.getHandCard2().remove(cardSelected);
			} else {
				duel.getHandCard1().remove(cardSelected);
			}
			duel.getPanel().add(cardSelected,0);
			cardSelected.setPosition("onField");
			cardSelected.setFieldPosition(1);
			cardSelected.setBounds(300,310,200,300);
			duel.getButtonPlaceCard()[1].setCardPanel(cardSelected);
		}
		if (duel.getButtonPlaceCard()[2].equals(fieldCard)) {
			System.out.println("puttingCard2");
			duel.getButtonPlaceCard()[2].setCardPanel(cardSelected);
			if (duel.isTurnJ2()) {
				duel.getHandCard2().remove(cardSelected);
			} else {
				duel.getHandCard1().remove(cardSelected);
			}
			duel.getPanel().add(cardSelected,0);
			cardSelected.setPosition("onField");
			cardSelected.setFieldPosition(2);
			cardSelected.setBounds(500,310,200,300);
			duel.getButtonPlaceCard()[2].setCardPanel(cardSelected);
		}
		if (duel.getButtonPlaceCard()[3].equals(fieldCard)) {
			System.out.println("puttingCard3");
			duel.getButtonPlaceCard()[3].setCardPanel(cardSelected);
			if (duel.isTurnJ2()) {
				duel.getHandCard2().remove(cardSelected);
			} else {
				duel.getHandCard1().remove(cardSelected);
			}
			duel.getPanel().add(cardSelected,0);
			cardSelected.setPosition("onField");
			cardSelected.setFieldPosition(3);
			cardSelected.setBounds(700,310,200,300);
			duel.getButtonPlaceCard()[3].setCardPanel(cardSelected);
		}
		
		if (duel.getButtonPlaceCard()[0].equals(fieldCard) || duel.getButtonPlaceCard()[1].equals(fieldCard) || duel.getButtonPlaceCard()[2].equals(fieldCard) || duel.getButtonPlaceCard()[3].equals(fieldCard)) {
			cardSelected.getCard().placeCard(duel, duel.getButtonPlaceCard(), cardSelected.getFieldPosition());
			cardSelected.getSelected().setVisible(false);
			duel.recalculateAttk(cardSelected.getCard(), cardSelected.getFieldPosition());
			if (cardSelected.getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("fecundity"))) {
				if (cardSelected.getCard() instanceof BeastCard) {
					BeastCard copycard = (BeastCard) cardSelected.getCard();
					CardPanel copyCardPanel = new CardPanel(copycard.cloneCard(copycard));
					if (duel.isTurnJ2()) {
						duel.getHandCard2().add(copyCardPanel);
					} else {
						duel.getHandCard1().add(copyCardPanel);
					}
					copyCardPanel.addMouseListener(this);
				}
			}
			boolean isFamiliar = cardSelected.getCard().getEffects().stream().anyMatch(effect -> effect.getName().equals("familiar"));
			if (isFamiliar) {
				if (!duel.isTurnJ2()) {
					cardSelected.getCard().familiarP1(duel, duel.getButtonPlaceCard(), this, cardSelected.getFieldPosition());
					duel.getBonePileCount().setText(": " + duel.getBoneP1());
				} else {
					cardSelected.getCard().familiarP2(duel, duel.getButtonPlaceCard(), this, cardSelected.getFieldPosition());
					duel.getBonePileCount().setText(": " + duel.getBoneP2());
				}
			}
			Optional<Effect> hoarder = cardSelected.getCard().getEffects().stream().filter(effect -> effect.getName().equals("hoarder")).findFirst();
			if (hoarder.isPresent()) {
				if (!duel.isTurnJ2()) {
					for (int i=0;i<hoarder.get().getLevel();i++) {
						if (!duel.getMainDeck1().isEmpty()) {
							duel.drawMainDeckCard(this);
						} else if (!duel.getSourceDeck1().isEmpty()) {
							duel.drawSourceDeckCard(this);
						}
					}
				} else {
					for (int i=0;i<hoarder.get().getLevel();i++) {
						if (!duel.getMainDeck2().isEmpty()) {
							duel.drawMainDeckCard(this);
						} else if (!duel.getSourceDeck2().isEmpty()) {
							duel.drawSourceDeckCard(this);
						}
					}
				}
			}
			Optional<Effect> gem_animator = cardSelected.getCard().getEffects().stream().filter(effect -> effect.getName().equals("gem_animator")).findFirst();
			if (gem_animator.isPresent()) {
				for (int i=0;i<4;i++) {
					if (i != cardSelected.getFieldPosition() && duel.getButtonPlaceCard()[i].getCardPanel() != null) {
						Card card = duel.getButtonPlaceCard()[i].getCardPanel().getCard();
						if (card instanceof WizardCard && !card.isSacrificiable()) {
							card.setAttack(card.getAttack()+gem_animator.get().getLevel());
							duel.getButtonPlaceCard()[i].getCardPanel().getAttack().setText(card.getAttack().toString());
						}
						
					}
				}
			}
			cardSelected = null;
			puttingBloodCard = false;
			try {
				duel.redrawHandCard(this);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void playCard(MouseEvent e) {
		//System.out.println("playCard");
		CardPanel card = (CardPanel) e.getSource();
		if (card.getPosition().equals("onHand") && !sacrifying && duel.playable(card.getCard()) && cardSelected == null) {
			System.out.println("playCard playable");
			cardSelected = card;
			cardSelected.getSelected().setVisible(true);
			if (card.getCard() instanceof BeastCard && ((BeastCard) card.getCard()).getCostType().equals("blood") && card.getCard().getLevel()>0) {
				sacrifying = true;
				System.out.println("sacrifying");
				sacrificeNeeded = card.getCard().getLevel();
				sacrifices = 0;
				cardBeingSacrified = new ArrayList<>();
			}
			if (card.getCard() instanceof BeastCard && ((BeastCard) card.getCard()).getCostType().equals("bone")) {
				if (duel.isTurnJ2()) {
					duel.setBoneP2(duel.getBoneP2() - card.getCard().getLevel());
					duel.getBonePileCount().setText(": " + duel.getBoneP2());
				} else {
					duel.setBoneP1(duel.getBoneP1() - card.getCard().getLevel());
					duel.getBonePileCount().setText(": " + duel.getBoneP1());
				}
				
			}
			if (card.getCard() instanceof RobotCard) {
				duel.setEnergy(duel.getEnergy() - card.getCard().getLevel());
				duel.getEnergyPileCount().setText(": " + duel.getEnergy() + "/" + duel.getEnergymax());
			}
			if (card.getCard() instanceof UndeadCard) {
				if (duel.isTurnJ2()) {
					duel.setBoneP2(duel.getBoneP2() - card.getCard().getLevel());
					duel.getBonePileCount().setText(": " + duel.getBoneP2());
				} else {
					duel.setBoneP1(duel.getBoneP1() - card.getCard().getLevel());
					duel.getBonePileCount().setText(": " + duel.getBoneP1());
				}
			}
		} else if (card.getPosition().equals("onField") && card.getFieldPosition()<4 && sacrifying && !cardBeingSacrified.contains(card)) {
			System.out.println("sacrifying + ");
			cardBeingSacrified.add(card);
			card.getBeingSacrified().setVisible(true);
			duel.getButtonPlaceCard()[card.getFieldPosition()].setCardPanel(null);
			sacrifices ++;
			//unkillable?
			
			if (sacrifices >= sacrificeNeeded) {
				for (int i=0;i<cardBeingSacrified.size();i++) {
					int bones = 1;
					Optional<Effect> bone_king = cardBeingSacrified.get(i).getCard().getEffects().stream().filter(effect->effect.getName().equals("bone_king")).findFirst();
					if (bone_king.isPresent()) {
						bones += bone_king.get().getLevel();
					}
					if (duel.isTurnJ2()) {
						duel.setBoneP2(duel.getBoneP2() + bones);
						duel.getBonePileCount().setText(": " + duel.getBoneP2());
					} else {
						duel.setBoneP1(duel.getBoneP1() + bones);
						duel.getBonePileCount().setText(": " + duel.getBoneP1());
					}
				}
				
				cardBeingSacrified.forEach(cardSacrified -> duel.getPanel().remove(cardSacrified));
				puttingBloodCard = true;
				sacrifying = false;
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

	public boolean isMustDrawCard() {
		return mustDrawCard;
	}

	public void setMustDrawCard(boolean mustDrawCard) {
		this.mustDrawCard = mustDrawCard;
	}
	
	

}
