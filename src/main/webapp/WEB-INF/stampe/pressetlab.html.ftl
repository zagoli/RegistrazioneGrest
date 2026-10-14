<#ftl strip_whitespace = true>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="mt-4 content">
    <h2 class="text-uppercase text-center"> presenze settimanali ${laboratorio.descrizione}</h2>
    <h5 class="text-uppercase text-center font-italic">settimana numero ${settimana.idSettimana?c}
        <small>(dal ${settimana.daQuando} al ${settimana.AQuando})</small>
    </h5>
    <@ui.reportTable>
            <@ui.tableHead columns=[
                {"label": "Ragazzo", "attributes": {}},
                {"label": "Classe", "attributes": {"style": "width:5%;"}}
            ]/>
            <tbody>
            <#list ragazzi as rag>
                <tr>
                    <td class="text-capitalize">${rag.cognome+" "+rag.nome}</td>
                    <td class="text-center">${rag.classe+rag.scuola.grado[0..0]?capitalize}</td>
                </tr>
            </#list>
            <#--riga vuota-->
            <tr>
                <th>Animatore</th>
                <th style="width:5%;">Anno</th>
                <th>Cellulare</th>
            </tr>
            <#list animatori as an>
                <tr>
                    <td class="text-capitalize">${an.cognome+" "+an.nome} <#if an.responsabileLaboratorio><img
                                src="../img/octicons/ruby.svg"></#if></td>
                    <td>${an.dataNascita?string.yyyy}</td>
                    <td>${an.cellulare}</td>
                </tr>
            </#list>
            </tbody>
        </@ui.reportTable>
</div>
</@ui.page>
<script> window.print() </script>