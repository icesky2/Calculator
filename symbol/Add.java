package symbol;

import inter.*;

public class Add extends Token {
    public Add() {
        super(Type.ADD);
    }

    public int cal(Expr a, Expr b) {
        return a.getResult() + b.getResult();
    }

    public String toString() {
        return "add";
    }
}
