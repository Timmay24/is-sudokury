package haw.is.sudokury;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Set;

import com.sun.org.apache.bcel.internal.generic.NEW;

public abstract class Solver {

	protected Map<Field, Set<Field>> constList = new HashMap<>();

	public Solver() {
		Set<Field> set = new HashSet<>();
		Map<Integer, Set<Field>> blockMap = new HashMap<>();

		// block 1
		set = new HashSet<>();
		for (int x = 0; x < 3; ++x) {
			for (int y = 0; y < 3; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(1, set);

		// block2
		set = new HashSet<>();
		for (int x = 3; x < 6; ++x) {
			for (int y = 0; y < 3; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(2, set);

		// block3
		set = new HashSet<>();
		for (int x = 6; x < 9; ++x) {
			for (int y = 0; y < 3; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(3, set);

		// block4
		set = new HashSet<>();
		for (int x = 0; x < 3; ++x) {
			for (int y = 3; y < 6; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(4, set);

		// block5
		set = new HashSet<>();
		for (int x = 3; x < 6; ++x) {
			for (int y = 3; y < 6; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(5, set);

		// block6
		set = new HashSet<>();
		for (int x = 3; x < 6; ++x) {
			for (int y = 6; y < 9; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(6, set);

		// block7
		set = new HashSet<>();
		for (int x = 6; x < 9; ++x) {
			for (int y = 0; y < 3; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(7, set);

		// block8
		set = new HashSet<>();
		for (int x = 6; x < 9; ++x) {
			for (int y = 3; y < 6; ++y) {
				set.add(new Field(x, y));
			}
		}
		blockMap.put(8, set);

		// block9
		set = new HashSet<>();
		for (int x = 6; x < 9; ++x) {
			for (int y = 6; y < 9; ++y) {

				set.add(new Field(x, y));
			}
		}
		blockMap.put(9, set);

		for (int x = 0; x < 9; ++x) {
			for (int y = 0; y < 9; ++y) {
				set = new HashSet<Field>();
				// Jedes Feld wird angefasst
				for (int x2 = 0; x2 < 9; ++x2) {
					if (x != x2) {
						// alle in einer Zeile zusammengefasst
						set.add(new Field(x2, y));
					}
				}
				for (int y2 = 0; y2 < 9; ++y2) {
					if (y != y2) {
						// alle in einer Spalte zusammengefasst
						set.add(new Field(x, y2));
					}
				}

				// wer ist im selben Block
				for (Entry<Integer, Set<Field>> keyValue : blockMap.entrySet()) {
					if (keyValue.getValue().contains(new Field(x, y))) {
						set.addAll(keyValue.getValue());
						set.remove(new Field(x, y));
					}
				}
				constList.put(new Field(x, y), set);
			}
		}

		String result = "";
		for (Entry entry : constList.entrySet()) {
			result += " - " + entry.getValue();

			result += "\n";

		}
		System.out.println(result);
	}

	public abstract int solve(int[][] board);

}
