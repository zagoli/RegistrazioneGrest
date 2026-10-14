<#ftl strip_whitespace = true>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">presenze giornaliere animatori</h2>
    <@ui.reportTable>
            <@ui.tableHead columns=[
                {"label": "Anno", "attributes": {"style": "width:5%;"}},
                {"label": "Cognome", "attributes": {}},
                {"label": "Nome", "attributes": {}},
                {"label": "Periodo", "attributes": {}},
                {"label": "Lunedì", "attributes": {"colspan": "2"}},
                {"label": "Martedì", "attributes": {"colspan": "2"}},
                {"label": "Mercoledì", "attributes": {"colspan": "2"}},
                {"label": "Giovedì", "attributes": {"colspan": "2"}},
                {"label": "Venerdì", "attributes": {"colspan": "2"}}
            ]/>
            <tbody>
            <#list animatori as ani>
                <tr>
                    <td>${ani[0].dataNascita?string.yyyy}</td>
                    <td class="text-capitalize">${ani[0].cognome}</td>
                    <td class="text-capitalize">${ani[0].nome}</td>
                    <td>${ani[1]?trim}</td>
                    <@ui.presenceWeekCells/>
                </tr>
            </#list>
            </tbody>
        </@ui.reportTable>
</div>
</@ui.page>
<script> window.print() </script>