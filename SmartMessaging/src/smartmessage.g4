grammar smartmessage;

program : (message | priorita | invia)+ exit;

priorita  : 'Priorità' NUM ;

invia : 'Invia';

message : new_mess destinatario+ duepunti titolo corpo opzioni;
opzioni : (cc)? (img)? (format)?;
titolo : 'Titolo' TESTO;
destinatario: TESTO;
corpo : 'Corpo' TESTO;
cc : 'CC';
img : 'Img' TESTO;
format : 'Formattazione' TESTO;

new_mess : 'Messaggio a';
duepunti : ':';

exit : EXIT;


TESTO
    :  '"' (~('\\'|'"') )* '"'
    ;

NUM
    :  [0-9]
    ;

EXIT : 'Exit';

LINE_COMMENT
    : '//' (~('\n'|'\r'))* -> skip;

WS: [ \n\t\r]+ -> skip;

