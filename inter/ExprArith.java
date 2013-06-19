package inter;

import java.io.FileOutputStream;
import java.io.IOException;

import symbol.*;

public class ExprArith extends Expr {
    
    public ExprArith(Token op, Expr expr1, Expr expr2, FileOutputStream fos) {
        super(op, expr1, expr2, fos);

        setResult(op.cal(expr1, expr2));
    }

    Expr reduce() {
        Expr e = gen();
        Temp t = new Temp();

        try {
	     emit(e.opToString() + "  " + t.toString() + "  " + e.exprToString()); 
        } catch (IOException e1) {
	     e1.printStackTrace();
	}
        return t;
    }
}
