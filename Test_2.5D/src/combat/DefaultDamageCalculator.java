package combat;

public class DefaultDamageCalculator implements CombatDamageCalculator {

	@Override
	public int calculateDamageDone(CombatReady other, Attack attack) {
		return attack.getPower(); //Don't factor in defense or magic defense
	}

}
