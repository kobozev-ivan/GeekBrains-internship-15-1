import com.web.service.Factory;
import com.web.service.Persons;
import com.web.service.Sites;
import com.web.service.Keywords;
import com.web.service.Pages;
import com.web.service.PersonPageRank;

/**
 * Created by DRSPEED-PC on 24.12.2017.
 */
public class Main {
    public static void main(final String[] args) throws Exception {
//        Sites sites = new Sites();
//        sites.setName("www.list.ru");
        Persons persons = new Persons();
        persons.setName("Вася");
        try {
//            Factory.getInstance().getSitesInterface().addSite(sites);
            Factory.getInstance().getPersonsInterface().addPerson(persons);
        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }
}