// studentdetails.js
const studentIdInput = document.getElementById("studentId");
const toggleTechniquesButton = document.getElementById("toggleTechniquesInformation");
const techniquesTable = document.getElementById("techniquesInformation");
const buttonWrapper = document.getElementById("dropdownButtonWrapper");
const tableBody = document.getElementById("techniquesInformationBody");

async function toggleTechniquesTable() {
    if (techniquesTable.style.display === "table") {
        hideTechniquesTable();
    } else {
        const response = await fetch(`/api/students/${studentIdInput.value}/techniques`);
        if (response.status === 200) {
            const techniques = await response.json();
            tableBody.innerHTML = '';
            for (const technique of techniques) {
                tableBody.innerHTML += `
                    <tr>
                        <td>${technique.name}</td>
                        <td>${technique.type}</td>
                        <td>${technique.description}</td>
                    </tr>
                `;
            }
            showTechniquesTable();
        }
    }
}

function hideTechniquesTable() {
    techniquesTable.style.display = "none";
    buttonWrapper.classList.remove("dropup");
    if (!buttonWrapper.classList.contains("dropdown")) {
        buttonWrapper.classList.add("dropdown");
    }
}

function showTechniquesTable() {
    techniquesTable.style.display = "table";
    buttonWrapper.classList.remove("dropdown");
    if (!buttonWrapper.classList.contains("dropup")) {
        buttonWrapper.classList.add("dropup");
    }
}

toggleTechniquesButton.addEventListener("click", toggleTechniquesTable);

const studentNameInput = document.getElementById("studentName");
const updateButton = document.getElementById("updateButton");

async function updateStudent() {
    const studentId = studentIdInput.value;
    const updatedName = studentNameInput.value;

    const response = await fetch(`/api/students/${studentId}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ name: updatedName })
    });

    if (response.status === 200) {
        // Optionally handle success
        console.log("Student updated successfully");
    } else {
        // Optionally handle error
        console.error("Error updating student");
    }
}

updateButton.addEventListener("click", updateStudent);
studentNameInput.addEventListener("input", () => updateButton.disabled = false);
