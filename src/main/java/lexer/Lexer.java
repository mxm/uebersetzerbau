package lexer;

import lexer.IToken.TokenType;
import lombok.Getter;

public class Lexer implements ILexer {

	@Getter
	private int lineNumber;

	private String delimiterRegexp = "[\\s(=)+-/\\*]";

	InputStream is;

	public Lexer(String file) {
		lineNumber = 1;
		is = new InputStream(file);
	}

	public Token getNextToken() throws SyntaxErrorException {
		String peek;
		do {
			if(is.isEmpty())
				return null; // End of input stream - nothing more to read
			peek = is.getNextChars(1);
			if (peek.matches("\\n"))
				this.lineNumber += 1;
			if (peek.matches("\\s"))
				is.removeChars(1);
		} while (peek.matches("\\s"));
		Token t;
		if ((t = reservedAndTerminals()) != null) {
			return t;
		}
		if ((t = identifier()) != null) {
			return t;
		}
		if ((t = stringConstant()) != null) {
			return t;
		}
		if ((t = numConstant()) != null) {
			return t;
		}
		//if no rule could be applied there is something wrong!
		throw new SyntaxErrorException("Undefined something at line " + lineNumber + " near " + peek);
	}

	private Token numConstant() throws SyntaxErrorException {
		int state = 0;
		String result = "";
		String peek = is.getNextChars(1);
		if (peek.matches("\\d")) {
			if (state == 0)
				state = 1;
			else {
				state = 8;
			}
			while (new String(peek).matches("\\d")) {
				is.removeChars(1);
				result += peek;
				peek = is.getNextChars(1);
			}
		}

		if (peek.equals(".")) {
			if (state == 0)
				state = 2;
			else if (state == 1)
				state = 4;
			else
				state = 8;
			is.removeChars(1);
			result += peek;
			peek = is.getNextChars(1);
			if (peek.matches("\\d")) {
				if (state == 2)
					state = 3;
				else if (state == 4)
					state = 4;
				else
					state = 8;
			}
			while (peek.matches("\\d")) {
				is.removeChars(1);
				result += peek;
				peek = is.getNextChars(1);
			}
		}
		if (peek.equals("e") || peek.equals("E")) {
			if (state == 3 || state == 4)
				state = 5;
			else
				state = 8;
			is.removeChars(1);
			result += peek;
			peek = is.getNextChars(1);
			if (peek.equals("+") || peek.equals("-")) {
				if (state == 5)
					state = 6;
				else
					state = 8;
				result += peek;
				is.removeChars(1);
				peek = is.getNextChars(1);
			}
			if (!(peek.matches("\\d"))) {
				// TODO: throw new
				// SyntaxErrorException("Malformed floating point number");
				state = 8;
			} else {
				if (state == 6)
					state = 7;
				else
					state = 8;
			}
			while (peek.matches("\\d")) {
				is.removeChars(1);
				result += peek;
				peek = is.getNextChars(1);
			}
		}
		if (state == 1 || state == 3 || state == 4 || state == 7)
			return new Token(TokenType.NUM, result);
		else
			throw new SyntaxErrorException("Malformed floating point number");
	}

	private Token identifier() throws SyntaxErrorException {
		String peek = is.getNextChars(1);
		String id = "";
		if (peek.matches("[A-Za-z]"))
			while (!is.isEmpty() && peek.matches("[A-Za-z0-9_]")) {
				id += peek;
				is.removeChars(1);
				peek = is.getNextChars(1);
			}
		if (!id.isEmpty())
			return new Token(TokenType.ID, id);
		return null;

	}

	private Token stringConstant() throws SyntaxErrorException {
		String peek = is.getNextChars(1);
		String delimiter = "'";
		switch (peek.charAt(0)) {
		case '\'':
			break;
		case '"':
			delimiter = "\"";
			break;
		default:
			return null;
		}
		is.removeChars(1);
		String result = "";
		while (true) {
			peek = is.getNextChars(1);
			is.removeChars(1);
			if (peek.matches("\\s") && peek.charAt(0) != ' ') {
				// throw new
				// SyntaxErrorException("Unallowed whitespace in string in line "
				// + this.lineNumber);
				return null;
			}
			if (peek.startsWith(delimiter)) {
				return new Token(TokenType.STRING, result);
			}
			result += new String(peek);
			if (is.isEmpty()) {
				return new Token(TokenType.STRING, result);
			}
		}
	}

	private Token reservedAndTerminals() throws SyntaxErrorException {
		/* if then else while do break return print */
		String s = is.getNextChars(1);
		if (s.equals("i")) {
			// TODO: replace delimiterRegexp with real delimiters (there is no '+' after if!) 
			if (is.getNextChars(3).matches("if" + delimiterRegexp)) {
				is.removeChars(2);
				return new Token(TokenType.IF, null);
			}
			// TODO: replace delimiterRegexp with real delimiters
			if (is.getNextChars(4).matches("int" + delimiterRegexp)) {
				is.removeChars(3);
				return new Token(TokenType.INT, null);
			}
		}
		if (s.equals("t")) {
			s = is.getNextChars(5);
			// TODO: replace delimiterRegexp with real delimiters
			if (s.matches("then" + delimiterRegexp)) {
				is.removeChars(4);
				return new Token(TokenType.THEN, null);
			}
		}
		// TODO: replace delimiterRegexp with real delimiters
		if (s.equals("e")
				&& is.getNextChars(5).matches("else" + delimiterRegexp)) {
			is.removeChars(4);
			return new Token(TokenType.ELSE, null);
		}
		// TODO: replace delimiterRegexp with real delimiters
		if (s.equals("w")
				&& is.getNextChars(6).matches("while" + delimiterRegexp)) {
			is.removeChars(5);
			return new Token(TokenType.WHILE, null);
		}
		// TODO: replace delimiterRegexp with real delimiters
		if (s.equals("d")) {
			if (is.getNextChars(3).matches("do" + delimiterRegexp)) {
				is.removeChars(2);
				return new Token(TokenType.DO, null);
			} else if (is.getNextChars(4).matches("def ")) {
				is.removeChars(3);
				return new Token(TokenType.DEF, null);
			}
		}
		// TODO: replace delimiterRegexp with real delimiters
		if (s.equals("r")) {
			if (is.getNextChars(7).matches("return" + delimiterRegexp)) {
				is.removeChars(6);
				return new Token(TokenType.RETURN, null);
			}
			if (is.getNextChars(5).equals("real ")) {
				is.removeChars(4);
				return new Token(TokenType.REAL, null);
			}
		}
		// TODO: replace delimiterRegexp with real delimiters
		if (s.equals("b")
				&& is.getNextChars(6).matches("break" + delimiterRegexp)) {
			is.removeChars(5);
			return new Token(TokenType.BREAK, null);
		}
		// TODO: replace delimiterRegexp with real delimiters
		if (s.equals("p")
				&& is.getNextChars(6).matches("print" + delimiterRegexp)) {
			is.removeChars(5);
			return new Token(TokenType.PRINT, null);
		}
		if (s.equals("+")) {
			is.removeChars(1);
			return new Token(TokenType.ARITHOP, "ADD");
		}
		if (s.equals("-")) {
			is.removeChars(1);
			return new Token(TokenType.ARITHOP, "SUB");
		}
		if (s.equals("*")) {
			is.removeChars(1);
			return new Token(TokenType.ARITHOP, "MUL");
		}
		if (s.equals("/")) {
			is.removeChars(1);
			return new Token(TokenType.ARITHOP, "DIV");
		}
		if (s.equals("&")) {
			if (is.getNextChars(2).equals("&&")) {
				is.removeChars(2);
				return new Token(TokenType.BOOLOP, "AND");
			}
		}
		if (s.equals("|")) {
			if (is.getNextChars(2).equals("||")) {
				is.removeChars(2);
				return new Token(TokenType.BOOLOP, "OR");
			}
		}
		if (s.equals("!")) {
			s = is.getNextChars(2);
			if (s.equals("!=")) {
				is.removeChars(2);
				return new Token(TokenType.RELOP, "NE");
			} else {
				is.removeChars(1);
				return new Token(TokenType.BOOLOP, "NOT");
			}
		}
		if (s.equals("<")) {
			s = is.getNextChars(2);
			if (s.equals("<=")) {
				is.removeChars(2);
				return new Token(TokenType.RELOP, "LE");
			} else {
				is.removeChars(1);
				return new Token(TokenType.RELOP, "LT");
			}
		}
		if (s.equals(">")) {
			s = is.getNextChars(2);
			if (s.equals(">=")) {
				is.removeChars(2);
				return new Token(TokenType.RELOP, "GE");
			} else {
				is.removeChars(1);
				return new Token(TokenType.RELOP, "GT");
			}
		}
		if (s.equals("=")) {
			s = is.getNextChars(2);
			if (s.equals("==")) {
				is.removeChars(2);
				return new Token(TokenType.RELOP, "EQ");
			} else {
				is.removeChars(1);
				return new Token(TokenType.ASSIGN, null);
			}
		}
		if (s.equals("(")) {
			is.removeChars(1);
			return new Token(TokenType.BRL, null);
		}
		if (s.equals(")")) {
			is.removeChars(1);
			return new Token(TokenType.BRR, null);
		}
		if (s.equals("[")) {
			is.removeChars(1);
			return new Token(TokenType.SBRL, null);
		}
		if (s.equals("]")) {
			is.removeChars(1);
			return new Token(TokenType.SBRR, null);
		}
		if (s.equals("{")) {
			is.removeChars(1);
			return new Token(TokenType.CBRL, null);
		}
		if (s.equals("}")) {
			is.removeChars(1);
			return new Token(TokenType.CBRR, null);
		}
		if (s.equals(";")) {
			is.removeChars(1);
			return new Token(TokenType.SEMIC, null);
		}
		if (s.equals(",")){
			is.removeChars(1);
			return new Token(TokenType.COMMA, null);
		}
		if (s.equals(".")) {
			is.removeChars(1);
			return new Token(TokenType.DOT, null);
		}
		return null;
	}
}
