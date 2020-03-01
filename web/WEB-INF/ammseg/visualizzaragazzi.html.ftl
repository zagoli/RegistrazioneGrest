<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="pt-2 pl-3 pr-3 pb-2">
            <table class="table table-hover table-responsive nowrap" id="t_rag">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col" style="width: 2%">#</th> 
                        <th scope="col">Azioni</th>
                        <th scope="col">Cognome</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Nascita</th>
                        <th scope="col">Via</th>
                        <th scope="col">Localit&agrave;</th>
                        <th scope="col">Parrocchia</th>
                        <th scope="col">Classe</th>
                        <th scope="col">Sezione</th>
                        <th scope="col">Scuola</th>
                        <th scope="col">Squadra</th>
                        <th scope="col">Laboratorio</th>                               
                        <th scope="col">Mensa</th>
                        <th scope="col">Nouta</th>
                        <th scope="col">Entrata</th>
                        <th scope="col">Richieste</th>
                        <th scope="col">Allergie</th>
                        <th scope="col">Pagamento</th>
                    </tr>
                </thead>
                <tbody class="list">
                    <#list ragazzi as rag>
                        <tr>
                            <th style="text-align:center"><#if rag[1]??>${rag[1].ordineArrivo}</#if></th>
                            <td>
                                <a href="javascript:;" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=inforag&id=${rag[0].id}', 'Dettagli ragazzo', 'width=600, height=700, status, scrollbars=1, location');"><img src="../risorse/img/octicons/search.svg"></a>
                                <a href="/RegistrazioneGrest/App/ModificaRagazzo?id=${rag[0].id}"><img src="../risorse/img/octicons/pencil.svg"></a>
                                <a href="/RegistrazioneGrest/App/EliminaRagazzo?id=${rag[0].id}"><img src="../risorse/img/octicons/trashcan.svg"></a>
                            </td>
                            <td>${rag[0].cognome?capitalize}</td>
                            <td>${rag[0].nome?capitalize}</td>                                    
                            <td>${rag[0].dataNascita}</td>
                            <td>${rag[0].registrato.via?capitalize+" "+rag[0].registrato.civico}</td>
                            <td>${rag[0].registrato.localita?capitalize}</td>
                            <td>${rag[0].parrocchia.nome}</td>
                            <td>${rag[0].classe+rag[0].scuola.grado[0..0]?capitalize}</td>
                            <td>${rag[0].sezione}</td>
                            <td>${rag[0].scuola.descrizione}</td>
                            <td><#if rag[0].squadra??>${rag[0].squadra}<#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td>${rag[0].laboratorio.descrizione}</td>
                            <td><#if rag[0].mensa>S&igrave;<#else>No</#if></td>
                            <td><#if rag[0].saNuotare>S&igrave;<#else>No</#if></td>
                            <td><#if rag[0].entrataAnticipata>S&igrave;<#else>No</#if></td>
                            <td><#if rag[0].richieste??><img src="../risorse/img/octicons/check.svg"><#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td><#if rag[0].noteAlimentari??><img src="../risorse/img/octicons/check.svg"><#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td><#if rag[1]??>${rag[1].quota}&euro;<#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </div>
    </div>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/plug-ins/1.10.19/dataRender/ellipsis.js"></script>
    <script>
        $(document).ready( function () {
            $('#t_rag').DataTable({
                paging: false ,
                columnDefs: [
                {
                    targets: [2,3,4,5,6,7,10,12],
                    render: $.fn.dataTable.render.ellipsis(15, true)
                },
                {
                    targets: [1, 8, 9, 13, 14, 15, 16, 17],
                    searchable: false
                },
                {
                    targets: [1, 16, 17, 18],
                    orderable: false
                }
                ],
                info: false
            });
        } );
    </script>
<#include "../struct/footer.html.ftl">