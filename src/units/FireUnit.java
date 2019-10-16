package units;

import events.WorldListener;
import simulation.Address;


public abstract class FireUnit extends Unit {

	public FireUnit(String unitID, Address location, int stepsPerCycle,WorldListener worldListener) {
		super(unitID, location, stepsPerCycle,worldListener);
	}

}
