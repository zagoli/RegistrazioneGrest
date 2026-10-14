<#ftl strip_whitespace = true>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">Elenco mensa ${set.idSettimana?c} settimana</h2>
    <h5 class="text-uppercase text-center">Settimana dal ${set.daQuando} al ${set.AQuando} </h5>
    <@ui.reportTable wide=true>
                <@ui.tableHead columns=[
                {"label": "Cognome", "attributes": {}},
                {"label": "Nome", "attributes": {}},
                {"label": "Classe", "attributes": {"style": "width:5%;"}},
                {"label": "Periodo", "attributes": {}},
                {"label": "Allergia", "attributes": {"style": "width:25%;"}},
                {"label": "Squadra", "attributes": {}},
                {"label": "Laboratorio", "attributes": {}},
                {"label": "Telefoni urgenze", "attributes": {}}
            ]/>
                <tbody>
                <#list dati as rag>
                    <tr>
                        <td class="text-capitalize">${rag[0].cognome}</td>
                        <td class="text-capitalize">${rag[0].nome}</td>
                        <td >${rag[0].classe+rag[0].scuola.grado[0..0]?capitalize}</td>
                        <td> ${rag[1]?trim} </td>
                        <td> <#if rag[0].noteAlimentari??> ${rag[0].noteAlimentari} </#if> </td>
                        <td> <#if rag[0].squadra.id!=0> ${rag[0].squadra.nome} </#if> </td>
                        <td> ${rag[0].laboratorio.descrizione[0..*15]}...</td>
                        <td>
                            <@ui.reportEmergencyPhones registrato=rag[0].registrato contatti=rag[2]/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </@ui.reportTable>
</div>
</@ui.page>
<script> window.print() </script>