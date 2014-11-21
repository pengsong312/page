page
====

很方便的java分页操作


<title>用户列表</title>
</head>
<body>
<c:forEach var="user" items="${pageBean.result }">
    <p>${user.id }________________${user.name }</p>
</c:forEach>
${ pageBean.pageToolBar}
</body>
