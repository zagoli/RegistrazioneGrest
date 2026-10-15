<#import "../struct/macros.html.ftl" as ui>
<#include "../struct/header.html.ftl">
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare al parroco </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <@ui.anagraficaIscritto iscritto=animatore/>
        <div class="row">
            <div class="col-sm">
                <strong>Codice fiscale</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.codiceFiscale}</p>
            </div>
        </div>
        <@ui.presenzaIscritto iscritto=animatore animatore=true/>
        <@ui.laboratorioIscritto iscritto=animatore/>
        <div class="row">
            <div class="col-sm">
                <strong>Preferenza fascia d'età ragazzi</strong>
            </div>
            <div class="col-sm">
                <p>
                    <#if animatore.fasciaEtaRagazzi == "PIC">
                        Piccoli
                    <#elseif animatore.fasciaEtaRagazzi == "GRA">
                        Grandi
                    <#elseif animatore.fasciaEtaRagazzi == "TER">
                        3 Media
                    </#if>
                </p>
            </div>
        </div>
        <@ui.appartenenzaIscritto iscritto=animatore/>
        <@ui.tesseraIscritto iscritto=animatore/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <div class="row">
            <div class="col-sm">
                <strong>E-mail</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.mail}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cellulare</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.cellulare}</p>
            </div>
        </div>
        <strong> Sono inoltre disponibile a:</strong>
        <input type="checkbox"/><label class="ml-1 mr-1">servizio multimediale e amplificazione</label>
        <input type="checkbox"/><label class="ml-1 mr-1">servizio di manutenzione</label>

        <div class='col-sm'>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id='priv'/>
                <label for='priv' class="form-check-label">
                    L'animatore maggiorenne dichiara / I genitori dell'animatore minorenne dichiarano di acconsentire
                    alle dichiarazioni presentate in fase di
                    registrazione, e che le informazioni inserite in fase di registrazione sono veritiere.
                </label>
            </div>
        </div>
    </div>

    <div class="shadow mt-5 pt-2 pl-3 pr-3">
        <p class="text-center font-weight-light font-italic mt-4 mb-4"> Firma di chi richiede di fare l'animatore </p>
        <hr class="pb-2 border-dark"/>
        <p class="text-center font-weight-light font-italic mb-4"> Firme di entrambi i genitori (per i minorenni) </p>
        <hr class="pb-2 border-dark"/>
    </div>
</div>
<script>window.print();</script>
<#include "../struct/footer.html.ftl">