let positionsSwapped = false;
const originalPositions = {
    HOME_KEEPER: { margin: '0 50%', top: '0%', transform: 'translate(-50%, 10%)' },
    HOME_RIGHT_WING: { margin: '0 17.5%', top: '8%', transform: 'translate(-50%, -50%)' },
    HOME_RIGHT_BACK: { margin: '0 26%', top: '19%', transform: 'translate(-50%, -50%)' },
    HOME_CENTER_3: { margin: '0 40%', top: '23%', transform: 'translate(-50%, -50%)' },
    HOME_CENTER_4: { margin: '0 60%', top: '23%', transform: 'translate(-50%, -50%)' },
    HOME_LEFT_BACK: { margin: '0 74%', top: '19%', transform: 'translate(-50%, -50%)' },
    HOME_LEFT_WING: { margin: '0 82.5%', top: '8%', transform: 'translate(-50%, -50%)' },
    AWAY_KEEPER: { margin: '0 50%', top: '100%', transform: 'translate(-50%, -110%)' },
    AWAY_RIGHT_WING: { margin: '0 95%', top: '4%', transform: 'translate(-50%, -50%)' },
    AWAY_RIGHT_BACK: { margin: '0 85%', top: '31.5%', transform: 'translate(-50%, -50%)' },
    AWAY_CENTER: { margin: '0 50%', top: '23%', transform: 'translate(-50%, -50%)' },
    AWAY_PLAYMAKER: { margin: '0 50%', top: '33%', transform: 'translate(-50%, -50%)' },
    AWAY_LEFT_BACK: { margin: '0 15%', top: '31.5%', transform: 'translate(-50%, -50%)' },
    AWAY_LEFT_WING: { margin: '0 5%', top: '4%', transform: 'translate(-50%, -50%)' }
};

const swappedPositions = {
    HOME_KEEPER: { margin: '0 50%', top: '0%', transform: 'translate(-50%, 10%)' },
    HOME_RIGHT_WING: { margin: '0 5%', top: '96%', transform: 'translate(-50%, -50%)' },
    HOME_RIGHT_BACK: { margin: '0 15%', top: '72%', transform: 'translate(-50%, -50%)' },
    HOME_CENTER: { margin: '0 50%', top: '77%', transform: 'translate(-50%, -50%)' },
    HOME_PLAYMAKER: { margin: '0 50%', top: '67%', transform: 'translate(-50%, -50%)' },
    HOME_LEFT_BACK: { margin: '0 85%', top: '72%', transform: 'translate(-50%, -50%)' },
    HOME_LEFT_WING: { margin: '0 95%', top: '96%', transform: 'translate(-50%, -50%)' },
    AWAY_KEEPER: { margin: '0 50%', top: '100%', transform: 'translate(-50%, -110%)' },
    AWAY_RIGHT_WING: { margin: '0 17.5%', top: '92%', transform: 'translate(-50%, -50%)' },
    AWAY_RIGHT_BACK: { margin: '0 26%', top: '81%', transform: 'translate(-50%, -50%)' },
    AWAY_CENTER_3: { margin: '0 40%', top: '77%', transform: 'translate(-50%, -50%)' },
    AWAY_CENTER_4: { margin: '0 60%', top: '77%', transform: 'translate(-50%, -50%)' },
    AWAY_LEFT_BACK: { margin: '0 74%', top: '81%', transform: 'translate(-50%, -50%)' },
    AWAY_LEFT_WING: { margin: '0 82.5%', top: '92%', transform: 'translate(-50%, -50%)' }
};
document.addEventListener('DOMContentLoaded', function() {
    checkGoal();
});

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
    assistingPlayerImageLink = actionData.assistingPlayerTeamImageLink || null;

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

    if (playerTeamImage) playerTeamImage.src = teamImageLink;
    if (playerImage) playerImage.src = playerImageLink;
    if (playerNumberEl) playerNumberEl.innerText = playerNumber ? `#${playerNumber}` : '';
    if (playerNameEl) playerNameEl.innerText = playerName;
}

function displayActionInfo() {
    updatePlayerInfo('THROWING_PLAYER', playerName, playerJerseyNumber, playerImageLink , playerTeamImageLink);

    const assistingPlayerDiv = document.getElementById("ASSISTING_PLAYER");
    if (assistingPlayerName != null) {
        updatePlayerInfo('ASSISTING_PLAYER', assistingPlayerName, assistingPlayerJerseyNumber, assistingPlayerImageLink , playerTeamImageLink);
        if (assistingPlayerDiv) assistingPlayerDiv.style.display = "flex";
    } else {
        if (assistingPlayerDiv) assistingPlayerDiv.style.display = "none";
    }

    const goalKeeperDiv = document.getElementById("GOALKEEPER");
    if(goalKeeperName!==null){
        updatePlayerInfo('GOALKEEPER', goalKeeperName, goalKeeperJerseyNumber, goalKeeperImageLink, opponentTeamImageLink);
        goalKeeperDiv.style.display = "flex";
    } else {
        goalKeeperDiv.style.display = "none";
    }


    const matchTimeEl = document.getElementById("MATCH_TIME");
    const actionEl = document.getElementById("THROWING_PLAYER_ACTION");
    const actionInfoEl = document.getElementById("ACTION_INFO");

    if (matchTimeEl) matchTimeEl.innerText = matchTime;
    if (actionEl) actionEl.innerText = actionType;
    if (actionInfoEl) actionInfoEl.style.display = "flex";
}




function throwBall() {
    displayActionInfo();
    let positionString = "";
    let opponentPositionString = "";
    if (playerTeamName === homeTeamName) {
        positionString += "HOME_";
        opponentPositionString += "AWAY_";
    } else {
        positionString += "AWAY_";
        opponentPositionString += "HOME_";
    }

    const playerImage = document.getElementById(`${positionString}${playerPosition}_IMAGE`);
    if (playerImage) {
        playerImage.src = playerImageLink;
        playerImage.style.display = "block";
    }
    const goalKeeperImage = document.getElementById(`${opponentPositionString}${goalKeeperPosition}_IMAGE`);
    const goalKeeperElement = document.getElementById(`${opponentPositionString}KEEPER`);

    if (goalKeeperName !== null) {
        if (goalKeeperImage) {
            goalKeeperImage.src = goalKeeperImageLink;
            goalKeeperImage.style.display = "unset";
        }
    } else {
        if (goalKeeperElement) {
            goalKeeperElement.style.display = "none";
        }
    }

    const playerElement = document.getElementById(`${positionString}${playerPosition}`);
    if (!playerElement) {
        console.error('Player element not found:', `${positionString}${playerPosition}`);
        return;
    }

    const assistingPlayerImage = document.getElementById(`${positionString}${assistingPlayerPosition}_IMAGE`);
    if(assistingPlayerImage){
        assistingPlayerImage.src = assistingPlayerImageLink;
        assistingPlayerImage.style.display = "unset";
    }

    const fieldRect = document.querySelector('.field').getBoundingClientRect();
    const playerRect = playerElement.getBoundingClientRect();

    const playerXRelativeToField = ((playerRect.left + playerRect.right) / 2) - fieldRect.left;
    const playerYRelativeToField = ((playerRect.top + playerRect.bottom) / 2) - fieldRect.top;

    const playerXPercentage = `${(playerXRelativeToField / fieldRect.width) * 100}%`;
    const playerYPercentage = `${(playerYRelativeToField / fieldRect.height) * 100}%`;

    const ball = document.getElementById('ball');

    if (assistingPlayerName) {
        const assistingPlayerElement = document.getElementById(`${positionString}${assistingPlayerPosition}`);
        if (!assistingPlayerElement) {
            console.error('Assisting player element not found:', `${positionString}${assistingPlayerPosition}`);
            return;
        }

        const assistingPlayerRect = assistingPlayerElement.getBoundingClientRect();
        const assistingPlayerXRelativeToField = ((assistingPlayerRect.left + assistingPlayerRect.right) / 2) - fieldRect.left;
        const assistingPlayerYRelativeToField = ((assistingPlayerRect.top + assistingPlayerRect.bottom) / 2) - fieldRect.top;

        const assistingPlayerXPercentage = `${(assistingPlayerXRelativeToField / fieldRect.width) * 100}%`;
        const assistingPlayerYPercentage = `${(assistingPlayerYRelativeToField / fieldRect.height) * 100}%`;

        if (ball) {
            ball.style.position = 'absolute';
            ball.style.opacity = '1';
            ball.style.top = assistingPlayerYPercentage;
            ball.style.left = assistingPlayerXPercentage;
            ball.style.transform = 'translate(-50%, -50%)';

            const keyframesToPlayer = [
                { top: assistingPlayerYPercentage, left: assistingPlayerXPercentage, transform: 'rotate(0deg)' },
                { top: playerYPercentage, left: playerXPercentage, transform: 'rotate(360deg)' }
            ];
            let finalBallPosition = '';
            if(!positionsSwapped){
                finalBallPosition = '-6.4vh';
            } else {
                finalBallPosition = '84.4vh';
            }
            ball.animate(keyframesToPlayer, {
                duration: 1000,
                fill: 'forwards'
            }).onfinish = () => {
                const keyframesToGoal = [
                    { top: playerYPercentage, left: playerXPercentage, transform: 'rotate(0deg)' },
                    { top: finalBallPosition, left: '50%', transform: 'rotate(1060deg)' }
                ];

                ball.animate(keyframesToGoal, {
                    duration: 2000,
                    fill: 'forwards'
                });

                setTimeout(() => {
                    ball.style.opacity = '0';
                    const actionInfoEl = document.getElementById("ACTION_INFO");
                    if (actionInfoEl) actionInfoEl.style.display = "none";
                    if (playerImage) playerImage.style.display = "none";
                    if (goalKeeperImage) goalKeeperImage.style.display = "none";
                    if (assistingPlayerImage) assistingPlayerImage.style.display = "none";
                    if (goalKeeperElement) goalKeeperElement.style.display = "block";
                    swapPlayerPositions();
                }, 4500);
            };
        }
    } else {
        if (ball) {
            ball.style.position = 'absolute';
            ball.style.opacity = '1';
            ball.style.top = playerYPercentage;
            ball.style.left = playerXPercentage;
            ball.style.transform = 'translate(-50%, -50%)';

            let finalBallPosition = '';
            if(!positionsSwapped){
                finalBallPosition = '-6.4vh';
            } else {
                finalBallPosition = '84.4vh';
            }
            const keyframes = [
                { top: playerYPercentage, left: playerXPercentage, transform: 'rotate(0deg)' },
                { top: finalBallPosition, left: '50%', transform: 'rotate(1060deg)' }
            ];

            ball.animate(keyframes, {
                duration: 2000,
                fill: 'forwards'
            });

            setTimeout(() => {
                ball.style.opacity = '0';
                const actionInfoEl = document.getElementById("ACTION_INFO");
                if (actionInfoEl) actionInfoEl.style.display = "none";
                if (playerImage) playerImage.style.display = "none";
                if (goalKeeperImage) goalKeeperImage.style.display = "none";
                if (assistingPlayerImage) assistingPlayerImage.style.display = "none";
                if (goalKeeperElement) goalKeeperElement.style.display = "block";
                swapPlayerPositions();
            }, 4500);
        }
    }
}



function swapPlayerPositions() {
    const homePlayers = document.querySelectorAll('.team1');
    const awayPlayers = document.querySelectorAll('.team2');

    homePlayers.forEach(player => {
        const playerId = player.getAttribute('id');
        let newPlayerId = playerId;
        if (positionsSwapped) {
            newPlayerId = playerId === 'HOME_CENTER' ? 'HOME_CENTER_3' : playerId === 'HOME_PLAYMAKER' ? 'HOME_CENTER_4' : playerId;
            player.style.margin = originalPositions[newPlayerId].margin;
            player.style.top = originalPositions[newPlayerId].top;
            player.style.transform = originalPositions[newPlayerId].transform;
        } else {
            newPlayerId = playerId === 'HOME_CENTER_3' ? 'HOME_CENTER' : playerId === 'HOME_CENTER_4' ? 'HOME_PLAYMAKER' : playerId;
            player.style.margin = swappedPositions[newPlayerId].margin;
            player.style.top = swappedPositions[newPlayerId].top;
            player.style.transform = swappedPositions[newPlayerId].transform;
        }
        player.setAttribute('id', newPlayerId);
        player.setAttribute('data-position', newPlayerId);
    });

    awayPlayers.forEach(player => {
        const playerId = player.getAttribute('id');
        let newPlayerId = playerId;
        if (positionsSwapped) {
            newPlayerId = playerId === 'AWAY_CENTER_3' ? 'AWAY_CENTER' : playerId === 'AWAY_CENTER_4' ? 'AWAY_PLAYMAKER' : playerId;
            player.style.margin = originalPositions[newPlayerId].margin;
            player.style.top = originalPositions[newPlayerId].top;
            player.style.transform = originalPositions[newPlayerId].transform;
        } else {
            newPlayerId = playerId === 'AWAY_CENTER' ? 'AWAY_CENTER_3' : playerId === 'AWAY_PLAYMAKER' ? 'AWAY_CENTER_4' : playerId;
            player.style.margin = swappedPositions[newPlayerId].margin;
            player.style.top = swappedPositions[newPlayerId].top;
            player.style.transform = swappedPositions[newPlayerId].transform;
        }
        player.setAttribute('id', newPlayerId);
        player.setAttribute('data-position', newPlayerId);
    });

    positionsSwapped = !positionsSwapped;
    console.log('Positions swapped:', positionsSwapped);
}

function action() {
    switch (actionType) {
        case "GOAL":
            throwBall();
            break;
        case "SAVED":
            throwCaughtBall();
            break;
        case "MISSED":
            throwMissedBall();
            break;
    }
}

function throwMissedBall() {
    displayActionInfo();
    let positionString = "";
    let opponentPositionString = "";
    if (playerTeamName === homeTeamName) {
        positionString += "HOME_";
        opponentPositionString += "AWAY_";
    } else {
        positionString += "AWAY_";
        opponentPositionString += "HOME_";
    }

    const playerImage = document.getElementById(`${positionString}${playerPosition}_IMAGE`);
    if (playerImage) {
        playerImage.src = playerImageLink;
        playerImage.style.display = "unset";
    }

    const goalKeeperImage = document.getElementById(`${opponentPositionString}${goalKeeperPosition}_IMAGE`);
    if (goalKeeperImage) {
        goalKeeperImage.src = goalKeeperImageLink;
        goalKeeperImage.style.display = "unset";
    }

    const playerElement = document.getElementById(`${positionString}${playerPosition}`);
    if (!playerElement) {
        console.error('Player element not found:', `${positionString}${playerPosition}`);
        return;
    }

    const assistingPlayerImage = document.getElementById(`${positionString}${assistingPlayerPosition}_IMAGE`);
    if(assistingPlayerImage){
        assistingPlayerImage.src = assistingPlayerImageLink;
        assistingPlayerImage.style.display = "unset";
    }

    if (playerImage) {

        const ball = document.getElementById('ball');
        if (ball) {
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
                    const actionInfoEl = document.getElementById("ACTION_INFO");
                    if (actionInfoEl) actionInfoEl.style.display = "none";
                    ball.style.opacity = '0';
                    playerImage.style.display = "none";
                    swapPlayerPositions();
                }, 4500);
            };
        }
    }
}

function throwCaughtBall() {
    displayActionInfo();
    let positionString = "";
    let opponentPositionString = "";
    if (playerTeamName === homeTeamName) {
        positionString += "HOME_";
        opponentPositionString += "AWAY_";
    } else {
        positionString += "AWAY_";
        opponentPositionString += "HOME_";
    }

    const playerImage = document.getElementById(`${positionString}${playerPosition}_IMAGE`);
    if (!playerImage) {
        console.error(`Player image not found for position: ${positionString}${playerPosition}_IMAGE`);
        return;
    }

    playerImage.src = playerImageLink || "http://localhost:8080/assets/images/player/player.png";
    playerImage.style.display = "block";

    const goalKeeperImage = document.getElementById(`${opponentPositionString}${goalKeeperPosition}_IMAGE`);
    if (!goalKeeperImage) {
        console.error(`Goalkeeper image not found for position: ${opponentPositionString}${goalKeeperPosition}_IMAGE`);
        return;
    }

    goalKeeperImage.src = goalKeeperImageLink || "http://localhost:8080/assets/images/player/player.png";
    goalKeeperImage.style.display = "block";

    const ball = document.getElementById('ball');
    if (!ball) {
        console.error('Ball element not found');
        return;
    }

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
            const actionInfoEl = document.getElementById("ACTION_INFO");
            if (actionInfoEl) actionInfoEl.style.display = "none";
            if (playerImage) playerImage.style.display = "none";
            if (goalKeeperImage) goalKeeperImage.style.display = "none";
            ball.style.opacity = '0';
            swapPlayerPositions();
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

        } else if (ballBounds && goalBottomBounds && ballBounds.right > goalBottomBounds.left && ballBounds.left < goalBottomBounds.right &&
            ballBounds.bottom > goalBottomBounds.top && ballBounds.top < goalBottomBounds.bottom) {

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
