<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container mt-4 content">
        <div class="border border-light rounded pt-2 pl-3 pr-3 pb-2 bg-light vertical-center">
            <div class="container">
                <div class="title text-center">
                    <h5>Registrazione nuovo ragazzo</h5>
                </div>
                <form action="/RegistrazioneGrest/App/RegistraRagazzo" method="POST" data-parsley-validate="" id="mainForm">

                    <h3 class="text-center font-weight-bold text-uppercase"> 1 - dati anagrafici </h3>

                    <div class="form-group">
                        <label for="nome"> Nome </label>
                            <input class="form-control" type="text" id ="nome" required name="nome" placeholder="Nome" autofocus data-parsley-length="[2, 50]"> 
                    </div>

                    <div class="form-group">
                        <label for="cognome"> Cognome </label> 
                            <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome" data-parsley-length="[2, 50]"> 
                    </div>

                    <div class="form-group">
                        <label for="dataNascita"> Data di nascita </label>
                            <input class="form-control" type="date" required id="dataNascita" name="dataNascita">
                    </div>

                    <div class="form-group">
                        <label for="parrocchia"> Parrocchia </label>
                        <select id="parrocchia" class="form-control" name="parrocchia">
                            <#list parrocchie as par>
                                <option value="${par.id}">Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
                            </#list>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="circolo"> Circolo </label>
                        <select id="circolo" class="form-control" name="circolo">
                            <#list circoli as cir>
                                <option value="${cir.id}">${cir.nome} (${cir.luogo})</option>
                            </#list>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="nTessera"> Numero tessera Circolo Noi <b><script type="text/javascript">var theDate=new Date();document.write(theDate.getFullYear());</script></b></label>
                        <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control" data-parsley-length="[11, 11]" aria-describedby="helpNTessera">
                        <small id="helpNTessera" class="form-text text-muted">
                            Inserire il numero di tessera del Circolo Noi se non si &egrave; tesserati al Circolo Noi di Balconi.
                        </small>
                    </div>

                    <div class="form-group">
                        <label for="scuola"> Scuola </label>
                        <select id="scuola" class="form-control" name="scuola">
                            <#list scuole as scu>
                                <option value="${scu.id}">Scuola ${scu.grado} di ${scu.descrizione}</option>
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
                            <input class="form-control" type="text" required id="sezione" name="sezione" data-parsley-length="[1, 5]" placeholder="sezione">
                    </div>

                    <h3 class="text-center font-weight-bold text-uppercase"> 2 - partecipazione al grest nel periodo segnato </h3>

                    <div class="form-group">
                        <label> Presenza giornaliera </label> <br/>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="presenza" id="completo" value="C" checked>
                            <label class="form-check-label" for="completo">Completo</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="presenza" id="mattina" value="M">
                            <label class="form-check-label" for="mattina">Mattina</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="presenza" id="pomeriggio" value="P">
                            <label class="form-check-label" for="pomeriggio">Pomeriggio</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label> Partecipazione </label>
                        <#list calendari as cal>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="${cal.idSettimana}" id="cal${cal.idSettimana}" name="cal" checked>
                                <label class="form-check-label" for="cal${cal.idSettimana}"> Settimana dal ${cal.daQuando} al ${cal.aQuando} </label>
                            </div>
                        </#list>
                        <a class="btn btn-secondary btn-sm mt-1" href="javascript:;" onClick="window.open('/RegistrazioneGrest/quote.html', 'Quote di iscrizione', 'width=1000, height=600, status, scrollbars=1, location');">clicca qui per consultare le quote d'iscrizione</a>
                    </div>

                    <h3 class="text-center font-weight-bold text-uppercase"> 3 - scelta dell'attivit&agrave; per i ragazzi iscritti al grest </h3>
                    <p>Durante il Grest scelgo di svolgere la seguente  attivit&agrave;</p>

                    <div class="form-group">
                        <label for="laboratorio" class="sr-only"> Laboratorio </label>
                        <select id="laboratorio" class="form-control" name="laboratorio" aria-describedby="helpLaboratorio">
                            <#list laboratori as lab>
                                <option value="${lab.id}">${lab.descrizione}</option>
                            </#list>
                        </select>
                    </div>

                    <h3 class="text-center font-weight-bold text-uppercase"> 4 - Allergie o intolleranze alimentari o terapie in corso </h3>

                    <div class="form-group">
                        <label for="noteAlimentari" class="sr-only">Allergie o intolleranze alimentari o terapie in corso</label>
                        <textarea class="form-control" rows="5" id="noteAlimentari" name="noteAlimentari" placeholder="specificare SOLO se presenti"></textarea>
                    </div>

                    <h3 class="text-center font-weight-bold text-uppercase"> 5 - Eventuali richieste o proposte </h3>

                    <div class="form-group">
                        <label for="richieste" class="sr-only">Eventuali richieste o proposte</label>
                        <textarea class="form-control" rows="5" id="richieste" name="richieste" placeholder="specificare SOLO se presenti. Si ricorda che, se si richiede di essere in squadra con qualche altro ragazzo, i responsabili cercheranno di equilibrare le squadre anche in base a questa richiesta. Qualsiasi altra richiesta di cambio squadra durante il corso del Grest potrebbe quindi non essere soddisfatta. Si richiede pertanto di ponderare con attenzione la vostra scelta."></textarea>
                    </div>                       

                    <h3 class="text-center font-weight-bold text-uppercase"> 6 - richieste e dichiarazioni dei genitori  </h3>

                    <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="entrataAnticipata" name="entrataAnticipata">
                            <label class="form-check-label" for="entrataAnticipata">Si richiede, per gravi motivi, di anticipare l'entrata alle ore 8.30 e si versa  la quota di &euro; 10,00 a settimana. </label>
                        </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="mensa" name="mensa">
                        <label class="form-check-label" for="mensa"> Si richiede, per necessit&agrave; particolari, in caso entrambi i genitori lavorino, di usufruire del servizio mensa, versando la quota di iscrizione prevista. </label>
                    </div>

                    <br/>
                    <p class="font-weight-normal">I sottoscritti genitori dichiarano:</p>
                    <ul>
                        <li><div class="form-check">
                            <input class="form-check-input" type="checkbox" id="saNuotare" name="saNuotare">
                            <label class="form-check-label" for="saNuotare"> Che il/la proprio/a figlio/a sa nuotare.</label>
                        </div></li>

                        <li><div class="form-check">
                            <input class="form-check-input" type="checkbox" id="fratelloIscritto" name="fratelloIscritto">
                            <label class="form-check-label" for="fratelloIscritto"> Che il/la proprio/a figlio/a <b class="text-danger">ha gi&agrave; <i>uno o pi&ugrave;</i></b> fratelli/sorelle iscritti al grest.</label>
                        </div></li>
                        <li> <input type="checkbox"/> di autorizzare il/la figlio/a a tornare a casa al termine del Grest da solo;</li>
                        <li> <input type="checkbox"/> di <u>NON</u> autorizzare il/la figlio/a a tornare a casa dal Grest negli orari stabiliti da solo; deve aspettare una delle seguenti persone da noi incaricate, indicate nella sezione "Accompagnatori e Contatti telefonici Urgenze" della piattaforma </li> 
                        <li>che il/la proprio/a figlio/a gode di buona salute e che pertanto pu&ograve; affrontare le attivit&agrave; ludico-sportive previste; in caso contrario, si impegnano a chiederne l'esonero al momento dell'iscrizione (sezione richieste o proposte nella scheda di iscrizione ragazzo);</li>
                        <li>di essere consapevoli che la responsabilit&agrave; della Parrocchia nei confronti dei ragazzi e la relativa copertura 	assicurativa inizia e termina negli orari indicati; non c'&egrave; copertura per gli spostamenti da e verso l'abitazione;</li>
                        <li>di autorizzare i responsabili del Grest e i loro collaboratori ad assumere le iniziative che riterranno necessarie per garantire la sicurezza dei partecipanti e la buona riuscita delle attivit&agrave; del Grest;</li>
                        <li>di non ritenere gli organizzatori del Grest responsabili per eventuali furti di biciclette o di oggetti di qualsiasi valore; i proprietari dovranno pertanto provvedere ad eventuali sistemi di protezione o di custodia;</li>
                        <li>di essere informati, ai sensi e per gli effetti di cui all'art. 13 del D.Lgs 196/2003, che i dati personali raccolti saranno trattati, anche con strumenti informatici, esclusivamente nell'ambito dell'attivit&agrave; del Grest di Balconi e concedono la liberatoria alle riprese video/fotografiche esclusivamente nell'ambito dell'attivit&agrave; del Grest di Balconi.</li>
                    </ul>

                    <br/>

                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="checkprivacy" required/>
                        <label class="form-check-label" for="checkprivacy"> acconsento al trattamento dei dati personali, alla raccolta e all'utilizzo di materiale fotografico/video in conformit&agrave; a quanto riportato nell'informativa sulla privacy reperibile al link <a href="../privacy.html">informativa sulla privacy</a></label>
                    </div>
                    
                    <div class="pt-3 form-group" id="codiceForm">
                        <#if !ISCRRAG>
                        <!--modulo inserimento codice sblocco iscrizione-->
                        <label for="codicesblocco" class="text-danger">Le iscrizioni sono chiuse. Inserire il codice per sbloccare l'iscrizione. Il codice pu&ograve; essere eventualmente richiesto a <a href="mailto:parrocchiadibalconi@gmail.com">Don Lorenzo</a></label>
                        <div class="input-group">    
                            <input id="codicesblocco" type="text" class="form-control" placeholder="inserisci il codice usa e getta per sbloccare l'iscrizione" required data-parsley-errors-messages-disabled/>
                            <div class="input-group-append">
                                <button id="btnsblocco" type="button" class="btn btn-outline-primary">Verifica il codice e completa la registrazione</button>
                            </div>
                        </div>
                        <!--fine-->
                        <#else>
                        <div id="divbtnreg" class="row">
                            <input id="btnreg" class="form-control" type="submit" value="Registra"/>
                        </div>
                        </#if>
                    </div>
                    
                </form>
            </div>
        </div>
    </div>
<script src="../risorse/js/ajaxsbloccaiscr.js"></script>
<#include "../struct/footer.html.ftl">