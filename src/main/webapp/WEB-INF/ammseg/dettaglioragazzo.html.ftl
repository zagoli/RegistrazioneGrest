<#import "annotazioni.html.ftl" as note>
<#import "../struct/dettaglioIscritto.html.ftl" as dettaglioIscritto>
<#import "../struct/riferimentiIscritto.html.ftl" as riferimentiIscritto>
<#include "../struct/header.html.ftl">
<div class="container mt-3 content">
    <div class="shadow py-2 pl-3 pr-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong>Id Ragazzo</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.id?c}</p>
            </div>
        </div>
        <@dettaglioIscritto.anagraficaIscritto iscritto=ragazzo capitalize=true/>
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
                    <p>${ragazzo.richieste?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if ragazzo.noteAlimentari??>
            <div class="row">
                <div class="col-sm">
                    <strong>Note Alimentari</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.noteAlimentari?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if ragazzo.squadra.id!=0>
            <div class="row">
                <div class="col-sm">
                    <strong>Squadra</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.squadra.nome}</p>
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
                <span><#if ragazzo.fratelloIscritto>Sì<#else>No</#if>
                    <#if altroFratelloNonTrovato>
                        <img src="../img/octicons/alert.svg" alt="attenzione"
                             title="Non sono stati trovati altri ragazzi iscritti dallo stesso utente.">
                    </#if>
                    </span>
            </div>
        </div>
        <#if pagamento??>
            <div class="row">
                <div class="col-sm">
                    <strong>Quota pagamento</strong>
                </div>
                <div class="col-sm">
                    <p>${pagamento.quota}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong>Evaso da</strong>
                </div>
                <div class="col-sm">
                    <p>${pagamento.registrato.nome+" "+pagamento.registrato.cognome}</p>
                </div>
            </div>
        </#if>
    </div>

    <#--contatti urgenze-->
    <#if cu??>
        <@riferimentiIscritto.contattiUrgenze contatti=cu/>
    </#if>

    <#--accompagnatori-->
    <#if accompagnatori??>
        <@riferimentiIscritto.accompagnatoriAutorizzati accompagnatori=accompagnatori/>
    </#if>

    <#--info registrato-->
    <@riferimentiIscritto.informazioniRegistrato registrato=ragazzo.registrato attgen=attgen/>

    <#-- Annotazioni segreteria -->
    <@note.pannelloInfoDettaglio annotazioni=annotazioni/>
</div>
<#include "../struct/footer.html.ftl">
