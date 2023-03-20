<#ftl strip_whitespace = true>
<#include "../struct/header.html.ftl">
<div class="mt-4 content">
    <h2 class="text-uppercase text-center"> presenze settimanali <span
                style="color:${squadra.colore}; ">${squadra.nome}</span></h2>
    <h5 class="text-uppercase text-center font-italic">settimana numero ${settimana.idSettimana?c}
        <small>(dal ${settimana.daQuando} al ${settimana.aQuando})</small></h5>
    <div class="container">
        <table class="table table-bordered table-sm">
            <thead>
            <tr>
                <th>Ragazzo</th>
                <th style="width:5%;">Classe</th>
                <th> Laboratorio</th>
                <th> Periodo</th>
                <th style="width:5%;">Mensa</th>
                <th colspan="2">Lunedì</th>
                <th colspan="2">Martedì</th>
                <th colspan="2">Mercoledì</th>
                <th colspan="2">Giovedì</th>
                <th colspan="2">Venerdì</th>
            </tr>
            </thead>
            <tbody>
            <#list ragazzi as rag>
                <tr>
                    <td class="text-capitalize">${rag[0].cognome+" "+rag[0].nome}</td>
                    <td align="center">${rag[0].classe+rag[0].scuola.grado[0..0]?capitalize}</td>
                    <td> ${rag[0].laboratorio.descrizione[0..*15]}...</td>
                    <td> ${rag[1]?trim} </td>
                    <td align="center"><#if rag[0].mensa><img src="../risorse/img/octicons/check.svg"></#if></td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                    <td align="center"> M</td>
                        <td align="center"> P </td>
                        <td align="center"> M </td>
                        <td align="center"> P </td>
                    </tr>
                    </#list>
            <!--riga vuota-->
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
                            <td class="text-capitalize">${an[0].cognome+" "+an[0].nome+" "}<#if an[0].responsabileSquadra><img src="../risorse/img/octicons/ruby.svg"></#if></td>
                            <td>${an[0].dataNascita?string.yyyy}</td>                        
                            <td></td>
                            <td>${an[1]?trim}</td>
                            <td>${an[0].cellulare}</td>
                            <td align="center"> M </td>
                            <td align="center"> P </td>
                            <td align="center"> M </td>
                            <td align="center"> P </td>
                            <td align="center"> M </td>
                            <td align="center"> P </td>
                            <td align="center"> M </td>
                            <td align="center"> P </td>
                            <td align="center"> M </td>
                            <td align="center"> P </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
<#include "../struct/footer.html.ftl">
<script> window.print() </script>