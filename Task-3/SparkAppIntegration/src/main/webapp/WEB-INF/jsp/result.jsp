<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <style>
        <%@include file="/WEB-INF/jsp/styles.css"%>
    </style>
</head>
<body>
<div class="container">
    <form id="refreshForm">
        <div class="buttons">
            <button type="submit" class="btn" onclick="refresh()">Refresh</button>
            <button type="button" class="btn" onclick="runSpark()">Run</button>
            <button type="submit" class="btn" onclick="sync()">Sync with Hdfs</button>
        </div>
    </form>
    <div class="table-container" id="tableContainer">
        <table id="data">
            <thead>
            <tr>
                <th>Cuisine</th>
                <th>Count</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cuisine" items="${cuisineList}">
                <tr>
                    <td>${cuisine.name}</td>
                    <td>${cuisine.count}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function runSpark() {
        fetch('/start', {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    alert("Spark job started successfully!");
                } else {
                    alert("Error starting Spark job!");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Error starting Spark job!");
            });
    }

    function sync() {
        fetch('/sync', {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    alert("Spark job started successfully!");
                } else {
                    alert("Error starting Spark job!");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Error starting Spark job!");
            });
    }

    function refresh() {
        fetch('/result')
            .then(response => response.json())
            .then(data => {
                console.log(data);
            })
            .catch(error => console.error('Error:', error));
    }
</script>
</body>
</html>