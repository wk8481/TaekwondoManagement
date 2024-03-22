const searchButton = document.getElementById("searchButton");
const searchResultsSection = document.getElementById("searchResultsSection");
const searchTermInput = document.getElementById("searchTerm");
const tableBody = document.querySelector("#searchResultsSection tbody");

searchButton.addEventListener("click", async () => {
    const searchTerm = searchTermInput.value.trim();
    if (!searchTerm) {
        alert("Please enter a search term.");
        return;
    }

    try {
        const response = await fetch(`/api/students/search?searchTerm=${searchTerm}`,
            { headers: { "Accept": "application/json" } });
        if (response.status === 200) {
            const students = await response.json();
            displaySearchResults(students);
        } else {
            alert("An error occurred while searching for students.");
        }
    } catch (error) {
        console.error("Error fetching data:", error);
        alert("An error occurred. Please try again.");
    }
});

function displaySearchResults(students) {
    tableBody.innerHTML = '';
    if (students.length === 0) {
        searchResultsSection.style.display = "none";
        return;
    }

    students.forEach(student => {
        const row = `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.startDate}</td>
                <td>
                    <a href="/students/${student.id}" class="btn btn-info btn-sm">Details</a>
                </td>
                <td>
                    <button onclick="deleteStudent(${student.id})" class="btn btn-danger btn-sm">Delete</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });

    searchResultsSection.style.display = "block";
}

async function deleteStudent(studentId) {
    if (confirm("Are you sure you want to delete this student?")) {
        try {
            const response = await fetch(`/api/students/${studentId}`, {
                method: "DELETE",
            });
            if (response.ok) {
                alert("Student deleted successfully.");
                // Refresh the search results after deletion
                searchButton.click();
            } else {
                alert("An error occurred while deleting the student.");
            }
        } catch (error) {
            console.error("Error deleting student:", error);
            alert("An error occurred. Please try again.");
        }
    }
}
