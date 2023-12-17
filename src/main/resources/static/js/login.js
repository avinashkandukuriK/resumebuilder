// validation.js

function validateLoginForm() {
    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    // Simple email validation
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Invalid email format');
        return false;
    }

    // Password should be at least 6 characters
    if (password.length < 6) {
        alert('Password must be at least 6 characters');
        return false;
    }

    // If validation passes, continue with the form submission
    return true;
}

function onGoogleSignIn(googleUser) {
    var id_token = googleUser.getAuthResponse().id_token;

    // Send the 'id_token' to your server for authentication
    // You can use AJAX or other methods to make an API call to your Java controller
    // Example using jQuery:
    $.ajax({
        type: 'POST',
        url: '/api/googleSignIn',
        data: { idToken: id_token },
        success: function(response) {
            console.log(response);
            // Handle the server response as needed

            // If login is successful, add Resume and Cover Letter tabs
            if (response === 'Google Sign-In Successful') {
                addResumeAndCoverLetterTabs();
            }
        },
        error: function(error) {
            console.error(error);
            // Handle the error as needed
        }
    });
}

function addResumeAndCoverLetterTabs() {
    // Add your logic to dynamically add Resume and Cover Letter tabs
    // For example, you can manipulate the DOM, show/hide elements, or load new content
    alert('Resume and Cover Letter tabs added');
}
