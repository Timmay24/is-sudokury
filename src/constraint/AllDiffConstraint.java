package constraint;

public class AllDiffConstraint implements Constraint {

    private final ConstraintVariable f;
    private final ConstraintVariable g;

	public AllDiffConstraint (ConstraintVariable f, ConstraintVariable g) {
        this.f = f;
        this.g = g;
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
		AllDiffConstraint other = (AllDiffConstraint) obj;
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
