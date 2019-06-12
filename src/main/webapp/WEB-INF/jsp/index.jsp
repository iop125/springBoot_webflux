<%--
  Created by IntelliJ IDEA.
  User: miaoyu1
  Date: 2019-05-09
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script type="text/javascript">
   var es =new EventSource("/sseServlet");
   es.onmessage = function (evt) {
       if(evt.date == 9){
           console,log("----",evt.date)
       }
   }
</script>
<head>
    <title>Title</title>
</head>
<body>
</body>
</html>
