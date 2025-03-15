<#ftl strip_whitespace = true>
<#include "../struct/header.html.ftl">
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">elenco sintesi animatori</h2>
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
                <th>Laboratorio</th>
                <th>Periodo</th>
                <th>Squadra</th>
                <th>Cellulare</th>
            </tr>
            </thead>
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
        </table>
    </div>
</div>
<#include "../struct/footer.html.ftl">
<script> window.print() </script>