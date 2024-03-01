// students.js
const deleteButtons = document.querySelectorAll('button.btn-danger');

for (const deleteButton of deleteButtons) {
    deleteButton.addEventListener("click", handleDeleteStudent);
}

async function handleDeleteStudent(event) {
    const rowId = event.target.parentNode.parentNode.id;
    const studentId = parseInt(rowId.substring(rowId.indexOf('_') + 1));
    const response = await fetch(`/api/students/${studentId}`, {
        method: "DELETE"
    })
    if (response.status === 204) {
        const row = document.getElementById(`student_${studentId}`);
        row.parentNode.removeChild(row);
    }
}

const addButton = document.getElementById("addButton");
const studentTableBody = document.getElementById("studentTableBody");

async function addNewStudent() {
    addStudentToTable({id: 999, name: "New Student", start: "2024-02-22"});
}

/**
 * @param {{id: number, name: string, start: string}} student
 */
function addStudentToTable(student) {
    const tableRow = document.createElement("tr");
    tableRow.id = `student_${student.id}`;
    tableRow.innerHTML = `
        <td>${student.id}</td>
        <td>${student.name}</td>
        <td>${student.start}</td>
        <td><a href="/students/details/${student.id}">Details</a></td>
        <td><button type="button" class="btn btn-danger btn-sm">Delete</button></td>
    `
    studentTableBody.appendChild(tableRow);

    const newDeleteButton = tableRow.querySelector('button');
    newDeleteButton.addEventListener("click", handleDeleteStudent)
}

addButton.addEventListener("click", addNewStudent);
