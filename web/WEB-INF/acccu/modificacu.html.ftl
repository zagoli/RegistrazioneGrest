<#include "../struct/header.html.ftl">
<#include "../struct/navbar.html.ftl">
<div class="container content">
    <div class="mt-5">
        <div class="border border-light rounded pt-2 pl-3 pr-3 pb-2 bg-light">
            <div class="title pb-1">
                <h4>Modifica contatto telefonico urgenze</h4>
            </div>
            <form action="/RegistrazioneGrest/App/ModificaCU" method="POST" data-parsley-validate>
                <input type="hidden" name="id" value="${contatto.id}"/>
                <div class="form-group">
                    <label for="nome"> Nome </label> 
                    <input class="form-control" type="text" required id="nome" name="nome" placeholder="Nome" data-parsley-length="[2, 40]" value="${contatto.nome}"> 
                </div>
                <div class="form-group">
                    <label for="cognome"> Cognome </label> 
                    <input class="form-control" type="text" required id="cognome" name="cognome" placeholder="Cognome" data-parsley-length="[2, 40]" value="${contatto.cognome}"> 
                </div>
                <div class="form-group">
                    <label for="relazione"> Relazione con il contatto (nonno, pap&agrave;, zio...) </label> 
                    <input class="form-control" type="text" required id="relazione" name="relazione" placeholder="Relazione" data-parsley-length="[2, 40]" value="${contatto.relazione}"> 
                </div>
                <div class="form-group">
                    <label for="cellulare"> Telefono cellulare </label> 
                    <input class="form-control" type="text" id="cellulare" name="cellulare" placeholder="Cellulare" data-parsley-length="[10, 10]" value="${contatto.cellulare}"> 
                </div>
                <div class="form-group">
                    <label for="fisso"> Telefono fisso </label> 
                    <input class="form-control" type="text" id="fisso" name="fisso" placeholder="Fisso" data-parsley-length="[10, 10]" value="${contatto.fisso}"> 
                </div>
                <div class="pt-3 form-group">
                    <input class="form-control" type="submit" value="Modifica">
                </div>
            </form>
        </div>
    </div>
</div>
<#include "../struct/footer.html.ftl">