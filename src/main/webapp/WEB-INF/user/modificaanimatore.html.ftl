<#import "../struct/iscrizione.html.ftl" as iscrizione>
<#import "../ammseg/annotazioni.html.ftl" as note>
<#import "../struct/macros.html.ftl" as ui>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <h5 class="title text-center">Modifica l'iscrizione come animatore</h5>
            <#if INVALIDMAIL??>
                <@ui.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaAnimatore" method="POST" data-parsley-validate>

                <input type="hidden" value="${animatore.id?c}" name="id"/>

                <@iscrizione.anagraficaIscritto tipo="animatore" modifica=true iscritto=animatore dataNascita=dataNascita/>

                <div class="form-group">
                    <label for="codiceFiscale"> Codice fiscale </label>
                    <input class="form-control" type="text" required id="codiceFiscale" name="codiceFiscale"
                           data-parsley-length="[16, 16]" value="${animatore.codiceFiscale}">
                </div>

                <@iscrizione.presenzaGiornaliera tipo="animatore" selezionata=animatore.presenza/>

                <@iscrizione.partecipazioneGrest tipo="animatore" calendari=calendari modifica=true/>

                <@iscrizione.laboratorioIscritto tipo="animatore" modifica=true iscritto=animatore laboratori=laboratori/>

                <@iscrizione.parrocchiaIscritto parrocchie=parrocchie selezionata=animatore.parrocchia.id/>

                <@iscrizione.tesseramentoIscritto tipo="animatore" modifica=true iscritto=animatore circoli=circoli/>

                <div class="form-group">
                    <label for="cellulare"> Numero di cellulare </label>
                    <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]"
                           required id="cellulare" name="cellulare" placeholder="Cellulare personale"
                           value="${animatore.cellulare}">
                </div>

                <@iscrizione.fasciaEtaAnimatore selezionata=animatore.fasciaEtaRagazzi/>

                <div class="form-group">
                    <label for="mail"> E-Mail personale </label>
                    <input class="form-control" type="email" required id="mail" name="mail" placeholder="E-Mail"
                           value="${animatore.mail}">
                </div>

                <div class="pt-3 form-group">
                    <input class="form-control btn btn-primary" type="submit" value="Modifica l'iscrizione">
                </div>

            </form>

            <#-- Annotazioni segreteria -->
            <#if tipoUt < 3>
                <hr class="mt-4"/>
                <@note.modificaAnnotazioni tipoIscritto="an" id=animatore.id annotazioni=annotazioni/>
            </#if>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
