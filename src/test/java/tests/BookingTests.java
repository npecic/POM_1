package tests;

import org.testng.Assert;
import org.testng.annotations.*;
import pom_classes.BookingHomePage;
import selenium_core.DriverManager;
import excel_core.GetExcelData;

import javax.xml.stream.Location;
import java.io.IOException;
import java.util.Map;

public class BookingTests extends BaseTest{


    DriverManager driverManager;


    @BeforeMethod
    public void setup() {
        baseSetUP("CHROME", "99",5);
    }
    @Test
    @Parameters({"row","column"})
    public void booking(@Optional String row,@Optional String column) throws InterruptedException, IOException {

 //       Map<String, String> data = new GetExcelData().getRowData("src/test/test_data/Booking.xlsx", "BookingSheet", Integer.parseInt(row));
        Map<String, String> data = new GetExcelData().getColumnData("src/test/test_data/Booking.xlsx", "Columns", Integer.parseInt(column));
        String [] ages = data.get("ChildAges").split(",");

 //       String [] ages = {"3 years old", "4 years old","4 years old"};


        BookingHomePage bookingHomePage = new BookingHomePage(driver);
        bookingHomePage.setWhereAreYouGoing(data.get("Location"));
        bookingHomePage.checkInCheckOut(data.get("checkInMonth"),data.get("checkInYear"), data.get("checkInDay"),data.get("checkOutMonth"), data.get("checkOutYear"),data.get("checkOutDay"));
        bookingHomePage.addQuests(Integer.parseInt(data.get("AdultsNum")),Integer.parseInt(data.get("ChildrenNum")),ages,Integer.parseInt(data.get("RoomsNum")));
        bookingHomePage.search();
        bookingHomePage.clicks();


        /*
        Assertation
         */


        String adultsCount = bookingHomePage.getAdultsCount();
        String childrenCount = bookingHomePage.getChildrenCount();
        String roomCount = bookingHomePage.getRoomCount();


        Assert.assertEquals(adultsCount, data.get("adultsCount")); // Replace "3" with the expected value
        Assert.assertEquals(childrenCount, data.get("childrenCount")); // Replace "2" with the expected value
        Assert.assertEquals(roomCount, data.get("roomCount")); // Replace "2" with the expected value
    }

    @AfterMethod
    public void tearDown (){
        baseTearDown();

    }
}
