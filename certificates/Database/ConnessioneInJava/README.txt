I file che servono a Java sono solo keystore e truststore.

Il file client-keystore.p12 è stato ricavato a partire da client-cert e client-key e serve per generare il keystore da dare a Java.

Il file truststore contiene solo il file ca-cert.pem.
Il file keystore contiene client-cert.pem e client-key.pem.

La guida per la configurazione di una connessione SSL con MYSQL su java è al link:
https://www.querypie.com/blog/mysql-ssl-connection-using-jdbc/