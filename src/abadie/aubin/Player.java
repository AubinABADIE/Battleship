package abadie.aubin;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player implements IPlayer {

	protected static final int sizes[] = { 5, 4, 3, 3, 2 };
	protected static final int nbShips = 5;

	protected Ship ships[];
	protected ArrayList<Coord> shoots;
	protected boolean hasHitShip = false;

	/**
	 * Getters and setters
	 */
	public Ship[] getShips() {
		return ships;
	}
	
	public void addShoot(Coord shoot) {
		this.shoots.add(shoot);
	}
	
	public ArrayList<Coord> getShoots() {
		return shoots;
	}
	
	public boolean isHasHitShip() {
		return hasHitShip;
	}
	
	public void setHasHitShip(boolean hasHitShip) {
		this.hasHitShip = hasHitShip;
	}
	
	/**
	 * Check if the ship is valid before add it
	 * @param s Ship to place
	 * @param start Start coordinate of ship
	 * @param direction Direction of ship
	 * @return Boolean value true if the ship is ready to be place and false otherwise
	 */
	public boolean isValidShip(Ship s, Coord start, int direction) {
		
		// Check start coordinate
		if(direction == 0) {
			if (!(start.getX() >= 'A' && start.getX() <= ('J' - s.getSize() + 1) && start.getY() >= 1 && start.getY() <= 10)) {
				System.out.println("Invalid coordinates, please retry...\n");
				return false;
			}
		} else {
			if (!(start.getX() >= 'A' && start.getX() <= 'J' && start.getY() >= 1 && start.getY() <= (10 - s.getSize() + 1))) {
				System.out.println("Invalid coordinates, please retry...\n");
				return false;
			}
		}
		
		// Check if there are no ships here
		for(int i = 0 ; i < s.getSize() ; i++) {
			if(direction == 0) {
				if (isShipAtCoord(new Coord((char)(start.getX() + i) + Integer.toString(start.getY())))) {
					return false;
				}
			} else {
				if (isShipAtCoord(new Coord(start.getX() + Integer.toString(start.getY() + i)))) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Check if there is a ship at the coordinate
	 * @param c Coordinate to verify
	 * @return Boolean value true if there is a ship and false otherwise
	 */
	public boolean isShipAtCoord(Coord c) {
		
		for (Ship s : this.getShips()) {
			for (Coord sq : s.getSquares()) {
				if (sq != null && sq.equals(c)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Get the ship at the coordinate
	 * @param c Coordinate of the ship
	 * @return Ship placed on the coordinate
	 */
	public Ship getShipAtCoord(Coord c) {
		
		Ship res = null;
		
		for (Ship s : this.ships) {
			for (Coord sq : s.getSquares()) {
				if (sq != null && sq.equals(c)) {
					res = s;
				}
			}
		}
		return res;
	}
	
	/**
	 * Check if the player has already hit a coordinate
	 * @param c Coordinate of the shoot
	 * @return Boolean value true if there is a shoot at this coordinate and false otherwise
	 */
	public boolean hasHitAtCoord(Coord c) {
		
		return this.getShoots().contains(c);
	}
	
	/**
	 * Hit a ship at a coordinate and add this shoot in the player's shoot list
	 * @param s Ship hit
	 * @param c Shoot's coordinate
	 */
	public void hitShip(Ship s, Coord c) {
		
		if(!this.isHasHitShip())
			this.setHasHitShip(true);

		for (Coord sq : s.getSquares()) {
			if (sq.equals(c)) {
				if (sq.isHit() == false) {
					sq.setHit(true);
				}
				this.shoots.add(sq);
			}
		}
	}
	
	/**
	 * Make a random shoot coordinate
	 * @return Coord of the shoot
	 */
	protected String createShootInAllGrid() {
		
		String shoot;
		Random r = new Random();

		char row = (char) (r.nextInt(10) + 'A');
        int col = (int) (r.nextInt(10) + 1);
        
		shoot = row + Integer.toString(col);
		
		return shoot;
	}

	/**
	 * Check if the player has lost
	 * @return Boolean value true if all the player's ships are destroyed and false otherwise
	 */
	public boolean hasLost() {
		boolean res = true;

		for (Ship s : this.ships) {
			if (s.isDestroyed() == false) {
				res = false;
			}
		}

		return res;
	}
	
	/**
	 * Display the grid that contains the player's ships and the shots made by the opponent player
	 * @param e Opponent player
	 */
	public void printGrid(Player e) {
		
		System.out.println("      1   2   3   4   5   6   7   8   9  10  ");
		System.out.println("     ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ");

		for (char i = 'A' ; i <= 'J' ; i++){
			
			System.out.print(" " + i + "  |");
			
			for (int j = 1 ; j <= 10 ; j++) {
				
				Coord pos = new Coord(i + Integer.toString(j));
				
				if(e.hasHitAtCoord(pos)) {
					if(this.isShipAtCoord(pos) && this.getShipAtCoord(pos).isHit(pos)) {
						System.out.print(" X  ");
					} else {
						System.out.print(" O  ");
					}
				} else {
					if (this.isShipAtCoord(pos)) {
						System.out.print(" 1  ");
					} else {
						System.out.print(" ~  ");
					}
				}
			}
			
			System.out.println();
		}
		
		System.out.println();
	}

	/**
	 * Display the grid that contains the shots made by the player
	 * @param e Opponent player
	 */
	public void printShootGrid(Player e) {

		System.out.println("      1   2   3   4   5   6   7   8   9  10  ");
		System.out.println("     ___ ___ ___ ___ ___ ___ ___ ___ ___ ___ ");

		for (char i = 'A' ; i <= 'J' ; i++){
			
			System.out.print(" " + i + "  |");
			
			for (int j = 1 ; j <= 10 ; j++) {
				
				Coord pos = new Coord(i + Integer.toString(j));
				
				if(this.hasHitAtCoord(pos)) {
					if(e.isShipAtCoord(pos) && e.getShipAtCoord(pos).isHit(pos)) {
						System.out.print(" X  ");
					} else {
						System.out.print(" O  ");
					}
				} else {
					System.out.print(" ~  ");
				}
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
}