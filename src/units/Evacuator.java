package units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import disasters.Collapse;
import events.WorldListener;
import infrastructure.ResidentialBuilding;
import people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {

	public Evacuator(String unitID, Address location, int stepsPerCycle, WorldListener worldListener, int maxCapacity) {
		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);

	}

	@Override
	public void treat() {
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0 || target.getOccupants().size() == 0) {
			jobsDone();
			return;
		}

		for (int i = 0; getPassengers().size() != getMaxCapacity() && i < target.getOccupants().size(); i++) {
			getPassengers().add(target.getOccupants().remove(i));
			i--;
		}

		setDistanceToBase(target.getLocation().getX() + target.getLocation().getY());

	}

	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		boolean flag = false;
		if (r instanceof Citizen) {
			flag = true;
			throw new IncompatibleTargetException(this, r);
		}

			if (!this.canTreat(r)) {
			flag = true;
			
			throw new CannotTreatException(this, r);
		}
		if (!flag) {
			super.respond(r);
		}
	}
	public boolean canTreat(Rescuable r) {
		ResidentialBuilding b = (ResidentialBuilding) r;
		
		if (b.getStructuralIntegrity() ==0 || b.getFoundationDamage() ==0 )
			return false;
		else
			return true;
	}


}
