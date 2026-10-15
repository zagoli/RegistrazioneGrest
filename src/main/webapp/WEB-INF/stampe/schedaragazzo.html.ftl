<#import "../struct/dettaglioIscritto.html.ftl" as dettaglioIscritto>
<#import "../struct/riferimentiIscritto.html.ftl" as riferimentiIscritto>
<#include "../struct/header.html.ftl">
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare alla segreteria del grest al momento
            dell'iscrizione </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <@dettaglioIscritto.anagraficaIscritto iscritto=ragazzo/>
        <@dettaglioIscritto.presenzaIscritto iscritto=ragazzo/>
        <@dettaglioIscritto.laboratorioIscritto iscritto=ragazzo/>
        <@dettaglioIscritto.appartenenzaIscritto iscritto=ragazzo/>
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
        <@dettaglioIscritto.tesseraIscritto iscritto=ragazzo/>

        <@dettaglioIscritto.presenzaSettimanale calendari=calendari/>

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
                <p><#if ragazzo.entrataAnticipata>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Usufruisce della mensa</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.mensa>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Sa nuotare</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.saNuotare>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Ha un'altro fratello/sorella iscritto/a</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.fratelloIscritto>Sì<#else>No</#if></p>
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
        <@riferimentiIscritto.attivitaGenitore attivita=attgen/>
    </div>
    <div class="mt-3 shadow pt-2 pl-3 pr-3">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id='priv'/>
            <label for='priv' class="form-check-label">
                I sottoscritti genitori acconsentono alle dichiarazioni presentate in fase di
                registrazione, e dichiarano altresì che le informazioni inserite in fase di registrazione sono
                veritiere.
            </label>
        </div>
        <p class="text-center font-weight-light font-italic mb-4"> Firme di entrambi i genitori </p>
        <hr class="pb-2 border-dark"/>
    </div>
</div>
<script>window.print();</script>
<#include "../struct/footer.html.ftl">
