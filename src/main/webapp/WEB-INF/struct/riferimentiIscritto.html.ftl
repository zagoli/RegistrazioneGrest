<#macro contattiUrgenze contatti>
    <div class="shadow py-2 pl-3 pr-3 mt-3 bg-white">
        <div class="row">
            <div class="col-sm">
                <strong class="text-danger">Contatti telefonici Urgenze</strong>
            </div>
        </div>
        <#list contatti as contatto>
            <div class="pt-2 mb-2">
                <div class="row">
                    <div class="col-sm">
                        <strong>Nome</strong>
                    </div>
                    <div class="col-sm">
                        <p>${contatto.nome}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm">
                        <strong>Cognome</strong>
                    </div>
                    <div class="col-sm">
                        <p>${contatto.cognome}</p>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm">
                        <strong>Relazione</strong>
                    </div>
                    <div class="col-sm">
                        <p>${contatto.relazione}</p>
                    </div>
                </div>
                <#if contatto.cellulare??>
                    <div class="row">
                        <div class="col-sm">
                            <strong>Cellulare</strong>
                        </div>
                        <div class="col-sm">
                            <p>${contatto.cellulare}</p>
                        </div>
                    </div>
                </#if>
                <#if contatto.fisso??>
                    <div class="row">
                        <div class="col-sm">
                            <strong>Fisso</strong>
                        </div>
                        <div class="col-sm">
                            <p>${contatto.fisso}</p>
                        </div>
                    </div>
                </#if>
            </div>
            <#sep>
                <hr/>
        </#list>
    </div>
</#macro>

<#macro accompagnatoriAutorizzati accompagnatori>
    <div class="shadow py-2 pl-3 pr-3 mt-3 bg-white">
        <div class="row mb-3">
            <div class="col-sm">
                <strong>Accompagnatori autorizzati</strong>
            </div>
        </div>
        <#list accompagnatori as accompagnatore>
            <div class="row">
                <div class="col-sm">
                    <p>${accompagnatore.nome+" "+accompagnatore.cognome}</p>
                </div>
            </div>
            <#sep>
                <hr/>
        </#list>
    </div>
</#macro>

<#macro informazioniRegistrato registrato attgen=[]>
    <div class="shadow py-2 pl-3 pr-3 mt-3">
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
                    <p>${registrato.mail}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Nome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${registrato.nome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Cognome</i></strong>
                </div>
                <div class="col-sm">
                    <p>${registrato.cognome}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Telefono</i></strong>
                </div>
                <div class="col-sm">
                    <p>${registrato.telefono}</p>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <strong><i>Indirizzo</i></strong>
                </div>
                <div class="col-sm">
                    <p>${(registrato.via!"")+" "+(registrato.civico!"")+" "+(registrato.localita!"")}</p>
                </div>
            </div>
            <@attivitaGenitore attivita=attgen/>
        </div>
    </div>
</#macro>

<#macro attivitaGenitore attivita>
    <#if attivita?size != 0>
        <hr>
        <strong> Attività genitore </strong>
        <#list attivita as disponibilita>
            <div class="row">
                <div class="col-sm">
                    <p>${disponibilita.descrizione}</p>
                </div>
            </div>
        </#list>
    </#if>
</#macro>
