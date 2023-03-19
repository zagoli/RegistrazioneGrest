<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
            <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
                <#if segretari??>
                    <h5 class="text-center pb-1"> Segretari registrati </h5>
                    <#assign count = 0>
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th scope="col" style="width: 2%;">#</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Cognome</th>
                            <th scope="col">Mail</th>
                            <th scope="col" style="width: 2%;">Elimina</th>
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
                                <td><a href="/RegistrazioneGrest/App/GestisciSegretari?del&id=${seg.id?c}"><img
                                                src="../risorse/img/octicons/trashcan.svg"></a></td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </#if>
                <a href="/RegistrazioneGrest/App/RegistraUtente" class="btn btn-block btn-primary"> Registra un segretario </a>
                <hr/>
                <h5>promuovi un utente</h5>
                <form action="/RegistrazioneGrest/App/GestisciSegretari" class="form-inline">
                    <input type="hidden" name="promote"/>
                    <input type="text" placeholder="nome" name="nome" class="form-control" required/>
                    <input type="text" placeholder="cognome" name="cognome" class="form-control ml-2" required/>
                    <select class="form-control ml-2" name="level">
                        <option value="1">Segretario con modifica</option>
                        <option value="2">Segretario normale</option>
                        <option value="3">Utente normale</option>
                    </select>
                    <input type="submit" class="btn btn-primary ml-2" value="promuovi"/>
                </form>
            </div>
    </div>
<#include "../struct/footer.html.ftl">