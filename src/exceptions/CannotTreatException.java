package exceptions;

import units.Unit;
import simulation.Rescuable;

public class CannotTreatException extends UnitException {
	
	public CannotTreatException() {
		super();
	}
	
	public CannotTreatException(Unit unit, Rescuable target) {
		super(unit, target);
	}
	
	public CannotTreatException(Unit unit, Rescuable target, String message) {
		super(unit, target, message);
	}
	
}
