package lexer;

public interface IToken {

	public enum TokenType {
		/** relational operators <(LT), <=(LE), ==(EQ), !=(NE), >(GT), >=(GE) */
		OP_LT, OP_LE, OP_EQ, OP_NE, OP_GT, OP_GE,
		/** ||(OR), &&(AND), !(NOT) */
		OP_OR, OP_AND, OP_NOT,
		/** Plus (+) operator */
		OP_ADD,
		/** Minus (-) operator. Both for unary and binary operations */
		OP_MINUS,
		/** Multiplication (*) operator */
		OP_MUL,
		/** Division (/) operator */
		OP_DIV,
		/** Assignment (=) operator */
		OP_ASSIGN,
		/** Comma (,) operator */
		OP_COMMA,
		/** Dot (.) operator */
		OP_DOT,
		/** Semicolon (;) operator */
		OP_SEMIC,
		/** Other reserverd key words */
		IF, THEN, ELSE, WHILE, DO, BREAK,
		RETURN, PRINT,
		/** True literal - handle as keyword */
		TRUE,
		/** False literal - handle as keyword */
		FALSE,
		/** Function definition */
		DEF,
		/** Record keyword */
		RECORD,
		/** Identifier */
		ID,
		/** String constant */
		STRING,
		/** Integer number */
		INT,
		/** Real number */
		REAL,
		/** "(" */
		LPAREN,
		/** ")" */
		RPAREN,
		/** "[" */
		LBRACKET,
		/** "]" */
		RBRACKET,
		/** "{" */
		LBRACE,
		/** "}" */
		RBRACE,
		/** End-of-file marker */
		EOF
	}

	/**
	 * Get the type of this Token
	 * 
	 * @return Token type
	 */
	TokenType getType();

	/**
	 * Get the Token attribute value
	 * 
	 * E.g. for a Token of type REAL this can be "0.0"
	 * 
	 * @return Attribute value
	 */
	String getAttribute();

	/**
	 * Get the start offset of this Token's attribute
	 * 
	 * @note The position is relative to the beginning of the line
	 * 
	 * @return Start offset
	 */
	int getOffset();

	/**
	 * Get the line number of this Token's attribute
	 * 
	 * @return End offset
	 */
	int getLineNumber();
}
