<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare al parroco </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <@ui.identityRows person=animatore capitalize=false/>
        <@ui.displayRow label="Codice fiscale"><p>${animatore.codiceFiscale}</p></@ui.displayRow>
        <@ui.presenzaRow presenza=animatore.presenza label="Presenza giornaliera al Grest"/>
        <@ui.displayRow label="Laboratorio"><p>${animatore.laboratorio.descrizione}</p></@ui.displayRow>
        <@ui.displayRow label="Preferenza fascia d'età ragazzi"><p>
                    <@ui.fasciaEtaDescription fascia=animatore.fasciaEtaRagazzi/>
                </p></@ui.displayRow>
        <@ui.parrocchiaCircoloRows person=animatore/>
        <@ui.tesseraRow person=animatore/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <@ui.displayRow label="E-mail"><p>${animatore.mail}</p></@ui.displayRow>
        <@ui.displayRow label="Cellulare"><p>${animatore.cellulare}</p></@ui.displayRow>
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
</@ui.page>