import models.Product
import models.User
import java.lang.NumberFormatException
import java.util.regex.Pattern
import kotlin.system.exitProcess

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

//If code fails to compile because Redeclaration error, use: ./gradlew clean

//TODO: Optimize isLogged property usage
//TODO: Implement functions for general use business logic (repetitive ones)
//TODO: Optimize code

//Variables to be used in our app:
//1.- For handling user registration
var registrationUsername: String = ""
var registrationEmail: String = ""
//var registeredEmails = arrayListOf<String>()
var registrationPassword: String = ""
var registrationPasswordConfirmation: String = ""
var registeredUsersList = arrayListOf<User>()

//2.- For handling logging and session
//Use ? to let Kotlin know that this value can be null (i.e. User typing enter without entering a value)
var username: String? = ""
var password: String? = ""
var currentUser: Byte = 0
//var session: Boolean = false
//var registeredUsers = mutableMapOf<String, String>()

//3.- For uploading a product
var productName: String = ""
var productCategory: String = ""
var validCategories = setOf("clothes", "technology", "home", "food", "health")
var productStatus: String = ""
var validStatus = setOf("new", "pre-owned", "owned")
var productDescription: String = ""
var productPrice: Float = 0f
var productAddedCorrectly: Boolean = false
//Consider substituting for an object. Simplifying implementation for now
var registeredProductsList = arrayListOf<Product>()

//4.- For buying a product
var productCart = mutableListOf<Product>()
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
fun validCredentials() {

    if (username == "" || password == "")
        println("Neither the username nor password can be empty\nPlease try again\n")
    else {
        for ((index, oneUser) in registeredUsersList.withIndex()) {
            if (oneUser.getUsername() == username && oneUser.getPassword() == password){
                oneUser.setIsLogged(true)
                currentUser = index.toByte()
            }
        }
    }

}

//Function to validate the required fields of the registration form
fun validRegistration(): Boolean {
    var valid = false

    fun notNullInput(): Boolean {
        return if (registrationUsername.isBlank() || registrationEmail.isBlank() || registrationPassword.isBlank() || registrationPasswordConfirmation.isBlank()) {
            println("Input data cannot be null. Please renter the information\n")
            false
        } else
            true
    }

    fun validateUsername(username: String): Boolean {
        return if (username == registrationUsername) {
            println("Username already exists, please enter a different one")
            false
        } else
            true
    }

    fun validateEmail(email: String): Boolean {
        return if (email == registrationEmail) {
            println("Email already exists, please enter a different one")
            false
        } else
            true
    }

    //Function to validate email address structure
    fun isEmailValid(email: String): Boolean {
        return if (Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
            ).matcher(email).matches())
            true
        else {
            println("Email structure invalid, please follow email@domain.com")
            false
        }
    }

    fun isPasswordSafe(password: String): Boolean{
        return if (Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$").matcher(password).matches())
            true
        else {
            println("Password structure invalid, please follow this guidelines:")
            println("At least one digit (0-9)")
            println("At least one lower case letter (a-z)")
            println("At least one upper case letter (A-Z)")
            println("At least one special character (@#\\$%^&+=)")
            println("No white spaces")
            println("At least 8 characters")
            false
        }
    }

    fun validatePasswords(): Boolean {
        return if (registrationPassword == registrationPasswordConfirmation)
            true
        else {
            println("Passwords don't match")
            false
        }
    }

    //If a username exists loop and check for duplicate
    //If not, just return true
    if (registeredUsersList.isEmpty())
        valid = isEmailValid(registrationEmail) && validatePasswords() && isPasswordSafe(registrationPassword)
    else {
        for (oneUser in registeredUsersList) {
            if(validateUsername(oneUser.getUsername()) && validateEmail(oneUser.email) && isEmailValid(registrationEmail) && validatePasswords() && isPasswordSafe(registrationPassword)){
                valid = true
            }
        }
    }

    return notNullInput() && valid
}

//Function to check if a proper product was introduced
@OptIn(ExperimentalStdlibApi::class)
fun validateProduct(): Boolean {

    fun notNullInput(): Boolean {
        return if (productName.isBlank() || productCategory.isBlank() || productStatus.isBlank() || productDescription.isBlank()) {
            println("Input data cannot be null. Please renter the information\n")
            false
        } else
            true
    }

    fun validCategory(): Boolean {
        return if (validCategories.contains(productCategory.lowercase()))
            true
        else {
            println("Please enter a valid category")
            false
        }
    }

    fun validStatus(): Boolean {
        return if (validStatus.contains(productStatus.lowercase()))
            true
        else {
            println("Please enter a valid status")
            false
        }
    }

    fun validDescription(): Boolean {
        return if (productDescription.length > 200){
            println("Product description should be less than 200 words")
            false
        }
        else
            true
    }

    return notNullInput() && validCategory() && validStatus() && validDescription()
}

fun userLogged(): Boolean {
    var valid = false

    //If a username exists loop and check for duplicate
    //If not, just return true
    if (registeredUsersList.isNotEmpty()){
        for (oneUser in registeredUsersList){
            if(oneUser.isLogged())
                valid = true
        }
    }

    return valid
}

fun logout() {
    registeredUsersList.elementAt(currentUser.toInt()).setIsLogged(false)
    currentUser = 0
}

fun displayCurrentCart() {
    if(productCart.isEmpty()){
        println("No items have been added yet to the cart\n")
    }
    else {
        println("Current items in cart: ")
        println("    Product name - Category - Status - Description - Price")
        productCart.forEachIndexed { index, element -> println("${index + 1}.- ${element.productInformation()}") }
        println()
    }
}

fun displayRegisteredItems(): Boolean {
    return if(registeredProductsList.isEmpty()){
        println("No items have been added, sorry for the inconveniences")
        println("Please add an item first\n")
        false
    }
    else {
        displayCurrentCart()
        println("Please select the item number you would like to buy: ")
        println("    Product name - Category - Status - Description - Price")
        registeredProductsList.forEachIndexed { index, element -> println("${index + 1}.- ${element.productInformation()}") }
        true
    }
}

fun main() {
    //For testing purposes
    registeredUsersList.add(User("juanma", "juan@test.com", "Ju4nM4#45"))
    registeredProductsList.add(Product("test product", "home", "new", "testing description length", 12f))
    var firstOption: Byte = 1
    //Do while to keep the user iterating over the menu options till he decides to leave
    do {
        //Check to see if user has logged in
        if (!userLogged()){
            //Show welcome menu
            println("\nWelcome to <name in progress>!")
            println("What do you want to do?")
            println("1.- Login")
            println("2.- Register")
            println("3.- Exit")
            firstOption = try {
                readLine()!!.toByte()
            } catch (e: NumberFormatException){
                4
            }

            //Decision path: login, register or exit
            when (firstOption) {
                1.toByte() -> {
                    //Login path
                    if (registeredUsersList.isEmpty()) {
                        println("No users have been registered in the system. \nPlease register one first before attempting to login")
                    } else {
                        do {
                            var secondOption: Byte = 3
                            println("Please enter your username")
                            //Check if the value read is null or not. If it is, then assign ""
                            username = readLine()?:""
                            println("Please enter your password")
                            //Check if the value read is null or not. If it is, then assign ""
                            password = readLine()?:""
                            //Validate user input: if everything is ok, set session variable to true
                            validCredentials()
                            //Surround with try-catch to prevent the user from accessing an inexistent element in the registered user's array
                            try {
                                if (registeredUsersList.elementAt(currentUser.toInt()).isLogged()){
                                    println("Login successful! \nWelcome $username")
                                    break
                                } else {
                                    if (!(username == "" || password == "")) {
                                        println("Username or password are incorrect!")
                                        println("1.- Try again")
                                        println("2.- Return to main menu")
                                        secondOption = try {
                                            readLine()!!.toByte()
                                        } catch (e: NumberFormatException){
                                            println("Option not valid, returning to main menu")
                                            2
                                        }
                                    }
                                }
                            } catch (e: ArrayIndexOutOfBoundsException) {
                                println("Not such element exists in the system. Please try again")
                                secondOption = 1
                            }
                        } while (secondOption != 2.toByte())
                    }
                }
                2.toByte() -> {
                    do {
                        //Register path
                        var thirdOption: Byte = 3
                        println("Please enter an username")
                        //Check if the value read is null or not. If it is, then assign ""
                        registrationUsername = readLine()?:""
                        println("Please enter an email")
                        //Check if the value read is null or not. If it is, then assign ""
                        registrationEmail = readLine()?:""
                        println("Follow this guidelines to create a password:")
                        println("At least one digit (0-9)")
                        println("At least one lower case letter (a-z)")
                        println("At least one upper case letter (A-Z)")
                        println("At least one special character (@#\\$%^&+=)")
                        println("No white spaces")
                        println("At least 8 characters")
                        println("Please enter a password")
                        //Check if the value read is null or not. If it is, then assign ""
                        registrationPassword = readLine()?:""
                        println("Please re-enter your password")
                        //Check if the value read is null or not. If it is, then assign ""
                        registrationPasswordConfirmation = readLine()?:""

                        //If everything is ok, set session variable to true
                        if (validRegistration()){
                            //Add new user into the system
                            //Create new user Object and add it to the list of Users
                            //registeredUsers[registrationUsername] = registrationPassword
                            registeredUsersList.add(User(registrationUsername, registrationEmail, registrationPassword, true))
                            println("New user registered successfully! \nWelcome $registrationUsername")
                            break
                        } else {
                            if(!(registrationUsername == "" || registrationEmail == "" || registrationPassword == "" || registrationPasswordConfirmation == "")){
                                println("1.- Try again")
                                println("2.- Return to main menu")
                                thirdOption = try {
                                    readLine()!!.toByte()
                                } catch (e: NumberFormatException){
                                    println("Option not valid, returning to main menu")
                                    2
                                }
                            }
                        }
                    } while (thirdOption != 2.toByte())
                }
                3.toByte() -> firstOption = 3
                else -> println("Please enter a valid and not-null option")
            }
        } else {
            //Display articles list
            println("\nWhat would you like to do?")
            println("1.- Register an item")
            println("2.- Buy an item")
            println("3.- Logout")
            val fourthOption: Byte = try {
                readLine()!!.toByte()
            } catch (e: NumberFormatException){
                4
            }

            when(fourthOption) {
                1.toByte() -> {
                    do {
                        //Register path
                        var secondOption: Byte = 3
                        println("Please enter the product name")
                        //Check if the value read is null or not. If it is, then assign ""
                        productName = readLine()?:""
                        println("Please enter the product category")
                        println("Available categories: clothes, technology, home, food or health")
                        //Check if the value read is null or not. If it is, then assign ""
                        productCategory = readLine()?:""
                        println("Please enter the product status")
                        println("Product status options: new, pre-owned or owned")
                        //Check if the value read is null or not. If it is, then assign ""
                        productStatus = readLine()?:""
                        println("Please enter the product description")
                        //Check if the value read is null or not. If it is, then assign ""
                        productDescription = readLine()?:""
                        println("Please enter the product price (in USD)")
                        var test: Boolean
                        do {
                            try {
                                productPrice = readLine()!!.toFloat()
                                test = true
                            } catch (e: NumberFormatException){
                                println("Value invalid, please enter a valid price")
                                test = false
                            }
                        } while (!test)

                        //If everything is ok, set productAddedCorrectly variable to true
                        productAddedCorrectly = validateProduct()
                        if (productAddedCorrectly){
                            //Add new product into the system
                            registeredProductsList.add(Product(productName, productCategory, productStatus, productDescription, productPrice))
                            println("New product registered successfully!")
                            break
                        } else {
                            if(!(productName == "" || productCategory == "" || productStatus == "" || productDescription == "")) {
                                println("1.- Try again")
                                println("2.- Return to main menu")
                                secondOption = try {
                                    readLine()!!.toByte()
                                } catch (e: NumberFormatException) {
                                    println("Option not valid, returning to main menu")
                                    2
                                }
                            }
                        }
                    } while (secondOption != 2.toByte())
                }
                2.toByte() -> {
                    //Buy path
                    //To know which item the user wants to buy
                    var selectedItem: Byte = 0
                    //To see if the user wants to continue buying or wants to checkout
                    var tempDecision: Byte = 1
                    //To loop while the user wants to continue adding items to his/her cart
                    do {
                        if(displayRegisteredItems()){
                            try {
                                selectedItem = readLine()!!.toByte()
                            } catch (e: NumberFormatException){
                                println("Option not valid, returning to previous menu")
                                break
                            }
                            //Check to see if the selected number matches an existing product
                            if(selectedItem <= registeredProductsList.size) { //Exists
                                productCart.add(registeredProductsList[selectedItem.toInt() - 1])
                                println("Product added successfully to the cart\n")
                            } else
                                println("Unable to add the product\nPlease select a valid option")
                            do {
                                //Check to see if user wants to continue buying
                                println("Please select what you want to do: ")
                                println("1.- Continue buying")
                                println("2.- Checkout")
                                println("3.- Remove an item from Cart")
                                try {
                                    tempDecision = readLine()!!.toByte()
                                    if (tempDecision != 1.toByte() && tempDecision != 2.toByte() && tempDecision != 3.toByte())
                                        println("Option not valid, please select a valid option\n")
                                    else if(tempDecision == 3.toByte())
                                        removeProduct()
                                } catch (e: NumberFormatException){
                                    println("Option not valid, please select a valid option\n")
                                }
                            } while (tempDecision != 1.toByte() && tempDecision != 2.toByte() && tempDecision != 3.toByte())
                            selectedItem = if(tempDecision == 2.toByte())
                                0
                            else
                                1
                        }
                    } while (selectedItem != 0.toByte())
                    //Checkout process
                    if (tempDecision == 2.toByte()){
                        //TODO: Checkout process
                        println("Checkout process")
                        checkout()
                    }
                    //TODO: Delete products added to cart
                }
                3.toByte() -> {
                    logout()
                }
                else -> println("Please enter a valid and not-null option")
            }
        }

    } while (firstOption != 3.toByte())

    //Goodbye message
    println("Thanks for using our app")
}

fun checkout(){
    var total:Float = 0f
    productCart.forEach{
        println("${it.name} $${it.price}")
        total+=it.price
    }
    println("TOTAL  $$total")

    println("1.- Continue buying")
    println("2.- EXIT")

    var tempDecision: Byte = 1
    do {
        try {
            tempDecision = readLine()!!.toByte()
            if(tempDecision == 2.toByte())
                exitProcess(-1)
        } catch (e: NumberFormatException){
            println("Option not valid, please select a valid option\n")
        }
    } while (tempDecision != 1.toByte() && tempDecision != 2.toByte())
}

fun removeProduct(){
    //Remove path
    //To know which item the user wants to remove
    var selectedItem: Byte = 0
    //To see if the user wants to continue removing or wants to checkout
    var tempDecision: Byte = 1
    //To loop while the user wants to continue removing items to his/her cart
    do {
        if(!productCart.isEmpty()){
            displayCurrentCart()
            try {
                    selectedItem = readLine()!!.toByte()
                } catch (e: NumberFormatException){
                    println("Option not valid, returning to previous menu")
                    break
                }
                //Check to see if the selected number matches an existing product
                if(selectedItem <= productCart.size) { //Exists
                    productCart.remove(productCart[selectedItem.toInt() - 1])
                    println("Product removed successfully from the cart\n")
                } else
                    println("Unable to remove the product\nPlease select a valid option")
                do {
                    //Check to see if user wants to continue buying
                    println("Please select what you want to do: ")
                    println("1.- Continue removing")
                    println("2.- Checkout")
                    println("3.- Add an item to cart")
                    try {
                        tempDecision = readLine()!!.toByte()
                        if (tempDecision != 1.toByte() && tempDecision != 2.toByte() && tempDecision != 3.toByte())
                            println("Option not valid, please select a valid option\n")
                        else if(tempDecision == 3.toByte())
                            break
                    } catch (e: NumberFormatException){
                        println("Option not valid, please select a valid option\n")
                    }
                } while (tempDecision != 1.toByte() && tempDecision != 2.toByte() && tempDecision != 3.toByte())
                selectedItem = if(tempDecision == 2.toByte())
                    0
                else
                    1
            }
        } while (selectedItem != 0.toByte())
        //Checkout process
        if (tempDecision == 2.toByte()){
            println("Checkout process")
            checkout()
        }
}
