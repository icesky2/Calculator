package inter;

import java.io.FileOutputStream;
import java.io.IOException;
import main.*;

public class Stmt {
    private Expr expr;
    public int scanLine = 0;
    public static int label = 0;
    static FileOutputStream fos = null;
    int after = 0;
    
    public Stmt(Expr e, FileOutputStream fos) {
        expr = e;
        this.fos = fos;
	scanLine = Scanner.line;
    }
    public static Stmt Null = new Stmt(null, null);
    void error() throws IOException {
        System.out.println("near line " + scanLine + ": " + "error !");
    }

    public int newLabel() {
	return ++label;
   }

    public void emitLabel(int i) throws IOException {
        fos.write(("\nL" + i + ":\n").getBytes());
    }

    public void gen(String s, int b, int a) throws IOException {
        Expr e = expr.gen();
        Temp t = new Temp();
        
        emit(e.opToString() + "  " + e.expr1ToString() + " , " + e.expr2ToString());
	emit("\r\nmov  " + s + " , " +  t.toString() );
	//fos.write(("" + expr.getResult()).getBytes());
    }

    void emit(String s) throws IOException {
        //System.out.println("\t" + s);
    	fos.write(s.getBytes());
    }
}
