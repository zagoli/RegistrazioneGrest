<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <#if attivita??>
                <h5 class="text-center mb-3">Collaboro alla buona riuscita del Grest nei seguenti modi</h5>
                <#assign count = 0>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th scope="col" style="width: 2%;">#</th>
                        <th scope="col">Attivit&agrave;</th>
                        <th scope="col">Data</th>
                        <th scope="col" style="width: 2%;">Elimina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list attivita as att, dataid>
                            <#assign count = count + 1>
                            <tr>
                                <th scope="row">${count}</th>
                                <td>${att.descrizione}</td>
                                <td><#if dataid[0]??>${dataid[0]}</#if></td>
                                <td align="center"><a href="/RegistrazioneGrest/App/EliminaPrenotazioneAttGen?id=${dataid[1]}"><img src="../risorse/img/octicons/trashcan.svg"></a></td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
                <p class="text-center mt-4">&Egrave; possibile collaborare in pi&ugrave; di una attivit&agrave;! Ripetere il procedimento precedente per dare la propria disponibilit&agrave; in altri ambiti.</p>
            <#else>
                <p class="text-center">Per dare la tua disponibilit&agrave; a collaborare, scegli innanzitutto dalla lista il tipo di attivit&agrave; che ti interessa, poi scrivi opzionalmente un periodo in cui sei disponibile, quindi clicca invia.</p>
                <hr/>
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