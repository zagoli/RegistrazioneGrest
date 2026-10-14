<#ftl strip_whitespace = true>
<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=false>
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">elenco sintesi animatori</h2>
    <@ui.reportTable>
            <@ui.tableHead columns=[
                {"label": "Anno", "attributes": {"style": "width:5%;"}},
                {"label": "Cognome", "attributes": {}},
                {"label": "Nome", "attributes": {}},
                {"label": "Laboratorio", "attributes": {}},
                {"label": "Periodo", "attributes": {}},
                {"label": "Squadra", "attributes": {}},
                {"label": "Cellulare", "attributes": {}}
            ]/>
            <tbody>
            <#list animatori as ani>
                <tr>
                    <td>${ani[0].dataNascita?string.yyyy}</td>
                    <td class="text-capitalize">${ani[0].cognome}</td>
                    <td class="text-capitalize">${ani[0].nome}</td>
                    <td>${ani[0].laboratorio.descrizione} <#if ani[0].responsabileLaboratorio><img
                                src="../img/octicons/ruby.svg"></#if></td>
                    <td>${ani[1]?trim}</td>
                    <td><#if ani[0].squadra.id!=0>${ani[0].squadra.nome}</#if> <#if ani[0].responsabileSquadra>
                            <img src="../img/octicons/ruby.svg"></#if></td>
                    <td>${ani[0].cellulare}</td>
                </tr>
            </#list>
            </tbody>
        </@ui.reportTable>
</div>
</@ui.page>
<script> window.print() </script>