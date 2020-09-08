<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <#if attivita??>
                <#assign count = 0>
                    <table class="table table-striped nowrap" id="t_collab">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col" style="width: 2%">#</th>
                                <th scope="col">Attivit&agrave;</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Cognome</th>
                                <th scope="col">Data</th>
                            </tr>
                        </thead>
                        <tbody class="list">
                            <#list attivita as attarray>
                                <#assign count = count + 1>
                                <tr>
                                    <th scope="row">${count}</th>
                                    <td>${attarray[3]}</td>
                                    <td>${attarray[0]?capitalize}</td>
                                    <td>${attarray[1]?capitalize}</td>
                                    <td>${attarray[2]}</td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </#if>
        </div>  
    </div>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
        $(document).ready( function () {
            $('#t_collab').DataTable({
                paging: false,
                columnDefs: [{targets:[0],orderable:false}],
                info: false
            });
        } );
    </script>
<#include "../struct/footer.html.ftl">