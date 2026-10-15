package DAOManager;

import DAOManager.Mappers.ExportDAOMapper;
import Domain.TerzaMediaExport;
import Utility.DAOUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TerzamediaExportDAOImpl implements TerzamediaExportDAO {
    private static final String FIND_ALL =
            """
                    select
                    ter.nome, ter.cognome, ter.dataNascita, ter.presenza, ter.cellulare,
                    ter.noteAlimentari, ter.richieste, ter.saNuotare, ter.festaPassaggio,
                    ter.sezione, ter.nTessera, ter.mail,
                    la.id as la_id, la.descrizione as la_descrizione, la.riservato as la_riservato,
                    sc.id as sc_id, sc.grado as sc_grado, sc.descrizione as sc_descrizione,
                    pa.id as pa_id, pa.nome as pa_nome, pa.luogo as pa_luogo,
                    re.nome as re_nome, re.cognome as re_cognome, re.mail as re_mail,
                    re.telefono as re_telefono, re.localita as re_localita,
                    re.via as re_via, re.civico as re_civico,
                    ci.id as ci_id, ci.nome as ci_nome, ci.luogo as ci_luogo,
                    sq.id as sq_id, sq.nome as sq_nome, sq.colore as sq_colore,
                    pag.id as pagamento_id, pag.quota as pagamento_quota,
                    coalesce((
                        select string_agg(p.Calendario_idSettimana::text, ','
                                          order by p.Calendario_idSettimana)
                        from presenzaTer p
                        where p.Terzamedia_id = ter.id
                    ), '') as settimane
                    from Terzamedia ter
                    join Laboratorio la on ter.Laboratorio_id = la.id
                    join Scuola sc on ter.Scuola_id = sc.id
                    join Parrocchia pa on ter.Parrocchia_id = pa.id
                    join Registrato re on ter.Registrato_id = re.id
                    join Circolo ci on ter.Circolo_id = ci.id
                    left join Squadra sq on ter.Squadra_id = sq.id
                    left join PagamentoTerzamedia pag on ter.id = pag.Terzamedia_id
                    order by ter.cognome, ter.nome;
                    """;

    @Override
    public List<TerzaMediaExport> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL);
             ResultSet rs = pst.executeQuery()) {
            return DAOUtils.resultSetToList(rs, ExportDAOMapper::mapRowToTerzamediaExport);
        }
    }

}
