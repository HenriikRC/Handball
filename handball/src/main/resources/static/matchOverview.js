document.addEventListener('DOMContentLoaded', function() {
    fetchMatches();
});

function fetchMatches() {
    fetch('http://localhost:8080/api/v1/match') // Replace with your actual API URL
        .then(response => response.json())
        .then(data => {
            const table = document.querySelector('.responsive-table');
            data.forEach(match => {
                const row = document.createElement('li');
                row.classList.add('table-row');

                const dateCol = createColumn('col col-1', match.date);
                const homeTeamCol = createColumn('col col-2', match.homeTeam);
                const resultCol = createColumn('col col-3', `${match.homeScore} - ${match.awayScore}`);
                const awayTeamCol = createColumn('col col-4', match.awayTeam);
                const viewMoreCol = createColumn('col col-5', 'Se mere');
                // Assume viewMoreCol might link to a detailed view of the match
                // You can add an event listener or set it as a link

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