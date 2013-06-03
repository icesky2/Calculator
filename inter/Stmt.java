package inter;

import java.io.FileOutputStream;
import java.io.IOException;

public class Stmt {
    private Expr expr;
    FileOutputStream fos = null;

    public Stmt(Expr e, FileOutputStream fos) {
        expr = e;
        this.fos = fos;
    }

    public void gen() throws IOException {
        Expr e = expr.gen();
        Temp t = new Temp();
        emit(t.toString() + " = " + e.toString());
    }

    void emit(String s) throws IOException {
        //System.out.println("\t" + s);
    	fos.write(("\t" + s + "\n").getBytes());
    }
}
