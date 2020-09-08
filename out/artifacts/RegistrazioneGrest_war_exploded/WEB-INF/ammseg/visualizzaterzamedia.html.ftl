<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="pt-2 pl-3 pr-3 pb-2">
            <table class="table table-hover table-responsive nowrap" id="t_ter">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col" style="width: 2%">#</th> 
                        <th scope="col" style="width: 6%">Azioni</th>
                        <th scope="col">Cognome</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Nascita</th>
                        <th scope="col">Via</th>
                        <th scope="col">Localit&agrave;</th>
                        <th scope="col">Parrocchia</th>
                        <th scope="col">Sezione</th>
                        <th scope="col">Scuola</th>
                        <th scope="col">Squadra</th>
                        <th scope="col">Laboratorio</th>                               
                        <th scope="col">Nouta</th>
                        <th scope="col">Passaggio</th>
                        <th scope="col">Richieste</th>
                        <th scope="col">Allergie</th>
                        <th scope="col">Pagamento</th>
                    </tr>
                </thead>
                <tbody class="list">
                    <#list terzamedia as ter>
                        <tr>
                            <th class="ordineArrivo" style="text-align:center"><#if ter[1]??>${ter[1].ordineArrivo}</#if></th>
                            <td>
                                <a href="javascript:;" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=infoter&id=${ter[0].id}', 'Dettagli terzamedia', 'width=600, height=700, status, scrollbars=1, location');"><img src="../risorse/img/octicons/search.svg"></a>
                                <a href="/RegistrazioneGrest/App/ModificaTerzamedia?id=${ter[0].id}"><img src="../risorse/img/octicons/pencil.svg"></a>
                                <a href="/RegistrazioneGrest/App/EliminaTerzamedia?id=${ter[0].id}"><img src="../risorse/img/octicons/trashcan.svg"></a>
                            </td>
                            <td>${ter[0].cognome?capitalize}</td>
                            <td>${ter[0].nome?capitalize}</td>                                    
                            <td data-sort="${ter[0].dataNascita?string.iso}">${ter[0].dataNascita?string["dd/MM/yyyy"]}</td>
                            <td>${ter[0].registrato.via?capitalize+" "+ter[0].registrato.civico}</td>
                            <td>${ter[0].registrato.localita?capitalize}</td>
                            <td>${ter[0].parrocchia.nome}</td>
                            <td>${ter[0].sezione}</td>
                            <td>${ter[0].scuola.descrizione}</td>
                            <td><#if ter[0].squadra??>${ter[0].squadra}<#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td>${ter[0].laboratorio.descrizione}</td>
                            <td><#if ter[0].saNuotare><img src="../risorse/img/octicons/check.svg"><#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td><#if ter[0].festaPassaggio><img src="../risorse/img/octicons/check.svg"><#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td><#if ter[0].richieste??><img src="../risorse/img/octicons/check.svg"><#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td><#if ter[0].noteAlimentari??><img src="../risorse/img/octicons/check.svg"><#else><img src="../risorse/img/octicons/x.svg"></#if></td>
                            <td><#if ter[1]??>${ter[1].quota}&euro;<#else><img src="../risorse/img/octicons/x.svg"></#if></td>
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
            $('#t_ter').DataTable({
                paging: false ,
                columnDefs: [
                {
                    targets: [2,3,4,5,6,7,9,11],
                    render: $.fn.dataTable.render.ellipsis(15, true)
                },
                {
                    targets: [1, 8, 9, 12, 13, 14, 15, 16],
                    searchable: false
                },
                {
                    targets: [1, 14, 15, 16],
                    orderable: false
                }
                ],
                info: false
            });
        } );
    </script>
<#include "../struct/footer.html.ftl">