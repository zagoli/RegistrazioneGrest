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
        <h4 class="text-center font-weight-bold"> Dichiarazioni dei genitori </h4>
        <p class="font-weight-normal">I sottoscritti genitori dichiarano:</p>
        <ul>
            <li> <input type="checkbox"/> di autorizzare il/la figlio/a a tornare a casa al termine del Grest da solo;</li>
            <li> <input type="checkbox"/> di <u>NON</u> autorizzare il/la figlio/a a tornare a casa dal Grest negli orari stabiliti da solo; deve aspettare una delle seguenti persone da noi incaricate, indicate nella sezione "Accompagnatori e Contatti telefonici Urgenze" della piattaforma </li> 
            <div class="small">
            <li>che il/la proprio/a figlio/a gode di buona salute e che pertanto pu&ograve; affrontare le attivit&agrave; ludico-sportive previste; in caso contrario, si impegnano a chiederne l'esonero al momento dell'iscrizione (sezione richieste o proposte nella scheda di iscrizione ragazzo);</li>
            <li>di essere consapevoli che la responsabilit&agrave; della Parrocchia nei confronti dei ragazzi e la relativa copertura 	assicurativa inizia e termina negli orari indicati; non c'&egrave; copertura per gli spostamenti da e verso l'abitazione;</li>
            <li>di autorizzare i responsabili del Grest e i loro collaboratori ad assumere le iniziative che riterranno necessarie per garantire la sicurezza dei partecipanti e la buona riuscita delle attivit&agrave; del Grest;</li>
            <li>di non ritenere gli organizzatori del Grest responsabili per eventuali furti di biciclette o di oggetti di qualsiasi valore; i proprietari dovranno pertanto provvedere ad eventuali sistemi di protezione o di custodia;</li>
            <li>di essere informati, ai sensi e per gli effetti di cui all'art. 13 del D.Lgs 196/2003, che i dati personali raccolti saranno trattati, anche con strumenti informatici, esclusivamente nell'ambito dell'attivit&agrave; del Grest di Balconi e concedono la liberatoria alle riprese video/fotografiche esclusivamente nell'ambito dell'attivit&agrave; del Grest di Balconi.</li>
            </div>
        </ul>
        <div class='col-sm'>
            <input class="form-check-input" type="checkbox" id='priv' checked/><label for='priv' class="form-check-label"> Acconsento al trattamento dei dati personali, alla raccolta e all'utilizzo di materiale fotografico/video in conformit&agrave; a quanto riportato nell'informativa sulla privacy reperibile al link: grest.parrocchiadibalconi.it/RegistrazioneGrest/privacy.html</label>
        </div>
        <p class="text-center font-weight-light font-italic mb-4"> Firme di entrambi i genitori </p>
        <hr class="mb-5 border-bottom border-dark"/>
    </div>
</div>
<script>window.print();</script>
<#include "../struct/footer.html.ftl">
