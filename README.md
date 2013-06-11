### Installation

    javac -d bin main/Cal.java

### Modify Execute it !

    java -cp bin main/Cal input.exp -o output.asm

    input.exp means the file of your logic expression

    output.asm means the file of your change result of your logic expression    

    Example:

	 input.exp:20+8+7+42*5+4=$

	 output.asm:
		t1 = 20 + 8	// t1 = 28
		t2 = t1 + 7	// t2 = 35
		t3 = 42 * 5	// t3 = 210
		t4 = t2 + t3	// t4 = 245
		t5 = t4 + 4	// t5 = 249

### Installation

    javac -d bin main/Cal.java

### Execute it !

    java -cp bin main/Cal

Type:

    1+

and pressing Enter produce:

    cal: 1

and type:

    2+

and pressing Enter produce:

    cal: 3

and type:

    3=

and pressing Enter produce:

    6

and type:

    $

and pressing Enter produce:

    	t1 = 1 + 2
    	t2 = t1 + 3
