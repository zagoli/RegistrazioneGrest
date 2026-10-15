# Test manuali: Iscritto e refactoring DRY

Eseguire su un ambiente di test con database, non sui dati reali del Grest.
Usare account, iscritti, contatti e pagamenti dedicati alle prove. Le operazioni
di eliminazione sono distruttive per il record indicato.

## Preparazione

- Avere un amministratore, un segretario abilitato alle modifiche, un segretario
  di sola consultazione e due utenti ordinari diversi.
- Preparare parrocchie, circoli, scuole, calendari, squadre e laboratori,
  compreso almeno un laboratorio riservato.
- Annotare le quote configurate per settimane, mensa, fratelli, entrata
  anticipata e residenza fuori comune.
- Creare un ragazzo, un ragazzo di terza media e un animatore di prova, con
  settimane e campi facoltativi valorizzati. Usare solo recapiti sintetici
  o account di test controllati.

## Iscrizioni e modifica

| ID | Ruolo e percorso | Azioni | Risultato atteso |
|---|---|---|---|
| I01 | Utente, Home Utente -> Iscrivi un ragazzo | Compilare dati obbligatori; scegliere scuola, classe, sezione, parrocchia, circolo, laboratorio e settimane. Salvare e riaprire Modifica. | L'iscrizione compare nella propria dashboard; dati, selezioni e settimane coincidono con quelli salvati. Nessun laboratorio riservato e selezionabile per il ragazzo. |
| I02 | Utente, Home Utente -> Iscrivi terza media | Compilare dati, email, eventuale cellulare, festa del passaggio e settimane. Salvare e riaprire Modifica. | Dati precompilati corretti; email obbligatoria, cellulare facoltativo, sezione di un carattere. |
| I03 | Utente, Home Utente -> Iscrivi un animatore | Verificare la precompilazione dall'account; compilare data di nascita, codice fiscale, cellulare, email, fascia d'eta, laboratorio e settimane. | Iscrizione salvata; codice fiscale, fascia d'eta e selezioni conservati. Disponibili i laboratori previsti per gli animatori. |
| I04 | Utente, Modifica di ciascuno dei tre tipi | Cambiare nome, cognome, data di nascita, presenza giornaliera, parrocchia, circolo e laboratorio. Salvare, chiudere la pagina e riaprirla. | Tutti i cambiamenti persistono; gli altri valori non modificati restano invariati. |
| I05 | Utente, Modifica di ciascuno dei tre tipi | Cambiare le settimane: deselezionare una settimana e selezionarne un'altra, mantenendone almeno una. | Alla riapertura sono selezionate esattamente le settimane salvate, senza relazioni duplicate. |
| I06 | Utente, Registrazione/Modifica | Provare campi obbligatori vuoti, nome troppo corto, tessera di lunghezza errata, sezione troppo lunga e email non valida. | Restano attive le validazioni previste per la pagina. Un invio non valido non viene presentato come salvataggio riuscito. |
| I07 | Utente, Modifica ragazzo | Selezionare e poi deselezionare mensa, entrata anticipata, nuoto e fratello iscritto, salvando a ogni passaggio. | I booleani seguono le selezioni; deselezionare salva false. La nota/avvertenza sui fratelli mantiene il comportamento previsto. |
| I08 | Utente, Modifica terza media | Selezionare e poi deselezionare nuoto e festa del passaggio; salvare e riaprire. | I booleani seguono le selezioni e sono mostrati correttamente anche nel dettaglio. |
| I09 | Amministratore, impostazioni iscrizioni; utente, form iscrizione | Provare separatamente iscrizioni aperte e chiuse per i tre tipi. Con iscrizioni chiuse inserire un codice valido e uno non valido. Ripristinare le impostazioni iniziali. | Rimangono corretti pulsante di invio, richiesta del codice, feedback e sblocco AJAX. Il tipo di iscrizione non viene confuso con gli altri. |
| I10 | Utente, form ragazzo/terza media | Aprire il collegamento alle quote. | Si apre il prospetto del tipo corretto, con dimensioni/popup e importi previsti. |

## Cancellazione di tutti i campi facoltativi

Per ogni campo sotto: inserire un valore riconoscibile, salvare, riaprire e
verificare il valore; quindi svuotare, salvare, chiudere e riaprire la pagina.
Il valore precedente deve essere eliminato, non ricomparire. Verificare anche
il dettaglio, la stampa e l'esportazione quando espongono quel campo.

| ID | Ruolo e pagina | Campi da provare | Risultato atteso |
|---|---|---|---|
| F01 | Utente o segretario abilitato, Modifica ragazzo | Richieste, note alimentari/terapie, numero tessera NOI | Ogni campo svuotato rimane assente dopo la rilettura; gli altri campi non vengono cancellati. |
| F02 | Utente o segretario abilitato, Modifica terza media | Richieste, note alimentari/terapie, numero tessera NOI, cellulare facoltativo | Nessun vecchio valore ricompare. Email e gli altri dati obbligatori restano invariati. |
| F03 | Utente o segretario abilitato, Modifica animatore | Numero tessera NOI | La tessera viene cancellata; cellulare, email, codice fiscale e responsabilita non vengono alterati. |
| F04 | Utente, Accompagnatori e Contatti telefonici Urgenze -> Modifica contatto | Cellulare e fisso, prima uno alla volta e poi entrambi | I recapiti svuotati vengono eliminati; nome, cognome e relazione restano salvati. |
| F05 | Qualsiasi ruolo, Modifica Profilo | Telefono, localita, via e numero civico | Questi campi sono NOT NULL nello schema Registrato: sono obbligatori per tutti. Il browser impedisce l'invio vuoto; anche il server rifiuta valori assenti, vuoti o solo spazi, senza salvare null. |
| F06 | Tutti i form modificati che mostrano altri campi facoltativi | Ogni ulteriore campo facoltativo presente | Ripetere valorizzazione/salvataggio/cancellazione/rilettura. Nessun campo non presente nel form viene cancellato indirettamente. |
| F07 | Utente, Collaborazione genitori | Data/disponibilita testuale facoltativa della nuova collaborazione | Inserire una disponibilita senza testo facoltativo: il dato e assente, senza testo spurio nel riepilogo. Questa pagina inserisce una nuova disponibilita, non modifica un valore precedente. |

Se si dispone di un accesso DB di sola lettura, verificare anche l'assenza
effettivamente memorizzata, normalmente NULL per i campi testuali facoltativi.
Non e sufficiente vedere un input vuoto subito dopo il salvataggio.

## Liste, dettagli e permessi

| ID | Ruolo e percorso | Azioni | Risultato atteso |
|---|---|---|---|
| L01 | Segretario, elenco ragazzi | Cercare il ragazzo di prova; ordinare per cognome e nascita; controllare classe/sezione, mensa, nuoto, entrata, richieste, allergie e pagamento. | Colonne e dati corretti; date ordinate cronologicamente; ricerca e azioni funzionano. |
| L02 | Segretario, elenco terza media | Cercare e ordinare; controllare sezione, squadra, laboratorio, nuoto, passaggio, richieste, allergie e pagamento. | Nessuna colonna specifica dei ragazzi aggiunta; dati e azioni del tipo corretto. |
| L03 | Segretario, elenco animatori | Cercare e ordinare; controllare codice fiscale, cellulare, email e responsabilita. | Dati corretti; evidenziazione dei responsabili e badge squadra invariati. |
| L04 | Segretario, lente di dettaglio dai tre elenchi | Aprire ogni popup; controllare anagrafica, calendario, tessera, dati specifici, contatti e dati del registrato. | Popup e contenuto corretti per il tipo. I campi facoltativi eliminati non sono mostrati con il vecchio valore. |
| L05 | Segretario abilitato e amministratore, Modifica iscritto -> Annotazioni | Aggiungere una nota con piu righe e caratteri speciali; verificare dettaglio e cancellazione secondo il ruolo. | Testo e a capo corretti; diritti di eliminazione invariati; nessuna annotazione associata al tipo sbagliato. |
| L06 | Segretario di sola consultazione e utente ordinario | Provare le azioni consentite dal proprio ruolo; con il secondo utente tentare l'accesso ai record del primo tramite URL. | Autorizzazioni e divieti restano quelli previsti; nessuna estensione dei permessi dovuta al refactoring. |
| L07 | Utente, Home Utente; amministratore, dashboard | Controllare tessere degli iscritti, collegamenti e pannelli amministrativi; aprire le azioni dei tre tipi. | Ogni azione raggiunge il record e il flusso corretti; riepiloghi e visibilita dei pannelli invariati. |

## Assegnazioni e pagamenti

| ID | Ruolo e percorso | Azioni | Risultato atteso |
|---|---|---|---|
| A01 | Segretario abilitato, Squadre ragazzi/terza media | Assegnare una squadra, cambiarla, poi scegliere nessuna squadra. Salvare a ogni passaggio. | Assegnazione persistente; rimozione effettiva; badge e dettaglio aggiornati. |
| A02 | Segretario abilitato, Squadre animatori | Assegnare squadra e responsabilita; togliere la responsabilita; poi rimuovere la squadra. | Responsabilita e squadra persistono correttamente. Rimane il rifiuto di un responsabile senza squadra. |
| A03 | Segretario abilitato, Laboratori dei tre tipi | Cambiare laboratorio. Per animatori selezionare/deselezionare responsabile. | Selezioni persistenti; ragazzi limitati ai laboratori non riservati; responsabilita specifica degli animatori invariata. |
| P01 | Segretario abilitato, Gestisci pagamenti ragazzi | Controllare le quote per le settimane e per combinazioni mensa/fratelli/entrata/residenza. Registrare un pagamento di prova. | Quote coerenti con configurazione; importo e ordine arrivo corretti nella lista e nel dettaglio. |
| P02 | Segretario abilitato, Gestisci pagamenti terza media | Controllare settimane e supplemento fuori comune; registrare un pagamento di prova. | Formula specifica terza media invariata; nessuna applicazione delle opzioni mensa/fratelli. |
| P03 | Segretario abilitato, entrambi i pagamenti | Provare importo con virgola; duplicare pagamento o ordine arrivo gia usato; poi eliminare solo il pagamento di prova. | Parsing e messaggio di duplicato invariati; nessun secondo pagamento; eliminazione aggiorna la lista corretta. |
| P04 | Segretario, pagamenti senza iscritti e con iscritti non paganti | Aprire entrambi i prospetti su dati di test appropriati. | Stati vuoti e non paganti corretti, nessun errore dovuto a valori facoltativi assenti. |

## Account, stampe e altre pagine

| ID | Ruolo e percorso | Azioni | Risultato atteso |
|---|---|---|---|
| U01 | Nuovo utente di prova, Registrati; poi Modifica Profilo | Registrare account, accedere, modificare i dati; provare email non valida e validazioni password. | Campi e messaggi invariati; modifiche persistenti; password non alterata dalla modifica del profilo. |
| U02 | Utente, Accompagnatori e Contatti telefonici Urgenze | Inserire/modificare accompagnatore e contatto; cancellare solo record di prova. | Liste, azioni e collegamenti corretti; nessun record di un altro utente coinvolto. |
| U03 | Amministratore, gestione segretari e laboratori | Inserire/modificare record di prova e verificarne visibilita/permessi. Nel nuovo segretario compilare anche telefono, localita, via e civico. | I quattro campi account sono obbligatori e vengono salvati; permessi di modifica, password e azioni restano invariati. Eventuali eliminazioni limitate ai dati di prova. |
| S01 | Segretario, schede stampabili dei tre iscritti | Aprire le schede e anteprima di stampa; confrontare dati, settimane, testo, firme e quota. | Layout leggibile, sezioni specifiche conservate, nessun valore facoltativo eliminato ricompare. |
| S02 | Segretario, stampe elenchi sintetici, mensa, anticipo, presenze squadre/laboratori/animatori | Stampare con settimane, squadra e laboratorio diversi. | Filtri, ordine e colonne corretti; contatti e periodi corrispondono ai record. |
| S03 | Amministratore, esportazione massiva | Scaricare Excel e aprire i tre fogli. Controllare ordine colonne, date, settimane, importi e valori facoltativi cancellati. | Nomi e colonne invariati; importi numerici; valori cancellati assenti senza errori o testo spurio. |
| S04 | Utente, collaborazione genitori; segretario, elenco collaborazioni | Inserire e rimuovere una disponibilita di prova, verificare lista e report. | Form, riepilogo e collegamenti funzionano; disponibilita associata all'utente corretto. |
| S05 | Amministratore, statistiche; percorsi di errore 403/404 | Aprire statistiche e una rotta inesistente; provare un'azione non autorizzata con account di prova. | Contenuti e navigazione corretti; nessuna pagina di errore incompleta o macro non risolta. |

Eventuale test del reset password: usare esclusivamente un account controllato
e un server email di test. Verificare l'email e il successivo accesso; nessuna
email reale viene inviata dalla suite automatica.

## Esito da registrare

Per ogni ID annotare superato/non superato, ruolo, tipo di iscritto, dati di
prova e differenza osservata. In caso di errore includere il percorso e i
passaggi per riprodurlo, senza password o dati personali reali.

I test automatici verificano comportamento e rendering con fixture sintetiche;
queste prove manuali coprono anche l'integrazione con il database e il browser
del setup dell'utente.

## Template modificati da controllare

Tutti i percorsi sotto sono relativi a `src\main\webapp\WEB-INF`.
I nomi delle pagine riportati senza estensione terminano tutti in `.html.ftl`.
La revisione FreeMarker mantiene espliciti HTML, checkbox, input, intestazioni
di tabella, layout e script di pagina. Non ci sono macro generiche per queste
strutture.

Le sezioni applicative di visualizzazione sono divise per responsabilita:
`struct\dettaglioIscritto.html.ftl` (anagrafica, appartenenza e presenze),
`struct\riferimentiIscritto.html.ftl` (contatti, accompagnatori e registrato) e
`struct\avvisiValidazione.html.ftl` (avviso email).
Le pagine importano direttamente queste librerie; `struct\macros.html.ftl`
rimane soltanto un punto di ingresso compatibile, senza definizioni di macro.
`struct\iscrizione.html.ftl` condivide le sezioni applicative dei
sei form degli iscritti e i dati account tra registrazione e modifica.
Le due pagine di ogni coppia restano distinte: conservano consensi, submit,
sblocco e annotazioni propri di ciascun flusso. Sono invece uniformate,
su scelta dell'utente, le seguenti presentazioni:

| Sezione | Presentazione comune |
|---|---|
| Anagrafica iscritti | Nome prima di Cognome |
| Presenza | "Presenza giornaliera al Grest", opzione "Mattina e pomeriggio" |
| Settimane | "Periodo di partecipazione", senza br aggiuntivo dopo l'etichetta |
| Quote | Popup alto 900 per ragazzi e terza media; nessun link per animatori |
| Laboratorio | "Selezionare un laboratorio", visibile in tutti i form |
| Circolo e tessera | "Circolo di tesseramento NOI"; "Numero tessera Circolo NOI" con anno corrente |
| Aiuto tessera | "Inserire il numero di tessera del Circolo NOI se non si e tesserati al Circolo NOI di Balconi." (con accento nel template) |
| Opzioni circolo | Nome (luogo), spazio prima della parentesi aperta, nessuno prima della chiusa |
| Sezione scolastica | Etichetta "Sezione", placeholder "A" |
| Account | "Localita" con accento; validazione telefono [10, 10]; telefono, localita, via e civico obbligatori per tutti, anche nel nuovo segretario |

Restano specifiche le regole scolastiche (classe solo per ragazzi, sezione di
1 carattere per terza media e 1-5 per ragazzi), la precompilazione animatore
dall'account, i valori salvati in modifica e tutte le settimane selezionate in
registrazione. Nel server cambia soltanto la validazione dei quattro campi
account NOT NULL, che non sono piu trattati come facoltativi.

Rispetto al precedente refactoring, sono stati rivisti i 58 template di
pagina/frammento sotto elencati e la libreria di visualizzazione; si aggiunge
la libreria dei form. Nelle pagine accessorie viene semplicemente rimosso
il precedente livello di astrazione HTML, mantenendo l'output.

| Cartella | Pagine e frammenti modificati |
|---|---|
| `acccu` | `dashboardacccu`, `inserisciaccompagnatore`, `inseriscicu`, `modificaaccompagnatore`, `modificacu` |
| `ammseg` | `dashboardamministratore`, `dashboardsegretario`, `dashboardsegretariointernal`, `dettaglioanimatore`, `dettaglioragazzo`, `dettaglioterzamedia`, `gestiscilaboratori`, `gestiscipagamenti`, `gestiscipagamentiterzamedia`, `gestiscisegretari`, `labanimatori`, `labragazzi`, `labterzamedia`, `registrasegretario`, `squadreanimatori`, `squadreragazzi`, `squadreterzamedia`, `statistiche`, `visualizzaanimatori`, `visualizzaattgen`, `visualizzaragazzi`, `visualizzaterzamedia` |
| `err` | `403`, `404`, `errore`, `profilodisabilitato` |
| `stampe` | `anticipo`, `collaborazione`, `elesintan`, `elesintrag`, `elesintter`, `mensa`, `presgiornani`, `pressetlab`, `pressetsqu`, `schedaanimatore`, `schedaragazzo`, `schedaterzamedia` |
| `user` | `dashboardattgen`, `dashboardutente`, `login`, `modificaanimatore`, `modificapassword`, `modificaragazzo`, `modificaterzamedia`, `modificautente`, `quoteragazzi`, `quoteterzamedia`, `registraanimatore`, `registraragazzo`, `registraterzamedia`, `registrautente`, `resetpassword` |
| `struct` | `dettaglioIscritto.html.ftl`, `riferimentiIscritto.html.ftl`, `avvisiValidazione.html.ftl`, `iscrizione.html.ftl`; `macros.html.ftl` e soltanto un ingresso di compatibilita |

Non modificati: `ammseg\annotazioni.html.ftl`, i due template JSON, il
frammento `user\sbloccoiscrizione.html.ftl` e header/navbar/footer in `struct`.
