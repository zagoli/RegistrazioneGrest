<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.formPanel title="Iscrizione come animatore del Grest di Balconi" containerClass="container mt-4 content" inlineTitle=false>
            <#if INVALIDMAIL??>
                <@ui.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/RegistraAnimatore" method="POST" data-parsley-validate id="mainForm">

                <@ui.identityFields cognome="${registrato.cognome}" nome="${registrato.nome}"/>

                <@ui.inputField id="codiceFiscale" label="Codice fiscale" required=true placeholder="Codice Fiscale" length="[16, 16]"/>

                <@ui.presenzaField selected="C" label="Presenza giornaliera al Grest" completo="Completo"/>

                <@ui.partecipazioneField calendari=calendari label="Periodo di partecipazione" editing=false lineBreak=true quoteTarget="" popupHeight=600/>

                <@ui.selectField id="laboratorio" label="Selezionare un laboratorio">
                    <@ui.laboratorioOptions options=laboratori/>
                </@ui.selectField>

                <@ui.selectField id="parrocchia" label="Parrocchia di appartenenza">
                    <@ui.parrocchiaOptions options=parrocchie/>
                </@ui.selectField>

                <@ui.selectField id="circolo" label="Tesseramento Circolo NOI" year=true yearIndent=32>
                    <@ui.circoloOptions options=circoli/>
                </@ui.selectField>

                <@ui.tesseraField label=" Numero tessera Circolo NOI " help="Inserire il numero di tessera del Circolo NOI se non si è iscritti al Circolo NOI di Balconi."/>

                <@ui.inputField id="cellulare" label="Numero di cellulare" type="" required=true placeholder="Cellulare personale" length="[10, 10]" value=(registrato.telefono)![] attributes={"data-parsley-type": "digits"}/>

                <@ui.fasciaEtaField selected="PIC"/>

                <@ui.inputField id="mail" label="E-Mail personale" type="email" required=true placeholder="E-Mail" value="${registrato.mail}"/>

                <p><b>L'animatore maggiorenne dichiara / I genitori dell'animatore minorenne dichiarano:</b></p>
                <@ui.checkboxField id="checkprivacy" label="di acconsentire al trattamento dei dati personali, alla raccolta e all'utilizzo di materiale fotografico/video in conformità a quanto riportato nell'informativa sulla privacy reperibile al link <a href=\"../privacy.html\">informativa sulla privacy</a>" name=[] required=true labelClass="form-check-label"/>
                <@ui.checkboxField id="c1" label="che l'animatore gode di buona salute e che pertanto può affrontare le attività ludico-sportive previste; in caso contrario, se ne chiede l'esonero al momento dell'iscrizione; e che non soffre di allergie o intolleranze alimentari" name=[] required=true labelClass="form-check-label"/>
                <@ui.checkboxField id="c2" label="di essere consapevole/i che la responsabilità della Parrocchia e del circolo Noi nei confronti degli animatori e la relativa copertura assicurativa inizia e termina negli orari indicati; non c’è copertura per gli spostamenti da e verso l'abitazione" name=[] required=true labelClass="form-check-label"/>
                <@ui.checkboxField id="c3" label="di autorizzare i responsabili del Grest e i loro collaboratori ad assumere le iniziative che riterranno necessarie per garantire la sicurezza dei partecipanti e la buona riuscita delle attività del Grest" name=[] required=true labelClass="form-check-label"/>
                <@ui.checkboxField id="c4" label="di non ritenere gli organizzatori del Grest responsabili per eventuali furti di motorini, di biciclette o di oggetti di qualsiasi valore; i proprietari dovranno pertanto provvedere ad eventuali sistemi di protezione o di custodia" name=[] required=true labelClass="form-check-label"/>

                <@ui.registrationSubmit open=ISCRAN/>

            </form>
</@ui.formPanel>
<script src="../js/ajaxsbloccaiscr.js"></script>
</@ui.page>
