package symbol;

public class Word extends Token {
    private String lexeme;

    public Word(String s) {
	super(Type.WORD);
	lexeme = s;
    }

    public String toString() {
	return lexeme;
    }
}

