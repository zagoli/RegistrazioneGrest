<#include "../struct/header.html.ftl">      
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container border border-secondary rounded pt-2 pl-3 pr-3 pb-2 bg-light">
            <#if terzamedia??>
                <form action="/RegistrazioneGrest/App/Squadre?target=ter" method="POST">
                    <!-- l'input nascosto indica che abbiamo usato la pagina per impostare le squadre-->
                    <input type="hidden" name="submitted"/>
                    <input type="submit" class="btn btn-primary" value="Salva"/>
                    <table class="table table-striped" id="t_squadreter">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">Cognome</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Squadra</th>
                            </tr>
                        </thead>
                        <tbody class="list">
                            <#list terzamedia as ter>
                                <tr>
                                    <td>${ter.cognome}</td>
                                    <td>${ter.nome}</td>
                                    <td>
                                        <label class="sr-only" for="squadra">Squadra</label>
                                        <select class="custom-select mr-sm-2" id="squadra" name="squadra">
                                            <option value="${ter.id}" <#if !ter.squadra??>selected</#if>></option>
                                            <option value="${ter.id},Arancio grandi" <#if ter.squadra?? && ter.squadra="Arancio grandi">selected</#if>>Arancio grandi</option>
                                            <option value="${ter.id},Arancio piccoli" <#if ter.squadra?? && ter.squadra="Arancio piccoli">selected</#if>>Arancio piccoli</option>
                                            <option value="${ter.id},Azzurri grandi" <#if ter.squadra?? && ter.squadra="Azzurri grandi">selected</#if>>Azzurri grandi</option>
                                            <option value="${ter.id},Azzurri piccoli" <#if ter.squadra?? && ter.squadra="Azzurri piccoli">selected</#if>>Azzurri piccoli</option>
                                            <option value="${ter.id},Blu grandi" <#if ter.squadra?? && ter.squadra="Blu grandi">selected</#if>>Blu grandi</option>
                                            <option value="${ter.id},Blu piccoli" <#if ter.squadra?? && ter.squadra="Blu piccoli">selected</#if>>Blu piccoli</option>
                                            <option value="${ter.id},Gialli grandi" <#if ter.squadra?? && ter.squadra="Gialli grandi">selected</#if>>Gialli grandi</option>
                                            <option value="${ter.id},Gialli piccoli" <#if ter.squadra?? && ter.squadra="Gialli piccoli">selected</#if>>Gialli piccoli</option>
                                            <option value="${ter.id},Rossi grandi" <#if ter.squadra?? && ter.squadra="Rossi grandi">selected</#if>>Rossi grandi</option>
                                            <option value="${ter.id},Rossi piccoli" <#if ter.squadra?? && ter.squadra="Rossi piccoli">selected</#if>>Rossi piccoli</option>
                                            <option value="${ter.id},Verdi grandi" <#if ter.squadra?? && ter.squadra="Verdi grandi">selected</#if>>Verdi grandi</option>
                                            <option value="${ter.id},Verdi piccoli" <#if ter.squadra?? && ter.squadra="Verdi piccoli">selected</#if>>Verdi piccoli</option>
                                        </select>
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
            $('#t_squadreter').DataTable({
                paging: false,
                columnDefs: [{targets:[2],orderable:false}],
                info: false
            });
        } );
    </script>
    <!-- questo script permette di salvare solo le modifiche effettuate, invece che di salvare tutto quello che è rimasto uguale-->
    <script src="../risorse/js/salvasolomodificatoragter.js"></script>
<#include "../struct/footer.html.ftl">