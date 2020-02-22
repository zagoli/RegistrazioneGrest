<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container border border-secondary rounded pt-2 pl-3 pr-3 pb-2 bg-light">
            <#if attivita??>
                <h5 class="text-center pb-1"> Collaborazioni prenotate </h5>
                <#assign count = 0>
                <table class="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col" style="width: 2%">#</th>
                            <th scope="col">Attivit&agrave;</th>
                            <th scope="col">Data</th>
                            <th scope="col" style="width: 2%">Elimina</th>
                        </tr>
                    </thead>
                    <tbody>
                        <#list attivita as att, dataid>
                            <#assign count = count + 1>
                            <tr>
                                <th scope="row">${count}</th>
                                <td>${att.descrizione}</td>
                                <td><#if dataid[0]??>${dataid[0]}</#if></td>
                                <td><a href="/RegistrazioneGrest/App/EliminaPrenotazioneAttGen?id=${dataid[1]}"><img src="../risorse/img/octicons/trashcan.svg"></a></td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
            </#if>
                <form action="/RegistrazioneGrest/App/RegistraAttGen" method="POST" data-parsley-validate>
                    <div class="form-row">
                        <div class="col-md-6">
                            <select class="form-control" name="attivita">
                                <#list allattivita as att>
                                    <option value="${att.id}">${att.descrizione}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-md-5">
                            <input type="text" class="form-control" name="data" placeholder="data o periodo preferito (opzionale)"/>
                        </div>
                        <div class="col">
                            <button type="submit" class="btn btn-primary mb-2" required>Invia</button>
                        </div>
                    </div>
                </form>
        </div>  
    </div>
<#include "../struct/footer.html.ftl">