package Controller;

import DAOManager.DAOMan;
import Domain.AnimatoreExport;
import Domain.RagazzoExport;
import Domain.TerzamediaExport;
import ModelAndView.BinaryResult;
import ModelAndView.ControllerResult;
import Utility.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ControllerEsportazioneMassiva implements ControllerInterface {

    @Override
    public ControllerResult handleRequest(HttpServletRequest request, HttpServletResponse response) {

        try (Workbook workbook = new XSSFWorkbook()) {
            List<RagazzoExport> ragazzi = DAOMan.ragazzoExportDAO.findAll();
            List<TerzamediaExport> ragazziTerzamedia = DAOMan.terzamediaExportDAO.findAll();
            List<AnimatoreExport> animatori = DAOMan.animatoreExportDAO.findAll();

            return new BinaryResult("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "export_grest.xlsx",
                    workbook::write);
        } catch (SQLException | IOException e) {
            return Utils.getErrorPageAndLogException(e, ControllerEsportazioneMassiva.class.getSimpleName());
        }


    }

}
