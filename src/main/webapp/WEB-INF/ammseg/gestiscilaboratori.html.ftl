<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pl-3 pr-3 pb-2 bg-white" responsive=false>

        <#-- Aggiunta laboratorio -->
        <h5>Aggiungi un laboratorio</h5>

        <form action="/RegistrazioneGrest/App/GestisciLaboratori">
            <input type="hidden" name="add"/>
            <@ui.inputField id="nome" label="Nome laboratorio" placeholder="Nome laboratorio"/>
            <@ui.checkboxField id="riservato" label="Riservato agli animatori" labelClass="form-check-label"/>
            <button type="submit" class="btn btn-primary mt-2">Aggiungi</button>
        </form>

        <#-- Laboratori -->
        <#if laboratori??>
            <h5 class="mt-4 mb-2"> Laboratori </h5>
            <table class="table table-bordered">
                <@ui.tableHead columns=[
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Riservato agli animatori", "attributes": {"scope": "col"}},
                {"label": "", "attributes": {"scope": "col"}}
            ]/>
                <tbody>
                <#list laboratori as labConNumero>
                    <tr>
                        <td>${labConNumero.laboratorio.descrizione}</td>
                        <td><#if labConNumero.laboratorio.riservato>sì<#else>no</#if></td>
                        <td>
                            <#if (labConNumero.numeroIscritti > 0)>
                                <p>Questo laboratorio ha ${labConNumero.numeroIscritti} iscritti</p>
                            <#else>
                                <a href="/RegistrazioneGrest/App/GestisciLaboratori?delete&idLaboratorio=${labConNumero.laboratorio.id?c}">
                                    <button type="button" class="btn btn-danger">Elimina</button>
                                </a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
</@ui.contentPanel>
</@ui.page>