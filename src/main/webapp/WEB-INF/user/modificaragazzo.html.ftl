<#import "../struct/iscrizione.html.ftl" as iscrizione>
<#import "../ammseg/annotazioni.html.ftl" as note>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow py-2 px-3 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Modifica l'iscrizione</h5>
            </div>
            <form action="/RegistrazioneGrest/App/ModificaRagazzo" method="POST" data-parsley-validate="">

                <input type="hidden" value="${ragazzo.id?c}" name="id">

                <@iscrizione.anagraficaIscritto tipo="ragazzo" modifica=true iscritto=ragazzo dataNascita=dataNascita/>

                <@iscrizione.presenzaGiornaliera selezionata=ragazzo.presenza/>

                <@iscrizione.partecipazioneGrest tipo="ragazzo" calendari=calendari modifica=true/>

                <@iscrizione.laboratorioIscritto modifica=true iscritto=ragazzo laboratori=laboratori/>

                <@iscrizione.parrocchiaIscritto parrocchie=parrocchie selezionata=ragazzo.parrocchia.id/>

                <@iscrizione.tesseramentoIscritto modifica=true iscritto=ragazzo circoli=circoli/>


                <@iscrizione.scuolaIscritto tipo="ragazzo" modifica=true iscritto=ragazzo scuole=scuole/>

                <div class="form-group">
                    <label for="richieste">Eventuali richieste o proposte:</label>
                    <textarea class="form-control" rows="5" id="richieste" name="richieste"
                              placeholder="Si ricorda che, se si richiede di essere in squadra con qualche altro ragazzo, i responsabili cercheranno di equilibrare le squadre anche in base a questa richiesta. Qualsiasi altra richiesta di cambio squadra durante il corso del Grest potrebbe quindi non essere soddisfatta. Si richiede pertanto di ponderare con attenzione la vostra scelta, e di non richiedere cambiamenti, se non gravemente necessari."><#if ragazzo.richieste??>${ragazzo.richieste}</#if></textarea>
                </div>

                <div class="form-group">
                    <label for="noteAlimentari">Allergie o intolleranze alimentari o terapie in corso:</label>
                    <textarea class="form-control" rows="5" id="noteAlimentari"
                              name="noteAlimentari"><#if ragazzo.noteAlimentari??>${ragazzo.noteAlimentari}</#if></textarea>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="entrataAnticipata" name="entrataAnticipata"
                           <#if ragazzo.entrataAnticipata>checked</#if>>
                    <label class="form-check-label" for="entrataAnticipata"> Usufruisce dell'entrata
                        anticipata? </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="mensa" name="mensa"
                           <#if ragazzo.mensa>checked</#if>>
                    <label class="form-check-label" for="mensa"> Usufruisce della mensa? </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="saNuotare" name="saNuotare"
                           <#if ragazzo.saNuotare>checked</#if>>
                    <label class="form-check-label" for="saNuotare"> Sa nuotare? </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="fratelloIscritto" name="fratelloIscritto"
                           <#if ragazzo.fratelloIscritto>checked</#if>>
                    <label class="form-check-label" for="fratelloIscritto"> Ha già un fratello/sorella
                        iscritto/a al grest? <b class="text-danger">Da selezionare solo dall'iscrizione del
                            secondo ragazzo in poi!</b></label>
                </div>

                <br/>

                <div class="pt-3 form-group">
                    <input class="form-control btn btn-primary" type="submit" value="Modifica l'iscrizione">
                </div>
            </form>

            <#-- Annotazioni segreteria -->
            <#if tipoUt < 3>
                <hr class="mt-4"/>
                <@note.modificaAnnotazioni tipoIscritto="rag" id=ragazzo.id annotazioni=annotazioni/>
            </#if>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">