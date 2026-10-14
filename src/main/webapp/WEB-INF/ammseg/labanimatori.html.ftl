<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pl-3 pr-3 pb-2 bg-white" responsive=false>
        <#if animatori??>
            <form action="/RegistrazioneGrest/App/Laboratori?target=an" method="POST">
                <#-- l'input nascosto indica che abbiamo usato la pagina per impostare i laboratori-->
                <input type="hidden" name="submitted"/>
                <input type="submit" class="btn btn-primary" value="Salva"/>
                <table class="table table-striped" id="t_labani">
                    <@ui.tableHead columns=[
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Laboratorio", "attributes": {"scope": "col"}},
                {"label": "Responsabile", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
                    <tbody class="list">
                    <#list animatori as an>
                        <tr>
                            <td>${an.cognome?capitalize}</td>
                            <td>${an.nome?capitalize}</td>
                            <td>
                                <@ui.assignmentSelect id="${an.id?c}laboratorio" name="${an.id?c}" label="Laboratorio" options=laboratori selected=an.laboratorio.id labelProperty="descrizione"/>
                            </td>
                            <td class="text-center">
                                <@ui.responsabileToggle id="${an.id?c}responsabileLaboratorio" name="${an.id?c}" label="Responsabile laboratorio" checked=an.responsabileLaboratorio/>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </form>
        </#if>
</@ui.contentPanel>
<@ui.assignmentScripts id="t_labani" responsible=true column=2/>
</@ui.page>