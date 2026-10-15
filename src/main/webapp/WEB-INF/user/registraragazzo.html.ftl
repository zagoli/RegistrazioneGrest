<#import "../struct/iscrizione.html.ftl" as iscrizione>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Iscrizione al Grest di Balconi</h5>
            </div>
            <form action="/RegistrazioneGrest/App/RegistraRagazzo" method="POST" data-parsley-validate="" id="mainForm">

                <h3 class="text-center font-weight-bold text-uppercase"> 1 - dati anagrafici </h3>

                <@iscrizione.anagraficaIscritto tipo="ragazzo"/>

                <@iscrizione.parrocchiaIscritto parrocchie=parrocchie/>

                <@iscrizione.tesseramentoIscritto circoli=circoli/>

                <@iscrizione.scuolaIscritto tipo="ragazzo" scuole=scuole/>

                <h3 class="text-center font-weight-bold text-uppercase"> 2 - partecipazione al grest nel periodo
                    segnato </h3>

                <@iscrizione.presenzaGiornaliera/>

                <@iscrizione.partecipazioneGrest tipo="ragazzo" calendari=calendari/>

                <h3 class="text-center font-weight-bold text-uppercase"> 3 - scelta del laboratorio per i ragazzi
                    iscritti
                    al grest </h3>
                <p>Durante il Grest scelgo di svolgere la seguente attività</p>

                <@iscrizione.laboratorioIscritto laboratori=laboratori/>

                <p>
                    In relazione al numero di ragazzi, ai laboratori scelti e al numero di  animatori iscritti,
                    non siamo certi di poter assicurare le preferenze espresse.
                    Gli eventuali cambiamenti saranno discussi insieme.
                </p>

                <h3 class="text-center font-weight-bold text-uppercase"> 4 - Allergie o intolleranze alimentari o
                    terapie in corso </h3>

                <div class="form-group">
                    <label for="noteAlimentari" class="sr-only">Allergie o intolleranze alimentari o terapie in
                        corso</label>
                    <textarea class="form-control" rows="5" id="noteAlimentari" name="noteAlimentari"
                              placeholder="specificare SOLO allergie certificate (non cibi che non piacciono) ed eventuali farmaci da assumere durante il Grest. Specificare anche gli eventuali orari di assunzione."></textarea>
                </div>

                <h3 class="text-center font-weight-bold text-uppercase"> 5 - Eventuali richieste o proposte </h3>

                <div class="form-group">
                    <label for="richieste" class="sr-only">Eventuali richieste o proposte</label>
                    <textarea class="form-control" rows="5" id="richieste" name="richieste"
                              placeholder="specificare SOLO se presenti. Si ricorda che, se si richiede di essere in squadra con qualche altro ragazzo, i responsabili del Grest cercheranno di equilibrare le squadre anche in base alle richieste qui espresse. Qualsiasi altra richiesta di cambio squadra durante il corso del Grest potrebbe quindi non essere soddisfatta. Si richiede pertanto di ponderare con attenzione la vostra scelta e di non richiedere cambiamenti, se non gravemente necessari."></textarea>
                </div>

                <h3 class="text-center font-weight-bold text-uppercase"> 6 - Richieste e dichiarazioni dei
                    genitori </h3>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="entrataAnticipata" name="entrataAnticipata">
                    <label class="form-check-label" for="entrataAnticipata">Si richiede, per gravi motivi, di anticipare
                        l'entrata alle ore 8.30 e si versa la quota prevista. </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="mensa" name="mensa">
                    <label class="form-check-label" for="mensa"> Si richiede, per necessità particolari, in caso
                        entrambi i genitori lavorino, di usufruire del servizio mensa, versando la quota di iscrizione
                        prevista. </label>
                </div>

                <br/>
                <p class="font-weight-normal">I sottoscritti genitori dichiarano:</p>
                <ul>
                    <li>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="fratelloIscritto"
                                   name="fratelloIscritto" data-ha-altro-ragazzo="${haAltroRagazzoIscritto?c}"
                                   onclick="toggleAvvisoFratelloIscritto(this);">
                            <label class="form-check-label" for="fratelloIscritto"> Che il/la proprio/a figlio/a <b>ha già uno o più</b> fratelli/sorelle iscritti al grest.</label>
                        </div>
                        <div id="avvisoFratelloIscritto" class="alert alert-warning mt-2 d-none" role="alert">
                            Attenzione: non abbiamo trovato altri ragazzi già registrati tramite il tuo profilo. Controlla di aver selezionato l'opzione corretta.
                        </div>
                    </li>
                    <li><input type="checkbox"/> di autorizzare il/la figlio/a a tornare a casa al termine del Grest
                        da solo;
                    </li>
                    <li><input type="checkbox"/> di <u>NON</u> autorizzare il/la figlio/a a tornare a casa dal Grest
                        negli orari stabiliti da solo; deve aspettare una delle seguenti persone incaricate,
                        indicate nella sezione "Accompagnatori e Contatti telefonici Urgenze" di questo sito
                    </li>
                    <li>che il/la proprio/a figlio/a gode di buona salute e che pertanto può affrontare le
                        attività ludico-sportive previste; in caso contrario, si impegnano a chiederne
                        l'esonero al momento della conferma dell'iscrizione;
                    </li>
                    <li>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="saNuotare" name="saNuotare">
                            <label class="form-check-label" for="saNuotare"> Che il/la proprio/a figlio/a sa
                                nuotare.</label>
                        </div>
                    </li>
                    <li>di essere consapevoli che la responsabilità della Parrocchia e del Circolo Noi di Balconi nei
                        confronti dei
                        ragazzi e la relativa copertura assicurativa inizia e termina negli orari indicati; non c'è
                        copertura per gli spostamenti da e verso l'abitazione;
                    </li>
                    <li>di autorizzare i responsabili del Grest e i loro collaboratori ad assumere le iniziative che
                        riterranno necessarie per garantire la sicurezza dei partecipanti e la buona riuscita delle
                        attività del Grest;
                    </li>
                    <li>di non ritenere gli organizzatori del Grest responsabili per eventuali furti di biciclette o
                        di oggetti di qualsiasi valore; i proprietari dovranno pertanto provvedere ad eventuali
                        sistemi di protezione o di custodia;
                    </li>
                    <li>di essere informati, ai sensi e per gli effetti di cui all'art. 13 del D.Lgs 196/2003, che i
                        dati personali raccolti saranno trattati, anche con strumenti informatici, esclusivamente
                        nell'ambito dell'attività del Grest di Balconi e concedono la liberatoria alle
                        riprese video/fotografiche esclusivamente nell'ambito dell'attività del Grest di
                        Balconi.
                    </li>
                </ul>

                <br/>

                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="checkprivacy" required/>
                    <label class="form-check-label" for="checkprivacy"> acconsento al trattamento dei dati personali,
                        alla raccolta e all'utilizzo di materiale fotografico/video in conformità a quanto riportato
                        nell'informativa sulla privacy reperibile al link <a href="../privacy.html">informativa sulla
                            privacy</a></label>
                </div>

                <div class="pt-3 form-group" id="codiceForm">
                    <#if !ISCRRAG>
                        <#include "sbloccoiscrizione.html.ftl">
                    <#else>
                        <div id="divbtnreg" class="row">
                            <input id="btnreg" class="form-control btn btn-primary" type="submit"
                                   value="Invia l'iscrizione"/>
                        </div>
                    </#if>
                </div>

            </form>
        </div>
    </div>
</div>
<script>
    function toggleAvvisoFratelloIscritto(checkbox) {
        const avviso = document.getElementById("avvisoFratelloIscritto");
        const haAltroRagazzo = checkbox.getAttribute("data-ha-altro-ragazzo") === "true";
        if (checkbox.checked && !haAltroRagazzo) {
            avviso.classList.remove("d-none");
        } else {
            avviso.classList.add("d-none");
        }
    }
</script>
<script src="../js/ajaxsbloccaiscr.js"></script>
<#include "../struct/footer.html.ftl">