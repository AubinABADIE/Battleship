package abadie.aubin;

import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player {
	
	/**
	 * Constructor
	 */
	public Human() {
		
		this.ships = new Ship[nbShips];

		for (int i = 0; i < nbShips; i++) {
			ships[i] = new Ship(sizes[i]);
		}

		this.shoots = new ArrayList<Coord>();
	}
	
	/**
	 * Ship initialize method
	 */
	@Override
	public void initializeShips() {
		
		Scanner reader = new Scanner(System.in);

		for (Ship s : this.getShips()) {

			System.out.println("\nSize " + s.getSize() + " ship to set");
			
			Coord start = null;
			Coord end = null;
			int direction = -1;
			boolean validShip = false;

			while (!validShip) {
				
				System.out.print("Start row of ship (A-J): ");
				String rowStartTemp = reader.next().toUpperCase();
				char rowStart = rowStartTemp.charAt(0);
				System.out.print("Start column of ship (1-10): ");
				int colStart = reader.nextInt();
				
				start = new Coord(rowStart + Integer.toString(colStart));
				
				System.out.print("Direction of ship (0-V ou 1-H): ");
                direction = reader.nextInt();
                
                validShip = this.isValidShip(s, start, direction);
                
                if (direction == 0) {
                	end = new Coord((char)(rowStart + s.getSize() - 1) + Integer.toString(colStart));
                } else {
                	end = new Coord(rowStart + Integer.toString(colStart + s.getSize() - 1));
                }
			}
			
			s.setSquares(start, end, direction);
		}
	}
	
	/**
	 * Ask the human player to enter a coordinate
	 * @param Current player's opponent
	 * @return Coord of the shoot
	 */
	@Override
	public Coord prepareShoot(Player e) {
		
		System.out.println("State of your boats:");
		this.printGrid(e);
		
		System.out.println("Shoots grid:");
		printShootGrid(e);
		
		Scanner reader = new Scanner(System.in);
		String shoot;

		while (true) {
			System.out.print("Shoot row (A-J): ");
			String rowTemp = reader.next().toUpperCase();
			char row = rowTemp.charAt(0);
			System.out.print("Shoot column (1-10): ");
			int col = reader.nextInt();
			
			if (row >= 'A' && row <= 'J' && col >= 1 && col <= 10) {
				shoot = row + Integer.toString(col);
				break;
			}
			
			System.out.println("Coordonnées de tir invalides, veuillez recommencer...\n");
		}

		return new Coord(shoot);
	}
}
