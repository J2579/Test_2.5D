package combat;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import util.GenericStack;

public class CombatArena {
	
	/** List of allied Actors */
	private CombatReady[] alliedCombatActors;
	/** List of enemy Combat Actors */
	private CombatReady[] enemyCombatActors;
	/** The index of the player in the alliedCombatActors */
	private int indexOfPlayer = -1;
	
	/** The last attack made */
	private String lastActionTaken = "";

	/**
	 * Returns the number of allied characters
	 */
	public int getAllyActorNumber() {
		return alliedCombatActors.length;
	}
	
	/**
	 * Returns the number of enemy characters
	 */
	public int getEnemyActorNumber() {
		return enemyCombatActors.length;
	}
	
	/**
	 * Constructs a combat arena with the given CombatReady actors
	 * 
	 * @param allies Player character (and other allied characters)
	 * @param enemies Enemy characters
	 */
	public CombatArena(CombatReady[] allies, CombatReady[] enemies) {
		this.alliedCombatActors = allies;
		this.enemyCombatActors = enemies;
		
		int idx = 0;
		for(CombatReady cr: alliedCombatActors) {
			if(cr.isPlayer()) {
				indexOfPlayer = idx;
				break;
			}
			++idx;
		}
	}


	/**
	 * Constructs a 1 on 1 combat arena with an enemy, and the current player character.
	 * This is mostly used for testing purposes.
	 * 
	 * @param player The player.
	 * @param enemy The enemy to fight.
	 */
	public CombatArena(CombatReady player, CombatReady enemy) {
		this(new CombatReady[] {player}, new CombatReady[] {enemy});
		indexOfPlayer = 0;
	}
	
	/**
	 * Runs the combat loop. TODO: Make this thread-safe
	 * @throws InterruptedException 
	 * 
	 */
	public int run() throws InterruptedException {
		
		//Set the status message
		lastActionTaken = "What will " + alliedCombatActors[indexOfPlayer].getName() + " do?";
		
		//Write the status of all characters to the console 
		System.out.println("Allies: ");
		for(int idx = 0; idx < alliedCombatActors.length; ++idx) {
			CombatReady actor = alliedCombatActors[idx];
			System.out.println((idx+1) + ": " + actor.getName() + ", HP: " + actor.getHP() + ", MP:" + actor.getMP());
		}
		
		System.out.println("Enemies: ");
		for(int idx = 0; idx < enemyCombatActors.length; ++idx) {
			CombatReady actor = enemyCombatActors[idx];
			System.out.println((idx + 1) + ": " + actor.getName() + ", HP: " + actor.getHP() + ", MP:" + actor.getMP());
		}
		
		//Check if all enemies or all allies are dead.
		int continueFight = checkBothPartyStatus();
		
		if(continueFight != 0)
			return continueFight;
		
		//Wait for the user to select a target.
		ArrayList<Attack> unsortedAttackList = new ArrayList<Attack>();
		
		if(indexOfPlayer != -1) //not AI fight
			unsortedAttackList.add(getUserAttackInput());

		//Automatically select targets for every remaining AI.
		
		//Enemy selection
		for(CombatReady enemy: enemyCombatActors) {
			
			if(!enemy.isAlive())
				continue;
			
			Random r = new Random();
			
			//Look for an ally that is not dead
			int targetIdx = -1;
			
			boolean foundValidTarget = false;
			while(!foundValidTarget) {
				targetIdx = r.nextInt(alliedCombatActors.length);
				if(alliedCombatActors[targetIdx].isAlive())
					foundValidTarget = true;
			}
			
			
			Attack atk = null;
			
			if(!hasValidMoves(enemy))
				atk = Attack.STRUGGLE;
			else {
				boolean isValid = false;
				
				while(!isValid) {
					atk = enemy.getAttackList()[r.nextInt(enemy.getAttackList().length)];
					if(atk.getMpCost() <= enemy.getMP())
						isValid = true;
				}
			}
					
			unsortedAttackList.add(new Attack(atk.getName(), atk.getPower(), atk.getMpCost(), atk.getType(), enemy, alliedCombatActors[targetIdx]));
		}
		
		//Ally selection
		for(CombatReady ally: alliedCombatActors) {
			
			if(ally.isPlayer() || !ally.isAlive()) //The player already selected an attack!
				continue;
			
			Random r = new Random();
			
			//Look for an ally that is not dead
			int targetIdx = -1;
			
			boolean foundValidTarget = false;
			while(!foundValidTarget) {
				targetIdx = r.nextInt(enemyCombatActors.length);
				if(enemyCombatActors[targetIdx].isAlive())
					foundValidTarget = true;
			}
			
			Attack atk = null;
			
			if(!hasValidMoves(ally))
				atk = Attack.STRUGGLE;
			else {
				boolean isValid = false;
				
				while(!isValid) {
					atk = ally.getAttackList()[r.nextInt(ally.getAttackList().length)];
					if(atk.getMpCost() <= ally.getMP())
						isValid = true;
				}
			}

			unsortedAttackList.add(new Attack(atk.getName(), atk.getPower(), atk.getMpCost(), atk.getType(), ally, enemyCombatActors[targetIdx]));
		
		}
		
		//Push the attacks into a stack from low to high attacker speed order.
		GenericStack<Attack> stack = new GenericStack<Attack>();
		
		while(!unsortedAttackList.isEmpty()) {
			int lowestIdx = 0;
			int lowestSpeed = Integer.MAX_VALUE;
			
			for(int idx = 0; idx < unsortedAttackList.size(); ++idx) {
				if(unsortedAttackList.get(idx).getAttacker().getSpeed() < lowestSpeed) {
					lowestIdx = idx;
					lowestSpeed = unsortedAttackList.get(idx).getAttacker().getSpeed();
				}
			}
			stack.push(unsortedAttackList.remove(lowestIdx));
		}
		
		
		//Fire the attacks by iterating over the stack
		while(!stack.isEmpty()) {
			
			Attack atkToFire = stack.pop();
			
			//Check if the actor was killed by an earlier attack
			if(atkToFire.getAttacker().isAlive()) {
				
				//Set the flag of the attacker to Attacking
				atkToFire.getAttacker().setAttackState(AttackState.ATTACKING);
				//Set the flag of the target to Attacked
				atkToFire.getTarget().setAttackState(AttackState.ATTACKED);
				
				lastActionTaken = atkToFire.getAtkString();
				atkToFire.fireAttack();
				System.out.println(lastActionTaken);

				//Reset the attack state flags
				atkToFire.getAttacker().setAttackState(AttackState.NONE);
				atkToFire.getTarget().setAttackState(AttackState.NONE);
			}
			else 
				continue; //No need to delay for a dead actor
		}
		return 0;
	}
	
	/**
	 * Returns a string representation of the last attack made
	 * 
	 * @return The last attack made (in String form)
	 */
	public String getLastActionTaken() {
		return lastActionTaken;
	}
	
	/**
	 * Returns an attack based off user input
	 * 
	 * @return an attack
	 */
	public Attack getUserAttackInput() {
		// TODO Call this from game

		CombatReady player = alliedCombatActors[indexOfPlayer];
		Attack chosenAttack = null;
		
		//Get target input
		String rawInput = "";
		int targetInput = -1;
		boolean recievedValidInput = false;
		
		while(!recievedValidInput) {
			try {
				rawInput = JOptionPane.showInputDialog("Select Enemy Target: ");
				targetInput = Integer.parseInt(rawInput);
				recievedValidInput = true;
			}
			catch(NumberFormatException e) {JOptionPane.showMessageDialog(null, "" + rawInput + " is not a valid enemy index!"); continue;}
			finally {
				if(targetInput < 1 || targetInput > enemyCombatActors.length) {
					JOptionPane.showMessageDialog(null, "Select an enemy index from 1 to " + (enemyCombatActors.length));
					recievedValidInput = false;
				}	
				else if(!enemyCombatActors[targetInput-1].isAlive()) {
					JOptionPane.showMessageDialog(null, "The targeted enemy is already defeated!");
					recievedValidInput = false;
				}
			}
			
		}
		--targetInput; //The player should see 1-indexed, but the program should use 0-indexed
		
		
		if(!hasValidMoves(player))
			return new Attack(Attack.STRUGGLE.getName(), Attack.STRUGGLE.getPower(), Attack.STRUGGLE.getMpCost(), Attack.STRUGGLE.getType(), player, enemyCombatActors[targetInput]);
		
		//List the attacks
		String attackDisplay = "Select an Attack:\n";
		Attack[] atkList = player.getAttackList();
		for(int idx = 0; idx < atkList.length; ++idx) {
			attackDisplay += (idx+1) + ": " + atkList[idx].toString() + "\n";
		}
		
		//Check that the user input a valid attack
		int attackInput = -1;
		recievedValidInput = false;
		
		while(!recievedValidInput) {
			try {
				rawInput = JOptionPane.showInputDialog(attackDisplay);
				attackInput = Integer.parseInt(rawInput);
				recievedValidInput = true;
			}
			catch(NumberFormatException e) {JOptionPane.showMessageDialog(null, "" + rawInput + " is not a valid attack index!"); continue;} //Input was not a number
			finally {
				if(attackInput < 1 || attackInput > atkList.length) { //Input not in attack list range
					JOptionPane.showMessageDialog(null, "Select an attack index from 1 to " + (atkList.length));
					recievedValidInput = false;
				}
				
				--attackInput;
				
				chosenAttack = player.getAttackList()[attackInput];
				
				if(chosenAttack.getMpCost() > player.getMP()) {
					JOptionPane.showMessageDialog(null, "No MP left for this move!");
					recievedValidInput = false;
				}
			}
			
		}
		
		
		return new Attack(chosenAttack.getName(), chosenAttack.getPower(), chosenAttack.getMpCost(), chosenAttack.getType(), player, enemyCombatActors[targetInput]);
	}


	/**
	 * Returns true if the actor can cast another move, false otherwise
	 * 
	 * @param cr The combat ready actor to check
	 * @return True if the actor has mp left to cast a move
	 */
	private boolean hasValidMoves(CombatReady cr) {
		//Check if we can make any valid moves
		boolean hasValidMoves = false;
		for(Attack a: cr.getAttackList()) {
			if(a.getMpCost() <= cr.getMP()) {
				hasValidMoves = true;
				break;
			}
		}
		
		return hasValidMoves;
	}

	/**
	 * Checks the status of the fight.
	 * 
	 * @return 1 If team won, -1 if team lost, 0 if fight is ongoing.
	 */
	private int checkBothPartyStatus() {
		
		boolean allDead = true;
		
		for(CombatReady ally: alliedCombatActors) {
			if(ally.isAlive()) {
				allDead = false;
				break;
			}			
		}
		if(allDead)
			return -1;
		
		allDead = true;
		for(CombatReady enemy: enemyCombatActors) {
			if(enemy.isAlive()) {
				allDead = false;
				break;
			}			
		}
		if(allDead)
			return 1;
		
		return 0;
	}
}
