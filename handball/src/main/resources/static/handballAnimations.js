let playerTeamId = 1;
let playerTeamName = "playerTeamName";
let playerTeamShortName = "playerTeamShortName";
let playerTeamImageLink = "http://localhost:8080/assets/kif-logo.png";

let playerPosition = "RIGHT_WING";
let playerName = "Henrik Christensen";
let playerJerseyNumber = "18";
let playerImageLink  = "http://localhost:8080/assets/henne.jpg";

let assistingPlayerName = null;
let assistingPlayerJerseyNumber = "100";
let assistingPlayerPosition = "http://localhost:8080/assets/henne.jpg";
let assistingPlayerTeamImageLink = "http://localhost:8080/assets/kif-logo.png";

let opponentTeamId = 2;
let opponentTeamName = "opponentTeamName";
let opponentTeamShortName = "opponentTeamShortName";
let opponentTeamImageLink = "http://localhost:8080/assets/images/team/FHK.png";

let goalKeeperPosition = 'HOME_KEEPER';
let goalKeeperName = "Magnus Brandbyge";
let goalKeeperJerseyNumber = "1";
let goalKeeperImageLink = "http://localhost:8080/assets/Henrik.jpg";

let opponentPlayerPos = "HOME_LEFT_WING";
let opponentPlayerName = "Lasse Hvilsted";
let opponentPlayerJerseyNumber = "50";
let opponentPlayerImageLink = "http://localhost:8080/assets/Henrik.jpg";

let matchTime = "33:12";
let actionType = "CAUGHT";
let homeTeamName = "playerTeamName";

const missedSpotsTop = [
    { top: '5%', left: '10%' },
    { top: '5%', left: '90%' }
];

const url = window.location.href;
const matchId = url.split('/').pop();
console.log("Match ID = " + matchId);

function updateActionData(actionData) {
    playerTeamId = actionData.teamId || null;
    playerTeamName = actionData.teamName || null;
    playerTeamShortName = actionData.teamShortName || null;
    playerTeamImageLink = actionData.teamImageLink || null;

    playerPosition = actionData.playerPosition || null;
    playerName = actionData.playerName || null;
    playerJerseyNumber = actionData.playerJerseyNumber || null;
    playerImageLink = actionData.playerImageLink || null;

    assistingPlayerName = actionData.assistingPlayerName || null;
    assistingPlayerJerseyNumber = actionData.assistingPlayerJerseyNumber || null;
    assistingPlayerPosition = actionData.assistingPlayerPosition || null;
    assistingPlayerTeamImageLink = actionData.assistingPlayerTeamImageLink || null;

    opponentTeamId = actionData.opponentTeamid || null;
    opponentTeamName = actionData.opponentTeamName || null;
    opponentTeamShortName = actionData.opponentTeamShortName || null;
    opponentTeamImageLink = actionData.opponentTeamImageLink || null;

    goalKeeperName = actionData.goalKeeperName || null;
    goalKeeperPosition = actionData.goalKeeperPosition || null;
    goalKeeperJerseyNumber = actionData.goalKeeperJerseyNumber || null;
    goalKeeperImageLink = actionData.goalKeeperImageLink || null;

    opponentPlayerPos = actionData.opponentPlayerPos || null;
    opponentPlayerName = actionData.opponentPlayerName || null;
    opponentPlayerJerseyNumber = actionData.opponentPlayerJerseyNumber || null;
    opponentPlayerImageLink = actionData.opponentPlayerImageLink || null;

    matchTime = actionData.matchTime;
    actionType = actionData.actionType;

    console.log(actionData);

    action();
}

function updatePlayerInfo(playerIdPrefix, playerName, playerNumber, playerImageLink, teamImageLink) {
    let playerTeamImage = document.getElementById(`${playerIdPrefix}_TEAM_IMAGE`);
    let playerImage = document.getElementById(`${playerIdPrefix}_IMAGE_SMALL`);
    let playerNumberEl = document.getElementById(`${playerIdPrefix}_NUMBER`);
    let playerNameEl = document.getElementById(`${playerIdPrefix}_NAME`);

    playerTeamImage.src = teamImageLink;
    playerImage.src = playerImageLink;
    playerNumberEl.innerText = playerNumber ? `#${playerNumber}` : '';
    playerNameEl.innerText = playerName;
}

function displayActionInfo() {
    updatePlayerInfo('THROWING_PLAYER', playerName, playerJerseyNumber, playerImageLink , playerTeamImageLink);

    const assistingPlayerDiv = document.getElementById("ASSISTING_PLAYER");
    if (assistingPlayerName != null) {
        updatePlayerInfo('ASSISTING_PLAYER', assistingPlayerName, assistingPlayerJerseyNumber, assistingPlayerPosition , assistingPlayerTeamImageLink);
        assistingPlayerDiv.style.display = "flex";
    } else {
        assistingPlayerDiv.style.display = "none";
    }

    updatePlayerInfo('GOALKEEPER', goalKeeperName, goalKeeperJerseyNumber, goalKeeperImageLink, opponentTeamImageLink);

    document.getElementById("MATCH_TIME").innerText = matchTime;
    document.getElementById("THROWING_PLAYER_ACTION").innerText = actionType;
    document.getElementById("ACTION_INFO").style.display = "flex";

    action();
}


function throwBall() {
    displayActionInfo();
    let positionString = "";
    if(playerTeamName === homeTeamName) {
        positionString += "HOME_";
    } else {
        positionString += "AWAY_"
    }

    const playerImage = document.getElementById((positionString+playerPosition+"_IMAGE"));
    playerImage.src = playerImageLink;
    playerImage.style.display = "unset";
    const goalKeeperImage = document.getElementById((goalKeeperPosition+"_IMAGE"));
    goalKeeperImage.src = goalKeeperImageLink;
    goalKeeperImage.style.display = "unset";

    const playerElement = document.getElementById(playerPosition);
    if (!playerElement) {
        console.error('Player element not found:', playerPosition);
        return;
    }

    const fieldRect = document.querySelector('.field').getBoundingClientRect();
    const playerRect = playerElement.getBoundingClientRect();

    const playerXRelativeToField = ((playerRect.left + playerRect.right) / 2) - fieldRect.left;
    const playerYRelativeToField = ((playerRect.top + playerRect.bottom) / 2) - fieldRect.top;

    const playerXPercentage = `${(playerXRelativeToField / fieldRect.width) * 100}%`;
    const playerYPercentage = `${(playerYRelativeToField / fieldRect.height) * 100}%`;

    const ball = document.getElementById('ball');
    ball.style.position = 'absolute';
    ball.style.opacity = '1';
    ball.style.top = playerYPercentage;
    ball.style.left = playerXPercentage;
    ball.style.transform = 'translate(-50%, -50%)';

    const keyframes = [
        { top: playerYPercentage, left: playerXPercentage, transform: 'rotate(0deg)'},
        { top: '-6.4vh', left: '50%', transform: 'rotate(1060deg)'}
    ];

    ball.animate(keyframes, {
        duration: 2000,
        fill: 'forwards'
    });


    setTimeout(() => {
        ball.style.opacity = '0';
        document.getElementById("ACTION_INFO").style.display = "none";
        playerImage.style.display = "none";
        goalKeeperImage.style.display = "none";
    }, 4500);
}

function action() {
    switch (actionType) {
        case "GOAL":
            throwBall();
            break;
        case "CAUGHT":
            throwCaughtBall();
            break;
        case "MISSED":
            throwMissedBall();
            break;
    }
}

function throwMissedBall() {
    displayActionInfo();

    const playerImage = document.getElementById((playerPosition+"_IMAGE"));
    if(playerImageLink != null){
        playerImage.src = playerImageLink ;
    } else {
        playerImage.src = "http://localhost:8080/assets/images/player/player.png";
    }

    playerImage.style.display = "block";

    const ball = document.getElementById('ball');
    ball.style.opacity = '1';

    const fieldRect = document.querySelector('.field').getBoundingClientRect();
    const playerRect = playerImage.getBoundingClientRect();
    const startTop = `${(playerRect.top + playerRect.height / 2 - fieldRect.top) / fieldRect.height * 100}%`;
    const startLeft = `${(playerRect.left + playerRect.width / 2 - fieldRect.left) / fieldRect.width * 100}%`;

    ball.style.top = startTop;
    ball.style.left = startLeft;
    ball.style.transform = 'translate(-50%, -50%)';

    const missedSpots = [
        { top: '-5%', left: '30%' },
        { top: '-5%', left: '70%' }
    ];

    const targetSpot = missedSpots[Math.floor(Math.random() * missedSpots.length)];

    const keyframes = [
        { top: startTop, left: startLeft, transform: 'rotate(0deg)' },
        { top: targetSpot.top, left: targetSpot.left, transform: 'rotate(360deg)' }
    ];

    ball.animate(keyframes, {
        duration: 2000,
        fill: 'forwards'
    }).onfinish = () => {
        setTimeout(() => {
            document.getElementById("ACTION_INFO").style.display = "none";
            ball.style.opacity = '0';
            playerImage.style.display = "none";
        }, 4500);
    };
}

function throwCaughtBall() {
    displayActionInfo();

    const playerImage = document.getElementById((playerPosition + "_IMAGE"));
    const goalKeeperImage = document.getElementById((goalKeeperPosition + "_IMAGE"));

    if(playerImageLink != null){
        playerImage.src = playerImageLink ;
    } else {
        playerImage.src = "http://localhost:8080/assets/images/player/player.png";
    }

    if(goalKeeperImageLink != null) {
        goalKeeperImage.src = goalKeeperImageLink;
    } else {
        goalKeeperImage.src = "http://localhost:8080/assets/images/player/player.png";
    }

    playerImage.style.display = "block";
    goalKeeperImage.style.display = "block";

    const ball = document.getElementById('ball');
    const fieldRect = document.querySelector('.field').getBoundingClientRect();
    const playerRect = playerImage.getBoundingClientRect();
    const goalKeeperRect = goalKeeperImage.getBoundingClientRect();

    const ballStartPosition = {
        x: ((playerRect.left + playerRect.width / 2) - fieldRect.left) / fieldRect.width * 100,
        y: ((playerRect.top + playerRect.height / 2) - fieldRect.top) / fieldRect.height * 100
    };

    const ballEndPosition = {
        x: ((goalKeeperRect.left + goalKeeperRect.width) - fieldRect.left) / fieldRect.width * 100,
        y: ((goalKeeperRect.top + goalKeeperRect.height) - fieldRect.top) / fieldRect.height * 100
    };

    ball.style.left = `${ballStartPosition.x}%`;
    ball.style.top = `${ballStartPosition.y}%`;
    ball.style.opacity = '1';
    ball.style.zIndex = '1000';

    ball.animate([
        {
            offset: 0,
            top: `${ballStartPosition.y}%`,
            left: `${ballStartPosition.x}%`,
            transform: 'translate(-50%, -50%) rotate(0deg)'
        },
        {
            offset: 1,
            top: `${ballEndPosition.y - 5}%`,
            left: `${ballEndPosition.x - 5}%`,
            transform: 'translate(-50%, -50%) rotate(360deg)'
        }
    ], {
        duration: 2000,
        fill: 'forwards'
    }).onfinish = () => {
        setTimeout(() => {
            document.getElementById("ACTION_INFO").style.display = "none";
            playerImage.style.display = "none";
            goalKeeperImage.style.display = "none";
            ball.style.opacity = '0';
        }, 2500);
    };
}

function throwReboundBallReturn(){

}

function throwReboundBallLost(){

}

function checkGoal() {
    const ball = document.getElementById('ball');
    const goalTop = document.getElementById('goalTop');
    const goalBottom = document.getElementById('goalBottom');

    function getBounds(element) {
        if (element) {
            const rect = element.getBoundingClientRect();
            return {
                left: rect.left + window.scrollX,
                top: rect.top + window.scrollY,
                right: rect.right + window.scrollX,
                bottom: rect.bottom + window.scrollY
            };
        }
        return null;
    }

    function ballInGoal() {
        const ballBounds = getBounds(ball);
        const goalTopBounds = getBounds(goalTop);
        const goalBottomBounds = getBounds(goalBottom);

        if (ballBounds && goalTopBounds && ballBounds.right > goalTopBounds.left && ballBounds.left < goalTopBounds.right &&
            ballBounds.bottom > goalTopBounds.top && ballBounds.top < goalTopBounds.bottom) {
            console.log('Ball in top goal!');

        } else if (ballBounds && goalBottomBounds && ballBounds.right > goalBottomBounds.left && ballBounds.left < goalBottomBounds.right &&
            ballBounds.bottom > goalBottomBounds.top && ballBounds.top < goalBottomBounds.bottom) {
            console.log('Ball in bottom goal!');
        }

        requestAnimationFrame(ballInGoal);
    }

    if (ball && goalTop && goalBottom) {
        requestAnimationFrame(ballInGoal);
    } else {
        console.error('One or more elements could not be found');
    }
}

function toggle() {
    if(actionType === "GOAL") {
        actionType = "CAUGHT";
        document.getElementById("toggleNow").innerText = actionType
    } else if (actionType ==="CAUGHT") {
        actionType = "MISSED";
        document.getElementById("toggleNow").innerText = actionType
    } else {
        actionType = "GOAL";
        document.getElementById("toggleNow").innerText = actionType
    }
}

document.addEventListener('DOMContentLoaded', checkGoal);