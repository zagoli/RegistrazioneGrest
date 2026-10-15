<#macro anagraficaIscritto iscritto capitalize=false>
    <div class="row">
        <div class="col-sm">
            <strong>Nome</strong>
        </div>
        <div class="col-sm">
            <p><#if capitalize>${iscritto.nome?capitalize}<#else>${iscritto.nome}</#if></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm">
            <strong>Cognome</strong>
        </div>
        <div class="col-sm">
            <p><#if capitalize>${iscritto.cognome?capitalize}<#else>${iscritto.cognome}</#if></p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm">
            <strong>Data di nascita</strong>
        </div>
        <div class="col-sm">
            <p>${iscritto.dataNascita?string["dd/MM/yyyy"]}</p>
        </div>
    </div>
</#macro>

<#macro presenzaIscritto iscritto animatore=false>
    <div class="row">
        <div class="col-sm">
            <strong>Presenza giornaliera<#if animatore> al Grest</#if></strong>
        </div>
        <div class="col-sm">
            <p>
                <#if iscritto.presenza == "M">
                    Mattina
                <#elseif iscritto.presenza == "P">
                    Pomeriggio
                <#elseif iscritto.presenza == "C">
                    Completo
                </#if>
            </p>
        </div>
    </div>
</#macro>

<#macro laboratorioIscritto iscritto>
    <div class="row">
        <div class="col-sm">
            <strong>Laboratorio</strong>
        </div>
        <div class="col-sm">
            <p>${iscritto.laboratorio.descrizione}</p>
        </div>
    </div>
</#macro>

<#macro appartenenzaIscritto iscritto>
    <div class="row">
        <div class="col-sm">
            <strong>Parrocchia</strong>
        </div>
        <div class="col-sm">
            <p>${iscritto.parrocchia.nome+" - "+iscritto.parrocchia.luogo}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm">
            <strong>Circolo NOI</strong>
        </div>
        <div class="col-sm">
            <p>${iscritto.circolo.nome+" - "+iscritto.circolo.luogo}</p>
        </div>
    </div>
</#macro>

<#macro tesseraIscritto iscritto>
    <#if iscritto.NTessera??>
        <div class="row">
            <div class="col-sm">
                <strong>Numero tessera Circolo NOI</strong>
            </div>
            <div class="col-sm">
                <p>${iscritto.NTessera}</p>
            </div>
        </div>
    </#if>
</#macro>

<#macro presenzaSettimanale calendari>
    <div class="row">
        <div class="col-sm">
            <strong>Presenza settimanale</strong>
        </div>
        <div class="col-sm">
            <#list calendari as cal>
                <p>dal ${cal.daQuando+" al "+cal.AQuando}</p>
            </#list>
        </div>
    </div>
</#macro>
