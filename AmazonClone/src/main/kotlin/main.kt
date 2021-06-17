/*Amazon Clone is an app that will mimic the main functions that Amazon handles:
1.- Be able to register an user
2.- Be able to login an user
3.- Be able to upload products for sale
4.- Be able to buy products (complete checkout process*)

*The complete checkout process includes:
- Adding an item to the cart
- View the cart and the total
- Pay for the product
- Monitor the product delivery
* */

//Variables to be used in our app:
//1.- For handling user registration
var registrationUsername: String = ""
var registrationEmail: String = ""
var registrationPassword: String = ""
var registrationPasswordConfirmation: String = ""

//2.- For handling logging and session
var username: String = ""
var password: String = ""
var session: Boolean = false
var registeredUsernames = listOf<String>()
var registeredPasswords = listOf<String>()

//3.- For uploading a product
var productName: String = ""
var productCategory: String = ""
var productStatus: String = ""
var productPrice: String = ""
var productDescription: String = ""

//4.- For buying a product
var productCart = listOf<String>()
var totalPrice = 0F
//Credit card details
var cardNumber: Long = 0
var cardName: String = ""
var cardDate: String = ""
var cardCVC: Byte = 0
//Ship to information
var firstName: String = ""
var lastName: String = ""
var addressLine: String = ""
var city: String = ""
var state: String = ""
var zipCode: Short = 0
var country: String = ""
var phoneNumber: Int = 0
var shipDate: String = ""
var trackingNumber: String = ""
var orderStatus: String = ""


fun main() {
    //Show welcome menu
    println("Welcome to <name in progress>!")
    println("What do you want to do?")
    println("1.- Login")    
    println("2.- Register")
    val firstOption: Byte = readLine()?.toByte()!!

    //Login path
    println("Please enter your username")
    username = readLine()!!
    println("Please enter your password")
    password = readLine()!!
    //Validate user input
    //If everything is ok, set session variable to true
    session = true

    //Register path
    println("Please enter an username")
    registrationUsername = readLine()!!
    //Validate if username already exists
    println("Please enter an email")
    registrationEmail = readLine()!!
    //Extra validation: Check for duplicate emails
    println("Please enter a password")
    //Extra validation: Check user password structure
    registrationPassword = readLine()!!
    println("Please re-enter your password")
    registrationPasswordConfirmation = readLine()!!
    //Validate if passwords match
    //If everything is ok, set session variable to true
    session = true

    //Display articles list

}



