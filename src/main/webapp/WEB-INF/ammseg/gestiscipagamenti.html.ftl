<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pb-2 bg-white" responsive=false>
        <#if errorePagamento??>
            <div class="alert alert-danger" role="alert">${errorePagamento}</div>
        </#if>
        <#if ragazzi??>
            <table class="table table-striped" id="t_pagamenti">
                <@ui.tableHead columns=[
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Info", "attributes": {"scope": "col"}},
                {"label": "Scheda", "attributes": {"scope": "col"}},
                {"label": "Ordine Iscrizione", "attributes": {"scope": "col"}},
                {"label": "Quota", "attributes": {"scope": "col"}},
                {"label": "", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
                <tbody class="list">
                <#list ragazzi as datirag>
                    <@ui.paymentRow paymentData=datirag action="GestisciPagamenti" infoTarget="inforag" sheetTarget="schedarag" popupTitle="Dettagli ragazzo" quota=(datirag[1]?then("", datirag[2])) imageAlts=false/>
                </#list>
                </tbody>
            </table>
        </#if>
</@ui.contentPanel>
<@ui.dataTable id="t_pagamenti">
            columnDefs: [{targets: [2, 3, 4, 5, 6], orderable: false}],
            info: false
</@ui.dataTable>
</@ui.page>