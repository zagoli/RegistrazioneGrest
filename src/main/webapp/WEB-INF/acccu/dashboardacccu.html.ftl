<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<div class="container-fluid mt-5 content">
    <#--TABELLE ACCOMPAGNATORI CONTATTI-->
    <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
        <#if accompagnatori??>
            <h5 class="text-center pb-1"> Accompagnatori autorizzati </h5>
            <#assign count = 0>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <@ui.tableHead columns=[
                {"label": "#", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Modifica", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Elimina", "attributes": {"scope": "col", "style": "width: 2%;"}}
            ]/>
                    <tbody>
                    <#list accompagnatori as acc>
                        <#assign count = count + 1>
                        <tr>
                            <th scope="row">${count}</th>
                            <td>${acc.nome}</td>
                            <td>${acc.cognome}</td>
                            <td><a href="/RegistrazioneGrest/App/ModificaAccompagnatore?id=${acc.id?c}"><img
                                            src="../img/octicons/pencil.svg"></a></td>
                            <td><a href="/RegistrazioneGrest/App/EliminaAccompagnatore?id=${acc.id?c}"><img
                                            src="../img/octicons/trashcan.svg"></a></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </#if>
        <a href="/RegistrazioneGrest/App/InserisciAccompagnatore" class="btn btn-block btn-primary">
            Autorizza un accompagnatore
        </a>
    </div>

    <div class="container shadow pt-2 pl-3 pr-3 pb-2 mt-4 bg-white">
        <#if contatti??>
            <h5 class="text-center pb-1"> Contatti telefonici urgenze </h5>
            <#assign count = 0>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <@ui.tableHead columns=[
                {"label": "#", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Cellulare", "attributes": {"scope": "col"}},
                {"label": "Fisso", "attributes": {"scope": "col"}},
                {"label": "Relazione", "attributes": {"scope": "col"}},
                {"label": "Modifica", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Elimina", "attributes": {"scope": "col", "style": "width: 2%;"}}
            ]/>
                    <tbody>
                    <#list contatti as cu>
                        <#assign count = count + 1>
                        <tr>
                            <th scope="row">${count}</th>
                            <td>${cu.nome}</td>
                            <td>${cu.cognome}</td>
                            <td>${cu.cellulare!}</td>
                            <td>${cu.fisso!}</td>
                            <td>${cu.relazione}</td>
                            <td>
                                <a href="/RegistrazioneGrest/App/ModificaCU?id=${cu.id?c}">
                                    <img src="../img/octicons/pencil.svg" alt="modifica">
                                </a>
                            </td>
                            <td>
                                <a href="/RegistrazioneGrest/App/EliminaCU?id=${cu.id?c}">
                                    <img src="../img/octicons/trashcan.svg" alt="elimina">
                                </a>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </#if>
        <a href="/RegistrazioneGrest/App/InserisciCU" class="btn btn-block btn-primary">Inserisci un contatto telefonico
            urgenze</a>
    </div>

</div>
</@ui.page>