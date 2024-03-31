import { header, token } from "./util/csrf.js";

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
        } else if (response.status === 404) {
            // Handle 404 - Student not found
            console.error("Student not found");
            // You can display an error message to the user, for example:
            tableBody.innerHTML = `
                <tr>
                    <td colspan="3">Student not found</td>
                </tr>
            `;
            showTechniquesTable();
        } else {
            // Handle other errors
            console.error("Error fetching techniques");
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

const startDateInput = document.getElementById("startDate");
const updateStartDateButton = document.getElementById("updateStartDateButton");

async function changeStartDate() {
    const studentId = studentIdInput.value;


    const response = await fetch(`/api/students/${studentId}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            startDate: startDateInput.value
        })
    });

    if (response.status === 200) {
        updateStartDateButton.disabled = true;
    } else {
        // Optionally handle error
        alert("Error updating start date");
    }
}

updateStartDateButton?.addEventListener("click", changeStartDate);
startDateInput?.addEventListener("input", () => updateStartDateButton.disabled = false);
