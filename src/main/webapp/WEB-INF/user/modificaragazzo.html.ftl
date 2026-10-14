<#import "../struct/macros.html.ftl" as ui>
<#import "../ammseg/annotazioni.html.ftl" as note>
<@ui.page navbar=true>
<@ui.formPanel title="Modifica l'iscrizione" containerClass="container mt-4 content" inlineTitle=false>
            <form action="/RegistrazioneGrest/App/ModificaRagazzo" method="POST" data-parsley-validate="">

                <input type="hidden" value="${ragazzo.id?c}" name="id">

                <@ui.identityFields cognome="${ragazzo.cognome}" nome="${ragazzo.nome}" dataNascita="${dataNascita}"/>

                <@ui.presenzaField selected=ragazzo.presenza label="Presenza giornaliera" completo="Mattina e pomeriggio"/>

                <@ui.partecipazioneField calendari=calendari label="Partecipazione" editing=true lineBreak=true quoteTarget="ragazzi" popupHeight=900/>

                <@ui.selectField id="laboratorio" label="Laboratorio">
                    <@ui.laboratorioOptions options=laboratori selected=ragazzo.laboratorio.id/>
                </@ui.selectField>

                <@ui.selectField id="parrocchia" label="Parrocchia di appartenenza">
                    <@ui.parrocchiaOptions options=parrocchie selected=ragazzo.parrocchia.id/>
                </@ui.selectField>

                <@ui.selectField id="circolo" label="Circolo di tesseramento">
                    <@ui.circoloOptions options=circoli selected=ragazzo.circolo.id/>
                </@ui.selectField>
                <@ui.tesseraField label="Numero tessera Circolo Noi" year=true yearIndent=32 help="Inserire il numero di tessera del Circolo Noi se non si è iscritti al Circolo Noi di Balconi." value=(ragazzo.NTessera)![]/>


                <@ui.selectField id="scuola" label="Scuola">
                    <@ui.scuolaOptions options=scuole selected=ragazzo.scuola.id/>
                </@ui.selectField>

                <@ui.selectField id="classe" label="Classe">
                    <@ui.classeOptions selected=ragazzo.classe/>
                </@ui.selectField>

                <@ui.inputField id="sezione" label="Sezione" required=true placeholder="Classe" length="[1, 5]" value="${ragazzo.sezione}"/>

                <@ui.textareaField id="richieste" label="Eventuali richieste o proposte:" value=(ragazzo.richieste)!"" placeholder="Si ricorda che, se si richiede di essere in squadra con qualche altro ragazzo, i responsabili cercheranno di equilibrare le squadre anche in base a questa richiesta. Qualsiasi altra richiesta di cambio squadra durante il corso del Grest potrebbe quindi non essere soddisfatta. Si richiede pertanto di ponderare con attenzione la vostra scelta, e di non richiedere cambiamenti, se non gravemente necessari."/>

                <@ui.textareaField id="noteAlimentari" label="Allergie o intolleranze alimentari o terapie in corso:" value=(ragazzo.noteAlimentari)!""/>

                <@ui.checkboxField id="entrataAnticipata" label="Usufruisce dell'entrata anticipata?" checked=ragazzo.entrataAnticipata labelClass="form-check-label"/>

                <@ui.checkboxField id="mensa" label="Usufruisce della mensa?" checked=ragazzo.mensa labelClass="form-check-label"/>

                <@ui.checkboxField id="saNuotare" label="Sa nuotare?" checked=ragazzo.saNuotare labelClass="form-check-label"/>

                <@ui.checkboxField id="fratelloIscritto" label="Ha già un fratello/sorella iscritto/a al grest? <b class=\"text-danger\">Da selezionare solo dall'iscrizione del secondo ragazzo in poi!</b>" checked=ragazzo.fratelloIscritto labelClass="form-check-label"/>

                <br/>

                <@ui.submitButton label="Modifica l'iscrizione"/>
            </form>

            <#-- Annotazioni segreteria -->
            <#if tipoUt < 3>
                <hr class="mt-4"/>
                <@note.modificaAnnotazioni tipoIscritto="rag" id=ragazzo.id annotazioni=annotazioni/>
            </#if>
</@ui.formPanel>
</@ui.page>