package units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import disasters.Infection;
import events.WorldListener;
import infrastructure.ResidentialBuilding;
import people.Citizen;
import people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class DiseaseControlUnit extends MedicalUnit {

	public DiseaseControlUnit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);
		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getToxicity() > 0) {
			target.setToxicity(target.getToxicity() - getTreatmentAmount());
			if (target.getToxicity() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getToxicity() == 0)
			heal();

	}

	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		boolean flag = false;
		if (r instanceof ResidentialBuilding) {
			flag = true;
			throw new IncompatibleTargetException(this, r);
		}
		if (!this.canTreat(r)) {
			flag = true;
			throw new CannotTreatException(this, r);
		}
		if (!flag) {
			if (getTarget() != null && ((Citizen) getTarget()).getToxicity() > 0 && getState() == UnitState.TREATING)
				reactivateDisaster();
			finishRespond(r);
		}
	}
	public boolean canTreat(Rescuable r) {
		Citizen c = (Citizen) r;
		//if (c.getState() == CitizenState.SAFE || c.getState() == CitizenState.RESCUED)
		if(c.getToxicity() == 0)
			return false;
		else
			return true;
}
}
