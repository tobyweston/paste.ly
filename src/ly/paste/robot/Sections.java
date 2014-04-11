package ly.paste.robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;

public class Sections {

	private List<String> sections = new ArrayList<String>();
	private Iterator<String> iterator = new EmptyIterator();
	private Boolean reset = false;

	public void initialise(String text) {
		sections = asList(text.split("§"));
		iterator = sections.iterator();
		reset = false;
	}

	public boolean isEmpty() {
		return !iterator.hasNext();
	}

	public String next() {
		if (isEmpty())
			return "";
		return iterator.next();
	}

	public void shouldReset() {
		reset = true;
	}

	public boolean reset() {
		return reset;
	}

	private static class EmptyIterator implements Iterator<String> {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public String next() {
			return "";
		}

		@Override
		public void remove() {
		}
	}
}
