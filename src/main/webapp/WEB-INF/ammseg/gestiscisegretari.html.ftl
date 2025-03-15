<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container-fluid mt-5 content">
    <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
        <!--AMMINISTRATORI-->
        <h5 class="text-center pb-1"> Amministratori registrati </h5>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th scope="col">Nome</th>
                <th scope="col">Cognome</th>
                <th scope="col">Mail</th>
            </tr>
            </thead>
            <tbody>
            <#list amministratori as amm>
                <tr>
                    <td>${amm.nome}</td>
                    <td>${amm.cognome}</td>
                    <td>${amm.mail}</td>
                </tr>
            </#list>
            </tbody>
        </table>
        <!--SEGRETARI-->
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
                    <th scope="col">Modifica</th>
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
                        <td><#if seg.tipoUt == 1>sì<#elseif seg.tipoUt == 2>no</#if></td>
                        <td><a href="/RegistrazioneGrest/App/GestisciSegretari?del&id=${seg.id?c}"><img
                                        src="../img/octicons/trashcan.svg"></a></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
        <a href="/RegistrazioneGrest/App/RegistraUtente" class="btn btn-block btn-primary"> Registra un segretario </a>
        <hr/>
        <h5>Promuovi un utente</h5>
        <form action="/RegistrazioneGrest/App/GestisciSegretari" class="form-inline">
            <input type="hidden" name="promote"/>
            <div class="form-group m-2">
                <label for="nome" class="sr-only">Nome</label>
                <input type="text" placeholder="nome" name="nome" id="nome" class="form-control" required/>
            </div>
            <div class="form-group m-2">
                <label for="cognome" class="sr-only">Cognome</label>
                <input type="text" placeholder="cognome" name="cognome" id="cognome" class="form-control ml-2"
                       required/>
            </div>
            <div class="form-group m-2">
                <label for="level" class="sr-only">Tipo di utente</label>
                <select class="form-control ml-2" id="level" name="level">
                    <option value="1">Segretario con modifica</option>
                    <option value="2">Segretario normale</option>
                    <option value="3">Utente normale</option>
                </select>
            </div>
            <input type="submit" class="btn btn-primary m-2" value="promuovi"/>
        </form>
    </div>
</div>
<#include "../struct/footer.html.ftl">