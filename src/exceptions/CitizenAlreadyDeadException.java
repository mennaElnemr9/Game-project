package exceptions;

import disasters.Disaster;

public class CitizenAlreadyDeadException extends DisasterException{
	
	public CitizenAlreadyDeadException() {
		super();
	}
    
	public CitizenAlreadyDeadException(Disaster disaster) {
		super (disaster);
	}
	
	public CitizenAlreadyDeadException(Disaster disaster,String message){
		super (disaster);
	}
}
