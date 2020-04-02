import exception.InvalidTicketException;
import exception.ParkingLotFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTest {

  @BeforeEach
  void setUp() {
    //TODO:delete all data in database
  }

  @Test
  void should_return_ticket_information_when_park_given_init_and_car_and_general_boy() {
    Application.init("A:2,B:2");

    String eightTicket = Application.park("8");
    assertEquals("A,1,8", eightTicket);
    String nineTicket = Application.park("9");
    assertEquals("A,2,9", nineTicket);
    String sixTicket = Application.park("6");
    assertEquals("B,1,6", sixTicket);

  }

  @Test
  void should_throw_exception_when_park_given_init_and_car_and_general_boy() {
    Application.init("A:1,B:1");
    Application.park("8");
    Application.park("6");
    assertThrows(ParkingLotFullException.class, () -> Application.park("9"));
  }

  @Test
  void should_return_car_information_when_fetch_given_ticket_and_car() {
    Application.init("A:8,B:10");

    String eightTicket = Application.park("8");
    String eightCar = Application.fetch(eightTicket);
    assertEquals("8", eightCar);


    String nineTicket = Application.park("9");
    String nineCar = Application.fetch(nineTicket);
    assertEquals("9", nineCar);

    String sixTicket = Application.park("6");
    String sixCar = Application.fetch(sixTicket);
    assertEquals("6", sixCar);
  }

  @Test
  void should_throw_exception_when_fetch_given_ticket_information_is_not_correct() {
    Application.init("A:8,B:10");

    assertThrows(InvalidTicketException.class, () -> Application.fetch("C,1,8"));
    assertThrows(InvalidTicketException.class, () -> Application.fetch("A,9,9"));
    assertThrows(InvalidTicketException.class, () -> Application.fetch("B,-1,1"));

  }

  @Test
  void should_throw_exception_when_fetch_given_spacee_has_no_car() {
    Application.init("A:8,B:10");

    String eightTicket = Application.park("8");
    Application.fetch(eightTicket);

    assertThrows(InvalidTicketException.class, () -> Application.fetch(eightTicket));
  }

  @Test
  void should_throw_exception_when_fetch_given_space_is_other_car() {
    Application.init("A:8,B:10");

    String eightTicket = Application.park("8");
    Application.fetch(eightTicket);
    Application.park("3");

    assertThrows(InvalidTicketException.class, () -> Application.fetch(eightTicket));
  }
}