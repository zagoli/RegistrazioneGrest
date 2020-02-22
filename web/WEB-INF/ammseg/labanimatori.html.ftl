<#include "../struct/header.html.ftl">      
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container border border-secondary rounded pt-2 pl-3 pr-3 pb-2 bg-light">
            <#if animatori??>
                <form action="/RegistrazioneGrest/App/Laboratori?target=an" method="POST">
                    <!-- l'input nascosto indica che abbiamo usato la pagina per impostare i laboratori-->
                    <input type="hidden" name="submitted"/>
                    <input type="submit" class="btn btn-primary" value="Salva"/>
                    <table class="table table-striped" id="t_labani">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">Cognome</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Laboratorio</th>
                                <th scope="col">Responsabile</th>
                            </tr>
                        </thead>
                        <tbody class="list">
                            <#list animatori as an>
                                <tr>
                                    <td>${an.cognome?capitalize}</td>
                                    <td>${an.nome?capitalize}</td>
                                    <td>
                                        <label class="sr-only" for="${an.id}laboratorio">Laboratorio</label>
                                        <select class="custom-select mr-sm-2" id="${an.id}laboratorio" name="${an.id}">
                                            <#list laboratori as lab>
                                                <option value="${lab.id}" <#if an.laboratorio.id==lab.id>selected</#if>>${lab.descrizione}</option>
                                            </#list>
                                        </select>
                                    </td>
                                    <td align="center">
                                        <div class="checkbox">
                                            <label class="sr-only" for="${an.id}responsabileLaboratorio">Responsabile laboratorio</label>
                                            <input id="${an.id}responsabileLaboratorio" name="${an.id}" type="checkbox" data-toggle="toggle" data-off="No" data-on="S&igrave;" <#if an.responsabileLaboratorio>checked</#if>>
                                        </div>
                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </form>
            </#if>               
        </div>  
    </div>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
    <script>
        $(document).ready( function () {
            $('#t_labani').DataTable({
                paging: false,
                columnDefs: [
                    {orderable:false,targets:[2,3]},
                    {searchable:false,targets:3},
                    {width:"10%",targets:3}
                ],
                info: false
            });
        } );
    </script>
    <!-- questo script permette di salvare solo le modifiche effettuate, invece che di salvare tutti gli animatori compresi quelli che sono rimasti uguali-->
    <script src="../risorse/js/salvasolomodificatoanimatori.js"></script>
<#include "../struct/footer.html.ftl">