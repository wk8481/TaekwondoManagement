document.addEventListener("DOMContentLoaded", function () {
    const deleteButtons = document.querySelectorAll('button.btn-danger');
    const addButton = document.getElementById("addButton");
    const studentTableBody = document.getElementById("studentTableBody");

    for (const deleteButton of deleteButtons) {
        deleteButton.addEventListener("click", handleDeleteStudent);
    }

    async function handleDeleteStudent(event) {
        const rowId = event.target.parentNode.parentNode.id;
        const studentId = parseInt(rowId.substring(rowId.indexOf('_') + 1));
        const response = await fetch(`/api/students/${studentId}`, {
            method: "DELETE"
        });
        if (response.status === 204) {
            const row = document.getElementById(`student_${studentId}`);
            row.parentNode.removeChild(row);
        }
    }

    addButton.addEventListener("click", addNewStudent);

    async function addNewStudent() {
        const nameInput = document.getElementById("nameInput").value;
        const startInput = document.getElementById("startInput").value;

        const response = await fetch(`/api/students`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: nameInput,
                start: startInput
            })
        });
        if (response.ok) {
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
});
