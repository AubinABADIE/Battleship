package abadie.aubin;
import java.util.Scanner;

public class Battleship {
	
	/**
	 * Main method : run the game
	 * @param args
	 */
	public static void main(String[] args) {
		
		Player p1;
		Player p2;
		int round = 0; // rounds number 
		int p1Score = 0; // p1's wins count
		int p2Score = 0; // p1's wins count
		int gameMode = 0; // 0 : Human vs Human 
						  // 1 : Human vs IA
		int difficulty = -1; // 0 : Easy 
							 // 1 : Medium 
							 // 2 : Hard
		Scanner reader = new Scanner(System.in);
		
		// Choose game mode : Human, AIEasy, AIMedium, AIHard
		while (true) {
			
			System.out.println("Which game type?\n 0 : Human vs Human \n 1 : Human vs IA");
			String t = reader.nextLine();
			
			if (!t.equals("0") && !t.equals("1")) {
				System.out.println("Choose a correct type...");
			} else {
				gameMode = Integer.parseInt(t);
				break;
			}
		}
		
		// Choose AI type : AIEasy, AIMedium, AIHard
		if (gameMode == 1) {
			while (true) {
				
				System.out.println("Which AI difficulty?\n 0 : Easy \n 1 : Medium \n 2 : Hard");
				String d = reader.nextLine();
				
				if (!d.equals("0") && !d.equals("1") && !d.equals("2")) {
					System.out.println("Choose a correct level...");
				}
				else {
					difficulty = Integer.parseInt(d);
					break;
				}
			}
		}
		
		// Initialize players and ships
		while(true) {
			
			int first = round%2;
			
			switch(gameMode) {
				case 0: // Human vs Human
					System.out.println("\nJoueur 1 :");
					p1 = new Human();
					p1.initializeShips();
					System.out.println("\nJoueur 2 :");
					p2 = new Human();
					p2.initializeShips();
					break;
					
				case 1: // Human vs AI
					System.out.println("\nJoueur 1 :");
					p1 = new Human();
					p1.initializeShips();
					System.out.println("\nAI initialization...");
					switch(difficulty) {
						case 0:
							p2 = new AIEasy();
							break;
						case 1:
							p2 = new AIMedium();
							break;
						case 2:
							p2 = new AIHard();
							break;
						default:
							p2 = new AIMedium();
							break;
					}
					p2.initializeShips();
					System.out.println("\nAI ready!");
					p2.printGrid(p1);
					break;
					
				default:
					System.out.println("\nJoueur 1 :");
					p1 = new Human();
					p1.initializeShips();
					System.out.println("\nJoueur 2 :");
					p2 = new Human();
					p2.initializeShips();
					break;
			}
			
			// Launch game
			while (true) {

				if(first == 0) { //Player 1 begins
					System.out.println("\nPlayer, which target?");
					shoot(p1, p2, p1.prepareShoot(p2));
					if (p1.hasLost()) {
						System.out.println("Player 2 wins!");
						p2Score++;
						break;
					}
					
					System.out.println("\nAI is choosing target...");
					shoot(p2, p1, p2.prepareShoot(p1));
					if (p2.hasLost()) {
						System.out.println("Player 1 wins!");
						p1Score++;
						break;
					}
				} else { //Player 2 begins
					System.out.println("\nAI is choosing target...");
					shoot(p2, p1, p2.prepareShoot(p1));
					if (p1.hasLost()) {
						System.out.println("Player 2 wins!");
						p2Score++;
						break;
					}
					
					System.out.println("\nPlayer, which target?");
					shoot(p1, p2, p1.prepareShoot(p2));
					if (p2.hasLost()) {
						System.out.println("Player 1 wins!");
						p1Score++;
						break;
					}
				}
			}
			
			// Choose replay or not
			while (true) {
				System.out.println("Current score:\n Player 1: " + p1Score + "/ Player 2: " + p2Score);
				
				System.out.println("Want to replay? " + "\n YES (y) or NO (n)");
				String replay = reader.nextLine().toUpperCase();
				
				if(!replay.equals("YES") && !replay.equals("NO") && !replay.equals("Y") && !replay.equals("N")) {
					System.out.println("Invalid...");
				}
				else {
					if(replay.equals("YES") || replay.equals("Y")) {
						round++;
						break;
					} else {
						System.out.println("Thanks for playing!");
						System.exit(0);
					}
				}
			}
		}
	}
	
	/**
	 * Shooting method : check if a ship is hit or not, add the shoot and print the result
	 * @param p Current player who guess a target
	 * @param e Current player's opponent
	 * @param shoot Shooting coordinates
	 */
	public static void shoot(Player p, Player e, Coord shoot) {
		
		if(e.isShipAtCoord(shoot) ) {
			Ship s = e.getShipAtCoord(shoot);
			if (s.isHit(shoot)) {
				System.out.println("Already hit " + shoot.toString());
			} else {
				System.out.println("Hit in " + shoot.toString());
				p.hitShip(s, shoot);
				if (s.isDestroyed()) {
					if(p.isHasHitShip())
						p.setHasHitShip(false);
					
					System.out.println("Hit and sank!");
				}
			}
		} else {		
			if(p.isHasHitShip())
				p.setHasHitShip(false);
			
			if(!p.getShoots().isEmpty()) {
				if(p.hasHitAtCoord(shoot)) {
					System.out.println("Already hit " + shoot.toString());
				} else {
					System.out.println("Miss in " + shoot.toString());
					p.addShoot(shoot);
				}
			} else {
				System.out.println("Miss in " + shoot.toString());
				p.addShoot(shoot);
			}
		}
	}
}
