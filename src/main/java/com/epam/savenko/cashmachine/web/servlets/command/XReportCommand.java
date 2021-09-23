package com.epam.savenko.cashmachine.web.servlets.command;

import com.epam.savenko.cashmachine.dao.AppPropertiesDao;
import com.epam.savenko.cashmachine.dao.OrderDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcAppPropertiesDao;
import com.epam.savenko.cashmachine.dao.jdbc.JdbcOrderDaoImpl;
import com.epam.savenko.cashmachine.exception.CashMachineException;
import com.epam.savenko.cashmachine.model.AppProperties;
import com.epam.savenko.cashmachine.web.constant.Path;
import com.epam.savenko.cashmachine.web.constant.SessionParam;
import com.epam.savenko.cashmachine.web.servlets.RoutePath;
import com.epam.savenko.cashmachine.web.servlets.RouteType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class XReportCommand extends Command {

    private static final Logger LOG = Logger.getLogger(XReportCommand.class);

    @Override
    public RoutePath execute(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        RoutePath forward = new RoutePath(Path.PAGE_TAX_REPORT, RouteType.FORWARD);
        HttpSession session = req.getSession();
        try {
            AppPropertiesDao appProperties = new JdbcAppPropertiesDao();
            session.setAttribute(SessionParam.STORE_NAME, appProperties.getByName("name").get().getValue());
            session.setAttribute(SessionParam.ADDRESS, appProperties.getByName("address").get().getValue());
            session.setAttribute(SessionParam.IPN, appProperties.getByName("taxnumber").get().getValue());
            OrderDao orderDao = new JdbcOrderDaoImpl();
            session.setAttribute(SessionParam.ORDER_COUNT, orderDao.getCount());
            double sumCash = orderDao.getSumCash();
            double sumCard = orderDao.getSumCard();
            session.setAttribute(SessionParam.ORDER_SUM_CASH, String.format("%,.2f",sumCash));
            session.setAttribute(SessionParam.ORDER_SUM_CARD, String.format("%,.2f",sumCard));
            double tax = appProperties.getTax();
            LOG.error("CURRENT TAX: "+tax);
            String fTax = String.format("%,.2f", (sumCard+sumCash)*tax/100);
            session.setAttribute(SessionParam.ORDER_TAX,fTax);
            session.setAttribute(SessionParam.ORDER_TOTAL,String.format("%,.2f",(sumCard+sumCash)));
            List<String> users = orderDao.getUsersFullNameInOrders();
            session.setAttribute(SessionParam.ORDER_USERS_OF_ORDERS,users);
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter fDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            DateTimeFormatter fTime = DateTimeFormatter.ofPattern("HH:mm:ss");
            LOG.error("REPORT DATE: "+localDateTime.format(fDate));
            LOG.error("REPORT DATE: "+localDateTime.format(fTime));
            session.setAttribute(SessionParam.REPORT_DATE,localDateTime.format(fDate));
            session.setAttribute(SessionParam.REPORT_TIME,localDateTime.format(fTime));
            Optional<AppProperties> cashnumber = appProperties.getByName("cashnumber");
            if (cashnumber.isPresent()) {
                String cashRegisterNumber = cashnumber.get().getValue();
                session.setAttribute(SessionParam.REPORT_CASH_REGISTER_NUMBER,cashRegisterNumber);
            }
        } catch (CashMachineException e) {
            LOG.error("Error when receive parameters: ", e);
        }
        session.setAttribute(SessionParam.REPORT_TYPE, "x");
        return forward;
    }
}
