package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import effects.Effect;

public class Help extends JFrame {
	
	
	public Help() {
		super("Help");
	}
	
	public void open() throws IOException, FontFormatException  {
		setSize(1500, 820);
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		JPanel p5 = new JPanel();
		JPanel p6 = new JPanel();
		//JScrollPane jscrollpane = new JScrollPane(panel);
		
		//this.add(jscrollpane, BorderLayout.CENTER);
		
		JTabbedPane onglets = new JTabbedPane();
		onglets.setBounds(40,20,1400,750);
		onglets.add("Describe card", p1);
	    onglets.add("Costs", p2);
	    onglets.add("beast effects", p3);
	    onglets.add("robot effects", p4);
	    onglets.add("undead effects", p5);
	    onglets.add("wizard effects", p6);
	    
	    //Describe card
	    describeCard(p1);
	    
	    //costs
	    listCosts(p2);
	    
	    //effects
	    listBeastEffects(p3);
	    listRobotEffects(p4);
	    listUndeadEffects(p5);
	    listWizardEffects(p6);
		
	    add(onglets);
	    setLayout(null);
	    setVisible(true);
	}
	
	public void describeCard(JPanel panel) throws IOException {
		JLabel description = new JLabel();
		description.setIcon(new ImageIcon(ImageIO.read(new File("img/description_card.png"))
				.getScaledInstance(800,600, 
				Image.SCALE_DEFAULT)));
		description.setBounds(0,0,800,600);
		panel.setBounds(0,0,800,600);
		panel.add(description);
	}
	
	public void listCosts(JPanel panel) throws IOException, FontFormatException {
		panel.setLayout(null);
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		JLabel bloodCost = new JLabel();
		bloodCost.setIcon(new ImageIcon(ImageIO.read(new File("img/costs/blood.png"))
				.getScaledInstance(60,40, 
				Image.SCALE_DEFAULT)));
		bloodCost.setBounds(0,0,60,40);
		panel.add(bloodCost);
		JLabel boneCost = new JLabel();
		boneCost.setIcon(new ImageIcon(ImageIO.read(new File("img/costs/bone.png"))
				.getScaledInstance(60,40, 
				Image.SCALE_DEFAULT)));
		boneCost.setBounds(0,50,60,40);
		panel.add(boneCost);
		JLabel energyCost = new JLabel();
		energyCost.setIcon(new ImageIcon(ImageIO.read(new File("img/costs/energy.png"))
				.getScaledInstance(30,40, 
				Image.SCALE_DEFAULT)));
		energyCost.setBounds(20,100,30,40);
		panel.add(energyCost);
		JLabel gemCost = new JLabel();
		gemCost.setIcon(new ImageIcon(ImageIO.read(new File("img/costs/green_gem.png"))
				.getScaledInstance(60,40, 
				Image.SCALE_DEFAULT)));
		gemCost.setBounds(0,150,60,40);
		panel.add(gemCost);
		
		JLabel bloodCostDescription = new JLabel("Blood cost: to play a card with this cost, you need to sacrify one or several card(s) on your side board.");
		bloodCostDescription.setFont(font.deriveFont(Font.BOLD,14f));
		bloodCostDescription.setBounds(100,10,1500,40);
		panel.add(bloodCostDescription);
		JLabel boneCostDescription = new JLabel("Bone cost: to play a card with this cost, you need to get enought bones. You gain bones when your cards are destroyed on the board.");
		boneCostDescription.setFont(font.deriveFont(Font.BOLD,14f));
		boneCostDescription.setBounds(100,60,1500,40);
		panel.add(boneCostDescription);
		JLabel energyCostDescription = new JLabel("Energy cost: to play a card with this cost, you need to get enought energy. Each turn your initial energy is increments (up to 6).");
		energyCostDescription.setFont(font.deriveFont(Font.BOLD,14f));
		energyCostDescription.setBounds(100,110,1500,40);
		panel.add(energyCostDescription);
		JLabel gemCostDescription = new JLabel("Gem cost: to play a card with this cost, you need to get a card with the corresponding gem on the board (for this example, you need green mox card).");
		gemCostDescription.setFont(font.deriveFont(Font.BOLD,13f));
		gemCostDescription.setBounds(100,160,1500,40);
		panel.add(gemCostDescription);
		panel.setBounds(0,0,800,600);
	}
	
	public void listBeastEffects(JPanel panel) throws IOException, FontFormatException {
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		JPanel panel2 = new JPanel();
		panel.setLayout(null);
		panel2.setLayout(null);
		panel2.setPreferredSize(new Dimension(1350,100*Effect.namesBeastEffects.size()));
		panel2.setBounds(0,0,1400,100*Effect.namesBeastEffects.size());
		for (int i=0; i< Effect.namesBeastEffects.size();i++) {
			JLabel imgEffectLabel = new JLabel();
			imgEffectLabel.setIcon(new ImageIcon(ImageIO.read(new File("img/beast/effects/" + Effect.namesBeastEffects.get(i) + ".png"))
					.getScaledInstance(50,50, 
					Image.SCALE_DEFAULT)));
			imgEffectLabel.setBounds(0,100*i,50,50);
			panel2.add(imgEffectLabel);
			JTextArea textEffectLabel = new JTextArea(Effect.mapEffectToDescription.get(Effect.namesBeastEffects.get(i)));
			textEffectLabel.setFont(font.deriveFont(Font.BOLD,14f));
			textEffectLabel.setEditable(false);
			textEffectLabel.setBounds(50,100*i,1300,95);
			textEffectLabel.setOpaque(false);
			panel2.add(textEffectLabel);
		}
		
		JScrollPane jscrollpane = new JScrollPane(panel2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscrollpane.setPreferredSize(new Dimension(1400,600));
		panel.add(jscrollpane, BorderLayout.CENTER);
		jscrollpane.setBounds(0,0,1400,600);
	}
	
	public void listRobotEffects(JPanel panel) throws IOException, FontFormatException {
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		JPanel panel2 = new JPanel();
		panel.setLayout(null);
		panel2.setLayout(null);
		panel2.setPreferredSize(new Dimension(1350,100*Effect.namesRobotEffects.size()));
		panel2.setBounds(0,0,1400,100*Effect.namesRobotEffects.size());
		for (int i=0; i< Effect.namesRobotEffects.size();i++) {
			JLabel imgEffectLabel = new JLabel();
			imgEffectLabel.setIcon(new ImageIcon(ImageIO.read(new File("img/robot/effects/" + Effect.namesRobotEffects.get(i) + ".png"))
					.getScaledInstance(50,50, 
					Image.SCALE_DEFAULT)));
			imgEffectLabel.setBounds(0,100*i,50,50);
			panel2.add(imgEffectLabel);
			JTextArea textEffectLabel = new JTextArea(Effect.mapEffectToDescription.get(Effect.namesRobotEffects.get(i)));
			textEffectLabel.setFont(font.deriveFont(Font.BOLD,14f));
			textEffectLabel.setBounds(50,100*i,1300,95);
			textEffectLabel.setEditable(false);
			textEffectLabel.setOpaque(false);
			panel2.add(textEffectLabel);
		}
		
		JScrollPane jscrollpane = new JScrollPane(panel2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscrollpane.setPreferredSize(new Dimension(1400,600));
		panel.add(jscrollpane, BorderLayout.CENTER);
		jscrollpane.setBounds(0,0,1400,600);
	}
	
	public void listUndeadEffects(JPanel panel) throws IOException, FontFormatException {
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		JPanel panel2 = new JPanel();
		panel.setLayout(null);
		panel2.setLayout(null);
		panel2.setPreferredSize(new Dimension(1350,100*Effect.namesUndeadEffects.size()));
		panel2.setBounds(0,0,1400,100*Effect.namesUndeadEffects.size());
		for (int i=0; i< Effect.namesUndeadEffects.size();i++) {
			JLabel imgEffectLabel = new JLabel();
			imgEffectLabel.setIcon(new ImageIcon(ImageIO.read(new File("img/undead/effects/" + Effect.namesUndeadEffects.get(i) + ".png"))
					.getScaledInstance(50,50, 
					Image.SCALE_DEFAULT)));
			imgEffectLabel.setBounds(0,100*i,50,50);
			panel2.add(imgEffectLabel);
			JLabel backgroundImg = new JLabel(); 
			backgroundImg.setIcon(new ImageIcon(ImageIO.read(new File("img/black_square.png"))
					.getScaledInstance(50,50, 
					Image.SCALE_DEFAULT)));
			backgroundImg.setBounds(0,100*i,50,50);
			panel2.add(backgroundImg);
			JTextArea textEffectLabel = new JTextArea(Effect.mapEffectToDescription.get(Effect.namesUndeadEffects.get(i)));
			textEffectLabel.setFont(font.deriveFont(Font.BOLD,14f));
			textEffectLabel.setBounds(50,100*i,1300,95);
			textEffectLabel.setEditable(false);
			textEffectLabel.setOpaque(false);
			panel2.add(textEffectLabel);
		}
		
		JScrollPane jscrollpane = new JScrollPane(panel2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscrollpane.setPreferredSize(new Dimension(1400,600));
		panel.add(jscrollpane, BorderLayout.CENTER);
		jscrollpane.setBounds(0,0,1400,600);
	}
	
	public void listWizardEffects(JPanel panel) throws IOException, FontFormatException {
		Font font = Font.createFont(Font.TRUETYPE_FONT, new File("conthrax-sb.ttf"));
		JPanel panel2 = new JPanel();
		panel.setLayout(null);
		panel2.setLayout(null);
		panel2.setPreferredSize(new Dimension(1350,100*Effect.namesWizardGreenOrangeBlueEffects.size()));
		panel2.setBounds(0,0,1400,100*Effect.namesWizardGreenOrangeBlueEffects.size());
		for (int i=0; i< Effect.namesWizardGreenOrangeBlueEffects.size();i++) {
			JLabel imgEffectLabel = new JLabel();
			imgEffectLabel.setIcon(new ImageIcon(ImageIO.read(new File("img/wizard/effects/" + Effect.namesWizardGreenOrangeBlueEffects.get(i) + ".png"))
					.getScaledInstance(50,50, 
					Image.SCALE_DEFAULT)));
			imgEffectLabel.setBounds(0,100*i,50,50);
			panel2.add(imgEffectLabel);
			JTextArea textEffectLabel = new JTextArea(Effect.mapEffectToDescription.get(Effect.namesWizardGreenOrangeBlueEffects.get(i)));
			textEffectLabel.setFont(font.deriveFont(Font.BOLD,14f));
			textEffectLabel.setBounds(50,100*i,1300,95);
			textEffectLabel.setEditable(false);
			textEffectLabel.setOpaque(false);
			panel2.add(textEffectLabel);
		}
		
		JScrollPane jscrollpane = new JScrollPane(panel2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jscrollpane.setPreferredSize(new Dimension(1400,600));
		panel.add(jscrollpane, BorderLayout.CENTER);
		jscrollpane.setBounds(0,0,1400,600);
	}
	

}
