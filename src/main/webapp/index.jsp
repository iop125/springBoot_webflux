<!DOCTYPE html>
<html>
<script type="text/javascript">
    var es = new EventSource("/sseServlet");
    es.onmessage = function (evt) {
        console.log("----ok", evt.data)
        if (evt.data == 9) {
            es.close();
        }
    }
    es.addEventListener("test", function (evt) {
        console.log("----test", evt.data)
        if (evt.data == 5) {
            es.close();
        }
    })
</script>
<head>
    <title>Title</title>
</head>
<body>
1111111111
</body>
</html>
