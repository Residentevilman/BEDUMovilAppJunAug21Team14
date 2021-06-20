import kotlin.reflect.typeOf

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
var registeredEmails = arrayListOf<String>()
var registrationPassword: String = ""
var registrationPasswordConfirmation: String = ""

//2.- For handling logging and session
var username: String = ""
var password: String = ""
var session: Boolean = false
var registeredUsers = mutableMapOf<String, String>()

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

//Function to validate if user username and password are correct
fun validCredentials(): Boolean {
    var registeredPassword: String = ""

    fun validUsername(): Boolean {
        //Check to see if the username exists in the registered users Map
        return if (registeredUsers.containsKey(username)){
            registeredPassword = registeredUsers.getValue(username)
            true
        } else
            false
    }

    fun validPassword(): Boolean {
        return registeredPassword == password
    }

    return validUsername() && validPassword()
}

//Function to validate the required fields of the registration form
fun validRegistration(): Boolean {
    fun validateUsername(): Boolean {
        return if (registeredUsers.containsKey(registrationUsername)) {
            println("Username already exists, please enter a different one")
            false
        } else
            true
    }

    fun validateEmail(): Boolean {
        return if (registeredEmails.contains(registrationEmail)) {
            println("Email already exists, please enter a different one")
            false
        } else
            true
    }

    fun validatePasswords(): Boolean {
        return if (registrationPassword == registrationPasswordConfirmation)
            true
        else {
            println("Passwords don't match")
            false
        }
    }

    return validateUsername() && validateEmail() && validatePasswords()
}

//TODO: Validate for nulls
fun main() {

    //Do while to keep the user iterating over the menu options till he decides to leave
    do {
        //Show welcome menu
        println("\nWelcome to <name in progress>!")
        println("What do you want to do?")
        println("1.- Login")
        println("2.- Register")
        println("3.- Exit")
        var firstOption: Byte = readLine()?.toByte()!!

        //Decision path: login, register or exit
        when (firstOption) {
            1.toByte() -> {
                //Login path
                if (registeredUsers.isEmpty()) {
                    println("No users have been registered in the system. \nPlease register one first before attempting to login")
                } else {
                    do {
                        var secondOption: Byte = 1
                        println("Please enter your username")
                        username = readLine()!!
                        println("Please enter your password")
                        password = readLine()!!
                        //Validate user input: if everything is ok, set session variable to true
                        session = validCredentials()
                        if (session){
                            println("Login successful! \nWelcome $username")
                            break
                        } else {
                            println("Username or password are incorrect!")
                            println("1.- Try again")
                            println("2.- Return to main menu")
                            secondOption = readLine()!!.toByte()
                        }
                    } while (secondOption != 2.toByte())
                }
            }
            2.toByte() -> {
                do {
                    //Register path
                    var thirdOption: Byte = 1
                    println("Please enter an username")
                    registrationUsername = readLine()!!
                    println("Please enter an email")
                    registrationEmail = readLine()!!
                    println("Please enter a password")
                    //Extra validation: Check user password structure
                    registrationPassword = readLine()!!
                    println("Please re-enter your password")
                    registrationPasswordConfirmation = readLine()!!

                    //If everything is ok, set session variable to true
                    session = validRegistration()
                    if (session){
                        //Add new user into the system
                        registeredUsers.put(registrationUsername, registrationPassword)
                        registeredEmails.add(registrationEmail)
                        println("New user registered successfully! \nWelcome $registrationUsername")
                        break
                    } else {
                        println("1.- Try again")
                        println("2.- Return to main menu")
                        thirdOption = readLine()!!.toByte()
                    }
                } while (thirdOption != 2.toByte())
            }
            3.toByte() -> firstOption = 3
        }

        //Display articles list

    } while (firstOption != 3.toByte())

    //Goodbye message
    println("Thanks for using our app")
}



