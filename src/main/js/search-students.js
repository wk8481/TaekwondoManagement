import '../scss/search-students.scss'
import Fuse from 'fuse.js'

const searchButton = document.getElementById('searchButton')
const searchResultsSection = document.getElementById('searchResultsSection')
const searchTermInput = document.getElementById('searchTerm')
const tableBody = document.getElementsByTagName('tbody')[0]

// Define Fuse options
const fuseOptions = {
    keys: ['name', 'startDate'], // Specify the fields to search in
    includeScore: true // Include search score in results
}

// Create a new instance of Fuse with your students data
let fuse = null

searchButton.addEventListener('click', async () => {
    const response = await fetch('/api/students')
    if (response.status === 200) {
        const students = await response.json()

        // Initialize Fuse with the students data
        fuse = new Fuse(students, fuseOptions)

        // Search for students based on the search term
        const searchResults = fuse.search(searchTermInput.value)

        tableBody.innerHTML = ''
        for (const result of searchResults) {
            const student = result.item
            tableBody.innerHTML += `
                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student.startDate}</td>
                    <td>
                        <a href="/students/${student.id}" class="btn btn-info btn-sm">Details</a>
                    </td>
                </tr>
            `
        }

        searchResultsSection.style.display = 'block'
    } else {
        searchResultsSection.style.display = 'none'
    }
})
