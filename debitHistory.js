<script>
    window.onload = function() {
        fetch('DebitHistoryServlet')  // Call the servlet to get data
            .then(response => response.text())
            .then(data => {
                document.querySelector('#debitList tbody').innerHTML = data;
            })
            .catch(error => console.error('Error loading debit history:', error));
    };
</script>
