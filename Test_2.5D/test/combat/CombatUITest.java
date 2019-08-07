package combat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import game.Game;
import util.SortedCenterList;


@SuppressWarnings("serial")
public class CombatUITest extends JFrame implements ActionListener {
	
	
	
	JTextArea currentStatus;
	JPanel combatActorPanelMain;
	
	Game game;
	CombatReady[] allies;
	CombatReady[] enemies;
	CombatArena ca;
	
	Timer timer;
	
	Color defaultBorderColor = Color.GRAY;
	Color attackingBorderColor = Color.GREEN;
	Color targetBorderColor = Color.RED;
	
	private void setUpCombatants() {
		
		allies = new CombatReady[2];
		enemies = new CombatReady[4];
		
		//Some test characters
		allies[0] = game.getPlayer().getPlayerCombatActor();
		allies[0].setName("Jessica");
		
		allies[1] = new Mook();
		allies[1].setName("Allied Mook");
		allies[1].addAttack(new Attack("WaterBlast", 8, 5, DamageType.MAGICAL, null, null));
		
		enemies[0] = new Mook();
		enemies[0].addAttack(new Attack("Punch", 3, 0, DamageType.PHYSICAL, null, null));
		
		enemies[1] = new Mook();
		enemies[1].setName("Albert");
		enemies[1].addAttack(new Attack("Lightningbolt", 10, 10, DamageType.MAGICAL, null, null));
		enemies[1].setMaxHP(15);
		enemies[1].setHp(15);
				
		enemies[2] = new Mook();
		enemies[2].addAttack(new Attack("Kick", 3, 0, DamageType.PHYSICAL, null, null));
		
		enemies[3] = new Mook();
		enemies[3].addAttack(new Attack("Throw Rock", 3, 0, DamageType.PHYSICAL, null, null));
	}
	
	public CombatUITest() throws InterruptedException {
		
		game = Game.getInstance();
		
		
		/******************************************
		 * This will be called uniquely from game *
		 ******************************************/
		setUpCombatants();
		
		
		
		setTitle("Combat Test 1.0");
		setSize(1100,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(1, 2)); //Text area on left, combatants on the right
		
		currentStatus = new JTextArea();
		currentStatus.setFont(new Font("Courier", Font.PLAIN, 25));
		
		//Text Area
		currentStatus.setLineWrap(true);
		currentStatus.setWrapStyleWord(true);
		add(currentStatus);
		
		//Combatants
		combatActorPanelMain = new JPanel();
		
			
		int widthOfField;
		
		if(enemies.length < allies.length)
			widthOfField = allies.length;
		else 
			widthOfField = enemies.length;
		
		combatActorPanelMain.setLayout(new GridLayout(2, widthOfField));
	
		SortedCenterList<CombatReady> sortedEnemies = new SortedCenterList<CombatReady>();
		SortedCenterList<CombatReady> sortedAllies = new SortedCenterList<CombatReady>();
		
		
		for(int idx = 0; idx < widthOfField; ++idx) {
			try {
				sortedEnemies.addNormal(enemies[idx]);
			}
			catch(IndexOutOfBoundsException e) {
				sortedEnemies.add(null);
			}
		}
		
		for(int idx = 0; idx < widthOfField; ++idx) {
			try {
				sortedAllies.addNormal(allies[idx]);
			}
			catch(IndexOutOfBoundsException e) {
				sortedAllies.add(null);
			}
		}
		
		int actorPositionCounter = 0; //Determines position on field
		
		for(int idx = 0; idx < sortedEnemies.size(); ++idx) { //Add all existing enemies to the list
			if(sortedEnemies.get(idx) != null) {
				combatActorPanelMain.add(new CombatActorPanel(sortedEnemies.get(idx), actorPositionCounter));
				++actorPositionCounter;
			}
			else
				combatActorPanelMain.add(new JPanel());
		}
		
		actorPositionCounter = 0; //Reset
		
		for(int idx = 0; idx < sortedAllies.size(); ++idx) { //Add all existing allies to the list
			if(sortedAllies.get(idx) != null) {
				combatActorPanelMain.add(new CombatActorPanel(sortedAllies.get(idx), actorPositionCounter));
				++actorPositionCounter;
			}	
			else
				combatActorPanelMain.add(new JPanel());
		}
		
		add(combatActorPanelMain);
		setVisible(true);
		
		timer = new Timer(36, this); //30 fps
		
		ca = new CombatArena(allies, enemies);
		
		timer.start();
		
		int combatRunState = 0;
		
		do {
			combatRunState = ca.run();
		}
		while(combatRunState == 0);
		
		JOptionPane.showMessageDialog(null, "Your party was " + (combatRunState == 1 ? "victorious!" : "wiped out..."));
	}
	
	
	private class CombatActorPanel extends JPanel {
		
		private CombatReady cr;
		private JLabel hp;
		private JLabel mp;
		private JLabel imageLabel;
		private JProgressBar mpBar;
		private JProgressBar hpBar;
		
		public CombatActorPanel(CombatReady cr, int positionNumber) {
			
			UIManager.put("ProgressBar.selectionForeground", Color.BLACK);
			
			this.cr = cr;
			setLayout(new GridLayout(2, 1));
			setBorder(new TitledBorder(cr.getName() + " (Position " + (positionNumber + 1) + ")"));
			
			imageLabel = new JLabel(new ImageIcon(cr.getImage()));
			add(imageLabel);
			
			JPanel hpMpPanel = new JPanel();
			hpMpPanel.setLayout(new GridLayout(2, 1)); //HP bar, then MP bar vertical
			
			hp = new JLabel();
			hp.setHorizontalAlignment(JLabel.CENTER);
			hp.setVerticalAlignment(JLabel.CENTER);
			hp.setFont(new Font("Courier", Font.PLAIN, 16));
			
			hpBar = new JProgressBar();
			hpBar.setOrientation(SwingConstants.HORIZONTAL);
			hpBar.setMinimum(0);
			hpBar.setMaximum(100);
			hpBar.add(hp);
			hpBar.setStringPainted(true);
			hpBar.setForeground(Color.RED);
			hpMpPanel.add(hpBar);
			
			mp = new JLabel();
			mp.setHorizontalAlignment(JLabel.CENTER);
			mp.setHorizontalAlignment(JLabel.CENTER);
			mp.setVerticalAlignment(JLabel.CENTER);
			mp.setFont(new Font("Courier", Font.PLAIN, 16));
			
			mpBar = new JProgressBar();
			mpBar.setOrientation(SwingConstants.HORIZONTAL);
			mpBar.setMinimum(0);
			mpBar.setMaximum(100);
			mpBar.add(mp);
			mpBar.setStringPainted(true);
			mpBar.setForeground(Color.BLUE);
			hpMpPanel.add(mpBar);
			
			add(hpMpPanel);
			
			update();
		}
		
		public void update() {
			hp.setText("HP: " + cr.getHP() + "/" + cr.getMaxHP());
			hpBar.setValue((int)(((double)cr.getHP() / (double)cr.getMaxHP()) * 100));
			hpBar.setString(hp.getText());
			
			mp.setText("MP: " + cr.getMP() + "/" + cr.getMaxMP());
			mpBar.setValue((int)(((double)cr.getMP() / (double)cr.getMaxMP()) * 100));
			mpBar.setString(mp.getText());
			
			if(cr.getAttackState().equals(AttackState.ATTACKING))
				setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(attackingBorderColor), ((TitledBorder)getBorder()).getTitle()));
			else if(cr.getAttackState().equals(AttackState.ATTACKED))
				setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(targetBorderColor), ((TitledBorder)getBorder()).getTitle()));
			else //No special color
				setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(defaultBorderColor), ((TitledBorder)getBorder()).getTitle()));
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(timer)) {
			
			for(Component panel: combatActorPanelMain.getComponents()) {
				if(panel instanceof CombatActorPanel) {
					((CombatActorPanel) panel).update();
				}
			}
			
			currentStatus.setText(ca.getLastActionTaken());
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		@SuppressWarnings("unused")
		CombatUITest c = new CombatUITest();
	}	
}