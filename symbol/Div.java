package symbol;

import inter.*;

public class Div extends Token {
    public Div() {
        super(Type.DIV);
    }

    public int cal(Expr a, Expr b) {
        return a.getResult() / b.getResult();
    }

    public String toString() {
        return "div";
    }
}
