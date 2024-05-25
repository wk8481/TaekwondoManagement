import '../scss/student.scss'
import { header, token } from './util/csrf.js'
import { Notyf } from 'notyf'
import 'notyf/notyf.min.css'

const notyf = new Notyf()

const toggleTechniquesButton = document.getElementById('toggleTechniquesInformation')
const tableBody = document.getElementById('techniquesInformationBody')

async function toggleTechniquesTable() {
    const techniquesTable = document.getElementById('techniquesInformation')
    const buttonWrapper = document.getElementById('dropdownButtonWrapper')

    if (techniquesTable.style.display === 'table') {
        techniquesTable.style.display = 'none'
        buttonWrapper.classList.remove('dropup')
        buttonWrapper.classList.add('dropdown')
    } else {
        const studentIdInput = document.getElementById('studentId')
        const response = await fetch(`/api/students/${studentIdInput.value}/techniques`)

        if (response.status === 200) {
            const techniques = await response.json()
            tableBody.innerHTML = ''

            for (const technique of techniques) {
                const row = document.createElement('tr')
                row.innerHTML = `
                    <td>${technique.name}</td>
                    <td>${technique.type}</td>
                    <td>${technique.description}</td>
                `
                tableBody.appendChild(row)
            }

            techniquesTable.style.display = 'table'
            buttonWrapper.classList.remove('dropdown')
            buttonWrapper.classList.add('dropup')
        } else if (response.status === 404) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="3">Student not found</td>
                </tr>
            `
            techniquesTable.style.display = 'table'
            buttonWrapper.classList.remove('dropdown')
            buttonWrapper.classList.add('dropup')
        } else {
            notyf.error('Error fetching techniques')
            console.error('Error fetching techniques')
        }
    }
}

toggleTechniquesButton.addEventListener('click', toggleTechniquesTable)

const updateButton = document.getElementById('updateButton')
const startDateInput = document.getElementById('startDateInput')

async function changeStartDate() {
    const studentIdInput = document.getElementById('studentId')

    // Check if elements are null
    if (!startDateInput) {
        console.error('startDateInput not found in the DOM.')
        return
    }

    if (!studentIdInput) {
        console.error('studentIdInput not found in the DOM.')
        return
    }

    try {
        const response = await fetch(`/api/students/${studentIdInput.value}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify({
                startDate: startDateInput.value
            })
        })

        if (response.status === 204) { // Check for 204 No Content
            updateButton.disabled = true
            notyf.success('Start date updated successfully')
        } else {
            notyf.error('Error updating start date')
            console.error('Error updating start date', response)
        }
    } catch (error) {
        notyf.error('Error updating start date')
        console.error('Error updating start date', error)
    }
}

updateButton?.addEventListener('click', changeStartDate)
startDateInput?.addEventListener('input', () => updateButton.disabled = false)
