<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Iscrizione come animatore del Grest di Balconi</h5>
            </div>
            <#if INVALIDMAIL??>
                        <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
                            <p class="text-danger text-center font-weight-bold">La mail non esiste, non è scritta correttamente o è una e-mail temporanea! Inserisci una mail valida</p>
                        </div>
            </#if>
            <form action="/RegistrazioneGrest/App/RegistraAnimatore" method="POST" data-parsley-validate id="mainForm">

                <div class="form-group">
                    <label for="cognome"> Cognome </label>
                    <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome"
                           data-parsley-length="[2, 50]" value="${registrato.cognome}">
                </div>

                <div class="form-group">
                    <label for="nome"> Nome </label>
                    <input class="form-control" type="text" id="nome" required name="nome" placeholder="Nome" autofocus
                           data-parsley-length="[2, 50]" value="${registrato.nome}">
                </div>

                <div class="form-group">
                    <label for="dataNascita"> Data di nascita </label>
                    <input class="form-control" type="date" required id="dataNascita" name="dataNascita">
                </div>

                <div class="form-group">
                    <label for="codiceFiscale"> Codice fiscale </label>
                    <input class="form-control" type="text" required placeholder="Codice Fiscale" id="codiceFiscale"
                           name="codiceFiscale" data-parsley-length="[16, 16]">
                </div>

                <div class="form-group">
                    <label> Presenza giornaliera al Grest</label> <br/>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="completo" value="C" checked>
                        <label class="form-check-label" for="completo">Completo</label>
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
                    <label> Periodo di partecipazione </label> <br/>
                    <#list calendari as cal>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="${cal.idSettimana}" id="cal${cal.idSettimana}" name="cal" checked>
                                    <label class="form-check-label" for="cal${cal.idSettimana}"> Settimana dal ${cal.daQuando} al ${cal.aQuando} </label>
                                </div>
                            </#list>
                </div>

                <div class="form-group">
                    <label for="laboratorio"> Selezionare un laboratorio </label>
                    <select id="laboratorio" class="form-control" name="laboratorio">
                        <#list laboratori as lab>
                                    <option value="${lab.id}">${lab.descrizione}</option>
                                </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="parrocchia"> Parrocchia di appartenenza </label>
                    <select id="parrocchia" class="form-control" name="parrocchia">
                        <#list parrocchie as par>
                                    <option value="${par.id}">Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
                                </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="circolo"> Tesseramento Circolo NOI
                        <b>
                            <script type="text/javascript">var theDate = new Date();
                                document.write(theDate.getFullYear());</script>
                        </b>
                    </label>
                    <select id="circolo" class="form-control" name="circolo">
                        <#list circoli as cir>
                                    <option value="${cir.id}">${cir.nome} (${cir.luogo})</option>
                                </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="nTessera"> Numero tessera Circolo NOI </label>
                    <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control"
                           data-parsley-length="[11, 11]" aria-describedby="helpNTessera">
                    <small id="helpNTessera" class="form-text text-muted">
                        Inserire il numero di tessera del Circolo NOI se non si è iscritti al Circolo NOI di
                        Balconi.
                    </small>
                </div>

                <div class="form-group">
                    <label for="cellulare"> Numero di cellulare </label>
                    <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]" required
                           id="cellulare" name="cellulare" placeholder="Cellulare personale"
                           value="${registrato.telefono}">
                </div>

                <div class="form-group">
                    <label> Fascia età ragazzi con cui vorrei compiere il servizio come animatore </label> <br/>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="PIC" value="PIC">
                        <label class="form-check-label" for="PIC">Piccoli (1°,2°,3°,4° della scuola primaria)</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="GRA" value="GRA">
                        <label class="form-check-label" for="GRA">Grandi (5° primaria, 1°,2° media)</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="TER" value="TER">
                        <label class="form-check-label" for="TER">Animatori gruppo 3° media</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="mail"> E-Mail personale </label>
                    <input class="form-control" type="email" required id="mail" name="mail" placeholder="E-Mail"
                           value="${registrato.mail}">
                </div>

                <p><b>L'animatore maggiorenne dichiara / I genitori dell'animatore minorenne dichiarano:</b></p>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="checkprivacy" required/>
                    <label class="form-check-label" for="checkprivacy"> di acconsentire al trattamento dei dati
                        personali, alla raccolta e all'utilizzo di materiale fotografico/video in conformità a
                        quanto riportato nell'informativa sulla privacy reperibile al link <a href="../privacy.html">informativa
                            sulla privacy</a></label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c1" required/>
                    <label class="form-check-label" for="c1"> che l'animatore gode di buona salute e che pertanto può
                        affrontare le attività ludico-sportive previste; in caso contrario, se ne chiede l'esonero al
                        momento dell'iscrizione; e che non soffre di allergie o intolleranze alimentari</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c2" required/>
                    <label class="form-check-label" for="c2"> di essere consapevole/i che la responsabilità della
                        Parrocchia e del circolo Noi nei confronti degli animatori e la relativa copertura assicurativa inizia e termina
                        negli orari indicati; non c’è copertura per gli spostamenti da e verso l'abitazione</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c3" required/>
                    <label class="form-check-label" for="c3"> di autorizzare i responsabili del Grest e i loro
                        collaboratori ad assumere le iniziative che riterranno necessarie per garantire la sicurezza dei
                        partecipanti e la buona riuscita delle attività del Grest</label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="c4" required/>
                    <label class="form-check-label" for="c4"> di non ritenere gli organizzatori del Grest responsabili
                        per eventuali furti di motorini, di biciclette o di oggetti di qualsiasi valore; i proprietari
                        dovranno pertanto provvedere ad eventuali sistemi di protezione o di custodia</label>
                </div>

                <div class="pt-3 form-group" id="codiceForm">
                    <#if !ISCRAN>
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
<script src="../risorse/js/ajaxsbloccaiscr.min.js"></script>
<#include "../struct/footer.html.ftl">
