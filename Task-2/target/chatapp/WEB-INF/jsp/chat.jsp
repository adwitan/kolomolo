<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chat&Transcription</title>
    <style>
        <%@include file="/WEB-INF/css/styles.css"%>
    </style>
</head>
<body>

<div class="container">
    <h2>Chat</h2>
    <c:if test="${not empty responseMessage}">
        <div id="chat-history">
            <div class="message" style="text-align: right;">
                <span class="content">${responseMessage.question}</span>
                <br>
            </div>
            <div class="message answer">
                <span class="content">${responseMessage.answer}</span>
                <br>
            </div>
        </div>
    </c:if>

    <form action="/chat" method="post">
        <textarea id="question" name="question" placeholder="message..." rows="1" cols="30"></textarea><br>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Send">
    </form>
</div>
<hr style="width:100%;text-align:left;margin-left:0">
<div class="container">
    <h2>Transcription</h2>
    <form action="/transcription" method="post" enctype="multipart/form-data">
        <label for="file">Add file:</label><br>
        <input type="file" id="file" name="file"><br>
        <input type="submit" value="Send">
        <br>
        <span class="content">${responseTranscription.text}</span>
    </form>
</div>
</body>
</html>
