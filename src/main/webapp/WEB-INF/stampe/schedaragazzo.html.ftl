<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="container content" onclick="window.print();">
    <div class="mt-3">
        <p class="font-weight-bold text-uppercase text-center"> da consegnare alla segreteria del grest al momento
            dell'iscrizione </p>
    </div>
    <div class="shadow pt-2 pl-3 pr-3">
        <@ui.identityRows person=ragazzo capitalize=false/>
        <@ui.presenzaRow presenza=ragazzo.presenza label="Presenza giornaliera"/>
        <@ui.displayRow label="Laboratorio"><p>${ragazzo.laboratorio.descrizione}</p></@ui.displayRow>
        <@ui.parrocchiaCircoloRows person=ragazzo/>
        <@ui.schoolRows person=ragazzo label="Classe e sezione" value=ragazzo.classe+ragazzo.sezione/>
        <@ui.tesseraRow person=ragazzo/>

        <@ui.presenzaSettimanale calendari=calendari/>

        <@ui.noteRows person=ragazzo lowerCase=false/>
        <@ui.yesNoRow label="Entrata anticipata" value=ragazzo.entrataAnticipata/>
        <@ui.yesNoRow label="Usufruisce della mensa" value=ragazzo.mensa/>
        <@ui.yesNoRow label="Sa nuotare" value=ragazzo.saNuotare/>
        <@ui.yesNoRow label="Ha un'altro fratello/sorella iscritto/a" value=ragazzo.fratelloIscritto/>
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
