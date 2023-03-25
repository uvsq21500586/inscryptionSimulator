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
import cards.RobotCard;
import events.RandomCards;
import frames.menubuttons.ButtonToDuel;
import frames.menubuttons.ButtonToSimulatorCard;
import frames.duelbuttons.CardPanel;

public class CostCardsControler implements ActionListener,MouseListener {
	private CostCards costCards;
	private CostPanel selectedCost = null;
	
	public CostCardsControler(CostCards costCards) {
		this.costCards = costCards;
		//menu.getButtonduel().addActionListener(this);
		for (int i=0;i<3;i++) {
			costCards.getCostPanels()[i].addMouseListener(this);
			//randomCards.getCardsPanelsMainDeck().get(i).addMouseListener(this);
		}
		costCards.getButtonValidate().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked");
		// TODO Auto-generated method stub
		if (e.getSource() instanceof CostPanel) {
			System.out.println("CostPanel");
			selectedCost = (CostPanel) e.getSource();
			Random r = new Random();
			if (costCards.getMenu().getTypecards1().equals("beast")) {
				try {
					costCards.getResultCard().repaint(CardFactory.beastCardFixedLevel(selectedCost.getType(),
							selectedCost.getLevel(),
							costCards.getMenu().getModulo1(),
							costCards.getMenu().getMultiplier1(),
							costCards.getMenu().getGlobalStrenght1(),
							costCards.getMenu().getRarityStrenght1(),
							r.nextInt(costCards.getMenu().getModulo1()-1)+1));
				} catch (IOException | FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			for (int i=0;i<3;i++) {
				costCards.getCostPanels()[i].setEnabled(false);
			}
			costCards.getMenu().getMainDeck1().add(costCards.getResultCard().getCard());
			costCards.getButtonValidate().setEnabled(true);
		}
		
		if (e.getSource() instanceof JButton) {
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
