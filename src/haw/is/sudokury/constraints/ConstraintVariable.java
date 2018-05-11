package haw.is.sudokury.constraints;

import java.util.HashSet;

public abstract class ConstraintVariable<T, V> implements Comparable<V> {
	private final T variable;
	private final HashSet<V> domain;

	protected ConstraintVariable(T variable, HashSet<V> domain) {
		this.variable = variable;
		this.domain = domain;
	}

	public final T getVariable() {
		return variable;
	}

	public final HashSet<V> getDomain() {
		return domain;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
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
		ConstraintVariable other = (ConstraintVariable) obj;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		ConstraintVariable<T, V> var = (ConstraintVariable<T, V>) o;
		if (this.domain.size() == var.domain.size()) {
			return 0;
		} else if (this.domain.size() > var.domain.size()) {
			return 1;
		} else {
			return -1;
		}
	}

	public abstract ConstraintVariable cloneVar();

}
