import java.util.Iterator;
        import java.util.Vector;

class Movie {
    public static final int CHILDRENS = 2;
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;

    private String _title;
    private int _priceCode;

    public Movie(String title, int priceCode) {
        _title = title;
        _priceCode = priceCode;
    }

    public int getPriceCode() {
        return _priceCode;
    }

    public void setPriceCode(int arg) {
        _priceCode = arg;
    }

    public String getTitle() {
        return _title;
    }
}

class Rental {
    private Movie _movie;
    private int _daysRented;

    public Rental(Movie movie, int daysRented) {
        _movie = movie;
        _daysRented = daysRented;
    }

    public int getDaysRented() {
        return _daysRented;
    }

    public Movie getMovie() {
        return _movie;
    }


    //Make the amountFor a method of Rental itself
    public double amountFor(){
        double thisAmount = 0;
        // determine amounts for each line
        switch(this.getMovie().getPriceCode()){
            case Movie.REGULAR:
                thisAmount += 2;
                if(this.getDaysRented() > 2)
                    thisAmount += (this.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += this.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if(this.getDaysRented() > 3)
                    thisAmount +=(this.getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }

}

class Customer {
    private String _name;
    private Vector<Rental> _rentals = new Vector<Rental>();

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental rental) {
        _rentals.addElement(rental);
    }

    public String getName() {
        return _name;
    }

    //Make the switch into a new method
  /*  private double amountFor(Rental each){
        double thisAmount = 0;
        // determine amounts for each line
        switch(each.getMovie().getPriceCode()){
            case Movie.REGULAR:
                thisAmount += 2;
                if(each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += each.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if(each.getDaysRented() > 3)
                    thisAmount +=(each.getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }*/
    //Now this can be very simple
    private double amountFor(Rental each){
        return each.amountFor();
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Iterator<Rental> rentals = _rentals.iterator();

        String result = "Rental Record for " + getName() + "\n";

        while(rentals.hasNext()) {
            //double thisAmount = 0;
            Rental each = rentals.next();

            totalAmount += each.amountFor();

            // add frequent renter points
            ++frequentRenterPoints;
            // add bonus for a two day new release rental
            if((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
                    each.getDaysRented() > 1) ++frequentRenterPoints;

            // show figures for this rental
            result += "\t" + each.getMovie().getTitle() + "\t" +
                    String.valueOf(each.amountFor()) + "\n";

        }

        // add footer lines
        result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points";
        return result;
    }
}

final class Refac1 {
    public static void main(String[] args) {
        System.out.println("Refac1.java");

        Customer x = new Customer("Penny");
        assert x.statement().equals(
                "Rental Record for Penny\n" +
                        "Amount owed is 0.0\n" +
                        "You earned 0 frequent renter points");

        x.addRental(new Rental(new Movie("Shane", Movie.REGULAR), 2));
        assert x.statement().equals(
                "Rental Record for Penny\n" +
                        "\tShane\t2.0\n" +
                        "Amount owed is 2.0\n" +
                        "You earned 1 frequent renter points");

        x.addRental(new Rental(new Movie("Red River", Movie.REGULAR), 5));
        assert x.statement().equals(
                "Rental Record for Penny\n" +
                        "\tShane\t2.0\n" +
                        "\tRed River\t6.5\n" +
                        "Amount owed is 8.5\n" +
                        "You earned 2 frequent renter points");

        x.addRental(new Rental(new Movie("Giant", Movie.NEW_RELEASE), 1));
        assert x.statement().equals(
                "Rental Record for Penny\n" +
                        "\tShane\t2.0\n" +
                        "\tRed River\t6.5\n" +
                        "\tGiant\t3.0\n" +
                        "Amount owed is 11.5\n" +
                        "You earned 3 frequent renter points");

        x.addRental(new Rental(new Movie("2001", Movie.NEW_RELEASE), 3));
        assert x.statement().equals(
                "Rental Record for Penny\n" +
                        "\tShane\t2.0\n" +
                        "\tRed River\t6.5\n" +
                        "\tGiant\t3.0\n" +
                        "\t2001\t9.0\n" +
                        "Amount owed is 20.5\n" +
                        "You earned 5 frequent renter points");

        x.addRental(new Rental(new Movie("Big Country", Movie.CHILDRENS), 3));
        assert x.statement().equals(
                "Rental Record for Penny\n" +
                        "\tShane\t2.0\n" +
                        "\tRed River\t6.5\n" +
                        "\tGiant\t3.0\n" +
                        "\t2001\t9.0\n" +
                        "\tBig Country\t1.5\n" +
                        "Amount owed is 22.0\n" +
                        "You earned 6 frequent renter points");

        System.out.println("Done.");
    }
}