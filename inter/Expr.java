package inter;

import java.io.FileOutputStream;
import java.io.IOException;

import symbol.*;

public class Expr {
    public Token op;
    public Expr expr1, expr2;
    private int result;
    FileOutputStream fos = null;

    public Expr(Token op, Expr expr1, Expr expr2, FileOutputStream fos) {
        this.op = op;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.fos = fos;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    Expr gen() {
        return new Expr(op, expr1.reduce(), expr2.reduce(), fos);
    }

    Expr reduce() {
        return this;
    }

    public String toString() {
        return expr1.toString() + " " + op.toString() + " " + expr2.toString();
    }

    void emit(String s) throws IOException {
        //System.out.println("\t" + s);
    	fos.write(("          " + s + "\n").getBytes());
    }
}
