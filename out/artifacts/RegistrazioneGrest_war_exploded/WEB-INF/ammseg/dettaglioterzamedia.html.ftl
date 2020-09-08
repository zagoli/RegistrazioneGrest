<#include "../struct/header.html.ftl">
<div class="container mt-3 content">
    <div class="shadow pt-2 pl-3 pr-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong>Id Terza media</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.id}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Nome</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.nome?capitalize}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Cognome</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.cognome?capitalize}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Data di nascita</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.dataNascita?string["dd/MM/yyyy"]}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Presenza giornaliera</strong>
            </div>
            <div class="col-sm">
                <p>
                    <#if terzamedia.presenza = "M"> 
                    Mattina
                    <#elseif terzamedia.presenza = "P"> 
                    Pomeriggio
                    <#elseif terzamedia.presenza = "C"> 
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
                <p>${terzamedia.laboratorio.descrizione}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Parrocchia</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.parrocchia.nome+" - "+terzamedia.parrocchia.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Circolo NOI</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.circolo.nome+" - "+terzamedia.circolo.luogo}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Scuola</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.scuola.descrizione+" - "+terzamedia.scuola.grado}</p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Sezione</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.sezione}</p>
            </div>
        </div>
        <#if terzamedia.nTessera??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero tessera Circolo NOI</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.nTessera}</p>
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
        
        <div class="row">
            <div class="col-sm">
                <strong>E-mail</strong>
            </div>
            <div class="col-sm">
                <p>${terzamedia.mail}</p>
            </div>
        </div>
        <#if terzamedia.cellulare??>
            <div class="row">
                <div class="col-sm">
                    <strong>Numero di cellulare</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.cellulare}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.richieste??>
            <div class="row">
                <div class="col-sm">
                    <strong>Richieste</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.richieste?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.noteAlimentari??>
            <div class="row">
                <div class="col-sm">
                    <strong>Note Alimentari</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.noteAlimentari?lower_case}</p>
                </div>
            </div>
        </#if>
        <#if terzamedia.squadra??>
            <div class="row">
                <div class="col-sm">
                    <strong>Squadra</strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.squadra}</p>
                </div>
            </div>
        </#if>
        <div class="row">
            <div class="col-sm">
                <strong>Sa nuotare</strong>
            </div>
            <div class="col-sm">
                <p><#if terzamedia.saNuotare>S&igrave;<#else>No</#if></p>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <strong>Ha partecipato alla festa del passaggio a Balconi</strong>
            </div>
            <div class="col-sm">
                <p><#if terzamedia.festaPassaggio>S&igrave;<#else>No</#if></p>
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
                    <p>${terzamedia.registrato.mail}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Nome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.registrato.nome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Cognome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.registrato.cognome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Telefono</i></strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.registrato.telefono}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Indirizzo</i></strong>
                </div>
                <div class="col-sm">
                    <p>${terzamedia.registrato.via+" "+terzamedia.registrato.civico+" "+terzamedia.registrato.localita}</p>
                </div>
            </div>
            <hr>
            <strong> Attivit&agrave; genitore </strong>
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
