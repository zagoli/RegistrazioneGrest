<#macro avvisoMailNonValida>
    <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
        <p class="text-danger text-center font-weight-bold">La mail non esiste, non è scritta correttamente o è una
            e-mail temporanea! Inserisci una mail valida</p>
    </div>
</#macro>

<#macro presenzaSettimanale calendari>
    <div class="row">
        <div class="col-sm">
            <strong>Presenza settimanale</strong>
        </div>
        <div class="col-sm">
            <#list calendari as cal>
                <p>dal ${cal.daQuando+" al "+cal.AQuando}</p>
            </#list>
        </div>
    </div>
</#macro>

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
                    <p>${registrato.via+" "+registrato.civico+" "+registrato.localita}</p>
                </div>
            </div>
            <#if attgen?size != 0>
                <hr>
                <strong> Attività genitore </strong>
                <#list attgen as attivita>
                    <div class="row">
                        <div class="col-sm">
                            <p>${attivita.descrizione}</p>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
</#macro>
