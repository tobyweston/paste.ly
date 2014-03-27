package ly.paste.robot;

import java.util.ArrayList;
import java.util.List;

class Observable {

	private final List<Observer> observers = new ArrayList<Observer>();

	public void addObserver(Observer... observers) {
		for (Observer observer : observers)
			this.observers.add(observer);
	}

	public void removeObserver(Observer... observers) {
		for (Observer observer : observers) {
			this.observers.remove(observer);
		}
	}

	public void notifyObservers(KeyHeldEvent event) {
		for (Observer observer : observers)
			observer.update(this, event);
	}

	public void notifyObservers(KeyPressedEvent event) {
		for (Observer observer : observers)
			observer.update(this, event);
	}

}
