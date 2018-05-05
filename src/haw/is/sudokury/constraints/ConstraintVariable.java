package haw.is.sudokury.constraints;

import java.util.HashSet;
import java.util.Objects;

public class ConstraintVariable<T, V> implements Comparable<V>{
	private final T variable;
	private final HashSet<V> domain;
	
	public ConstraintVariable(T variable, HashSet<V> domain) {
		this.variable = variable;
		this.domain = domain;
	}

	public ConstraintVariable(ConstraintVariable<T, V> constraintVariable) {
	    this(constraintVariable.getVariable(), new HashSet<>(constraintVariable.getDomain()));
    }

	public T getVariable() {
		return variable;
	}

	public HashSet<V> getDomain() {
		return domain;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstraintVariable<?, ?> that = (ConstraintVariable<?, ?>) o;
        return Objects.equals(variable, that.variable) &&
                Objects.equals(domain, that.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable, domain);
    }

    @Override
	public int compareTo(Object o) {
		ConstraintVariable<T, V> var = (ConstraintVariable<T, V>) o;
        if (this.getDomain().size() == var.getDomain().size()) {
        	return 0;
        } else if (this.getDomain().size() > var.getDomain().size()) {
        	return 1;
        } else {
        	return -1;
        }
    }

    public ConstraintVariable<T, V> copy() {
	    return new ConstraintVariable<>(this);
    }

    @Deprecated
	public ConstraintVariable cloneVar() {
		return new ConstraintVariable(this.getVariable(), new HashSet<>(this.getDomain()));
	}
}
