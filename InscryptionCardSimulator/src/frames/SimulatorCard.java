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

import frames.duelbuttons.CardPanel;
import frames.menubuttons.ButtonToDuel;

public class SimulatorCard extends JFrame {
	private String listTypes[] = {"beast", "robot", "undead"}; 
	
	
	private JButton buttonSimulation;
	private JTextArea moduloText;
	private JTextArea multiplicatorText;
	private JTextArea seed;
	private JTextArea globalstrengh;
	private JTextArea raritystrengh;
	//private JLabel typeLabel;
	private JList<String> typeCard;
	private CardPanel resultCard;

	public SimulatorCard() {
		super("Card simulator");
	}


	public void open() throws IOException, FontFormatException {
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
		this.getContentPane().add(buttonSimulation);
		JLabel labelType = new JLabel("type: ");
		labelType.setBounds(100,50,100,50);
		this.getContentPane().add(labelType);
		labelType.setFont(font.deriveFont(Font.BOLD,24f));
		labelType.setForeground(Color.WHITE);
		JLabel labelmodulo = new JLabel("modulo: ");
		labelmodulo.setBounds(400,50,150,50);
		this.getContentPane().add(labelmodulo);
		labelmodulo.setFont(font.deriveFont(Font.BOLD,24f));
		labelmodulo.setForeground(Color.WHITE);
		JLabel labelmultiplicator = new JLabel("multiplier: ");
		labelmultiplicator.setBounds(670,50,150,50);
		this.getContentPane().add(labelmultiplicator);
		labelmultiplicator.setFont(font.deriveFont(Font.BOLD,20f));
		labelmultiplicator.setForeground(Color.WHITE);
		JLabel labelseed = new JLabel("seed: ");
		labelseed.setBounds(950,50,100,50);
		this.getContentPane().add(labelseed);
		labelseed.setFont(font.deriveFont(Font.BOLD,20f));
		labelseed.setForeground(Color.WHITE);
		JLabel globalstrenghLabel = new JLabel("global strengh: ");
		globalstrenghLabel.setBounds(200,200,300,50);
		this.getContentPane().add(globalstrenghLabel);
		globalstrenghLabel.setFont(font.deriveFont(Font.BOLD,20f));
		globalstrenghLabel.setForeground(Color.WHITE);
		JLabel raritystrenghLabel = new JLabel("rarity strengh: ");
		raritystrenghLabel.setBounds(700,200,300,50);
		this.getContentPane().add(raritystrenghLabel);
		raritystrenghLabel.setFont(font.deriveFont(Font.BOLD,20f));
		raritystrenghLabel.setForeground(Color.WHITE);
		
		
		typeCard = new JList<String>(listTypes);
		typeCard.setSelectedIndex(0);
		System.out.println(typeCard.getSelectedValue());
		this.getContentPane().add(typeCard);
		typeCard.setBounds(200,50,140,100);
		moduloText = new JTextArea("3");
		moduloText.setBounds(550,50,100,50);
		moduloText.setFont(font.deriveFont(Font.BOLD,24f));
		this.getContentPane().add(moduloText);
		multiplicatorText = new JTextArea("2");
		multiplicatorText.setBounds(830,50,100,50);
		multiplicatorText.setFont(font.deriveFont(Font.BOLD,24f));
		this.getContentPane().add(multiplicatorText);
		seed = new JTextArea("1");
		seed.setBounds(1030,50,100,50);
		seed.setFont(font.deriveFont(Font.BOLD,24f));
		this.getContentPane().add(seed);
		globalstrengh = new JTextArea("1");
		globalstrengh.setBounds(430,200,100,50);
		globalstrengh.setFont(font.deriveFont(Font.BOLD,24f));
		this.getContentPane().add(globalstrengh);
		raritystrengh = new JTextArea("1");
		raritystrengh.setBounds(930,200,100,50);
		raritystrengh.setFont(font.deriveFont(Font.BOLD,24f));
		this.getContentPane().add(raritystrengh);
		
		resultCard = new CardPanel();
		this.getContentPane().add(resultCard);
		resultCard.setBounds(400,400,200,300);
		
		this.getContentPane().add(panelBackground);
		this.setVisible(true);
		new SimulatorCardControler(this);
	}
	
	private void setButtons() {
		buttonSimulation = new ButtonToDuel();
		buttonSimulation.setBounds(600, 300, 100, 50);
		buttonSimulation.setForeground(new Color(255, 255, 255));
		buttonSimulation.add(new JLabel("Simulation"));
		this.repaint();
		this.revalidate();
	}


	public String[] getListTypes() {
		return listTypes;
	}


	public void setListTypes(String[] listTypes) {
		this.listTypes = listTypes;
	}


	public JButton getButtonSimulation() {
		return buttonSimulation;
	}


	public void setButtonSimulation(JButton buttonSimulation) {
		this.buttonSimulation = buttonSimulation;
	}


	public JTextArea getModuloText() {
		return moduloText;
	}


	public void setModuloText(JTextArea moduloText) {
		this.moduloText = moduloText;
	}


	public JTextArea getMultiplicatorText() {
		return multiplicatorText;
	}


	public void setMultiplicatorText(JTextArea multiplicatorText) {
		this.multiplicatorText = multiplicatorText;
	}


	public JTextArea getSeed() {
		return seed;
	}


	public void setSeed(JTextArea seed) {
		this.seed = seed;
	}


	public JTextArea getGlobalstrengh() {
		return globalstrengh;
	}


	public void setGlobalstrengh(JTextArea globalstrengh) {
		this.globalstrengh = globalstrengh;
	}


	public JTextArea getRaritystrengh() {
		return raritystrengh;
	}


	public void setRaritystrengh(JTextArea raritystrengh) {
		this.raritystrengh = raritystrengh;
	}
	

	public JList<String> getTypeCard() {
		return typeCard;
	}


	public void setTypeCard(JList<String> typeCard) {
		this.typeCard = typeCard;
	}


	public CardPanel getResultCard() {
		return resultCard;
	}


	public void setResultCard(CardPanel resultCard) {
		this.resultCard = resultCard;
	}
	
	

}
