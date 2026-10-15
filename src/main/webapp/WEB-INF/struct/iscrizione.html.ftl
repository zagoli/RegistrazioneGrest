<#macro anagraficaIscritto tipo modifica=false iscritto={} registrato={} dataNascita="">
    <#local ordine=(tipo == "animatore" && modifica)?then(["nome", "cognome"], ["cognome", "nome"])>
    <#list ordine as campo>
        <div class="form-group">
            <label for="${campo}">${campo?cap_first}</label>
            <input class="form-control" type="text" required id="${campo}" name="${campo}" placeholder="${campo?cap_first}"<#if campo == "nome"> autofocus</#if>
                   data-parsley-length="[2, 50]"<#if modifica> value="${iscritto[campo]}"<#elseif tipo == "animatore"> value="${registrato[campo]}"</#if>>
        </div>
    </#list>
    <div class="form-group">
        <label for="dataNascita">Data di nascita</label>
        <input class="form-control" type="date" required id="dataNascita" name="dataNascita"<#if modifica> value="${dataNascita}"</#if>>
    </div>
</#macro>

<#macro presenzaGiornaliera tipo selezionata="C">
    <div class="form-group">
        <label>Presenza giornaliera<#if tipo == "animatore"> al Grest</#if></label> <br/>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="presenza" id="completo" value="C"<#if selezionata == "C"> checked</#if>>
            <label class="form-check-label" for="completo"><#if tipo == "animatore">Completo<#else>Mattina e pomeriggio</#if></label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="presenza" id="mattina" value="M"<#if selezionata == "M"> checked</#if>>
            <label class="form-check-label" for="mattina">Solo mattina</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="presenza" id="pomeriggio" value="P"<#if selezionata == "P"> checked</#if>>
            <label class="form-check-label" for="pomeriggio">Solo pomeriggio</label>
        </div>
    </div>
</#macro>

<#macro partecipazioneGrest tipo calendari modifica=false>
    <div class="form-group">
        <label><#if tipo == "animatore">Periodo di partecipazione<#else>Partecipazione</#if></label><#if modifica || tipo == "animatore"> <br/></#if>
        <#if modifica>
            <#list calendari as cal, partecipa>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="${cal.idSettimana?c}" id="cal${cal.idSettimana?c}" name="cal"<#if partecipa> checked</#if>>
                    <label class="form-check-label" for="cal${cal.idSettimana?c}">Settimana dal ${cal.daQuando} al ${cal.AQuando}</label>
                </div>
            </#list>
        <#else>
            <#list calendari as cal>
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" value="${cal.idSettimana?c}" id="cal${cal.idSettimana?c}" name="cal" checked>
                    <label class="form-check-label" for="cal${cal.idSettimana?c}">Settimana dal ${cal.daQuando} al ${cal.AQuando}</label>
                </div>
            </#list>
        </#if>
        <#if tipo != "animatore">
            <a class="btn btn-secondary btn-sm mt-1" href="javascript:;"
               onClick="window.open('/RegistrazioneGrest/App/VisualizzaQuote?${(tipo == "ragazzo")?then("ragazzi", "terzamedia")}', '_blank', 'width=1000, height=${(tipo == "ragazzo")?then("900", "600")}, status=yes, scrollbars=no, location=yes');">clicca
                qui per consultare le quote d'iscrizione</a>
        </#if>
    </div>
</#macro>

<#macro laboratorioIscritto tipo laboratori modifica=false iscritto={}>
    <div class="form-group">
        <label for="laboratorio"<#if !modifica && tipo != "animatore"> class="sr-only"</#if>><#if !modifica && tipo == "animatore">Selezionare un laboratorio<#else>Laboratorio</#if></label>
        <select id="laboratorio" class="form-control" name="laboratorio"<#if !modifica && tipo != "animatore"> aria-describedby="helpLaboratorio"</#if>>
            <#list laboratori as lab>
                <option value="${lab.id?c}"<#if modifica && lab.id == iscritto.laboratorio.id> selected</#if>>${lab.descrizione}</option>
            </#list>
        </select>
    </div>
</#macro>

<#macro parrocchiaIscritto parrocchie selezionata=0>
    <div class="form-group">
        <label for="parrocchia">Parrocchia di appartenenza</label>
        <select id="parrocchia" class="form-control" name="parrocchia">
            <#list parrocchie as par>
                <option value="${par.id?c}"<#if par.id == selezionata> selected</#if>>Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
            </#list>
        </select>
    </div>
</#macro>

<#macro tesseramentoIscritto tipo circoli modifica=false iscritto={}>
    <div class="form-group">
        <label for="circolo"><#if tipo == "animatore"><#if modifica>Circolo NOI di iscrizione<#else>Tesseramento Circolo NOI</#if> <b><script type="text/javascript">var theDate = new Date();
                                document.write(theDate.getFullYear());</script></b><#else>Circolo di tesseramento</#if></label>
        <select id="circolo" class="form-control" name="circolo">
            <#list circoli as cir>
                <option value="${cir.id?c}"<#if modifica && cir.id == iscritto.circolo.id> selected</#if>>${cir.nome} (${cir.luogo}<#if tipo == "animatore" && modifica> </#if>)</option>
            </#list>
        </select>
    </div>
    <div class="form-group">
        <label for="nTessera">Numero tessera Circolo <#if tipo == "animatore">NOI<#else>Noi <b><script type="text/javascript">var theDate = new Date();
                                document.write(theDate.getFullYear());</script></b></#if></label>
        <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control"
               data-parsley-length="[11, 11]" aria-describedby="helpNTessera"<#if modifica && iscritto.NTessera??> value="${iscritto.NTessera}"</#if>>
        <small id="helpNTessera" class="form-text text-muted">
            Inserire il numero di tessera del Circolo <#if tipo == "animatore">NOI<#else>Noi</#if> se non si è <#if !modifica && tipo != "animatore">tesserati<#else>iscritti</#if> al Circolo <#if tipo == "animatore">NOI<#else>Noi</#if> di Balconi.
        </small>
    </div>
</#macro>

<#macro scuolaIscritto tipo scuole modifica=false iscritto={}>
    <div class="form-group">
        <label for="scuola">Scuola</label>
        <select id="scuola" class="form-control" name="scuola">
            <#list scuole as scu>
                <option value="${scu.id?c}"<#if modifica && scu.id == iscritto.scuola.id> selected</#if>>Scuola ${scu.grado} di ${scu.descrizione}</option>
            </#list>
        </select>
    </div>
    <#if tipo == "ragazzo">
        <div class="form-group">
            <label for="classe">Classe</label>
            <select id="classe" class="form-control" name="classe">
                <#list 1..5 as classe>
                    <option value="${classe?c}"<#if modifica && iscritto.classe == classe?c> selected</#if>>${classe?c}</option>
                </#list>
            </select>
        </div>
    </#if>
    <div class="form-group">
        <label for="sezione"><#if tipo == "terzamedia" && !modifica>frequentante la classe terza media , sezione<#else>Sezione</#if></label>
        <input class="form-control" type="text" required id="sezione" name="sezione"
               data-parsley-length="<#if tipo == "terzamedia">[1, 1]<#else>[1, 5]</#if>" placeholder="<#if tipo == "terzamedia">A<#elseif modifica>Classe<#else>sezione</#if>"<#if modifica> value="${iscritto.sezione}"</#if>>
    </div>
</#macro>

<#macro fasciaEtaAnimatore selezionata="PIC">
    <div class="form-group">
        <label>Fascia età ragazzi con cui vorrei compiere il servizio come animatore</label> <br/>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="PIC" value="PIC"<#if selezionata == "PIC"> checked</#if>>
            <label class="form-check-label" for="PIC">Piccoli (1°,2°,3°,4° della scuola primaria)</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="GRA" value="GRA"<#if selezionata == "GRA"> checked</#if>>
            <label class="form-check-label" for="GRA">Grandi (5° primaria, 1°,2° media)</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="TER" value="TER"<#if selezionata == "TER"> checked</#if>>
            <label class="form-check-label" for="TER">Animatori gruppo 3° media</label>
        </div>
    </div>
</#macro>

<#macro datiAccount registrato=false tipoUt=3>
    <#local modifica=registrato?is_hash>
    <div class="form-group">
        <label for="nome">Nome</label>
        <input class="form-control" type="text" id="nome" required name="nome" placeholder="Nome" autofocus
               data-parsley-length="[2, 50]"<#if modifica> value="${registrato.nome}"</#if>>
    </div>
    <div class="form-group">
        <label for="cognome">Cognome</label>
        <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome"
               data-parsley-length="[2, 50]"<#if modifica> value="${registrato.cognome}"</#if>>
    </div>
    <div class="form-group">
        <label for="email">E-Mail</label>
        <input class="form-control" type="email" required id="email" name="mail" placeholder="E-Mail"<#if modifica> value="${registrato.mail}"</#if>>
    </div>
    <div class="form-group">
        <label for="telefono">Numero di telefono</label>
        <input class="form-control" data-parsley-type="digits" data-parsley-length="<#if modifica>[10,10]<#else>[10, 10]</#if>"
               <#if tipoUt == 3>required</#if> id="telefono" name="telefono" placeholder="Cellulare personale"<#if modifica && registrato.telefono??> value="${registrato.telefono}"</#if>>
    </div>
    <div class="form-group">
        <label for="localita"><#if modifica>Localita<#else>Località</#if></label>
        <input class="form-control" type="text"<#if tipoUt == 3> required</#if> id="localita" name="localita"
               placeholder="Localit&agrave"<#if modifica && registrato.localita??> value="${registrato.localita}"</#if>>
    </div>
    <div class="form-group">
        <label for="via">Via</label>
        <input class="form-control" type="text"<#if tipoUt == 3> required</#if> id="via" name="via"
               placeholder="Via"<#if modifica && registrato.via??> value="${registrato.via}"</#if>>
    </div>
    <div class="form-group">
        <label for="civico">Numero civico</label>
        <input class="form-control" type="text"<#if tipoUt == 3> required</#if> id="civico" name="civico"
               placeholder="Numero Civico"<#if modifica && registrato.civico??> value="${registrato.civico}"</#if>>
    </div>
</#macro>
