<#include "../struct/header.html.ftl">
<div class="container pt-2 content">
    <div class="title">
        <h2>Errore! Qualcosa non va...</h2>
    </div>
    <#if eccezione??>
        <p>Se il seguente errore persiste, contattare
            <a href="mailto:assistenzatecnica@parrocchiadibalconi.it?subject=${"Errore del sito del Grest!"}&body=${"Ho riscontrato il seguente errore utilizzando il sito del Grest: " + eccezione.toString()}">l'amministratore
                del sito.</a>
        </p>
    <#else>
        <p>Errore sconosciuto.</p>
    </#if>
</div>
<#include "../struct/footer.html.ftl">
