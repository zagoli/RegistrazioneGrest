<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.contentPanel panelClass="pt-2 pl-3 pr-3 pb-2" responsive=true>
            <table class="table table-hover nowrap" id="t_rag">
            <@ui.tableHead columns=[
                {"label": "#", "attributes": {"scope": "col", "style": "width: 2%;"}},
                {"label": "Azioni", "attributes": {"scope": "col"}},
                {"label": "Cognome", "attributes": {"scope": "col"}},
                {"label": "Nome", "attributes": {"scope": "col"}},
                {"label": "Nascita", "attributes": {"scope": "col"}},
                {"label": "Via", "attributes": {"scope": "col"}},
                {"label": "Località", "attributes": {"scope": "col"}},
                {"label": "Parrocchia", "attributes": {"scope": "col"}},
                {"label": "Classe", "attributes": {"scope": "col"}},
                {"label": "Sezione", "attributes": {"scope": "col"}},
                {"label": "Scuola", "attributes": {"scope": "col"}},
                {"label": "Squadra", "attributes": {"scope": "col"}},
                {"label": "Laboratorio", "attributes": {"scope": "col"}},
                {"label": "Mensa", "attributes": {"scope": "col"}},
                {"label": "Nuota", "attributes": {"scope": "col"}},
                {"label": "Entrata", "attributes": {"scope": "col"}},
                {"label": "Richieste", "attributes": {"scope": "col"}},
                {"label": "Allergie", "attributes": {"scope": "col"}},
                {"label": "Pagamento", "attributes": {"scope": "col"}}
            ] class="thead-dark"/>
            <tbody class="list">
            <#list ragazzi as rag>
                <tr>
                    <th style="text-align:center;"><#if rag[1]??>${rag[1].ordineArrivo}</#if></th>
                    <td>
                        <@ui.iscrittoActions id=rag[0].id infoTarget="inforag" popupTitle="Dettagli ragazzo" modifyAction="ModificaRagazzo" deleteAction="EliminaRagazzo" href="javascript:" detailAlt="dettaglio"/>
                    </td>
                    <td>${rag[0].cognome?capitalize}</td>
                    <td>${rag[0].nome?capitalize}</td>
                    <td data-sort="${rag[0].dataNascita?string.iso}">${rag[0].dataNascita?string["dd/MM/yyyy"]}</td>
                    <td>${ui.indirizzo(rag[0].registrato, true, false)}</td>
                    <td>${(rag[0].registrato.localita!"")?capitalize}</td>
                    <td title="${rag[0].parrocchia.nome}">${rag[0].parrocchia.luogo}</td>
                    <td>${rag[0].classe+rag[0].scuola.grado[0..0]?capitalize}</td>
                    <td>${rag[0].sezione}</td>
                    <td>${rag[0].scuola.descrizione}</td>
                    <#--Se nel db un intero è null, il resultset lo restituisce come zero-->
                    <td class="badge-squadra-support">
                        <@ui.squadraBadge squadra=rag[0].squadra style="list"/>
                    </td>
                    <td>${rag[0].laboratorio.descrizione}</td>
                    <td><#if rag[0].mensa>Sì<#else>No</#if></td>
                    <td><#if rag[0].saNuotare>Sì<#else>No</#if></td>
                    <td><#if rag[0].entrataAnticipata>Sì<#else>No</#if></td>
                    <td><#if rag[0].richieste??><img src="../img/octicons/check.svg" alt="presenti"><#else>
                            <img src="../img/octicons/x.svg" alt="assenti"></#if></td>
                    <td><#if rag[0].noteAlimentari??><img src="../img/octicons/check.svg"
                                                          alt="presenti"><#else><img
                                src="../img/octicons/x.svg" alt="assenti"></#if></td>
                    <td><#if rag[1]??>${rag[1].quota}&euro;<#else><img src="../img/octicons/x.svg"
                                                                       alt="assente"></#if></td>
                </tr>
            </#list>
            </tbody>
        </table>
</@ui.contentPanel>
<script type="text/javascript" src="https://cdn.datatables.net/plug-ins/1.10.19/dataRender/ellipsis.js"></script>
<@ui.iscrittiDataTable id="t_rag" ellipsisTargets="[2, 3, 4, 5, 6, 7, 10, 12]" searchTargets="[1, 8, 9, 13, 14, 15, 16, 17]" orderTargets="[1, 16, 17, 18]"/>
</@ui.page>