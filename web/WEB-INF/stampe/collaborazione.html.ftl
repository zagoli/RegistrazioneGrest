<#ftl strip_whitespace = true>
<#include "../struct/header.html.ftl">
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">Collaborazione Genitori</h2>
    <div class="container-block">
        <div class="mr-2 ml-2">
            <table class="table table-bordered pl-2 pr-2 table-sm">
                <thead>
                <tr>
                    <th>Genitore</th>
                    <th>Ragazzo</th>
                    <th>Periodo</th>
                    <th>Cellulare</th>
                    <th>Collaborazione</th>
                    <th>Data</th>
                </tr>
                </thead>
                <tbody>
                <#list dati as dt>
                    <tr>
                        <td class="text-capitalize">${dt[2].cognome?capitalize+" "+dt[2].nome?capitalize}</td>
                        <td class="text-capitalize"><#if dt[3]??>${dt[3].cognome?capitalize+" "+dt[3].nome?capitalize}</#if></td>
                        <td><#if dt[4]??><#list dt[4] as cal>${cal.idSettimana+" "?c}</#list></#if></td>
                        <td> ${dt[2].telefono} </td>
                        <td> ${dt[1].descrizione} </td>
                        <td> ${dt[0].data}</td>
                    </tr>
                </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<#include "../struct/footer.html.ftl">
<script> window.print() </script>