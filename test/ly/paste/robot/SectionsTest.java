package ly.paste.robot;

import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SectionsTest {

	private final Sections sections = new Sections();

	@Test
	public void shouldInitialise() {
		assertThat(sections.isEmpty(), is(true));
		assertThat(sections.next(), is(""));
		sections.initialise("some string");
		assertThat(sections.next(), is("some string"));
		assertThat(sections.isEmpty(), is(true));
	}

	@Test
	public void shouldConsumeString() {
		sections.initialise("some string");
		sections.next();
		assertThat(sections.isEmpty(), is(true));
		assertThat(sections.next(), is(""));
	}

	@Test
	public void shouldParseTextBySectionMarker() {
		sections.initialise("1§2");
		assertThat(sections.next(), is("1"));
		assertThat(sections.next(), is("2"));
		assertThat(sections.next(), is(""));
	}

}
