document.addEventListener('DOMContentLoaded', function() {
    fetchMatches();
});

function fetchMatches() {
    fetch('http://localhost:8080/api/v1/matches')
        .then(response => response.json())
        .then(data => {
            const table = document.querySelector('.responsive-table');
            console.log(data);
            data.forEach(match => {
                const row = document.createElement('li');
                row.classList.add('table-row');

                const dateCol = createColumn('col col-1', new Date(match.matchTime).toLocaleString());
                const homeTeamCol = createColumn('col col-2', match.homeTeamName);
                const resultCol = createColumn('col col-3', `${match.homeTeamGoals} - ${match.awayTeamGoals}`);
                const awayTeamCol = createColumn('col col-4', match.awayTeamName);
                const viewMoreCol = createColumn('col col-5', 'Se mere');

                viewMoreCol.addEventListener('click', () => window.location.href = `http://localhost:8080/live/match/${match.id}`);


                row.appendChild(dateCol);
                row.appendChild(homeTeamCol);
                row.appendChild(resultCol);
                row.appendChild(awayTeamCol);
                row.appendChild(viewMoreCol);

                table.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching match data:', error);
        });
}

function createColumn(colClass, text) {
    const col = document.createElement('div');
    col.className = colClass;
    col.textContent = text;
    return col;
}