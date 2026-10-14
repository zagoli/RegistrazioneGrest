<#import "annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<#include "../struct/header.html.ftl">
<div class="container mt-3 content">
    <div class="shadow py-2 pl-3 pr-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong>Id Animatore</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.id?c}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Nome</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.nome?capitalize}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cognome</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.cognome?capitalize}</p>
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
                <strong>Codice Fiscale</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.codiceFiscale}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Presenza giornaliera</strong>
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
        <#if animatore.NTessera??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero tessera Circolo NOI</strong>
                </div>
                <div class="col-sm">
                    <p>${animatore.NTessera}</p>
                </div>
            </div>
        </#if>
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

        <@ui.presenzaSettimanale calendari=calendari/>
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
    <@ui.informazioniRegistrato registrato=animatore.registrato/>

    <#-- Annotazioni segreteria -->
    <@note.pannelloInfoDettaglio annotazioni=annotazioni/>
</div>
<#include "../struct/footer.html.ftl">
