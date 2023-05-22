let id = id => document.getElementById(id);

//Establish the WebSocket connection and set up event handlers
let ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/gol");
ws.onmessage = msg => update(msg);
ws.onclose = () => alert("WebSocket connection closed");
let restarts = 0;

function update(msg) { // Update chat-panel and list of connected users
    let data = JSON.parse(msg.data);
    console.log('watchers', data);
    if (data.watcherMessage !== undefined) {
        id("watchers").innerHTML = data.watcherMessage;
    }


    if (data.restart) {
        restarts += 1;
        id("restarts").innerText = `Number of restarts: ${restarts}`;
    }

    if (data.board) {
        drawBoard(data.board, 50);
    }
}

const canvas = document.getElementById("canvas");
const context = canvas.getContext("2d");
function drawBoard(board, squareSize) {
    const numSquares = board.length;
    canvas.width = numSquares * squareSize;
    canvas.height = numSquares * squareSize;

    // Draw each square
    for (let i = 0; i < numSquares; i++) {
        for (let j = 0; j < board[i].length; j++) {
            if (board[i][j] === 1) {
                context.fillStyle = "rgb(240,128,128)";
                context.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
            } else {
                context.strokeRect(i * squareSize, j * squareSize, squareSize, squareSize);
            }
        }
    }
}

drawBoard([], 50);
