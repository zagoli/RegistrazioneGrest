<#import "../struct/iscrizione.html.ftl" as iscrizione>
<#import "../struct/avvisiValidazione.html.ftl" as avvisiValidazione>
<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container pt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Modifica account</h5>
            </div>
            <#if INVALIDMAIL??>
                <@avvisiValidazione.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaUtente" method="POST" data-parsley-validate>
                <@iscrizione.datiAccount registrato=registrato/>
                <div class="pt-3 form-group">
                    <input class="form-control btn btn-primary" type="submit" value="Conferma">
                </div>
            </form>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
