<#import "../struct/iscrizione.html.ftl" as iscrizione>
<#import "../struct/avvisiValidazione.html.ftl" as avvisiValidazione>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Iscrizione come animatore del Grest di Balconi</h5>
            </div>
            <#if INVALIDMAIL??>
                <@avvisiValidazione.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/RegistraAnimatore" method="POST" data-parsley-validate id="mainForm">

                <@iscrizione.anagraficaIscritto tipo="animatore" registrato=registrato/>

                <div class="form-group">
                    <label for="codiceFiscale"> Codice fiscale </label>
                    <input class="form-control" type="text" required placeholder="Codice Fiscale" id="codiceFiscale"
                           name="codiceFiscale" data-parsley-length="[16, 16]">
                </div>

                <@iscrizione.presenzaGiornaliera/>

                <@iscrizione.partecipazioneGrest tipo="animatore" calendari=calendari/>

                <@iscrizione.laboratorioIscritto laboratori=laboratori/>

                <@iscrizione.parrocchiaIscritto parrocchie=parrocchie/>

                <@iscrizione.tesseramentoIscritto circoli=circoli/>

                <div class="form-group">
                    <label for="cellulare"> Numero di cellulare </label>
                    <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]" required
                           id="cellulare" name="cellulare" placeholder="Cellulare personale"
                           value="${registrato.telefono!""}">
                </div>

                <@iscrizione.fasciaEtaAnimatore/>

                <div class="form-group">
                    <label for="mail"> E-Mail personale </label>
                    <input class="form-control" type="email" required id="mail" name="mail" placeholder="E-Mail"
                           value="${registrato.mail}">
                </div>

                <p><b>L'animatore maggiorenne dichiara / I genitori dell'animatore minorenne dichiarano:</b></p>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="checkprivacy" required/>
                    <label class="form-check-label" for="checkprivacy"> di acconsentire al trattamento dei dati
                        personali, alla raccolta e all'utilizzo di materiale fotografico/video in conformità a
                        quanto riportato nell'informativa sulla privacy reperibile al link <a href="../privacy.html">informativa
                            sulla privacy</a></label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c1" required/>
                    <label class="form-check-label" for="c1"> che l'animatore gode di buona salute e che pertanto può
                        affrontare le attività ludico-sportive previste; in caso contrario, se ne chiede l'esonero al
                        momento dell'iscrizione; e che non soffre di allergie o intolleranze alimentari</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c2" required/>
                    <label class="form-check-label" for="c2"> di essere consapevole/i che la responsabilità della
                        Parrocchia e del circolo Noi nei confronti degli animatori e la relativa copertura assicurativa
                        inizia e termina
                        negli orari indicati; non c’è copertura per gli spostamenti da e verso l'abitazione</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c3" required/>
                    <label class="form-check-label" for="c3"> di autorizzare i responsabili del Grest e i loro
                        collaboratori ad assumere le iniziative che riterranno necessarie per garantire la sicurezza dei
                        partecipanti e la buona riuscita delle attività del Grest</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c4" required/>
                    <label class="form-check-label" for="c4"> di non ritenere gli organizzatori del Grest responsabili
                        per eventuali furti di motorini, di biciclette o di oggetti di qualsiasi valore; i proprietari
                        dovranno pertanto provvedere ad eventuali sistemi di protezione o di custodia</label>
                </div>

                <div class="pt-3 form-group" id="codiceForm">
                    <#if !ISCRAN>
                        <#include "sbloccoiscrizione.html.ftl">
                    <#else>
                        <div id="divbtnreg" class="row">
                            <input id="btnreg" class="form-control btn btn-primary" type="submit"
                                   value="Invia l'iscrizione"/>
                        </div>
                    </#if>
                </div>

            </form>
        </div>
    </div>
</div>
<script src="../js/ajaxsbloccaiscr.js"></script>
<#include "../struct/footer.html.ftl">
