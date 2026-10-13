<#ftl encoding="UTF-8" output_format="HTML" auto_esc=true>
<#-- Uso: <#import "annotazioni.html.ftl" as note> <@note.pannello annotazioni=annotazioni![] /> -->
<#macro pannello annotazioni=[]>
    <#if annotazioni?has_content>
        <section class="shadow pt-2 pl-3 pr-3 pb-1 mt-3 bg-white" aria-label="Annotazioni della segreteria">
            <h2 class="h6 mb-3 font-weight-bold">Annotazioni della segreteria</h2>
            <#list annotazioni as nota>
                <article class="card mb-3">
                    <div class="card-header d-flex flex-wrap justify-content-between align-items-center">
                        <strong class="mr-3">${nota.segretario.nome?capitalize} ${nota.segretario.cognome?capitalize}</strong>
                        <time class="text-muted" datetime="${nota.dataCreazione?string["yyyy-MM-dd'T'HH:mm:ss"]}" title="Data creazione">${nota.dataCreazione?string["dd/MM/yyyy HH:mm"]}</time>
                    </div>
                    <div class="card-body">
                        <p class="card-text" style="white-space: pre-wrap; overflow-wrap: anywhere;">${nota.annotazione}</p>
                    </div>
                </article>
            </#list>
        </section>
    </#if>
</#macro>
