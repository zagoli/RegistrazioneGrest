<#import "../ammseg/annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.formPanel title="Modifica terza media" containerClass="container mt-4 content" inlineTitle=false>
            <#if INVALIDMAIL??>
                <@ui.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaTerzamedia" method="POST" data-parsley-validate="">

                <input type="hidden" value="${terzamedia.id?c}" name="id"></input>

                <@ui.identityFields cognome="${terzamedia.cognome}" nome="${terzamedia.nome}" dataNascita="${dataNascita}"/>

                <@ui.presenzaField selected=terzamedia.presenza label="Presenza giornaliera" completo="Mattina e pomeriggio"/>

                <@ui.partecipazioneField calendari=calendari label="Partecipazione" editing=true lineBreak=true quoteTarget="terzamedia" popupHeight=600/>

                <@ui.selectField id="laboratorio" label="Laboratorio">
                    <@ui.laboratorioOptions options=laboratori selected=terzamedia.laboratorio.id/>
                </@ui.selectField>

                <@ui.selectField id="parrocchia" label="Parrocchia di appartenenza">
                    <@ui.parrocchiaOptions options=parrocchie selected=terzamedia.parrocchia.id/>
                </@ui.selectField>

                <@ui.selectField id="circolo" label="Circolo di tesseramento">
                    <@ui.circoloOptions options=circoli selected=terzamedia.circolo.id/>
                </@ui.selectField>

                <@ui.tesseraField label="Numero tessera Circolo Noi" year=true yearIndent=32 help="Inserire il numero di tessera del Circolo Noi se non si è iscritti al Circolo Noi di Balconi." value=(terzamedia.NTessera)![]/>

                <@ui.selectField id="scuola" label="Scuola">
                    <@ui.scuolaOptions options=scuole selected=terzamedia.scuola.id/>
                </@ui.selectField>

                <@ui.inputField id="sezione" label="Sezione" required=true placeholder="A" length="[1, 1]" value="${terzamedia.sezione}"/>

                <@ui.inputField id="cellulare" label="Eventuale numero di cellulare" placeholder="cellulare" length="[10, 10]" value=(terzamedia.cellulare)![]/>

                <@ui.inputField id="mail" label="Indirizzo E-Mail del ragazzo o della famiglia" type="email" required=true placeholder="E-Mail" value="${terzamedia.mail}"/>

                <@ui.textareaField id="richieste" label="Eventuali richieste o proposte:" value=(terzamedia.richieste)!""/>

                <@ui.textareaField id="noteAlimentari" label="Allergie o intolleranze alimentari o terapie in corso:" value=(terzamedia.noteAlimentari)!""/>

                <@ui.checkboxField id="saNuotare" label="Sa nuotare?" checked=terzamedia.saNuotare labelClass="form-check-label"/>

                <@ui.checkboxField id="festaPassaggio" label="Si dichiara che il/la proprio/a figlio/a ha partecipato alla festa del passaggio a Balconi." checked=terzamedia.festaPassaggio labelClass="form-check-label"/>

                <br/>

                <@ui.submitButton label="Modifica"/>
            </form>

            <#-- Annotazioni segreteria -->
            <#if tipoUt < 3>
                <hr class="mt-4"/>
                <@note.modificaAnnotazioni tipoIscritto="ter" id=terzamedia.id annotazioni=annotazioni/>
            </#if>
</@ui.formPanel>
</@ui.page>