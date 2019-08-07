package combat;

import game.Game;
import combat.Mook;

public class CombatTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		Game game = Game.getInstance();
		
		CombatReady player = game.getPlayer().getPlayerCombatActor();
		CombatReady opponent = new Mook();
		
		CombatArena ca = new CombatArena(player, opponent);
		
		int combatRunState = 0;
		
		do {
			combatRunState = ca.run();
		}
		while(combatRunState == 0);
		
		System.out.print("Exit code: " + combatRunState);
	}
}
