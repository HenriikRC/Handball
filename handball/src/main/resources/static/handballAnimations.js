const throwingPlayerPos = "AWAY_RIGHT_WING";
const throwingPlayerName = "Henrik Christensen";
const throwingPlayerJerseyNumber = "18";
const throwingPlayerImageLink = "http://localhost:8080/assets/henne.jpg";
const throwingPlayerTeamImageLink = "http://localhost:8080/assets/kif-logo.png";
const goalKeeperPos = 'HOME_KEEPER';
const goalKeeperName = "Målmanden";
const goalKeeperImageLink = "http://localhost:8080/assets/Henrik.jpg";
const goalKeeperTeamImageLink = "http://localhost:8080/assets/fhk-logo.png";
const opponentPlayerPos = 'HOME_KEEPER';
const opponentPlayerName = "Modstanderen";
const opponentPlayerImageLink = "/assets/henne.jpg";
const assistingPlayerPos = null;
const assistingPlayerName = "Medspilleren";
const assistingPlayerImageLink = "http://localhost:8080/assets/henne.jpg";
const matchTime = "33:12";
const actionType = "GOAL";

// DOES NOT CHANGE
const ball = document.getElementById('ball');
const field = document.querySelector('.field');
function action(){
    switch (actionType) {
        case "GOAL": throwBall();
    }
}

function throwBall() {
    const player = document.getElementById(throwingPlayerPos);
    const playerImage = document.getElementById((throwingPlayerPos+"_IMAGE"));
    playerImage.src = throwingPlayerImageLink;
    playerImage.style.display = "unset";

    const throwingPlayerTeamImage = document.getElementById("THROWING_PLAYER_TEAM_IMAGE");
    throwingPlayerTeamImage.src = throwingPlayerTeamImageLink;

    const throwingPlayerNumber = document.getElementById("THROWING_PLAYER_NUMBER");
    throwingPlayerNumber.innerText = " #" + throwingPlayerJerseyNumber + " ";

    const throwingPlayerNameP = document.getElementById("THROWING_PLAYER_NAME")
    throwingPlayerNameP.innerText = throwingPlayerName;

    const throwingPlayerGoal = document.getElementById("THROWING_PLAYER_ACTION");
    throwingPlayerGoal.innerText = actionType;

    const matchTimeP = document.getElementById("MATCH_TIME");
    matchTimeP.innerText = matchTime;

    const actionInfo = document.getElementById("ACTION_INFO")
    actionInfo.style.display = "unset";

    const goalKeeperImage = document.getElementById((goalKeeperPos+"_IMAGE"));
    goalKeeperImage.src = goalKeeperImageLink;
    goalKeeperImage.style.display = "unset";

    if (!player || !field) {
        console.error("Throwing player or field not found:", throwingPlayerPos);
        return;
    }

    const playerRect = player.getBoundingClientRect();
    const fieldRect = field.getBoundingClientRect();

    const playerXRelativeToField = ((playerRect.left + playerRect.right) / 2) - fieldRect.left;
    const playerYRelativeToField = ((playerRect.top + playerRect.bottom) / 2) - fieldRect.top;

    const playerXPercentage = `${(playerXRelativeToField / fieldRect.width) * 100}%`;
    const playerYPercentage = `${(playerYRelativeToField / fieldRect.height) * 100}%`;

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
        ball.setAttribute("style", "top: 50% !important");
        ball.style.left = '50%';
        ball.style.transform = 'translate(-50%, -50%)';
        ball.style.opacity = '0';
        playerImage.style.display = "none";
        goalKeeperImage.style.display = "none";
        actionInfo.style.display = "none";
    }, 4500);
}

function throwMissedBall(){
    const player = document.getElementById(throwingPlayerPos);

}

function throwCaughtBall(){

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

document.addEventListener('DOMContentLoaded', checkGoal);