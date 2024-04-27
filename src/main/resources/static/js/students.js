import { header, token} from "./util/csrf.js";

document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll('button.btn-danger');

    // Move addButton initialization here
    const addButton = document.getElementById("addButton");

    for (const deleteButton of deleteButtons) {
        deleteButton.addEventListener("click", handleDeleteStudent);
    }

    async function handleDeleteStudent(event) {
        const rowId = event.target.parentNode.parentNode.id;
        const studentId = parseInt(rowId.substring(rowId.indexOf('_') + 1));
        const response = await fetch(`/api/students/${studentId}`, {
            method: "DELETE",
            headers: {
                [header]: token
            }
        });
        if (response.status === 204) {
            const row = document.getElementById(`student_${studentId}`);
            row.parentNode.removeChild(row);
        }
    }

    // Move addButton event listener assignment here
    addButton.addEventListener("click", addNewStudent);

    const nameInput = document.getElementById("nameInput");
    const startInput = document.getElementById("startInput");
    const studentTableBody = document.getElementById("studentTableBody");

    async function addNewStudent() {
        const response = await fetch(`/api/students`, {
            method: "POST",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify({
                name: nameInput.value,
                startDate: startInput.value
            })
        });
        if (response.status === 201) {
            const student = await response.json();
            addStudentToTable(student);
        } else {
            alert("Failed to add student.");
        }
    }

    // function addStudentToTable(student) {
    //     const tableRow = document.createElement("tr");
    //     tableRow.id = `student_${student.id}`;
    //     tableRow.innerHTML = `
    //         <td>${student.id}</td>
    //         <td>${student.name}</td>
    //         <td>${student.startDate}</td>
    //         <td><a href="/student?id=${student.id}">Details</a></td>
    //         <td><button type="button" class="btn btn-danger btn-sm">Delete</button></td>
    //     `;
    //     studentTableBody.appendChild(tableRow);
    //
    //     const newDeleteButton = tableRow.querySelector('button');
    //     newDeleteButton.addEventListener("click", handleDeleteStudent);
    // }
    /**
     * @param {{id: number, name: string, startDate: string}} student
     */
    function addStudentToTable(student) {
        // Format the startDate using JavaScript's Date object
        const formattedStartDate = new Date(student.startDate).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: '2-digit'
        });

        const tableRow = document.createElement("tr");
        tableRow.id = `student_${student.id}`;
        tableRow.innerHTML = `
        <td>${student.name}</td>
        <td>${formattedStartDate}</td> <!-- Use formattedStartDate here -->
        <td><a href="/student?id=${student.id}">Details</a></td>
        <td><button type="button" class="btn btn-danger btn-sm">Delete</button></td>
    `;
        studentTableBody.appendChild(tableRow);

        const newDeleteButton = tableRow.querySelector('button');
        newDeleteButton.addEventListener("click", handleDeleteStudent);
    }

    addButton?.addEventListener("click", addNewStudent);
});
