<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare alla segreteria del grest al momento
            dell'iscrizione </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <@ui.identityRows person=terzamedia capitalize=false/>
        <@ui.presenzaRow presenza=terzamedia.presenza label="Presenza giornaliera"/>
        <@ui.displayRow label="Laboratorio"><p>${terzamedia.laboratorio.descrizione}</p></@ui.displayRow>
        <@ui.parrocchiaCircoloRows person=terzamedia/>
        <@ui.schoolRows person=terzamedia label="Sezione" value=terzamedia.sezione/>
        <@ui.tesseraRow person=terzamedia/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <@ui.displayRow label="E-mail"><p>${terzamedia.mail}</p></@ui.displayRow>
        <#if terzamedia.cellulare??>
            <@ui.displayRow label="Cellulare"><p>${terzamedia.cellulare}</p></@ui.displayRow>
        </#if>
        <@ui.noteRows person=terzamedia lowerCase=false/>
        <@ui.yesNoRow label="Sa nuotare" value=terzamedia.saNuotare/>
        <@ui.yesNoRow label="Ha partecipato alla festa del passaggio a Balconi" value=terzamedia.festaPassaggio/>
        <div class="row">
            <div class="col-sm">
                <strong>Quota d'iscrizione</strong> <br>
            </div>
            <div class="col-sm">
                <strong>${quotaIscrizione}&euro;</strong>
            </div>
        </div>
        <@ui.attivitaGenitore activities=attgen/>
    </div>
    <@ui.parentSignature/>
</div>
<script>window.print();</script>
</@ui.page>
