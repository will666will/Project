package concert;

import utils.Constants;
import venue.Venue;

/**
 * The Concert class represents a concert in the ticket management system.
 * It includes the concert's ID, date, timing, artist name, venue name, and zone pricing.
 * It also provides methods to get and set the prices for different zones (standing, seating, VIP).
 *
 * @version 1.0
 */
public class Concert {

    /**
     * The unique ID of the concert.
     */
    private String concertId;

    /**
     * The date of the concert.
     */
    private String date;

    /**
     * The timing of the concert.
     */
    private String timing;

    /**
     * The name of the artist performing at the concert.
     */
    private String artistName;

    /**
     * The name of the venue where the concert is held.
     */
    private String venueName;

    /**
     * The pricing for different zones at the concert.
     */
    private String zonePricing;

    /**
     * The prices for standing zone.
     */
    private Double[] standingPrice;

    /**
     * The prices for seating zone.
     */
    private Double[] seatingPrice;

    /**
     * The prices for VIP zone.
     */
    private Double[] vipPrice;

    /**
     * Constructs a new Concert with the specified details.
     *
     * @param concertId   the unique ID of the concert
     * @param date        the date of the concert
     * @param timing      the timing of the concert
     * @param artistName  the name of the artist performing at the concert
     * @param venueName   the name of the venue where the concert is held
     * @param zonePricing the pricing for different zones at the concert
     */
    public Concert(String concertId, String date, String timing, String artistName, String venueName, String zonePricing) {
        this.concertId = concertId;
        this.date = date;
        this.timing = timing;
        this.artistName = artistName;
        this.venueName = venueName;
        this.zonePricing = zonePricing;
        standingPrice = new Double[3];
        seatingPrice = new Double[3];
        vipPrice = new Double[3];
        String[] prices = zonePricing.split(":");
        standingPrice[0] = Double.parseDouble(prices[1]);
        standingPrice[1] = Double.parseDouble(prices[2]);
        standingPrice[2] = Double.parseDouble(prices[3]);
        seatingPrice[0] = Double.parseDouble(prices[5]);
        seatingPrice[1] = Double.parseDouble(prices[6]);
        seatingPrice[2] = Double.parseDouble(prices[7]);
        vipPrice[0] = Double.parseDouble(prices[9]);
        vipPrice[1] = Double.parseDouble(prices[10]);
        vipPrice[2] = Double.parseDouble(prices[11]);
    }

    /**
     * Returns the unique ID of the concert.
     *
     * @return the unique ID of the concert
     */
    public String getConcertId() {
        return concertId;
    }

    /**
     * Returns the date of the concert.
     *
     * @return the date of the concert
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the timing of the concert.
     *
     * @return the timing of the concert
     */
    public String getTiming() {
        return timing;
    }

    /**
     * Returns the name of the artist performing at the concert.
     *
     * @return the name of the artist performing at the concert
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Returns the name of the venue where the concert is held.
     *
     * @return the name of the venue where the concert is held
     */
    public String getVenueName() {
        return venueName;
    }

    /**
     * Returns the prices for the standing zone.
     *
     * @return the prices for the standing zone
     */
    public Double[] getStandingPrice() {
        return standingPrice;
    }

    /**
     * Returns the prices for the seating zone.
     *
     * @return the prices for the seating zone
     */
    public Double[] getSeatingPrice() {
        return seatingPrice;
    }

    /**
     * Returns the prices for the VIP zone.
     *
     * @return the prices for the VIP zone
     */
    public Double[] getVipPrice() {
        return vipPrice;
    }

    /**
     * Returns the price of a specific seat based on the zone type and seat number.
     *
     * @param type      the type of the zone (0 for VIP, 1 for seating, 2 for standing)
     * @param seatNumber the seat number to get the price for
     * @param venue     the venue where the concert is held
     * @return the price of the specified seat
     */
    public Double getSeatPrice(int type, int seatNumber, Venue venue) {
        if (type == 0) {
            if (seatNumber <= venue.getLeft()) {
                return vipPrice[0];
            } else if (seatNumber <= venue.getLeft() + venue.getMiddle()) {
                return vipPrice[1];
            } else if (seatNumber <= venue.getLeft() + venue.getMiddle() + venue.getRight()) {
                return vipPrice[2];
            }
        } else if (type == 1) {
            if (seatNumber <= venue.getLeft()) {
                return seatingPrice[0];
            } else if (seatNumber <= venue.getLeft() + venue.getMiddle()) {
                return seatingPrice[1];
            } else if (seatNumber <= venue.getLeft() + venue.getMiddle() + venue.getRight()) {
                return seatingPrice[2];
            }
        } else {
            if (seatNumber <= venue.getLeft()) {
                return standingPrice[0];
            } else if (seatNumber <= venue.getLeft() + venue.getMiddle()) {
                return standingPrice[1];
            } else if (seatNumber <= venue.getLeft() + venue.getMiddle() + venue.getRight()) {
                return standingPrice[2];
            }
        }
        return 0.0;
    }

    /**
     * Sets the prices for a specific zone.
     *
     * @param zone   the type of the zone (VIP, SEATING, STANDING)
     * @param left   the price for the left section
     * @param middle the price for the middle section
     * @param right  the price for the right section
     */
    public void setSeatPrice(String zone, Double left, Double middle, Double right) {
        if (zone.equals(Constants.VIP)) {
            vipPrice[0] = left;
            vipPrice[1] = middle;
            vipPrice[2] = right;
        } else if (zone.equals(Constants.SEATING)) {
            seatingPrice[0] = left;
            seatingPrice[1] = middle;
            seatingPrice[2] = right;
        } else {
            standingPrice[0] = left;
            standingPrice[1] = middle;
            standingPrice[2] = right;
        }
    }
}
