<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="container shadow pt-2 pb-2 bg-white" responsive=false>
        <#if errorePagamento??>
            <div class="alert alert-danger" role="alert">${errorePagamento}</div>
        </#if>
        <#if terzamedia??>
            <div class="table-responsive">
                <table class="table table-hover" id="t_pagamentiter">
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
                <#list terzamedia as datiter>
                    <@ui.paymentRow paymentData=datiter action="GestisciPagamentiTerzamedia" infoTarget="infoter" sheetTarget="schedater" popupTitle="Dettagli terzamedia" quota=(datiter[1]?then("", (datiter[2])!"")) imageAlts=true/>
                </#list>
                </tbody>
            </table>
            </div>
        </#if>
</@ui.contentPanel>
<@ui.dataTable id="t_pagamentiter">
            columnDefs: [{targets: [2, 3, 4, 5, 6], orderable: false}],
            info: false
</@ui.dataTable>
</@ui.page>