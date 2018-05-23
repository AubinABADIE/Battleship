package abadie.aubin;
public class Ship {

	private int size;
	private Coord squares[];

	public Ship(int s) {
		this.size = s;
		this.squares = new Coord[size];
	}

	public int getSize() {
		return size;
	}

	public Coord[] getSquares() {
		return squares;
	}

	public void setSquares(Coord start, Coord end, int dir) {

		this.squares[0] = start;
		this.squares[getSize() - 1] = end;
		
		for (int i = 1 ; i < getSize() - 1 ; i++) {
			if (dir == 1) { // horizontal
				String c = start.getX() + Integer.toString(start.getY() + i);
				this.squares[i] = new Coord(c);
			} else { // vertical
				String c = (char) (start.getX() + i) + Integer.toString(start.getY());
				this.squares[i] = new Coord(c);
			}
		}
	}
	
	public boolean isHit(Coord c) {

		for (Coord sq : getSquares()) {
			if (sq.equals(c)) {
				return sq.isHit();
			}
		}

		return false;
	}

	public boolean isDestroyed() {

		boolean res = true;

		for (Coord sq : getSquares()) {
			if (sq.isHit() == false) {
				res = false;
				break;
			}
		}

		return res;
	}
}