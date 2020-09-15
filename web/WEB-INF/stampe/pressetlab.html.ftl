<#ftl strip_whitespace = true>
<#include "../struct/header.html.ftl">
    <div class="mt-4 content">
        <h2 class="text-uppercase text-center"> presenze settimanali ${laboratorio.descrizione}</h2>
        <h5 class="text-uppercase text-center font-italic">settimana numero ${settimana.idSettimana} <small>(dal ${settimana.daQuando} al ${settimana.aQuando})</small>
        </h5>
        <div class="container">
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>Ragazzo</th>
                    <th style="width:5%;">Classe</th>
                </tr>
                </thead>
                <tbody>
                <#list ragazzi as rag>
                    <tr>
                        <td class="text-capitalize">${rag.cognome+" "+rag.nome}</td>
                        <td align="center">${rag.classe+rag.scuola.grado[0..0]?capitalize}</td>                        
                    </tr>
                    </#list>
                <!--riga vuota-->
                <tr>
                    <th>Animatore</th>
                    <th style="width:5%;">Anno</th>
                    <th>Cellulare</th>
                </tr>
                <#list animatori as an>
                        <tr>
                            <td class="text-capitalize">${an.cognome+" "+an.nome} <#if an.responsabileLaboratorio><img src="../risorse/img/octicons/ruby.svg"></#if></td>                      
                            <td>${an.dataNascita?string.yyyy}</td>
                            <td>${an.cellulare}</td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
<#include "../struct/footer.html.ftl">
<script> window.print() </script>