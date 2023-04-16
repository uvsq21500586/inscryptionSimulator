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
import cards.CardPanel;
import effects.Effect;
import frames.Menu;

public class CostCards  extends JFrame {
	List<CostPanel> cardsPanelsMainDeck = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private CostPanel costPanels[];
	private CardPanel resultCard;
	
	
	public CostCards(Menu menu, Integer nbChoices) throws IOException, FontFormatException {
		super("Cost cards");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 950);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		costPanels = new CostPanel[nbChoices];
		int nbcosts = 0;
		if (!menu.getTypecards1().equals("wizard")) {
			String costs[] = new String[nbChoices];
			Integer levels[] = new Integer[nbChoices];
			if (menu.getTypecards1().equals("beast")) {
				for (int i=0;i<nbChoices;i++) {
					costBeastFactory(costs, levels, nbcosts);
					if (isNewCost(costs, levels, nbcosts)) {
						costPanels[nbcosts] = new CostPanel(costs[nbcosts], levels[nbcosts]);
						costPanels[nbcosts].setBounds(200*nbcosts,0,200,300);
						panel.add(costPanels[nbcosts]);
						nbcosts++;
					}
				}
				
			} else if (menu.getTypecards1().equals("robot")) {
				for (int i=0;i<nbChoices;i++) {
					costRobotFactory(costs, levels, nbcosts);
					if (isNewCost(costs, levels, nbcosts)) {
						costPanels[nbcosts] = new CostPanel(costs[nbcosts], levels[nbcosts]);
						costPanels[nbcosts].setBounds(200*nbcosts,0,200,300);
						panel.add(costPanels[nbcosts]);
						nbcosts++;
					}
				}
				
			} else if (menu.getTypecards1().equals("undead")) {
				for (int i=0;i<nbChoices;i++) {
					costUndeadFactory(costs, levels, nbcosts);
					if (isNewCost(costs, levels, nbcosts)) {
						costPanels[nbcosts] = new CostPanel(costs[nbcosts], levels[nbcosts]);
						costPanels[nbcosts].setBounds(200*nbcosts,0,200,300);
						panel.add(costPanels[nbcosts]);
						nbcosts++;
					}
				}
				
			}
			
		} else {
			int costs[][] = new int[nbChoices][3];
			Integer levels[] = new Integer[nbChoices];
			for (int i=0;i<nbChoices;i++) {
				costWizardFactory(costs, levels, nbcosts);
				if (isNewGemCost(costs, levels, nbcosts)) {
					costPanels[nbcosts] = new CostPanel(costs[nbcosts], levels[nbcosts]);
					costPanels[nbcosts].setBounds(200*nbcosts,0,200,300);
					panel.add(costPanels[nbcosts]);
					nbcosts++;
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
		panel.setBounds(0, 0, 200 * costPanels.length, 650);
		panel.setPreferredSize(new Dimension(200 * costPanels.length, 650));
		
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
	
	private boolean isNewGemCost(int costs[][], Integer levels[], int pos) {
		for (int i=0;i<pos;i++) {
			if (costs[i][0] == costs[pos][0] && costs[i][1] == costs[pos][1] && costs[i][2] == costs[pos][2] && levels[i] == levels[pos]) {
				return false;
			}
		}
		return true;
	}
	
	private void costBeastFactory(String costs[], Integer levels[], int pos) {
		Random r = new Random();
		int modulo = menu.getModulo1();
		int multiplier = menu.getMultiplier1();
		int u = r.nextInt(modulo-1)+1;
		if (u%4 == 3) {
			costs[pos] = "bone";
			u = (u*multiplier)%modulo;
			int lvlmax = 8;
			ArrayList<Integer> integerSeen = new ArrayList<Integer>();
			while (u%5 == 4 && !integerSeen.contains(u)) {
				integerSeen.add(u);
				u = (u * multiplier)%modulo;
				lvlmax++;
			}
			u = (u * multiplier)%modulo;
			levels[pos] = 2 + u%(lvlmax-1);
		} else {
			costs[pos] = "blood";
			u = (u*multiplier)%modulo;
			int lvlmax = 2;
			u = (u * multiplier)%modulo;
			ArrayList<Integer> integerSeen = new ArrayList<Integer>();
			while (u%4 == 3 && !integerSeen.contains(u)) {
				integerSeen.add(u);
				u = (u * multiplier)%modulo;
				lvlmax++;
			}
			u = (u * multiplier)%modulo;
			levels[pos] = 1 + u%(lvlmax);
		}
	}
	
	private void costRobotFactory(String costs[], Integer levels[], int pos) {
		costs[pos] = "energy";
		Random r = new Random();
		int modulo = menu.getModulo1();
		int multiplier = menu.getMultiplier1();
		int u = r.nextInt(modulo-1)+1;
		int lvlmax = 6;
		ArrayList<Integer> integerSeen = new ArrayList<Integer>();
		while (u%4 == 3 && !integerSeen.contains(u)) {
			integerSeen.add(u);
			u = (u * multiplier)%modulo;
			lvlmax++;
		}
		u = (u * multiplier)%modulo;
		levels[pos] = 1 + u%(lvlmax);
	}
	
	private void costUndeadFactory(String costs[], Integer levels[], int pos) {
		costs[pos] = "bone";
		Random r = new Random();
		int modulo = menu.getModulo1();
		int multiplier = menu.getMultiplier1();
		int u = r.nextInt(modulo-1)+1;
		int lvlmax = 10;
		ArrayList<Integer> integerSeen = new ArrayList<Integer>();
		while (u%4 == 3 && !integerSeen.contains(u)) {
			integerSeen.add(u);
			u = (u * multiplier)%modulo;
			lvlmax++;
		}
		u = (u * multiplier)%modulo;
		levels[pos] = 1 + u%(lvlmax);
		u = (u * multiplier)%modulo;
		levels[pos] = Math.min(levels[pos], 1 + u%(lvlmax));
	}
	
	private void costWizardFactory(int costs[][], Integer levels[], int pos) {
		Random r = new Random();
		int level = 0;
		int nbAnyMox = 0;
		int nbGreenMox = 0;
		int nbOrangeMox = 0;
		int nbBlueMox = 0;
		int modulo = menu.getModulo1();
		int multiplicator = menu.getMultiplier1();
		int u = r.nextInt(modulo-1)+1;
		int lvlmax = 1;
		ArrayList<Integer> integerSeen = new ArrayList<Integer>();
		while (u%4 == 3 && !integerSeen.contains(u)) {
			integerSeen.add(u);
			u = (u * multiplicator)%modulo;
			lvlmax++;
		}
		u = (u * multiplicator)%modulo;
		level = u%(lvlmax)+1;
		u = (u * multiplicator)%modulo;
		
		if (level == 1) {
			if (u%3 == 0) {
				nbGreenMox ++;
			}
			if (u%3 == 1) {
				nbOrangeMox ++;
			}
			if (u%3 == 2) {
				nbBlueMox ++;
			}
			u = (u * multiplicator)%modulo;
		}
		if (level == 2) {
			if (u%2 == 0) {
				//one color
				u = (u * multiplicator)%modulo;
				if (u%3 == 0) {
					nbGreenMox ++;
					u = (u * multiplicator)%modulo;
					if (u%2 == 0) {
						nbAnyMox ++;
					} else {
						nbGreenMox ++;
					}
				} else if (u%3 == 1) {
					nbOrangeMox ++;
					u = (u * multiplicator)%modulo;
					if (u%2 == 0) {
						nbAnyMox ++;
					} else {
						nbOrangeMox ++;
					}
				} else {
					nbBlueMox ++;
					u = (u * multiplicator)%modulo;
					if (u%2 == 0) {
						nbAnyMox ++;
					} else {
						nbBlueMox ++;
					}
				}
			} else {
				//two colors
				u = (u * multiplicator)%modulo;
				if (u%3 == 0) {
					nbOrangeMox ++;
					nbBlueMox ++;
					
				}
				if (u%3 == 1) {
					nbGreenMox ++;
					nbBlueMox ++;
				}
				if (u%3 == 2) {
					nbGreenMox ++;
					nbOrangeMox ++;
				}
			}
		}
		if (level>2) {
			if (u%3 == 0) {
				//one color
				u = (u * multiplicator)%modulo;
				int colorGem = u%3;
				u = (u * multiplicator)%modulo;
				int nbcoloredGem = 1+u%level;
				nbAnyMox += level - nbcoloredGem;
				if (colorGem == 0) {
					nbGreenMox += nbcoloredGem;
				}
				if (colorGem == 1) {
					nbOrangeMox += nbcoloredGem;
				}
				if (colorGem == 2) {
					nbBlueMox += nbcoloredGem;
				}
				u = (u * multiplicator)%modulo;
			} else if (u%3 == 1) {
				//two colors
				u = (u * multiplicator)%modulo;
				int absentColorGem = u%3;
				u = (u * multiplicator)%modulo;
				int nbcoloredGem = 1+u%(level/2);
				nbAnyMox += level - 2 *nbcoloredGem;
				if (absentColorGem == 0) {
					nbOrangeMox += nbcoloredGem;
					nbBlueMox += nbcoloredGem;
				}
				if (absentColorGem == 1) {
					nbGreenMox += nbcoloredGem;
					nbBlueMox += nbcoloredGem;
				}
				if (absentColorGem == 2) {
					nbGreenMox += nbcoloredGem;
					nbOrangeMox += nbcoloredGem;
				}
				u = (u * multiplicator)%modulo;
			} else {
				//all colors
				u = (u * multiplicator)%modulo;
				int nbcoloredGem = 1+u%(level/3);
				nbAnyMox += level - 3 *nbcoloredGem;
				nbGreenMox += nbcoloredGem;
				nbOrangeMox += nbcoloredGem;
				nbBlueMox += nbcoloredGem;
				u = (u * multiplicator)%modulo;
			}
		}
		
		costs[pos][0] = nbGreenMox;
		costs[pos][1] = nbOrangeMox;
		costs[pos][2] = nbBlueMox;
		levels[pos] = level;
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
