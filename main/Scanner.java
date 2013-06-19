package main;

import symbol.*;
import java.io.*;
public class Scanner {
    char look = ' ';
    public FileInputStream source;
    public static int line = 1;

    public Scanner(FileInputStream source) {
        this.source = source;
    }

    void read() throws IOException {
        /*try {
            look = (char) System.in.read();
        } catch(Exception e) {
            e.printStackTrace();
        }*/
	
    	int buffer = source.read();
    	if(buffer != -1) {
    		look = (char)buffer;
    	} else {
    		source.close();
    	}
    }

    Token scan() throws IOException  {
        while(look == ' ' || look == '\t') {
            read();
        }

	while (look == '\n') {
	    line = line + 1;
	    read();
	}

        if(Character.isDigit(look)) {
            int num = 0;
            do {
                num *= 10;
                num += look - '0';
                read();
            } while(Character.isDigit(look));
            return new Num(num);
        }

	if(Character.isLetter(look)){
	    StringBuffer b = new StringBuffer();
	    do {
		b.append(look);
		read();
	    } while(Character.isLetterOrDigit(look));
	    String s = b.toString();
	    return new Word(s);
	}

        Token tok = null;
        switch(look) {
        case '+':
            tok = new Add();
            break;
        case '-':
            tok = new Sub();
            break;
	case '*':
	    tok = new Mul();
	    break;
	case '/':
	    tok = new Div();
	    break;
	case '(':
	    tok = new Token(Type.LPR);
	    break;
	case ')':
	    tok = new Token(Type.RPR);
	    break;
        case '=':
            tok = new Token(Type.EQ);
            break;
        case '$':
            tok = new Token(Type.EOF);
            break;
        default:
            System.out.println("syntax error");
            System.exit(1);
        }
        read();
        return tok;
    }
}
