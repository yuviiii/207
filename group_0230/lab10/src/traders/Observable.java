package traders;

import java.util.Collection;

public abstract class Observable implements Observer{

	private static Collection<Observer> observerCollection;

	public int getPrice(){
		return 0;
	}
	
	public void registerObserver(Observer o){
		observerCollection.add(o);
	}
	
	
	public void unregisterObserver(Observer o){
		
	}
	
	public void notifyObservers(){
		
	}
}
