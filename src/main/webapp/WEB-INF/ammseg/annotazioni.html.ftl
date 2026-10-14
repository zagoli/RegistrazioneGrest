<#ftl encoding="UTF-8" output_format="HTML" auto_esc=true>

<#macro pannelloInfoDettaglio annotazioni=[]>
    <#if annotazioni?size != 0>
        <div class="shadow py-2 px-3 mt-3 bg-white">
            <p class="mb-2"><strong>Annotazioni della segreteria</strong></p>
            <#list annotazioni as annotazione>
                <@nota annotazione=annotazione/>
            </#list>
        </div>
    </#if>
</#macro>

<#macro modificaAnnotazioni annotazioni=[]>
    <h5 class="mb-3 title text-center">Annotazioni della segreteria</h5>
    <#list annotazioni as annotazione>
        <@nota annotazione=annotazione modifica=true/>
    </#list>
</#macro>

<#macro nota annotazione modifica=false>
    <article class="card mb-3">
        <div class="card-header d-flex flex-wrap justify-content-between align-items-center">
            <strong class="mr-3">${annotazione.segretario.nome?capitalize} ${annotazione.segretario.cognome?capitalize}</strong>
            <time class="text-muted" title="Data creazione">${annotazione.dataCreazione?string["dd/MM/yyyy HH:mm"]}</time>
        </div>
        <div class="card-body">
            <p class="card-text" style="white-space: pre-wrap; overflow-wrap: anywhere;">${annotazione.annotazione}</p>
        </div>
    </article>
</#macro>
