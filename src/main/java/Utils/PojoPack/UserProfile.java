package Utils.PojoPack;

public class UserProfile {
    private String title;
    private String name;
    private String email;
    private String password;
    private String day;
    private String month;
    private String year;
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String address2;
    private String country;
    private String state;
    private String city;
    private String zipcode;
    private String mobileNumber;
    private String nameOnCard;
    private String cardNumber;
    private String cvc;

    public String getExpirationDateMonth() {
        return expirationDateMonth;
    }

    public void setExpirationDateMonth(String expirationDateMonth) {
        this.expirationDateMonth = expirationDateMonth;
    }

    public String getExpirationDateYear() {
        return expirationDateYear;
    }

    public void setExpirationDateYear(String expirationDateYear) {
        this.expirationDateYear = expirationDateYear;
    }

    private String expirationDateMonth;
    private String expirationDateYear;

    // Default constructor
    public UserProfile() {}

    // Parameterized constructor
    public UserProfile(String title, String name, String email, String password,
                       String day, String month, String year,
                       String firstName, String lastName, String company,
                       String address, String address2, String country,
                       String state, String city, String zipcode, String mobileNumber, String nameOnCard, String cardNumber, String cvc, String expirationDateMonth, String expirationDateYear) {
        this.title = title;
        this.name = name;
        this.email = email;
        this.password = password;
        this.day = day;
        this.month = month;
        this.year = year;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.address = address;
        this.address2 = address2;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipcode = zipcode;
        this.mobileNumber = mobileNumber;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.expirationDateMonth = expirationDateMonth;
        this.expirationDateYear = expirationDateYear;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZipcode() { return zipcode; }
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }


    public String getNameOnCard() { return nameOnCard; }
    public void setNameOnCard(String nameOnCard) { this.nameOnCard = nameOnCard; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCvc() { return cvc; }
    public void setCvc(String cvc) { this.cvc = cvc; }

    @Override
    public String toString() {
        return "UserProfile{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", nameOnCard='" + nameOnCard + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvc='" + cvc + '\'' +
                ", expirationDateMonth='" + expirationDateMonth + '\'' +
                ", expirationDateYear='" + expirationDateYear + '\'' +
                '}'
                ;
    }
}
