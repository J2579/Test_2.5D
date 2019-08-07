package combat;

public interface CombatDamageCalculator {

	/**
	 * Damage done by another combat-ready actor, with an attacking
	 * move of power 'power'. 
	 * 
	 * @param other Actor doing damage to 'this'
	 * @param attack TODO
	 * @return Damage dealt.
	 */
	int calculateDamageDone(CombatReady other, Attack attack);
}
