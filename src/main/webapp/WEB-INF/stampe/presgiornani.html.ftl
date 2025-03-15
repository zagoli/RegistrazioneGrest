<#ftl strip_whitespace = true>
<#include "../struct/header.html.ftl">
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">presenze giornaliere animatori</h2>
    <p>Stampato il
        <script type="text/javascript">
            var theDate = new Date();
            document.write(theDate.toLocaleDateString('it-IT'));
        </script>
    </p>
    <div class="container">
        <table class="table table-bordered table-sm">
            <thead>
            <tr>
                <th style="width:5%;">Anno</th>
                <th>Cognome</th>
                <th>Nome</th>
                <th>Periodo</th>
                <th colspan="2">Lunedì</th>
                <th colspan="2">Martedì</th>
                <th colspan="2">Mercoledì</th>
                <th colspan="2">Giovedì</th>
                <th colspan="2">Venerdì</th>
            </tr>
            </thead>
            <tbody>
            <#list animatori as ani>
                <tr>
                    <td>${ani[0].dataNascita?string.yyyy}</td>
                    <td class="text-capitalize">${ani[0].cognome}</td>
                    <td class="text-capitalize">${ani[0].nome}</td>
                    <td>${ani[1]?trim}</td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                    <td align="center"> M</td>
                    <td align="center"> P</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<#include "../struct/footer.html.ftl">
<script> window.print() </script>