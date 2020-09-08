<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container pt-4 content">
    <div class="shadow pt-2 pl-3 pr-3 pb-2 bg-white vertical-center">
        <div class="container">
            <div class="title text-center">
                <h5>Modifica account</h5>
            </div>
            <#if INVALIDMAIL??>
                <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
                    <p class="text-danger text-center font-weight-bold">La mail non esiste, non &egrave; scritta correttamente o &egrave; una e-mail temporanea! Inserisci una mail valida</p>
                </div>
            </#if>
            <form action="/RegistrazioneGrest/App/ModificaUtente" method="POST" data-parsley-validate>
                <div class="form-group">
                    <label for="nome"> Nome </label>
                        <input class="form-control" type="text" id ="nome" required name="nome" placeholder="Nome" autofocus data-parsley-length="[2, 50]" value="${registrato.nome}"> 
                </div>
                <div class="form-group">
                    <label for="cognome"> Cognome </label> 
                        <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome" data-parsley-length="[2, 50]" value="${registrato.cognome}"> 
                </div>
                <div class="form-group">
                    <label for="email"> E-Mail </label> 
                        <input class="form-control" type="email" required id="email" name="mail" placeholder="E-Mail" value="${registrato.mail}"> 
                </div>
                <div class="form-group">
                    <label for="telefono"> Numero di telefono </label> 
                        <input class="form-control" data-parsley-type="digits" data-parsley-length="[10,10]" <#if tipoUt = 3>required</#if> id="telefono" name="telefono" placeholder="Cellulare personale" <#if registrato.telefono??>value="${registrato.telefono}"</#if>> 
                </div>
                <div class="form-group">
                    <label for="localita"> Localita </label> 
                        <input class="form-control" type="text" <#if tipoUt = 3>required</#if> id="localita" name="localita" placeholder="Localit&agrave" <#if registrato.localita??>value="${registrato.localita}"</#if>> 
                </div>
                <div class="form-group">
                    <label for="via"> Via </label> 
                        <input class="form-control" type="text" <#if tipoUt = 3>required</#if> id="via" name="via" placeholder="Via" <#if registrato.via??>value="${registrato.via}"</#if>> 
                </div>
                <div class="form-group">
                    <label for="civico"> Numero civico </label> 
                        <input class="form-control" type="text" <#if tipoUt = 3>required</#if> id="civico" name="civico" placeholder="Numero Civico" <#if registrato.civico??>value="${registrato.civico}"</#if>> 
                </div>
                <div class="pt-3 form-group">
                    <input class="form-control btn btn-primary" type="submit" value="Conferma">
                </div>
            </form>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">
