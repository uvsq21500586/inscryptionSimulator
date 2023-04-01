package frames;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;

public class Help extends JFrame {
	
	
	public Help() {
		super("Help");
	}
	
	public void open() throws IOException, FontFormatException  {
		setSize(1500, 800);
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

}
