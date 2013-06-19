package main;

import symbol.*;
import inter.*;
import java.io.*;

// expr -> term termlist
// termlist -> + term Es termlist |
//	       - term Es termlist 
// term ->  factor factorist |        
// factorist -> * factor Es factorist |
//              / factor Es factorist |
// fator -> num | (expr)
// Es -> = Es | epsilon
// (Es : expressions)

class Cal {
    private Scanner scanner;
    private Token look;
    static FileOutputStream fos = null;
    static String assign = "";
   
    public static void main(String args[]) throws IOException {
	String inputFile = args[0];
        fos = new FileOutputStream(args[2]);
    	FileInputStream fis = null;

	try {
	    fis = new FileInputStream(inputFile);
	} catch(FileNotFoundException e) {
	    e.printStackTrace();
	}

	Scanner s = new Scanner(fis);
		
        Cal c = new Cal(s);
        Expr e = c.expr();
        
        // Code generation.
        Stmt stmt = new Stmt(e, fos);
	int begin = stmt.newLabel();
        int after = stmt.newLabel();
        stmt.emitLabel(begin);
        stmt.gen(assign, begin, after);
        stmt.emitLabel(after);
    }

    Cal(Scanner s) {
        scanner = s;
    }

    /*Stmt stmts(FileOutputStream fos) throws IOException {
        if (look.getType() != '$') {
            return new Seq(Stmt(expr(), fos), stmts(fos));
        } 
	return  Stmt.Null;
    }*/

    Expr expr() throws IOException {
        Expr expr = null;
        next();
        switch(look.getType()) {
	case Type.WORD:
	    assign += look.toString();
	    next();
	    if(look.getType() == Type.EQ) {
		expr = expr();
	   } else {
		break;
	   }
	break;
	case Type.SUB:
        case Type.NUM:
            // expr ->[term] termlist
            Expr expr1 = term();

            // expr -> term [termlist]
            expr = termlist(expr1);
            break;
        default:
            syntaxError("expr");
        }
        return expr;
    }

    Expr termlist(Expr expr1) throws IOException   {
        Expr expr = null;
        switch(look.getType()) {
  	case Type.ADD:
        case Type.SUB:
            //System.out.println("cal : " + expr1.getResult());
	    //fos.write(("cal : " + expr1.getResult() + "\n").getBytes());

            // [+] term termlist Es | [-] term termlist Es
            Token op = look;
	    next();
   
            // + [term] termlist Es | - [term] termlist Es
            Expr expr2 = term();

            // Build AST node.
            expr = new ExprArith(op, expr1, expr2, fos);
	
            //+ term [Es] termlist| - term [Es] termlist 
            boolean cal = false;
            expr = Es(expr, cal);

	    // + term Es [termlist] | - term Es [termlist] 
 	    expr = termlist(expr);
            break;
	case Type.MUL:
        case Type.DIV:
	case Type.LPR:
	case Type.RPR:
	case Type.EQ:
        case Type.EOF:
	case Type.WORD:
            expr = expr1;
            break;
        default:
            syntaxError("termlist");
        }
        return expr;
    }

    Expr term() throws IOException   {
	Expr expr = null;
	switch(look.getType()) {
	case Type.NUM:
	    Expr expr1 = num();
	    expr = factorlist(expr1);
	    break;
	default:
	    syntaxError("term");
        }
        return expr;
    }

    Expr factorlist(Expr expr1) throws IOException   {
        Expr expr = null;
        switch(look.getType()) {
	case Type.MUL:
	case Type.DIV:
            //System.out.println("cal : " + expr1.getResult());
	    //fos.write(("cal : " + expr1.getResult() + "\n").getBytes());

            // [*] factor factorist | [/] factor factorist
            Token op = look;
            next();

            // * [factor] factorist | / [factor] factorist
            Expr expr2 = factor();

            // Build AST node.
            expr = new ExprArith(op, expr1, expr2, fos);

            // * factor [Es] factorist | / factor [Es] factorist
            boolean cal = false;
            expr = Es(expr, cal);

	    // * factor Es [factorist] | / factor Es [factorist]*/
	    expr = factorlist(expr);
            break;
 	case Type.ADD:
        case Type.SUB:
	case Type.LPR:
	case Type.RPR:
	case Type.EQ:
        case Type.EOF:
	case Type.WORD:
            expr = expr1;
            break;
        default:
            syntaxError("factorlist");
        }
        return expr;
    }

    Expr factor() throws IOException {
	Expr expr = null;	
	switch(look.getType()) {
	case Type.NUM:
	    expr = num();
	    break;
	case Type.LPR:
	    expr = expr();
	    next();
	    if(look.getType() == Type.RPR) {
		break;
	    }
	break;
	default:
            syntaxError("factor");
        }
        return expr;
    }

    Expr Es(Expr expr, boolean cal) throws IOException {
        switch(look.getType()) {
        case Type.EQ:
            if(cal) {
                // Build AST node.
                expr = new ExprArith(expr.op, expr, expr.expr2, fos);
            } else {
                cal = true;
            }
 
            //System.out.println(expr.getResult());
	    //fos.write(("" + expr.getResult()).getBytes());

            // Es -> [=] Es | epsilon
            next();

            // Es -> = [Es] | epsilon
            expr = Es(expr, cal);
            break;
        case Type.ADD:
        case Type.SUB:
	case Type.MUL:
	case Type.DIV:
	case Type.LPR:
	case Type.RPR:
        case Type.EOF:
	case Type.WORD:
            break;
        default:
            syntaxError("Es");
        }
        return expr;
    }

    Expr num() throws IOException {
        Expr expr = null;
        switch(look.getType()) {
        case Type.NUM:
            expr = new ExprNum(look, fos);
            next();
            break;
        default:
            syntaxError("num");
        }
        return expr;
    }

    void next() throws IOException {
        look = scanner.scan();
    }

    void syntaxError(String s) {
        System.out.println(s + " Syntax error");
        System.exit(1);
    }
}
