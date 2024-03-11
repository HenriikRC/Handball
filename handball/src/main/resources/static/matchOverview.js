function fetchMatches() {
    return fetch("http://localhost:8080/api/v1/match")
        .then(response => response.json());
}


async function loadMatches(){
    const matches = fetchMatches();
    for (const match of matches) {
        var row = document.createElement('tr');
    }
}