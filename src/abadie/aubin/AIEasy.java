package abadie.aubin;

import java.util.ArrayList;
import java.util.Random;

public class AIEasy extends Player {
	
	/**
	 * Constructor
	 */
	public AIEasy() {
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
	 * Make a random shoot coordinate
	 * @param Current player's opponent
	 * @return Coord of the shoot
	 */
	@Override
	public Coord prepareShoot(Player e) {
		
		String shoot = this.createShootInAllGrid();

		return new Coord(shoot);
	}
}
