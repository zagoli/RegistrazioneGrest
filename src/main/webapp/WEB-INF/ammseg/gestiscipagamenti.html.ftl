<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container-fluid mt-5 content">
    <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
        <#if ragazzi??>
            <table class="table table-striped" id="t_pagamenti">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col">Info</th>
                    <th scope="col">Scheda</th>
                    <th scope="col">Ordine Iscrizione</th>
                    <th scope="col">Quota</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody class="list">
                <#list ragazzi as datirag>
                    <tr>
                        <td class="nome">
                            ${datirag[0].nome}
                            <#-- form pagamento nascosto -->
                            <form id="form-pagamento-${datirag[0].id?c}"
                                  action="/RegistrazioneGrest/App/GestisciPagamenti"
                                  method="POST">
                                <#if datirag[1]>
                                <#-- ha pagato, quindi posso eliminare il pagamento -->
                                    <input type="hidden" name="deletePagamento" value="${datirag[2].id?c}"/>
                                <#else>
                                <#-- non ha pagato, quindi posso aggiungere un nuovo pagamento -->
                                    <input type="hidden" name="addPagamento" value="${datirag[0].id?c}"/>
                                </#if>
                            </form>
                            <#------------------------------>
                        </td>
                        <td class="cognome">${datirag[0].cognome}</td>
                        <td><a href="javascript:" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=inforag&id=${datirag[0].id?c}', 'Dettagli ragazzo', 'width=600, height=700, status, scrollbars=1, location');">
                                <img src="../img/octicons/search.svg"></a></td>
                        <td><a href="/RegistrazioneGrest/App/InfoDettaglio?target=schedarag&id=${datirag[0].id?c}">
                                <img src="../img/octicons/file.svg"></a></td>
                        <#if datirag[1]>
                            <#--ha già pagato-->
                            <td class="text-center">
                                <span title="Evaso da ${datirag[2].nomeRegistrato + " " + datirag[2].cognomeRegistrato} il ${datirag[2].data}">
                                    <b>${datirag[2].ordineArrivo}</b>
                                </span>
                            </td>
                            <td class="text-center">
                                <span>${datirag[2].quota}&euro;</span>
                            </td>
                            <td class="text-center">
                                <button type="submit"
                                        form="form-pagamento-${datirag[0].id?c}"
                                        class="btn btn-danger btn-sm">
                                    Elimina Pagamento
                                </button>
                            </td>
                        <#else>
                            <#--non ha già pagato-->
                            <td>
                                <label>
                                    <input type="number"
                                           class="form-control"
                                           name="ordineArrivo"
                                           form="form-pagamento-${datirag[0].id?c}"
                                           placeholder="Ordine iscrizione"
                                           required>
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input type="number"
                                           class="form-control"
                                           name="quota"
                                           form="form-pagamento-${datirag[0].id?c}"
                                           value="${datirag[2]}"
                                           required>
                                </label>
                            </td>
                            <td class="text-center">
                                <button type="submit"
                                        form="form-pagamento-${datirag[0].id?c}"
                                        class="btn btn-success btn-sm">
                                    Aggiungi Pagamento
                                </button>
                            </td>
                        </#if>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#t_pagamenti').DataTable({
            columnDefs: [{targets: [2, 3, 4, 5, 6], orderable: false}],
            info: false
        });
    });
</script>
<#include "../struct/footer.html.ftl">