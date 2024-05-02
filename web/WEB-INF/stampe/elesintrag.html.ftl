<#ftl strip_whitespace = true>
<#include "../struct/header.html.ftl">
<div class="mt-4 content">
    <h2 class="text-uppercase text-center">Elenco sintesi ragazzi</h2>
    <p>Stampato il
        <script type="text/javascript">
            var theDate = new Date();
            document.write(theDate.toLocaleDateString('it-IT'));
        </script>
    </p>
    <div class="container-block">
        <div class="mr-2 ml-2">
            <table class="table table-bordered pl-2 pr-2 table-sm">
                <thead>
                <tr>
                    <th>Cognome</th>
                    <th>Nome</th>
                    <th style="width:5%;">Classe</th>
                    <th>Periodo</th>
                    <th>Squadra</th>
                    <th>Laboratorio</th>
                    <th style="width:5%;">Mensa</th>
                    <th>Telefoni urgenze</th>
                </tr>
                </thead>
                <tbody>
                <#list ragazzi as rag>
                    <tr>
                        <td class="text-capitalize">${rag[0].cognome}</td>
                        <td class="text-capitalize">${rag[0].nome}</td>
                        <td align="center">${rag[0].classe+rag[0].scuola.grado[0..0]?capitalize}</td>
                        <td> ${rag[1]?trim} </td>
                        <td> <#if rag[0].squadra.id!=0> ${rag[0].squadra.nome} </#if> </td>
                        <td> ${rag[0].laboratorio.descrizione[0..*15]}...</td>
                        <td align="center"><#if rag[0].mensa><img src="../risorse/img/octicons/check.svg"></#if>
                        </td>
                        <td>
                            <i>R </i><b>${rag[0].registrato.telefono}</b> -
                            <#list rag[2][0..*4] as cu>
                                <i>${cu.relazione[0..*1]?capitalize+" "}</i>
                                <b>${cu.cellulare+" "+cu.fisso}</b> -
                            </#list>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
<script> window.print() </script>