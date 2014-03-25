package ly.paste.robot;

import com.google.gson.internal.Streams;
import groovy.transform.NotYetImplemented;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.*;

public class Sections {

	private List<String> sections = new ArrayList<String>();
	private Iterator<String> iterator = new EmptyIterator();

	public void initialise(String text) {
		sections = asList(text.split("§"));
		iterator = sections.iterator();
	}

	public boolean isEmpty() {
		return !iterator.hasNext();
	}

	public String next() {
		if (isEmpty())
			return "";
		return iterator.next();
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
	}
}
