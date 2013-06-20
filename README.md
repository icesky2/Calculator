### Installation

    javac -d bin main/Cal.java

### Modify Execute it !

    java -cp bin main/Cal input.exp -o output.asm

    input.exp means the file of your logic expression

    output.asm means the file of your change result of your logic expression    

    Example:

	 input.exp:testa=2*(3*(4-5)+6)==$

	 output.asm:
	L1:
	sub  eax  4 , 5
	mul  ebx  3 , eax
	add  ecx  ebx , 6
	mul  edx  2 , ecx
	sub  eex  4 , 5
	mul  efx  3 , eex
	add  egx  efx , 6
	mul  testa  edx , egx

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
