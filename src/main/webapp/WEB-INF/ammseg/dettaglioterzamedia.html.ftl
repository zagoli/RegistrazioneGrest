<#import "annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container mt-3 content">
    <div class="shadow py-2 pl-3 pr-3 bg-white">
        <@ui.displayRow label="Id Terza media"><p>${terzamedia.id?c}</p></@ui.displayRow>
        <@ui.identityRows person=terzamedia capitalize=true/>
        <@ui.presenzaRow presenza=terzamedia.presenza label="Presenza giornaliera"/>
        <@ui.displayRow label="Laboratorio"><p>${terzamedia.laboratorio.descrizione}</p></@ui.displayRow>
        <@ui.parrocchiaCircoloRows person=terzamedia/>
        <@ui.schoolRows person=terzamedia label="Sezione" value=terzamedia.sezione/>
        <@ui.tesseraRow person=terzamedia/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <@ui.displayRow label="E-mail"><p>${terzamedia.mail}</p></@ui.displayRow>
        <#if terzamedia.cellulare??>
            <@ui.displayRow label="Numero di cellulare"><p>${terzamedia.cellulare}</p></@ui.displayRow>
        </#if>
        <@ui.noteRows person=terzamedia lowerCase=true/>
        <#if terzamedia.squadra.id!=0>
            <@ui.displayRow label="Squadra"><p>${terzamedia.squadra.nome}</p></@ui.displayRow>
        </#if>
        <@ui.yesNoRow label="Sa nuotare" value=terzamedia.saNuotare/>
        <@ui.yesNoRow label="Ha partecipato alla festa del passaggio a Balconi" value=terzamedia.festaPassaggio/>
        <#if pagamento??>
            <@ui.paymentDetails pagamento=pagamento/>
        </#if>
    </div>

    <@ui.detailContacts registrato=terzamedia.registrato contatti=(cu)!false accompagnatori=(accompagnatori)!false attgen=attgen/>

    <#-- Annotazioni segreteria -->
    <@note.pannelloInfoDettaglio annotazioni=annotazioni/>
</div>
</@ui.page>
