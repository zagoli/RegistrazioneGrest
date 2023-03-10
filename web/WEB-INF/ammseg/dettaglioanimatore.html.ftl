<#include "../struct/header.html.ftl">
<div class="container mt-3 content">
    <div class="shadow pt-2 pl-3 pr-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong>Id Animatore</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.id}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Nome</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.nome?capitalize}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cognome</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.cognome?capitalize}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Data di nascita</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.dataNascita?string["dd/MM/yyyy"]}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Codice Fiscale</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.codiceFiscale}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Presenza giornaliera</strong>
            </div>
            <div class="col-sm">
                <p>
                    <#if animatore.presenza == "M">
                        Mattina
                    <#elseif animatore.presenza == "P">
                        Pomeriggio
                    <#elseif animatore.presenza == "C">
                        Completo
                    </#if>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cellulare personale</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.cellulare}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Fascia età ragazzi</strong>
            </div>
            <div class="col-sm">
                <p>
                    <#if animatore.fasciaEtaRagazzi == "PIC">
                        Piccoli
                    <#elseif animatore.fasciaEtaRagazzi == "GRA">
                        Grandi
                    <#elseif animatore.fasciaEtaRagazzi == "TER">
                        3 Media
                    </#if>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>E-mail</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.mail}</p>
            </div>
        </div>  
        <div class="row">
            <div class="col-sm">
                <strong>Laboratorio</strong>
            </div>
            <div class="col-sm">
                <p <#if animatore.responsabileLaboratorio>class="mark"</#if>>${animatore.laboratorio.descrizione}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Parrocchia</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.parrocchia.nome+" - "+animatore.parrocchia.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Circolo NOI</strong>
            </div>
            <div class="col-sm">
                <p>${animatore.circolo.nome+" - "+animatore.circolo.luogo}</p>
            </div>
        </div>
        <#if animatore.nTessera??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero tessera Circolo NOI</strong>
                </div>
                <div class="col-sm">
                    <p>${animatore.nTessera}</p>
                </div>
            </div>
        </#if>
        <#if animatore.squadra.id!=0>
            <div class="row">
                <div class="col-sm">
                    <strong>Squadra</strong>
                </div>
                <div class="col-sm">
                    <p <#if animatore.responsabileSquadra>class="mark"</#if>>${animatore.squadra.nome}</p>
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
                    <p>${animatore.registrato.mail}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Nome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${animatore.registrato.nome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Cognome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${animatore.registrato.cognome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Telefono</i></strong>
                </div>
                <div class="col-sm">
                    <p>${animatore.registrato.telefono}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Indirizzo</i></strong>
                </div>
                <div class="col-sm">
                    <p>${animatore.registrato.via+" "+animatore.registrato.civico+" "+animatore.registrato.localita}</p>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
