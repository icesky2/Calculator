package inter;

class Temp extends Expr {
    static int count = 0;
    int id;
    char reg = 'a' - 1;

    Temp() {
        super(null, null, null, null);
        count++;
        id = count;
	reg += id;
    }

    public String toString() {
        return "e" + reg + "x";
    }
}
