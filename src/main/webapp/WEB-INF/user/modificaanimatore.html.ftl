<#import "../ammseg/annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.formPanel title="Modifica l'iscrizione come animatore" containerClass="container mt-4 content" inlineTitle=true>
            <#if INVALIDMAIL??>
                <@ui.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaAnimatore" method="POST" data-parsley-validate>

                <input type="hidden" value="${animatore.id?c}" name="id"/>

                <@ui.identityFields nome="${animatore.nome}" cognome="${animatore.cognome}" dataNascita="${dataNascita}" nomeFirst=true/>

                <@ui.inputField id="codiceFiscale" label="Codice fiscale" required=true length="[16, 16]" value="${animatore.codiceFiscale}"/>

                <@ui.presenzaField selected=animatore.presenza label="Presenza giornaliera al Grest" completo="Completo"/>

                <@ui.partecipazioneField calendari=calendari label="Periodo di partecipazione" editing=true lineBreak=true quoteTarget="" popupHeight=600/>

                <@ui.selectField id="laboratorio" label="Laboratorio">
                    <@ui.laboratorioOptions options=laboratori selected=animatore.laboratorio.id/>
                </@ui.selectField>

                <@ui.selectField id="parrocchia" label="Parrocchia di appartenenza">
                    <@ui.parrocchiaOptions options=parrocchie selected=animatore.parrocchia.id/>
                </@ui.selectField>

                <@ui.selectField id="circolo" label="Circolo NOI di iscrizione" year=true yearIndent=32>
                    <@ui.circoloOptions options=circoli selected=animatore.circolo.id beforeClose=" "/>
                </@ui.selectField>

                <@ui.tesseraField label=" Numero tessera Circolo NOI " help="Inserire il numero di tessera del Circolo NOI se non si è iscritti al Circolo NOI di Balconi." value=(animatore.NTessera)![]/>

                <@ui.inputField id="cellulare" label="Numero di cellulare" type="" required=true placeholder="Cellulare personale" length="[10, 10]" value="${animatore.cellulare}" attributes={"data-parsley-type": "digits"}/>

                <@ui.fasciaEtaField selected=animatore.fasciaEtaRagazzi/>

                <@ui.inputField id="mail" label="E-Mail personale" type="email" required=true placeholder="E-Mail" value="${animatore.mail}"/>

                <@ui.submitButton label="Modifica l'iscrizione"/>

            </form>

            <#-- Annotazioni segreteria -->
            <#if tipoUt < 3>
                <hr class="mt-4"/>
                <@note.modificaAnnotazioni tipoIscritto="an" id=animatore.id annotazioni=annotazioni/>
            </#if>
</@ui.formPanel>
</@ui.page>
