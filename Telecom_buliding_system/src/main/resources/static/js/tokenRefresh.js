document.addEventListener('DOMContentLoaded', function() {
    // Set up token refresh every 8 minutes (slightly before the 10-minute expiration)
    setInterval(function() {
        refreshToken();
    }, 8 * 60 * 1000);
    
    function refreshToken() {
        fetch('/refresh-token', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'same-origin'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Token refresh failed');
            }
            return response.json();
        })
        .then(data => {
            console.log("Token refreshed successfully");
        })
        .catch(error => {
            console.error('Error refreshing token:', error);
            // Redirect to login if token refresh fails
            window.location.href = '/login';
        });
    }
});