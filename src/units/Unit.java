package units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import disasters.Collapse;
import disasters.Disaster;
import disasters.Fire;
import disasters.GasLeak;
import events.SOSResponder;
import events.WorldListener;
import infrastructure.ResidentialBuilding;
import people.Citizen;
import people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setStepsPerCycle(int stepsPerCycle) {
		this.stepsPerCycle = stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		boolean flag = false;
		if (r instanceof ResidentialBuilding) {
			ResidentialBuilding b = (ResidentialBuilding) r;
			if(this instanceof MedicalUnit) {
				flag = true;
				throw new IncompatibleTargetException(this, r);
			}
		}
		else {
			if(this instanceof Evacuator || this instanceof FireTruck || this instanceof GasControlUnit){
				flag = true;
				throw new IncompatibleTargetException(this, r);
			}
		}
		
		if (!flag) {
			if (target != null && state == UnitState.TREATING)
				reactivateDisaster();
			finishRespond(r);
		}

	}

	public void reactivateDisaster() {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX()) + Math.abs(t.getY() - location.getY());

	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}

	//public boolean canTreat(Rescuable r) {
//		if (r instanceof Citizen) {
//			Citizen c = (Citizen) r;
//			if (c.getState() == CitizenState.SAFE || c.getState() == CitizenState.RESCUED)
//				return false;
//			else
//				return true;
//
//		} else {
//			ResidentialBuilding b = (ResidentialBuilding) r;
//			if ( (b.getFireDamage() == 0 && b.getDisaster() instanceof Fire)
//					|| (b.getGasLevel() == 0 && b.getDisaster() instanceof GasLeak)
//					|| (b.getFoundationDamage() == 0 && b.getDisaster() instanceof Collapse))
//				return false;
//			else
//				return true;
//		}
	//}
}
