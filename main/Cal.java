package main;

import symbol.*;
import inter.*;
import java.io.*;

// expr -> unary opE
// unary -> - num | num
// opE -> + num Es opE |
//        - num Es opE
// (opE : operator with expression)
//
// Es -> = Es | epsilon
// (Es : expressions)

class Cal {
    private Scanner scanner;
    private Token look;
    static FileOutputStream fos = null;
   
    public static void main(String args[]) throws IOException {
	String inputFile = "";
	int index = 0;
	while(index < args.length - 1) {
	    inputFile += args[index];
	    index++;
	}
	
	System.out.println("Your Arith File: " + inputFile);
    	FileInputStream fis = null;
        fos = new FileOutputStream("output.asm");

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
        stmt.gen();
        System.out.println("check result : output.asm");
    }

    Cal(Scanner s) {
        scanner = s;
    }

    Expr expr() throws IOException {
        Expr expr = null;
        next();
        switch(look.getType()) {
        case Type.SUB:
        case Type.NUM:
            // expr -> [unary] opE
            Expr expr1 = unary();

            // expr -> unary [opE]
            expr = opE(expr1);
            break;
        default:
            syntaxError();
        }
        return expr;
    }

    Expr opE(Expr expr1) throws IOException   {
        Expr expr = null;
        switch(look.getType()) {
        case Type.ADD:
        case Type.SUB:
            //System.out.println("cal : " + expr1.getResult());
	    fos.write(("cal : " + expr1.getResult() + "\n").getBytes());

            // [+] num Es opE | [-] num Es opE
            Token op = look;
            next();

            // + [num] Es opE | - [num] Es opE
            Expr expr2 = num();

            // Build AST node.
            expr = new ExprArith(op, expr1, expr2, fos);

            // + num [Es] opE | - num [Es] opE
            boolean cal = false;
            expr = Es(expr, cal);

            // + num Es [opE] | - num Es [opE]
            expr = opE(expr);
            break;
        case Type.EOF:
            expr = expr1;
            break;
        default:
            syntaxError();
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
	    fos.write(("" + expr.getResult()).getBytes());

            // Es -> [=] Es | epsilon
            next();

            // Es -> = [Es] | epsilon
            expr = Es(expr, cal);
            break;
        case Type.ADD:
        case Type.SUB:
        case Type.EOF:
            break;
        default:
            syntaxError();
        }
        return expr;
    }

    Expr unary() throws IOException {
        Expr expr = null;
        switch(look.getType()) {
        case Type.SUB:
            // unary -> [-] num | num
            next();

            // unary -> - [num] | num
            expr = num();

            expr.setResult(-expr.getResult());
            break;
        case Type.NUM:
            // unary -> - num | [num]
            expr = num();
            break;
        default:
            syntaxError();
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
            syntaxError();
        }
        return expr;
    }

    void next() throws IOException {
        look = scanner.scan();
    }

    void syntaxError() {
        System.out.println("Syntax error");
        System.exit(1);
    }
}
