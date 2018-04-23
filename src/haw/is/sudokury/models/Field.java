package haw.is.sudokury.models;

import java.util.HashMap;
import java.util.Map;

public class Field {

	//map die xCord auf yCord auf das Feld [xCord, yCord] mappt.
	private static Map<Integer,Map<Integer, Field>> fields = new HashMap<>();
	private final int x;
	private final int y;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	
	private Field(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * singleton Pattern auf die Klasse Field. Bestehende Felder werden gehalten.
	 *  Eine Position x y existiert nur einmal
	 * @param x - x-koordinate
	 * @param y	- y-koordinate
	 * @return	eine Referenz auf ein Feld mit x-und y-koordinate
	 */
	public static Field getField(int x, int y){
		//wenn x schon gemappt wurde
		if(fields.containsKey(x)){
			//dazu ein y existiert
			if(fields.get(x).containsKey(y)){
				//gebe das bereits existierende Feld aus
				return fields.get(x).get(y);
			}
			//wenn x bereits gemappt wurde, aber nicht y
			else{
				//erstelle das Feld, lege es in der map ab und gebe es raus
				Field field = new Field(x, y);
				fields.get(x).put(y, field);
				return field;
			}
		}
		//wenn x und y noch nicht gemappt wurden
		else{
			//erstelle das Feld, lege das Mapping an und gebe es aus.
			Map<Integer, Field> pos = new HashMap<>();
			Field field = new Field(x, y);
			pos.put(y, field);
			fields.put(x, pos);
			return field;
		}
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
