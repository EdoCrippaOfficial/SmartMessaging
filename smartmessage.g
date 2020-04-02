grammar smartmessage;

program : (message | priorita)+;

priorita  : 'Priorità ' NUM ;

message : new_mess TESTO+ duepunti titolo corpo opzioni;
opzioni : (ore)?;
titolo : 'Titolo ' TESTO;
corpo : 'Corpo ' TESTO;
ore : 'Ore ' ORA;

new_mess : 'Messaggio a';
duepunti : ':';


TESTO
    :  '"' (~('\\'|'"') )* '"'
    ;
NUM
    :  ('0'..'9') 
    ;
ORA 
    :  ('0'..'9')('0'..'9') '.' ('0'..'9')('0'..'9') 
    ;
WS  :   ( ' '           
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;
