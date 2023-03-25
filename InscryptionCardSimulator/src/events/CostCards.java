package events;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cards.Card;
import cards.CardFactory;
import frames.Menu;
import frames.duelbuttons.CardPanel;

public class CostCards  extends JFrame {
	List<CostPanel> cardsPanelsMainDeck = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private CostPanel costPanels[];
	private CardPanel resultCard;
	
	
	public CostCards(Menu menu) throws IOException, FontFormatException {
		super("Cost cards");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 650);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		costPanels = new CostPanel[3];
		if (!menu.getTypecards1().equals("wizard")) {
			String costs[] = new String[3];
			Integer levels[] = new Integer[3];
			if (menu.getTypecards1().equals("beast")) {
				for (int i=0;i<3;i++) {
					int type = r.nextInt(4);
					if (type == 3) {
						costs[i] = "bone";
					} else {
						costs[i] = "blood";
					}
					if (costs[i].equals("blood")) {
						levels[i] = 1 + r.nextInt(3);
					} else {
						levels[i] = 2 + r.nextInt(7);
					}
					if (!isNewCost(costs, levels, i)) {
						i--;
					} else {
						costPanels[i] = new CostPanel(costs[i], levels[i]);
						costPanels[i].setBounds(200*i,0,200,300);
						panel.add(costPanels[i]);
					}
				}
				
			}
			
		}
		
		resultCard = new CardPanel();
		this.getContentPane().add(resultCard);
		resultCard.setBounds(50,400,200,300);
		
		/*List<Card> boosterMain = new ArrayList<>();
		for(int i=0;i<3;i++) {
			Card card1 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1());
			Card card2 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1());
			if (card2.getRarity() < card1.getRarity()) {
				boosterMain.add(card2);
			} else {
				boosterMain.add(card1);
			}
		}
		
		
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(boosterMain.get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 0, 200, 300);
		}*/
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(500,400,100,50);
		buttonValidate.setEnabled(false);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new CostCardsControler(this);
	}
	
	private boolean isNewCost(String costs[], Integer levels[], int pos) {
		for (int i=0;i<pos;i++) {
			if (costs[i].equals(costs[pos]) && levels[i] == levels[pos]) {
				return false;
			}
		}
		return true;
	}

	public JButton getButtonValidate() {
		return buttonValidate;
	}

	public void setButtonValidate(JButton buttonValidate) {
		this.buttonValidate = buttonValidate;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public CostPanel[] getCostPanels() {
		return costPanels;
	}

	public void setCostPanels(CostPanel[] costPanels) {
		this.costPanels = costPanels;
	}

	public CardPanel getResultCard() {
		return resultCard;
	}

	public void setResultCard(CardPanel resultCard) {
		this.resultCard = resultCard;
	}
	
	
	

}
