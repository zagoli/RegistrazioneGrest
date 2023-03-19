<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Modifica l'iscrizione</h5>
            </div>
            <form action="/RegistrazioneGrest/App/ModificaRagazzo" method="POST" data-parsley-validate="">

                <input type="hidden" value="${ragazzo.id?c}" name="id">

                <div class="form-group">
                    <label for="nome"> Nome </label>
                    <input class="form-control" type="text" id="nome" required name="nome" placeholder="Nome" autofocus
                           data-parsley-length="[2, 50]" value="${ragazzo.nome}">
                </div>

                <div class="form-group">
                    <label for="cognome"> Cognome </label>
                    <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome"
                           data-parsley-length="[2, 50]" value="${ragazzo.cognome}">
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
                               <#if ragazzo.presenza == "C">checked</#if>>
                        <label class="form-check-label" for="completo">Completo</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="mattina" value="M"
                               <#if ragazzo.presenza == "M">checked</#if>>
                        <label class="form-check-label" for="mattina">Mattina</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="pomeriggio" value="P"
                               <#if ragazzo.presenza == "P">checked</#if>>
                        <label class="form-check-label" for="pomeriggio">Pomeriggio</label>
                    </div>
                </div>

                <div class="form-group">
                    <label> Partecipazione </label> <br/>
                    <#list calendari as cal, partecipa>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="${cal.idSettimana?c}"
                                   id="cal${cal.idSettimana?c}" name="cal" <#if partecipa>checked</#if>>
                            <label class="form-check-label" for="cal${cal.idSettimana}"> Settimana dal ${cal.daQuando?c}
                                al ${cal.aQuando} </label>
                        </div>
                    </#list>
                    <a class="btn btn-secondary btn-sm mt-1" href="javascript:;"
                       onClick="window.open('/RegistrazioneGrest/App/VisualizzaQuote?ragazzi', '_blank', 'width=1000, height=600, status=yes, scrollbars=no, location=yes');">clicca
                        qui per consultare le quote d'iscrizione</a>
                </div>

                <div class="form-group">
                    <label for="laboratorio"> Laboratorio </label>
                    <select id="laboratorio" class="form-control" name="laboratorio">
                        <#list laboratori as lab>
                            <option value="${lab.id?c}"
                                    <#if lab.id == ragazzo.laboratorio.id>selected</#if>>${lab.descrizione}</option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="parrocchia"> Parrocchia </label>
                    <select id="parrocchia" class="form-control" name="parrocchia">
                        <#list parrocchie as par>
                            <option value="${par.id?c}" <#if par.id == ragazzo.parrocchia.id>selected</#if>>
                                Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="circolo"> Circolo </label>
                    <select id="circolo" class="form-control" name="circolo">
                        <#list circoli as cir>
                            <option value="${cir.id?c}"
                                    <#if cir.id == ragazzo.circolo.id>selected</#if>>${cir.nome} (${cir.luogo})
                            </option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <label for="nTessera"> Numero tessera Circolo Noi <b>
                            <script type="text/javascript">var theDate = new Date();
                                document.write(theDate.getFullYear());</script>
                        </b></label>
                    <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control"
                           data-parsley-length="[11, 11]" aria-describedby="helpNTessera"
                           <#if ragazzo.nTessera??>value="${ragazzo.nTessera}"</#if>/>
                    <small id="helpNTessera" class="form-text text-muted">
                        Inserire il numero di tessera del Circolo Noi se non si è iscritti al Circolo Noi di Balconi.
                    </small>
                </div>


                <div class="form-group">
                    <label for="scuola"> Scuola </label>
                    <select id="scuola" class="form-control" name="scuola">
                        <#list scuole as scu>
                            <option value="${scu.id?c}" <#if scu.id == ragazzo.scuola.id>selected</#if>>
                                Scuola ${scu.grado} di ${scu.descrizione}</option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="classe"> Classe </label>
                    <select id="classe" class="form-control" name="classe">
                        <option value="1" <#if ragazzo.classe == "1">selected</#if>>1</option>
                        <option value="2" <#if ragazzo.classe == "2">selected</#if>>2</option>
                        <option value="3" <#if ragazzo.classe == "3">selected</#if>>3</option>
                        <option value="4" <#if ragazzo.classe == "4">selected</#if>>4</option>
                        <option value="5" <#if ragazzo.classe == "5">selected</#if>>5</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="sezione"> Sezione </label>
                    <input class="form-control" type="text" required id="sezione" name="sezione"
                           data-parsley-length="[1, 5]" placeholder="Classe" value="${ragazzo.sezione}">
                </div>

                <div class="form-group">
                    <label for="richieste">Eventuali richieste o proposte:</label>
                    <textarea class="form-control" rows="5" id="richieste" name="richieste"
                              placeholder="Si ricorda che, se si richiede di essere in squadra con qualche altro ragazzo, i responsabili cercheranno di equilibrare le squadre anche in base a questa richiesta. Qualsiasi altra richiesta di cambio squadra durante il corso del Grest potrebbe quindi non essere soddisfatta. Si richiede pertanto di ponderare con attenzione la vostra scelta."><#if ragazzo.richieste??>${ragazzo.richieste}</#if></textarea>
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
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">