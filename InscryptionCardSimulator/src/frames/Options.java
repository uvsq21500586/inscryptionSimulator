package frames;

import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

import cards.CardPanel;
import frames.menubuttons.ButtonToDuel;

public class Options extends JFrame {
	private String listTypes[] = {"beast", "robot", "undead","wizard"}; 
	
	private Menu menu;
	
	private JTextArea moduloTextP1;
	private JTextArea multiplicatorTextP1;
	private JTextArea globalstrenghP1;
	private JTextArea raritystrenghP1;
	private JList<String> typeCardP1;
	private JTextArea lifePointsP1;
	private JTextArea nbMainCardsP1;
	private JTextArea nbSourceCardsP1;
	
	private JTextArea moduloTextP2;
	private JTextArea multiplicatorTextP2;
	private JTextArea globalstrenghP2;
	private JTextArea raritystrenghP2;
	private JList<String> typeCardP2;
	private JTextArea lifePointsP2;
	private JTextArea nbMainCardsP2;
	private JTextArea nbSourceCardsP2;
	private JTextArea nbSupCardsP2;
	
	private JButton saveButton;

	public Options() {
		super("Options");
	}


	public void open(Menu menu) throws IOException, FontFormatException {
		this.menu = menu;
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		Image img = ImageIO.read(new File("img/wood-background.jpeg"));
		JPanel panelBackground = new JPanel();
		panelBackground.setBounds(0, 0, 1500, 800);
		panelBackground.setLayout(null);
		//frame.addKeyListener((KeyListener) panelTerrain);
		JLabel labelBackground = new JLabel("");
		labelBackground.setBounds(0, 0, panelBackground.getWidth(), panelBackground.getHeight());
		panelBackground.add(labelBackground);
		labelBackground.setIcon(new ImageIcon(img
							.getScaledInstance(panelBackground.getWidth(), panelBackground.getHeight(), 
							Image.SCALE_DEFAULT)));
		this.setSize(1500, 800);
		setButtons();
		
		JLabel labelP1Type = new JLabel("type P1: ");
		labelP1Type.setBounds(60,50,120,50);
		this.getContentPane().add(labelP1Type);
		labelP1Type.setFont(font.deriveFont(Font.BOLD,20f));
		labelP1Type.setForeground(Color.WHITE);
		JLabel labelP1modulo = new JLabel("modulo P1: ");
		labelP1modulo.setBounds(400,50,150,50);
		this.getContentPane().add(labelP1modulo);
		labelP1modulo.setFont(font.deriveFont(Font.BOLD,20f));
		labelP1modulo.setForeground(Color.WHITE);
		JLabel labelP1multiplicator = new JLabel("multiplier P1: ");
		labelP1multiplicator.setBounds(670,50,200,50);
		this.getContentPane().add(labelP1multiplicator);
		labelP1multiplicator.setFont(font.deriveFont(Font.BOLD,20f));
		labelP1multiplicator.setForeground(Color.WHITE);
		JLabel globalstrenghP1Label = new JLabel("global strengh P1: ");
		globalstrenghP1Label.setBounds(180,200,300,50);
		this.getContentPane().add(globalstrenghP1Label);
		globalstrenghP1Label.setFont(font.deriveFont(Font.BOLD,20f));
		globalstrenghP1Label.setForeground(Color.WHITE);
		JLabel raritystrenghP1Label = new JLabel("rarity strengh P1: ");
		raritystrenghP1Label.setBounds(680,200,300,50);
		this.getContentPane().add(raritystrenghP1Label);
		raritystrenghP1Label.setFont(font.deriveFont(Font.BOLD,20f));
		raritystrenghP1Label.setForeground(Color.WHITE);
		JLabel lpP1Label = new JLabel("life points P1: ");
		lpP1Label.setBounds(1000,50,300,50);
		this.getContentPane().add(lpP1Label);
		lpP1Label.setFont(font.deriveFont(Font.BOLD,20f));
		lpP1Label.setForeground(Color.WHITE);
		JLabel nbMainCardsP1Label = new JLabel("nb main cards P1: ");
		nbMainCardsP1Label.setBounds(1100,150,300,50);
		this.getContentPane().add(nbMainCardsP1Label);
		nbMainCardsP1Label.setFont(font.deriveFont(Font.BOLD,16f));
		nbMainCardsP1Label.setForeground(Color.WHITE);
		JLabel nbSourceCardsP1Label = new JLabel("nb source cards P1: ");
		nbSourceCardsP1Label.setBounds(1100,220,300,50);
		this.getContentPane().add(nbSourceCardsP1Label);
		nbSourceCardsP1Label.setFont(font.deriveFont(Font.BOLD,16f));
		nbSourceCardsP1Label.setForeground(Color.WHITE);
		
		typeCardP1 = new JList<String>(listTypes);
		typeCardP1.setSelectedIndex(0);
		typeCardP1.setSelectedValue(menu.getTypecards1(), rootPaneCheckingEnabled);
		this.getContentPane().add(typeCardP1);
		typeCardP1.setBounds(200,50,140,100);
		moduloTextP1 = new JTextArea(menu.getModulo1().toString());
		moduloTextP1.setBounds(550,50,100,50);
		moduloTextP1.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(moduloTextP1);
		multiplicatorTextP1 = new JTextArea(menu.getMultiplier1().toString());
		multiplicatorTextP1.setBounds(880,50,100,50);
		multiplicatorTextP1.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(multiplicatorTextP1);
		globalstrenghP1 = new JTextArea(menu.getGlobalStrenght1().toString());
		globalstrenghP1.setBounds(430,200,100,50);
		globalstrenghP1.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(globalstrenghP1);
		raritystrenghP1 = new JTextArea(menu.getRarityStrenght1().toString());
		raritystrenghP1.setBounds(930,200,100,50);
		raritystrenghP1.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(raritystrenghP1);
		lifePointsP1 = new JTextArea(menu.getLifePointsP1().toString());
		lifePointsP1.setBounds(1300,50,100,50);
		lifePointsP1.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(lifePointsP1);
		nbMainCardsP1 = new JTextArea(menu.getNbMainCards1().toString());
		nbMainCardsP1.setBounds(1350,150,100,50);
		nbMainCardsP1.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(nbMainCardsP1);
		nbSourceCardsP1 = new JTextArea(menu.getNbSourceCards1().toString());
		nbSourceCardsP1.setBounds(1350,220,100,50);
		nbSourceCardsP1.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(nbSourceCardsP1);
		
		
		JLabel labelP2Type = new JLabel("type P2: ");
		labelP2Type.setBounds(60,350,120,50);
		this.getContentPane().add(labelP2Type);
		labelP2Type.setFont(font.deriveFont(Font.BOLD,20f));
		labelP2Type.setForeground(Color.WHITE);
		JLabel labelP2modulo = new JLabel("modulo P2: ");
		labelP2modulo.setBounds(380,350,170,50);
		this.getContentPane().add(labelP2modulo);
		labelP2modulo.setFont(font.deriveFont(Font.BOLD,20f));
		labelP2modulo.setForeground(Color.WHITE);
		JLabel labelP2multiplicator = new JLabel("multiplier P2: ");
		labelP2multiplicator.setBounds(670,350,200,50);
		this.getContentPane().add(labelP2multiplicator);
		labelP2multiplicator.setFont(font.deriveFont(Font.BOLD,20f));
		labelP2multiplicator.setForeground(Color.WHITE);
		JLabel globalstrenghP2Label = new JLabel("global strengh P2: ");
		globalstrenghP2Label.setBounds(180,500,300,50);
		this.getContentPane().add(globalstrenghP2Label);
		globalstrenghP2Label.setFont(font.deriveFont(Font.BOLD,20f));
		globalstrenghP2Label.setForeground(Color.WHITE);
		JLabel raritystrenghP2Label = new JLabel("rarity strengh P2: ");
		raritystrenghP2Label.setBounds(680,500,300,50);
		this.getContentPane().add(raritystrenghP2Label);
		raritystrenghP2Label.setFont(font.deriveFont(Font.BOLD,20f));
		raritystrenghP2Label.setForeground(Color.WHITE);
		JLabel lpP2Label = new JLabel("life points P2: ");
		lpP2Label.setBounds(1000,350,300,50);
		this.getContentPane().add(lpP2Label);
		lpP2Label.setFont(font.deriveFont(Font.BOLD,20f));
		lpP2Label.setForeground(Color.WHITE);
		JLabel nbMainCardsP2Label = new JLabel("nb main cards P2: ");
		nbMainCardsP2Label.setBounds(1100,450,300,50);
		this.getContentPane().add(nbMainCardsP2Label);
		nbMainCardsP2Label.setFont(font.deriveFont(Font.BOLD,16f));
		nbMainCardsP2Label.setForeground(Color.WHITE);
		JLabel nbSourceCardsP2Label = new JLabel("nb source cards P2: ");
		nbSourceCardsP2Label.setBounds(1100,520,300,50);
		this.getContentPane().add(nbSourceCardsP2Label);
		nbSourceCardsP2Label.setFont(font.deriveFont(Font.BOLD,16f));
		nbSourceCardsP2Label.setForeground(Color.WHITE);
		JLabel nbSupCardsP2Label = new JLabel("nb cards sup. P2: ");
		nbSupCardsP2Label.setBounds(180,620,300,50);
		this.getContentPane().add(nbSupCardsP2Label);
		nbSupCardsP2Label.setFont(font.deriveFont(Font.BOLD,16f));
		nbSupCardsP2Label.setForeground(Color.WHITE);
		
		typeCardP2 = new JList<String>(listTypes);
		typeCardP2.setSelectedIndex(0);
		typeCardP2.setSelectedValue(menu.getTypecards2(), rootPaneCheckingEnabled);
		this.getContentPane().add(typeCardP2);
		typeCardP2.setBounds(200,350,140,100);
		moduloTextP2 = new JTextArea(menu.getModulo2().toString());
		moduloTextP2.setBounds(550,350,100,50);
		moduloTextP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(moduloTextP2);
		multiplicatorTextP2 = new JTextArea(menu.getMultiplier2().toString());
		multiplicatorTextP2.setBounds(880,350,100,50);
		multiplicatorTextP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(multiplicatorTextP2);
		globalstrenghP2 = new JTextArea(menu.getGlobalStrenght2().toString());
		globalstrenghP2.setBounds(440,500,100,50);
		globalstrenghP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(globalstrenghP2);
		raritystrenghP2 = new JTextArea(menu.getRarityStrenght2().toString());
		raritystrenghP2.setBounds(930,500,100,50);
		raritystrenghP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(raritystrenghP2);
		lifePointsP2 = new JTextArea(menu.getLifePointsP2().toString());
		lifePointsP2.setBounds(1300,350,100,50);
		lifePointsP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(lifePointsP2);
		nbMainCardsP2 = new JTextArea(menu.getNbMainCards2().toString());
		nbMainCardsP2.setBounds(1350,450,100,50);
		nbMainCardsP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(nbMainCardsP2);
		nbSourceCardsP2 = new JTextArea(menu.getNbSourceCards2().toString());
		nbSourceCardsP2.setBounds(1350,520,100,50);
		nbSourceCardsP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(nbSourceCardsP2);
		nbSupCardsP2 = new JTextArea(menu.getNbSupCards2().toString());
		nbSupCardsP2.setBounds(380,620,100,50);
		nbSupCardsP2.setFont(font.deriveFont(Font.BOLD,20f));
		this.getContentPane().add(nbSupCardsP2);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(1350,620,100,50);
		this.getContentPane().add(saveButton);
		
		this.getContentPane().add(panelBackground);
		this.setVisible(true);
		new OptionsControler(this);
	}
	
	private void setButtons() {
		//buttonSimulation = new ButtonToDuel();
		//buttonSimulation.setBounds(600, 300, 100, 50);
		//buttonSimulation.setForeground(new Color(255, 255, 255));
		//buttonSimulation.add(new JLabel("Simulation"));
		this.repaint();
		this.revalidate();
	}


	public String[] getListTypes() {
		return listTypes;
	}


	public void setListTypes(String[] listTypes) {
		this.listTypes = listTypes;
	}


	public Menu getMenu() {
		return menu;
	}


	public void setMenu(Menu menu) {
		this.menu = menu;
	}


	public JTextArea getModuloTextP1() {
		return moduloTextP1;
	}


	public void setModuloTextP1(JTextArea moduloTextP1) {
		this.moduloTextP1 = moduloTextP1;
	}


	public JTextArea getMultiplicatorTextP1() {
		return multiplicatorTextP1;
	}


	public void setMultiplicatorTextP1(JTextArea multiplicatorTextP1) {
		this.multiplicatorTextP1 = multiplicatorTextP1;
	}


	public JTextArea getGlobalstrenghP1() {
		return globalstrenghP1;
	}


	public void setGlobalstrenghP1(JTextArea globalstrenghP1) {
		this.globalstrenghP1 = globalstrenghP1;
	}


	public JTextArea getRaritystrenghP1() {
		return raritystrenghP1;
	}


	public void setRaritystrenghP1(JTextArea raritystrenghP1) {
		this.raritystrenghP1 = raritystrenghP1;
	}


	public JList<String> getTypeCardP1() {
		return typeCardP1;
	}


	public void setTypeCardP1(JList<String> typeCardP1) {
		this.typeCardP1 = typeCardP1;
	}


	public JTextArea getLifePointsP1() {
		return lifePointsP1;
	}


	public void setLifePointsP1(JTextArea lifePointsP1) {
		this.lifePointsP1 = lifePointsP1;
	}


	public JTextArea getNbMainCardsP1() {
		return nbMainCardsP1;
	}


	public void setNbMainCardsP1(JTextArea nbMainCardsP1) {
		this.nbMainCardsP1 = nbMainCardsP1;
	}


	public JTextArea getNbSourceCardsP1() {
		return nbSourceCardsP1;
	}


	public void setNbSourceCardsP1(JTextArea nbSourceCardsP1) {
		this.nbSourceCardsP1 = nbSourceCardsP1;
	}


	public JTextArea getModuloTextP2() {
		return moduloTextP2;
	}


	public void setModuloTextP2(JTextArea moduloTextP2) {
		this.moduloTextP2 = moduloTextP2;
	}


	public JTextArea getMultiplicatorTextP2() {
		return multiplicatorTextP2;
	}


	public void setMultiplicatorTextP2(JTextArea multiplicatorTextP2) {
		this.multiplicatorTextP2 = multiplicatorTextP2;
	}


	public JTextArea getGlobalstrenghP2() {
		return globalstrenghP2;
	}


	public void setGlobalstrenghP2(JTextArea globalstrenghP2) {
		this.globalstrenghP2 = globalstrenghP2;
	}


	public JTextArea getRaritystrenghP2() {
		return raritystrenghP2;
	}


	public void setRaritystrenghP2(JTextArea raritystrenghP2) {
		this.raritystrenghP2 = raritystrenghP2;
	}


	public JList<String> getTypeCardP2() {
		return typeCardP2;
	}


	public void setTypeCardP2(JList<String> typeCardP2) {
		this.typeCardP2 = typeCardP2;
	}


	public JTextArea getLifePointsP2() {
		return lifePointsP2;
	}


	public void setLifePointsP2(JTextArea lifePointsP2) {
		this.lifePointsP2 = lifePointsP2;
	}


	public JTextArea getNbMainCardsP2() {
		return nbMainCardsP2;
	}


	public void setNbMainCardsP2(JTextArea nbMainCardsP2) {
		this.nbMainCardsP2 = nbMainCardsP2;
	}


	public JTextArea getNbSourceCardsP2() {
		return nbSourceCardsP2;
	}


	public void setNbSourceCardsP2(JTextArea nbSourceCardsP2) {
		this.nbSourceCardsP2 = nbSourceCardsP2;
	}


	public JButton getSaveButton() {
		return saveButton;
	}


	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}


	public JTextArea getNbSupCardsP2() {
		return nbSupCardsP2;
	}


	public void setNbSupCardsP2(JTextArea nbSupCardsP2) {
		this.nbSupCardsP2 = nbSupCardsP2;
	}
	
	

}
