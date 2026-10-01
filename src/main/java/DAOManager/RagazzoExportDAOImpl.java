package DAOManager;

import Domain.RagazzoExport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RagazzoExportDAOImpl implements RagazzoExportDAO {
    private static final String FIND_ALL =
            """
                    select
                    ra.nome, ra.cognome, ra.dataNascita, ra.presenza, ra.entrataAnticipata,
                    ra.richieste, ra.noteAlimentari, ra.mensa, ra.saNuotare,
                    ra.fratelloIscritto, ra.nTessera, ra.sezione, ra.classe,
                    la.id as la_id, la.descrizione as la_descrizione, la.riservato as la_riservato,
                    pa.id as pa_id, pa.nome as pa_nome, pa.luogo as pa_luogo,
                    re.nome as re_nome, re.cognome as re_cognome, re.mail as re_mail,
                    re.telefono as re_telefono, re.localita as re_localita,
                    re.via as re_via, re.civico as re_civico,
                    ci.id as ci_id, ci.nome as ci_nome, ci.luogo as ci_luogo,
                    sc.id as sc_id, sc.grado as sc_grado, sc.descrizione as sc_descrizione,
                    sq.id as sq_id, sq.nome as sq_nome, sq.colore as sq_colore,
                    pag.id as pagamento_id, pag.quota as pagamento_quota,
                    coalesce((
                        select string_agg(p.Calendario_idSettimana::text, ','
                                          order by p.Calendario_idSettimana)
                        from presenzaRag p
                        where p.Ragazzo_id = ra.id
                    ), '') as settimane
                    from Ragazzo ra
                    join Laboratorio la on ra.Laboratorio_id = la.id
                    join Parrocchia pa on ra.Parrocchia_id = pa.id
                    join Registrato re on ra.Registrato_id = re.id
                    join Circolo ci on ra.Circolo_id = ci.id
                    join Scuola sc on ra.Scuola_id = sc.id
                    left join Squadra sq on ra.Squadra_id = sq.id
                    left join Pagamento pag on ra.id = pag.Ragazzo_id
                    order by ra.cognome, ra.nome;
                    """;

    @Override
    public List<RagazzoExport> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL);
             ResultSet rs = pst.executeQuery()) {
            List<RagazzoExport> ragazzi = new LinkedList<>();
            while (rs.next()) {
                ragazzi.add(mapRow(rs));
            }
            return ragazzi;
        }
    }

    private RagazzoExport mapRow(ResultSet rs) throws SQLException {
        boolean pagato = rs.getObject("pagamento_id") != null;
        return new RagazzoExport(
                ExportDAOMapper.text(rs, "nome"),
                ExportDAOMapper.text(rs, "cognome"),
                rs.getDate("dataNascita"),
                ExportDAOMapper.presenza(rs),
                ExportDAOMapper.laboratorio(rs),
                ExportDAOMapper.parrocchia(rs),
                ExportDAOMapper.registrato(rs),
                ExportDAOMapper.circolo(rs),
                ExportDAOMapper.siNo(rs.getBoolean("entrataAnticipata")),
                ExportDAOMapper.text(rs, "richieste"),
                ExportDAOMapper.text(rs, "noteAlimentari"),
                ExportDAOMapper.siNo(rs.getBoolean("mensa")),
                ExportDAOMapper.siNo(rs.getBoolean("saNuotare")),
                ExportDAOMapper.siNo(rs.getBoolean("fratelloIscritto")),
                ExportDAOMapper.scuola(rs),
                ExportDAOMapper.trimmedText(rs, "nTessera"),
                ExportDAOMapper.trimmedText(rs, "sezione"),
                ExportDAOMapper.trimmedText(rs, "classe"),
                ExportDAOMapper.squadra(rs),
                ExportDAOMapper.settimane(rs),
                ExportDAOMapper.siNo(pagato),
                ExportDAOMapper.importoPagamento(rs)
        );
    }
}
