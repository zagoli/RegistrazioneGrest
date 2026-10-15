<#import "annotazioni.html.ftl" as note>
<#import "../struct/dettaglioIscritto.html.ftl" as dettaglioIscritto>
<#import "../struct/riferimentiIscritto.html.ftl" as riferimentiIscritto>
<#include "../struct/header.html.ftl">
<div class="container mt-3 content">
    <div class="shadow py-2 px-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong>Id Animatore</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.id?c}</p>
            </div>
        </div>
        <@dettaglioIscritto.anagraficaIscritto iscritto=animatore/>
        <div class="row">
            <div class="col-sm">
                <strong>Codice Fiscale</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.codiceFiscale}</p>
            </div>
        </div>
        <@dettaglioIscritto.presenzaIscritto iscritto=animatore/>
        <div class="row">
            <div class="col-sm">
                <strong>Cellulare personale</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.cellulare}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Fascia età ragazzi</strong>
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
                <strong>E-mail</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.mail}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Laboratorio</strong>
            </div>
            <div class="col-sm">
                <p <#if animatore.responsabileLaboratorio>class="mark"</#if>>${animatore.laboratorio.descrizione}</p>
            </div>
        </div>
        <@dettaglioIscritto.appartenenzaIscritto iscritto=animatore/>
        <@dettaglioIscritto.tesseraIscritto iscritto=animatore/>
        <#if animatore.squadra.id!=0>
            <div class="row">
                <div class="col-sm">
                    <strong>Squadra</strong>
                </div>
                <div class="col-sm">
                    <p <#if animatore.responsabileSquadra>class="mark"</#if>>${animatore.squadra.nome}</p>
                </div>
            </div>
        </#if>

        <@dettaglioIscritto.presenzaSettimanale calendari=calendari/>
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
    <@riferimentiIscritto.informazioniRegistrato registrato=animatore.registrato/>

    <#-- Annotazioni segreteria -->
    <@note.pannelloInfoDettaglio annotazioni=annotazioni/>
</div>
<#include "../struct/footer.html.ftl">
