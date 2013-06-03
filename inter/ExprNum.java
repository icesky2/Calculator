package inter;

import java.io.FileOutputStream;

import symbol.*;

public class ExprNum extends Expr {
    public ExprNum(Token op, FileOutputStream fos) {
        super(op, null, null, fos);

        setResult(op.cal(null, null));
    }

    public String toString() {
        return op.toString();
    }
}
