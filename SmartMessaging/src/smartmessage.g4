grammar smartmessage;

program : (message | priorita | invia)+;

priorita  : 'Priorità ' NUM ;

invia : 'Invia';

message : new_mess TESTO+ duepunti titolo corpo opzioni;
opzioni : (cc)? (img)?;
titolo : 'Titolo ' TESTO;
corpo : 'Corpo ' TESTO;
cc : 'CC';
img : 'Img ' TESTO;

new_mess : 'Messaggio a';
duepunti : ':';


TESTO
    :  '"' (~('\\'|'"') )* '"'
    ;

NUM
    :  [0-9]
    ;

LINE_COMMENT
    : '//' (~('\n'|'\r'))* -> skip;

WS: [ \n\t\r]+ -> skip;

