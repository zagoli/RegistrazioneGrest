<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container-fluid mt-5 content">
    <div class="container shadow pt-2 pb-2 bg-white">
        <#if errorePagamento??>
            <div class="alert alert-danger" role="alert">${errorePagamento}</div>
        </#if>
        <#if terzamedia??>
            <div class="table-responsive">
                <table class="table table-hover" id="t_pagamentiter">
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
                <#list terzamedia as datiter>
                    <tr>
                        <td class="nome">
                            ${datiter[0].nome}
                            <#-- form pagamento nascosto -->
                            <form id="form-pagamento-${datiter[0].id?c}"
                                  action="/RegistrazioneGrest/App/GestisciPagamentiTerzamedia"
                                  method="POST">
                                <#if datiter[1]>
                                <#-- ha pagato, quindi posso eliminare il pagamento -->
                                    <input type="hidden" name="deletePagamento" value="${datiter[2].id?c}"/>
                                <#else>
                                <#-- non ha pagato, quindi posso aggiungere un nuovo pagamento -->
                                    <input type="hidden" name="addPagamento" value="${datiter[0].id?c}"/>
                                </#if>
                            </form>
                            <#------------------------------>
                        </td>
                        <td class="cognome">${datiter[0].cognome}</td>
                        <td><a href="javascript:"
                               onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=infoter&id=${datiter[0].id?c}', 'Dettagli terzamedia', 'width=600, height=700, status, scrollbars=1, location');">
                                <img src="../img/octicons/search.svg" alt="cerca"></a></td>
                        <td><a href="/RegistrazioneGrest/App/InfoDettaglio?target=schedater&id=${datiter[0].id?c}">
                                <img src="../img/octicons/file.svg" alt="stampa scheda"></a></td>
                        <#if datiter[1]>
                            <#--ha già pagato-->
                            <td class="text-center">
                                <span title="Evaso da ${datiter[2].nomeRegistrato + " " + datiter[2].cognomeRegistrato} il ${datiter[2].data}">
                                    ${datiter[2].ordineArrivo}
                                </span>
                            </td>
                            <td class="text-center">
                                <span>${datiter[2].quota}&euro;</span>
                            </td>
                            <td class="text-center">
                                <button type="submit"
                                        form="form-pagamento-${datiter[0].id?c}"
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
                                           form="form-pagamento-${datiter[0].id?c}"
                                           placeholder="Ordine iscrizione"
                                           required>
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input type="number"
                                           class="form-control"
                                           name="quota"
                                           form="form-pagamento-${datiter[0].id?c}"
                                           value="${datiter[2]!}"
                                           required>
                                </label>
                            </td>
                            <td class="text-center">
                                <button type="submit"
                                        form="form-pagamento-${datiter[0].id?c}"
                                        class="btn btn-success btn-sm">
                                    Aggiungi Pagamento
                                </button>
                            </td>
                        </#if>
                    </tr>
                </#list>
                </tbody>
            </table>
            </div>
        </#if>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#t_pagamentiter').DataTable({
            columnDefs: [{targets: [2, 3, 4, 5, 6], orderable: false}],
            info: false
        });
    });
</script>
<#include "../struct/footer.html.ftl">