<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
        <div class="container mt-4 content">
            <div class="border border-light rounded pt-2 pl-3 pr-3 pb-2 bg-light vertical-center">
                <div class="container">
                    <div class="title text-center">
                        <h5>Modifica animatore</h5>
                    </div>
                    <#if INVALIDMAIL??>
                        <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
                            <p class="text-danger text-center font-weight-bold">La mail non esiste, non &egrave; scritta correttamente o &egrave; una e-mail temporanea! Inserisci una mail valida</p>
                        </div>
                    </#if>
                    <form action="/RegistrazioneGrest/App/ModificaAnimatore" method="POST" data-parsley-validate>
                        
                        <input type="hidden" value="${animatore.id}" name="id"></input>
                        
                        <div class="form-group">
                            <label for="nome"> Nome </label>
                                <input class="form-control" type="text" id ="nome" required name="nome" placeholder="Nome" autofocus data-parsley-length="[2, 50]" value="${animatore.nome}"> 
                        </div>

                        <div class="form-group">
                            <label for="cognome"> Cognome </label> 
                                <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome" data-parsley-length="[2, 50]" value="${animatore.cognome}"> 
                        </div>
                        
                        <div class="form-group">
                            <label for="dataNascita"> Data di nascita </label>
                                <input class="form-control" type="date" required id="dataNascita" name="dataNascita" value="${dataNascita}">
                        </div>
                        
                        <div class="form-group">
                            <label for="codiceFiscale"> Codice fiscale </label>
                                <input class="form-control" type="text" required id="codiceFiscale" name="codiceFiscale" data-parsley-length="[16, 16]" value="${animatore.codiceFiscale}">
                        </div>
                        
                        <div class="form-group">
                            <label> Presenza giornaliera </label> <br/>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="presenza" id="mattina" value="M" <#if animatore.presenza = "M">checked</#if>>
                                <label class="form-check-label" for="mattina">Mattina</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="presenza" id="pomeriggio" value="P" <#if animatore.presenza = "P">checked</#if>>
                                <label class="form-check-label" for="pomeriggio">Pomeriggio</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="presenza" id="completo" value="C" <#if animatore.presenza = "C">checked</#if>>
                                <label class="form-check-label" for="completo">Completo</label>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label> Partecipazione </label> <br/>
                            <#list calendari as cal, partecipa>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="${cal.idSettimana}" id="cal${cal.idSettimana}" name="cal" <#if partecipa>checked</#if>>
                                    <label class="form-check-label" for="cal${cal.idSettimana}"> Settimana dal ${cal.daQuando} al ${cal.aQuando} </label>
                                </div>
                            </#list>
                        </div>
                        
                        <div class="form-group">
                            <label for="laboratorio"> Laboratorio </label>
                            <select id="laboratorio" class="form-control" name="laboratorio">
                                <#list laboratori as lab>
                                    <option value="${lab.id}" <#if lab.id = animatore.laboratorio.id>selected</#if>>${lab.descrizione}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="parrocchia"> Parrocchia </label>
                            <select id="parrocchia" class="form-control" name="parrocchia">
                                <#list parrocchie as par>
                                    <option value="${par.id}" <#if par.id = animatore.parrocchia.id>selected</#if>>Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="circolo"> Circolo </label>
                            <select id="circolo" class="form-control" name="circolo">
                                <#list circoli as cir>
                                    <option value="${cir.id}" <#if cir.id = animatore.circolo.id>selected</#if>>${cir.nome} (${cir.luogo})</option>
                                </#list>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="cellulare"> Numero di cellulare </label> 
                                <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]" required id="cellulare" name="cellulare" placeholder="Cellulare personale" value="${animatore.cellulare}"> 
                        </div>
                        
                        <div class="form-group">
                            <label> Fascia et&agrave; ragazzi preferita </label> <br/>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="P14" value="P14" <#if animatore.fasciaEtaRagazzi = "P14">checked</#if>>
                                <label class="form-check-label" for="P14">Piccoli (1-4 primaria)</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="G52" value="G52" <#if animatore.fasciaEtaRagazzi = "G52">checked</#if>>
                                <label class="form-check-label" for="G52">Grandi (5 primaria - 2 media)</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="AA3" value="AA3" <#if animatore.fasciaEtaRagazzi = "AA3">checked</#if>>
                                <label class="form-check-label" for="AA3">Aiuto animatori (3 media)</label>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="mail"> E-Mail </label> 
                                <input class="form-control" type="email" required id="mail" name="mail" placeholder="E-Mail" value="${animatore.mail}"> 
                        </div>

                        <div class="form-group">
                            <label for="nTessera"> Numero tessera Circolo Noi <b><script type="text/javascript">var theDate=new Date();document.write(theDate.getFullYear());</script></b> </label>
                            <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control" data-parsley-length="[11, 11]" aria-describedby="helpNTessera" <#if animatore.nTessera??>value="${animatore.nTessera}"</#if>>
                            <small id="helpNTessera" class="form-text text-muted">
                                Inserire il numero di tessera del Circolo Noi se non si &egrave; iscritti al Circolo Noi di Balconi.
                            </small>
                        </div>
                        
                         <div class="pt-3 form-group">
                            <input class="form-control" type="submit" value="Modifica">
                        </div>
                        
                    </form>
                </div>
            </div>
        </div>
<#include "../struct/footer.html.ftl">
