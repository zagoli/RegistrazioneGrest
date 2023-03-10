<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
    <#if !DONE>
        <div class="container mt-4 content">
            <div class="shadow pt-2 pl-3 pr-3 bg-white vertical-center">
                <div class="container">
                    <div class="title text-center">
                        <h5>Registrazione nuovo utente</h5>
                    </div>
                    <#if INVALIDMAIL??>
                        <div class="container border border-danger rounded pt-2 pl-3 pr-3 mb-2 bg-warning">
                            <p class="text-danger text-center font-weight-bold">La mail non esiste, non è scritta correttamente o è una e-mail temporanea! Inserisci una mail valida</p>
                        </div>
                    </#if>
                    <form action="/RegistrazioneGrest/App/RegistraUtente" method="POST" data-parsley-validate>
                        <div class="form-group">
                            <label for="nome"> Nome </label>
                                <input class="form-control" type="text" id ="nome" required name="nome" placeholder="Nome" autofocus data-parsley-length="[2, 50]"> 
                        </div>
                        <div class="form-group">
                            <label for="cognome"> Cognome </label> 
                                <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome" data-parsley-length="[2, 50]">
                        </div>
                        <div class="form-group">
                            <label for="email"> E-Mail </label>
                            <input class="form-control" type="email" required id="email" name="mail"
                                   placeholder="E-Mail">
                        </div>
                        <div class="form-group">
                            <label for="telefono"> Numero di telefono </label>
                            <input class="form-control" data-parsley-type="digits" data-parsley-length="[10, 10]"
                                   required id="telefono" name="telefono" placeholder="Cellulare personale">
                        </div>
                        <div class="form-group">
                            <label for="localita"> Località </label>
                            <input class="form-control" type="text" required id="localita" name="localita"
                                   placeholder="Localit&agrave">
                        </div>
                        <div class="form-group">
                            <label for="via"> Via </label>
                            <input class="form-control" type="text" required id="via" name="via" placeholder="Via">
                        </div>
                        <div class="form-group">
                            <label for="civico"> Numero civico </label>
                            <input class="form-control" type="text" required id="civico" name="civico"
                                   placeholder="Numero Civico">
                        </div>
                        <div class="form-group">
                            <label for="pswd"> Password </label> 
                                <input class="form-control" type="password" required id="pswd" name="password" placeholder="Password" data-parsley-length="[6, 100]">
                        </div>
                        <div class="form-group">
                            <label for="confpswd"> Conferma la password </label>
                            <input class="form-control" type="password" required id="confpswd" name="password"
                                   placeholder="Password" data-parsley-equalto="#pswd">
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" id="checkprivacy" required/>
                            <label class="form-check-label" for="checkprivacy"> Acconsento al trattamento dei dati
                                personali in conformità a quanto riportato nell’informativa sulla privacy reperibile al
                                link <a href="../privacy.html">informativa sulla privacy</a></label>
                        </div>
                        <div class="pt-3 form-group">
                            <input class="form-control btn btn-primary" type="submit" value="Registra"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    <#else>
        <div class="container content">
            <div class="row vertical-center">
                <div class="col-md-12">
                    <div class="text-center">
                        <div class="title">
                            <h4> Registrazione avvenuta con successo </h4>
                        </div>
                        <div>
                            <a href="/RegistrazioneGrest/App/Login" class="btn btn-large btn-primary"> Accedi </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#if>
<#include "../struct/footer.html.ftl">
