<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pl-3 pr-3 pb-2 bg-white" responsive=false>
        <#if animatori??>
            <form action="/RegistrazioneGrest/App/Squadre?target=an" method="POST">
                <#-- l'input nascosto indica che abbiamo usato la pagina per impostare le squadre-->
                <input type="hidden" name="submitted"/>
                <input type="submit" class="btn btn-primary" value="Salva"/>
                <table class="table table-striped" id="t_squadreani">
                    <@ui.tableHead columns=[
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Squadra", "attributes": {"scope": "col"}},
                {"label": "Responsabile", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
                    <tbody class="list">
                    <#list animatori as ani>
                        <tr>
                            <td>${ani.cognome?capitalize}</td>
                            <td>${ani.nome?capitalize}</td>
                            <td>
                                <@ui.assignmentSelect id="${ani.id?c}squadra" name="${ani.id?c}" label="Squadra" options=squadre selected=ani.squadra.id labelProperty="nome" emptyOption=true/>
                            </td>
                            <td class="text-center">
                                <@ui.responsabileToggle id="${ani.id?c}responsabileSquadra" name="${ani.id?c}" label="Responsabile squadra" checked=ani.responsabileSquadra/>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </form>
        </#if>
</@ui.contentPanel>
<@ui.assignmentScripts id="t_squadreani" responsible=true column=2/>
</@ui.page>