<#include "../struct/header.html.ftl">
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare a Don Lorenzo </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <div class="row">
            <div class="col-sm">
                <strong>Nome</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.nome}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cognome</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.cognome}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Data di nascita</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.dataNascita?string["dd/MM/yyyy"]}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Codice fiscale</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.codiceFiscale}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Presenza giornaliera al Grest</strong>
            </div>
            <div class="col-sm">
                <p>
                    <#if animatore.presenza == "M">
                        Mattina
                    <#elseif animatore.presenza == "P">
                        Pomeriggio
                    <#elseif animatore.presenza == "C">
                        Completo
                    </#if>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Laboratorio</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.laboratorio.descrizione}</p>
            </div>
        </div>
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
        <div class="row">
            <div class="col-sm">
                <strong>Parrocchia</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.parrocchia.nome+" - "+animatore.parrocchia.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Circolo NOI</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.circolo.nome+" - "+animatore.circolo.luogo}</p>
            </div>
        </div>
        <#if animatore.nTessera??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero tessera Circolo NOI</strong>
                </div>
                <div class="col-sm">
                    <p>${animatore.nTessera}</p>
                </div>
            </div>
        </#if>

        <div class="row">
            <div class="col-sm">
                <strong>Presenza settimanale</strong>
            </div>
        </div>
        <#list calendari as cal>
            <div class="row">
                <div class="col-sm"></div>
                <div class="col-sm">
                    <p>dal ${cal.daQuando+" al "+cal.aQuando}</p>
                </div>
            </div>
        </#list>

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