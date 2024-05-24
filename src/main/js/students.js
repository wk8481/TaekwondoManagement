import anime from 'animejs'
import * as Joi from 'joi'
import { header, token } from './util/csrf.js'
import { Notyf } from 'notyf'
import 'notyf/notyf.min.css'

document.addEventListener('DOMContentLoaded', function () {
    const deleteButtons = document.querySelectorAll('button.btn-danger')
    const addButton = document.getElementById('addButton')
    const nameInput = document.getElementById('nameInput')
    const startInput = document.getElementById('startInput')
    const studentTableBody = document.getElementById('studentTableBody')
    const nameError = document.getElementById('nameError')
    const startError = document.getElementById('startError')
    const addStudentForm = document.getElementById('addStudentForm')

    const notyf = new Notyf({
        duration: 3000,
        position: {
            x: 'right',
            y: 'top'
        }
    })

    for (const deleteButton of deleteButtons) {
        deleteButton.addEventListener('click', handleDeleteStudent)
    }

    addButton?.addEventListener('click', trySubmitForm)

    async function handleDeleteStudent(event) {
        const rowId = event.target.parentNode.parentNode.id
        const studentId = parseInt(rowId.substring(rowId.indexOf('_') + 1))
        const response = await fetch(`/api/students/${studentId}`, {
            method: 'DELETE',
            headers: {
                [header]: token
            }
        })
        if (response.status === 204) {
            const row = document.getElementById(`student_${studentId}`)
            anime({
                targets: row,
                opacity: 0,
                easing: 'linear',
                duration: 600,
                direction: 'normal',
                complete: () => {
                    row.parentNode.removeChild(row)
                }
            })
        }
    }

    function trySubmitForm() {
        const schema = Joi.object({
            name: Joi.string()
                .min(3)
                .max(30)
                .required(),
            startDate: Joi.date()
                .iso()
                .required()
        })

        const studentObject = {
            name: nameInput.value,
            startDate: startInput.value
        }

        const validationResult = schema.validate(studentObject, { abortEarly: false })

        // Clear previous error messages
        nameError.innerHTML = ''
        startError.innerHTML = ''

        if (validationResult.error) {
            for (const errorDetail of validationResult.error.details) {
                if (errorDetail.context.key === 'name') {
                    nameError.innerHTML = errorDetail.message
                } else if (errorDetail.context.key === 'startDate') {
                    startError.innerHTML = errorDetail.message
                }
            }
        } else {
            addNewStudent()
        }
    }

    async function addNewStudent() {
        const response = await fetch('/api/students', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify({
                name: nameInput.value,
                startDate: startInput.value
            })
        })

        if (response.status === 201) {
            const student = await response.json()
            addStudentToTable(student)
            // Show success notification
            notyf.success('Yippee! Student enrolled successfully!')
            // Reset the form after a successful submission
            addStudentForm.reset()
        } else {
            notyf.error('Oh no, student enrollment failed!')
        }
    }

    function addStudentToTable(student) {
        const formattedStartDate = new Date(student.startDate).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: '2-digit'
        })

        const tableRow = document.createElement('tr')
        tableRow.id = `student_${student.id}`
        tableRow.innerHTML = `
            <td>${student.name}</td>
            <td>${formattedStartDate}</td>
            <td><a href="/student?id=${student.id}">Details</a></td>
            <td><button type="button" class="btn btn-danger btn-sm"><i class="bi bi-trash3"></i>Delete</button></td>
        `
        studentTableBody.appendChild(tableRow)

        const newDeleteButton = tableRow.querySelector('button')
        newDeleteButton.addEventListener('click', handleDeleteStudent)
    }
})
