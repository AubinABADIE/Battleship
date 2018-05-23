package abadie.aubin;

import java.util.ArrayList;
import java.util.Random;

public class AIHard extends Player {
	
	int testCptMax = 100;
	
	/**
	 * Constructor
	 */
	public AIHard() {
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
		
		for (Ship s : this.getShips()) {
			
			Coord start = null;
			Coord end = null;
			int direction = -1;
			boolean validShip = false;

			while (!validShip) {
				
				Random r = new Random();
				
				direction = r.nextInt(2);
				
				char rowStart;
				int colStart;
				
				if(direction == 0) {
					rowStart = (char) (r.nextInt(10 - s.getSize() + 1) + 'A');
					colStart = r.nextInt(10) + 1;
					start = new Coord(rowStart + Integer.toString(colStart));
					validShip = this.isValidShip(s, start, direction);
					end = new Coord((char)(rowStart + s.getSize() - 1) + Integer.toString(colStart));
				} else {
					rowStart = (char) (r.nextInt(10) + 'A');
					colStart = r.nextInt(10 - s.getSize() + 1) + 1;
					start = new Coord(rowStart + Integer.toString(colStart));
					validShip = this.isValidShip(s, start, direction);
					end = new Coord(rowStart + Integer.toString(colStart + s.getSize() - 1));
				}
			}
			
			s.setSquares(start, end, direction);
		}
	}	
	
	/**
	 * Make a random shoot coordinate, but check if this Coord isn't already hit
	 * @param Current player's opponent
	 * @return Coord of the shoot
	 */
	@Override
	public Coord prepareShoot(Player e) {
		
		String shoot;

		if(this.shoots.isEmpty()) {			
			shoot = this.createShootInAllGrid();
		}
		else {
			if(this.isHasHitShip()) {	
				shoot = this.createShoot();
			} else {
				do {
					shoot = this.createShootInAllGrid();
				}while(this.hasHitAtCoord(new Coord(shoot)));
			}
		}

		return new Coord(shoot);
	}
	
	protected String createShoot() {
		
		String shoot;
		Random r = new Random();
		int index = this.shoots.size() - 1; // take the last save shoot position
		String lastShootPos = this.shoots.get(index).toString(); // Get the value of the last save shoot position

		char rowLetter = lastShootPos.charAt(0);
		int colNumber = Integer.parseInt(lastShootPos.substring(1, lastShootPos.length()));
		
		char row;
		int col;
		int testCpt = 0;
		
		// Get the new shoot position with cross method
		do {
			if(rowLetter == 'A') {
				row = (char) (r.nextInt(2) + rowLetter);
			} else if(rowLetter == 'J') {
				row = (char) (r.nextInt(2) + (rowLetter - 1));
			} else {			
				row = (char) (r.nextInt(3) + (rowLetter - 1));
			}
			
			if(row == rowLetter) {
				if(colNumber == 1) {
					col = colNumber + 1;
				} else if(colNumber == 10) {
					col = colNumber - 1;
				} else {
					col = (r.nextInt(2) != 0) ? colNumber + 1 : colNumber - 1;
				}
			} else {
				col = colNumber;
			}
			
			shoot = row + Integer.toString(col);
			testCpt++;
		}while(this.hasHitAtCoord(new Coord(shoot)) && testCpt < this.testCptMax);
		
		if(testCpt >= this.testCptMax) {
			do {
				shoot = this.createShootInAllGrid();
			}while(this.hasHitAtCoord(new Coord(shoot)));
		}
		
		return shoot;
	}
}