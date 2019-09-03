package cinemaShowtime;

public class Util {
	
	public static boolean containsSecialCharacter(String s) {
		for (int i = 0; i < s.length();) {
			int codepoint = s.codePointAt(i);
			i += Character.charCount(codepoint);
			if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN
					|| Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.CYRILLIC) {
				return true;
			}
		}
		return false;
	}
}
