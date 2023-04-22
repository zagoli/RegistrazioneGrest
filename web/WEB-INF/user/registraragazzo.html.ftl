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

                <div class="form-group">
                    <label for="cognome"> Cognome </label>
                    <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome"
                           data-parsley-length="[2, 50]">
                </div>

                <div class="form-group">
                    <label for="nome"> Nome </label>
                    <input class="form-control" type="text" id="nome" required name="nome" placeholder="Nome" autofocus
                           data-parsley-length="[2, 50]">
                </div>

                <div class="form-group">
                    <label for="dataNascita"> Data di nascita </label>
                    <input class="form-control" type="date" required id="dataNascita" name="dataNascita">
                </div>

                <div class="form-group">
                    <label for="parrocchia"> Parrocchia di appartenenza </label>
                    <select id="parrocchia" class="form-control" name="parrocchia">
                        <#list parrocchie as par>
                            <option value="${par.id?c}">Parrocchia di ${par.luogo?upper_case}
                                - ${par.nome} </option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="circolo"> Circolo di tesseramento </label>
                    <select id="circolo" class="form-control" name="circolo">
                        <#list circoli as cir>
                            <option value="${cir.id?c}">${cir.nome} (${cir.luogo})</option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="nTessera"> Numero tessera Circolo Noi <b>
                            <script type="text/javascript">var theDate = new Date();
                                document.write(theDate.getFullYear());</script>
                        </b></label>
                    <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control"
                           data-parsley-length="[11, 11]" aria-describedby="helpNTessera">
                    <small id="helpNTessera" class="form-text text-muted">
                        Inserire il numero di tessera del Circolo Noi se non si è tesserati al Circolo Noi di Balconi.
                    </small>
                </div>

                <div class="form-group">
                    <label for="scuola"> Scuola </label>
                    <select id="scuola" class="form-control" name="scuola">
                        <#list scuole as scu>
                            <option value="${scu.id?c}">Scuola ${scu.grado} di ${scu.descrizione}</option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="classe"> Classe </label>
                    <select id="classe" class="form-control" name="classe">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="sezione"> Sezione </label>
                    <input class="form-control" type="text" required id="sezione" name="sezione"
                           data-parsley-length="[1, 5]" placeholder="sezione">
                </div>

                <h3 class="text-center font-weight-bold text-uppercase"> 2 - partecipazione al grest nel periodo
                    segnato </h3>

                <div class="form-group">
                    <label> Presenza giornaliera </label> <br/>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="completo" value="C" checked>
                        <label class="form-check-label" for="completo">Mattina e pomeriggio</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="mattina" value="M">
                        <label class="form-check-label" for="mattina">Solo mattina</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="pomeriggio" value="P">
                        <label class="form-check-label" for="pomeriggio">Solo pomeriggio</label>
                    </div>
                </div>

                <div class="form-group">
                    <label> Partecipazione </label>
                    <#list calendari as cal>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="${cal.idSettimana?c}"
                                   id="cal${cal.idSettimana?c}" name="cal" checked>
                            <label class="form-check-label" for="cal${cal.idSettimana?c}"> Settimana
                                dal ${cal.daQuando} al ${cal.aQuando} </label>
                        </div>
                    </#list>
                    <a class="btn btn-secondary btn-sm mt-1" href="javascript:;"
                       onClick="window.open('/RegistrazioneGrest/App/VisualizzaQuote?ragazzi', '_blank', 'width=1000, height=900, status=yes, scrollbars=no, location=yes');">clicca
                        qui per consultare le quote d'iscrizione</a>
                </div>

                <h3 class="text-center font-weight-bold text-uppercase"> 3 - scelta del laboratorio per i ragazzi
                    iscritti
                    al grest </h3>
                <p>Durante il Grest scelgo di svolgere la seguente attività</p>

                <div class="form-group">
                    <label for="laboratorio" class="sr-only"> Laboratorio </label>
                    <select id="laboratorio" class="form-control" name="laboratorio" aria-describedby="helpLaboratorio">
                        <#list laboratori as lab>
                            <option value="${lab.id?c}">${lab.descrizione}</option>
                        </#list>
                    </select>
                </div>

                <h3 class="text-center font-weight-bold text-uppercase"> 4 - Allergie o intolleranze alimentari o
                    terapie in corso </h3>

                <div class="form-group">
                    <label for="noteAlimentari" class="sr-only">Allergie o intolleranze alimentari o terapie in
                        corso</label>
                    <textarea class="form-control" rows="5" id="noteAlimentari" name="noteAlimentari"
                              placeholder="specificare SOLO allergie certificate (non cibi che non piacciono) ed eventuali farmaci da assumere durante il Grest"></textarea>
                </div>

                <h3 class="text-center font-weight-bold text-uppercase"> 5 - Eventuali richieste o proposte </h3>

                <div class="form-group">
                    <label for="richieste" class="sr-only">Eventuali richieste o proposte</label>
                    <textarea class="form-control" rows="5" id="richieste" name="richieste"
                              placeholder="specificare SOLO se presenti. Si ricorda che, se si richiede di essere in squadra con qualche altro ragazzo, i responsabili cercheranno di equilibrare le squadre anche in base a questa richiesta. Qualsiasi altra richiesta di cambio squadra durante il corso del Grest potrebbe quindi non essere soddisfatta. Si richiede pertanto di ponderare con attenzione la vostra scelta e di non richiedere cambiamenti, se non gravemente necessari."></textarea>
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
                            <input class="form-check-input" type="checkbox" id="saNuotare" name="saNuotare">
                            <label class="form-check-label" for="saNuotare"> Che il/la proprio/a figlio/a sa
                                nuotare.</label>
                        </div>
                    </li>

                    <li>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="fratelloIscritto"
                                   name="fratelloIscritto">
                            <label class="form-check-label" for="fratelloIscritto"> Che il/la proprio/a figlio/a <b
                                        class="text-danger">ha già <i>uno o più</i></b>
                                fratelli/sorelle iscritti al grest.</label>
                        </div>
                    </li>
                    <li><input type="checkbox"/> di autorizzare il/la figlio/a a tornare a casa al termine del Grest
                        da solo;
                    </li>
                    <li><input type="checkbox"/> di <u>NON</u> autorizzare il/la figlio/a a tornare a casa dal Grest
                        negli orari stabiliti da solo; deve aspettare una delle seguenti persone da noi incaricate,
                        indicate nella sezione "Accompagnatori e Contatti telefonici Urgenze" della piattaforma
                    </li>
                    <li>che il/la proprio/a figlio/a gode di buona salute e che pertanto può affrontare le
                        attività ludico-sportive previste; in caso contrario, si impegnano a chiederne
                        l'esonero al momento della conferma dell'iscrizione;
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
<script src="../risorse/js/ajaxsbloccaiscr.js"></script>
<#include "../struct/footer.html.ftl">