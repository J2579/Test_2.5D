package combat;

public class Attack {

	private int power;
	private int mpCost;
	private String name;
	private DamageType type;
	
	
	private CombatReady attacker, target;
	
	public static final Attack STRUGGLE = new Attack("Struggle", 1, 0, DamageType.PURE, null, null);

	public Attack(String name, int power, int mpCost, DamageType type, CombatReady attacker, CombatReady target) {
		this.name = name;
		this.power = power;
		this.mpCost = mpCost;
		this.type = type;
		this.attacker = attacker;
		this.target = target;
	}
	
	public int getPower() {
		return power;
	}

	public String getName() {
		return name;
	}

	public int getMpCost() {
		return mpCost;
	}

	public DamageType getType() {
		return type;
	}
	
	public CombatReady getAttacker() {
		return attacker;
	}
	
	public CombatReady getTarget() {
		return target;
	}

	/**
	 * Calculates damage dealt on the user then returns a string representation of the action.
	 * 
	 * @return Attacks the target, then returns a string representation of the attack
	 */
	public String getAtkString() {
		int damageDealt = target.getCombatDamageCalculator().calculateDamageDone(attacker, this);
		return attacker.getName() + " damaged " + target.getName() + " using " + getName() + " for " + damageDealt + " damage!";
	}
	
	/**
	 * Decrements the target's HP sequentially.
	 * @throws InterruptedException 
	 */
	public void fireAttack() throws InterruptedException {
		
		int damageDealt = target.getCombatDamageCalculator().calculateDamageDone(attacker, this);
		Thread.sleep(300); //Play attack damage sound
		long msPerDamageTick = 1500 / damageDealt;
		
		attacker.changeMP(-mpCost);
		
		for(int i = 0; i < damageDealt; ++i) {
			target.changeHP(-1);
			
			if(!target.isAlive()) {
				target.setMp(0);//Set MP to zero if target dies
				break; //No need to keep delaying
			}
			
			Thread.sleep(msPerDamageTick);
		}
		
		
		Thread.sleep(500); //Buffer time
	}
	
	/**
	 * Returns string representation of the attack:
	 * 
	 * @return CSV String: Name, Damage, Type
	 */
	@Override
	public String toString() {
		return getName() + ", Power: " + getPower() + ", Cost: " + getMpCost() + ", Type: " + type.toString();
	}
}
