document.addEventListener('DOMContentLoaded', function() {
    const url = window.location.href;
    const matchId = url.split('/').pop();
    console.log("Match ID = " + matchId);

    fetchInitialMatchActions(matchId);
    setupSSE(matchId);
});

function fetchInitialMatchActions(matchId) {
    fetch(`/api/v1/matches/${matchId}`)
        .then(response => response.json())
        .then(match => {
            homeTeamName = match.homeTeamName;
            awayTeamName = match.awayTeamName;

            fetch(`/api/v1/matchactions/${matchId}`)
                .then(response => response.json())
                .then(matchActions => {
                    const matchActionsContainer = document.getElementById('matchActionsContainer');
                    matchActions.forEach(action => {
                        const actionElement = createMatchActionElement(action, homeTeamName, awayTeamName);
                        matchActionsContainer.insertBefore(actionElement, matchActionsContainer.firstChild);
                        document.querySelectorAll('.matchEvent').forEach(function(element) {
                            element.style.listStyleType = 'none';
                        });
                    });
                })
                .catch(error => console.error('Error fetching match actions:', error));
        })
        .catch(error => console.error('Error fetching match details:', error));
}

function setupSSE(matchId) {
    const eventSource = new EventSource(`/api/v1/live/matchactions/${matchId}/stream`);

    eventSource.addEventListener('message', function(event) {
        const matchAction = JSON.parse(event.data);
        updateMatchActions(matchAction, homeTeamName, awayTeamName);
        const actionData = {
            teamId: matchAction.team?.id,
            teamName: matchAction.team?.name,
            teamShortName: matchAction.team?.shortName,
            teamImageLink: matchAction.team?.shortName ? "http://localhost:8080/assets/images/team/" + matchAction.team.shortName + ".png" : null,

            playerName: matchAction.player?.name,
            playerPosition: matchAction.matchActionPositionType ? matchAction.matchActionPositionType : null,
            playerJerseyNumber: matchAction.player?.playerNumber,
            playerImageLink: matchAction.player?.playerImageLink,

            assistingPlayerName: matchAction.assistingPlayer?.name,
            assistingPlayerJerseyNumber: matchAction.assistingPlayer?.playerNumber,
            assistingPlayerPosition: matchAction.assistingPlayer?.positionType,
            assistingPlayerTeamImageLink: matchAction.assistingPlayer?.playerImageLink,

            opponentTeamid: matchAction.opponentTeam?.id,
            opponentTeamName: matchAction.opponentTeam?.name,
            opponentTeamShortName: matchAction.opponentTeam?.shortName,
            opponentTeamImageLink: matchAction.opponentTeam?.shortName ? "http://localhost:8080/assets/images/team/" + matchAction.opponentTeam.shortName + ".png" : null,

            goalKeeperName: matchAction.goalKeeper?.name,
            goalKeeperPosition: matchAction.goalKeeper?.positionType,
            goalKeeperJerseyNumber: matchAction.goalKeeper?.playerNumber,
            goalKeeperImageLink: matchAction.goalKeeper?.playerImageLink,

            opponentPlayerName: matchAction.opponentPlayer?.name,
            opponentPlayerPos: matchAction.opponentPlayer?.positionType,
            opponentPlayerJerseyNumber: matchAction.opponentPlayer?.playerNumber,
            opponentPlayerImageLink: matchAction.opponentPlayer?.playerImageLink,

            matchTime: matchAction.matchTime,
            actionType: matchAction.matchActionType
        };
        updateActionData(actionData);
    });
}


function createMatchActionElement(action, homeTeamName, awayTeamName) {
    let cssClass = 'centerContent';
    if (action.team) {
        cssClass = action.team.name === homeTeamName ? 'leftMarker' : 'rightMarker';
    }

    let playerInfo = '';
    if (action.player) {
        playerInfo = `<div class="matchEventLine">
                        <span><span>${action.team ? action.team.shortName : ''}</span> Player: <span>${action.player.name}</span></span><br>
                      </div>`;
    }

    let goalKeeperInfo = '';
    if (action.goalKeeper) {
        goalKeeperInfo = `<div class="matchEventLine">
                            <span>Keeper: <span>${action.goalKeeper.name}</span></span><br>
                          </div>`;
    }

    const listItem = document.createElement('li');
    listItem.className = `matchEvent ${cssClass}`;
    listItem.innerHTML = `
        <div class="matchEventLine">
            <span class="matchEventLine action">${action.matchActionType}</span> <span class="matchEventTime">${action.matchTime}</span>
        </div>
        ${playerInfo}
        ${goalKeeperInfo}
    `;

    return listItem;
}


function updateMatchActions(newMatchAction, homeTeamName, awayTeamName) {
    const matchActionsContainer = document.getElementById('matchActionsContainer');
    const actionElement = createMatchActionElement(newMatchAction, homeTeamName, awayTeamName);
    matchActionsContainer.insertBefore(actionElement, matchActionsContainer.firstChild);
    document.querySelectorAll('.matchEvent').forEach(function(element) {
        element.style.listStyleType = 'none';
    });
}
