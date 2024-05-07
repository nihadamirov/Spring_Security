// Register Form Submission
document.getElementById('registerForm').addEventListener('submit', function (event) {
    event.preventDefault();
    var formData = new FormData(this);
    var role = document.getElementById('role').value;
    formData.append('authorities', role);
    fetch('/auth/registration', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            window.location.href = "/login.html";
        })
        .catch(error => {
            console.error('Error:', error);
        });
});


// Login Form Submission
document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();
    var formData = new FormData(document.getElementById('loginForm'));
    fetch('/auth/login', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            console.log(data); // Handle response from backend
            var token = data.token; // Assuming token is received in response
            // Use token in subsequent requests
            var role = token.includes("ADMIN") ? "admin" : "user";
            var endpoint = role === "admin" ? "/auth/admin" : "/auth/user";
            fetch(endpoint, {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            })
                .then(response => {
                    if (response.ok) {
                        return response.text();
                    }
                    throw new Error('Network response was not ok.');
                })
                .then(data => {
                    console.log(data); // Handle response from admin or user endpoint
                })
                .catch(error => {
                    console.error('Error:', error); // Handle error
                });
        })
        .catch(error => {
            console.error('Error:', error); // Handle error
        });
});

// Password Reset Form Submission
document.getElementById('passwordResetForm').addEventListener('submit', function (event) {
    event.preventDefault();
    var formData = new FormData(document.getElementById('passwordResetForm'));
    fetch('/api/password/verifyMail/' + formData.get('email'), {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(data => {
            console.log(data); // Handle response from backend
            document.getElementById('otpSection').style.display = "block";
        })
        .catch(error => {
            console.error('Error:', error); // Handle error
        });
});

// Password Reset Submission
document.getElementById('resetButton').addEventListener('click', function (event) {
    event.preventDefault();
    var formData = new FormData(document.getElementById('passwordResetForm'));
    var otp = formData.get('otp');
    var newPassword = formData.get('newPassword');
    var repeatPassword = formData.get('repeatPassword');
    if (newPassword === repeatPassword) {
        fetch('/api/password/change/' + formData.get('email'), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                password: newPassword,
                repeatPassword: repeatPassword
            })
        })
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error('Network response was not ok.');
            })
            .then(data => {
                console.log(data); // Handle response from backend
            })
            .catch(error => {
                console.error('Error:', error); // Handle error
            });
    } else {
        console.error('Passwords do not match.');
    }
});
