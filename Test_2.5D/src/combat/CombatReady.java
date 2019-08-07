package combat;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JOptionPane;

import io.ImageLoader;
import io.InvalidImageLoadException;

public class CombatReady{

	private int hp = 20;
	private int maxHP = hp;
	private int mp = 20;
	private int maxMP = mp;
	private int atk = 5;
	private int def = 5;
	private int intl = 5;
	private int mdef = 2;
	private int speed = 3;
	
	private BufferedImage image = null;
	private String name = "Mook";
	private AttackState state = AttackState.NONE;
	private CombatDamageCalculator damageCalculator = new DefaultDamageCalculator();
	private Attack[] atkList = new Attack[0];
	private boolean isPlayer = false;
	
	public CombatReady(BufferedImage image) {
		
		if(image != null)
			setImage(image);
		else {
		
			try {
				setImage(ImageLoader.loadImage("assets/sphere.png"));
			} catch (InvalidImageLoadException e) { 
				JOptionPane.showMessageDialog(null, "Error: some files are missing or corrupted.");
				System.exit(-1);
			}
		}
	}
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHP() {
		return hp;
	}

	public int getMP() {
		return mp;
	}

	public int getAtk() {
		return atk;
	}

	public int getDef() {
		return def;
	}

	public int getIntl() {
		return intl;
	}

	public int getMDef() {
		return mdef;
	}

	public int getSpeed() {
		return speed;
	}

	public void changeHP(int hp) {
		this.hp += hp;
		
		if(this.hp < 0)
			this.hp = 0;
	}

	public void changeMP(int mp) {
		this.mp += mp;
		
		if(this.mp < 0)
			this.mp = 0;
	}

	public boolean isAlive() {
		return hp > 0;
	}

	public void setCombatDamageCalculator(CombatDamageCalculator c) {
		damageCalculator = c;
	}

	public CombatDamageCalculator getCombatDamageCalculator() {
		return damageCalculator;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getMaxMP() {
		return maxMP;
	}

	public AttackState getAttackState() {
		return state;
	}

	public void setAttackState(AttackState atkState) {
		this.state = atkState;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public void setMaxMP(int maxMP) {
		this.maxMP = maxMP;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public void setIntl(int intl) {
		this.intl = intl;
	}

	public void setMdef(int mdef) {
		this.mdef = mdef;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setState(AttackState state) {
		this.state = state;
	}
	
	public void setAttackList(Attack[] list) {
		atkList = list;
	}
	
	public Attack[] getAttackList() {
		if(atkList.length != 0)
			return atkList;
		else
			return new Attack[] {Attack.STRUGGLE};
	}

	
	public void addAttack(Attack newAttack) {
		Attack[] temp = Arrays.copyOf(atkList, atkList.length + 1);
		temp[atkList.length] = newAttack;
		atkList = temp;
	}
	
	public void setIsPlayer(boolean b) {
		this.isPlayer = b;
	}
	
	public boolean isPlayer() {
		return isPlayer;
	}
}