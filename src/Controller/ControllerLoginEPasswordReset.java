package Controller;

import DAOManager.DAOMan;
import Domain.Registrato;
import ModelAndView.ModelAndView;
import ModelAndView.ModelAndViewStandard;
import Utility.BCrypt;
import Utility.ConfigProperties;
import Utility.ConfigPropertyException;
import Utility.Utils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class ControllerLoginEPasswordReset implements ControllerInterface {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndViewStandard();
        try {
            mv.addObject("TITOLOPAGINA", "Login");
            if (!request.getParameterMap().containsKey("mail") && !request.getParameterMap().containsKey("password")) {
                mv.setView("user/login.html");
                if (request.getParameterMap().containsKey("logout")) {
                    if (!request.getSession().isNew()) {
                        request.getSession().invalidate();
                    }
                } else if (request.getParameterMap().containsKey("reset")) {
                    mv.setView("user/resetpassword.html");
                    if (request.getParameterMap().containsKey("mailtoreset")) {
                        Registrato r = DAOMan.registratoDAO.findByMail(request.getParameter("mailtoreset"));
                        if (r == null) {
                            mv.addObject("UTENTENONTROVATO", true);
                        } else {
                            String newpswd = getNewPswd();
                            r.setPassword(newpswd);
                            DAOMan.registratoDAO.updatePassword(r);
                            sendResetPasswordEmail(r.getMail(), newpswd);
                            mv.addObject("FATTO", true);
                        }
                    }
                }
            } else {
                String mail = request.getParameter("mail");
                String pswdDaValidare = request.getParameter("password");
                Registrato r = DAOMan.registratoDAO.findByMail(mail);
                if (r == null) {
                    mv.setView("user/login.html");
                    mv.addObject("UTENTENONTROVATO", true);
                } else {
                    String pswd = r.getPassword();
                    if (!BCrypt.checkpw(pswdDaValidare, pswd)) {
                        mv.setView("user/login.html");
                        mv.addObject("ERRATO", true);
                    } else {
                        request.getSession().setAttribute("idUtente", r.getId());
                        request.getSession().setAttribute("tipoUtente", r.getTipoUt());
                        response.sendRedirect("/RegistrazioneGrest/App/Dashboard");
                    }
                }
            }
        } catch (final RuntimeException | IOException | SQLException | MessagingException | ConfigPropertyException e) {
            mv = Utils.getErrorPageAndLogException(e, ControllerLoginEPasswordReset.class.getName());
        }
        return mv;
    }

    private String getNewPswd() {
        char[] possibleCharacters = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!$&*?").toCharArray();
        int lengthpasswd = ThreadLocalRandom.current().nextInt(12, 18 + 1);
        String newpswd = RandomStringUtils.random(lengthpasswd, 0, possibleCharacters.length - 1, false, false, possibleCharacters, new SecureRandom());
        return newpswd;
    }


    private void sendResetPasswordEmail(String destinatario, String newpswd) throws MessagingException, ConfigPropertyException, IOException {
        String mittente = ConfigProperties.getProperty("INDIRIZZO_EMAIL_ASSISTENZA");
        String timeout = ConfigProperties.getProperty("CONNECTION_TIMEOUT_SERVER_MAIL_ASSISTENZA");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", ConfigProperties.getProperty("INDIRIZZO_SERVER_MAIL_ASSISTENZA"));
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.port", ConfigProperties.getProperty("PORTA_SERVER_MAIL_ASSISTENZA"));
        properties.put("mail.smtp.connectiontimeout", timeout);
        properties.put("mail.smtp.timeout", timeout);
        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mittente));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.setSubject("Reset password Grest di Balconi");
        message.setContent(
                // <editor-fold defaultstate="collapsed">
                "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head>"
                        + "    <!--[if gte mso 9]><xml>"
                        + "     <o:OfficeDocumentSettings>"
                        + "      <o:AllowPNG/>"
                        + "      <o:PixelsPerInch>96</o:PixelsPerInch>"
                        + "     </o:OfficeDocumentSettings>"
                        + "    </xml><![endif]-->"
                        + "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">"
                        + "    <meta name=\"viewport\" content=\"width=device-width\">"
                        + "    <!--[if !mso]><!--><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->"
                        + "    <title></title>"
                        + "    <!--[if !mso]><!-- -->"
                        + "	<link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\">"
                        + "	<!--<![endif]-->"
                        + "    "
                        + "    <style type=\"text/css\" id=\"media-query\">"
                        + "      body {"
                        + "  margin: 0;"
                        + "  padding: 0; }"
                        + "table, tr, td {"
                        + "  vertical-align: top;"
                        + "  border-collapse: collapse; }"
                        + ".ie-browser table, .mso-container table {"
                        + "  table-layout: fixed; }"
                        + "* {"
                        + "  line-height: inherit; }"
                        + "a[x-apple-data-detectors=true] {"
                        + "  color: inherit !important;"
                        + "  text-decoration: none !important; }"
                        + "[owa] .img-container div, [owa] .img-container button {"
                        + "  display: block !important; }"
                        + "[owa] .fullwidth button {"
                        + "  width: 100% !important; }"
                        + "[owa] .block-grid .col {"
                        + "  display: table-cell;"
                        + "  float: none !important;"
                        + "  vertical-align: top; }"
                        + ".ie-browser .num12, .ie-browser .block-grid, [owa] .num12, [owa] .block-grid {"
                        + "  width: 620px !important; }"
                        + ".ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {"
                        + "  line-height: 100%; }"
                        + ".ie-browser .mixed-two-up .num4, [owa] .mixed-two-up .num4 {"
                        + "  width: 204px !important; }"
                        + ".ie-browser .mixed-two-up .num8, [owa] .mixed-two-up .num8 {"
                        + "  width: 408px !important; }"
                        + ".ie-browser .block-grid.two-up .col, [owa] .block-grid.two-up .col {"
                        + "  width: 310px !important; }"
                        + ".ie-browser .block-grid.three-up .col, [owa] .block-grid.three-up .col {"
                        + "  width: 206px !important; }"
                        + ".ie-browser .block-grid.four-up .col, [owa] .block-grid.four-up .col {"
                        + "  width: 155px !important; }"
                        + ".ie-browser .block-grid.five-up .col, [owa] .block-grid.five-up .col {"
                        + "  width: 124px !important; }"
                        + ".ie-browser .block-grid.six-up .col, [owa] .block-grid.six-up .col {"
                        + "  width: 103px !important; }"
                        + ".ie-browser .block-grid.seven-up .col, [owa] .block-grid.seven-up .col {"
                        + "  width: 88px !important; }"
                        + ".ie-browser .block-grid.eight-up .col, [owa] .block-grid.eight-up .col {"
                        + "  width: 77px !important; }"
                        + ".ie-browser .block-grid.nine-up .col, [owa] .block-grid.nine-up .col {"
                        + "  width: 68px !important; }"
                        + ".ie-browser .block-grid.ten-up .col, [owa] .block-grid.ten-up .col {"
                        + "  width: 62px !important; }"
                        + ".ie-browser .block-grid.eleven-up .col, [owa] .block-grid.eleven-up .col {"
                        + "  width: 56px !important; }"
                        + ".ie-browser .block-grid.twelve-up .col, [owa] .block-grid.twelve-up .col {"
                        + "  width: 51px !important; }"
                        + "@media only screen and (min-width: 640px) {"
                        + "  .block-grid {"
                        + "    width: 620px !important; }"
                        + "  .block-grid .col {"
                        + "    vertical-align: top; }"
                        + "    .block-grid .col.num12 {"
                        + "      width: 620px !important; }"
                        + "  .block-grid.mixed-two-up .col.num4 {"
                        + "    width: 204px !important; }"
                        + "  .block-grid.mixed-two-up .col.num8 {"
                        + "    width: 408px !important; }"
                        + "  .block-grid.two-up .col {"
                        + "    width: 310px !important; }"
                        + "  .block-grid.three-up .col {"
                        + "    width: 206px !important; }"
                        + "  .block-grid.four-up .col {"
                        + "    width: 155px !important; }"
                        + "  .block-grid.five-up .col {"
                        + "    width: 124px !important; }"
                        + "  .block-grid.six-up .col {"
                        + "    width: 103px !important; }"
                        + "  .block-grid.seven-up .col {"
                        + "    width: 88px !important; }"
                        + "  .block-grid.eight-up .col {"
                        + "    width: 77px !important; }"
                        + "  .block-grid.nine-up .col {"
                        + "    width: 68px !important; }"
                        + "  .block-grid.ten-up .col {"
                        + "    width: 62px !important; }"
                        + "  .block-grid.eleven-up .col {"
                        + "    width: 56px !important; }"
                        + "  .block-grid.twelve-up .col {"
                        + "    width: 51px !important; } }"
                        + "@media (max-width: 640px) {"
                        + "  .block-grid, .col {"
                        + "    min-width: 320px !important;"
                        + "    max-width: 100% !important;"
                        + "    display: block !important; }"
                        + "  .block-grid {"
                        + "    width: calc(100% - 40px) !important; }"
                        + "  .col {"
                        + "    width: 100% !important; }"
                        + "    .col > div {"
                        + "      margin: 0 auto; }"
                        + "  img.fullwidth, img.fullwidthOnMobile {"
                        + "    max-width: 100% !important; }"
                        + "  .no-stack .col {"
                        + "    min-width: 0 !important;"
                        + "    display: table-cell !important; }"
                        + "  .no-stack.two-up .col {"
                        + "    width: 50% !important; }"
                        + "  .no-stack.mixed-two-up .col.num4 {"
                        + "    width: 33% !important; }"
                        + "  .no-stack.mixed-two-up .col.num8 {"
                        + "    width: 66% !important; }"
                        + "  .no-stack.three-up .col.num4 {"
                        + "    width: 33% !important; }"
                        + "  .no-stack.four-up .col.num3 {"
                        + "    width: 25% !important; }"
                        + "  .mobile_hide {"
                        + "    min-height: 0px;"
                        + "    max-height: 0px;"
                        + "    max-width: 0px;"
                        + "    display: none;"
                        + "    overflow: hidden;"
                        + "    font-size: 0px; } }"
                        + "    </style>"
                        + "</head>"
                        + "<body class=\"clean-body\" style=\"margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #FFFFFF\">"
                        + "  <style type=\"text/css\" id=\"media-query-bodytag\">"
                        + "    @media (max-width: 520px) {"
                        + "      .block-grid {"
                        + "        min-width: 320px!important;"
                        + "        max-width: 100%!important;"
                        + "        width: 100%!important;"
                        + "        display: block!important;"
                        + "      }"
                        + "      .col {"
                        + "        min-width: 320px!important;"
                        + "        max-width: 100%!important;"
                        + "        width: 100%!important;"
                        + "        display: block!important;"
                        + "      }"
                        + "        .col > div {"
                        + "          margin: 0 auto;"
                        + "        }"
                        + "      img.fullwidth {"
                        + "        max-width: 100%!important;"
                        + "      }"
                        + "			img.fullwidthOnMobile {"
                        + "        max-width: 100%!important;"
                        + "      }"
                        + "      .no-stack .col {"
                        + "				min-width: 0!important;"
                        + "				display: table-cell!important;"
                        + "			}"
                        + "			.no-stack.two-up .col {"
                        + "				width: 50%!important;"
                        + "			}"
                        + "			.no-stack.mixed-two-up .col.num4 {"
                        + "				width: 33%!important;"
                        + "			}"
                        + "			.no-stack.mixed-two-up .col.num8 {"
                        + "				width: 66%!important;"
                        + "			}"
                        + "			.no-stack.three-up .col.num4 {"
                        + "				width: 33%!important;"
                        + "			}"
                        + "			.no-stack.four-up .col.num3 {"
                        + "				width: 25%!important;"
                        + "			}"
                        + "      .mobile_hide {"
                        + "        min-height: 0px!important;"
                        + "        max-height: 0px!important;"
                        + "        max-width: 0px!important;"
                        + "        display: none!important;"
                        + "        overflow: hidden!important;"
                        + "        font-size: 0px!important;"
                        + "      }"
                        + "    }"
                        + "  </style>"
                        + "  <!--[if IE]><div class=\"ie-browser\"><![endif]-->"
                        + "  <!--[if mso]><div class=\"mso-container\"><![endif]-->"
                        + "  <table class=\"nl-container\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #FFFFFF;width: 100%\" cellpadding=\"0\" cellspacing=\"0\">"
                        + "	<tbody>"
                        + "	<tr style=\"vertical-align: top\">"
                        + "		<td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top\">"
                        + "    <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color: #FFFFFF;\"><![endif]-->"
                        + "    <div style=\"background-color:transparent;\">"
                        + "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 620px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">"
                        + "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">"
                        + "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 620px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->"
                        + "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"620\" style=\" width:620px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:0px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                        + "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 620px;display: table-cell;vertical-align: top;\">"
                        + "              <div style=\"background-color: transparent; width: 100% !important;\">"
                        + "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:0px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->"
                        + "                  "
                        + "                    <div class=\"\">"
                        + "	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\"><![endif]-->"
                        + "	<div style=\"color:#555555;line-height:180%;font-family:'Lato', Tahoma, Verdana, Segoe, sans-serif; padding-right: 0px; padding-left: 0px; padding-top: 0px; padding-bottom: 0px;\">	"
                        + "		<div style=\"line-height:22px;font-size:12px;color:#555555;font-family:'Lato', Tahoma, Verdana, Segoe, sans-serif;text-align:left;\"><p style=\"margin: 0;text-align: center;line-height: 22px;font-size: 12px\"><span style=\"color: #4dbc65; line-height: 54px; font-size: 30px;\">LA TUA PASSWORD&#160;È STATA RESETTATA!</span></p></div>	"
                        + "	</div>"
                        + "	<!--[if mso]></td></tr></table><![endif]-->"
                        + "</div>"
                        + "                  "
                        + "                  "
                        + "                    "
                        + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"divider \" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 100%;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">"
                        + "    <tbody>"
                        + "        <tr style=\"vertical-align: top\">"
                        + "            <td class=\"divider_inner\" style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;padding-right: 10px;padding-left: 10px;padding-top: 10px;padding-bottom: 15px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">"
                        + "                <table class=\"divider_content\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 1px solid #222222;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">"
                        + "                    <tbody>"
                        + "                        <tr style=\"vertical-align: top\">"
                        + "                            <td style=\"word-break: break-word;border-collapse: collapse !important;vertical-align: top;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%\">"
                        + "                                <span></span>"
                        + "                            </td>"
                        + "                        </tr>"
                        + "                    </tbody>"
                        + "                </table>"
                        + "            </td>"
                        + "        </tr>"
                        + "    </tbody>"
                        + "</table>"
                        + "                  "
                        + "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->"
                        + "              </div>"
                        + "            </div>"
                        + "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->"
                        + "        </div>"
                        + "      </div>"
                        + "    </div>    <div style=\"background-color:transparent;\">"
                        + "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 620px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">"
                        + "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">"
                        + "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 620px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->"
                        + "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"620\" style=\" width:620px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                        + "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 620px;display: table-cell;vertical-align: top;\">"
                        + "              <div style=\"background-color: transparent; width: 100% !important;\">"
                        + "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->"
                        + "                  "
                        + "                    <div align=\"center\" class=\"img-container center fixedwidth \" style=\"padding-right: 0px;  padding-left: 0px;\">"
                        + "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr style=\"line-height:0px;line-height:0px;\"><td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\"><![endif]-->"
                        + "  <img class=\"center fixedwidth\" align=\"center\" border=\"0\" src=\"https://d1oco4z2z1fhwp.cloudfront.net/templates/default/18/okok.gif\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 155px\" width=\"155\">"
                        + "<!--[if mso]></td></tr></table><![endif]-->"
                        + "</div>"
                        + "                  "
                        + "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->"
                        + "              </div>"
                        + "            </div>"
                        + "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->"
                        + "        </div>"
                        + "      </div>"
                        + "    </div>    <div style=\"background-color:transparent;\">"
                        + "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 620px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">"
                        + "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">"
                        + "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 620px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->"
                        + "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"620\" style=\" width:620px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:10px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                        + "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 620px;display: table-cell;vertical-align: top;\">"
                        + "              <div style=\"background-color: transparent; width: 100% !important;\">"
                        + "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:10px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->"
                        + "                  "
                        + "                    <div class=\"\">"
                        + "	<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 25px;\"><![endif]-->"
                        + "	<div style=\"color:#71777D;line-height:120%;font-family:'Lato', Tahoma, Verdana, Segoe, sans-serif; padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 25px;\">	"
                        + "		<div style=\"line-height:14px;font-family:Lato,Tahoma,Verdana,Segoe,sans-serif;font-size:12px;color:#71777D;text-align:left;\"><p style=\"margin: 0;text-align: center;line-height: 17px;font-size: 14px\">La tua nuova password è: <b>" + newpswd + "</b></p></div>	"
                        + "	</div>"
                        + "	<!--[if mso]></td></tr></table><![endif]-->"
                        + "</div>"
                        + "                  "
                        + "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->"
                        + "              </div>"
                        + "            </div>"
                        + "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->"
                        + "        </div>"
                        + "      </div>"
                        + "    </div>    <div style=\"background-color:transparent;\">"
                        + "      <div style=\"Margin: 0 auto;min-width: 320px;max-width: 620px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;\" class=\"block-grid \">"
                        + "        <div style=\"border-collapse: collapse;display: table;width: 100%;background-color:transparent;\">"
                        + "          <!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"background-color:transparent;\" align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 620px;\"><tr class=\"layout-full-width\" style=\"background-color:transparent;\"><![endif]-->"
                        + "              <!--[if (mso)|(IE)]><td align=\"center\" width=\"620\" style=\" width:620px; padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><![endif]-->"
                        + "            <div class=\"col num12\" style=\"min-width: 320px;max-width: 620px;display: table-cell;vertical-align: top;\">"
                        + "              <div style=\"background-color: transparent; width: 100% !important;\">"
                        + "              <!--[if (!mso)&(!IE)]><!--><div style=\"border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\"><!--<![endif]-->"
                        + "                  "
                        + "                    <div class=\"\" style=\"font-size: 16px;font-family:'Lato', Tahoma, Verdana, Segoe, sans-serif; text-align: center;\">"
                        + "            <div style=\"background-color: #4dbc65; color: white\">"
                        + "                <small>©2018 Jacopo Zagoli, Grest di Balconi"
                        + "                </small>"
                        + "            </div>"
                        + "        </div>"
                        + "                  "
                        + "              <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->"
                        + "              </div>"
                        + "            </div>"
                        + "          <!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->"
                        + "        </div>"
                        + "      </div>"
                        + "    </div>   <!--[if (mso)|(IE)]></td></tr></table><![endif]-->"
                        + "		</td>"
                        + "  </tr>"
                        + "  </tbody>"
                        + "  </table>"
                        + "  <!--[if (mso)|(IE)]></div><![endif]-->"
                        + "</body></html>" // </editor-fold>
                , "text/html; charset=utf-8"
        );
        Transport.send(message, mittente, ConfigProperties.getProperty("PASSWORD_EMAIL_ASSISTENZA"));
    }
}
