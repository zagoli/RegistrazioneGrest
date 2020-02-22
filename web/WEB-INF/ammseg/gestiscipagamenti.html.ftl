<#include "../struct/header.html.ftl">      
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container border border-secondary rounded pt-2 pl-3 pr-3 pb-2 bg-light">
            <#if ragazzi??>
                <table class="table table-striped" id="t_pagamenti">
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
                        <#list ragazzi as datirag>
                            <tr>
                                <td class="nome">${datirag[0].nome}</td>
                                <td class="cognome">${datirag[0].cognome}</td>   
                                <td align="center"> <a href="javascript:;" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=inforag&id=${datirag[0].id}', 'Dettagli ragazzo', 'width=600, height=700, status, scrollbars=1, location');"><img src="../risorse/img/octicons/search.svg"></a> </td>
                                <td align="center"> <a href="/RegistrazioneGrest/App/InfoDettaglio?target=schedarag&id=${datirag[0].id}"><img src="../risorse/img/octicons/file.svg"></a> </td>
                                <#if datirag[1]>
                                    <!--ha già pagato-->
                                    <td>
                                        <form action="/RegistrazioneGrest/App/GestisciPagamenti" method="POST" class="form-inline ml-5">
                                            <input type="hidden" name="deletePagamento" value="${datirag[0].id}"/>
                                            <p class="font-weight-bold ml-5 mr-5">${datirag[2].ordineArrivo}</p>
                                            <p class="ml-5 mr-2">Quota:
                                                <b>${datirag[2].quota}&euro;</b>
                                                <br/>
                                                <small>
                                                    <i>Evaso da ${datirag[2].registrato.nome+" "+datirag[2].registrato.cognome+" il "+datirag[2].data}</i>
                                                </small>
                                            </p> 
                                            <button type="submit" class="btn btn-danger ml-5" required><img src="../risorse/img/octicons/trashcan.svg"> Elimina Pagamento</button>  
                                        </form>
                                    </td>   
                                <#else>
                                    <!--non ha già pagato-->
                                    <td>
                                        <form action="/RegistrazioneGrest/App/GestisciPagamenti" method="POST" class="form-inline">
                                            <input type="hidden" name="addPagamento" value="${datirag[0].id}"/>
                                                <input type="number" class="form-control mr-2" name="ordineArrivo" placeholder="Ordine iscrizione" required>
                                                <input type="number" class="form-control mr-2" name="quota"  value="${datirag[2]}" required>
                                                <button type="submit" class="btn btn-success" required><img src="../risorse/img/octicons/plus.svg"> Aggiungi Pagamento</button>
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
            $('#t_pagamenti').DataTable({
                paging: false,
                columnDefs: [{targets:[2,3,4],orderable:false}],
                info: false
            });
        } );
    </script>
<#include "../struct/footer.html.ftl">