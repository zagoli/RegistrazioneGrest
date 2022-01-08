<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-4 content">
        <#if NOCU??>
        <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-4 bg-warning">
            <p class="text-danger text-center font-weight-bold">Non sono ancora stati inseriti i contatti telefonici per le urgenze! Si prega di inserire <i>almeno</i> un contatto tramite la voce nel menu "Accompagnatori e Contatti telefonici Urgenze"!</p>
        </div>
        </#if>
        <div class="container shadow bg-white pt-2 pl-3 pr-3 pb-2">
            <#if ragazzi??>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col" style="width: 2%;"></th>
                        <th scope="col">Nome</th>
                        <th scope="col">Cognome</th>
                        <th scope="col" style="width: 15%;">Stampa scheda</th>
                        <th scope="col" style="width: 2%;">Pagato</th>
                        <th scope="col" style="width: 2%;">Modifica</th>
                        <th scope="col" style="width: 2%;">Elimina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list ragazzi as rag, pagato>
                        <tr>
                            <th scope="row">
                                <#if rag.squadra.id != 0>
                                    <div class="badge-squadra" style="background-color:${rag.squadra.colore}; "></div>
                                </#if>
                            </th>
                            <td>${rag.nome}</td>
                            <td>${rag.cognome}</td>
                            <td align="center"><a
                                        href="/RegistrazioneGrest/App/InfoDettaglio?target=schedarag&id=${rag.id}"><img
                                            src="../risorse/img/octicons/file.svg"></a></td>
                            <td align="center">
                                <#if pagato>
                                    <img src="../risorse/img/octicons/check.svg">
                                <#else>
                                    <img src="../risorse/img/octicons/x.svg">
                                </#if>
                            </td>
                                <td align="center"><#if !pagato><a href="/RegistrazioneGrest/App/ModificaRagazzo?id=${rag.id}"><img src="../risorse/img/octicons/pencil.svg"></a></#if></td>
                                <td align="center"><#if !pagato><a href="/RegistrazioneGrest/App/EliminaRagazzo?id=${rag.id}"><img src="../risorse/img/octicons/trashcan.svg"></a></#if></td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </#if>
            <a href="/RegistrazioneGrest/App/RegistraRagazzo" class="btn btn-block btn-primary">Iscrivi un ragazzo</a>
        </div>

        <div class="container shadow bg-white pt-2 pl-3 pr-3 pb-2 mt-4">
            <#if animatori??>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col" style="width: 2%;"></th>
                        <th scope="col">Nome</th>
                        <th scope="col">Cognome</th>
                        <th scope="col" style="width: 15%;">Stampa scheda</th>
                        <th scope="col" style="width: 2%;">Modifica</th>
                        <th scope="col" style="width: 2%;">Elimina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list animatori as ani>
                        <tr>
                            <th scope="row">
                                <#if ani.squadra.id != 0>
                                    <div class="badge-squadra" style="background-color:${ani.squadra.colore}; "></div>
                                </#if>
                            </th>
                            <td>${ani.nome}</td>
                            <td>${ani.cognome}</td>
                            <td align="center"><a
                                        href="/RegistrazioneGrest/App/InfoDettaglio?target=schedaani&id=${ani.id}"><img
                                            src="../risorse/img/octicons/file.svg"></a></td>
                            <td align="center"><#if ISCRAN><a
                                    href="/RegistrazioneGrest/App/ModificaAnimatore?id=${ani.id}"><img
                                                src="../risorse/img/octicons/pencil.svg"></a></#if></td>
                            <td align="center"><#if ISCRAN><a
                                    href="/RegistrazioneGrest/App/EliminaAnimatore?id=${ani.id}"><img
                                                src="../risorse/img/octicons/trashcan.svg"></a></#if></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </#if>
            <a href="/RegistrazioneGrest/App/RegistraAnimatore" class="btn btn-block btn-primary">Iscrivi un animatore</a>
        </div>
        
        <div class="container shadow bg-white pt-2 pl-3 pr-3 pb-2 mt-4">
            <#if terzamedia??>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col" style="width: 2%;"></th>
                        <th scope="col">Nome</th>
                        <th scope="col">Cognome</th>
                        <th scope="col" style="width: 15%;">Stampa scheda</th>
                        <th scope="col" style="width: 2%;">Pagato</th>
                        <th scope="col" style="width: 2%;">Modifica</th>
                        <th scope="col" style="width: 2%;">Elimina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list terzamedia as ter, pagato>
                        <tr>
                            <th scope="row">
                                <#if ter.squadra.id != 0>
                                    <div class="badge-squadra" style="background-color:${ter.squadra.colore}; "></div>
                                </#if>
                            </th>
                            <td>${ter.nome}</td>
                            <td>${ter.cognome}</td>
                            <td align="center"><a
                                        href="/RegistrazioneGrest/App/InfoDettaglio?target=schedater&id=${ter.id}"><img
                                            src="../risorse/img/octicons/file.svg"></a></td>
                            <td align="center">
                                <#if pagato>
                                    <img src="../risorse/img/octicons/check.svg">
                                <#else>
                                    <img src="../risorse/img/octicons/x.svg">
                                </#if>
                            </td>
                            <td align="center"><#if !pagato><a
                                    href="/RegistrazioneGrest/App/ModificaTerzamedia?id=${ter.id}"><img
                                                src="../risorse/img/octicons/pencil.svg"></a></#if></td>
                            <td align="center"><#if !pagato><a
                                    href="/RegistrazioneGrest/App/EliminaTerzamedia?id=${ter.id}"><img
                                                src="../risorse/img/octicons/trashcan.svg"></a></#if></td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </#if>
            <a href="/RegistrazioneGrest/App/RegistraTerzamedia" class="btn btn-block btn-primary">Iscrivi un ragazzo di terza media</a>
        </div>
    </div>
<#include "../struct/footer.html.ftl">