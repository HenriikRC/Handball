let throwingPlayerPos = "AWAY_RIGHT_WING";
let throwingPlayerName = "Henrik Christensen";
let throwingPlayerJerseyNumber = "18";
let throwingPlayerImageLink = "http://localhost:8080/assets/henne.jpg";
let throwingPlayerTeamImageLink = "http://localhost:8080/assets/kif-logo.png";
let goalKeeperPos = 'HOME_KEEPER';
let goalKeeperName = "Magnus Brandbyge";
let goalKeeperJerseyNumber = "1";
let goalKeeperImageLink = "http://localhost:8080/assets/Henrik.jpg";
let goalKeeperTeamImageLink = "http://localhost:8080/assets/fhk-logo.png";
let assistingPlayerName = null;
let assistingPlayerJerseyNumber = "100";
let assistingPlayerImageLink = "http://localhost:8080/assets/henne.jpg";
let assistingPlayerTeamImageLink = "http://localhost:8080/assets/kif-logo.png";
let opponentPlayerPos = "HOME_LEFT_WING";
let opponentPlayerName = "Lasse Hvilsted";
let opponentPlayerJerseyNumber = "50";
let opponentPlayerImageLink = "http://localhost:8080/assets/Henrik.jpg";
let opponentPlayerTeamImageLink = "http://localhost:8080/assets/fhk-logo.png";
let matchTime = "33:12";
let actionType = "CAUGHT";
const missedSpotsTop = [
    { top: '5%', left: '10%' },
    { top: '5%', left: '90%' }
];

const url = window.location.href;
const matchId = url.split('/').pop();
console.log("Match ID = " + matchId);

function updatePlayerInfo(playerIdPrefix, playerName, playerNumber, playerImageLink, teamImageLink) {
    const playerTeamImage = document.getElementById(`${playerIdPrefix}_TEAM_IMAGE`);
    const playerNumberEl = document.getElementById(`${playerIdPrefix}_NUMBER`);
    const playerNameEl = document.getElementById(`${playerIdPrefix}_NAME`);

    playerTeamImage.src = teamImageLink;
    playerNumberEl.innerText = playerNumber ? `#${playerNumber}` : '';
    playerNameEl.innerText = playerName;
}

function displayActionInfo() {
    updatePlayerInfo('THROWING_PLAYER', throwingPlayerName, throwingPlayerJerseyNumber, throwingPlayerImageLink, throwingPlayerTeamImageLink);

    const assistingPlayerDiv = document.getElementById("ASSISTING_PLAYER");
    if (assistingPlayerName != null) {
        updatePlayerInfo('ASSISTING_PLAYER', assistingPlayerName, assistingPlayerJerseyNumber, assistingPlayerImageLink, assistingPlayerTeamImageLink);
        assistingPlayerDiv.style.display = "flex";
    } else {
        assistingPlayerDiv.style.display = "none";
    }

    updatePlayerInfo('GOALKEEPER', goalKeeperName, goalKeeperJerseyNumber, goalKeeperImageLink, goalKeeperTeamImageLink);

    document.getElementById("MATCH_TIME").innerText = matchTime;
    document.getElementById("THROWING_PLAYER_ACTION").innerText = actionType;
    document.getElementById("ACTION_INFO").style.display = "flex";
}


function throwBall() {
    displayActionInfo();

    const playerImage = document.getElementById((throwingPlayerPos+"_IMAGE"));
    playerImage.src = throwingPlayerImageLink;
    playerImage.style.display = "unset";
    const goalKeeperImage = document.getElementById((goalKeeperPos+"_IMAGE"));
    goalKeeperImage.src = goalKeeperImageLink;
    goalKeeperImage.style.display = "unset";

    const playerElement = document.getElementById(throwingPlayerPos);
    if (!playerElement) {
        console.error('Player element not found:', throwingPlayerPos);
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

    const playerImage = document.getElementById((throwingPlayerPos+"_IMAGE"));
    playerImage.src = throwingPlayerImageLink;
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

    const playerImage = document.getElementById((throwingPlayerPos + "_IMAGE"));
    const goalKeeperImage = document.getElementById((goalKeeperPos + "_IMAGE"));

    playerImage.src = throwingPlayerImageLink;
    goalKeeperImage.src = goalKeeperImageLink;

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