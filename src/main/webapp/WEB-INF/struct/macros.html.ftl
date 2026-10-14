<#macro avvisoMailNonValida>
    <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
        <p class="text-danger text-center font-weight-bold">La mail non esiste, non è scritta correttamente o è una
            e-mail temporanea! Inserisci una mail valida</p>
    </div>
</#macro>

<#macro presenzaSettimanale calendari>
    <@displayRow label="Presenza settimanale"><#list calendari as cal>
                <p>dal ${cal.daQuando+" al "+cal.AQuando}</p>
            </#list></@displayRow>
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
                <@displayRow label="Cognome"><p>${contatto.cognome}</p></@displayRow>
                <@displayRow label="Relazione"><p>${contatto.relazione}</p></@displayRow>
                <#if contatto.cellulare??>
                    <@displayRow label="Cellulare"><p>${contatto.cellulare}</p></@displayRow>
                </#if>
                <#if contatto.fisso??>
                    <@displayRow label="Fisso"><p>${contatto.fisso}</p></@displayRow>
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
            <@displayRow label="<i>Nome</i>"><p>${registrato.nome}</p></@displayRow>
            <@displayRow label="<i>Cognome</i>"><p>${registrato.cognome}</p></@displayRow>
            <@displayRow label="<i>Telefono</i>"><p>${registrato.telefono!""}</p></@displayRow>
            <@displayRow label="<i>Indirizzo</i>"><p>${indirizzo(registrato)}</p></@displayRow>
            <@attivitaGenitore activities=attgen/>
        </div>
    </div>
</#macro>

<#function indirizzo registrato capitalize=false includeLocalita=true>
    <#local via=registrato.via!"" localita=registrato.localita!"">
    <#if capitalize>
        <#local via=via?capitalize localita=localita?capitalize>
    </#if>
    <#return via+" "+(registrato.civico!"")+includeLocalita?then(" "+localita, "")>
</#function>

<#-- [] distinguishes an absent attribute from an explicitly empty string. Keep legacy output unescaped. -->
<#macro inputField id label type="text" name=id required=false autofocus=false placeholder=[] length=[] value=[] labelClass="" attributes={} labelFor=id groupClass="form-group" inputClass="form-control">
    <div class="${groupClass}">
        <label for="${labelFor}"<#if labelClass?has_content> class="${labelClass}"</#if>>${label}</label>
        <input class="${inputClass}"<#if type?has_content> type="${type}"</#if> id="${id}" name="${name}"<#if required> required</#if><#if autofocus> autofocus</#if><#if placeholder?is_string> placeholder="${placeholder}"</#if><#if length?is_string> data-parsley-length="${length}"</#if><#if value?is_string> value="${value}"</#if><#list attributes as key, val> ${key}="${val}"</#list>>
    </div>
</#macro>

<#macro textareaField id label value="" placeholder=[] labelClass="">
    <div class="form-group">
        <label for="${id}"<#if labelClass?has_content> class="${labelClass}"</#if>>${label}</label>
        <textarea class="form-control" rows="5" id="${id}" name="${id}"<#if placeholder?is_string> placeholder="${placeholder}"</#if>>${value}</textarea>
    </div>
</#macro>

<#macro checkboxField id label name=id checked=false required=false attributes={} labelClass="form-check-label">
    <div class="form-check">
        <input class="form-check-input" type="checkbox" id="${id}"<#if name?is_string> name="${name}"</#if><#if checked> checked</#if><#if required> required</#if><#list attributes as key, val> ${key}="${val}"</#list>>
        <label class="${labelClass}" for="${id}">${label}</label>
    </div>
</#macro>

<#macro displayRow label rowClass="row" labelClass="">
    <div class="${rowClass}">
        <div class="col-sm">
            <strong<#if labelClass?has_content> class="${labelClass}"</#if>>${label}</strong>
        </div>
        <div class="col-sm">
            <#nested>
        </div>
    </div>
</#macro>

<#macro presenzaField selected="C" label=" Presenza giornaliera " completo="Mattina e pomeriggio">
    <div class="form-group">
        <label>${label}</label> <br/>
        <#list [{"id": "completo", "value": "C", "label": completo}, {"id": "mattina", "value": "M", "label": "Solo mattina"}, {"id": "pomeriggio", "value": "P", "label": "Solo pomeriggio"}] as choice>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="presenza" id="${choice.id}" value="${choice.value}"<#if selected == choice.value> checked</#if>>
                <label class="form-check-label" for="${choice.id}">${choice.label}</label>
            </div>
        </#list>
    </div>
</#macro>

<#macro calendarioCheckbox cal checked>
    <div class="form-check">
        <input class="form-check-input" type="checkbox" value="${cal.idSettimana?c}" id="cal${cal.idSettimana?c}" name="cal"<#if checked> checked</#if>>
        <label class="form-check-label" for="cal${cal.idSettimana?c}"> Settimana dal ${cal.daQuando} al ${cal.AQuando} </label>
    </div>
</#macro>

<#macro partecipazioneField calendari label=" Partecipazione " editing=false lineBreak=false quoteTarget="" popupHeight=600>
    <div class="form-group">
        <label>${label}</label><#if lineBreak> <br/></#if>
        <#if editing>
            <#list calendari as cal, partecipa>
                <@calendarioCheckbox cal=cal checked=partecipa/>
            </#list>
        <#else>
            <#list calendari as cal>
                <@calendarioCheckbox cal=cal checked=true/>
            </#list>
        </#if>
        <#if quoteTarget?has_content>
            <a class="btn btn-secondary btn-sm mt-1" href="javascript:;"
               onClick="window.open('/RegistrazioneGrest/App/VisualizzaQuote?${quoteTarget}', '_blank', 'width=1000, height=${popupHeight?c}, status=yes, scrollbars=no, location=yes');">clicca
                qui per consultare le quote d'iscrizione</a>
        </#if>
    </div>
</#macro>

<#macro selectField id label labelClass="" attributes={} year=false yearIndent=32>
    <div class="form-group">
        <label for="${id}"<#if labelClass?has_content> class="${labelClass}"</#if>>${label}<#if year><b><@currentYear indent=yearIndent/></b></#if></label>
        <select id="${id}" class="form-control" name="${id}"<#list attributes as key, val> ${key}="${val}"</#list>>
            <#nested>
        </select>
    </div>
</#macro>

<#macro laboratorioOptions options selected=[]>
    <#list options as lab>
        <option value="${lab.id?c}"<#if selected?is_number && lab.id == selected> selected</#if>>${lab.descrizione}</option>
    </#list>
</#macro>

<#macro parrocchiaOptions options selected=[]>
    <#list options as par>
        <option value="${par.id?c}"<#if selected?is_number && par.id == selected> selected</#if>>Parrocchia di ${par.luogo?upper_case} - ${par.nome} </option>
    </#list>
</#macro>

<#macro circoloOptions options selected=[] beforeClose="">
    <#list options as cir>
        <option value="${cir.id?c}"<#if selected?is_number && cir.id == selected> selected</#if>>${cir.nome} (${cir.luogo}${beforeClose})</option>
    </#list>
</#macro>

<#macro scuolaOptions options selected=[]>
    <#list options as scu>
        <option value="${scu.id?c}"<#if selected?is_number && scu.id == selected> selected</#if>>Scuola ${scu.grado} di ${scu.descrizione}</option>
    </#list>
</#macro>

<#macro classeOptions selected="">
    <#list 1..5 as classe>
        <option value="${classe?c}"<#if classe?c == selected> selected</#if>>${classe?c}</option>
    </#list>
</#macro>

<#macro registrationSubmit open>
                <div class="pt-3 form-group" id="codiceForm">
                    <#if !open>
                        <#include "../user/sbloccoiscrizione.html.ftl">
                    <#else>
                        <div id="divbtnreg" class="row">
                            <input id="btnreg" class="form-control btn btn-primary" type="submit"
                                   value="Invia l'iscrizione"/>
                        </div>
                    </#if>
                </div>
</#macro>

<#macro submitButton label class="form-control btn btn-primary">
    <div class="pt-3 form-group">
        <input class="${class}" type="submit" value="${label}">
    </div>
</#macro>

<#macro tesseraField label help value=[] year=false yearIndent=32>
    <div class="form-group">
        <label for="nTessera">${label}<#if year><b><@currentYear indent=yearIndent/></b></#if></label>
        <input id="nTessera" name="nTessera" type="text" placeholder="Numero Tessera" class="form-control" data-parsley-length="[11, 11]" aria-describedby="helpNTessera"<#if value?is_string> value="${value}"</#if>>
        <small id="helpNTessera" class="form-text text-muted">${help}</small>
    </div>
</#macro>

<#macro tableHead columns class="">
    <thead<#if class?has_content> class="${class}"</#if>>
        <tr>
            <#list columns as column>
                <th<#list column.attributes as key, val> ${key}="${val}"</#list>>${column.label}</th>
            </#list>
        </tr>
    </thead>
</#macro>

<#macro assignmentSelect id name label options selected labelProperty pairedId=[] emptyOption=false>
    <label class="sr-only" for="${id}">${label}</label>
    <select class="custom-select mr-sm-2" id="${id}" name="${name}">
        <#if emptyOption>
            <option<#if pairedId?is_number> value="${pairedId?c}"</#if><#if selected == 0> selected</#if>></option>
        </#if>
        <#list options as option>
            <option value="<#if pairedId?is_number>${pairedId?c},</#if>${option.id?c}"<#if selected == option.id> selected</#if>>${option[labelProperty]}</option>
        </#list>
    </select>
</#macro>

<#macro responsabileToggle id name label checked>
    <div class="checkbox">
        <label class="sr-only" for="${id}">${label}</label>
        <input id="${id}" name="${name}" type="checkbox" data-toggle="toggle" data-off="No" data-on="Sì"<#if checked> checked</#if>>
    </div>
</#macro>

<#macro paymentRow paymentData action infoTarget sheetTarget popupTitle quota imageAlts=false>
<tr>
                        <td class="nome">
                            ${paymentData[0].nome}
                            <#-- form pagamento nascosto -->
                            <form id="form-pagamento-${paymentData[0].id?c}"
                                  action="/RegistrazioneGrest/App/${action}"
                                  method="POST">
                                <#if paymentData[1]>
                                <#-- ha pagato, quindi posso eliminare il pagamento -->
                                    <input type="hidden" name="deletePagamento" value="${paymentData[2].id?c}"/>
                                <#else>
                                <#-- non ha pagato, quindi posso aggiungere un nuovo pagamento -->
                                    <input type="hidden" name="addPagamento" value="${paymentData[0].id?c}"/>
                                </#if>
                            </form>
                            <#------------------------------>
                        </td>
                        <td class="cognome">${paymentData[0].cognome}</td>
                        <td><a href="javascript:" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=${infoTarget}&id=${paymentData[0].id?c}', '${popupTitle}', 'width=600, height=700, status, scrollbars=1, location');">
                                <img src="../img/octicons/search.svg"<#if imageAlts> alt="cerca"</#if>></a></td>
                        <td><a href="/RegistrazioneGrest/App/InfoDettaglio?target=${sheetTarget}&id=${paymentData[0].id?c}">
                                <img src="../img/octicons/file.svg"<#if imageAlts> alt="stampa scheda"</#if>></a></td>
                        <#if paymentData[1]>
                            <#--ha già pagato-->
                            <td class="text-center">
                                <span title="Evaso da ${paymentData[2].nomeRegistrato + " " + paymentData[2].cognomeRegistrato} il ${paymentData[2].data}">
                                    ${paymentData[2].ordineArrivo}
                                </span>
                            </td>
                            <td class="text-center">
                                <span>${paymentData[2].quota}&euro;</span>
                            </td>
                            <td class="text-center">
                                <button type="submit"
                                        form="form-pagamento-${paymentData[0].id?c}"
                                        class="btn btn-danger btn-sm">
                                    Elimina Pagamento
                                </button>
                            </td>
                        <#else>
                            <#--non ha già pagato-->
                            <td>
                                <label>
                                    <input type="number"
                                           class="form-control"
                                           name="ordineArrivo"
                                           form="form-pagamento-${paymentData[0].id?c}"
                                           placeholder="Ordine iscrizione"
                                           required>
                                </label>
                            </td>
                            <td>
                                <label>
                                    <input type="number"
                                           class="form-control"
                                           name="quota"
                                           form="form-pagamento-${paymentData[0].id?c}"
                                           value="${quota}"
                                           required>
                                </label>
                            </td>
                            <td class="text-center">
                                <button type="submit"
                                        form="form-pagamento-${paymentData[0].id?c}"
                                        class="btn btn-success btn-sm">
                                    Aggiungi Pagamento
                                </button>
                            </td>
                        </#if>
                    </tr>
</#macro>

<#macro iscrittoActions id infoTarget popupTitle modifyAction deleteAction href="javascript:" detailAlt="dettaglio">
    <a href="${href}" onClick="window.open('/RegistrazioneGrest/App/InfoDettaglio?target=${infoTarget}&id=${id?c}', '${popupTitle}', 'width=600, height=700, status, scrollbars=1, location');"><img src="../img/octicons/search.svg" alt="${detailAlt}"></a>
    <a href="/RegistrazioneGrest/App/${modifyAction}?id=${id?c}"><img src="../img/octicons/pencil.svg" alt="modifica"></a>
    <a href="/RegistrazioneGrest/App/${deleteAction}?id=${id?c}"><img src="../img/octicons/trashcan.svg" alt="elimina"></a>
</#macro>

<#macro squadraBadge squadra style="list">
    <#if squadra.id != 0>
        <#if style == "list">
            <div class="badge-squadra" style="background-color: ${squadra.colore}; margin-right: 5px;"></div> ${squadra.nome}
        <#else>
            <div class="badge-squadra" style="background-color:${squadra.colore}; "></div>
        </#if>
    </#if>
</#macro>

<#macro formPanel title containerClass="container mt-4 content" inlineTitle=false>
<div class="${containerClass}">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <#if inlineTitle>
                <h5 class="title text-center">${title}</h5>
            <#else>
                <div class="title text-center">
                    <h5>${title}</h5>
                </div>
            </#if>
            <#nested>
        </div>
    </div>
</div>
</#macro>

<#macro errorPanel title message titleClass="text-danger font-weight-bold">
<div class="container content">
    <div class="row vertical-center">
        <div class="col-md-12">
            <div class="text-center">
                <h2 class="${titleClass}">${title}</h2>
                <h3 class="font-italic">${message}</h3>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro dashboardLink href label rowClass="row mt-2" columnClass="col" buttonClass="btn btn-primary btn-block btn-lg">
<div class="${rowClass}">
    <div class="${columnClass}">
        <a href="${href}" class="${buttonClass}">${label}</a>
    </div>
</div>
</#macro>

<#macro registrationSwitch id label checked>
<div class="col">
    <div class="form-check form-check-inline">
        <div class="custom-control custom-switch">
            <input class="custom-control-input" id="${id}" type="checkbox"<#if checked> checked</#if>>
            <label class="custom-control-label" for="${id}">${label}</label>
        </div>
    </div>
</div>
</#macro>

<#macro weeklyReportLinks settimane target>
<div class="row mt-2">
    <div class="col">
        <div class="text-center">
            <div class="btn-group btn-group-lg" role="group">
                <#list settimane as set>
                    <a href="/RegistrazioneGrest/App/Stampa?target=${target}&idSet=${set.idSettimana?c}" class="btn btn-info"> ${set.idSettimana?c} SETTIMANA </a>
                </#list>
            </div>
        </div>
    </div>
</div>
</#macro>

<#macro statisticBadge label count>
<li class="list-group-item d-flex justify-content-between align-items-center">
    ${label}
    <span class="badge badge-primary">${count}</span>
</li>
</#macro>

<#macro statisticChart element selector variable type data mapData label palette labelPrefix="" labelSuffix="">    var ${element} = $('${selector}');
    var ${variable} = new Chart(${element}, {
        type: '${type}',
        data: {
            labels: [<#if mapData><#list data as key, val>'${labelPrefix}${key}${labelSuffix}', </#list><#else><#list data as item>'${labelPrefix}${item[0]}${labelSuffix}', </#list></#if>],
            datasets: [{
                data: [<#if mapData><#list data as key, val>'${val}', </#list><#else><#list data as item>'${item[1]}', </#list></#if>],
                label: '${label}',
                backgroundColor: palette('${palette}', ${data?size}).map(function (hex) {
                    return '#' + hex;
                }),
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
</#macro>

<#macro reportEmergencyPhones registrato contatti>
<i>R </i><b>${registrato.telefono!""}</b> -
<#list contatti[0..*4] as cu>
    <i>${cu.relazione?capitalize+" "}</i>
    <b>${(cu.cellulare!)+" "+(cu.fisso!)}</b> -
</#list>
</#macro>

<#macro presenceWeekCells >
<#list 1..5 as day>
    <td>M</td>
    <td>P</td>
</#list>
</#macro>

<#macro quoteTable prices mensa=[]>
<table class="table table-bordered">
    <#list ["per tutto il periodo", "per tre settimane", "per due settimane", "per una settimana"] as period>
        <tr>
            <td>${period}</td>
            <td><#if mensa?has_content>senza mensa </#if>${prices[period?index]}€</td>
            <#if mensa?has_content>
                <td>con mensa ${mensa[period?index]}€</td>
            </#if>
        </tr>
    </#list>
</table>
</#macro>

<#macro identityRows person capitalize=false>
    <@displayRow label="Nome"><p><#if capitalize>${person.nome?capitalize}<#else>${person.nome}</#if></p></@displayRow>
    <@displayRow label="Cognome"><p><#if capitalize>${person.cognome?capitalize}<#else>${person.cognome}</#if></p></@displayRow>
    <@displayRow label="Data di nascita"><p>${person.dataNascita?string["dd/MM/yyyy"]}</p></@displayRow>
</#macro>

<#macro presenzaDescription presenza>
<#if presenza == "M">
    Mattina
<#elseif presenza == "P">
    Pomeriggio
<#elseif presenza == "C">
    Completo
</#if>
</#macro>

<#macro fasciaEtaDescription fascia>
<#if fascia == "PIC">
    Piccoli
<#elseif fascia == "GRA">
    Grandi
<#elseif fascia == "TER">
    3 Media
</#if>
</#macro>

<#macro parrocchiaCircoloRows person>
    <@displayRow label="Parrocchia"><p>${person.parrocchia.nome+" - "+person.parrocchia.luogo}</p></@displayRow>
    <@displayRow label="Circolo NOI"><p>${person.circolo.nome+" - "+person.circolo.luogo}</p></@displayRow>
</#macro>

<#macro tesseraRow person>
    <#if person.NTessera??>
        <@displayRow label="Numero tessera Circolo NOI"><p>${person.NTessera}</p></@displayRow>
    </#if>
</#macro>

<#macro noteRows person lowerCase=false>
    <#if person.richieste??>
        <@displayRow label="Richieste"><p><#if lowerCase>${person.richieste?lower_case}<#else>${person.richieste}</#if></p></@displayRow>
    </#if>
    <#if person.noteAlimentari??>
        <@displayRow label="Note Alimentari"><p><#if lowerCase>${person.noteAlimentari?lower_case}<#else>${person.noteAlimentari}</#if></p></@displayRow>
    </#if>
</#macro>

<#macro paymentDetails pagamento>
    <@displayRow label="Quota pagamento"><p>${pagamento.quota}</p></@displayRow>
    <@displayRow label="Evaso da"><p>${pagamento.registrato.nome+" "+pagamento.registrato.cognome}</p></@displayRow>
</#macro>

<#macro detailContacts registrato contatti=false accompagnatori=false attgen=[]>
    <#if contatti?is_sequence>
        <@contattiUrgenze contatti=contatti/>
    </#if>
    <#if accompagnatori?is_sequence>
        <@accompagnatoriAutorizzati accompagnatori=accompagnatori/>
    </#if>
    <@informazioniRegistrato registrato=registrato attgen=attgen/>
</#macro>

<#macro attivitaGenitore activities>
    <#if activities?size != 0>
        <hr>
        <strong> Attività genitore </strong>
        <#list activities as attivita>
            <div class="row">
                <div class="col-sm">
                    <p>${attivita.descrizione}</p>
                </div>
            </div>
        </#list>
    </#if>
</#macro>

<#macro fasciaEtaField selected="PIC">
    <div class="form-group">
        <label> Fascia età ragazzi con cui vorrei compiere il servizio come animatore </label> <br/>
        <#list [{"id": "PIC", "label": "Piccoli (1°,2°,3°,4° della scuola primaria)"}, {"id": "GRA", "label": "Grandi (5° primaria, 1°,2° media)"}, {"id": "TER", "label": "Animatori gruppo 3° media"}] as fascia>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="fasciaEtaRagazzi" id="${fascia.id}" value="${fascia.id}"<#if selected == fascia.id> checked</#if>>
                <label class="form-check-label" for="${fascia.id}">${fascia.label}</label>
            </div>
        </#list>
    </div>
</#macro>

<#macro dataTable id><script>
    $(document).ready(function () {
        $('#${id}').DataTable({
<#nested>        });
    });
</script>
</#macro>

<#macro identityFields cognome=[] nome=[] dataNascita=[] nomeFirst=false>
    <#list nomeFirst?then(["nome", "cognome"], ["cognome", "nome"]) as field>
        <@inputField id=field label=field?cap_first required=true placeholder=field?cap_first autofocus=(field == "nome") length="[2, 50]" value=(field == "nome")?then(nome, cognome)/>
    </#list>
    <@inputField id="dataNascita" label="Data di nascita" type="date" required=true value=dataNascita/>
</#macro>

<#macro presenzaRow presenza label="Presenza giornaliera">
    <@displayRow label=label><p><@presenzaDescription presenza=presenza/></p></@displayRow>
</#macro>

<#macro schoolRows person label value>
    <@displayRow label="Scuola"><p>${person.scuola.descrizione+" - "+person.scuola.grado}</p></@displayRow>
    <@displayRow label=label><p>${value}</p></@displayRow>
</#macro>

<#macro yesNoRow label value>
    <@displayRow label=label><p><#if value>Sì<#else>No</#if></p></@displayRow>
</#macro>

<#macro dashboardIscrittoRow person sheetTarget action editable paid=[] printAlt=[] paidAlts=false>
    <tr>
        <th scope="row"><@squadraBadge squadra=person.squadra style="dashboard"/></th>
        <td>${person.nome}</td>
        <td>${person.cognome}</td>
        <td class="text-center">
            <a href="/RegistrazioneGrest/App/InfoDettaglio?target=${sheetTarget}&id=${person.id?c}"><img src="../img/octicons/file.svg"<#if printAlt?is_string> alt="${printAlt}"</#if>></a>
        </td>
        <#if paid?is_boolean>
            <td class="text-center">
                <#if paid>
                    <img src="../img/octicons/check.svg"<#if paidAlts> alt="spunta"</#if>>
                <#else>
                    <img src="../img/octicons/x.svg"<#if paidAlts> alt="croce"</#if>>
                </#if>
            </td>
        </#if>
        <td class="text-center">
            <#if editable>
                <a href="/RegistrazioneGrest/App/Modifica${action}?id=${person.id?c}"><img src="../img/octicons/pencil.svg" alt="modifica"></a>
            </#if>
        </td>
        <td class="text-center">
            <#if editable>
                <a href="/RegistrazioneGrest/App/Elimina${action}?id=${person.id?c}"><img src="../img/octicons/trashcan.svg" alt="elimina"></a>
            </#if>
        </td>
    </tr>
</#macro>

<#macro contactFormPanel title titleClass="title pb-1">
<div class="container content">
    <div class="mt-5">
        <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white">
            <div class="${titleClass}">
                <h4>${title}</h4>
            </div>
            <#nested>
        </div>
    </div>
</div>
</#macro>

<#macro parentSignature >
    <div class="mt-3 shadow pt-2 pl-3 pr-3">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id='priv'/>
            <label for='priv' class="form-check-label">
                I sottoscritti genitori acconsentono alle dichiarazioni presentate in fase di
                registrazione, e dichiarano altresì che le informazioni inserite in fase di registrazione sono
                veritiere.
            </label>
        </div>
        <p class="text-center font-weight-light font-italic mb-4"> Firme di entrambi i genitori </p>
        <hr class="pb-2 border-dark"/>
    </div>
</#macro>

<#macro page navbar=true>
<#include "header.html.ftl">
<#if navbar>
    <#include "navbar.html.ftl">
</#if>
<#nested>
<#include "footer.html.ftl">
</#macro>

<#macro contentPanel containerClass="container-fluid mt-5 content" panelClass="container shadow pt-2 pl-3 pr-3 pb-2 bg-white" responsive=false>
    <div class="${containerClass}">
        <div class="${panelClass}">
            <#if responsive><div class="table-responsive"></#if>
            <#nested>
            <#if responsive></div></#if>
        </div>
    </div>
</#macro>

<#macro reportTable wide=false>
    <#if wide>
        <div class="container-block">
            <div class="mr-2 ml-2">
                <table class="table table-bordered pl-2 pr-2 table-sm">
                    <#nested>
                </table>
            </div>
        </div>
    <#else>
        <div class="container">
            <table class="table table-bordered table-sm">
                <#nested>
            </table>
        </div>
    </#if>
</#macro>

<#macro currentYear indent=32><script type="text/javascript">var theDate = new Date();
${""?left_pad(indent)}document.write(theDate.getFullYear());</script></#macro>

<#macro healthDeclaration >
<li>che il/la proprio/a figlio/a gode di buona salute e che pertanto può affrontare le
                        attività ludico-sportive previste; in caso contrario, si impegnano a chiederne
                        l'esonero al momento della conferma dell'iscrizione;
                    </li>
</#macro>

<#macro parentDeclarations >
<li>di essere consapevoli che la responsabilità della Parrocchia e del Circolo Noi di Balconi nei
                        confronti dei
                        ragazzi e la relativa copertura assicurativa inizia e termina negli orari indicati; non c'è
                        copertura per gli spostamenti da e verso l'abitazione;
                    </li>
                    <li>di autorizzare i responsabili del Grest e i loro collaboratori ad assumere le iniziative che
                        riterranno necessarie per garantire la sicurezza dei partecipanti e la buona riuscita delle
                        attività del Grest;
                    </li>
                    <li>di non ritenere gli organizzatori del Grest responsabili per eventuali furti di biciclette o
                        di oggetti di qualsiasi valore; i proprietari dovranno pertanto provvedere ad eventuali
                        sistemi di protezione o di custodia;
                    </li>
                    <li>di essere informati, ai sensi e per gli effetti di cui all'art. 13 del D.Lgs 196/2003, che i
                        dati personali raccolti saranno trattati, anche con strumenti informatici, esclusivamente
                        nell'ambito dell'attività del Grest di Balconi e concedono la liberatoria alle
                        riprese video/fotografiche esclusivamente nell'ambito dell'attività del Grest di
                        Balconi.
                    </li>
</#macro>

<#macro weeklyAccordion settimane accordionId targetPrefix controlPrefix=targetPrefix>
<div id="${accordionId}">
    <#list settimane as set>
        <div class="card">
            <div class="card-header" id="heading${targetPrefix}${set.idSettimana?c}">
                <h5 class="mb-0 text-center">
                    <button class="btn btn-link" data-toggle="collapse" data-target="#${targetPrefix}${set.idSettimana?c}" aria-expanded="false" aria-controls="${controlPrefix}${set.idSettimana?c}">
                        Settimana dal ${set.daQuando} al ${set.AQuando}
                    </button>
                </h5>
            </div>
            <div id="${targetPrefix}${set.idSettimana?c}" class="collapse" aria-labelledby="heading${targetPrefix}${set.idSettimana?c}" data-parent="#${accordionId}">
                <div class="card-body">
                    <#nested set>
                </div>
            </div>
        </div>
    </#list>
</div>
</#macro>

<#macro assignmentScripts id responsible=false column=2>
<@dataTable id=id>
            paging: false,
<#if responsible>
            columnDefs: [
                {orderable: false, targets: [2, 3]},
                {searchable: false, targets: [2, 3]},
                {width: "10%", targets: 3}
            ],
<#else>
            columnDefs: [{targets: [${column?c}], orderable: false}],
</#if>
            info: false
</@dataTable>
<script src="../js/salvasolomodificato${responsible?then("animatori", "ragter")}.js"></script>
</#macro>

<#macro iscrittiDataTable id ellipsisTargets searchTargets orderTargets="">
<@dataTable id=id>
            columnDefs: [
                {
                    targets: ${ellipsisTargets},
                    render: $.fn.dataTable.render.ellipsis(15, true)
                },
                {
                    targets: ${searchTargets},
<#if orderTargets?has_content>
                    searchable: false
                },
                {
                    targets: ${orderTargets},
<#else>
                    searchable: false,
</#if>
                    orderable: false
                }
            ],
            info: false
</@dataTable>
</#macro>
