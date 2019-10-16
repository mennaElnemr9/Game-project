package units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import disasters.Collapse;
import disasters.Fire;
import disasters.GasLeak;
import events.WorldListener;
import infrastructure.ResidentialBuilding;
import people.Citizen;
import people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class FireTruck extends FireUnit {

	public FireTruck(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getFireDamage() > 0)

			target.setFireDamage(target.getFireDamage() - 10);

		if (target.getFireDamage() == 0)

			jobsDone();

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
		//if ((b.getFireDamage() == 0 && b.getDisaster() instanceof Fire))
		if (b.getFireDamage() == 0 || b.getStructuralIntegrity()==0)
			return false;
		else
			return true;
	}

}
