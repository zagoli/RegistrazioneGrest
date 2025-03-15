<#include "../struct/header.html.ftl">
<div class="container pt-2 content">
    <div class="title">
        <h2>Errore! Qualcosa non va...</h2>
    </div>
    <#if eccezione??>
        <p>Se il seguente errore persiste, contattare l'<a href="mailto:assistenzatecnica@parrocchiadibalconi.it">amministratore
                del sito</a>.</p>
        <div class="border border-danger rounded pt-2 pl-3 pr-3 pb-2 bg-white">
            <code>${eccezione.toString()}</code>
        </div>
    <#else>
        <p>Errore sconosciuto.</p>
    </#if>
</div>
<#include "../struct/footer.html.ftl">
