<#ftl encoding="UTF-8" output_format="HTML" auto_esc=true>

<#macro pannelloInfoDettaglio annotazioni=[]>
    <#if annotazioni?size != 0>
        <div class="shadow pt-2 pl-3 pr-3 mt-3 bg-white">
            <div class="row mb-3">
                <div class="col-sm">
                    <strong>Annotazioni della segreteria</strong>
                </div>
            </div>
            <#list annotazioni as nota>
                <div class="row">
                    <div class="col-sm">
                        <p>${nota.segretario.nome?capitalize} ${nota.segretario.cognome?capitalize}</p>
                        <time class="text-muted" title="Data creazione">${nota.dataCreazione?string["dd/MM/yyyy HH:mm"]}</time>
                    </div>
                    <div class="col-sm">
                        <p>${nota.annotazione}</p>
                    </div>
                </div>
                <hr/>
            </#list>
        </div>
    </#if>
</#macro>

<#--<article class="card mb-3">-->
<#--    <div class="card-header d-flex flex-wrap justify-content-between align-items-center">-->
<#--        <strong class="mr-3">${nota.segretario.nome?capitalize} ${nota.segretario.cognome?capitalize}</strong>-->
<#--        <time class="text-muted" datetime="${nota.dataCreazione?string["yyyy-MM-dd'T'HH:mm:ss"]}" title="Data creazione">${nota.dataCreazione?string["dd/MM/yyyy HH:mm"]}</time>-->
<#--    </div>-->
<#--    <div class="card-body">-->
<#--        <p class="card-text" style="white-space: pre-wrap; overflow-wrap: anywhere;">${nota.annotazione}</p>-->
<#--    </div>-->
<#--</article>-->
