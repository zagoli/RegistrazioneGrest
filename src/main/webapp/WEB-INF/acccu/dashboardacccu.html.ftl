<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container-fluid mt-5 content">
    <!--TABELLE ACCOMPAGNATORI CONTATTI-->
    <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
        <#if accompagnatori??>
            <h5 class="text-center pb-1"> Accompagnatori autorizzati </h5>
            <#assign count = 0>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th scope="col" style="width: 2%;">#</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col" style="width: 2%;">Modifica</th>
                    <th scope="col" style="width: 2%;">Elimina</th>
                </tr>
                </thead>
                <tbody>
                <#list accompagnatori as acc>
                    <#assign count = count + 1>
                    <tr>
                        <th scope="row">${count}</th>
                        <td>${acc.nome}</td>
                        <td>${acc.cognome}</td>
                        <td><a href="/RegistrazioneGrest/App/ModificaAccompagnatore?id=${acc.id?c}"><img
                                        src="../img/octicons/pencil.svg"></a></td>
                        <td><a href="/RegistrazioneGrest/App/EliminaAccompagnatore?id=${acc.id?c}"><img
                                        src="../img/octicons/trashcan.svg"></a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
        <a href="/RegistrazioneGrest/App/InserisciAccompagnatore" class="btn btn-block btn-primary">Autorizza un
            accompagnatore</a>
    </div>

    <div class="container shadow pt-2 pl-3 pr-3 pb-2 mt-4 bg-white">
        <#if contatti??>
            <h5 class="text-center pb-1"> Contatti telefonici urgenze </h5>
            <#assign count = 0>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th scope="col" style="width: 2%;">#</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Cellulare</th>
                    <th scope="col">Fisso</th>
                    <th scope="col">Relazione</th>
                    <th scope="col" style="width: 2%;">Modifica</th>
                    <th scope="col" style="width: 2%;">Elimina</th>
                </tr>
                </thead>
                <tbody>
                <#list contatti as cu>
                    <#assign count = count + 1>
                    <tr>
                        <th scope="row">${count}</th>
                        <td>${cu.nome}</td>
                        <td>${cu.cognome}</td>
                        <td>${cu.cellulare}</td>
                        <td>${cu.fisso}</td>
                        <td>${cu.relazione}</td>
                        <td><a href="/RegistrazioneGrest/App/ModificaCU?id=${cu.id?c}"><img
                                        src="../img/octicons/pencil.svg"></a></td>
                        <td><a href="/RegistrazioneGrest/App/EliminaCU?id=${cu.id?c}"><img
                                        src="../img/octicons/trashcan.svg"></a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
        <a href="/RegistrazioneGrest/App/InserisciCU" class="btn btn-block btn-primary">Inserisci un contatto telefonico
            urgenze</a>
    </div>

</div>
<#include "../struct/footer.html.ftl">