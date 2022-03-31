<#if tipoUt??>
    <nav class="navbar navbar-expand-sm bg-secondary navbar-dark sticky-top">
        <ul class="nav navbar-nav">
            <li class="nav-item pr-4">
              <a href="/RegistrazioneGrest/App/Dashboard" class="btn btn-block btn-secondary">Home</a>
            </li>
            <#if tipoUt == 3>
                <li class="nav-item pr-4">
                    <a href="/RegistrazioneGrest/App/AccompagnatoriContatti" class="btn btn-block btn-secondary">Accompagnatori
                        e Contatti telefonici Urgenze</a>
                </li>
                <li class="nav-item pr-4">
                    <a href="/RegistrazioneGrest/App/DashboardAttGen" class="btn btn-block btn-secondary">Collaborazione
                        genitori</a>
                </li>
            <#elseif tipoUt <= 2>
                <li class="nav-item pr-4">
                    <button class="btn btn-block btn-secondary" onclick="$(this).html('Grazie mille');">Cliccami
                    </button>
                </li>
            </#if>
            <li class="nav-item pr-4">
              <a href="/RegistrazioneGrest/App/ModificaUtente" class="btn btn-block btn-secondary"> Modifica Profilo </a>
            </li>
            <li class="nav-item pr-4">
              <a href="/RegistrazioneGrest/App/ModificaPassword" class="btn btn-block btn-secondary"> Cambia la password </a>
            </li>
        </ul>
        <ul class="nav navbar-nav navbar-destra">
                <li class="nav-item pr-4">
                  <a class="nav-link" href="/RegistrazioneGrest/App/Login?logout" class="btn btn-block btn-secondary">LOGOUT</a>
                </li>
        </ul>
    </nav>
</#if>