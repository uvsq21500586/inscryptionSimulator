package frames;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import cards.BeastCard;
import cards.Card;
import cards.RobotCard;
import cards.UndeadCard;
import cards.WizardCard;
import effects.Effect;
import frames.duelbuttons.*;
import frames.menubuttons.ButtonToDuel;

public class Duel extends JFrame {
	private ButtonPlaceCard buttonPlaceCard[];
	private ButtonMainDeck buttonMainDeck;
	private ButtonSourceDeck buttonSourceDeck;
	private LeftButton buttonLeft;
	private RightButton buttonRight;
	private NextTurnButton nextTurnButton;
	private JLayeredPane panel;
	private List<CardPanel> handCard1 = new ArrayList<>();
	private List<CardPanel> handCard2 = new ArrayList<>();
	private List<Card> mainDeck1 = new ArrayList<>();
	private List<Card> mainDeck2 = new ArrayList<>();
	private List<Card> sourceDeck1 = new ArrayList<>();
	private List<Card> sourceDeck2 = new ArrayList<>();
	private HandCardPanel handPanel;
	private JPanel handPanelArea;
	private int idFirstCard = 0;
	private JLabel bonePile;
	private JLabel bonePileCount;
	private Integer boneP1 = 0;
	private Integer boneP2 = 0;
	private JLabel energyPile;
	private Integer energy = 1;
	private Integer energymax = 1;
	private JLabel energyPileCount;
	private Integer balance = 0;
	private JLabel balanceLabel;
	private Integer lifeP1 = 5;
	private Integer lifeP2 = 5;
	private JLabel activedCard;
	private boolean turnJ2 = false;
	private JLabel turnLabel;
	private boolean isFirstTurn = true;
	private int starvationP1 = 0;
	private int starvationP2 = 0;
	
	public Duel() {
		super("Duel");
	}
	
	public void openTest() throws IOException, FontFormatException {
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		buildDecks();
		putObjectsForDuel(font);
		drawBegininCards();
		drawHandCard();
		putButtonsAndControler(font);
	}
	
	public void open(List<Card> maindeck1, List<Card> maindeck2, List<Card> sourcedeck1, List<Card> sourcedeck2, int lifeP1, int lifeP2) throws IOException, FontFormatException {
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		mainDeck1 = buildDeck(maindeck1);
		mainDeck2 = buildDeck(maindeck2);
		sourceDeck1 = buildDeck(sourcedeck1);
		sourceDeck2 = buildDeck(sourcedeck2);
		putObjectsForDuel(font);
		drawBegininCardsTrueDuel();
		drawHandCard();
		putButtonsAndControler(font);
		this.lifeP1 = lifeP1;
		this.lifeP2 = lifeP2;
	}
	
	private void drawBegininCardsTrueDuel() throws IOException, FontFormatException {
		// TODO Auto-generated method stub
		handCard1.add(new CardPanel(sourceDeck1.get(0)));
		handCard1.add(new CardPanel(mainDeck1.get(0)));
		handCard1.add(new CardPanel(mainDeck1.get(1)));
		handCard1.add(new CardPanel(mainDeck1.get(2)));
		handCard2.add(new CardPanel(sourceDeck2.get(0)));
		handCard2.add(new CardPanel(mainDeck2.get(0)));
		handCard2.add(new CardPanel(mainDeck2.get(1)));
		handCard2.add(new CardPanel(mainDeck2.get(2)));
		mainDeck1.remove(0);
		mainDeck1.remove(0);
		mainDeck1.remove(0);
		mainDeck2.remove(0);
		mainDeck2.remove(0);
		mainDeck2.remove(0);
		sourceDeck1.remove(0);
		sourceDeck2.remove(0);
		
	}

	private List<Card> buildDeck(List<Card> maindeck) throws IOException {
		Random r = new Random();
		List<Card> mainDeckToPlay = new LinkedList<>(Arrays.asList());
		for (int i=0;i<maindeck.size();i++) {
			if (maindeck.get(i) instanceof BeastCard) {
				BeastCard newcard = (BeastCard) maindeck.get(i);
				mainDeckToPlay.add(r.nextInt(i+1), newcard.cloneCard(newcard));
			} else if (maindeck.get(i) instanceof RobotCard) {
				RobotCard newcard = (RobotCard) maindeck.get(i);
				mainDeckToPlay.add(r.nextInt(i+1), newcard.cloneCard(newcard));
			} else if (maindeck.get(i) instanceof UndeadCard) {
				UndeadCard newcard = (UndeadCard) maindeck.get(i);
				mainDeckToPlay.add(r.nextInt(i+1), newcard.cloneCard(newcard));
			} else {
				WizardCard newcard = (WizardCard) maindeck.get(i);
				mainDeckToPlay.add(r.nextInt(i+1), newcard.cloneCard(newcard));
			}
			
		}
		return mainDeckToPlay;
	}

	private void putButtonsAndControler(Font font) throws IOException, FontFormatException {
		Image img;
		buttonLeft = new LeftButton();
		buttonRight = new RightButton();
		nextTurnButton = new NextTurnButton();
		panel.add(nextTurnButton,0);
		panel.add(buttonLeft,0);
		panel.add(buttonRight,0);
		nextTurnButton.setBounds(975, 500, 50, 50);
		buttonLeft.setBounds(0, 800, 50, 50);
		buttonRight.setBounds(850, 800, 50, 50);
		activedCard = new JLabel();
		img = ImageIO.read(new File("img/activedCard.png"));
		activedCard.setIcon(new ImageIcon(img
				.getScaledInstance(200, 300, 
				Image.SCALE_DEFAULT)));
		panel.add(activedCard,0);
		activedCard.setVisible(false);
		
		turnLabel = new JLabel("turn: P1");
		panel.add(turnLabel,0);
		turnLabel.setFont(font.deriveFont(Font.BOLD,24f));
		turnLabel.setForeground(Color.WHITE);
		turnLabel.setBounds(1300,200,200,50);
		//panel.add(handPanel,0);
		JScrollPane jscrollpane = new JScrollPane(panel);
		this.add(jscrollpane, BorderLayout.CENTER);
		this.setVisible(true);
		new DuelControler(this);
	}

	private void putObjectsForDuel(Font font) throws IOException {
		Image img = ImageIO.read(new File("img/wood-background.jpeg"));
		//handPanel = new JPanel();
		panel = new JLayeredPane();
		panel.setBounds(0, 0, 1530, 1010);
		panel.setPreferredSize(new Dimension(1530, 1010));
		panel.setLayout(null);
		//frame.addKeyListener((KeyListener) panelTerrain);
		JLabel labelBackground = new JLabel("");
		labelBackground.setBounds(0, 0, panel.getWidth(), panel.getHeight());
		panel.add(labelBackground, 2);
		labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(panel.getWidth(), panel.getHeight(), 
							Image.SCALE_DEFAULT)));
		this.setSize(1530, 810);
		//JScrollBar scrollBarV = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 500);
		//this.getContentPane().add(scrollBarV, BorderLayout.EAST);
		
		img = ImageIO.read(new File("img/costs/bone.png"));
		bonePile = new JLabel();
		bonePile.setIcon(new ImageIcon(img
				.getScaledInstance(50, 50, 
				Image.SCALE_DEFAULT)));
		
		img = ImageIO.read(new File("img/costs/energy.png"));
		energyPile = new JLabel();
		energyPile.setIcon(new ImageIcon(img
				.getScaledInstance(30, 50, 
				Image.SCALE_DEFAULT)));
		
		bonePileCount = new JLabel(": " + boneP1.toString());
		bonePileCount.setFont(font.deriveFont(Font.BOLD,24f));
		bonePileCount.setForeground(Color.WHITE);
		energyPileCount = new JLabel(": " + energy + "/" + energymax);
		energyPileCount.setFont(font.deriveFont(Font.BOLD,24f));
		energyPileCount.setForeground(Color.WHITE);
		balanceLabel = new JLabel("Balance: " + balance);
		balanceLabel.setFont(font.deriveFont(Font.BOLD,24f));
		balanceLabel.setForeground(Color.WHITE);
		panel.add(bonePile,0);
		panel.add(bonePileCount,0);
		panel.add(balanceLabel,0);
		bonePile.setBounds(1000, 100, 50, 50);
		bonePileCount.setBounds(1050, 100, 100, 50);
		balanceLabel.setBounds(1050, 200, 200, 50);
		panel.add(energyPile,0);
		panel.add(energyPileCount,0);
		energyPile.setBounds(1000, 150, 50, 50);
		energyPileCount.setBounds(1050, 150, 200, 50);
		setPlaceCardButtons();
		for (int i=0;i<8;i++) {
			panel.add(buttonPlaceCard[i],0);
		}
		panel.add(buttonMainDeck,0);
		panel.add(buttonSourceDeck,0);
	}
	
	private void setPlaceCardButtons() throws IOException {
		buttonPlaceCard = new ButtonPlaceCard[8];
		for (int i=0;i<8;i++) {
			buttonPlaceCard[i] = new ButtonPlaceCard();
			buttonPlaceCard[i].setBounds(100 + 200 * (i%4), 310 - 300 * (i/4), 200, 300);
			Image img = ImageIO.read(new File("img/place.png"));
			buttonPlaceCard[i].setIcon(new ImageIcon(img
								.getScaledInstance(200, 300, 
								Image.SCALE_DEFAULT)));
		}
		buttonMainDeck = new ButtonMainDeck();
		buttonMainDeck.setBounds(1100, 450, 200, 300);
		Image img = ImageIO.read(new File("img/maincard.png"));
		buttonMainDeck.setIcon(new ImageIcon(img
				.getScaledInstance(200, 300, 
				Image.SCALE_DEFAULT)));
		
		buttonSourceDeck = new ButtonSourceDeck();
		buttonSourceDeck.setBounds(1300, 450, 200, 300);
		img = ImageIO.read(new File("img/source.png"));
		buttonSourceDeck.setIcon(new ImageIcon(img
				.getScaledInstance(200, 300, 
				Image.SCALE_DEFAULT)));
		
		this.repaint();
		this.revalidate();
	}
	
	private void buildDecks() throws IOException {
		//sourceDeck1.add(WizardCard.sourceCard(1, Arrays.asList(new Effect("orange_gem","wizard",1))));
		//sourceDeck1.add(UndeadCard.sourceCard(1, 1, Arrays.asList(new Effect("brittle","undead"))));
		//sourceDeck1.add(WizardCard.sourceCard(1, Arrays.asList(new Effect("orange_gem","wizard",1))));
		//sourceDeck1.add(WizardCard.sourceCard(1, Arrays.asList(new Effect("orange_gem","wizard",1))));
		//sourceDeck2.add(WizardCard.sourceCard(1, Arrays.asList(new Effect("orange_gem","wizard",1))));
		sourceDeck2.add(BeastCard.sourceCard(1, new ArrayList<>()));
		sourceDeck1.add(BeastCard.sourceCard(1, new ArrayList<>()));
		sourceDeck2.add(BeastCard.sourceCard(1, new ArrayList<>()));
		sourceDeck1.add(BeastCard.sourceCard(1, new ArrayList<>()));
		sourceDeck2.add(BeastCard.sourceCard(1, new ArrayList<>()));
		List<Effect> effects = new LinkedList<>(Arrays.asList(new Effect("trifurcated_strike","robot"), new Effect("bifurcated_strike","robot")));
		List<Effect> effects2 = new LinkedList<>(Arrays.asList(new Effect("detonator","undead",1)));
		List<Effect> effects3 = new LinkedList<>(Arrays.asList(new Effect("ruby_heart","wizard",1), new Effect("emerald_heart","wizard",1)));
		List<Effect> effects4 = new ArrayList<>();
		effects4.add(new Effect("loose_tail_right","beast",1));
		mainDeck1.add(RobotCard.mainCard("s0n1a", 1, 1, 1, effects));
		//mainDeck1.add(UndeadCard.mainCard("bone_lord", 1, 1, 1, effects2));
		mainDeck1.add(WizardCard.mainCard("alchemist", 0, 0, 1, 0, 1, 1, 0, Arrays.asList(new Effect("gem_animator","wizard",2))));
		mainDeck1.add(WizardCard.mainCard("blue_mage", 0, 0, 1, 0, 1, 1, 1, effects3));
		mainDeck1.add(BeastCard.mainCard("kingfisher", "blood", 1, 1, 1, effects4));
		mainDeck1.add(BeastCard.mainCard("kingfisher", "blood", 1, 1, 1, Arrays.asList(new Effect("bee_within","beast",1))));
		mainDeck1.add(BeastCard.mainCard("kingfisher", "bone", 2, 1, 1, new ArrayList<>()));
		mainDeck1.add(BeastCard.mainCard("kingfisher", "bone", 1, 1, 1, new ArrayList<>()));
		//mainDeck2.add(RobotCard.mainCard("s0n1a", 1, 5, 1, effects2));
		mainDeck2.add(WizardCard.mainCard("alchemist", 0, 0, 1, 0, 1, 1, 0, Arrays.asList(new Effect("gem_animator","wizard",2))));
		mainDeck2.add(WizardCard.mainCard("blue_mage", 0, 0, 1, 0, 1, 1, 1, effects3));
		mainDeck2.add(UndeadCard.mainCard("bone_lord", 1, 1, 1, effects2));
		mainDeck2.add(BeastCard.mainCard("kingfisher", "blood", 1, 1, 1, new ArrayList<>()));
		mainDeck2.add(BeastCard.mainCard("kingfisher", "blood", 1, 1, 1, new ArrayList<>()));
		mainDeck2.add(BeastCard.mainCard("kingfisher", "bone", 1, 1, 1, new ArrayList<>()));
	}
	
	public void drawBegininCards() throws IOException, FontFormatException {
		handCard1.add(new CardPanel(sourceDeck1.get(0)));
		handCard1.add(new CardPanel(sourceDeck1.get(1)));
		//handCard1.add(new CardPanel(sourceDeck1.get(2)));
		handCard1.add(new CardPanel(mainDeck1.get(0)));
		handCard1.add(new CardPanel(mainDeck1.get(1)));
		handCard1.add(new CardPanel(mainDeck1.get(2)));
		//handCard1.add(new CardPanel(mainDeck1.get(1)));
		handCard2.add(new CardPanel(sourceDeck2.get(0)));
		handCard2.add(new CardPanel(sourceDeck2.get(1)));
		handCard2.add(new CardPanel(mainDeck2.get(0)));
		handCard2.add(new CardPanel(mainDeck2.get(1)));
		handCard2.add(new CardPanel(mainDeck2.get(2)));
		mainDeck1.remove(0);
		mainDeck1.remove(0);
		mainDeck1.remove(0);
		mainDeck2.remove(0);
		mainDeck2.remove(0);
		mainDeck2.remove(0);
		sourceDeck1.remove(0);
		sourceDeck2.remove(0);
	}
	
	public void drawHandCard() throws IOException, FontFormatException {
		handPanel = new HandCardPanel(handCard1);
		//handPanel.setBounds(50, 650, 850, 330);
		int id = idFirstCard;
		while (id < handCard1.size() && id < idFirstCard+4) {
			panel.add(handCard1.get(id),0);
			handCard1.get(id).setBounds(50 + (id - idFirstCard) * 200, 650, 200, 300);
			id ++;
		}
	}
	public void drawMainDeckCard(DuelControler controler) throws IOException, FontFormatException {
		if (!turnJ2) {
			Card card = mainDeck1.get(0);
			mainDeck1.remove(0);
			handCard1.add(new CardPanel(card));
		} else {
			Card card = mainDeck2.get(0);
			mainDeck2.remove(0);
			handCard2.add(new CardPanel(card));
		}
		redrawHandCard(controler);
	}
	
	public void drawSourceDeckCard(DuelControler controler) throws IOException, FontFormatException {
		if (!turnJ2) {
			Card card = sourceDeck1.get(0);
			sourceDeck1.remove(0);
			handCard1.add(new CardPanel(card));
		} else {
			Card card = sourceDeck2.get(0);
			sourceDeck2.remove(0);
			handCard2.add(new CardPanel(card));
		}
		redrawHandCard(controler);
	}
	
	public void redrawHandCard(DuelControler controler) throws IOException, FontFormatException {
		this.handPanel.cardsPanels.forEach(cardPanel -> panel.remove(cardPanel));
		
		//handPanel.setBounds(50, 650, 850, 330);
		int id = idFirstCard;
		if (!turnJ2) {
			handPanel = new HandCardPanel(handCard1);
			while (id < handCard1.size() && id < idFirstCard+4) {
				panel.add(handCard1.get(id),0);
				handCard1.get(id).addMouseListener(controler);
				handCard1.get(id).setBounds(50 + (id - idFirstCard) * 200, 650, 200, 300);
				id ++;
			}
		} else {
			handPanel = new HandCardPanel(handCard2);
			while (id < handCard2.size() && id < idFirstCard+4) {
				panel.add(handCard2.get(id),0);
				handCard2.get(id).addMouseListener(controler);
				handCard2.get(id).setBounds(50 + (id - idFirstCard) * 200, 650, 200, 300);
				id ++;
			}
		}
		
		panel.repaint();
		panel.revalidate();
	}
	
	public void nextTurn(DuelControler duelControler) throws IOException, FontFormatException {
		//combat
		if (!turnJ2) {
			for (int i=0;i<4;i++) {
				if (buttonPlaceCard[i].getCardPanel() != null && buttonPlaceCard[i].getCardPanel().getCard().getAttack()>0) {
					buttonPlaceCard[i].getCardPanel().getCard().attackPlayer2(this, buttonPlaceCard, i, duelControler);
				}
			}
		} else {
			for (int i=0;i<4;i++) {
				if (buttonPlaceCard[i].getCardPanel() != null && buttonPlaceCard[i].getCardPanel().getCard().getAttack()>0) {
					buttonPlaceCard[i].getCardPanel().getCard().attackPlayer1(this, buttonPlaceCard, i, duelControler);
				}
			}
		}
		
		//familiar
		
		for (int i=0;i<4;i++) {
			if (buttonPlaceCard[i].getCardPanel() != null) {
				Card card = buttonPlaceCard[i].getCardPanel().getCard();
				boolean isFamiliar = card.getEffects().stream().anyMatch(effect -> effect.getName().equals("familiar"));
				if (isFamiliar) {
					if (!isTurnJ2()) {
						card.familiarP1(this, buttonPlaceCard, duelControler, i);
					} else {
						card.familiarP2(this, buttonPlaceCard, duelControler, i);
					}
				}
			}
		}
		for (int i=4;i<8;i++) {
			if (buttonPlaceCard[i].getCardPanel() != null) {
				Card card = buttonPlaceCard[i].getCardPanel().getCard();
				boolean isFamiliar = card.getEffects().stream().anyMatch(effect -> effect.getName().equals("familiar"));
				if (isFamiliar) {
					if (!isTurnJ2()) {
						card.familiarP2(this, buttonPlaceCard, duelControler, i);
						boneP2++;
					} else {
						card.familiarP1(this, buttonPlaceCard, duelControler, i);
						boneP1++;
					}
				}
			}
		}
		
		//resources generator (like bone_digger)
		for (int i=0;i<4;i++) {
			if (buttonPlaceCard[i].getCardPanel() != null) {
				Card card = buttonPlaceCard[i].getCardPanel().getCard();
				Optional<Effect> bone_digger = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_digger")).findFirst();
				if (bone_digger.isPresent()) {
					if (!isTurnJ2()) {
						boneP1+= bone_digger.get().getLevel();
					} else {
						boneP2+= bone_digger.get().getLevel();
					}
				}
			}
		}
		
		//poison or brittle?
		for (int i=0;i<4;i++) {
			CardPanel copycard = buttonPlaceCard[i].getCardPanel();
			if (copycard != null) {
				Card card = copycard.getCard();
				Optional<Effect> brittleEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("brittle")).findFirst();
				Optional<Effect> unkillableEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("unkillable")).findFirst();
				if (brittleEffect.isPresent()) {
					if (unkillableEffect.isPresent()) {
						//unkillable
						applyUnkillableEffect(i, copycard, card, unkillableEffect);
						if (!turnJ2) {
							Optional<Effect> bone_kingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
							if (bone_kingEffect.isPresent()) {
								setBoneP1(getBoneP1()+ 1 + 3*bone_kingEffect.get().getLevel());
							} else {
								setBoneP1(getBoneP1()+1);
							}
						} else {
							Optional<Effect> bone_kingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
							if (bone_kingEffect.isPresent()) {
								setBoneP2(getBoneP2()+ 1 + 3*bone_kingEffect.get().getLevel());
							} else {
								setBoneP2(getBoneP2()+1);
							}
						}
					} else {
						card.deadCard(this, buttonPlaceCard, i);
					}
					if (!turnJ2) {
						card.corpse_eaterEffectP1(this, buttonPlaceCard, duelControler, i);
					} else {
						card.corpse_eaterEffectP2(this, buttonPlaceCard, duelControler, i);
					}
					
				} else if (card.getPoisoned()>0) {
				card.setHp(card.getHp()-card.getPoisoned());
				copycard.getHp().setText(card.getHp().toString());
				if (card.getHp()<=0) {
					card.setPoisoned(0);
					
					if (unkillableEffect.isPresent()) {
						//unkillable
						applyUnkillableEffect(i, copycard, card, unkillableEffect);
						if (!turnJ2) {
							Optional<Effect> bone_kingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
							if (bone_kingEffect.isPresent()) {
								setBoneP1(getBoneP1()+ 1 + 3*bone_kingEffect.get().getLevel());
							} else {
								setBoneP1(getBoneP1()+1);
							}
						} else {
							Optional<Effect> bone_kingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("bone_king")).findFirst();
							if (bone_kingEffect.isPresent()) {
								setBoneP2(getBoneP2()+ 1 + 3*bone_kingEffect.get().getLevel());
							} else {
								setBoneP2(getBoneP2()+1);
							}
						}
					} else {
						card.deadCard(this, buttonPlaceCard, i);
					}
					
					if (!turnJ2) {
						card.corpse_eaterEffectP1(this, buttonPlaceCard, duelControler, i);
					} else {
						card.corpse_eaterEffectP2(this, buttonPlaceCard, duelControler, i);
					}
				}
				}
			}

		}
		
		//déplacements?
		List<CardPanel> cardsOnField = new ArrayList<CardPanel>();
		for (int i=0;i<4;i++) {
			CardPanel copycard = buttonPlaceCard[i].getCardPanel();
			if (copycard != null) {
				cardsOnField.add(copycard);
			}
		}
		for (int i=0;i<cardsOnField.size();i++) {
			cardsOnField.get(i).getCard().deplacement(this, buttonPlaceCard, cardsOnField.get(i).getFieldPosition());
		}
		
		//win?
		if (balance >= lifeP2) {
			JOptionPane.showMessageDialog(this, "Player 1 wins by (+ " + (balance-lifeP2) + "). Congratuation",
                    "INFORMATION",
                    JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		if (balance <= -lifeP1) {
			JOptionPane.showMessageDialog(this, "Player 2 wins by (+ " + (-balance-lifeP1) + "). Congratuation",
                    "INFORMATION",
                    JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		turnJ2 = !turnJ2;
		if (!turnJ2) {
			turnLabel.setText("turn: P1");
			buttonMainDeck.setEnabled(mainDeck1.size()>0);
			buttonSourceDeck.setEnabled(sourceDeck1.size()>0);
			bonePileCount.setText(": " + boneP1);
			energymax = Math.min(6, energymax+1);
			energy = energymax;
		} else {
			turnLabel.setText("turn: P2");
			buttonMainDeck.setEnabled(mainDeck2.size()>0);
			buttonSourceDeck.setEnabled(sourceDeck2.size()>0);
			bonePileCount.setText(": " + boneP2);
			energy = energymax;
		}
		energyPileCount.setText(": " + energy + "/" + energymax);
		for (int i=0;i<8;i++) {
			if (buttonPlaceCard[i].getCardPanel() != null) {
				panel.remove(buttonPlaceCard[i].getCardPanel());
			}
		}
		
		for (int i=0;i<4;i++) {
			CardPanel copycard = buttonPlaceCard[i].getCardPanel();
			buttonPlaceCard[i].setCardPanel(buttonPlaceCard[7-i].getCardPanel());
			buttonPlaceCard[7-i].setCardPanel(copycard);
			if (buttonPlaceCard[i].getCardPanel() != null) {
				buttonPlaceCard[i].getCardPanel().setBounds(100 + 200 * (i%4), 310 - 300 * (i/4), 200, 300);
				panel.add(buttonPlaceCard[i].getCardPanel(),0);
				buttonPlaceCard[i].getCardPanel().setFieldPosition(i);
				buttonPlaceCard[i].getCardPanel().addMouseListener(duelControler);
			}
			if (buttonPlaceCard[7-i].getCardPanel() != null) {
				buttonPlaceCard[7-i].getCardPanel().setBounds(100 + 200 * ((7-i)%4), 310 - 300 * ((7-i)/4), 200, 300);
				panel.add(buttonPlaceCard[7-i].getCardPanel(),0);
				buttonPlaceCard[7-i].getCardPanel().setFieldPosition(7-i);
				buttonPlaceCard[7-i].getCardPanel().addMouseListener(duelControler);
			}
		}
		//inverse direction
		for (int i=0;i<8;i++) {
			CardPanel copycardPanel = buttonPlaceCard[i].getCardPanel();
			if (copycardPanel != null) {
			Card copycard = copycardPanel.getCard();
			for (int j=0;j<copycard.getEffects().size();j++) {
				copycard.getEffects().get(j).inverseDirection(copycardPanel.getEffects()[j], copycard);
			}
			}
		}
		
		panel.repaint();
		idFirstCard = 0;
		try {
			redrawHandCard(duelControler);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!isFirstTurn) {
			if (!turnJ2) {
				if (mainDeck1.size()>0 || sourceDeck1.size()>0) {
					duelControler.setMustDrawCard(true);
				} else {
					starvationP1++;
					handCard2.add(new CardPanel(new BeastCard("starvation", "bone", 0, starvationP1, starvationP1, new ArrayList<>(), false)));
				}
				//evolution
				for (int i=0;i<4;i++) {
					CardPanel copycard = buttonPlaceCard[i].getCardPanel();
					if (copycard != null) {
						Card card = copycard.getCard();
						if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("fledgling"))) {
							card.setHpBase(card.getHpBase()+2);
							card.setHp(card.getHp()+2);
							card.setAttackBase(card.getAttackBase()+1);
							card.setAttack(card.getAttack()+1);
							Effect fledglingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("fledgling")).findFirst().get();
							int index = card.getEffects().indexOf(fledglingEffect);
							System.out.println(index);
							fledglingEffect.setLevel(fledglingEffect.getLevel()-1);
							if (fledglingEffect.getLevel() == 0) {
								card.getEffects().remove(index);
								copycard.remove(copycard.getEffects()[index]);
								copycard.remove(copycard.getLevelsEffects()[index]);
								copycard.redrawEffects();
							} else {
								copycard.getLevelsEffects()[index].setText(fledglingEffect.getLevel().toString());
							}
							copycard.getHp().setText(card.getHp().toString());
							copycard.getAttack().setText(card.getAttack().toString());
						}
					}
				}
				
			} else {
				if (mainDeck2.size()>0 || sourceDeck2.size()>0) {
					duelControler.setMustDrawCard(true);
				} else {
					starvationP2++;
					handCard1.add(new CardPanel(new BeastCard("starvation", "bone", 0, starvationP2, starvationP2, new ArrayList<>(), false)));
				}
				//evolution
				for (int i=0;i<4;i++) {
					CardPanel copycard = buttonPlaceCard[i].getCardPanel();
					if (copycard != null) {
						Card card = copycard.getCard();
						if (card.getEffects().stream().anyMatch(effect -> effect.getName().equals("fledgling"))) {
							card.setHpBase(card.getHpBase()+2);
							card.setHp(card.getHp()+2);
							card.setAttackBase(card.getAttackBase()+1);
							card.setAttack(card.getAttack()+1);
							Effect fledglingEffect = card.getEffects().stream().filter(effect -> effect.getName().equals("fledgling")).findFirst().get();
							int index = card.getEffects().indexOf(fledglingEffect);
							System.out.println(index);
							fledglingEffect.setLevel(fledglingEffect.getLevel()-1);
							if (fledglingEffect.getLevel() == 0) {
								card.getEffects().remove(index);
								copycard.remove(copycard.getEffects()[index]);
								copycard.remove(copycard.getLevelsEffects()[index]);
								copycard.redrawEffects();
							} else {
								copycard.getLevelsEffects()[index].setText(fledglingEffect.getLevel().toString());
							}
							copycard.getHp().setText(card.getHp().toString());
							copycard.getAttack().setText(card.getAttack().toString());
						}
					}
				}
			}
			
		} else {
			isFirstTurn = false;
		}
		
	}
	
	public void recalculateAttk(Card card, int position) {
		Integer attack = card.getAttack();
		if (position<4) {
			for (int i=0;i<4;i++ ) {
				if (i!=position && buttonPlaceCard[i].getCardPanel() != null) {
					//gem_animator
					if (card instanceof WizardCard && !card.isMainDeck()) {
						//mox
						Optional<Effect> gem_animator = buttonPlaceCard[i].getCardPanel().getCard().getEffects().stream()
								.filter(effect ->effect.getName().equals("gem_animator")).findFirst();
						if (gem_animator.isPresent()) {
							attack += gem_animator.get().getLevel();
						}
					}
				}
			}
			
		} else {
			for (int i=4;i<7;i++ ) {
				if (i!=position && buttonPlaceCard[i].getCardPanel() != null) {
					//gem_animator
					if (card instanceof WizardCard && !card.isMainDeck()) {
						//mox
						Optional<Effect> gem_animator = buttonPlaceCard[i].getCardPanel().getCard().getEffects().stream()
								.filter(effect ->effect.getName().equals("gem_animator")).findFirst();
						if (gem_animator.isPresent()) {
							attack += gem_animator.get().getLevel();
						}
					}
				}
			}
		}
		card.setAttack(attack);
		buttonPlaceCard[position].getCardPanel().getAttack().setText(attack.toString());
	}

	private void applyUnkillableEffect(int i, CardPanel copycard, Card card, Optional<Effect> unkillableEffect) {
		card.setAttackBase(card.getAttackBase() + (unkillableEffect.get().getLevel()-1)/3);
		card.setHpBase(card.getHpBase() + (unkillableEffect.get().getLevel()-1)/3);
		card.setPoisoned(0);
		copycard.getPoison().setVisible(false);
		int lvlSup = (unkillableEffect.get().getLevel()-1)%3;
		if (lvlSup == 1) {
			card.setHpBase(card.getHpBase() + 1);
		}
		if (lvlSup == 2) {
			card.setAttackBase(card.getAttackBase() + 1);
		}
		card.setAttack(card.getAttackBase());
		card.setHp(card.getHpBase());
		copycard.setPosition("onHand");
		copycard.getAttack().setText(card.getAttackBase().toString());
		copycard.getHp().setText(card.getHpBase().toString());
		if (!turnJ2) {
			getHandCard1().add(copycard);
		} else {
			getHandCard2().add(copycard);
		}
		getPanel().remove(copycard);
		buttonPlaceCard[i].setCardPanel(null);
	}
	
	public boolean playable(Card card) {
		if (card instanceof RobotCard) {
			return card.playable(buttonPlaceCard, energy);
		}
		if (isTurnJ2()) {
			return card.playable(buttonPlaceCard, boneP2);
		}
		return card.playable(buttonPlaceCard, boneP1);
	}
	
	public void player1Wins() {
	}
	
	public void player2Wins() {
	}

	public ButtonPlaceCard[] getButtonPlaceCard() {
		return buttonPlaceCard;
	}

	public void setButtonPlaceCard(ButtonPlaceCard[] buttonPlaceCard) {
		this.buttonPlaceCard = buttonPlaceCard;
	}

	public ButtonMainDeck getButtonMainDeck() {
		return buttonMainDeck;
	}

	public void setButtonMainDeck(ButtonMainDeck buttonMainDeck) {
		this.buttonMainDeck = buttonMainDeck;
	}

	public ButtonSourceDeck getButtonSourceDeck() {
		return buttonSourceDeck;
	}

	public void setButtonSourceDeck(ButtonSourceDeck buttonSourceDeck) {
		this.buttonSourceDeck = buttonSourceDeck;
	}

	public JLayeredPane getPanel() {
		return panel;
	}

	public void setPanel(JLayeredPane panel) {
		this.panel = panel;
	}

	public List<CardPanel> getHandCard1() {
		return handCard1;
	}

	public void setHandCard1(List<CardPanel> handCard1) {
		this.handCard1 = handCard1;
	}

	public List<CardPanel> getHandCard2() {
		return handCard2;
	}

	public void setHandCard2(List<CardPanel> handCard2) {
		this.handCard2 = handCard2;
	}

	public List<Card> getMainDeck1() {
		return mainDeck1;
	}

	public void setMainDeck1(List<Card> mainDeck1) {
		this.mainDeck1 = mainDeck1;
	}

	public List<Card> getMainDeck2() {
		return mainDeck2;
	}

	public void setMainDeck2(List<Card> mainDeck2) {
		this.mainDeck2 = mainDeck2;
	}

	public List<Card> getSourceDeck1() {
		return sourceDeck1;
	}

	public void setSourceDeck1(List<Card> sourceDeck1) {
		this.sourceDeck1 = sourceDeck1;
	}

	public List<Card> getSourceDeck2() {
		return sourceDeck2;
	}

	public void setSourceDeck2(List<Card> sourceDeck2) {
		this.sourceDeck2 = sourceDeck2;
	}

	public HandCardPanel getHandPanel() {
		return handPanel;
	}

	public void setHandPanel(HandCardPanel handPanel) {
		this.handPanel = handPanel;
	}

	public JPanel getHandPanelArea() {
		return handPanelArea;
	}

	public void setHandPanelArea(JPanel handPanelArea) {
		this.handPanelArea = handPanelArea;
	}

	public int getIdFirstCard() {
		return idFirstCard;
	}

	public void setIdFirstCard(int idFirstCard) {
		this.idFirstCard = idFirstCard;
	}

	public JLabel getBonePile() {
		return bonePile;
	}

	public void setBonePile(JLabel bonePile) {
		this.bonePile = bonePile;
	}

	public JLabel getBonePileCount() {
		return bonePileCount;
	}

	public void setBonePileCount(JLabel bonePileCount) {
		this.bonePileCount = bonePileCount;
	}

	public Integer getBoneP1() {
		return boneP1;
	}

	public void setBoneP1(Integer boneP1) {
		this.boneP1 = boneP1;
	}

	public Integer getBoneP2() {
		return boneP2;
	}

	public void setBoneP2(Integer boneP2) {
		this.boneP2 = boneP2;
	}

	public boolean isTurnJ2() {
		return turnJ2;
	}

	public void setTurnJ2(boolean turnJ2) {
		this.turnJ2 = turnJ2;
	}

	public LeftButton getButtonLeft() {
		return buttonLeft;
	}

	public void setButtonLeft(LeftButton buttonLeft) {
		this.buttonLeft = buttonLeft;
	}

	public RightButton getButtonRight() {
		return buttonRight;
	}

	public void setButtonRight(RightButton buttonRight) {
		this.buttonRight = buttonRight;
	}

	public NextTurnButton getNextTurnButton() {
		return nextTurnButton;
	}

	public void setNextTurnButton(NextTurnButton nextTurnButton) {
		this.nextTurnButton = nextTurnButton;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public JLabel getBalanceLabel() {
		return balanceLabel;
	}

	public void setBalanceLabel(JLabel balanceLabel) {
		this.balanceLabel = balanceLabel;
	}

	public JLabel getEnergyPile() {
		return energyPile;
	}

	public void setEnergyPile(JLabel energyPile) {
		this.energyPile = energyPile;
	}

	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	public Integer getEnergymax() {
		return energymax;
	}

	public void setEnergymax(Integer energymax) {
		this.energymax = energymax;
	}

	public JLabel getEnergyPileCount() {
		return energyPileCount;
	}

	public void setEnergyPileCount(JLabel energyPileCount) {
		this.energyPileCount = energyPileCount;
	}
	
	public int scavengerLevel() {
		//buttonPlaceCard
		int maxLevelScavenger = 0;
		for (int i=0;i<4;i++) {
			if (buttonPlaceCard[i].getCardPanel() != null) {
				Optional<Effect> scavengerEffect = buttonPlaceCard[i].getCardPanel().getCard().getEffects().stream().filter(effect -> effect.getName().equals("scavenger")).findFirst();
				if (scavengerEffect.isPresent() && scavengerEffect.get().getLevel()>maxLevelScavenger) {
					maxLevelScavenger = scavengerEffect.get().getLevel();
				}
			}
		}
		return maxLevelScavenger;
	}

}
