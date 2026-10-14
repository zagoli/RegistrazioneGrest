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

<#macro modificaAnnotazioni target id annotazioni=[]>
    <h5 class="mb-3 title text-center">Annotazioni della segreteria</h5>
    <form action="/RegistrazioneGrest/App/ModificaAnnotazioni" method="POST" class="card mb-3"
          data-parsley-validate>
        <input type="hidden" name="target" value="${target}"/>
        <input type="hidden" name="id" value="${id?c}"/>
        <input type="hidden" name="azione" value="aggiungi"/>
        <div class="card-body">
            <label for="nuova-annotazione-${target}-${id?c}" class="sr-only">Nuova annotazione</label>
            <textarea class="form-control" id="nuova-annotazione-${target}-${id?c}" name="annotazione"
                      rows="5" maxlength="5000" required data-parsley-maxlength="5000"
                      placeholder="Scrivi la tua annotazione..."
                      aria-describedby="limite-annotazione-${target}-${id?c}"></textarea>
            <small id="limite-annotazione-${target}-${id?c}" class="form-text text-muted">Massimo 5000 caratteri.</small>
        </div>
        <div class="card-footer text-right">
            <button type="submit" class="btn btn-primary">Salva</button>
        </div>
    </form>
    <#list annotazioni as annotazione>
        <@nota annotazione=annotazione/>
    </#list>
</#macro>

<#macro nota annotazione>
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
