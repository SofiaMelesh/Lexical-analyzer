package mathlogic_lab1;

import java.util.*;

public class Lexer {
	private List<LexemeType> lexemeTypes;
	private Set<String> skipTypes;

	public Lexer(List<LexemeType> lexemeTypes, String[] skipTypes) {
		this.lexemeTypes = lexemeTypes;
		this.skipTypes = new HashSet<>(Arrays.asList(skipTypes));
	}

	public List<Lexem> analyze(String text) {
		List<Lexem> lexems = new ArrayList<>();
		int pos = 0;
		int length = text.length();

		while (pos < length) {
			// Пропускаем пробелы
			if (Character.isWhitespace(text.charAt(pos))) {
				pos++;
				continue;
			}

			// Проверяем комментарий
			if (text.charAt(pos) == '#') {
				int endComment = pos;
				while (endComment < length && text.charAt(endComment) != '\n') {
					endComment++;
				}
				pos = endComment;
				continue;
			}

			// Собираем токен до пробела или спецсимвола
			StringBuilder token = new StringBuilder();
			int start = pos;

			// Специальные символы (одиночные)
			char c = text.charAt(pos);
			if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '|') {
				token.append(c);
				pos++;
			}
			// Проверка на :=
			else if (c == ':' && pos + 1 < length && text.charAt(pos + 1) == '=') {
				token.append(":=");
				pos += 2;
			} else {
				while (pos < length) {
					c = text.charAt(pos);
					if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '|' || c == ':'
							|| Character.isWhitespace(c)) {
						break;
					}
					token.append(c);
					pos++;
				}
			}

			String tokenStr = token.toString();

			Lexem foundLexem = null;
			// Сначала пробуем как HEX (должен начинаться с цифры)
			if (tokenStr.matches("[0-9][0-9a-fA-F]*")) {
				boolean validHex = true;
				for (int i = 0; i < tokenStr.length(); i++) {
					char ch = tokenStr.charAt(i);
					if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F'))) {
						validHex = false;
						break;
					}
				}

				if (validHex) {
					try {
						String value = String.valueOf(Long.parseLong(tokenStr.toLowerCase(), 16));
						foundLexem = new Lexem(tokenStr, "HEX_NUMBER", value);
					} catch (NumberFormatException e) {
						System.out.println("Ошибка: некорректное HEX число '" + tokenStr + "'");
					}
				} else {
					System.out.println("Ошибка: недопустимый символ в HEX числе '" + tokenStr + "'");
				}
			} else if (tokenStr.matches("[A-Za-z][A-Za-z0-9]*")) {
				if (tokenStr.length() > 15) {
					System.out.println("Ошибка: идентификатор '" + tokenStr + "' превышает длину 15");
					// не добавляем в таблицу лексем
				} else {
					// Добавляем в таблицу идентификаторов

					String value;
					if (LexemeDefinitions.hasIdentifier(tokenStr)) {
						value = LexemeDefinitions.getIdentifierValue(tokenStr);
					} else {
						value = tokenStr + " : " + (getNextId());
						addIdentifier(tokenStr, value);
					}

					foundLexem = new Lexem(tokenStr, "IDENTIFIER", value);
				}
			}
			// Проверяем операторы
			else if (tokenStr.equals(":=")) {
				foundLexem = new Lexem(tokenStr, "ASSIGN", "");
			} else if (tokenStr.equals("+") || tokenStr.equals("-") || tokenStr.equals("*") || tokenStr.equals("/")) {
				foundLexem = new Lexem(tokenStr, "ARITHMETIC_OP", "");
			} else if (tokenStr.equals("(")) {
				foundLexem = new Lexem(tokenStr, "LPAREN", "");
			} else if (tokenStr.equals(")")) {
				foundLexem = new Lexem(tokenStr, "RPAREN", "");
			} else if (tokenStr.equals("|")) {
				foundLexem = new Lexem(tokenStr, "SEPARATOR", "");
			} else {
				System.out.println("Недопустимая лексема: " + tokenStr);
				// НЕ добавляем в таблицу лексем
			}

			if (foundLexem != null) {
				lexems.add(foundLexem);
			}
		}

		return lexems;
	}

	private int getNextId() {
		return LexemeDefinitions.getIdCounter();
	}

	private void addIdentifier(String name, String value) {
		LexemeDefinitions.addIdentifier(name, value);
	}
}