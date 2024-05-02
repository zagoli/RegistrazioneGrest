<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container mt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Modifica l'iscrizione come animatore</h5>
            </div>
            <#if INVALIDMAIL??>
                <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
                    <p class="text-danger text-center font-weight-bold">La mail non esiste, non è scritta correttamente
                        o è una e-mail temporanea! Inserisci una mail valida</p>
                </div>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaAnimatore" method="POST" data-parsley-validate>

                <input type="hidden" value="${animatore.id?c}" name="id"></input>

                <div class="form-group">
                    <label for="nome"> Nome </label>
                    <input class="form-control" type="text" id="nome" required name="nome" placeholder="Nome" autofocus
                           data-parsley-length="[2, 50]" value="${animatore.nome}">
                </div>

                <div class="form-group">
                    <label for="cognome"> Cognome </label>
                    <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome"
                           data-parsley-length="[2, 50]" value="${animatore.cognome}">
                </div>

                <div class="form-group">
                    <label for="dataNascita"> Data di nascita </label>
                    <input class="form-control" type="date" required id="dataNascita" name="dataNascita"
                           value="${dataNascita}">
                </div>

                <div class="form-group">
                    <label for="codiceFiscale"> Codice fiscale </label>
                    <input class="form-control" type="text" required id="codiceFiscale" name="codiceFiscale"
                           data-parsley-length="[16, 16]" value="${animatore.codiceFiscale}">
                </div>

                <div class="form-group">
                    <label> Presenza giornaliera al Grest</label> <br/>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="completo" value="C"
                               <#if animatore.presenza == "C">checked</#if>>
                        <label class="form-check-label" for="completo">Completo</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="mattina" value="M"
                               <#if animatore.presenza == "M">checked</#if>>
                        <label class="form-check-label" for="mattina">Solo mattina</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="presenza" id="pomeriggio" value="P"
                               <#if animatore.presenza == "P">checked</#if>>
                        <label class="form-check-label" for="pomeriggio">Solo pomeriggio</label>
                    </div>
                </div>

                <div class="form-group">
                    <label> Periodo di partecipazione </label> <br/>
                    <#list calendari as cal, partecipa>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="${cal.idSettimana?c}"
                                   id="cal${cal.idSettimana?c}" name="cal" <#if partecipa>checked</#if>>
                            <label class="form-check-label" for="cal${cal.idSettimana?c}"> Settimana
                                dal ${cal.daQuando} al ${cal.aQuando} </label>
                        </div>
                    </#list>
                </div>

                <div class="form-group">
                    <label for="laboratorio"> Laboratorio </label>
                    <select id="laboratorio" class="form-control" name="laboratorio">
                        <#list laboratori as lab>
                            <option value="${lab.id?c}"
                                    <#if lab.id == animatore.laboratorio.id>selected</#if>>${lab.descrizione}</option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="parrocchia"> Parrocchia di appartenenza </label>
                    <select id="parrocchia" class="form-control" name="parrocchia">
                        <#list parrocchie as par>
                            <option value="${par.id?c}" <#if par.id == animatore.parrocchia.id>selected</#if>>
                                Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="circolo"> Circolo NOI di iscrizione <b>
                            <script type="text/javascript">var theDate = new Date();
                                document.write(theDate.getFullYear());</script>
                        </b> </label>
                    <select id="circolo" class="form-control" name="circolo">
                        <#list circoli as cir>
                            <option value="${cir.id?c}"
                                    <#if cir.id == animatore.circolo.id>selected</#if>>${cir.nome} (${cir.luogo}
                                )
                            </option>
                        </#list>
                    </select>
                </div>

                <div class="form-group">
                    <label for="nTessera"> Numero tessera Circolo NOI </label>
                    <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera"
                           class="form-control" data-parsley-length="[11, 11]" aria-describedby="helpNTessera"
                           <#if animatore.nTessera??>value="${animatore.nTessera}"</#if>>
                    <small id="helpNTessera" class="form-text text-muted">
                        Inserire il numero di tessera del Circolo NOI se non si è iscritti al Circolo NOI
                        di Balconi.
                    </small>
                </div>

                <div class="form-group">
                    <label for="cellulare"> Numero di cellulare </label>
                    <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]"
                           required id="cellulare" name="cellulare" placeholder="Cellulare personale"
                           value="${animatore.cellulare}">
                </div>

                <div class="form-group">
                    <label> Fascia età ragazzi con cui vorrei compiere il servizio come animatore </label> <br/>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="PIC"
                               value="PIC" <#if animatore.fasciaEtaRagazzi == "PIC">checked</#if>>
                        <label class="form-check-label" for="PIC">Piccoli (1°,2°,3°,4° della scuola primaria)</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="GRA"
                               value="GRA" <#if animatore.fasciaEtaRagazzi == "GRA">checked</#if>>
                        <label class="form-check-label" for="GRA">Grandi (5° primaria, 1°,2° media)</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="TER"
                               value="TER" <#if animatore.fasciaEtaRagazzi == "TER">checked</#if>>
                        <label class="form-check-label" for="TER">Animatori gruppo 3° media</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="mail"> E-Mail personale </label>
                    <input class="form-control" type="email" required id="mail" name="mail" placeholder="E-Mail"
                           value="${animatore.mail}">
                </div>

                <div class="pt-3 form-group">
                    <input class="form-control btn btn-primary" type="submit" value="Modifica l'iscrizione">
                </div>

            </form>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
