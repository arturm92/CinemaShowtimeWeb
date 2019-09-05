package cinemaShowtime.utils;

public class Util {

	public static boolean containsSecialCharacter(String s) {
		for (int i = 0; i < s.length();) {
			int codepoint = s.codePointAt(i);
			i += Character.charCount(codepoint);
			if (!(Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.COMMON
					|| Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.LATIN)) {
				return true;
			}
		}
		return false;
	}
}
