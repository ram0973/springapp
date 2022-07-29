import React, { useState } from "react"
import { AxiosInstance } from "axios"
import { useNavigate } from "react-router-dom"
import { Container } from "./style"

function signUp() {
    const history = useNavigate();

    const initialFormData = Object.freeze({
        email: '',
        password: ''
    })
}

export const SignupForm = () => {
    return (<Container>Signup</Container>)
}
