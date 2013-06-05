package symbol;

import inter.*;

public class Mul extends Token {
    public Mul() {
        super(Type.MUL);
    }

    public int cal(Expr a, Expr b) {
        return a.getResult() * b.getResult();
    }

    public String toString() {
        return "*";
    }
}
