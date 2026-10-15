<#import "annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<#include "../struct/header.html.ftl">
<div class="container mt-3 content">
    <div class="shadow py-2 pl-3 pr-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong>Id Terza media</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.id?c}</p>
            </div>
        </div>
        <@ui.anagraficaIscritto iscritto=terzamedia capitalize=true/>
        <@ui.presenzaIscritto iscritto=terzamedia/>
        <@ui.laboratorioIscritto iscritto=terzamedia/>
        <@ui.appartenenzaIscritto iscritto=terzamedia/>
        <div class="row">
            <div class="col-sm">
                <strong>Scuola</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.scuola.descrizione+" - "+terzamedia.scuola.grado}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Sezione</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.sezione}</p>
            </div>
        </div>
        <@ui.tesseraIscritto iscritto=terzamedia/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <div class="row">
            <div class="col-sm">
                <strong>E-mail</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.mail}</p>
            </div>
        </div>
        <#if terzamedia.cellulare??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero di cellulare</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.cellulare}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.richieste??>
            <div class="row">
                <div class="col-sm">
                    <strong>Richieste</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.richieste?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.noteAlimentari??>
            <div class="row">
                <div class="col-sm">
                    <strong>Note Alimentari</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.noteAlimentari?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.squadra.id!=0>
            <div class="row">
                <div class="col-sm">
                    <strong>Squadra</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.squadra.nome}</p>
                </div>
            </div>
        </#if>
        <div class="row">
            <div class="col-sm">
                <strong>Sa nuotare</strong>
            </div>
            <div class="col-sm">
                <p><#if terzamedia.saNuotare>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Ha partecipato alla festa del passaggio a Balconi</strong>
            </div>
            <div class="col-sm">
                <p><#if terzamedia.festaPassaggio>Sì<#else>No</#if></p>
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
        <@ui.contattiUrgenze contatti=cu/>
    </#if>

    <#--accompagnatori-->
    <#if accompagnatori??>
        <@ui.accompagnatoriAutorizzati accompagnatori=accompagnatori/>
    </#if>

    <#--info registrato-->
    <@ui.informazioniRegistrato registrato=terzamedia.registrato attgen=attgen/>

    <#-- Annotazioni segreteria -->
    <@note.pannelloInfoDettaglio annotazioni=annotazioni/>
</div>
<#include "../struct/footer.html.ftl">
