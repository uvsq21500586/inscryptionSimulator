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
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;

public class CostCardsControler implements ActionListener,MouseListener {
	private CostCards costCards;
	private CostPanel selectedCost = null;
	
	public CostCardsControler(CostCards costCards) {
		this.costCards = costCards;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<costCards.getCostPanels().length;i++) {
			if (costCards.getCostPanels()[i] != null) {
				costCards.getCostPanels()[i].addMouseListener(this);
			}
			//randomCards.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		costCards.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CostPanel && selectedCost == null) {
			System.out.println("CostPanel");
			selectedCost = (CostPanel) e.getSource();
			Random r = new Random();
			if (costCards.getMenu().getTypecards1().equals("beast")) {
				try {
					Card card1 = CardFactory.beastCardFixedLevel(selectedCost.getType(),
							selectedCost.getLevel(),
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					Card card2 = CardFactory.beastCardFixedLevel(selectedCost.getType(),
							selectedCost.getLevel(),
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					if (card2.getRarity()<card1.getRarity()) card1 = card2;
					
					costCards.getResultCard().repaint(card1);
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (costCards.getMenu().getTypecards1().equals("robot")) {
				try {
					Card card1 = CardFactory.robotCardFixedLevel(
							selectedCost.getLevel(),
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					Card card2 = CardFactory.robotCardFixedLevel(
							selectedCost.getLevel(),
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					if (card2.getRarity()<card1.getRarity()) card1 = card2;
					
					costCards.getResultCard().repaint(card1);
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (costCards.getMenu().getTypecards1().equals("undead")) {
				try {
					Card card1 = CardFactory.undeadCardFixedLevel(
							selectedCost.getLevel(),
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					Card card2 = CardFactory.undeadCardFixedLevel(
							selectedCost.getLevel(),
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					if (card2.getRarity()<card1.getRarity()) card1 = card2;
					
					costCards.getResultCard().repaint(card1);
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (costCards.getMenu().getTypecards1().equals("wizard")) {
				try {
					int prismcost = selectedCost.getLevel() - selectedCost.getGemCosts()[0] - selectedCost.getGemCosts()[1] - selectedCost.getGemCosts()[2];
					Card card1 = CardFactory.wizardCardFixedLevel(
							selectedCost.getLevel(),
							prismcost,
							selectedCost.getGemCosts()[0],
							selectedCost.getGemCosts()[1],
							selectedCost.getGemCosts()[2],
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					Card card2 = CardFactory.wizardCardFixedLevel(
							selectedCost.getLevel(),
							prismcost,
							selectedCost.getGemCosts()[0],
							selectedCost.getGemCosts()[1],
							selectedCost.getGemCosts()[2],
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1);
					if (card2.getRarity()<card1.getRarity()) card1 = card2;
					
					costCards.getResultCard().repaint(card1);
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			for (int i=0;i<costCards.getCostPanels().length;i++) {
				if (costCards.getCostPanels()[i] != null) {
					costCards.getCostPanels()[i].setEnabled(false);
				}
				
			}
			costCards.getMenu().getMainDeck1().add(costCards.getResultCard().getCard());
			costCards.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton) {
			costCards.getMenu().saveDeck(costCards.getMenu().getMainDeck1(), costCards.getMenu().getSourceDeck1());
			costCards.dispose();
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
