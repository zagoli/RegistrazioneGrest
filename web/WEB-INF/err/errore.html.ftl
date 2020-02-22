<#include "../struct/header.html.ftl">
    <div class="container pt-2 content">
        <div class="title">
            <h2>Errore! Qualcosa non va...</h2>
        </div>
        <!--errore mail già presente nel database-->
        <#if eccezione?? && eccezione.getMessage()?starts_with("Violation of UNIQUE KEY constraint 'Unmail'")>
            <div class="border border-warning rounded pt-2 pl-3 pr-3 pb-2 bg-light mt-5">
                <h3 class="font-weight-bold text-warning" align="center">Un utente ha gi&agrave; utilizzato questa mail in fase di registrazione!</h3>    
            </div>
        <#elseif eccezione??>
        <p>Se il seguente errore persiste, riportarlo all'amministratore del sito.</p>
        <div class="border border-danger rounded pt-2 pl-3 pr-3 pb-2 bg-light">
                <code>${eccezione.toString()}</code>   
        </div>
        <#else>
        <p>Errore sconosciuto.</p>
        </#if>  
    </div>
<#include "../struct/footer.html.ftl">
