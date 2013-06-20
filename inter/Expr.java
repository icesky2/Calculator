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
        return op.toString() + "  " + "  " +expr1.toString() +" , " + expr2.toString();
    }

    public String opToString() {
	return op.toString();
    }

    public String exprToString() {
        return expr1.toString() +" , " + expr2.toString();
    }

    public String expr1ToString() {
        return expr1.toString();
    }

    public String expr2ToString() {
        return expr2.toString();
    }

    public void jumping(int t, int f) {
        emitjumps(t, f);
    }

    public void emitjumps(int t, int f) { // nothing since both t and f fall through
    }

    void emit(String s) throws IOException {
        //System.out.println("\t" + s);
    	fos.write((s + "\n").getBytes());
    }
}
