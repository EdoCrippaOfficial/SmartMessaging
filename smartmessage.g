grammar smartmessage;

program : (message | priorita | invia)+;

priorita  : 'Priorità ' NUM ;

invia : 'Invia'

message : new_mess TESTO+ duepunti titolo corpo opzioni;
opzioni : (cc)? (img)?;
titolo : 'Titolo ' TESTO;
corpo : 'Corpo ' TESTO;
cc : 'CC'
img : 'Img ' TESTO;

new_mess : 'Messaggio a';
duepunti : ':';


TESTO
    :  '"' (~('\\'|'"') )* '"'
    ;

NUM
    :  ('0'..'9') 
    ;

WS  :   ( ' '           
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;
