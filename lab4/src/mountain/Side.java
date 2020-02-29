package mountain;

import java.util.ArrayList;

public class Side {
	private Point p1;
	private Point p2;
	
	public Side(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	@Override
	public int hashCode() {
		return p1.hashCode() + p2.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		Side s = (Side) obj;
		if (p1.getX() == s.p1.getX() && p2.getX() == s.p2.getX()) {
			if (p1.getY() == s.p1.getY() && p2.getY() == s.p2.getY()) {
				return true;
			}
		} else if (p1.getX() == s.p2.getX() && p2.getX() == s.p1.getX()) {
			if (p1.getY() == s.p2.getY() && p2.getY() == s.p1.getY()) {
				return true;
			}
		}
		return false;
	}
}
