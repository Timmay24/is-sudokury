package haw.is.sudokury.constraints;

import haw.is.sudokury.constraints.interfaces.Constraint;

/*
 * Generic Type E: Type der durch Constraint-Variablen-Klassen genutzt werden 
 */

public class AllDiffConstraint<E> implements Constraint {

    private final ConstraintVariable<E, Integer> f;
    private final ConstraintVariable<E, Integer> g;

	public AllDiffConstraint (ConstraintVariable<E, Integer> f, ConstraintVariable<E, Integer> g) {
        this.f = f;
        this.g = g;
    }
	
    public final ConstraintVariable<E, Integer> getF() {
		return f;
	}

	public final ConstraintVariable<E, Integer> getG() {
		return g;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((g == null) ? 0 : g.hashCode());
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
		AllDiffConstraint<E> other = (AllDiffConstraint<E>) obj;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		if (g == null) {
			if (other.g != null)
				return false;
		} else if (!g.equals(other.g))
			return false;
		return true;
	}
}
