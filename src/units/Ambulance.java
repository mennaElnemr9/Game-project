package units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import disasters.Collapse;
import disasters.Fire;
import disasters.GasLeak;
import disasters.Injury;
import events.WorldListener;
import infrastructure.ResidentialBuilding;
import people.Citizen;
import people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {

	public Ambulance(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);

	}

	@Override
	public void treat() {
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

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
		if(!flag){
			if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0 && getState() == UnitState.TREATING)
				reactivateDisaster();
			finishRespond(r);
		}
	}
	public boolean canTreat(Rescuable r) {
			Citizen c = (Citizen) r;
			//if (c.getState() == CitizenState.SAFE || c.getState() == CitizenState.RESCUED)
			if(c.getBloodLoss() == 0 || c.getState()==CitizenState.DECEASED || c.getHp()==0)
				return false;
			else
				return true;
	}
}
