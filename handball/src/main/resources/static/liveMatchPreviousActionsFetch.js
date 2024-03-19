document.addEventListener('DOMContentLoaded', function() {
    const url = window.location.href;
    const matchId = url.split('/').pop();
    console.log("Match ID = " + matchId);
    fetchMatchActions(matchId);
});

function fetchMatchActions(matchId) {
    fetch(`/api/v1/matches/${matchId}`)
        .then(response => response.json())
        .then(match => {
            const homeTeamName = match.homeTeamName;
            const awayTeamName = match.awayTeamName;

            fetch(`/api/v1/matchactions/${matchId}`)
                .then(response => response.json())
                .then(matchActions => {
                    const matchActionsContainer = document.getElementById('matchActionsContainer');

                    if (matchActions.length > 0) {
                        const html = '<ul>' +
                            matchActions.map(action => {
                                let cssClass = 'centerContent';
                                if (action.team) {
                                    cssClass = action.team.name === homeTeamName ? 'leftMarker' : 'rightMarker';
                                }

                                let playerInfo = '';
                                if (action.player) {
                                    playerInfo = '<div class="matchEventLine">' +
                                        '<span><span>' + (action.team ? action.team.shortName : '') + '</span>Player: <span>' + action.player.name + '</span></span><br>' +
                                        '</div>';
                                }

                                let goalKeeperInfo = '';
                                if (action.goalKeeper) {
                                    goalKeeperInfo = '<div class="matchEventLine">' +
                                        '<span>Keeper: <span>' + action.goalKeeper.name + '</span></span><br>' +
                                        '</div>';
                                }

                                return '<li class="matchEvent ' + cssClass + '">' +
                                    '<div class="matchEventLine">' +
                                    '<span class="matchEventLine action">' + action.matchActionType + '</span>' +
                                    '</div>' +
                                    playerInfo +
                                    goalKeeperInfo +
                                    '</li>';
                            }).join('') +
                            '</ul>';

                        matchActionsContainer.innerHTML = html;
                    } else {
                        matchActionsContainer.innerHTML = '<p>No match actions available for this match</p>';
                    }
                })
                .catch(error => console.error('Error fetching match actions:', error));
        })
        .catch(error => console.error('Error fetching match details:', error));
}
