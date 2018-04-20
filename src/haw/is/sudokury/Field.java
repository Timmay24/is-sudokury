package haw.is.sudokury;

public class Field {

	private final int x;
	private final int y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	
	public Field(int xCord, int yCord) {
		super();
		this.x = xCord;
		this.y = yCord;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Field [x=" + x + ", y=" + y + "]";
	}
	
}
