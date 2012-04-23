package lexer;

public interface IToken {

	public enum TokenType {
		/** relational operators <(LT), <=(LE), ==(EQ), !=(NE), >(GT), >=(GE) */
		RELOP,
		/** ||(OR), &&(AND), !(NOT) */
		BOOLOP,
		/** Arithmetic Operator +(SUM) -(SUB) *(MUL) /(DIV) -(NEG) */
		ARITHOP,
		/** Assignment operator */
		ASSIGN,
		/** Other reserverd words */
		IF, THEN, ELSE, WHILE, DO, BREAK, // no continue?
		RETURN, PRINT,
		/** Function definition */
		DEF,
		/** Identifier */
		ID,
		/** String constant */
		STRING,
		/** Integer number */
		INT,
		/** Real number */
		REAL,
		/**
		 * For array defintions, this marks the field count
		 */
		NUM,
		/** Terminal symbols */
		BRL, BRR, SBRL, SBRR, CBRL, CBRR,
		/** Comma ',' operator */
		COMMA,
		/** Dot operator */
		DOT,
		/** Semicolon (;) operator */
		SEMIC
	}

	TokenType getType();

	String getAttribute();

	/**
	 * Get the start offset of this Token's attribute
	 *
	 * @return Start offset
	 */
	int getOffset();

	/**
	 * Get the nine number of this Token's attribute
	 *
	 * @return End offset
	 */
	int getLineNumber();
}
