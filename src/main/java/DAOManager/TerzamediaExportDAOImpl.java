package DAOManager;

import Domain.TerzaMediaExport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
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
            List<TerzaMediaExport> terzamedia = new LinkedList<>();
            while (rs.next()) {
                terzamedia.add(mapRow(rs));
            }
            return terzamedia;
        }
    }

    private TerzaMediaExport mapRow(ResultSet rs) throws SQLException {
        boolean pagato = rs.getObject("pagamento_id") != null;
        return new TerzaMediaExport(
                ExportDAOMapper.text(rs, "nome"),
                ExportDAOMapper.text(rs, "cognome"),
                rs.getDate("dataNascita"),
                ExportDAOMapper.presenza(rs),
                ExportDAOMapper.laboratorio(rs),
                ExportDAOMapper.scuola(rs),
                ExportDAOMapper.parrocchia(rs),
                ExportDAOMapper.circolo(rs),
                ExportDAOMapper.trimmedText(rs, "cellulare"),
                ExportDAOMapper.text(rs, "noteAlimentari"),
                ExportDAOMapper.text(rs, "richieste"),
                ExportDAOMapper.siNo(rs.getBoolean("saNuotare")),
                ExportDAOMapper.siNo(rs.getBoolean("festaPassaggio")),
                ExportDAOMapper.trimmedText(rs, "sezione"),
                ExportDAOMapper.trimmedText(rs, "nTessera"),
                ExportDAOMapper.text(rs, "mail"),
                ExportDAOMapper.squadra(rs),
                ExportDAOMapper.settimane(rs),
                ExportDAOMapper.registrato(rs),
                ExportDAOMapper.siNo(pagato),
                ExportDAOMapper.importoPagamento(rs)
        );
    }
}
