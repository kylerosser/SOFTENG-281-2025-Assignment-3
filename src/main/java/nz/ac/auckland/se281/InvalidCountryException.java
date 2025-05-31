package nz.ac.auckland.se281;

public class InvalidCountryException extends Exception {
  public InvalidCountryException(String country) {
    super(MessageCli.INVALID_COUNTRY.getMessage(country));
  }
}
