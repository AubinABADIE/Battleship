package fr.battleship;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import abadie.aubin.AIEasy;
import abadie.aubin.AIHard;
import abadie.aubin.AIMedium;
import abadie.aubin.Coord;
import abadie.aubin.Player;
import abadie.aubin.Ship;

public class TestIA {
	
	/**
	 * Main method : run the test
	 * Generate a CSV file which contains results
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		
		// Create CSV file and initialize FileWriter
		File f = new File("ai_proof.csv");
		FileWriter fw = new FileWriter(f);
		fw.append("AI Name; score; AI Name2; score2\n");
		
		Player p1;
		Player p2;
		int nbGame = 100;
		
		for (int k = 0 ; k < 3 ; k++) {
			
			int p1Score = 0;
			int p2Score = 0;
			int round = 0; //rounds number
			
			// Initialize players and ships
			while(round < nbGame) {
				
				int first = round%2;
				
				switch (k) { 
					case 0: //AI Level Beginner; X1; AI Level Medium; Y1
						p1 = new AIEasy();
						p1.initializeShips();
						p2 = new AIMedium();
						p2.initializeShips();
						break;
					case 1: //AI Level Beginner; X2; AI Level Hard; Y2
						p1 = new AIEasy();
						p1.initializeShips();
						p2 = new AIHard();
						p2.initializeShips();
						break;
					case 2 : //AI Level Medium; X3; AI Level Hard ;Y3
						p1 = new AIMedium();
						p1.initializeShips();
						p2 = new AIHard();
						p2.initializeShips();
						break;
					default:
						p1 = new AIEasy();
						p1.initializeShips();
						p2 = new AIMedium();
						p2.initializeShips();
						break;
				}
				
				// Launch game
				while (true) {
					
					if(first == 0) { //Player 1 begins
						shoot(p1, p2, p1.prepareShoot(p2));
						if (p1.hasLost()) {
							p2Score++;
							break;
						}
						shoot(p2, p1, p2.prepareShoot(p1));
						if (p2.hasLost()) {
							p1Score++;
							break;
						}
					} else { //Player 2 begins
						shoot(p2, p1, p2.prepareShoot(p1));
						if (p1.hasLost()) {
							p2Score++;
							break;
						}
						shoot(p1, p2, p1.prepareShoot(p2));
						if (p2.hasLost()) {
							p1Score++;
							break;
						}
					}
				}
				round++;
			}
			
			// Add lines in the CSV file
			switch (k) {
				case 0:
					fw.append("AI Level Beginner; " + p1Score + "; AI Level Medium; " + p2Score + "\n");
					break;
				case 1:
					fw.append("AI Level Beginner; " + p1Score + "; AI Level Hard; " + p2Score + "\n");
					break;
				case 2:
					fw.append("AI Level Medium; " + p1Score + "; AI Level Hard; " + p2Score + "\n");
					break;
				default:
					break;
			}
		}
		
		System.out.println("Game results are in ai_proof.csv, located in src");
		fw.flush();
		fw.close();
	}
		
	/**
	 * Shooting method : check if a ship is hit or not, add the shoot
	 * @param p Current player who guess a target
	 * @param e Current player's opponent
	 * @param shoot Shooting coordinates
	 */
	public static void shoot(Player p, Player e, Coord shoot) {
		
		if(e.isShipAtCoord(shoot) ) {
			Ship s = e.getShipAtCoord(shoot);
			if (!s.isHit(shoot)) {
				p.hitShip(s, shoot);
				if (s.isDestroyed()) {
					if(p.isHasHitShip()){
						p.setHasHitShip(false);
					}
				}
			}
		} else {		
			if(p.isHasHitShip()) {
				p.setHasHitShip(false);
			}
			if(!p.getShoots().isEmpty()) {
				if(!p.hasHitAtCoord(shoot)) {
					p.addShoot(shoot);
				}
			} else {
				p.addShoot(shoot);
			}
		}
	}
}
