import com.web.service.hibernate.Factory;
import com.web.service.hibernate.Pages;
import com.web.service.hibernate.Persons;
import com.web.service.hibernate.Sites;
import org.hibernate.mapping.Map;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        ArrayList<Sites> SitesList = new ArrayList<Sites>();
        try {
//            Factory.getInstance().getSitesInterface().addSite(sites);
//            Factory.getInstance().getPersonsInterface().addPerson(persons);
//            Factory.getInstance().getPagesInterface().addPage(pages);
//            Factory.getInstance().getSitesInterface().addSite("www.mysite.com");
            SitesList.add(Factory.getInstance().getSitesInterface().getSite(2));
            System.out.println(SitesList.get(0).getID() + ":" + SitesList.get(0).getName());
//            Factory.getInstance().getSitesInterface().updateSite(2, "www.list.ru");
//            System.out.println(sites1.toString());

        } catch (SQLException e){
            System.out.println(e.getStackTrace());
        }

    }
}