<#import "../struct/macros.html.ftl" as ui>
<@ui.page navbar=true>
<@ui.formPanel title="Modifica account" containerClass="container pt-4 content" inlineTitle=false>
            <#if INVALIDMAIL??>
                <@ui.avvisoMailNonValida/>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaUtente" method="POST" data-parsley-validate>
                <@ui.inputField id="nome" label="Nome" required=true autofocus=true placeholder="Nome" length="[2, 50]" value="${registrato.nome}"/>
                <@ui.inputField id="cognome" label="Cognome" required=true placeholder="Cognome" length="[2, 50]" value="${registrato.cognome}"/>
                <@ui.inputField id="email" label="E-Mail" type="email" name="mail" required=true placeholder="E-Mail" value="${registrato.mail}"/>
                <@ui.inputField id="telefono" label="Numero di telefono" type="" required=(tipoUt == 3) placeholder="Cellulare personale" length="[10,10]" value=(registrato.telefono)![] attributes={"data-parsley-type": "digits"}/>
                <@ui.inputField id="localita" label="Localita" required=(tipoUt == 3) placeholder="Localit&agrave" value=(registrato.localita)![]/>
                <@ui.inputField id="via" label="Via" required=(tipoUt == 3) placeholder="Via" value=(registrato.via)![]/>
                <@ui.inputField id="civico" label="Numero civico" required=(tipoUt == 3) placeholder="Numero Civico" value=(registrato.civico)![]/>
                <@ui.submitButton label="Conferma"/>
            </form>
</@ui.formPanel>
</@ui.page>
