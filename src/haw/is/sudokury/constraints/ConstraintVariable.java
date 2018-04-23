package haw.is.sudokury.constraints;

import java.util.Set;

public abstract class ConstraintVariable<T, V> {
	private final T variable;
	private final Set<V> domain;
	
	protected ConstraintVariable(T variable, Set<V> domain) {
		this.variable = variable;
		this.domain = domain;
	}	
}
