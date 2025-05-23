/**
 * =====================================================================
 * Programming Project for NCEA Level 3, Standard 91906
 * ---------------------------------------------------------------------
 * Project Name:   Out Of Control
 * Project Author: Torrian Kinred-Harding
 * GitHub Repo:    GITHUB REPO URL HERE
 * ---------------------------------------------------------------------
 * Notes:
 * PROJECT NOTES HERE
 * =====================================================================
 */



import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Data fields
    var hasKey: Boolean
    var currentRoom: Room
    var keyRoom: Room
    var needsKeyRoom: List<Room>
    var finalRoom: List<Room>

    //defining every room
    init {
        val quarters = Room("Personal Quarters", "HUD has gone out of control. Your options are: find HUD and convince " +
                "them to resume work, shut off the factory to prevent a meltdown, or leave the facility outright.")
        val quickTravelPoint = Room("Travel Point", "You can head to the main exit of the facility much faster from here," +
                "if you have the Overall Access Key." )
        val hallway2AF = Room("Hallway 2-AF", "This is one of the main hallways used for the overview team to " +
                "traverse the facility for routine inspections and leaving at the end of the day.")
        val keyHere = Room("Overview Team Storage Room"," The main area wherein the overview team keeps personal belongings" +
                "during work hours")
        val endHUD = Room("HUD", "The main operations of HUD are here. Congratulations on winning the game.")
        val messHall = Room("Mess Hall", "The main area where the overview team eats. Not the only, but the main.")
        val productionLine = Room("Production Line", "The beating heart of the UPF, or at least it's supposed to be." +
                "The entire facility is either stopped, or malfunctioning. It seems to be stable for now, but that could change. Keep going.")
        val endFactorySettings = Room("Factory Settings", "The factory operations can be manually adjusted from here. Congratulations on " +
                "winning the game.")
        val shippingArea = Room("Shipping Area", "This area handles all the materials coming in and goods coming out." +
                "There are several automated vehicles currently driving in random directions like bumper cars on the ground floor.")
        val quickTravelDestination = Room("Travel Destination", "You can reach this point quickly from the Personal Quarters " +
                "if you have the access key." )
        val centralAtrium = Room("Central Atrium", "The 'lobby' of the UPF. Exit is within your grasp.")
        val endExit = Room("Exit", "The front door. Congratulations on winning the game.")


        //defining the directional relationships between the Personal Quarters and the other rooms
        quarters.northEast = quickTravelPoint
        quarters.south = hallway2AF

        //defining the directional relationships between the Hallway 2-AF and the other rooms
        hallway2AF.north = quarters
        hallway2AF.east = messHall
        hallway2AF.south = keyHere

        //defining the directional relationships between the Overview Team Storage Room and the other rooms
        keyHere.north = hallway2AF
        keyHere.southEast = endHUD

        //defining the directional relationships between the Travel Point and the other rooms
        quickTravelPoint.southWest = quarters
        quickTravelPoint.south = messHall
        quickTravelPoint.east = quickTravelDestination

        //defining the directional relationships between the Travel Destination and the other rooms
        quickTravelDestination.southEast = centralAtrium

        //defining the directional relationships between the Mess Hall and the other rooms
        messHall.north = quickTravelPoint
        messHall.west = hallway2AF
        messHall.northEast = productionLine

        //defining the directional relationships between the Production Line and the other rooms
        productionLine.southWest = messHall
        productionLine.southEast = shippingArea
        productionLine.south = endFactorySettings

        //defining the directional relationships between the Shipping Area and the other rooms
        shippingArea.northWest = productionLine
        shippingArea.north = centralAtrium

        //defining the directional relationships between the Shipping Area and the other rooms
        centralAtrium.northWest = quickTravelDestination
        centralAtrium.south = shippingArea
        centralAtrium.east = endExit

        /**
         * resetting things every time the window is opened;
         * current room is defined as the Personal Quarters
         * and the player having the key is set as FALSE
         */
        currentRoom = quarters
        keyRoom = keyHere
        needsKeyRoom = listOf(quickTravelDestination, endHUD, endFactorySettings, endExit)
        finalRoom = listOf(endHUD, endFactorySettings, endExit)
        hasKey = false
    }

    // Shared movement logic
    fun moveToRoom(nextRoom: Room?) {

        //If there's no room to go to than return
        if (nextRoom == null) return

        //If entering the room requires the key and player doesn't have key then return
        if (nextRoom in needsKeyRoom && !hasKey) return

        /**
         * this works by checking if the room defined as [direction] to the current room
         * is NOT null. If it is not, then the current room is redefined as that room.
         */
        currentRoom = nextRoom

        // Final room reached — show message after a delay
        if (nextRoom in finalRoom) {
            Timer(3000) { // 3-second delay before showing the dialog
                JOptionPane.showMessageDialog(null, "You win!")
                System.exit(0)  // Close the app after showing the message
            }.start()
        }
    }

    fun moveNorth() = moveToRoom(currentRoom.north)
    fun moveNorthEast() = moveToRoom(currentRoom.northEast)
    fun moveEast() = moveToRoom(currentRoom.east)
    fun moveSouthEast() = moveToRoom(currentRoom.southEast)
    fun moveSouth() = moveToRoom(currentRoom.south)
    fun moveSouthWest() = moveToRoom(currentRoom.southWest)
    fun moveWest() = moveToRoom(currentRoom.west)
    fun moveNorthWest() = moveToRoom(currentRoom.northWest)
}

//this makes the main class; the rooms in the game.
class Room(val name: String, val description: String) {
    var north: Room? = null
    var northEast: Room? = null
    var east: Room? = null
    var southEast: Room? = null
    var south: Room? = null
    var southWest: Room? = null
    var west: Room? = null
    var northWest: Room? = null
}

/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the movement buttons
    private lateinit var northButton: JButton
    private lateinit var northwestButton: JButton
    private lateinit var westButton: JButton
    private lateinit var southwestButton: JButton
    private lateinit var southButton: JButton
    private lateinit var southeastButton: JButton
    private lateinit var eastButton: JButton
    private lateinit var northeastButton: JButton

    // Fields to hold the text boxes and key indicator
    private lateinit var areaName: JLabel
    private lateinit var areaDesc: JLabel
    private lateinit var keyYesNo: JLabel

     //Configure the UI and display it
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        updateView()                    // Initialise the UI
    }


    //Configure the main window
    private fun configureWindow() {
        title = "Out Of Control"
        contentPane.preferredSize = Dimension(800, 400)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }


    //Populate the UI with UI controls
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 12)

        //these two code blocks show the Name and Description of the current room
        areaName = JLabel("AREA NAME HERE")
        areaName.horizontalAlignment = SwingConstants.CENTER
        areaName.bounds = Rectangle(25, 25, 350, 50)
        areaName.font = baseFont
        add(areaName)

        areaDesc = JLabel("AREA DESCRIPTION HERE")
        areaDesc.horizontalAlignment = SwingConstants.CENTER
        areaDesc.bounds = Rectangle(25, 100, 350, 150)
        areaDesc.font = baseFont
        add(areaDesc)

        //this code block shows the key of the game, if the player has it
        keyYesNo = JLabel("SHOW KEY HERE")
        keyYesNo.horizontalAlignment = SwingConstants.CENTER
        keyYesNo.bounds = Rectangle(25, 275, 100, 100)
        keyYesNo.font = baseFont
        add(keyYesNo)

        //these eight code blocks show the movement buttons
        northeastButton = JButton("NE")
        northeastButton.bounds = Rectangle(600, 150, 50, 50)
        northeastButton.font = baseFont
        northeastButton.addActionListener(this)     // Handle any clicks
        add(northeastButton)

        northButton = JButton("N")
        northButton.bounds = Rectangle(550, 150, 50, 50)
        northButton.font = baseFont
        northButton.addActionListener(this)     // Handle any clicks
        add(northButton)

        northwestButton = JButton("NW")
        northwestButton.bounds = Rectangle(500, 150, 50, 50)
        northwestButton.font = baseFont
        northwestButton.addActionListener(this)     // Handle any clicks
        add(northwestButton)

        westButton = JButton("W")
        westButton.bounds = Rectangle(500, 200, 50, 50)
        westButton.font = baseFont
        westButton.addActionListener(this)     // Handle any clicks
        add(westButton)

        southwestButton = JButton("SW")
        southwestButton.bounds = Rectangle(500, 250, 50, 50)
        southwestButton.font = baseFont
        southwestButton.addActionListener(this)     // Handle any clicks
        add(southwestButton)

        southButton = JButton("S")
        southButton.bounds = Rectangle(550, 250, 50, 50)
        southButton.font = baseFont
        southButton.addActionListener(this)     // Handle any clicks
        add(southButton)

        southeastButton = JButton("SE")
        southeastButton.bounds = Rectangle(600, 250, 50, 50)
        southeastButton.font = baseFont
        southeastButton.addActionListener(this)     // Handle any clicks
        add(southeastButton)

        eastButton = JButton("E")
        eastButton.bounds = Rectangle(600, 200, 50, 50)
        eastButton.font = baseFont
        eastButton.addActionListener(this)     // Handle any clicks
        add(eastButton)
    }

    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        areaName.text = app.currentRoom.name
        areaDesc.text = "<html>" + app.currentRoom.description

        /**
         * These lines disable the corresponding movement button
         * if there is no room to move to in that direction
         */
        northButton.isEnabled = app.currentRoom.north != null
        northeastButton.isEnabled = app.currentRoom.northEast != null
        eastButton.isEnabled = app.currentRoom.east != null
        southeastButton.isEnabled = app.currentRoom.southEast != null
        southButton.isEnabled = app.currentRoom.south != null
        southwestButton.isEnabled = app.currentRoom.southWest != null
        westButton.isEnabled = app.currentRoom.west != null
        northwestButton.isEnabled = app.currentRoom.northWest != null

        if (app.hasKey) keyYesNo.text = "You have the key" else keyYesNo.text = "You do not have the key"
        if (app.currentRoom == app.keyRoom) app.hasKey = true
    }

    /**
     * These lines handle UI events; aka button clicks
     * Executes the function to move in the
     * corresponding direction, then updates the
     * panel to show the new current room and
     * all the directions you can go from there
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            northButton -> {
                app.moveNorth()
                updateView()
            }
            northwestButton -> {
                app.moveNorthWest()
                updateView()
            }
            westButton -> {
                app.moveWest()
                updateView()
            }
            southwestButton -> {
                app.moveSouthWest()
                updateView()
            }
            southButton -> {
                app.moveSouth()
                updateView()
            }
            southeastButton -> {
                app.moveSouthEast()
                updateView()
            }
            eastButton -> {
                app.moveEast()
                updateView()
            }
            northeastButton -> {
                app.moveNorthEast()
                updateView()
            }
        }
    }
}