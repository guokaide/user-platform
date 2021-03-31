<head>
    <jsp:directive.include file="/WEB-INF/jsp/prelude/include-head-meta.jspf"/>
    <title>My Home Page</title>
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <form class="form-register" action="<%=request.getContextPath()%>/register/submit" method="post">
        <h1 class="h3 mb-3 font-weight-normal">注册</h1>
        <table>
            <tr>
                <td>Name: </td>
                <td>
                    <input name="name" type="text" class="form-control" placeholder="请输入姓名" required autofocus>
                </td>
            </tr>
            <tr>
                <td>Password: </td>
                <td>
                    <input name="password" type="password" class="form-control" placeholder="请输入密码" required>
                </td>
            </tr>
            <tr>
                <td>Email: </td>
                <td>
                    <input name="email" type="email" class="form-control" placeholder="请输入电子邮箱" required autofocus>
                </td>
            </tr>
            <tr>
                <td>PhoneNumber: </td>
                <td>
                    <input name="phoneNumber" type="text" class="form-control" placeholder="请输入电话号码" required autofocus>
                </td>
            </tr>
        </table>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
        <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
    </form>
</div>
</body>