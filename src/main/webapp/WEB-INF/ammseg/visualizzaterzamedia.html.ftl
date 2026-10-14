<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="pt-2 pl-3 pr-3 pb-2" responsive=true>
            <table class="table table-hover nowrap" id="t_ter">
            <@ui.tableHead columns=[
                {"label": "#", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Azioni", "attributes": {"scope": "col", "style": "width: 6%;"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Nascita", "attributes": {"scope": "col"}},
                {"label": "Via", "attributes": {"scope": "col"}},
                {"label": "Località", "attributes": {"scope": "col"}},
                {"label": "Parrocchia", "attributes": {"scope": "col"}},
                {"label": "Sezione", "attributes": {"scope": "col"}},
                {"label": "Scuola", "attributes": {"scope": "col"}},
                {"label": "Squadra", "attributes": {"scope": "col"}},
                {"label": "Laboratorio", "attributes": {"scope": "col"}},
                {"label": "Nuota", "attributes": {"scope": "col"}},
                {"label": "Passaggio", "attributes": {"scope": "col"}},
                {"label": "Richieste", "attributes": {"scope": "col"}},
                {"label": "Allergie", "attributes": {"scope": "col"}},
                {"label": "Pagamento", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
            <tbody class="list">
            <#list terzamedia as ter>
                <tr>
                    <th class="ordineArrivo"
                        style="text-align:center;"><#if ter[1]??>${ter[1].ordineArrivo}</#if></th>
                    <td>
                        <@ui.iscrittoActions id=ter[0].id infoTarget="infoter" popupTitle="Dettagli terzamedia" modifyAction="ModificaTerzamedia" deleteAction="EliminaTerzamedia" href="javascript:;" detailAlt="dettaglio"/>
                    </td>
                    <td>${ter[0].cognome?capitalize}</td>
                    <td>${ter[0].nome?capitalize}</td>
                    <td data-sort="${ter[0].dataNascita?string.iso}">${ter[0].dataNascita?string["dd/MM/yyyy"]}</td>
                    <td>${ui.indirizzo(ter[0].registrato, true, false)}</td>
                    <td>${(ter[0].registrato.localita!"")?capitalize}</td>
                    <td title="${ter[0].parrocchia.nome}">${ter[0].parrocchia.luogo}</td>
                    <td>${ter[0].sezione}</td>
                    <td>${ter[0].scuola.descrizione}</td>
                    <td class="badge-squadra-support">
                        <@ui.squadraBadge squadra=ter[0].squadra style="list"/>
                    </td>
                    <td>${ter[0].laboratorio.descrizione}</td>
                    <td><#if ter[0].saNuotare><img src="../img/octicons/check.svg" alt="si"><#else><img
                                src="../img/octicons/x.svg" alt="no"></#if></td>
                    <td><#if ter[0].festaPassaggio><img src="../img/octicons/check.svg" alt="si"><#else><img
                                src="../img/octicons/x.svg" alt="no"></#if></td>
                    <td><#if ter[0].richieste??><img src="../img/octicons/check.svg" alt="presenti"><#else>
                            <img src="../img/octicons/x.svg" alt="assenti"></#if></td>
                    <td><#if ter[0].noteAlimentari??><img src="../img/octicons/check.svg"
                                                          alt="presenti"><#else><img
                                src="../img/octicons/x.svg" alt="assenti"></#if></td>
                    <td><#if ter[1]??>${ter[1].quota}&euro;<#else><img src="../img/octicons/x.svg"
                                                                       alt="assente"></#if></td>
                </tr>
            </#list>
            </tbody>
        </table>
</@ui.contentPanel>
<script type="text/javascript" src="https://cdn.datatables.net/plug-ins/1.10.19/dataRender/ellipsis.js"></script>
<@ui.iscrittiDataTable id="t_ter" ellipsisTargets="[2, 3, 4, 5, 6, 7, 9, 11]" searchTargets="[1, 8, 9, 12, 13, 14, 15, 16]" orderTargets="[1, 14, 15, 16]"/>
</@ui.page>