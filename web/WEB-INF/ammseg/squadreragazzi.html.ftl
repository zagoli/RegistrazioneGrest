<#include "../struct/header.html.ftl">      
<#include "../struct/navbar.html.ftl">
    <div class="container-fluid mt-5 content">
        <div class="container shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <#if ragazzi??>
                <form action="/RegistrazioneGrest/App/Squadre?target=rag" method="POST">
                    <!-- l'input nascosto indica che abbiamo usato la pagina per impostare le squadre-->
                    <input type="hidden" name="submitted"/>
                    <input type="submit" class="btn btn-primary" value="Salva"/>
                    <table class="table table-striped" id="t_squadrerag">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col">Cognome</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Classe</th>
                                <th scope="col">Squadra</th>
                            </tr>
                        </thead>
                        <tbody class="list">
                            <#list ragazzi as rag>
                                <tr>
                                    <td>${rag.cognome}</td>
                                    <td>${rag.nome}</td>
                                    <td>${rag.classe+rag.scuola.grado[0..0]?capitalize}</td>
                                    <td>
                                        <label class="sr-only" for="squadra">Squadra</label>
                                        <select class="custom-select mr-sm-2" id="squadra" name="squadra">
                                            <option value="${rag.id}" <#if !rag.squadra.id??>selected</#if>></option>
                                            <#list squadre as sq>
                                                <option value="${rag.id},${sq.id}"
                                                        <#if rag.squadra.id! = sq.id>selected</#if>>${sq.nome}</option>
                                            </#list>
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
            $('#t_squadrerag').DataTable({
                paging: false,
                columnDefs: [{targets:[3],orderable:false}],
                info: false
            });
        } );
    </script>
    <!-- questo script permette di salvare solo le modifiche effettuate, invece che di salvare tutto quello che è rimasto uguale-->
    <script src="../risorse/js/salvasolomodificatoragter.min.js"></script>
<#include "../struct/footer.html.ftl">