document.addEventListener('DOMContentLoaded', function() {
    // Fetch initial match actions data
    const url = window.location.href;
    const matchId = url.split('/').pop();
    console.log("Match ID = " + matchId);
    fetchMatchActions(matchId);

    // Establish SSE connection
    const eventSource = new EventSource(`/api/v1/live/matchactions/${matchId}/stream`);

    // Event listener for receiving match actions
    eventSource.addEventListener('matchAction', function(event) {
        const newMatchAction = JSON.parse(event.data);
        console.log('New match action received:', newMatchAction);

        updateMatchActions(newMatchAction);
    });
});

function fetchMatchActions(matchId) {
}

function updateMatchActions(newMatchAction) {
    const matchActionsContainer = document.getElementById('matchActionsContainer');
    const cssClass = newMatchAction.team ? (newMatchAction.team.name === 'homeTeam' ? 'leftMarker' : 'rightMarker') : 'centerContent';
    const playerInfo = newMatchAction.player ? `<div class="matchEventLine">
                            <span><span>${newMatchAction.team ? newMatchAction.team.shortName : ''}</span>Player: <span>${newMatchAction.player.name}</span></span><br>
                            </div>` : '';
    const goalKeeperInfo = newMatchAction.goalKeeper ? `<div class="matchEventLine">
                            <span>Keeper: <span>${newMatchAction.goalKeeper.name}</span></span><br>
                            </div>` : '';
    const html = `<li class="matchEvent ${cssClass}">
                    <div class="matchEventLine">
                    <span class="matchEventLine action">${newMatchAction.matchActionType}</span>
                    </div>
                    ${playerInfo}
                    ${goalKeeperInfo}
                </li>`;
    matchActionsContainer.innerHTML += html;
}