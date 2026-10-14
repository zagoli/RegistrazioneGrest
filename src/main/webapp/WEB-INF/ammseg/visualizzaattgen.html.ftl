<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<div class="container-fluid mt-5 content">
    <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
        <#if attivita??>
        <#assign count = 0>
        <table class="table table-striped nowrap" id="t_collab">
            <@ui.tableHead columns=[
                {"label": "#", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Attività", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Data", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
            <tbody class="list">
            <#list attivita as attarray>
                <#assign count = count + 1>
                <tr>
                    <th scope="row">${count}</th>
                    <td>${attarray[3]}</td>
                    <td>${attarray[0]?capitalize}</td>
                    <td>${attarray[1]?capitalize}</td>
                    <td>${attarray[2]!"Non specificato"}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    </#if>
</div>
</div>
<@ui.dataTable id="t_collab">
            paging: false,
            columnDefs: [{targets: [0], orderable: false}],
            info: false
</@ui.dataTable>
</@ui.page>