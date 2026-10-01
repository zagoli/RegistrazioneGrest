package DAOManager;

import Domain.AnimatoreExport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AnimatoreExportDAOImpl implements AnimatoreExportDAO {
    private static final String FIND_ALL =
            """
                    select
                    an.nome, an.cognome, an.dataNascita, an.presenza, an.cellularePersonale,
                    an.fasciaEtaRagazzi, an.mail, an.nTessera, an.codiceFiscale,
                    an.isResponsabileSquadra, an.isResponsabileLaboratorio,
                    la.id as la_id, la.descrizione as la_descrizione, la.riservato as la_riservato,
                    pa.id as pa_id, pa.nome as pa_nome, pa.luogo as pa_luogo,
                    re.nome as re_nome, re.cognome as re_cognome, re.mail as re_mail,
                    re.telefono as re_telefono, re.localita as re_localita,
                    re.via as re_via, re.civico as re_civico,
                    ci.id as ci_id, ci.nome as ci_nome, ci.luogo as ci_luogo,
                    sq.id as sq_id, sq.nome as sq_nome, sq.colore as sq_colore,
                    coalesce((
                        select string_agg(p.Calendario_idSettimana::text, ','
                                          order by p.Calendario_idSettimana)
                        from presenzaAn p
                        where p.Animatore_id = an.id
                    ), '') as settimane
                    from Animatore an
                    join Laboratorio la on an.Laboratorio_id = la.id
                    join Parrocchia pa on an.Parrocchia_id = pa.id
                    join Registrato re on an.Registrato_id = re.id
                    join Circolo ci on an.Circolo_id = ci.id
                    left join Squadra sq on an.Squadra_id = sq.id
                    order by an.cognome, an.nome;
                    """;

    @Override
    public List<AnimatoreExport> findAll() throws SQLException {
        try (Connection con = DAOMan.getConnection();
             PreparedStatement pst = con.prepareStatement(FIND_ALL);
             ResultSet rs = pst.executeQuery()) {
            List<AnimatoreExport> animatori = new LinkedList<>();
            while (rs.next()) {
                animatori.add(mapRow(rs));
            }
            return animatori;
        }
    }

    private AnimatoreExport mapRow(ResultSet rs) throws SQLException {
        return new AnimatoreExport(
                ExportDAOMapper.text(rs, "nome"),
                ExportDAOMapper.text(rs, "cognome"),
                rs.getDate("dataNascita"),
                ExportDAOMapper.presenza(rs),
                ExportDAOMapper.laboratorio(rs),
                ExportDAOMapper.parrocchia(rs),
                ExportDAOMapper.registrato(rs),
                ExportDAOMapper.circolo(rs),
                ExportDAOMapper.trimmedText(rs, "cellularePersonale"),
                ExportDAOMapper.fasciaEtaRagazzi(rs),
                ExportDAOMapper.text(rs, "mail"),
                ExportDAOMapper.trimmedText(rs, "nTessera"),
                ExportDAOMapper.text(rs, "codiceFiscale"),
                ExportDAOMapper.siNo(rs.getBoolean("isResponsabileSquadra")),
                ExportDAOMapper.siNo(rs.getBoolean("isResponsabileLaboratorio")),
                ExportDAOMapper.squadra(rs),
                ExportDAOMapper.settimane(rs)
        );
    }
}
