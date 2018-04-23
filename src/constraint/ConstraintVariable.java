package constraint;

import java.util.Set;

public abstract class ConstraintVariable<T, V> {
	private final T variable;
	private final Set<V> value;
	
	protected ConstraintVariable(T variable, Set<V> value) {
		this.variable = variable;
		this.value = value;
	}

	public final T getVariable() {
		return variable;
	}

	public final Set<V> getValue() {
		return value;
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
	
	
}
