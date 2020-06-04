<p align="center">
  <img src="Android/SmartMessaging/app/src/main/res/drawable/logo.png" title="SmartMessaging" alt="SmartMessaging">
</p>


# SmartMessaging

[![Build Status](http://img.shields.io/travis/badges/badgerbadgerbadger.svg?)]()
[![Dependencies](https://david-dm.org/dwyl/esta/status.svg)]()
[![License](http://img.shields.io/:license-mit-blue.svg?)](http://badges.mit-license.org) 

Sistema in grado di inviare messaggi da un pc ad un’app Android.  
I messaggi inviati possono essere spediti a più destinatari, possono contenere url ad immagini e hanno una priorità, che modificherà il colore della notifica a seconda dell’importanza.  
Oltre a questo si potrà vedere l’utilizzo dell’app da parte di un determinato utente attraverso la creazione di statistiche. 

<div style="text-align: center">
    <a target="_blank" rel="noopener noreferrer"  href="https://asciinema.org/a/0FQb89ajFCUZ8kqzyXL4mkV7L"><img src="https://asciinema.org/a/0FQb89ajFCUZ8kqzyXL4mkV7L.svg"></a>
</div>
<div style="text-align: center">
    <br> qua ci mettiamo due screen dell'app
</div>


## Documentazione
Consultare la [wiki](https://github.com/EdoCrippaOfficial/SmartMessaging/wiki) ufficiale

## Come usare il sistema

### Server pc

L'applicazione è utilizzabile attraverso un *.jar* eseguibile ([releases](https://github.com/EdoCrippaOfficial/smartmessaging/releases/latest))

#### Comandi
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

### App Android
L'applicazione è installabile attraverso il file *.apk* ([releases](https://github.com/EdoCrippaOfficial/smartmessaging/releases/latest))
> **Nota:** attivare l'opzione installa app sconosciute sul dispositivo
