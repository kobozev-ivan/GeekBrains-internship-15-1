import com.web.service.hibernate.Factory;
import com.web.service.hibernate.Pages;
import com.web.service.hibernate.Persons;
import com.web.service.hibernate.Sites;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class Main {
    public static void main(final String[] args) throws Exception {
        Sites sites = new Sites();
        sites.setName("www.list.ru");

        Persons persons = new Persons();
        persons.setName("Вася");
        


        Pages pages = new Pages();
        pages.setURL("http://www.lenta.ru/page/page");
        pages.setSiteID(1);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = simpleDateFormat.format(date);

        pages.setFound(currentTime);
        pages.setLastScan(currentTime);
//        try {
//            Factory.getInstance().getSitesInterface().addSite(sites);
//            Factory.getInstance().getPersonsInterface().addPerson(persons);
//            Factory.getInstance().getPagesInterface().addPage(pages);
//        } catch (SQLException e){
//            System.out.println(e.getStackTrace());
//        }
    }
}