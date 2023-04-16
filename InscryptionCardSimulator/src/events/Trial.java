package events;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cards.Card;
import cards.CardFactory;
import cards.CardPanel;
import frames.Menu;

public class Trial  extends JFrame {
	List<CardPanel> cardsPanelsMainDeck = new ArrayList<>();
	List<CardPanel> cardsPanelsNewCards = new ArrayList<>();
	private JButton buttonValidate;
	private Menu menu;
	private TrialPanel trialPanels[];
	private CardPanel resultCard;
	private List<String> listTrialBeast = Arrays.asList("health","power", "wisdom", "blood", "bone");
	private List<String> listTrialRobot = Arrays.asList("health","power", "wisdom", "energy");
	private List<String> listTrialUndead = Arrays.asList("health","power", "wisdom", "bone");
	private List<String> listTrialWizard = Arrays.asList("health","power", "wisdom", "green", "orange", "blue", "prism");
	public final static Map<String, Integer> mapTrialToLevel = buildMapNamesTrialToLevel();
	
	public Trial(Menu menu, Integer nbChoices, Integer nbDrawnCardsSup) throws IOException, FontFormatException {
		super("Trial");
		this.menu = menu;
		Random r = new Random();
		this.setSize(1530, 950);
		JPanel panel = new JPanel(); 
		panel.setLayout(null);
		trialPanels = new TrialPanel[3];
		if (!menu.getTypecards1().equals("wizard")) {
			String symbols[] = new String[3];
			if (menu.getTypecards1().equals("beast")) {
				for (int i=0;i<3;i++) {
					int typeId = r.nextInt(listTrialBeast.size());
					symbols[i] = listTrialBeast.get(typeId);
					if (!isNewCost(symbols, i)) {
						i--;
					} else {
						trialPanels[i] = new TrialPanel(symbols[i], mapTrialToLevel.get(symbols[i])+Integer.parseInt(menu.getDifficultyP2().getText()));
						trialPanels[i].setBounds(200*i,0,200,300);
						panel.add(trialPanels[i]);
					}
				}
				
			} else if (menu.getTypecards1().equals("robot")) {
				for (int i=0;i<3;i++) {
					int typeId = r.nextInt(listTrialRobot.size());
					symbols[i] = listTrialRobot.get(typeId);
					if (!isNewCost(symbols, i)) {
						i--;
					} else {
						trialPanels[i] = new TrialPanel(symbols[i], mapTrialToLevel.get(symbols[i])+Integer.parseInt(menu.getDifficultyP2().getText()));
						trialPanels[i].setBounds(200*i,0,200,300);
						panel.add(trialPanels[i]);
					}
				}
			} else if (menu.getTypecards1().equals("undead")) {
				for (int i=0;i<3;i++) {
					int typeId = r.nextInt(listTrialUndead.size());
					symbols[i] = listTrialUndead.get(typeId);
					if (!isNewCost(symbols, i)) {
						i--;
					} else {
						trialPanels[i] = new TrialPanel(symbols[i], mapTrialToLevel.get(symbols[i])+Integer.parseInt(menu.getDifficultyP2().getText()));
						trialPanels[i].setBounds(200*i,0,200,300);
						panel.add(trialPanels[i]);
					}
				}
			}
			
		} else {
			String symbols[] = new String[3];
			for (int i=0;i<3;i++) {
				int typeId = r.nextInt(listTrialWizard.size());
				symbols[i] = listTrialWizard.get(typeId);
				if (!isNewCost(symbols, i)) {
					i--;
				} else {
					trialPanels[i] = new TrialPanel(symbols[i], mapTrialToLevel.get(symbols[i])+Integer.parseInt(menu.getDifficultyP2().getText()));
					trialPanels[i].setBounds(200*i,0,200,300);
					panel.add(trialPanels[i]);
				}
			}
		}
		
		resultCard = new CardPanel();
		this.getContentPane().add(resultCard);
		resultCard.setBounds(50,400,200,300);
		
		List<Card> boosterMain = new ArrayList<>();
		List<Card> drawCardsMainDeck = new ArrayList<>();
		for(int i=0;i<nbChoices;i++) {
			Card card1 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1(),1,false);
			Card card2 = CardFactory.mainCard(
					menu.getModulo1(),
					menu.getMultiplier1(),
					menu.getGlobalStrenght1(),
					menu.getRarityStrenght1(),
					r.nextInt(menu.getModulo1()-1)+1,
					menu.getTypecards1(),1,false);
			if (card2.getRarity() < card1.getRarity()) {
				boosterMain.add(card2);
			} else {
				boosterMain.add(card1);
			}
			
		}
		for(int i=0;i<3+nbDrawnCardsSup;i++) {
			Card card = menu.getMainDeck1().get(r.nextInt( menu.getMainDeck1().size()));
			if (!drawCardsMainDeck.contains(card)) {
				drawCardsMainDeck.add(card);
			}
		}
		
		
		for(int i=0;i<drawCardsMainDeck.size();i++) {
			cardsPanelsMainDeck.add(new CardPanel(drawCardsMainDeck.get(i)));
			panel.add(cardsPanelsMainDeck.get(i));
			cardsPanelsMainDeck.get(i).setBounds(200*i, 310, 200, 300);
			cardsPanelsMainDeck.get(i).setVisible(false);
		}
		
		for(int i=0;i<boosterMain.size();i++) {
			cardsPanelsNewCards.add(new CardPanel(boosterMain.get(i)));
			panel.add(cardsPanelsNewCards.get(i));
			cardsPanelsNewCards.get(i).setBounds(200*i, 620, 200, 300);
			cardsPanelsNewCards.get(i).setVisible(false);
		}
		
		
		buttonValidate = new JButton("Validate");
		buttonValidate.setBounds(650,150,100,50);
		buttonValidate.setEnabled(false);
		panel.add(buttonValidate);
		panel.setBounds(0, 0, 200 * cardsPanelsMainDeck.size(), 650);
		panel.setPreferredSize(new Dimension(200 * cardsPanelsMainDeck.size(), 650));
		
		JScrollPane jscrollpane = new JScrollPane(panel);
		
		this.add(jscrollpane, BorderLayout.CENTER);
		
		this.setVisible(true);
		new TrialControler(this);
	}
	
	private static Map<String, Integer> buildMapNamesTrialToLevel() {
		// TODO Auto-generated method stub
		Map<String, Integer> trialsToLevel = new HashMap<>();
		trialsToLevel.put("health",6);
		trialsToLevel.put("power",4);
		trialsToLevel.put("wisdom",3);
		trialsToLevel.put("blood",4);
		trialsToLevel.put("bone",5);
		trialsToLevel.put("energy",10);
		trialsToLevel.put("green",3);
		trialsToLevel.put("orange",3);
		trialsToLevel.put("blue",3);
		trialsToLevel.put("prism",2);

		return trialsToLevel;
	}

	private boolean isNewCost(String costs[], int pos) {
		for (int i=0;i<pos;i++) {
			if (costs[i].equals(costs[pos])) {
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

	public CardPanel getResultCard() {
		return resultCard;
	}

	public void setResultCard(CardPanel resultCard) {
		this.resultCard = resultCard;
	}

	public List<CardPanel> getCardsPanelsMainDeck() {
		return cardsPanelsMainDeck;
	}

	public void setCardsPanelsMainDeck(List<CardPanel> cardsPanelsMainDeck) {
		this.cardsPanelsMainDeck = cardsPanelsMainDeck;
	}

	public List<CardPanel> getCardsPanelsNewCards() {
		return cardsPanelsNewCards;
	}

	public void setCardsPanelsNewCards(List<CardPanel> cardsPanelsNewCards) {
		this.cardsPanelsNewCards = cardsPanelsNewCards;
	}

	public TrialPanel[] getTrialPanels() {
		return trialPanels;
	}

	public void setTrialPanels(TrialPanel[] trialPanels) {
		this.trialPanels = trialPanels;
	}
	
	
	

}
