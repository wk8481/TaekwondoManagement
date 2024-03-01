const searchButton = document.getElementById("searchButton");
const searchResultsSection = document.getElementById("searchResultsSection");
const searchTermInput = document.getElementById("searchTerm");
const tableBody = document.getElementsByTagName("tbody")[0];

searchButton.addEventListener("click", async () => {
    const searchTerm = searchTermInput.value.trim();
    if (!searchTerm) {
        alert("Please enter a search term.");
        return;
    }

    const response = await fetch(`/api/techniques/search?term=${searchTerm}`);
    if (response.ok) {
        const techniques = await response.json();
        displaySearchResults(techniques);
    } else {
        alert("An error occurred while searching for techniques.");
    }
});

function displaySearchResults(techniques) {
    tableBody.innerHTML = '';
    if (techniques.length === 0) {
        searchResultsSection.style.display = "none";
        return;
    }

    techniques.forEach(technique => {
        const row = `
            <tr>
                <td>${technique.id}</td>
                <td>${technique.name}</td>
                <td>${technique.type}</td>
                <td>${technique.description}</td>
                <td>
                    <a href="/techniques/details/${technique.id}" class="btn btn-info btn-sm">Details</a>
                </td>
                <td>
                    <a href="/techniques/delete/${technique.id}" class="btn btn-danger btn-sm">Delete</a>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });

    searchResultsSection.style.display = "block";
}
