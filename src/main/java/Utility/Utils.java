package Utility;

import Response.FreemarkerTemplate;
import Servlet.CorrelationIdFilter;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private static final DateTimeFormatter ERROR_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss XXX");

    public static FreemarkerTemplate getErrorPageAndLogException(Exception e, String loggerName) {
        String errorTime = ZonedDateTime.now().format(ERROR_TIME_FORMAT);
        logException(e, loggerName);
        FreemarkerTemplate mv = new FreemarkerTemplate("err/errore.html");
        mv.addObject("TITOLOPAGINA", "Errore!");
        String correlationId = MDC.get(CorrelationIdFilter.CORRELATION_ID);
        if (correlationId != null) {
            mv.addObject("codiceErrore", correlationId);
            mv.addObject("dataOraErrore", errorTime);
        }
        return mv;
    }

    public static void logException(Exception e, String loggerName) {
        LoggerFactory.getLogger(loggerName).error("Errore durante l'elaborazione della richiesta", e);
    }
}
