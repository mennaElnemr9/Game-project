package disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import infrastructure.ResidentialBuilding;


public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	public void strike() throws CitizenAlreadyDeadException, BuildingAlreadyCollapsedException 
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();	
		target.setFoundationDamage(target.getFoundationDamage()+10);
		super.strike();
	}
	public void cycleStep()
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFoundationDamage(target.getFoundationDamage()+10);
	}

}
