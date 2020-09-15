<#ftl strip_whitespace = true>
<#include "../struct/header.html.ftl">
    <div class="mt-4 content">
        <h2 class="text-uppercase text-center">Elenco sintesi terzamedia</h2>
        <div class="container-block">
            <div class="mr-2 ml-2">
                <table class="table table-bordered pl-2 pr-2 table-sm">
                    <thead>
                        <th>Cognome</th>
                        <th>Nome</th>
                        <th>Periodo</th>                    
                        <th>Squadra</th>
                        <th>Laboratorio</th>
                        <th>Cellulare</th>
                        <th>Telefoni urgenze</th>
                    </thead>
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
                                    <i>R </i><b>${ter[0].registrato.telefono}</b> -
                                    <#list ter[2][0..*4] as cu>
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