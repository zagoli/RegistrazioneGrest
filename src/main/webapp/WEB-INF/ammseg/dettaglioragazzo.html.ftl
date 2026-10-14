<#import "annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container mt-3 content">
    <div class="shadow py-2 pl-3 pr-3 bg-white">
        <@ui.displayRow label="Id Ragazzo"><p>${ragazzo.id?c}</p></@ui.displayRow>
        <@ui.identityRows person=ragazzo capitalize=true/>
        <@ui.presenzaRow presenza=ragazzo.presenza label="Presenza giornaliera"/>
        <@ui.displayRow label="Laboratorio"><p>${ragazzo.laboratorio.descrizione}</p></@ui.displayRow>
        <@ui.parrocchiaCircoloRows person=ragazzo/>
        <@ui.schoolRows person=ragazzo label="Classe e sezione" value=ragazzo.classe+ragazzo.sezione/>
        <@ui.tesseraRow person=ragazzo/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <@ui.noteRows person=ragazzo lowerCase=true/>
        <#if ragazzo.squadra.id!=0>
            <@ui.displayRow label="Squadra"><p>${ragazzo.squadra.nome}</p></@ui.displayRow>
        </#if>
        <@ui.yesNoRow label="Entrata anticipata" value=ragazzo.entrataAnticipata/>
        <@ui.yesNoRow label="Usufruisce della mensa" value=ragazzo.mensa/>
        <@ui.yesNoRow label="Sa nuotare" value=ragazzo.saNuotare/>
        <@ui.displayRow label="Ha un'altro fratello/sorella iscritto/a"><span><#if ragazzo.fratelloIscritto>Sì<#else>No</#if>
                    <#if altroFratelloNonTrovato>
                        <img src="../img/octicons/alert.svg" alt="attenzione"
                             title="Non sono stati trovati altri ragazzi iscritti dallo stesso utente.">
                    </#if>
                    </span></@ui.displayRow>
        <#if pagamento??>
            <@ui.paymentDetails pagamento=pagamento/>
        </#if>
    </div>

    <@ui.detailContacts registrato=ragazzo.registrato contatti=(cu)!false accompagnatori=(accompagnatori)!false attgen=attgen/>

    <#-- Annotazioni segreteria -->
    <@note.pannelloInfoDettaglio annotazioni=annotazioni/>
</div>
</@ui.page>
