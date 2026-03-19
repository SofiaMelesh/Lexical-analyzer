package mathlogic_lab1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.function.Function;

public class LexemeType {
	private String name;
	private Pattern pattern;
	private Function<String, String> valueFunc;

	public LexemeType(String name, String regex) {
		this(name, regex, s -> "");
	}

	public LexemeType(String name, String regex, Function<String, String> valueFunc) {
		this.name = name;

		this.pattern = Pattern.compile("^\\s*(" + regex + ")");
		this.valueFunc = valueFunc;
	}

	public Lexem consume(String text) {
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			String lexemeText = matcher.group(1);
			String value = valueFunc.apply(lexemeText);
			return new Lexem(lexemeText, name, value);
		}
		return null;
	}

	public int getConsumedLength(String text) {
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return matcher.end();
		}
		return 0;
	}

	public String getName() {
		return name;
	}
}