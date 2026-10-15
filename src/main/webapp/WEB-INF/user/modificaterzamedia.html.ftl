<#import "../struct/iscrizione.html.ftl" as iscrizione>
<#import "../ammseg/annotazioni.html.ftl" as note>
<#import "../struct/avvisiValidazione.html.ftl" as avvisiValidazione>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow py-2 px-3 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Modifica terza media</h5>
            </div>
            <#if INVALIDMAIL??>
                <@avvisiValidazione.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaTerzamedia" method="POST" data-parsley-validate="">

                <input type="hidden" value="${terzamedia.id?c}" name="id"></input>

                <@iscrizione.anagraficaIscritto tipo="terzamedia" modifica=true iscritto=terzamedia dataNascita=dataNascita/>

                <@iscrizione.presenzaGiornaliera selezionata=terzamedia.presenza/>

                <@iscrizione.partecipazioneGrest tipo="terzamedia" calendari=calendari modifica=true/>

                <@iscrizione.laboratorioIscritto modifica=true iscritto=terzamedia laboratori=laboratori/>

                <@iscrizione.parrocchiaIscritto parrocchie=parrocchie selezionata=terzamedia.parrocchia.id/>

                <@iscrizione.tesseramentoIscritto modifica=true iscritto=terzamedia circoli=circoli/>

                <@iscrizione.scuolaIscritto tipo="terzamedia" modifica=true iscritto=terzamedia scuole=scuole/>

                <div class="form-group">
                    <label for="cellulare"> Eventuale numero di cellulare </label>
                    <input class="form-control" type="text" id="cellulare" name="cellulare"
                           data-parsley-length="[10, 10]" placeholder="cellulare"
                           <#if terzamedia.cellulare??>value="${terzamedia.cellulare}"</#if>>
                </div>

                <div class="form-group">
                    <label for="mail"> Indirizzo E-Mail del ragazzo o della famiglia </label>
                    <input class="form-control" type="email" required id="mail" name="mail" placeholder="E-Mail"
                           value="${terzamedia.mail}">
                </div>

                <div class="form-group">
                    <label for="richieste">Eventuali richieste o proposte:</label>
                    <textarea class="form-control" rows="5" id="richieste"
                              name="richieste"><#if terzamedia.richieste??>${terzamedia.richieste}</#if></textarea>
                </div>

                <div class="form-group">
                    <label for="noteAlimentari">Allergie o intolleranze alimentari o terapie in corso:</label>
                    <textarea class="form-control" rows="5" id="noteAlimentari"
                              name="noteAlimentari"><#if terzamedia.noteAlimentari??>${terzamedia.noteAlimentari}</#if></textarea>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="saNuotare" name="saNuotare"
                           <#if terzamedia.saNuotare>checked</#if>>
                    <label class="form-check-label" for="saNuotare"> Sa nuotare? </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="festaPassaggio" name="festaPassaggio"
                           <#if terzamedia.festaPassaggio>checked</#if>>
                    <label class="form-check-label" for="festaPassaggio"> Si dichiara che il/la proprio/a figlio/a ha
                        partecipato alla festa del passaggio a Balconi.</label>
                </div>

                <br/>

                <div class="pt-3 form-group">
                    <input class="form-control btn btn-primary" type="submit" value="Modifica">
                </div>
            </form>

            <#-- Annotazioni segreteria -->
            <#if tipoUt < 3>
                <hr class="mt-4"/>
                <@note.modificaAnnotazioni tipoIscritto="ter" id=terzamedia.id annotazioni=annotazioni/>
            </#if>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">