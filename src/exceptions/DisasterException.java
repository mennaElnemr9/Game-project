package exceptions;

import disasters.Disaster;

abstract public class DisasterException extends SimulationException {
	private Disaster disaster; //read only 
	
	public DisasterException() {
		super();
	}
	
	public Disaster getDisaster() {
		return disaster;
	}
	public DisasterException(Disaster disaster) {
		super();
		this.disaster = disaster;
	}
	
	public DisasterException(Disaster disaster,String message){
		super(message);
		this.disaster = disaster;
		
	}
	
	
	
}
