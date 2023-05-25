# Game of life
### Server generated with updates over websockets

This is a simple and limited implementation of the traditional game of life
game. Since the game is a zero player game the game plays itself on the server
and push updates over the websocket protocol.

The limitation is that the play field is limited to the configuration on the server
side. In a "real" implementation the map should probably be unlimited. The standard
configuration is 30x30 squares.

### Running the project:

You don't have to compile it!
You can see it live here: https://gol.sorping.se/

You need a JDK to be installed on your machine and also gradle.
In order to run the project, the easiest way is to bundle it as a fat jar.

Just run the project with gradle:

`gradle clean shadowJar`

This will clean it from previous builds and buold a fat jar from scratch.
You will find the jar in the `build/libs` directory.

Run the jar by running:
`java -jar build\libs\game-of-life-1.0-SNAPSHOT-all.jar` and visit `localhost:7070` in any web browser (with support for websockets).

In order to run the tests simply run `gradle test`

### Possible improvements that can be made

1. (Finish implementing) tests of the websocket communication logic with the front end.
2. Implement an unlimited map.
3. Cover edge more edge cases in tests.
4. Improve detection of edge cases where generations gets into loops.

### The code

The code is divided into three main classes.
The first class is the GameOfLife class that handles the initial setup of the web framework Javalin and creates the app,
this is the entry point of the application. It also handles all the communication with the client.

Then second class is the GameServer class that handles the game loop which runs in it's own thread.
The GameServer class also handles logic that concerns the game loop, such as when the game is stuck either by every "life"
being the same round after round (stuck) or when the lives just goes in an endless loop.

It handles the lives being stuck by simply generating some new generations, clearing out the old.
If the lives goes into a loop, it will restart the app after a while when the ticks (game loops) are high enough.

Then there is the board class that handles everything concering the game board. It creates all the generations
and handles all the logic with generating them and calculating neighbors. It handles the rules of the game of life and applies it
to the board.

Then we have the simple front end that creates a html canvas context and renders a square of boxes.
It gets an update from the server every tick and will redraw the canvas each tick with the data it recieved from the backend.

Thus this concludes my implementation of the game of life.