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

public class SpecialEvents extends JFrame {
	private String listTypes[] = {"beast", "robot", "undead","wizard"}; 
	//private String listSpecial[] = {"boulder", "cost", "random", "trapper", "trial", "alter", "backpack", "campfire", "copy", "mycologist", "remove", "totem", "trade"}; 
	private String listSpecial[] = {"alter", "backpack", "campfire", "cost", "random", "trader", "trapper"}; 
	private Menu menu;
	private JButton buttonSimulation;
	private JList<String> typeEvent;

	public SpecialEvents() {
		super("Card simulator");
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
		this.getContentPane().add(buttonSimulation);
		JLabel labelType = new JLabel("event: ");
		labelType.setBounds(50,50,150,50);
		this.getContentPane().add(labelType);
		labelType.setFont(font.deriveFont(Font.BOLD,24f));
		labelType.setForeground(Color.WHITE);
		
		
		typeEvent = new JList<String>(listSpecial);
		typeEvent.setSelectedIndex(0);
		System.out.println(typeEvent.getSelectedValue());
		this.getContentPane().add(typeEvent);
		typeEvent.setBounds(200,50,140,250);
		
		this.getContentPane().add(panelBackground);
		this.setVisible(true);
		new SpecialEventsControler(this);
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


	public JList<String> getTypeEvent() {
		return typeEvent;
	}


	public void setTypeEvent(JList<String> typeEvent) {
		this.typeEvent = typeEvent;
	}


	public Menu getMenu() {
		return menu;
	}


	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	

}
