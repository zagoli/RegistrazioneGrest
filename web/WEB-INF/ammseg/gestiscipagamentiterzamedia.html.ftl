<#include "../struct/header.html.ftl">      
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <#if terzamedia??>
                <table class="table table-striped" id="t_pagamentiter">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Nome</th>
                            <th scope="col">Cognome</th>
                            <th scope="col">Info</th>
                            <th scope="col">Scheda</th>
                            <th scope="col" style="width: 70%">Ordine Iscrizione e Pagamento</th>
                        </tr>
                    </thead>
                    <tbody class="list">
                        <#list terzamedia as datiter>
                            <tr>
                                <td class="nome">${datiter[0].nome}</td>
                                <td class="cognome">${datiter[0].cognome}</td>   
                                <td align="center"> <a href="javascript:;" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=infoter&id=${datiter[0].id}', 'Dettagli terzamedia', 'width=600, height=700, status, scrollbars=1, location');"><img src="../risorse/img/octicons/search.svg"></a> </td>
                                <td align="center"> <a href="/RegistrazioneGrest/App/InfoDettaglio?target=schedater&id=${datiter[0].id}"><img src="../risorse/img/octicons/file.svg"></a> </td>
                                <#if datiter[1]>
                                    <!--ha già pagato-->
                                    <td>
                                        <form action="/RegistrazioneGrest/App/GestisciPagamentiTerzamedia" method="POST" class="form-inline">
                                            <input type="hidden" name="deletePagamento" value="${datiter[0].id}"/>
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-3">
                                                        <p class="text-center" data-toggle="tooltip" title="Evaso da ${datiter[2].registrato.nome+" "+datiter[2].registrato.cognome+" il "+datiter[2].data}"><b>${datiter[2].ordineArrivo}</b></p>
                                                    </div>
                                                    <div class="col" align="center">
                                                        <p>Quota:
                                                            <b>${datiter[2].quota}&euro;</b>
                                                        </p>
                                                    </div>
                                                    <div class="col" align="center">
                                                        <button type="submit" class="btn btn-danger ml-2" required><img src="../risorse/img/octicons/trashcan.svg">Elimina Pagamento</button>  
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </td>   
                                <#else>
                                    <!--non ha già pagato-->
                                    <td>
                                        <form action="/RegistrazioneGrest/App/GestisciPagamentiTerzamedia" method="POST" class="form-inline">
                                            <input type="hidden" name="addPagamento" value="${datiter[0].id}"/>
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col">
                                                        <input type="number" class="form-control mr-2" name="ordineArrivo" placeholder="Ordine iscrizione" required>
                                                    </div>
                                                    <div class="col">
                                                        <input type="number" class="form-control mr-2" name="quota"  value="${datiter[2]}" required>
                                                    </div>
                                                    <div class="col">
                                                        <button type="submit" class="btn btn-success" required>Aggiungi Pagamento</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </td>
                                </#if>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </#if>               
        </div>  
    </div>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
        $(document).ready( function () {
            $('#t_pagamentiter').DataTable({
                paging: false,
                columnDefs: [{targets:[2,3,4],orderable:false}],
                info: false
            });
        } );
        $(document).ready(function () {
            $('[data-toggle="tooltip"]').tooltip()
        });
    </script>
<#include "../struct/footer.html.ftl">