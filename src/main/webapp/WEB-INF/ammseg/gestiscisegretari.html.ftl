<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pl-3 pr-3 pb-2 bg-white" responsive=false>
        <#--AMMINISTRATORI-->
        <h5 class="text-center pb-1"> Amministratori registrati </h5>
        <table class="table table-bordered">
            <@ui.tableHead columns=[
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Mail", "attributes": {"scope": "col"}}
            ]/>
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
        <#--SEGRETARI-->
        <#if segretari??>
            <h5 class="text-center pb-1"> Segretari registrati </h5>
            <#assign count = 0>
            <table class="table table-bordered">
                <@ui.tableHead columns=[
                {"label": "#", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Mail", "attributes": {"scope": "col"}},
                {"label": "Modifica", "attributes": {"scope": "col"}},
                {"label": "Elimina", "attributes": {"scope": "col", "style": "width: 2%;"}}
            ]/>
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
        <h5>Modifica permessi utente</h5>
        <form action="/RegistrazioneGrest/App/GestisciSegretari" class="form-inline">
            <input type="hidden" name="changeUserType"/>
            <@ui.inputField id="nome" label="Nome" labelClass="sr-only" groupClass="form-group m-2" placeholder="Nome" required=true/>
            <@ui.inputField id="cognome" label="Cognome" labelClass="sr-only" groupClass="form-group m-2" inputClass="form-control ml-2" placeholder="Cognome" required=true/>
            <div class="form-group m-2">
                <label for="level" class="sr-only">Tipo di utente</label>
                <select class="form-control ml-2" id="level" name="level">
                    <option value="1">Segretario con modifica</option>
                    <option value="2">Segretario normale</option>
                    <option value="3">Utente normale</option>
                    <option value="4">Disabilita profilo</option>
                </select>
            </div>
            <input type="submit" class="btn btn-primary m-2" value="Modifica"/>
        </form>
</@ui.contentPanel>
</@ui.page>