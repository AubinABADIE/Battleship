package abadie.aubin;

public class Coord {

	private char x;
	private int y;
	private boolean hit;

	/**
	 * Constructor
	 * @param c String which is the coordinate of the square
	 */
	public Coord(String c) {
		this.x = c.charAt(0);
		if (c.length() == 3) {
			this.y = Integer.parseInt(c.substring(1,3));
		} else {
			this.y = Integer.parseInt(c.substring(1));
		}
		this.hit = false;
	}

	/**
	 * Getters and setters
	 */
	public char getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isHit() {
		return hit;
	}
	
	/**
	 * Print a coordinate
	 */
	@Override
	public String toString() {
		return getX() + Integer.toString(getY());
	}

	/**
	 * Check if two coordinate are at same position
	 */
	@Override
	public boolean equals(Object obj) {

		boolean result = false;

		if (obj instanceof Coord) {
			Coord c = (Coord) obj;
			result = (this.getX() == c.getX() && this.getY() == c.getY());
		}

		return result;
	}
}
