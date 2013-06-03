package inter;

class Temp extends Expr {
    static int count = 0;
    int id;

    Temp() {
        super(null, null, null, null);
        count++;
        id = count;
    }

    public String toString() {
        return "t" + id;
    }
}
