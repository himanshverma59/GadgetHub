<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Bootstrap demo</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <jsp:include page="header.jsp" />
    <% String message=request.getParameter("message"); %>
    <div class="container mt-5 mb-5">
      <div class="row justify-content-center">
        <form action="./LoginServlet" class="col-md-4 myform" method="post">

          <div class="text-center mt-3">
            <h2 class="text-primary">Login Form</h2>
            <% if(message!=null){%>
            <p style="color: crimson"><%=message%></p>
            <%} %>
          </div>
          <div class="row mt-3">
            <div class="col-md-12 form-group">
              <label for="username">Username</label>
              <input name="username" type="text" class="form-control" id="username" required="" />
            </div>
          </div>
          <div class="row mt-3">
            <div class="col-md-12 from-group">
              <label for="password">Password</label>
              <input name="password" type="password" class="form-control" id="password" required="" />
            </div>
          </div>
          <div class="row mt-3">
            <div class="col-md-12 from-group">
              <label for="loginas">Login As</label>
              <select name="usertype" id="loginas" class="form-control">
                <option value="customer">Customer</option>
                <option value="admin">Admin</option>
              </select>
            </div>
          </div>
          <div class="row mt-3">
            <div class="col-md-12 text-center">
              <button type="submit" class="btn btn-primary">Login</button>
            </div>
          </div>
        </form>
      </div>
    </div>
    <jsp:include page="footer.jsp" />
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
