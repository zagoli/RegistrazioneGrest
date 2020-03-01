<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
        <div class="container mt-4 content">
            <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
                <div class="container">
                    <div class="title text-center">
                        <h5>Registrazione nuovo animatore</h5>
                    </div>
                    <#if INVALIDMAIL??>
                        <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
                            <p class="text-danger text-center font-weight-bold">La mail non esiste, non &egrave; scritta correttamente o &egrave; una e-mail temporanea! Inserisci una mail valida</p>
                        </div>
                    </#if>
                    <form action="/RegistrazioneGrest/App/RegistraAnimatore" method="POST" data-parsley-validate id="mainForm">
                        
                        <div class="form-group">
                            <label for="nome"> Nome </label>
                                <input class="form-control" type="text" id ="nome" required name="nome" placeholder="Nome" autofocus data-parsley-length="[2, 50]" value="${registrato.nome}"> 
                        </div>

                        <div class="form-group">
                            <label for="cognome"> Cognome </label> 
                                <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome" data-parsley-length="[2, 50]" value="${registrato.cognome}"> 
                        </div>
                        
                        <div class="form-group">
                            <label for="dataNascita"> Data di nascita </label>
                                <input class="form-control" type="date" required id="dataNascita" name="dataNascita">
                        </div>
                        
                        <div class="form-group">
                            <label for="codiceFiscale"> Codice fiscale </label>
                                <input class="form-control" type="text" required placeholder="Codice Fiscale" id="codiceFiscale" name="codiceFiscale" data-parsley-length="[16, 16]">
                        </div>
                        
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
                            <label> Partecipazione </label> <br/>
                            <#list calendari as cal>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="${cal.idSettimana}" id="cal${cal.idSettimana}" name="cal" checked>
                                    <label class="form-check-label" for="cal${cal.idSettimana}"> Settimana dal ${cal.daQuando} al ${cal.aQuando} </label>
                                </div>
                            </#list>
                        </div>
                        
                        <div class="form-group">
                            <label for="laboratorio"> Laboratorio </label>
                            <select id="laboratorio" class="form-control" name="laboratorio">
                                <#list laboratori as lab>
                                    <option value="${lab.id}">${lab.descrizione}</option>
                                </#list>
                            </select>
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
                            <label for="cellulare"> Numero di cellulare </label> 
                                <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]" required id="cellulare" name="cellulare" placeholder="Cellulare personale" value="${registrato.telefono}"> 
                        </div>
                        
                        <div class="form-group">
                            <label> Fascia et&agrave; ragazzi preferita </label> <br/>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="P14" value="P14" checked>
                                <label class="form-check-label" for="P14">Piccoli (1-4 primaria)</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="G52" value="G52">
                                <label class="form-check-label" for="G52">Grandi (5 primaria - 2 media)</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="AA3" value="AA3">
                                <label class="form-check-label" for="AA3">Aiuto animatori (3 media)</label>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="mail"> E-Mail </label> 
                                <input class="form-control" type="email" required id="mail" name="mail" placeholder="E-Mail" value="${registrato.mail}"> 
                        </div>

                        <div class="form-group">
                            <label for="nTessera"> Numero tessera Circolo Noi <b><script type="text/javascript">var theDate=new Date();document.write(theDate.getFullYear());</script></b></label>
                            <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control" data-parsley-length="[11, 11]" aria-describedby="helpNTessera">
                            <small id="helpNTessera" class="form-text text-muted">
                                Inserire il numero di tessera del Circolo Noi se non si &egrave; iscritti al Circolo Noi di Balconi.
                            </small>
                        </div>
                        
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="checkprivacy" required/>
                            <label class="form-check-label" for="checkprivacy"> acconsento al trattamento dei dati personali, alla raccolta e all'utilizzo di materiale fotografico/video in conformit&agrave; a quanto riportato nell'informativa sulla privacy reperibile al link <a href="../privacy.html">informativa sulla privacy</a></label>
                        </div>
                        
                        <div class="pt-3 form-group" id="codiceForm">
                            <#if !ISCRAN>
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
                                <input id="btnreg" class="form-control btn btn-primary" type="submit" value="Registra"/>
                            </div>
                            </#if>
                        </div>
                        
                    </form>
                </div>
            </div>
        </div>
<script src="../risorse/js/ajaxsbloccaiscr.js"></script>
<#include "../struct/footer.html.ftl">
