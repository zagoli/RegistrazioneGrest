<#include "../struct/header.html.ftl">
<div class="container pt-2 content">
    <div class="title">
        <h2>Errore! Qualcosa non va...</h2>
    </div>
    <#if codiceErrore??>
        <p>Codice di riferimento: <strong>${codiceErrore?html}</strong></p>
        <p>Data e ora dell'errore: <strong>${dataOraErrore?html}</strong></p>
        <p>Se l'errore persiste, contattare
            <a href="mailto:assistenzatecnica@parrocchiadibalconi.it?subject=${"Errore del sito del Grest!"?url('UTF-8')}&amp;body=${("Ho riscontrato un errore utilizzando il sito del Grest.\nCodice di riferimento: " + codiceErrore + "\nData e ora dell'errore: " + dataOraErrore)?url('UTF-8')}">l'amministratore
                del sito.</a>
        </p>
    <#else>
        <p>Errore sconosciuto.</p>
    </#if>
</div>
<#include "../struct/footer.html.ftl">
