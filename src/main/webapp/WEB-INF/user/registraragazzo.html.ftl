<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.formPanel title="Iscrizione al Grest di Balconi" containerClass="container mt-4 content" inlineTitle=false>
            <form action="/RegistrazioneGrest/App/RegistraRagazzo" method="POST" data-parsley-validate="" id="mainForm">

                <h3 class="text-center font-weight-bold text-uppercase"> 1 - dati anagrafici </h3>

                <@ui.identityFields />

                <@ui.selectField id="parrocchia" label="Parrocchia di appartenenza">
                    <@ui.parrocchiaOptions options=parrocchie/>
                </@ui.selectField>

                <@ui.selectField id="circolo" label="Circolo di tesseramento">
                    <@ui.circoloOptions options=circoli/>
                </@ui.selectField>

                <@ui.tesseraField label="Numero tessera Circolo Noi" year=true yearIndent=32 help="Inserire il numero di tessera del Circolo Noi se non si è tesserati al Circolo Noi di Balconi."/>

                <@ui.selectField id="scuola" label="Scuola">
                    <@ui.scuolaOptions options=scuole/>
                </@ui.selectField>

                <@ui.selectField id="classe" label="Classe">
                    <@ui.classeOptions selected=""/>
                </@ui.selectField>

                <@ui.inputField id="sezione" label="Sezione" required=true placeholder="sezione" length="[1, 5]"/>

                <h3 class="text-center font-weight-bold text-uppercase"> 2 - partecipazione al grest nel periodo
                    segnato </h3>

                <@ui.presenzaField selected="C" label="Presenza giornaliera" completo="Mattina e pomeriggio"/>

                <@ui.partecipazioneField calendari=calendari label="Partecipazione" editing=false lineBreak=false quoteTarget="ragazzi" popupHeight=900/>

                <h3 class="text-center font-weight-bold text-uppercase"> 3 - scelta del laboratorio per i ragazzi
                    iscritti
                    al grest </h3>
                <p>Durante il Grest scelgo di svolgere la seguente attività</p>

                <@ui.selectField id="laboratorio" label="Laboratorio" labelClass="sr-only" attributes={"aria-describedby": "helpLaboratorio"}>
                    <@ui.laboratorioOptions options=laboratori/>
                </@ui.selectField>

                <p>
                    In relazione al numero di ragazzi, ai laboratori scelti e al numero di  animatori iscritti,
                    non siamo certi di poter assicurare le preferenze espresse.
                    Gli eventuali cambiamenti saranno discussi insieme.
                </p>

                <h3 class="text-center font-weight-bold text-uppercase"> 4 - Allergie o intolleranze alimentari o
                    terapie in corso </h3>

                <@ui.textareaField id="noteAlimentari" label="Allergie o intolleranze alimentari o terapie in corso" placeholder="specificare SOLO allergie certificate (non cibi che non piacciono) ed eventuali farmaci da assumere durante il Grest. Specificare anche gli eventuali orari di assunzione." labelClass="sr-only"/>

                <h3 class="text-center font-weight-bold text-uppercase"> 5 - Eventuali richieste o proposte </h3>

                <@ui.textareaField id="richieste" label="Eventuali richieste o proposte" placeholder="specificare SOLO se presenti. Si ricorda che, se si richiede di essere in squadra con qualche altro ragazzo, i responsabili del Grest cercheranno di equilibrare le squadre anche in base alle richieste qui espresse. Qualsiasi altra richiesta di cambio squadra durante il corso del Grest potrebbe quindi non essere soddisfatta. Si richiede pertanto di ponderare con attenzione la vostra scelta e di non richiedere cambiamenti, se non gravemente necessari." labelClass="sr-only"/>

                <h3 class="text-center font-weight-bold text-uppercase"> 6 - Richieste e dichiarazioni dei
                    genitori </h3>

                <@ui.checkboxField id="entrataAnticipata" label="Si richiede, per gravi motivi, di anticipare l'entrata alle ore 8.30 e si versa la quota prevista." labelClass="form-check-label"/>
                <@ui.checkboxField id="mensa" label="Si richiede, per necessità particolari, in caso entrambi i genitori lavorino, di usufruire del servizio mensa, versando la quota di iscrizione prevista." labelClass="form-check-label"/>

                <br/>
                <p class="font-weight-normal">I sottoscritti genitori dichiarano:</p>
                <ul>
                    <li>
                        <@ui.checkboxField id="fratelloIscritto" label="Che il/la proprio/a figlio/a <b>ha già uno o più</b> fratelli/sorelle iscritti al grest." attributes={"data-ha-altro-ragazzo": "${haAltroRagazzoIscritto?c}", "onclick": "toggleAvvisoFratelloIscritto(this);"} labelClass="form-check-label"/>
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
                    <@ui.healthDeclaration/>
                    <li>
                        <@ui.checkboxField id="saNuotare" label="Che il/la proprio/a figlio/a sa nuotare." labelClass="form-check-label"/>
                    </li>
                    <@ui.parentDeclarations/>
                </ul>

                <br/>

                <@ui.checkboxField id="checkprivacy" label="acconsento al trattamento dei dati personali, alla raccolta e all'utilizzo di materiale fotografico/video in conformità a quanto riportato nell'informativa sulla privacy reperibile al link <a href=\"../privacy.html\">informativa sulla privacy</a>" name=[] required=true labelClass="form-check-label"/>

                <@ui.registrationSubmit open=ISCRRAG/>

            </form>
</@ui.formPanel>
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
</@ui.page>