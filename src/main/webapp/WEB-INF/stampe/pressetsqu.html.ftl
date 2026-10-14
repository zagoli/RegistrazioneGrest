<#ftl strip_whitespace = true>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="mt-4 content">
    <h2 class="text-uppercase text-center"> presenze settimanali <span
                style="color:${squadra.colore}; ">${squadra.nome}</span></h2>
    <h5 class="text-uppercase text-center font-italic">settimana numero ${settimana.idSettimana?c}
        <small>(dal ${settimana.daQuando} al ${settimana.AQuando})</small></h5>
    <@ui.reportTable>
            <@ui.tableHead columns=[
                {"label": "Ragazzo", "attributes": {}},
                {"label": "Classe", "attributes": {"style": "width:5%;"}},
                {"label": "Laboratorio", "attributes": {}},
                {"label": "Periodo", "attributes": {}},
                {"label": "Mensa", "attributes": {"style": "width:5%;"}},
                {"label": "Lunedì", "attributes": {"colspan": "2"}},
                {"label": "Martedì", "attributes": {"colspan": "2"}},
                {"label": "Mercoledì", "attributes": {"colspan": "2"}},
                {"label": "Giovedì", "attributes": {"colspan": "2"}},
                {"label": "Venerdì", "attributes": {"colspan": "2"}}
            ]/>
            <tbody>
            <#list ragazzi as rag>
                <tr>
                    <td class="text-capitalize">${rag[0].cognome+" "+rag[0].nome}</td>
                    <td>${rag[0].classe+rag[0].scuola.grado[0..0]?capitalize}</td>
                    <td> ${rag[0].laboratorio.descrizione[0..*15]}...</td>
                    <td> ${rag[1]?trim} </td>
                    <td><#if rag[0].mensa><img src="../img/octicons/check.svg"></#if></td>
                    <@ui.presenceWeekCells/>
                </tr>
            </#list>
            <#--riga vuota-->
            <tr>
                <th>Animatore</th>
                <th style="width:5%;">Anno</th>
                <th></th>
                <th> Periodo</th>
                <th style="width:5%;">Cellulare</th>
                <th colspan="2">Lunedì</th>
                <th colspan="2">Martedì</th>
                <th colspan="2">Mercoledì</th>
                <th colspan="2">Giovedì</th>
                <th colspan="2">Venerdì</th>
            </tr>
            <#list animatori as an>
                <tr>
                    <td class="text-capitalize">${an[0].cognome+" "+an[0].nome+" "}<#if an[0].responsabileSquadra><img
                                src="../img/octicons/ruby.svg"></#if></td>
                    <td>${an[0].dataNascita?string.yyyy}</td>
                    <td></td>
                    <td>${an[1]?trim}</td>
                    <td>${an[0].cellulare}</td>
                    <@ui.presenceWeekCells/>
                </tr>
            </#list>
            </tbody>
        </@ui.reportTable>
</div>
</@ui.page>
<script> window.print() </script>