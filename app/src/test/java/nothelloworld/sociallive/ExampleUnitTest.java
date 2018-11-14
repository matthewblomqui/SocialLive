package nothelloworld.sociallive;

import android.graphics.Point;
import android.location.Location;

import org.junit.Test;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    /*
    ------Tests to make---------
    Create a party
    user input
    database push and pull
     */
    @Test
    public void testCreateParty() {

        Date aDate = new Date();
        Location location = new Location("fightMe");
        String title = "We are creating a bomb digity party";
        Time startTime = new Time(5);
        Time endTime = new Time(10);
        int numPeopleGoing = 0;
        Party aParty = new Party(location, title, aDate, startTime, endTime, -25);

        assert(aParty.getNumPeopleGoing() > 0);
//
//        // test default user
//        assertSame(aParty, bParty);
//        assertEquals(aParty.getHost(), "MadHatter");
//
//        // test default location (rexburg)
//        Location lemon = new Location();
//        assertEquals(aParty.getLocation(), lemon);
//
//        // test default title
//        assertEquals(aParty.getTitle(), "Party of the Century");
//
//        // test default date
//        Date aDate = new Date();
//        assertEquals(aParty.getDate(), aDate);

    }

    @Test
    public void testUserInput() {
        String input =  "StevenBillyBob";
        User aUser = new User("KITTENLOVERS!!!");
        assert(aUser.getUserName().length() > 40);
        assert(aUser.getUserName().length() < 100);

    }

    public void testListsOfParties() {
        Party newParty = new Party("CatsForSinglesParty");
        Party aParty = new Party("something");
        Party bParty = new Party("Stuff");

        EventsFeed feed1 = new EventsFeed();
        EventsFeed feed2 = new EventsFeed();

        feed1.addParty(newParty);
        feed1.addParty(aParty);
        feed1.addParty(bParty);

        feed2.addParty(newParty);
        feed2.addParty(aParty);
        feed2.addParty(bParty);

        assertThat(feed1, is(feed2));
    }
}