<#include "../struct/header.html.ftl">
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare alla segreteria del grest al momento dell'iscrizione </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <div class="row">
            <div class="col-sm">
                <strong>Nome</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.nome}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cognome</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.cognome}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Data di nascita</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.dataNascita?string["dd/MM/yyyy"]}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Presenza giornaliera</strong>
            </div>
            <div class="col-sm">
                <p>
                    <#if ragazzo.presenza = "M"> 
                    Mattina
                    <#elseif ragazzo.presenza = "P"> 
                    Pomeriggio
                    <#elseif ragazzo.presenza = "C"> 
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
                <p>${ragazzo.laboratorio.descrizione}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Parrocchia</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.parrocchia.nome+" - "+ragazzo.parrocchia.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Circolo NOI</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.circolo.nome+" - "+ragazzo.circolo.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Scuola</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.scuola.descrizione+" - "+ragazzo.scuola.grado}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Classe e sezione</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.classe+ragazzo.sezione}</p>
            </div>
        </div>
        <#if ragazzo.nTessera??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero tessera Circolo NOI</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.nTessera}</p>
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
    
        <#if ragazzo.richieste??>
            <div class="row">
                <div class="col-sm">
                    <strong>Richieste</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.richieste}</p>
                </div>
            </div>
        </#if>
        <#if ragazzo.noteAlimentari??>
            <div class="row">
                <div class="col-sm">
                    <strong>Note Alimentari</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.noteAlimentari}</p>
                </div>
            </div>
        </#if>
        <div class="row">
            <div class="col-sm">
                <strong>Entrata anticipata</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.entrataAnticipata>S&igrave;<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Usufruisce della mensa</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.mensa>S&igrave;<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Sa nuotare</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.saNuotare>S&igrave;<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Ha un'altro fratello/sorella iscritto/a</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.fratelloIscritto>S&igrave;<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Quota d'iscrizione</strong> <br>
            </div>
            <div class="col-sm">
                <strong>${quotaIscrizione}&euro;</strong>
            </div>
        </div>
        <hr>
        <strong> Attivit&agrave; genitore </strong>
        <#list attgen as attgen>
        <div class="row">
            <div class="col-sm">
                <p>${attgen.descrizione}</p>
            </div>
        </div>
        </#list>
    </div>
    <div class="mt-3 shadow pt-2 pl-3 pr-3">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id='priv'/>
            <label for='priv' class="form-check-label">
                I sottoscritti genitori acconsentono alle dichiarazioni presentate in fase di
                registrazione, e dichiarano altres&igrave; che le informazioni inserite in fase di registrazione sono
                veritiere.
            </label>
        </div>
        <p class="text-center font-weight-light font-italic mb-4"> Firme di entrambi i genitori </p>
        <hr class="pb-2 border-dark"/>
    </div>
</div>
<script>window.print();</script>
<#include "../struct/footer.html.ftl">
