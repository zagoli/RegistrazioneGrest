<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
        <div class="container mt-4 content">
            <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
                <div class="container">
                    <div class="title text-center">
                        <h5>Modifica terza media</h5>
                    </div>
                    <#if INVALIDMAIL??>
                        <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
                            <p class="text-danger text-center font-weight-bold">La mail non esiste, non è scritta
                                correttamente o è una e-mail temporanea! Inserisci una mail valida</p>
                        </div>
                    </#if>
                    <form action="/RegistrazioneGrest/App/ModificaTerzamedia" method="POST" data-parsley-validate="">

                        <input type="hidden" value="${terzamedia.id?c}" name="id"></input>

                        <div class="form-group">
                            <label for="nome"> Nome </label>
                            <input class="form-control" type="text" id="nome" required name="nome" placeholder="Nome"
                                   autofocus data-parsley-length="[2, 50]" value="${terzamedia.nome}">
                        </div>

                        <div class="form-group">
                            <label for="cognome"> Cognome </label>
                            <input class="form-control" type="text" required id="cognome" name="cognome"
                                   placeholder="Cognome" data-parsley-length="[2, 50]" value="${terzamedia.cognome}">
                        </div>

                        <div class="form-group">
                            <label for="dataNascita"> Data di nascita </label>
                            <input class="form-control" type="date" required id="dataNascita" name="dataNascita"
                                   value="${dataNascita}">
                        </div>

                        <div class="form-group">
                            <label> Presenza giornaliera </label> <br/>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="presenza" id="completo" value="C"
                                       <#if terzamedia.presenza == "C">checked</#if>>
                                <label class="form-check-label" for="completo">Completo</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="presenza" id="mattina" value="M"
                                       <#if terzamedia.presenza == "M">checked</#if>>
                                <label class="form-check-label" for="mattina">Mattina</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="presenza" id="pomeriggio" value="P"
                                       <#if terzamedia.presenza == "P">checked</#if>>
                                <label class="form-check-label" for="pomeriggio">Pomeriggio</label>
                            </div>
                        </div>

                        <div class="form-group">
                            <label> Partecipazione </label> <br/>
                            <#list calendari as cal, partecipa>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" value="${cal.idSettimana?c}"
                                           id="cal${cal.idSettimana?c}" name="cal" <#if partecipa>checked</#if>>
                                    <label class="form-check-label" for="cal${cal.idSettimana?c}"> Settimana
                                        dal ${cal.daQuando} al ${cal.aQuando} </label>
                                </div>
                            </#list>
                            <a class="btn btn-secondary btn-sm mt-1" href="javascript:;"
                               onClick="window.open('/RegistrazioneGrest/App/VisualizzaQuote?terzamedia', '_blank', 'width=1000, height=600, status=yes, scrollbars=no, location=yes');">clicca
                                qui per consultare le quote d'iscrizione</a>
                        </div>

                        <div class="form-group">
                            <label for="laboratorio"> Laboratorio </label>
                            <select id="laboratorio" class="form-control" name="laboratorio">
                                <#list laboratori as lab>
                                    <option value="${lab.id?c}"
                                            <#if lab.id == terzamedia.laboratorio.id>selected</#if>>${lab.descrizione}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="parrocchia"> Parrocchia </label>
                            <select id="parrocchia" class="form-control" name="parrocchia">
                                <#list parrocchie as par>
                                    <option value="${par.id?c}" <#if par.id == terzamedia.parrocchia.id>selected</#if>>
                                        Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="circolo"> Circolo </label>
                            <select id="circolo" class="form-control" name="circolo">
                                <#list circoli as cir>
                                    <option value="${cir.id?c}"
                                            <#if cir.id == terzamedia.circolo.id>selected</#if>>${cir.nome}
                                        (${cir.luogo})
                                    </option>
                                </#list>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="nTessera"> Numero tessera Circolo Noi <b><script type="text/javascript">var theDate=new Date();document.write(theDate.getFullYear());</script></b></label>
                            <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control" data-parsley-length="[11, 11]" aria-describedby="helpNTessera" <#if terzamedia.nTessera??>value="${terzamedia.nTessera}"</#if>/>
                            <small id="helpNTessera" class="form-text text-muted">
                                Inserire il numero di tessera del Circolo Noi se non si è iscritti al Circolo Noi di Balconi.
                            </small>
                        </div>

                        <div class="form-group">
                            <label for="scuola"> Scuola </label>
                            <select id="scuola" class="form-control" name="scuola">
                                <#list scuole as scu>
                                    <option value="${scu.id?c}" <#if scu.id == terzamedia.scuola.id>selected</#if>>
                                        Scuola ${scu.grado} di ${scu.descrizione}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="sezione"> Sezione </label>
                            <input class="form-control" type="text" required id="sezione" name="sezione"
                                   data-parsley-length="[1, 1]" placeholder="A" value="${terzamedia.sezione}">
                        </div>

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
                            <textarea class="form-control" rows="5" id="richieste" name="richieste"><#if terzamedia.richieste??>${terzamedia.richieste}</#if></textarea>
                        </div>

                        <div class="form-group">
                            <label for="noteAlimentari">Allergie o intolleranze alimentari o terapie in corso:</label>
                            <textarea class="form-control" rows="5" id="noteAlimentari" name="noteAlimentari"><#if terzamedia.noteAlimentari??>${terzamedia.noteAlimentari}</#if></textarea>
                        </div>

                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="saNuotare" name="saNuotare" <#if terzamedia.saNuotare>checked</#if>>
                            <label class="form-check-label" for="saNuotare"> Sa nuotare? </label>
                        </div>
                        
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="festaPassaggio" name="festaPassaggio" <#if terzamedia.festaPassaggio>checked</#if>>
                            <label class="form-check-label" for="festaPassaggio"> Si dichiara che il/la proprio/a figlio/a ha partecipato alla festa del passaggio a Balconi.</label>
                        </div>

                        <br/>

                        <div class="pt-3 form-group">
                            <input class="form-control btn btn-primary" type="submit" value="Modifica">
                        </div>
                    </form>
                </div>
            </div>
        </div>
<#include "../struct/footer.html.ftl">