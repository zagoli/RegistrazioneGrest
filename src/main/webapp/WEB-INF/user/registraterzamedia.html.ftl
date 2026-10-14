<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.formPanel title="Iscrizione al Grest di Balconi come aiuto animatore" containerClass="container mt-4 content" inlineTitle=false>
            <#if INVALIDMAIL??>
                <@ui.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/RegistraTerzamedia" method="POST" data-parsley-validate=""
                  id="mainForm">

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

                <@ui.inputField id="sezione" label="frequentante la classe terza media , sezione" required=true placeholder="A" length="[1, 1]"/>

                <@ui.inputField id="cellulare" label="Eventuale numero di cellulare del/la ragazzo/a" placeholder="cellulare" length="[10, 10]"/>

                <@ui.inputField id="mail" label="Indirizzo E-Mail del ragazzo o della famiglia" type="email" required=true placeholder="E-Mail"/>

                <h3 class="text-center font-weight-bold text-uppercase"> 2 - partecipazione al grest nel periodo
                    segnato </h3>

                <@ui.presenzaField selected="C" label="Presenza giornaliera" completo="Mattina e pomeriggio"/>

                <@ui.partecipazioneField calendari=calendari label="Partecipazione" editing=false lineBreak=false quoteTarget="terzamedia" popupHeight=600/>

                <h3 class="text-center font-weight-bold text-uppercase"> 3 - scelta del laboratorio</h3>
                <p>Durante il Grest scelgo di svolgere la seguente attività</p>

                <@ui.selectField id="laboratorio" label="Laboratorio" labelClass="sr-only" attributes={"aria-describedby": "helpLaboratorio"}>
                    <@ui.laboratorioOptions options=laboratori/>
                </@ui.selectField>

                <p>
                    In relazione al numero di ragazzi, ai laboratori scelti e al numero di  animatori iscritti,
                    non siamo certi di poter assicurare le preferenze espresse.
                    Gli eventuali cambiamenti saranno discussi insieme.
                </p>

                <h3 class="text-center font-weight-bold text-uppercase"> 4 - Allergie o intolleranze alimentari
                    o terapie in corso </h3>

                <@ui.textareaField id="noteAlimentari" label="Allergie o intolleranze alimentari o terapie in corso" placeholder="specificare SOLO allergie certificate (non cibi che non piacciono) ed eventuali farmaci da assumere durante il Grest. Specificare anche gli eventuali orari di assunzione." labelClass="sr-only"/>

                <h3 class="text-center font-weight-bold text-uppercase"> 5 - Eventuali richieste o
                    proposte </h3>

                <@ui.textareaField id="richieste" label="Eventuali richieste o proposte" placeholder="specificare SOLO se presenti" labelClass="sr-only"/>

                <h3 class="text-center font-weight-bold text-uppercase"> 6 - Richieste e dichiarazioni dei
                    genitori </h3>
                <p class="font-weight-normal">I genitori dichiarano:</p>
                <ul>
                    <li>
                        <@ui.checkboxField id="festaPassaggio" label="Che il/la proprio/a figlio/a ha partecipato alla festa del passaggio a Balconi." labelClass="form-check-label"/>
                    </li>
                    <li><input type="checkbox"/> di autorizzare il/la figlio/a a tornare a casa al termine del
                        Grest da solo;
                    </li>
                    <li><input type="checkbox"/> di <u>NON</u> autorizzare il/la figlio/a a tornare a casa dal
                        Grest negli orari stabiliti da solo; deve aspettare una delle seguenti persone da noi
                        incaricate, indicate nella sezione "Accompagnatori e Contatti telefonici Urgenze" della
                        piattaforma
                    </li>
                    <@ui.healthDeclaration/>
                    <li>
                        <@ui.checkboxField id="saNuotare" label="Che il/la proprio/a figlio/a sa nuotare." labelClass="form-check-label"/>
                    </li>
                    <@ui.parentDeclarations/>
                </ul>
                <br/>

                <@ui.checkboxField id="checkprivacy" label="Acconsento al trattamento dei dati personali, alla raccolta e all’utilizzo di materiale fotografico/video in conformità a quanto riportato nell’informativa sulla privacy reperibile al link <a href=\"../privacy.html\">informativa sulla privacy</a>" name=[] required=true labelClass="form-check-label"/>

                <@ui.registrationSubmit open=ISCRTER/>
            </form>
</@ui.formPanel>
<script src="../js/ajaxsbloccaiscr.js"></script>
</@ui.page>