import { header, token } from "./util/csrf.js";

const toggleTechniquesButton = document.getElementById("toggleTechniquesInformation");
const tableBody = document.getElementById("techniquesInformationBody");

async function toggleTechniquesTable() {
    const techniquesTable = document.getElementById("techniquesInformation");
    const buttonWrapper = document.getElementById("dropdownButtonWrapper");

    if (techniquesTable.style.display === "table") {
        techniquesTable.style.display = "none";
        buttonWrapper.classList.remove("dropup");
        buttonWrapper.classList.add("dropdown");
    } else {
        const studentIdInput = document.getElementById("studentId");
        const response = await fetch(`/api/students/${studentIdInput.value}/techniques`);

        if (response.status === 200) {
            const techniques = await response.json();
            tableBody.innerHTML = '';

            for (const technique of techniques) {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${technique.name}</td>
                    <td>${technique.type}</td>
                    <td>${technique.description}</td>
                `;
                tableBody.appendChild(row);
            }

            techniquesTable.style.display = "table";
            buttonWrapper.classList.remove("dropdown");
            buttonWrapper.classList.add("dropup");
        } else if (response.status === 404) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="3">Student not found</td>
                </tr>
            `;
            techniquesTable.style.display = "table";
            buttonWrapper.classList.remove("dropdown");
            buttonWrapper.classList.add("dropup");
        } else {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="3">Error fetching techniques</td>
                </tr>
            `;
            techniquesTable.style.display = "table";
            buttonWrapper.classList.remove("dropdown");
            buttonWrapper.classList.add("dropup");
        }
    }
}

toggleTechniquesButton.addEventListener("click", toggleTechniquesTable);

const updateButton = document.getElementById("updateButton");
const startDateInput = document.getElementById("startDateInput");
async function changeStartDate() {

    const studentIdInput = document.getElementById("studentId");

    // Check if startDateInput is null
    if (!startDateInput) {
        console.error("startDateInput not found in the DOM.");
        return;
    }

    // Check if studentIdInput is null
    if (!studentIdInput) {
        console.error("studentIdInput not found in the DOM.");
        return;
    }

    const response = await fetch(`/api/students/${studentIdInput.value}`, {
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
        updateButton.disabled = true;
    } else {
        alert("Error updating start date");
    }
}


updateButton?.addEventListener("click", changeStartDate);
startDateInput?.addEventListener("input", () => updateButton.disabled = false);
