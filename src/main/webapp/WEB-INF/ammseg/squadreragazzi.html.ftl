<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pl-3 pr-3 pb-2 bg-white" responsive=false>
        <#if ragazzi??>
            <form action="/RegistrazioneGrest/App/Squadre?target=rag" method="POST">
                <#-- l'input nascosto indica che abbiamo usato la pagina per impostare le squadre-->
                <input type="hidden" name="submitted"/>
                <input type="submit" class="btn btn-primary" value="Salva"/>
                <table class="table table-striped" id="t_squadrerag">
                    <@ui.tableHead columns=[
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Classe", "attributes": {"scope": "col"}},
                {"label": "Squadra", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
                    <tbody class="list">
                    <#list ragazzi as rag>
                        <tr>
                            <td>${rag.cognome}</td>
                            <td>${rag.nome}</td>
                            <td>${rag.classe+rag.scuola.grado[0..0]?capitalize}</td>
                            <td>
                                <@ui.assignmentSelect id="squadra" name="squadra" label="Squadra" options=squadre selected=rag.squadra.id labelProperty="nome" pairedId=rag.id emptyOption=true/>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </form>
        </#if>
</@ui.contentPanel>
<@ui.assignmentScripts id="t_squadrerag" responsible=false column=3/>
</@ui.page>