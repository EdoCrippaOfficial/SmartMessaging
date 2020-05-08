# SmartMessaging

Attraverso l’utilizzo di Antlr è stato creato un progetto in grado di inviare messaggi dal pc all’app tramite l’utilizzo del database di firebase. I messaggi inviati possono essere spediti a più destinatari, possono contenere url ad immagini e hanno una priorità, che modificherà il colore della notifica a seconda dell’importanzza. Oltre a questo si potrà vedere l’utilizzo dell’app da parte di un determinato utente attraverso la creazione di statistiche. 

## Server

L'applicazione è utilizzabile attraverso un *.jar* eseguibile ([releases](https://github.com/EdoCrippaOfficial/smartmessaging/releases/latest))

### Comandi
```
usage: java -jar SmartMessaging.jar <-f|-s>
 -f,--file <file>       input file/path to file
 -h,--help              prints this help
 -s,--string <string>   input string
```
##### Esempi
```
java -jar SmartMessaging.jar -f /absolute/path/to/file
```
```
java -jar SmartMessaging.jar -f relative/path/to/file
```
```
java -jar SmartMessaging.jar -f input.sms
```
```
java -jar SmartMessaging.jar -s "Stats Exit"
```

## Android
L'applicazione è installabile attraverso il file *.apk* ([releases](https://github.com/EdoCrippaOfficial/smartmessaging/releases/latest))
> **Nota:** attivare l'opzione installa app sconosciute sul dispositivo
