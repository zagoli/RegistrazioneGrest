<#import "annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container mt-3 content">
    <div class="shadow py-2 pl-3 pr-3 bg-white">
        <@ui.displayRow label="Id Animatore"><p>${animatore.id?c}</p></@ui.displayRow>
        <@ui.identityRows person=animatore capitalize=true/>
        <@ui.displayRow label="Codice Fiscale"><p>${animatore.codiceFiscale}</p></@ui.displayRow>
        <@ui.presenzaRow presenza=animatore.presenza label="Presenza giornaliera"/>
        <@ui.displayRow label="Cellulare personale"><p>${animatore.cellulare}</p></@ui.displayRow>
        <@ui.displayRow label="Fascia età ragazzi"><p>
                    <@ui.fasciaEtaDescription fascia=animatore.fasciaEtaRagazzi/>
                </p></@ui.displayRow>
        <@ui.displayRow label="E-mail"><p>${animatore.mail}</p></@ui.displayRow>
        <@ui.displayRow label="Laboratorio"><p <#if animatore.responsabileLaboratorio>class="mark"</#if>>${animatore.laboratorio.descrizione}</p></@ui.displayRow>
        <@ui.parrocchiaCircoloRows person=animatore/>
        <@ui.tesseraRow person=animatore/>
        <#if animatore.squadra.id!=0>
            <@ui.displayRow label="Squadra"><p <#if animatore.responsabileSquadra>class="mark"</#if>>${animatore.squadra.nome}</p></@ui.displayRow>
        </#if>

        <@ui.presenzaSettimanale calendari=calendari/>
    </div>

    <@ui.detailContacts registrato=animatore.registrato contatti=(cu)!false accompagnatori=(accompagnatori)!false/>

    <#-- Annotazioni segreteria -->
    <@note.pannelloInfoDettaglio annotazioni=annotazioni/>
</div>
</@ui.page>
