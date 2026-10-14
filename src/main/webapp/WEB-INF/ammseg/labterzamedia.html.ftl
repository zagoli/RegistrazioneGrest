<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pl-3 pr-3 pb-2 bg-white" responsive=false>
        <#if terzamedia??>
            <form action="/RegistrazioneGrest/App/Laboratori?target=ter" method="POST">
                <#-- l'input nascosto indica che abbiamo usato la pagina per impostare i laboratori-->
                <input type="hidden" name="submitted"/>
                <input type="submit" class="btn btn-primary" value="Salva"/>
                <table class="table table-striped" id="t_labter">
                    <@ui.tableHead columns=[
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Laboratorio", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
                    <tbody class="list">
                    <#list terzamedia as ter>
                        <tr>
                            <td>${ter.cognome}</td>
                            <td>${ter.nome}</td>
                            <td>
                                <@ui.assignmentSelect id="laboratorio" name="laboratorio" label="Laboratorio" options=laboratori selected=ter.laboratorio.id labelProperty="descrizione" pairedId=ter.id/>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </form>
        </#if>
</@ui.contentPanel>
<@ui.assignmentScripts id="t_labter" responsible=false column=2/>
</@ui.page>