<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home | Bus_Ticketing</title>

    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
      crossorigin="anonymous"
    />
    <!-- JavaScript Bundle with Popper -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
      crossorigin="anonymous"
    ></script>
  </head>
  <body>
    <header>
      <div class="container bg-info rounded-bottom">
        <div class="row flex">
          <div class="col-3 my-3">
            <img
              src="https://www.bestbus.in/assets/images/news-cms/Royal_Travels_Online_Bus_Ticket_Booking1.png"
              class="img-thumbnail rounded float-start"
              alt="Bus_Image"
              height="200"
              width="300"
            />
          </div>
          <div class="col-5 my-3">
            <h1 class="pt-5">Bus Ticketing</h1>
          </div>
          <!-- <div class="col"></div> -->
          <div class="col-4 pt-5" id="loginfo">
            <a href="login.html" class="btn btn-warning mt-4 ms-5">Login</a>
            <a
              href="Register.html"
              class="btn btn-outline-light text-warning mt-4 ms-2"
              >Register</a
            >
          </div>
        </div>
      </div>
    </header>
    <div style="height: 3rem" class="container bg-primary bg-opacity-10"></div>
    <div class="container bg-primary bg-opacity-10">
      <div class="row pt-15">
        <div class="col-6">
          <h2 class="pb-15">Search Buses</h2>
        </div>
        <div class="col"></div>
      </div>
      <form action="bus" method="GET">
      <div class="row my-3">
        <div class="col-4">
          <div class="input-group mb-3">
            <span class="input-group-text" id="inputGroup-sizing-default"
              >Start:</span
            >
            <input
              type="text"
              name="startterminal"
              class="form-control"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              id="start"
            />
          </div>
        </div>
        <div class="col-4">
          <div class="input-group mb-3">
            <span class="input-group-text" id="inputGroup-sizing-default"
              >End:</span
            >
            <input
              type="text"
              name="endterminal"
              class="form-control"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              id="end"
            />
          </div>
        </div>
        <div class="col flex">
          <div class="input-group mb-3">
            <span class="input-group-text" id="inputGroup-sizing-default"
              >Date</span
            >
            <input
              type="date"
              class="form-control"
              name="date"
              aria-label="Sizing example input"
              aria-describedby="inputGroup-sizing-default"
              id="date"
            />
          </div>
        </div>
        <div class="col" id="get-btn">
          <input type="submit" class="btn btn-primary">
            Get Bus
        </input>
        </div>
      </div>
      </form>
    </div>

    <div class="container" id="buslistcontainer">
      <div class="row">
        <div class="col">
          <h2>Available Buses</h2>
        </div>
      </div>
      
    </div>
  </body>
</html>
