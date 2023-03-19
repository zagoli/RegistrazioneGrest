<#include "../struct/header.html.ftl">
<div class="container mt-3 content">
    <div class="shadow pt-2 pl-3 pr-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong>Id Ragazzo</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.id?c}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Nome</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.nome?capitalize}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cognome</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.cognome?capitalize}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Data di nascita</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.dataNascita?string["dd/MM/yyyy"]}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Presenza giornaliera</strong>
            </div>
            <div class="col-sm">
                <p>
                    <#if ragazzo.presenza == "M">
                        Mattina
                    <#elseif ragazzo.presenza == "P">
                        Pomeriggio
                    <#elseif ragazzo.presenza == "C">
                        Completo
                    </#if>
                </p>
            </div>
        </div> 
        <div class="row">
            <div class="col-sm">
                <strong>Laboratorio</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.laboratorio.descrizione}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Parrocchia</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.parrocchia.nome+" - "+ragazzo.parrocchia.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Circolo NOI</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.circolo.nome+" - "+ragazzo.circolo.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Scuola</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.scuola.descrizione+" - "+ragazzo.scuola.grado}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Classe e sezione</strong>
            </div>
            <div class="col-sm">
                <p>${ragazzo.classe+ragazzo.sezione}</p>
            </div>
        </div>
        <#if ragazzo.nTessera??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero tessera Circolo NOI</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.nTessera}</p>
                </div>
            </div>
        </#if>

        <div class="row">
            <div class="col-sm">
                <strong>Presenza settimanale</strong>
            </div>
        </div>
        <#list calendari as cal>
            <div class="row">
                <div class="col-sm"></div>
                <div class="col-sm">
                    <p>dal ${cal.daQuando+" al "+cal.aQuando}</p>
                </div>
            </div>
        </#list>
    
        <#if ragazzo.richieste??>
            <div class="row">
                <div class="col-sm">
                    <strong>Richieste</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.richieste?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if ragazzo.noteAlimentari??>
            <div class="row">
                <div class="col-sm">
                    <strong>Note Alimentari</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.noteAlimentari?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if ragazzo.squadra.id!=0>
            <div class="row">
                <div class="col-sm">
                    <strong>Squadra</strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.squadra.nome}</p>
                </div>
            </div>
        </#if>
        <div class="row">
            <div class="col-sm">
                <strong>Entrata anticipata</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.entrataAnticipata>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Usufruisce della mensa</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.mensa>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Sa nuotare</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.saNuotare>Sì<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Ha un'altro fratello/sorella iscritto/a</strong>
            </div>
            <div class="col-sm">
                <p><#if ragazzo.fratelloIscritto>Sì<#else>No</#if></p>
            </div>
        </div>
        <#if pagamento??>
        <div class="row">
            <div class="col-sm">
                <strong>Quota pagamento</strong>
            </div>
            <div class="col-sm">
                <p>${pagamento.quota}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Evaso da</strong>
            </div>
            <div class="col-sm">
                <p>${pagamento.registrato.nome+" "+pagamento.registrato.cognome}</p>
            </div>
        </div>
        </#if>
    </div>
    
    <!--contatti urgenze-->
    <#if cu??>
    <div class="shadow pt-2 pl-3 pr-3 mt-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong class="text-danger">Contatti telefonici Urgenze</strong>
            </div>
        </div>
        <#list cu as cu>
            <div class="pt-2 pl-3 pr-3 mb-2">
                <div class="row">
                    <div class="col-sm">
                        <strong>Nome</strong>
                    </div>
                    <div class="col-sm">
                        <p>${cu.nome}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm">
                        <strong>Cognome</strong>
                    </div>
                    <div class="col-sm">
                        <p>${cu.cognome}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm">
                        <strong>Relazione</strong>
                    </div>
                    <div class="col-sm">
                        <p>${cu.relazione}</p>
                    </div>
                </div>
                <#if cu.cellulare??>
                <div class="row">
                    <div class="col-sm">
                        <strong>Cellulare</strong>
                    </div>
                    <div class="col-sm">
                        <p>${cu.cellulare}</p>
                    </div>
                </div>
                </#if>
                <#if cu.fisso??>
                <div class="row">
                    <div class="col-sm">
                        <strong>Fisso</strong>
                    </div>
                    <div class="col-sm">
                        <p>${cu.fisso}</p>
                    </div>
                </div>
                </#if>
            </div>
            <#sep>
            <hr/>
        </#list>
    </div>
    </#if>
    
    <!--accompagnatori-->
    <#if accompagnatori??>
    <div class="shadow pt-2 pl-3 pr-3 mt-3 bg-white">
        <div class="row mb-3">
            <div class="col-sm">
                <strong>Accompagnatori autorizzati</strong>
            </div>
        </div>
        <#list accompagnatori as acc>
            <div class="row">
                <div class="col-sm">
                    <p>${acc.nome+" "+acc.cognome}</p>
                </div>
            </div>
            <#sep>
            <hr/>
        </#list>
    </div>
    </#if>
    
    <!--info registrato-->
    <div class="shadow pt-2 pl-3 pr-3 mt-3">
        <div class="text-secondary">
            <div class="row">
                <div class="col-sm">
                    <strong>Info registrato</strong>
                </div>
            </div>
            <div class="row mt-2">
                <div class="col-sm">
                    <strong><i>E-Mail</i></strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.registrato.mail}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Nome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.registrato.nome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Cognome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.registrato.cognome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Telefono</i></strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.registrato.telefono}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Indirizzo</i></strong>
                </div>
                <div class="col-sm">
                    <p>${ragazzo.registrato.via+" "+ragazzo.registrato.civico+" "+ragazzo.registrato.localita}</p>
                </div>
            </div>
            <hr>
            <strong> Attività genitore </strong>
            <#list attgen as attgen>
            <div class="row">
                <div class="col-sm">
                    <p>${attgen.descrizione}</p>
                </div>
            </div>
            </#list>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
