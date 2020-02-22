<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
            <div class="container border border-secondary rounded pt-2 pl-3 pr-3 pb-2 bg-light">
                <#if segretari??>
                    <h5 class="text-center pb-1"> Segretari registrati </h5>
                    <#assign count = 0>
                    <table class="table table-striped">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col" style="width: 2%">#</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Cognome</th>
                                <th scope="col">Mail</th>
                                <th scope="col" style="width: 2%">Elimina</th>
                            </tr>
                        </thead>
                        <tbody>
                            <#list segretari as seg>
                                <#assign count = count + 1>
                                <tr>
                                    <th scope="row">${count}</th>
                                    <td>${seg.nome}</td>
                                    <td>${seg.cognome}</td>
                                    <td>${seg.mail}</td>
                                    <td><a href="/RegistrazioneGrest/App/GestisciSegretari?del&id=${seg.id}"><img src="../risorse/img/octicons/trashcan.svg"></a></td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </#if>
                <a href="/RegistrazioneGrest/App/RegistraUtente" class="btn btn-block btn-primary"><img src="../risorse/img/octicons/plus.svg"> Registra un segretario </a>
            </div>
    </div>
<#include "../struct/footer.html.ftl">