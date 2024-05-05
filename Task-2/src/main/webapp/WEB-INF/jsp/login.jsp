<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log in with your account</title>
    <style>
        <%@include file="/WEB-INF/css/styles.css"%>
    </style></head>
<body>
<div class="container">
    <form method="GET" action="/login" class="form-signin">
        <h2 class="form-heading">Log in</h2>
        <input id="username" name="username" type="text" class="form-control" placeholder="Username" autofocus>
        <input id="password" name="password" type="password" class="form-control" placeholder="Password">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
    </form>
</div>
</body>
</html>
