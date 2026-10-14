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

<#macro modificaAnnotazioni tipoIscritto id annotazioni=[]>
    <h5 class="mb-3 title text-center" id="annotazioni">Annotazioni della segreteria</h5>
    <form action="/RegistrazioneGrest/App/ModificaAnnotazioni" method="POST" class="card mb-3">
        <input type="hidden" name="tipoIscritto" value="${tipoIscritto}"/>
        <input type="hidden" name="idIscritto" value="${id?c}"/>
        <input type="hidden" name="add"/>
        <div class="card-body" style="padding: 0;">
            <label for="nuova-annotazione" class="sr-only">Nuova annotazione</label>
            <textarea
                    style="width: 100%; border: none; border-bottom-left-radius: 0; border-bottom-right-radius: 0;"
                    class="form-control" id="nuova-annotazione" name="annotazione"
                    rows="5" maxlength="5000" required
                    placeholder="Scrivi la tua annotazione. Massimo 5000 caratteri."></textarea>
        </div>
        <div class="card-footer text-right" style="padding-top: .5rem; padding-bottom: .5rem;">
            <button type="submit" class="btn btn-primary">Aggiungi</button>
        </div>
    </form>
    <#list annotazioni as annotazione>
        <@nota
        annotazione=annotazione
        puoEliminare=(tipoUt == 0 || annotazione.segretario.id == idUtente)
        tipoIscritto=tipoIscritto
        idIscritto=id
        />
    </#list>
</#macro>

<#macro nota annotazione puoEliminare=false tipoIscritto="" idIscritto=0>
    <article class="card mb-3">
        <div class="card-header d-flex flex-wrap justify-content-between align-items-center">
            <strong class="mr-3">${annotazione.segretario.nome?capitalize} ${annotazione.segretario.cognome?capitalize}</strong>
            <span class="d-inline-flex align-items-center">
                <time class="text-muted"
                      title="Data creazione">${annotazione.dataCreazione?string["dd/MM/yyyy HH:mm"]}</time>
                <#if puoEliminare>
                    <a href="/RegistrazioneGrest/App/ModificaAnnotazioni?del&id=${annotazione.id?c}&tipoIscritto=${tipoIscritto}&idIscritto=${idIscritto?c}"
                       class="d-inline-flex align-items-center ml-2"
                       title="Elimina">
                        <img src="../img/octicons/trashcan.svg" alt="elimina" style="width: 16px; height: 16px;"/>
                    </a>
                </#if>
            </span>
        </div>
        <div class="card-body">
            <p class="card-text" style="white-space: pre-wrap; overflow-wrap: anywhere;">${annotazione.annotazione}</p>
        </div>
    </article>
</#macro>
