package exceptions;

import units.Unit;
import simulation.Rescuable;

public class IncompatibleTargetException extends UnitException {
	
	public IncompatibleTargetException() {
		super();
	}
	
	public IncompatibleTargetException(Unit unit, Rescuable target){
		super(unit,target);
	}
	public IncompatibleTargetException(Unit unit, Rescuable target, String message){
		super(unit,target,message);
	}
}
