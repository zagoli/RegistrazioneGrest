<#ftl strip_whitespace = true>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">Collaborazione Genitori</h2>
    <@ui.reportTable wide=true>
                <@ui.tableHead columns=[
                {"label": "Genitore", "attributes": {}},
                {"label": "Ragazzo", "attributes": {}},
                {"label": "Periodo", "attributes": {}},
                {"label": "Cellulare", "attributes": {}},
                {"label": "Collaborazione", "attributes": {}},
                {"label": "Data", "attributes": {}}
            ]/>
                <tbody>
                <#list dati as dt>
                    <tr>
                        <td class="text-capitalize">${dt[2].cognome?capitalize+" "+dt[2].nome?capitalize}</td>
                        <td class="text-capitalize"><#if dt[3]??>${dt[3].cognome?capitalize+" "+dt[3].nome?capitalize}</#if></td>
                        <td><#if dt[4]??><#list dt[4] as cal>${cal.idSettimana+" "}</#list></#if></td>
                        <td> ${dt[2].telefono!""} </td>
                        <td> ${dt[1].descrizione} </td>
                        <td> ${dt[0].data!"Non specificato"}</td>
                    </tr>
                </#list>
                </tbody>
            </@ui.reportTable>
</div>
</@ui.page>
<script> window.print() </script>