<#ftl strip_whitespace = true>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">Elenco sintesi terzamedia</h2>
    <@ui.reportTable wide=true>
                <@ui.tableHead columns=[
                {"label": "Cognome", "attributes": {}},
                {"label": "Nome", "attributes": {}},
                {"label": "Periodo", "attributes": {}},
                {"label": "Squadra", "attributes": {}},
                {"label": "Laboratorio", "attributes": {}},
                {"label": "Cellulare", "attributes": {}},
                {"label": "Telefoni urgenze", "attributes": {}}
            ]/>
                <tbody>
                <#list terzamedia as ter>
                    <tr>
                        <td class="text-capitalize">${ter[0].cognome}</td>
                        <td class="text-capitalize">${ter[0].nome}</td>
                        <td> ${ter[1]?trim} </td>
                        <td> <#if ter[0].squadra.id!=0> ${ter[0].squadra.nome} </#if> </td>
                        <td> ${ter[0].laboratorio.descrizione[0..*15]}...</td>
                        <td> <#if ter[0].cellulare??> ${ter[0].cellulare} </#if> </td>
                        <td>
                            <@ui.reportEmergencyPhones registrato=ter[0].registrato contatti=ter[2]/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </@ui.reportTable>
</div>
</@ui.page>
<script> window.print() </script>